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
@Tag("admin")
class SuspendedTicketsClientSpec extends Z4jSpec {

    @Shared
    SuspendedTicketsClient adminSuspendedTicketsClient, agentSuspendedTicketsClient, userSuspendedTicketsClient,
                           badTokenSuspendedTicketsClient, badUrlSuspendedTicketsClient

    def setupSpec() {
        adminSuspendedTicketsClient = adminCtx.getBean(SuspendedTicketsClient.class)
        agentSuspendedTicketsClient = agentCtx.getBean(SuspendedTicketsClient.class)
        userSuspendedTicketsClient = userCtx.getBean(SuspendedTicketsClient.class)
        badTokenSuspendedTicketsClient = badTokenCtx.getBean(SuspendedTicketsClient.class)
        badUrlSuspendedTicketsClient = badUrlCtx.getBean(SuspendedTicketsClient.class)
    }

    @Unroll
    def "can list suspended tickets as an #userType"(
            SuspendedTicketsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting suspended tickets list"
        client.listSuspendedTickets(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSuspendedTicketsClient, "admin"],
                [agentSuspendedTicketsClient, "agent"]
        ]
    }

    def "end user cannot list suspended tickets"() {
        given: "an end user client"

        when: "requesting suspended tickets as an end user"
        userSuspendedTicketsClient.listSuspendedTickets(null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling suspended tickets client with #description throws HttpClientException"(
            String description, SuspendedTicketsClient client) {
        when: "requesting suspended tickets with invalid client configuration"
        client.listSuspendedTickets(null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSuspendedTicketsClient
        "unreachable url" | badUrlSuspendedTicketsClient
    }
}
