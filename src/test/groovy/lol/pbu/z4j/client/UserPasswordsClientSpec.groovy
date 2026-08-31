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
@Tag("users")
class UserPasswordsClientSpec extends Z4jSpec {

    @Shared
    UserPasswordsClient adminPasswordsClient, agentPasswordsClient, userPasswordsClient,
                        badTokenPasswordsClient, badUrlPasswordsClient

    @Shared
    Long adminUserId

    def setupSpec() {
        adminPasswordsClient = adminCtx.getBean(UserPasswordsClient.class)
        agentPasswordsClient = agentCtx.getBean(UserPasswordsClient.class)
        userPasswordsClient = userCtx.getBean(UserPasswordsClient.class)
        badTokenPasswordsClient = badTokenCtx.getBean(UserPasswordsClient.class)
        badUrlPasswordsClient = badUrlCtx.getBean(UserPasswordsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id
    }

    @Unroll
    def "can get user password requirements as #userType"(
            UserPasswordsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user password requirements"
        if (adminUserId != null) {
            client.getUserPasswordRequirements(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminPasswordsClient, "admin"],
                [agentPasswordsClient, "agent"],
                [userPasswordsClient, "end user"]
        ]
    }

    @Unroll
    def "calling user passwords client with #description throws HttpClientException"(
            String description, UserPasswordsClient client) {
        when: "requesting user password requirements with invalid client configuration"
        client.getUserPasswordRequirements(adminUserId ?: 1).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenPasswordsClient
        "unreachable url" | badUrlPasswordsClient
    }
}
