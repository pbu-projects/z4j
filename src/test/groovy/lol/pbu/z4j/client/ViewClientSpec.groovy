/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lol.pbu.z4j.client

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import java.util.Set
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.SortOrder
import lol.pbu.z4j.model.View
import lol.pbu.z4j.model.ViewCondition
import lol.pbu.z4j.model.ViewConditionValue
import lol.pbu.z4j.model.ViewConditions
import lol.pbu.z4j.model.ViewCreateRequest
import lol.pbu.z4j.model.ViewExecuteResponse
import lol.pbu.z4j.model.ViewOutput
import lol.pbu.z4j.model.ViewResponse
import lol.pbu.z4j.model.ViewUpdateRequest
import spock.lang.Shared

import static io.micronaut.http.HttpStatus.FORBIDDEN
import static io.micronaut.http.HttpStatus.UNAUTHORIZED

@MicronautTest
class ViewClientSpec extends Z4jSpec {

    @Shared
    ViewClient adminViewClient

    @Shared
    ViewClient userViewClient

    @Shared
    ViewClient badEmailViewClient

    @Shared
    ViewClient badUrlViewClient

    @Shared
    Long firstActiveViewId

    @Shared
    Set<Long> createdViewIds = ConcurrentHashMap.newKeySet()

    def setupSpec() {
        adminViewClient = adminCtx.getBean(ViewClient.class)
        userViewClient = userCtx.getBean(ViewClient.class)
        badEmailViewClient = badEmailCtx.getBean(ViewClient.class)
        badUrlViewClient = badUrlCtx.getBean(ViewClient.class)
        def views = adminViewClient.listActiveViews().block().views()
        if (views != null && !views.isEmpty()) {
            firstActiveViewId = views.get(0).id()
        }
    }

    def cleanupSpec() {
        createdViewIds.each { id ->
            safeDelete(id)
        }
    }

    def "can list views"() {
        when:
        def response = adminViewClient.listViews().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            it != null
            views() != null
        }
    }

    def "can list active views"() {
        when:
        def response = adminViewClient.listActiveViews().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            it != null
            views() != null
        }
    }

    def "can show a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.showView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            it != null
            view() != null
            view().id() == firstActiveViewId
            view().conditions() != null
            view().execution() != null
        }
    }

    def "can execute a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.executeView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            it != null
            rows() != null
            columns() != null
        }
    }

    def "can count a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.countView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            it != null
            viewCount() != null
            viewCount().viewId() == firstActiveViewId
        }
    }

    def "can list tickets for a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.listTicketsForView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            it != null
            tickets() != null
        }
    }

    def "can list tickets for a view with cursor pagination"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.listTicketsForView(firstActiveViewId, null, 10).block()

        then:
        noExceptionThrown()
        response != null
        if (response.tickets() != null && !response.tickets().isEmpty()) {
            response.meta() != null
        }
    }

    def "can create, show, update, execute, and delete a view"() {
        given: "a new view definition with conditions and output configuration"
        String initialTitle = "Z4J Test View " + UUID.randomUUID().toString().substring(0, 8)
        String initialDescription = "Initial test view description"
        View newView = new View(initialTitle)
                .withDescription(initialDescription)
                .withActive(true)
                .withConditions(new ViewConditions()
                        .addAllItem(new ViewCondition("status", "less_than", "solved"))
                        .addAnyItem(new ViewCondition("priority", "is", "normal")))
                .withOutput(new ViewOutput(["status", "requester", "assignee"])
                        .withGroupBy("status")
                        .withGroupOrder(SortOrder.ASCENDING)
                        .withSortBy("updated")
                        .withSortOrder(SortOrder.DESCENDING))
        Long createdId = null

        when: "creating the view"
        ViewResponse createResponse = adminViewClient.createView(new ViewCreateRequest(newView)).block()
        if (createResponse?.view()?.id() != null) {
            createdId = createResponse.view().id()
            createdViewIds.add(createdId)
        }

        then: "the view is created with the requested settings"
        noExceptionThrown()
        verifyAll(createResponse) {
            it != null
            view() != null
            createdId != null
            view().title() == initialTitle
            view().description() == initialDescription
            view().active() == true
            view().conditions() != null
            view().conditions().all() != null
            !view().conditions().all().isEmpty()
            view().conditions().all().first().field() == "status"
            view().execution() != null
            view().execution().columns() != null
        }

        when: "showing the created view by id"
        ViewResponse showResponse = adminViewClient.showView(createdId).block()

        then: "the retrieved view matches the created view"
        noExceptionThrown()
        verifyAll(showResponse) {
            it != null
            view() != null
            view().id() == createdId
            view().title() == initialTitle
            view().description() == initialDescription
        }

        when: "updating the view title, description, conditions, and output columns"
        String updatedTitle = "Z4J Updated View " + UUID.randomUUID().toString().substring(0, 8)
        String updatedDescription = "Updated test view description"
        View updatePayload = new View(updatedTitle)
                .withDescription(updatedDescription)
                .addAllItem(new ViewCondition("status", "is", "open"))
                .withOutput(new ViewOutput(["status", "priority"]))
        ViewResponse updateResponse = adminViewClient.updateView(createdId, new ViewUpdateRequest(updatePayload)).block()

        then: "the view reflects the updated configuration"
        noExceptionThrown()
        verifyAll(updateResponse) {
            it != null
            view() != null
            view().id() == createdId
            view().title() == updatedTitle
            view().description() == updatedDescription
            view().conditions() != null
            view().conditions().all() != null
            view().conditions().all().first().value() == ViewConditionValue.of("open")
        }

        when: "executing the updated view"
        ViewExecuteResponse executeResponse = adminViewClient.executeView(createdId).block()

        then: "the view executes and returns typed columns and rows"
        noExceptionThrown()
        verifyAll(executeResponse) {
            it != null
            rows() != null
            columns() != null
            !columns().isEmpty()
        }

        when: "deleting the view"
        adminViewClient.deleteView(createdId).block()
        createdViewIds.remove(createdId)

        then: "delete completes without error"
        noExceptionThrown()

        cleanup: "always remove the created test view if still present"
        safeDelete(createdId)
    }

    def "can create and read back a view with #scenario condition value"(
            String scenario, String field, String operator, ViewConditionValue value, ViewConditionValue expectedValue) {
        given: "a view with a base status condition and the target condition value"
        String title = "Z4J Condition Test " + UUID.randomUUID().toString().substring(0, 8)
        View newView = new View(title)
                .withActive(true)
                .addAllItem(new ViewCondition("status", "less_than", "closed"))
                .addAnyItem(new ViewCondition(field, operator, value))
        Long createdId = null

        when: "creating the view"
        ViewResponse createResponse = adminViewClient.createView(new ViewCreateRequest(newView)).block()
        if (createResponse?.view()?.id() != null) {
            createdId = createResponse.view().id()
            createdViewIds.add(createdId)
        }

        and: "reading the created view back"
        ViewResponse showResponse = adminViewClient.showView(createdId).block()

        then: "the stored condition matches the expected value"
        noExceptionThrown()
        verifyAll(showResponse) {
            it != null
            view() != null
            view().id() == createdId
            view().conditions() != null
            view().conditions().any() != null
            !view().conditions().any().isEmpty()
            verifyAll(view().conditions().any().first()) {
                it.field() == field
                it.operator() == operator
                it.value() == expectedValue
            }
        }

        cleanup: "remove the created test view"
        safeDelete(createdId)

        where:
        scenario                  | field          | operator       | value                                    | expectedValue
        "text status open"        | "status"       | "is"           | ViewConditionValue.of("open")            | ViewConditionValue.of("open")
        "text status solved"      | "status"       | "less_than"    | ViewConditionValue.of("solved")          | ViewConditionValue.of("solved")
        "text priority"           | "priority"     | "is"           | ViewConditionValue.of("high")            | ViewConditionValue.of("high")
        "text tag"                | "current_tags" | "includes"     | ViewConditionValue.of("vip")             | ViewConditionValue.of("vip")
        "text list tags"          | "current_tags" | "includes"     | ViewConditionValue.of(["vip", "urgent"]) | ViewConditionValue.of("vip urgent")
        "null unassigned"         | "assignee_id"  | "is"           | null                                     | ViewConditionValue.of("")
    }

    def "cannot create a view with invalid payload: #scenario"(String scenario, View invalidView) {
        when: "creating a view with an invalid payload"
        ViewResponse response = adminViewClient.createView(new ViewCreateRequest(invalidView)).block()
        if (response?.view()?.id() != null) {
            createdViewIds.add(response.view().id())
        }

        then: "Zendesk rejects the request with a 4xx client error"
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.status.code >= 400 && error.status.code < 500

        where:
        scenario                     | invalidView
        "blank title"                | new View("").addAllItem(new ViewCondition("status", "is", "open"))
        "missing conditions"         | new View("Z4J Missing Conditions " + UUID.randomUUID().toString().substring(0, 8))
        "invalid condition field"    | new View("Z4J Bad Field " + UUID.randomUUID().toString().substring(0, 8)).addAllItem(new ViewCondition("nonexistent_field_xyz", "is", "open"))
        "invalid condition operator" | new View("Z4J Bad Op " + UUID.randomUUID().toString().substring(0, 8)).addAllItem(new ViewCondition("status", "nonexistent_operator_xyz", "open"))
        "numeric condition value"    | new View("Z4J Numeric Value " + UUID.randomUUID().toString().substring(0, 8)).addAllItem(new ViewCondition("status", "less_than", "closed")).addAnyItem(new ViewCondition("NEW", "greater_than", ViewConditionValue.of(24L)))
    }

    def "cannot create, update, or delete a view as #clientName"(String clientName, ViewClient client) {
        given:
        View view = new View("Unauthorized View")
                .addAllItem(new ViewCondition("status", "is", "open"))

        when: "attempting to create a view"
        client.createView(new ViewCreateRequest(view)).block()

        then:
        HttpClientException createError = thrown(HttpClientException)
        !(createError instanceof HttpClientResponseException) || createError.status == FORBIDDEN || createError.status == UNAUTHORIZED

        when: "attempting to update a view"
        client.updateView(1L, new ViewUpdateRequest(view)).block()

        then:
        HttpClientException updateError = thrown(HttpClientException)
        !(updateError instanceof HttpClientResponseException) || updateError.status == FORBIDDEN || updateError.status == UNAUTHORIZED

        when: "attempting to delete a view"
        client.deleteView(1L).block()

        then:
        HttpClientException deleteError = thrown(HttpClientException)
        !(deleteError instanceof HttpClientResponseException) || deleteError.status == FORBIDDEN || deleteError.status == UNAUTHORIZED

        where:
        clientName  | client
        "end user"  | userViewClient
        "bad email" | badEmailViewClient
        "bad url"   | badUrlViewClient
    }

    void safeDelete(Long id) {
        if (id == null) {
            return
        }
        try {
            adminViewClient.deleteView(id).block()
            createdViewIds.remove(id)
        } catch (HttpClientResponseException e) {
            if (e.status == HttpStatus.NOT_FOUND) {
                createdViewIds.remove(id)
            }
        } catch (Exception ignored) {
        }
    }
}
