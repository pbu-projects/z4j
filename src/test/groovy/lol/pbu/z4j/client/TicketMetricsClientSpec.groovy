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
import lol.pbu.z4j.model.TicketMetricsResponse
import lol.pbu.z4j.model.TicketsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("ticketing")
class TicketMetricsClientSpec extends Z4jSpec {

    @Shared
    TicketMetricsClient adminTicketMetricsClient, agentTicketMetricsClient, userTicketMetricsClient,
                        badTokenTicketMetricsClient, badUrlTicketMetricsClient

    @Shared
    Long testTicketId

    @Shared
    String testMetricId

    def setupSpec() {
        adminTicketMetricsClient = adminCtx.getBean(TicketMetricsClient.class)
        agentTicketMetricsClient = agentCtx.getBean(TicketMetricsClient.class)
        userTicketMetricsClient = userCtx.getBean(TicketMetricsClient.class)
        badTokenTicketMetricsClient = badTokenCtx.getBean(TicketMetricsClient.class)
        badUrlTicketMetricsClient = badUrlCtx.getBean(TicketMetricsClient.class)

        TicketClient ticketClient = adminCtx.getBean(TicketClient.class)
        TicketsResponse response = ticketClient.listTickets(null).block()
        if (response?.tickets && !response.tickets.isEmpty()) {
            testTicketId = response.tickets.first().id
        }

        TicketMetricsResponse metrics = adminTicketMetricsClient.listTicketMetrics().block()
        if (metrics?.ticketMetrics && !metrics.ticketMetrics.isEmpty()) {
            testMetricId = metrics.ticketMetrics.first().id?.toString()
        }
    }

    @Unroll
    def "can list ticket metrics as an #userType"(TicketMetricsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting all ticket metrics"
        client.listTicketMetrics().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketMetricsClient, "admin"],
                [agentTicketMetricsClient, "agent"]
        ]
    }

    @Unroll
    def "can show ticket metrics by ticket as an #userType"(TicketMetricsClient client, String userType) {
        given: "an authenticated client for #userType and test ticket ID"

        when: "requesting metrics for specific ticket"
        if (testTicketId != null) {
            client.showTicketMetricsByTicket(testTicketId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketMetricsClient, "admin"],
                [agentTicketMetricsClient, "agent"]
        ]
    }

    @Unroll
    def "can show ticket metrics by metric ID as an #userType"(TicketMetricsClient client, String userType) {
        given: "an authenticated client for #userType and test metric ID"

        when: "requesting metric by ID"
        if (testMetricId != null) {
            client.showTicketMetrics(testMetricId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketMetricsClient, "admin"],
                [agentTicketMetricsClient, "agent"]
        ]
    }

    def "end user cannot list ticket metrics"() {
        given: "an end user client"

        when: "requesting ticket metrics as an end user"
        userTicketMetricsClient.listTicketMetrics().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ticket metrics client with #description throws HttpClientException"(
            String description, TicketMetricsClient client) {
        when: "requesting ticket metrics with invalid client configuration"
        client.listTicketMetrics().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTicketMetricsClient
        "unreachable url" | badUrlTicketMetricsClient
    }
}
