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
class TicketImportClientSpec extends Z4jSpec {

    @Shared
    TicketImportClient adminImportClient, agentImportClient, userImportClient,
                       badTokenImportClient, badUrlImportClient

    def setupSpec() {
        adminImportClient = adminCtx.getBean(TicketImportClient.class)
        agentImportClient = agentCtx.getBean(TicketImportClient.class)
        userImportClient = userCtx.getBean(TicketImportClient.class)
        badTokenImportClient = badTokenCtx.getBean(TicketImportClient.class)
        badUrlImportClient = badUrlCtx.getBean(TicketImportClient.class)
    }

    @Unroll
    def "#userType cannot import tickets"(TicketImportClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "attempting ticket import"
        client.ticketImport(null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentImportClient, "agent"],
                [userImportClient, "end user"]
        ]
    }

    @Unroll
    def "calling ticket import client with #description throws HttpClientException"(
            String description, TicketImportClient client) {
        when: "attempting ticket import with invalid client configuration"
        client.ticketImport(null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenImportClient
        "unreachable url" | badUrlImportClient
    }
}
