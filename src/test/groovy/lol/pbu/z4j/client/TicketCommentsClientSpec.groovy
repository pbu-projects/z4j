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
import lol.pbu.z4j.model.TicketsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TicketCommentsClientSpec extends Z4jSpec {

    @Shared
    TicketCommentsClient adminTicketCommentsClient, agentTicketCommentsClient, userTicketCommentsClient,
                         badTokenTicketCommentsClient, badUrlTicketCommentsClient

    @Shared
    Integer testTicketId

    def setupSpec() {
        adminTicketCommentsClient = adminCtx.getBean(TicketCommentsClient.class)
        agentTicketCommentsClient = agentCtx.getBean(TicketCommentsClient.class)
        userTicketCommentsClient = userCtx.getBean(TicketCommentsClient.class)
        badTokenTicketCommentsClient = badTokenCtx.getBean(TicketCommentsClient.class)
        badUrlTicketCommentsClient = badUrlCtx.getBean(TicketCommentsClient.class)

        TicketClient ticketClient = adminCtx.getBean(TicketClient.class)
        TicketsResponse response = ticketClient.listTickets(null).block()
        if (response?.tickets && !response.tickets.isEmpty()) {
            testTicketId = response.tickets.first().id
        }
    }

    @Unroll
    def "can count ticket comments as an #userType"(TicketCommentsClient client, String userType) {
        given: "an authenticated client for #userType and test ticket ID"

        when: "requesting ticket comments count"
        if (testTicketId != null) {
            client.countTicketComments(testTicketId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketCommentsClient, "admin"],
                [agentTicketCommentsClient, "agent"]
        ]
    }

    @Unroll
    def "can list ticket comments as an #userType with includeInlineImages=#includeInlineImages"(
            TicketCommentsClient client, String userType, Boolean includeInlineImages) {
        given: "an authenticated client for #userType and test ticket ID"

        when: "requesting ticket comments list"
        if (testTicketId != null) {
            client.listTicketComments(testTicketId, includeInlineImages, null).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], includeInlineImages] << [
                [[adminTicketCommentsClient, "admin"], [agentTicketCommentsClient, "agent"]],
                [null, true, false]
        ].combinations()
    }

    def "end user cannot list ticket comments"() {
        given: "an end user client and test ticket ID"

        when: "requesting ticket comments count as an end user"
        if (testTicketId != null) {
            userTicketCommentsClient.countTicketComments(testTicketId).block()
        }

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ticket comments client with #description throws HttpClientException"(
            String description, TicketCommentsClient client) {
        when: "requesting ticket comments with invalid client configuration"
        if (testTicketId != null) {
            client.countTicketComments(testTicketId).block()
        }

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTicketCommentsClient
        "unreachable url" | badUrlTicketCommentsClient
    }
}
