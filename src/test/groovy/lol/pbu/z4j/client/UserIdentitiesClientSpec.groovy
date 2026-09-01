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
import lol.pbu.z4j.model.UserIdentitiesResponse
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("users")
class UserIdentitiesClientSpec extends Z4jSpec {

    @Shared
    UserIdentitiesClient adminUserIdentitiesClient, agentUserIdentitiesClient, userUserIdentitiesClient,
                         badTokenUserIdentitiesClient, badUrlUserIdentitiesClient

    @Shared
    Long adminUserId

    @Shared
    Long existingIdentityId

    def setupSpec() {
        adminUserIdentitiesClient = adminCtx.getBean(UserIdentitiesClient.class)
        agentUserIdentitiesClient = agentCtx.getBean(UserIdentitiesClient.class)
        userUserIdentitiesClient = userCtx.getBean(UserIdentitiesClient.class)
        badTokenUserIdentitiesClient = badTokenCtx.getBean(UserIdentitiesClient.class)
        badUrlUserIdentitiesClient = badUrlCtx.getBean(UserIdentitiesClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id

        if (adminUserId != null) {
            UserIdentitiesResponse identities = adminUserIdentitiesClient.listUserIdentities(adminUserId, null).block()
            if (identities?.identities && !identities.identities.isEmpty()) {
                existingIdentityId = identities.identities.first().id
            }
        }
    }

    @Unroll
    def "can list user identities for all roles as #userType"(
            UserIdentitiesClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user identities list"
        if (adminUserId != null) {
            client.listUserIdentities(adminUserId, null).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUserIdentitiesClient, "admin"],
                [agentUserIdentitiesClient, "agent"],
                        ]
    }

    @Unroll
    def "can show user identity by ID as an admin"() {
        given: "an authenticated client for admin, admin user ID, and identity ID"

        when: "requesting user identity by ID"
        if (adminUserId != null && existingIdentityId != null) {
            adminUserIdentitiesClient.showUserIdentity(adminUserId, existingIdentityId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show user identity by ID as an agent"() {
        given: "an authenticated client for agent, admin user ID, and identity ID"

        when: "requesting user identity by ID"
        if (adminUserId != null && existingIdentityId != null) {
            agentUserIdentitiesClient.showUserIdentity(adminUserId, existingIdentityId).block()
        }

        then: "throws exception because agent cannot view admin identity"
        thrown(io.micronaut.http.client.exceptions.HttpClientResponseException)
    }

    @Unroll
    def "calling user identities client with #description throws HttpClientException"(
            String description, UserIdentitiesClient client) {
        when: "requesting user identities with invalid client configuration"
        if (adminUserId != null) {
            client.listUserIdentities(adminUserId, null).block()
        }

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenUserIdentitiesClient
        "unreachable url" | badUrlUserIdentitiesClient
    }
}
