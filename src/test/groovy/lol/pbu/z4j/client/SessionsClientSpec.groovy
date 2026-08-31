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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CurrentUserResponse
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("admin")
class SessionsClientSpec extends Z4jSpec {

    @Shared
    SessionsClient adminSessionsClient, agentSessionsClient, userSessionsClient,
                   badTokenSessionsClient, badUrlSessionsClient

    @Shared
    Long adminUserId

    def setupSpec() {
        adminSessionsClient = adminCtx.getBean(SessionsClient.class)
        agentSessionsClient = agentCtx.getBean(SessionsClient.class)
        userSessionsClient = userCtx.getBean(SessionsClient.class)
        badTokenSessionsClient = badTokenCtx.getBean(SessionsClient.class)
        badUrlSessionsClient = badUrlCtx.getBean(SessionsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id
    }

    @Unroll
    def "can list sessions for all roles as #userType"(
            SessionsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting sessions list"
        client.listSessions().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSessionsClient, "admin"],
                [agentSessionsClient, "agent"],
                [userSessionsClient, "end user"]
        ]
    }

    @Unroll
    def "can list user sessions for all roles as #userType"(
            SessionsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user sessions list"
        if (adminUserId != null) {
            client.listUserSessions(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSessionsClient, "admin"],
                [agentSessionsClient, "agent"],
                [userSessionsClient, "end user"]
        ]
    }

    @Unroll
    def "calling sessions client with #description throws HttpClientException"(
            String description, SessionsClient client) {
        when: "requesting sessions with invalid client configuration"
        client.listSessions().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSessionsClient
        "unreachable url" | badUrlSessionsClient
    }
}
