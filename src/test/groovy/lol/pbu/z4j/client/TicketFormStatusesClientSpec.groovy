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
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("ticketing")
class TicketFormStatusesClientSpec extends Z4jSpec {

    @Shared
    TicketFormStatusesClient adminFormStatusesClient, agentFormStatusesClient, userFormStatusesClient,
                             badTokenFormStatusesClient, badUrlFormStatusesClient

    def setupSpec() {
        adminFormStatusesClient = adminCtx.getBean(TicketFormStatusesClient.class)
        agentFormStatusesClient = agentCtx.getBean(TicketFormStatusesClient.class)
        userFormStatusesClient = userCtx.getBean(TicketFormStatusesClient.class)
        badTokenFormStatusesClient = badTokenCtx.getBean(TicketFormStatusesClient.class)
        badUrlFormStatusesClient = badUrlCtx.getBean(TicketFormStatusesClient.class)
    }

    @Unroll
    def "can list ticket form statuses as an #userType"(
            TicketFormStatusesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ticket form statuses list"
        client.listTicketFormStatuses().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminFormStatusesClient, "admin"],
                [agentFormStatusesClient, "agent"]
        ]
    }

    def "end user cannot list ticket form statuses"() {
        given: "an end user client"

        when: "requesting ticket form statuses as an end user"
        userFormStatusesClient.listTicketFormStatuses().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ticket form statuses client with #description throws HttpClientException"(
            String description, TicketFormStatusesClient client) {
        when: "requesting ticket form statuses with invalid client configuration"
        client.listTicketFormStatuses().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenFormStatusesClient
        "unreachable url" | badUrlFormStatusesClient
    }
}
