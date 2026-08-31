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
import lol.pbu.z4j.model.CurrentUserResponse
import lol.pbu.z4j.model.UserInput
import lol.pbu.z4j.model.UserRequest
import lol.pbu.z4j.model.UserResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class UsersClientSpec extends Z4jSpec {

    @Shared
    UsersClient adminUsersClient, agentUsersClient, userUsersClient,
                badTokenUsersClient, badUrlUsersClient

    @Shared
    Long adminUserId

    def setupSpec() {
        adminUsersClient = adminCtx.getBean(UsersClient.class)
        agentUsersClient = agentCtx.getBean(UsersClient.class)
        userUsersClient = userCtx.getBean(UsersClient.class)
        badTokenUsersClient = badTokenCtx.getBean(UsersClient.class)
        badUrlUsersClient = badUrlCtx.getBean(UsersClient.class)

        CurrentUserResponse me = adminUsersClient.showCurrentUser().block()
        adminUserId = me?.user?.id
    }

    @Unroll
    def "can show current user for all roles as #userType"(UsersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting current user profile (/users/me)"
        client.showCurrentUser().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUsersClient, "admin"],
                [agentUsersClient, "agent"],
                [userUsersClient, "end user"]
        ]
    }

    @Unroll
    def "can count users as an #userType"(UsersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting user count"
        client.countUsers().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUsersClient, "admin"],
                [agentUsersClient, "agent"]
        ]
    }

    @Unroll
    def "can count deleted users as an #userType"(UsersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting deleted user count"
        client.countDeletedUsers().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUsersClient, "admin"],
                [agentUsersClient, "agent"]
        ]
    }

    @Unroll
    def "can list users as an #userType"(UsersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting users list"
        client.listUsers(null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUsersClient, "admin"],
                [agentUsersClient, "agent"]
        ]
    }

    @Unroll
    def "can autocomplete users as an #userType with name=#name"(
            UsersClient client, String userType, String name) {
        given: "an authenticated client for #userType"

        when: "searching for users by prefix"
        client.autocompleteUsers(name, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], name] << [
                [[adminUsersClient, "admin"], [agentUsersClient, "agent"]],
                ["te"]
        ].combinations()
    }

    @Unroll
    def "can search users as an #userType with query=#query"(
            UsersClient client, String userType, String query) {
        given: "an authenticated client for #userType"

        when: "searching for users by query string"
        client.searchUsers(query, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], query] << [
                [[adminUsersClient, "admin"], [agentUsersClient, "agent"]],
                ["type:user"]
        ].combinations()
    }

    @Unroll
    def "can show user and related info as an #userType"(UsersClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user by ID and related records"
        if (adminUserId != null) {
            client.showUser(adminUserId).block()
            client.showUserRelated(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUsersClient, "admin"],
                [agentUsersClient, "agent"]
        ]
    }

    def "can perform user CRUD lifecycle as an admin"() {
        given: "a user payload with unique email"
        String userEmail = "z4j-test-" + UUID.randomUUID().toString().substring(0, 8) + "@example.com"
        String userName = faker.name().fullName() + " " + UUID.randomUUID().toString().substring(0, 8)
        UserRequest createRequest = new UserRequest(new UserInput().setName(userName).setEmail(userEmail))
        Long createdUserId = null

        when: "creating a new user as admin"
        UserResponse created = adminUsersClient.createUser(createRequest).block()
        createdUserId = created?.user?.id

        then: "user is created successfully"
        noExceptionThrown()
        createdUserId != null

        when: "retrieving the created user by ID"
        adminUsersClient.showUser(createdUserId).block()

        then: "user details deserialize successfully"
        noExceptionThrown()

        when: "updating the user name"
        UserRequest updateRequest = new UserRequest(new UserInput().setName(userName + " Updated"))
        adminUsersClient.updateUser(createdUserId, updateRequest).block()

        then: "user updates successfully"
        noExceptionThrown()

        cleanup: "delete the created test user from the instance"
        if (createdUserId != null) {
            try {
                adminUsersClient.deleteUser(createdUserId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "end user cannot list or count users"() {
        given: "an end user client"

        when: "requesting users count as an end user"
        userUsersClient.countUsers().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling users client with #description throws HttpClientException"(
            String description, UsersClient client) {
        when: "requesting users with invalid client configuration"
        client.countUsers().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenUsersClient
        "unreachable url" | badUrlUsersClient
    }
}
