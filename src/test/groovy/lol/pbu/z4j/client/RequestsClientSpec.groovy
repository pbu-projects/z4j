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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class RequestsClientSpec extends Z4jSpec {

    @Shared
    RequestsClient adminRequestsClient, agentRequestsClient, userRequestsClient,
                   badTokenRequestsClient, badUrlRequestsClient

    def setupSpec() {
        adminRequestsClient = adminCtx.getBean(RequestsClient.class)
        agentRequestsClient = agentCtx.getBean(RequestsClient.class)
        userRequestsClient = userCtx.getBean(RequestsClient.class)
        badTokenRequestsClient = badTokenCtx.getBean(RequestsClient.class)
        badUrlRequestsClient = badUrlCtx.getBean(RequestsClient.class)
    }

    @Unroll
    def "can list requests for all roles as #userType"(
            RequestsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting requests list"
        client.listRequests(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRequestsClient, "admin"],
                [agentRequestsClient, "agent"],
                [userRequestsClient, "end user"]
        ]
    }

    @Unroll
    def "can list open requests as an #userType"(
            RequestsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting open requests"
        client.listOpenRequests(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRequestsClient, "admin"],
                [agentRequestsClient, "agent"],
                [userRequestsClient, "end user"]
        ]
    }

    @Unroll
    def "can list solved requests as an #userType"(
            RequestsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting solved requests"
        client.listSolvedRequests(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRequestsClient, "admin"],
                [agentRequestsClient, "agent"],
                [userRequestsClient, "end user"]
        ]
    }

    @Unroll
    def "can list CCD requests as an #userType"(
            RequestsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting CCD requests"
        client.listCCDRequests(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRequestsClient, "admin"],
                [agentRequestsClient, "agent"],
                [userRequestsClient, "end user"]
        ]
    }

    @Unroll
    def "can search requests as an #userType with query=#query"(
            RequestsClient client, String userType, String query) {
        given: "an authenticated client for #userType"

        when: "searching requests by query"
        client.searchRequests(query).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], query] << [
                [[adminRequestsClient, "admin"], [agentRequestsClient, "agent"], [userRequestsClient, "end user"]],
                ["help"]
        ].combinations()
    }

    @Unroll
    def "calling requests client with #description throws HttpClientException"(
            String description, RequestsClient client) {
        when: "requesting requests with invalid client configuration"
        client.listRequests(null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenRequestsClient
        "unreachable url" | badUrlRequestsClient
    }
}
