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

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.json.JsonMapper
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.fixture.FixtureLoader
import lol.pbu.z4j.fixture.TicketFixtures
import lol.pbu.z4j.fixture.TicketItem
import lol.pbu.z4j.model.*
import spock.lang.Shared

class TicketClientSpec extends Z4jSpec {

    @Shared
    TicketClient ticketsAgentClient, ticketsUserClient, ticketBadEmailClient, ticketBadUrlClient

    @Shared
    JobStatusClient jobStatusClient

    @Shared
    JsonMapper jsonMapper

    @Shared
    List<Ticket> tickets

    @Shared
    List<Map> clientTestMatrix

    @Shared
    TicketFixtures ticketFixtures

    void setupSpec() {
        ticketBadEmailClient = badEmailCtx.getBean(TicketClient.class)
        ticketBadUrlClient = badUrlCtx.getBean(TicketClient.class)
        ticketsAgentClient = agentCtx.getBean(TicketClient.class)
        ticketsAdminClient = ticketsAdminClient ?: adminCtx.getBean(TicketClient.class)
        ticketsUserClient = userCtx.getBean(TicketClient.class)
        jobStatusClient = agentCtx.getBean(JobStatusClient.class)
        jsonMapper = adminCtx.getBean(JsonMapper.class)
        tickets = ticketsAgentClient.listTickets(null).block().getTickets()
        ticketFixtures = FixtureLoader.loadFixture("/fixtures/ticket_fixtures.yaml", TicketFixtures.class)
        clientTestMatrix = [[client: ticketsAgentClient, clientType: "Agent", shouldSucceed: true, expectedTitle: "should"],
                            [client: ticketsAdminClient, clientType: "Admin", shouldSucceed: true, expectedTitle: "should"],
                            [client: ticketBadEmailClient, clientType: "bad email", shouldSucceed: false, expectedTitle: "should not"],
                            [client: ticketBadUrlClient, clientType: "bad url", shouldSucceed: false, expectedTitle: "should not"],
                            [client: ticketsUserClient, clientType: "simple user", shouldSucceed: false, expectedTitle: "should not"]]
        try {
            DeletedTicketsResponse stale = ticketsAdminClient.listDeletedTickets().block()
            if (stale?.getDeletedTickets()) {
                List<Long> staleIds = stale.getDeletedTickets().collect { it.getId() }.findAll { it != null }
                if (!staleIds.isEmpty()) {
                    ticketsAdminClient.deleteMultipleTicketsPermanently(staleIds).block()
                }
            }
        } catch (Exception ignored) {
        }
    }

    def "calling listTickets() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.listTickets(null).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling listTickets() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.listTickets(null).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "calling showTicket() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.showTicket(tickets.get(0).getId()).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling showTicket() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.showTicket(tickets.get(0).getId()).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "calling listAuditsForTicket() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        TicketAuditsResponse response = client.listAuditsForTicket(tickets.get(0).getId()).block()

        then:
        noExceptionThrown()
        response != null
        response.audits != null
        !response.audits.isEmpty()
        response.audits.first().ticketId == tickets.get(0).getId()
        response.audits.first().events != null

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling listAuditsForTicket() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.listAuditsForTicket(tickets.get(0).getId()).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "Trying to create a ticket succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketItem sampleData = ticketFixtures.getTicketData().first()
        TicketComment ticketComment = new TicketComment().setBody(sampleData.getComment())
        TicketCreateInput createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(sampleData.getSubject())
        TicketCreateRequest createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        client.createTicket(createTicketRequest).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "Trying to create a ticket fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketItem sampleData = ticketFixtures.getTicketData().first()
        TicketComment ticketComment = new TicketComment().setBody(sampleData.getComment())
        TicketCreateInput createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(sampleData.getSubject())
        TicketCreateRequest createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        client.createTicket(createTicketRequest).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "calling updateTicket() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketItem sampleData = ticketFixtures.getTicketData().first()
        TicketUpdateInput ticketUpdateInput = new TicketUpdateInput()
                .setComment(new TicketComment().setBody(sampleData.getUpdateComment()))
        TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest().setTicket(ticketUpdateInput)


        when:
        client.updateTicket(tickets.first.getId(), ticketUpdateRequest).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling updateTicket() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketItem sampleData = ticketFixtures.getTicketData().first()
        TicketUpdateInput ticketUpdateInput = new TicketUpdateInput()
                .setComment(new TicketComment().setBody(sampleData.getUpdateComment()))
        TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest().setTicket(ticketUpdateInput)


        when:
        client.updateTicket(tickets.first.getId(), ticketUpdateRequest).block()


        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }


    def "calling countTickets() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.getTicketCount().block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling countTickets() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.getTicketCount().block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "can call listTicketFields when using a(n) #clientType client and #creator flag"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored, Boolean creator, Locale locale) {
        when:
        client.listTicketFields(locale.getLocaleAbbreviation(), creator).block()

        then:
        noExceptionThrown()

        where:
        [[client, clientType, ignored, alsoIgnored], creator, locale] << [
                clientTestMatrix.findAll { it.shouldSucceed || it.clientType == "simple user" }, [true, false, null], accountLocales
        ].combinations()
    }

    def "calling listTicketFields when using a(n) #clientType client and #creator flag fails"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored, Boolean creator, Locale locale) {
        when:
        client.listTicketFields(locale.getLocaleAbbreviation(), creator).block()

        then:
        thrown(HttpClientException)

        where:
        [[client, clientType, ignored, alsoIgnored], creator, locale] << [
                clientTestMatrix.findAll { !it.shouldSucceed && it.clientType != "simple user"}, [true, false, null], accountLocales
        ].combinations()
    }

    def "can paginate listTicketFields with cursor pagination"() {
        given:
        LocaleAbbreviation locale = accountLocales.first().getLocaleAbbreviation()
        ensureMoreThan100TicketFields(locale)

        when:
        TicketFieldsResponse page = ticketsAdminClient.listTicketFields(locale, null, null, 100).block()
        List<TicketField> allFields = []
        while (page != null) {
            if (page.getTicketFields() != null) {
                allFields.addAll(page.getTicketFields())
            }
            if (!(page.getMeta()?.getHasMore()) || page.getMeta()?.getAfterCursor() == null) {
                break
            }
            page = ticketsAdminClient.listTicketFields(locale, null, page.getMeta().getAfterCursor(), 100).block()
        }

        then:
        allFields.size() > 100
        allFields*.id.toSet().size() == allFields.size()
    }

    private void ensureMoreThan100TicketFields(LocaleAbbreviation locale) {
        TicketFieldsResponse firstPage = ticketsAdminClient.listTicketFields(locale, null, null, 100).block()
        if (firstPage.getMeta()?.getHasMore()) {
            return
        }

        int needed = Math.max(0, 101 - (firstPage.getTicketFields()?.size() ?: 0))
        (1..needed).each {
            String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
            TicketField field = new TicketField()
                    .setTitle("z4j-cursor-fixture-${entropy}")
                    .setType(TicketFieldTypeEnum.TEXT.getValue())
            ticketsAdminClient.createTicketField(new TicketFieldCreateRequest(field)).block()
        }

        int maxAttempts = 10
        for (int i = 0; i < maxAttempts; i++) {
            TicketFieldsResponse refreshed = ticketsAdminClient.listTicketFields(locale, null, null, 100).block()
            if (refreshed.getMeta()?.getHasMore()) {
                return
            }
            sleep(2000)
        }
        throw new IllegalStateException("Ticket field fixture setup did not become visible in time. Please rerun or pre-seed your sandbox.")
    }

    def "can show and delete a ticket field as admin"() {
        given: "a created custom ticket field"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        TicketField field = new TicketField("z4j-field-${entropy}", TicketFieldTypeEnum.TEXT.getValue())
        TicketFieldResponse created = ticketsAdminClient.createTicketField(new TicketFieldCreateRequest(field)).block()
        Long fieldId = created.getTicketField().getId()

        when: "fetching the ticket field by id"
        TicketFieldResponse shown = ticketsAdminClient.showTicketField(fieldId).block()

        then: "the ticket field matches"
        noExceptionThrown()
        shown != null
        shown.getTicketField() != null
        shown.getTicketField().getId() == fieldId

        when: "deleting the ticket field"
        ticketsAdminClient.deleteTicketField(fieldId).block()

        then: "deletion succeeds without exception"
        noExceptionThrown()
    }

    def "fetching a ticket queries all custom fields preserving unset fields as Raw without null elements"() {
        given: "a custom ticket field created in the sandbox"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String customVal = "z4j-val-${entropy}"
        TicketField field = new TicketField("z4j-cf-${entropy}", TicketFieldTypeEnum.TEXT.getValue())
        TicketFieldResponse createdField = ticketsAdminClient.createTicketField(new TicketFieldCreateRequest(field)).block()
        Long fieldId = createdField.getTicketField().getId()

        and: "a ticket created with this custom field populated"
        TicketComment comment = new TicketComment().setBody("Testing custom fields ${entropy}")
        TicketCreateInput ticketInput = new TicketCreateInput(comment)
                .setRawSubject("Ticket with custom field ${entropy}")
        ticketInput.addCustomFieldsItem(new TicketCustomField.Text(fieldId, customVal))
        TicketResponse createdTicket = ticketsAdminClient.createTicket(new TicketCreateRequest(ticketInput)).block()
        Long ticketId = createdTicket.getTicket().getId()

        when: "fetching the ticket via showTicket"
        TicketResponse fetched = ticketsAgentClient.showTicket(ticketId).block()

        then: "the ticket is returned and custom fields are present"
        noExceptionThrown()
        fetched != null
        fetched.getTicket() != null
        List<TicketCustomField> customFields = fetched.getTicket().getCustomFields()
        customFields != null
        !customFields.isEmpty()

        and: "the custom fields list contains NO null elements"
        !customFields.any { it == null }

        and: "all custom field entries have valid IDs"
        customFields.every { it.id() != null }

        and: "the populated custom field is deserialized as a Text record with expected value"
        TicketCustomField populatedField = customFields.find { it.id() == fieldId }
        populatedField != null
        populatedField instanceof TicketCustomField.Text
        ((TicketCustomField.Text) populatedField).value() == customVal

        and: "unpopulated custom fields with null values are preserved as Raw records"
        List<TicketCustomField> nullValuedFields = customFields.findAll { it.value() == null }
        !nullValuedFields.isEmpty()
        nullValuedFields.every { it instanceof TicketCustomField.Raw }

        cleanup: "delete the test custom field from the sandbox"
        try {
            if (fieldId != null) {
                ticketsAdminClient.deleteTicketField(fieldId).block()
            }
        } catch (Exception ignored) {
        }
    }

    def "can update many tickets by ids as agent"() {
        given: "an existing ticket in the sandbox"
        Long ticketId = tickets.first().getId()
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        TicketUpdateInput updateInput = new TicketUpdateInput()
                .setComment(new TicketComment().setBody("Bulk update test ${entropy}").setIsPublic(false))
        TicketUpdateRequest updateRequest = new TicketUpdateRequest().setTicket(updateInput)

        when: "calling updateManyTickets"
        JobStatusResponse response = ticketsAgentClient.updateManyTickets(ticketId.toString(), updateRequest).block()

        then: "job status response is returned"
        noExceptionThrown()
        response != null
        response.getJobStatus() != null
        response.getJobStatus().getId() != null
        response.getJobStatus().getStatus() != null
    }

    def "can show multiple tickets by comma-separated string IDs as #clientType"(TicketClient client, String clientType) {
        given: "two ticket IDs from the existing ticket list"
        String ids = tickets.take(2)*.id.join(",")

        when: "calling showMultipleTickets"
        TicketsResponse response = client.showMultipleTickets(ids).block()

        then: "tickets are returned"
        noExceptionThrown()
        response != null
        response.getTickets() != null
        !response.getTickets().isEmpty()
        response.getTickets()*.id.contains(tickets.first().getId())

        where:
        [client, clientType] << [[ticketsAgentClient, "Agent"], [ticketsAdminClient, "Admin"]]
    }

    def "can show multiple tickets by list of IDs as agent"() {
        given: "ticket IDs as a list"
        List<Long> ids = tickets.take(2)*.id

        when: "calling showMultipleTickets with a list"
        TicketsResponse response = ticketsAgentClient.showMultipleTickets(ids).block()

        then: "tickets are returned"
        noExceptionThrown()
        response != null
        response.getTickets() != null
        !response.getTickets().isEmpty()
        response.getTickets()*.id.contains(tickets.first().getId())
    }

    def "calling showMultipleTickets fails when used with a(n) #clientType client"(TicketClient client, String clientType) {
        when: "calling showMultipleTickets with unauthorized or bad client"
        client.showMultipleTickets(tickets.take(2)*.id.join(",")).block()

        then: "an HttpClientException is thrown"
        thrown(HttpClientException)

        where:
        [client, clientType] << [[ticketsUserClient, "simple user"], [ticketBadEmailClient, "bad email"], [ticketBadUrlClient, "bad url"]]
    }

    def "can get ticket related information as agent"() {
        given: "an existing ticket ID"
        Long ticketId = tickets.first().getId()

        when: "fetching ticket related information"
        TicketRelatedResponse response = ticketsAgentClient.getTicketRelatedInformation(ticketId).block()

        then: "related information is returned"
        noExceptionThrown()
        response != null
        response.getTicketRelated() != null
        response.getTicketRelated().getUrl() != null
        response.getTicketRelated().getUrl().contains(ticketId.toString())
    }

    def "calling getTicketRelatedInformation fails when used with bad credentials"() {
        when: "calling with bad credentials"
        ticketBadEmailClient.getTicketRelatedInformation(tickets.first().getId()).block()

        then: "an HttpClientException is thrown"
        thrown(HttpClientException)
    }

    def "can list collaborators for a ticket as agent"() {
        given: "an existing ticket ID"
        Long ticketId = tickets.first().getId()

        when: "listing collaborators"
        UsersResponse response = ticketsAgentClient.listCollaboratorsForTicket(ticketId).block()

        then: "users response is returned"
        noExceptionThrown()
        response != null
        response.getUsers() != null
    }

    def "calling listCollaboratorsForTicket fails when used with bad credentials"() {
        when: "calling with bad credentials"
        ticketBadEmailClient.listCollaboratorsForTicket(tickets.first().getId()).block()

        then: "an HttpClientException is thrown"
        thrown(HttpClientException)
    }

    def "can list followers for a ticket as agent"() {
        given: "an existing ticket ID"
        Long ticketId = tickets.first().getId()

        when: "listing followers"
        UsersResponse response = ticketsAgentClient.listFollowersForTicket(ticketId).block()

        then: "users response is returned"
        noExceptionThrown()
        response != null
        response.getUsers() != null
    }

    def "calling listFollowersForTicket fails when used with bad credentials"() {
        when: "calling with bad credentials"
        ticketBadEmailClient.listFollowersForTicket(tickets.first().getId()).block()

        then: "an HttpClientException is thrown"
        thrown(HttpClientException)
    }

    def "can list email CCs for a ticket as agent"() {
        given: "an existing ticket ID"
        Long ticketId = tickets.first().getId()

        when: "listing email CCs"
        UsersResponse response = ticketsAgentClient.listEmailCcsForTicket(ticketId).block()

        then: "users response is returned"
        noExceptionThrown()
        response != null
        response.getUsers() != null
    }

    def "calling listEmailCcsForTicket fails when used with bad credentials"() {
        when: "calling with bad credentials"
        ticketBadEmailClient.listEmailCcsForTicket(tickets.first().getId()).block()

        then: "an HttpClientException is thrown"
        thrown(HttpClientException)
    }

    def "can list ticket incidents as agent"() {
        given: "an existing ticket ID"
        Long ticketId = tickets.first().getId()

        when: "listing ticket incidents"
        TicketsResponse response = ticketsAgentClient.listTicketIncidents(ticketId).block()

        then: "tickets response is returned"
        noExceptionThrown()
        response != null
        response.getTickets() != null
    }

    def "calling listTicketIncidents fails when used with bad credentials"() {
        when: "calling with bad credentials"
        ticketBadEmailClient.listTicketIncidents(tickets.first().getId()).block()

        then: "an HttpClientException is thrown"
        thrown(HttpClientException)
    }

    def "can autocomplete problems as agent"() {
        when: "calling autocomplete problems"
        TicketsResponse response = ticketsAgentClient.autocompleteProblems("test").block()

        then: "tickets response is returned"
        noExceptionThrown()
        response != null
        response.getTickets() != null
    }

    def "calling autocompleteProblems fails when used with bad credentials"() {
        when: "calling with bad credentials"
        ticketBadEmailClient.autocompleteProblems("test").block()

        then: "an HttpClientException is thrown"
        thrown(HttpClientException)
    }

    def "can create many tickets as agent"() {
        given: "two ticket create inputs with unique entropy"
        String entropy1 = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String entropy2 = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        TicketCreateInput input1 = new TicketCreateInput(new TicketComment().setBody("Body 1 ${entropy1}"))
                .setRawSubject("CreateMany 1 ${entropy1}")
        TicketCreateInput input2 = new TicketCreateInput(new TicketComment().setBody("Body 2 ${entropy2}"))
                .setRawSubject("CreateMany 2 ${entropy2}")
        String jobId = null

        when: "calling createManyTickets"
        JobStatusResponse response = ticketsAgentClient.createManyTickets([input1, input2]).block()
        jobId = response?.getJobStatus()?.getId()

        then: "job status is returned"
        noExceptionThrown()
        response != null
        response.getJobStatus() != null
        response.getJobStatus().getId() != null

        cleanup: "wait for job completion and delete created tickets"
        try {
            if (jobId != null) {
                JobStatus status = null
                for (int i = 0; i < 10; i++) {
                    sleep(1000)
                    status = jobStatusClient.showJobStatus(jobId).block()?.getJobStatus()
                    if (status?.getStatus() == "completed" || status?.getStatus() == "failed") {
                        break
                    }
                }
                List<Long> createdIds = status?.getResults()?.collect { (it.id as Number)?.longValue() }?.findAll { it != null }
                if (createdIds) {
                    ticketsAdminClient.bulkDeleteTickets(createdIds).block()
                    sleep(2000)
                    ticketsAdminClient.deleteMultipleTicketsPermanently(createdIds).block()
                }
            }
        } catch (Exception ignored) {
        }
    }

    def "can delete and restore a ticket as admin"() {
        given: "a newly created ticket in the sandbox"
        TicketResponse created = createTicketForTest()
        Long ticketId = created.getTicket().getId()

        when: "deleting the ticket"
        ticketsAdminClient.deleteTicket(ticketId).block()
        sleep(2000)

        then: "deletion succeeds without error"
        noExceptionThrown()

        and: "the deleted tickets list can be retrieved"
        DeletedTicketsResponse deletedResponse = ticketsAdminClient.listDeletedTickets().block()
        deletedResponse != null
        deletedResponse.getDeletedTickets() != null

        when: "restoring the deleted ticket"
        ticketsAdminClient.restoreDeletedTicket(ticketId).block()

        then: "restoration succeeds"
        noExceptionThrown()

        and: "the ticket can be shown again"
        TicketResponse restored = ticketsAdminClient.showTicket(ticketId).block()
        restored != null
        restored.getTicket() != null
        restored.getTicket().getId() == ticketId

        cleanup: "defensively delete and permanently remove the test ticket"
        try {
            if (ticketId != null) {
                ticketsAdminClient.deleteTicket(ticketId).block()
                ticketsAdminClient.deleteTicketPermanently(ticketId).block()
            }
        } catch (Exception ignored) {
        }
    }

    def "can bulk delete, restore in bulk, and permanently delete tickets in bulk as admin"() {
        given: "two newly created tickets"
        TicketResponse t1 = createTicketForTest()
        TicketResponse t2 = createTicketForTest()
        Long id1 = t1.getTicket().getId()
        Long id2 = t2.getTicket().getId()
        List<Long> ids = [id1, id2]

        when: "bulk deleting tickets using List<Long>"
        JobStatusResponse deleteJob = ticketsAdminClient.bulkDeleteTickets(ids).block()

        then: "bulk delete job status is returned"
        noExceptionThrown()
        deleteJob != null
        deleteJob.getJobStatus() != null
        deleteJob.getJobStatus().getId() != null

        when: "restoring deleted tickets in bulk using comma-separated string"
        sleep(2000)
        JobStatusResponse restoreJob = ticketsAdminClient.restoreDeletedTicketsInBulk(ids.join(",")).block()

        then: "bulk restore succeeds without error"
        noExceptionThrown()
        restoreJob != null
        restoreJob.getJobStatus() != null

        when: "soft deleting tickets again then permanently deleting them in bulk"
        sleep(2000)
        ticketsAdminClient.bulkDeleteTickets(ids).block()
        sleep(2000)
        JobStatusResponse permDeleteJob = ticketsAdminClient.deleteMultipleTicketsPermanently(ids).block()

        then: "permanent delete job status is returned"
        noExceptionThrown()
        permDeleteJob != null
        permDeleteJob.getJobStatus() != null
        permDeleteJob.getJobStatus().getId() != null

        cleanup: "defensive permanent deletion if needed"
        try {
            ticketsAdminClient.deleteMultipleTicketsPermanently(ids).block()
        } catch (Exception ignored) {
        }
    }

    def "can permanently delete a single soft-deleted ticket as admin"() {
        given: "a soft-deleted ticket"
        TicketResponse created = createTicketForTest()
        Long ticketId = created.getTicket().getId()
        ticketsAdminClient.deleteTicket(ticketId).block()

        when: "permanently deleting the ticket"
        JobStatusResponse response = ticketsAdminClient.deleteTicketPermanently(ticketId).block()

        then: "job status response is returned"
        noExceptionThrown()
        response != null
        response.getJobStatus() != null
        response.getJobStatus().getId() != null
    }

    def "can mark ticket as spam and bulk mark tickets as spam as admin"() {
        given: "two tickets created with disposable end user requesters"
        Long user1Id = createDisposableEndUser()
        Long user2Id = createDisposableEndUser()
        TicketComment comment1 = new TicketComment().setBody("Spam ticket body 1")
        TicketCreateInput input1 = new TicketCreateInput(comment1)
                .setRawSubject("Spam ticket 1 " + UUID.randomUUID().toString())
                .setRequesterId(user1Id)
        TicketComment comment2 = new TicketComment().setBody("Spam ticket body 2")
        TicketCreateInput input2 = new TicketCreateInput(comment2)
                .setRawSubject("Spam ticket 2 " + UUID.randomUUID().toString())
                .setRequesterId(user2Id)
        TicketResponse t1 = ticketsAdminClient.createTicket(new TicketCreateRequest(input1)).block()
        TicketResponse t2 = ticketsAdminClient.createTicket(new TicketCreateRequest(input2)).block()
        Long id1 = t1.getTicket().getId()
        Long id2 = t2.getTicket().getId()

        when: "marking a single ticket as spam"
        ticketsAdminClient.markTicketAsSpam(id1).block()

        then: "marking as spam succeeds"
        noExceptionThrown()

        when: "bulk marking tickets as spam"
        JobStatusResponse bulkSpamJob = ticketsAdminClient.bulkMarkTicketsAsSpam([id2]).block()

        then: "bulk mark spam job status is returned"
        noExceptionThrown()
        bulkSpamJob != null
        bulkSpamJob.getJobStatus() != null
        bulkSpamJob.getJobStatus().getId() != null

        cleanup: "permanently delete tickets and disposable users"
        try {
            ticketsAdminClient.deleteMultipleTicketsPermanently([id1, id2]).block()
        } catch (Exception ignored) {
        }
        deleteUserSafely(user1Id)
        deleteUserSafely(user2Id)
    }

    def "can merge tickets as admin"() {
        given: "target and source tickets created for merge"
        TicketResponse target = createTicketForTest()
        TicketResponse source = createTicketForTest()
        Long targetId = target.getTicket().getId()
        Long sourceId = source.getTicket().getId()
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        MergeTicketsRequest mergeRequest = new MergeTicketsRequest([sourceId])
                .setTargetComment("Target merge comment ${entropy}")
                .setSourceComment("Source merge comment ${entropy}")

        when: "merging tickets"
        JobStatusResponse job = ticketsAdminClient.mergeTickets(targetId, mergeRequest).block()

        then: "job status response is returned"
        noExceptionThrown()
        job != null
        job.getJobStatus() != null
        job.getJobStatus().getId() != null

        cleanup: "delete the merged tickets"
        try {
            ticketsAdminClient.bulkDeleteTickets([targetId, sourceId]).block()
            sleep(2000)
            ticketsAdminClient.deleteMultipleTicketsPermanently([targetId, sourceId]).block()
        } catch (Exception ignored) {
        }
    }

    private Long createDisposableEndUser() {
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String email = "disposable-${entropy}@example.com"
        HttpClient httpClient = adminCtx.getBean(HttpClient.class)
        String body = "{\"user\":{\"name\":\"Disposable ${entropy}\",\"email\":\"${email}\",\"role\":\"end-user\"}}"
        HttpRequest<?> req = HttpRequest.POST("${System.getenv('Z4J_URL')}/api/v2/users.json", body)
        HttpResponse<String> res = httpClient.toBlocking().exchange(req, String.class)
        Map responseMap = jsonMapper.readValue(res.body(), Map.class)
        return (responseMap.get("user")["id"] as Number).longValue()
    }

    private void deleteUserSafely(Long userId) {
        if (userId == null) return
        try {
            HttpClient httpClient = adminCtx.getBean(HttpClient.class)
            HttpRequest<?> req = HttpRequest.DELETE("${System.getenv('Z4J_URL')}/api/v2/users/${userId}.json")
            httpClient.toBlocking().exchange(req, String.class)
        } catch (Exception ignored) {
        }
    }
}
