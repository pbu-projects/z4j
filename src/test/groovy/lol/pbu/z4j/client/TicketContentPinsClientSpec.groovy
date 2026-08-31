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
class TicketContentPinsClientSpec extends Z4jSpec {

    @Shared
    TicketContentPinsClient adminPinsClient, agentPinsClient, userPinsClient,
                            badTokenPinsClient, badUrlPinsClient

    def setupSpec() {
        adminPinsClient = adminCtx.getBean(TicketContentPinsClient.class)
        agentPinsClient = agentCtx.getBean(TicketContentPinsClient.class)
        userPinsClient = userCtx.getBean(TicketContentPinsClient.class)
        badTokenPinsClient = badTokenCtx.getBean(TicketContentPinsClient.class)
        badUrlPinsClient = badUrlCtx.getBean(TicketContentPinsClient.class)
    }

    @Unroll
    def "can list ticket content pins as an #userType"(
            TicketContentPinsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ticket content pins list"
        client.listTicketContentPins(null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminPinsClient, "admin"],
                [agentPinsClient, "agent"]
        ]
    }

    def "end user cannot list ticket content pins"() {
        given: "an end user client"

        when: "requesting ticket content pins as an end user"
        userPinsClient.listTicketContentPins(null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ticket content pins client with #description throws HttpClientException"(
            String description, TicketContentPinsClient client) {
        when: "requesting ticket content pins with invalid client configuration"
        client.listTicketContentPins(null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenPinsClient
        "unreachable url" | badUrlPinsClient
    }
}
