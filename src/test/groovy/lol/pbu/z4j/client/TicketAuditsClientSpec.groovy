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
import org.junit.jupiter.api.Tag

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.TicketAuditsResponseNoneCursor
import lol.pbu.z4j.model.TicketsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("ticketing")
class TicketAuditsClientSpec extends Z4jSpec {

    @Shared
    TicketAuditsClient adminTicketAuditsClient, agentTicketAuditsClient, userTicketAuditsClient,
                       badTokenTicketAuditsClient, badUrlTicketAuditsClient

    @Shared
    Long testTicketId

    @Shared
    Long testAuditId

    def setupSpec() {
        adminTicketAuditsClient = adminCtx.getBean(TicketAuditsClient.class)
        agentTicketAuditsClient = agentCtx.getBean(TicketAuditsClient.class)
        userTicketAuditsClient = userCtx.getBean(TicketAuditsClient.class)
        badTokenTicketAuditsClient = badTokenCtx.getBean(TicketAuditsClient.class)
        badUrlTicketAuditsClient = badUrlCtx.getBean(TicketAuditsClient.class)

        TicketClient ticketClient = adminCtx.getBean(TicketClient.class)
        TicketsResponse response = ticketClient.listTickets(null).block()
        if (response?.tickets && !response.tickets.isEmpty()) {
            testTicketId = response.tickets.first().id
            TicketAuditsResponseNoneCursor audits = adminTicketAuditsClient.listAuditsForTicket(testTicketId).block()
            if (audits?.audits && !audits.audits.isEmpty()) {
                testAuditId = audits.audits.first().id
            }
        }
    }

    @Unroll
    def "can count audits for a ticket as an #userType"(TicketAuditsClient client, String userType) {
        given: "an authenticated client for #userType and test ticket ID"

        when: "requesting audit count for ticket"
        if (testTicketId != null) {
            client.countAuditsForTicket(testTicketId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketAuditsClient, "admin"],
                [agentTicketAuditsClient, "agent"]
        ]
    }

    @Unroll
    def "can list audits for a ticket as an #userType"(TicketAuditsClient client, String userType) {
        given: "an authenticated client for #userType and test ticket ID"

        when: "requesting audits for ticket"
        if (testTicketId != null) {
            client.listAuditsForTicket(testTicketId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketAuditsClient, "admin"],
                [agentTicketAuditsClient, "agent"]
        ]
    }

    @Unroll
    def "can show audit details as an #userType"(TicketAuditsClient client, String userType) {
        given: "an authenticated client for #userType, test ticket ID, and test audit ID"

        when: "requesting individual audit by ID"
        if (testTicketId != null && testAuditId != null) {
            client.showTicketAudit(testTicketId, testAuditId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketAuditsClient, "admin"],
                [agentTicketAuditsClient, "agent"]
        ]
    }

    def "can list all ticket audits as an admin"() {
        given: "an admin client"

        when: "requesting global ticket audits"
        adminTicketAuditsClient.listTicketAudits(null, null, 10).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "end user cannot list or count ticket audits"() {
        given: "an end user client and test ticket ID"

        when: "requesting audit count as an end user"
        if (testTicketId != null) {
            userTicketAuditsClient.countAuditsForTicket(testTicketId).block()
        }

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ticket audits client with #description throws HttpClientException"(
            String description, TicketAuditsClient client) {
        when: "requesting audits with invalid client configuration"
        if (testTicketId != null) {
            client.countAuditsForTicket(testTicketId).block()
        }

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTicketAuditsClient
        "unreachable url" | badUrlTicketAuditsClient
    }
}
