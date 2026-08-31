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
import lol.pbu.z4j.model.CurrentUserResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("ticketing")
class TicketSkipsClientSpec extends Z4jSpec {

    @Shared
    TicketSkipsClient adminSkipsClient, agentSkipsClient, userSkipsClient,
                      badTokenSkipsClient, badUrlSkipsClient

    @Shared
    Long adminUserId

    def setupSpec() {
        adminSkipsClient = adminCtx.getBean(TicketSkipsClient.class)
        agentSkipsClient = agentCtx.getBean(TicketSkipsClient.class)
        userSkipsClient = userCtx.getBean(TicketSkipsClient.class)
        badTokenSkipsClient = badTokenCtx.getBean(TicketSkipsClient.class)
        badUrlSkipsClient = badUrlCtx.getBean(TicketSkipsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id
    }

    @Unroll
    def "can list skips as an #userType"(
            TicketSkipsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting skips list"
        client.listSkips(null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSkipsClient, "admin"],
                [agentSkipsClient, "agent"]
        ]
    }

    @Unroll
    def "can list user ticket skips as an #userType"(
            TicketSkipsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user ticket skips"
        if (adminUserId != null) {
            client.listTicketSkips(adminUserId, null).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSkipsClient, "admin"],
                [agentSkipsClient, "agent"]
        ]
    }

    def "end user cannot list skips"() {
        given: "an end user client"

        when: "requesting skips as an end user"
        userSkipsClient.listSkips(null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling skips client with #description throws HttpClientException"(
            String description, TicketSkipsClient client) {
        when: "requesting skips with invalid client configuration"
        client.listSkips(null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSkipsClient
        "unreachable url" | badUrlSkipsClient
    }
}
