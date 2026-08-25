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
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TicketMetricEventsClientSpec extends Z4jSpec {

    @Shared
    TicketMetricEventsClient adminMetricEventsClient, agentMetricEventsClient, userMetricEventsClient,
                             badTokenMetricEventsClient, badUrlMetricEventsClient

    def setupSpec() {
        adminMetricEventsClient = adminCtx.getBean(TicketMetricEventsClient.class)
        agentMetricEventsClient = agentCtx.getBean(TicketMetricEventsClient.class)
        userMetricEventsClient = userCtx.getBean(TicketMetricEventsClient.class)
        badTokenMetricEventsClient = badTokenCtx.getBean(TicketMetricEventsClient.class)
        badUrlMetricEventsClient = badUrlCtx.getBean(TicketMetricEventsClient.class)
    }

    def "can list ticket metric events as an admin"() {
        given: "an authenticated admin client and recent start timestamp"
        int oneWeekAgo = (System.currentTimeMillis() / 1000L - 604800) as int

        when: "requesting ticket metric events list"
        adminMetricEventsClient.listTicketMetricEvents(oneWeekAgo, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list ticket metric events"(TicketMetricEventsClient client, String userType) {
        given: "an unauthorized client for #userType"
        int oneWeekAgo = (System.currentTimeMillis() / 1000L - 604800) as int

        when: "requesting ticket metric events"
        client.listTicketMetricEvents(oneWeekAgo, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentMetricEventsClient, "agent"],
                [userMetricEventsClient, "end user"]
        ]
    }

    @Unroll
    def "calling ticket metric events client with #description throws HttpClientException"(
            String description, TicketMetricEventsClient client) {
        given:
        int oneWeekAgo = (System.currentTimeMillis() / 1000L - 604800) as int

        when: "requesting ticket metric events with invalid client configuration"
        client.listTicketMetricEvents(oneWeekAgo, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenMetricEventsClient
        "unreachable url" | badUrlMetricEventsClient
    }
}
