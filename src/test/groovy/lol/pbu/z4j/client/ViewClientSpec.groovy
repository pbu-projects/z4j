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

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ViewsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class ViewClientSpec extends Z4jSpec {

    @Shared
    ViewsClient adminViewsClient, agentViewsClient, userViewsClient,
                badTokenViewsClient, badUrlViewsClient

    @Shared
    Long existingViewId

    def setupSpec() {
        adminViewsClient = adminCtx.getBean(ViewsClient.class)
        agentViewsClient = agentCtx.getBean(ViewsClient.class)
        userViewsClient = userCtx.getBean(ViewsClient.class)
        badTokenViewsClient = badTokenCtx.getBean(ViewsClient.class)
        badUrlViewsClient = badUrlCtx.getBean(ViewsClient.class)

        ViewsResponse activeViews = adminViewsClient.listActiveViews(null, null, null, null).block()
        if (activeViews?.views && !activeViews.views.isEmpty()) {
            existingViewId = activeViews.views.first().id
        }
    }

    @Unroll
    def "can count views as an #userType"(ViewsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting view count"
        client.countViews().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminViewsClient, "admin"],
                [agentViewsClient, "agent"]
        ]
    }

    @Unroll
    def "can list active views as an #userType"(ViewsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting active views"
        client.listActiveViews(null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminViewsClient, "admin"],
                [agentViewsClient, "agent"]
        ]
    }

    @Unroll
    def "can list compact views as an #userType"(ViewsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting compact views list"
        client.listCompactViews().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminViewsClient, "admin"],
                [agentViewsClient, "agent"]
        ]
    }

    @Unroll
    def "can list views as an #userType"(ViewsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting all views"
        client.listViews(null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminViewsClient, "admin"],
                [agentViewsClient, "agent"]
        ]
    }

    @Unroll
    def "can execute view and query counts as an #userType"(ViewsClient client, String userType) {
        given: "an authenticated client for #userType and existing view ID"

        when: "requesting view count and execution"
        if (existingViewId != null) {
            client.getViewCount(existingViewId).block()
            client.executeView(existingViewId, null, null).block()
            client.listTicketsFromView(existingViewId, null, null).block()
        }

        then: "responses deserialize successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminViewsClient, "admin"],
                [agentViewsClient, "agent"]
        ]
    }

    def "end user cannot list or count views"() {
        given: "an end user client"

        when: "requesting view count as an end user"
        userViewsClient.countViews().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling views client with #description throws HttpClientException"(
            String description, ViewsClient client) {
        when: "requesting views with invalid client configuration"
        client.countViews().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenViewsClient
        "unreachable url" | badUrlViewsClient
    }
}
