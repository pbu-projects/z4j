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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.DeletedUserResponse
import lol.pbu.z4j.model.JobStatusResponse
import lol.pbu.z4j.model.User
import lol.pbu.z4j.model.UserCountResponse
import lol.pbu.z4j.model.UserRelatedResponse
import lol.pbu.z4j.model.UserRequest
import lol.pbu.z4j.model.UserResponse
import lol.pbu.z4j.model.UsersResponse
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class UserClientSpec extends Z4jSpec {

    @Shared
    UserClient adminUserClient

    @Shared
    UserClient agentUserClient

    @Shared
    UserClient userUserClient

    @Shared
    UserClient badUrlUserClient

    @Shared
    User adminUser

    @Shared
    Long adminUserId

    def setupSpec() {
        adminUserClient = adminCtx.getBean(UserClient)
        agentUserClient = agentCtx.getBean(UserClient)
        userUserClient = userCtx.getBean(UserClient)
        badUrlUserClient = badUrlCtx.getBean(UserClient)

        def meResponse = adminUserClient.showCurrentUser().block()
        if (meResponse != null && meResponse.getUser() != null) {
            adminUser = meResponse.getUser()
            adminUserId = adminUser.getId()
        }
    }

    @Unroll
    def "can show current user as #roleName"(UserClient client, String roleName, String expectedRole) {
        when:
        def response = client.showCurrentUser().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            user != null
            user.id != null
            user.role == expectedRole
            user.email != null
        }

        where:
        client          | roleName   | expectedRole
        adminUserClient | "admin"    | "admin"
        agentUserClient | "agent"    | "agent"
        userUserClient  | "end-user" | "end-user"
    }

    def "can list users without role filter"() {
        when:
        def response = adminUserClient.listUsers().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            users != null
            !users.isEmpty()
        }
    }

    def "can list users with role filter"() {
        when:
        def response = adminUserClient.listUsers("admin", null, null).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            users != null
            !users.isEmpty()
            users.every { it.role == "admin" }
        }
    }

    def "can count users without role filter"() {
        when:
        def response = adminUserClient.countUsers().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            count != null
            count.value != null
            count.value > 0
        }
    }

    def "can count users with role filter"() {
        when:
        def response = adminUserClient.countUsers("admin", null).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            count != null
            count.value != null
            count.value > 0
        }
    }

    def "can show user by id"() {
        setup:
        assert adminUserId != null : "adminUserId must be populated by setupSpec"

        when:
        def response = adminUserClient.showUser(adminUserId).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            user != null
            user.id == adminUserId
            user.email != null
        }
    }

    def "can search users by query"() {
        when:
        def response = adminUserClient.searchUsers("role:admin", null).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            users != null
            !users.isEmpty()
        }
    }

    def "can autocomplete users by name"() {
        given:
        def prefix = adminUser?.getName() ? adminUser.getName().take(3) : "adm"

        when:
        def response = adminUserClient.autocompleteUsers(prefix).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            users != null
            !users.isEmpty()
        }
    }

    def "can show many users by ids"() {
        setup:
        assert adminUserId != null : "adminUserId must be populated by setupSpec"

        when:
        def response = adminUserClient.showManyUsers([adminUserId]).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            users != null
            !users.isEmpty()
            users.any { it.id == adminUserId }
        }
    }

    def "can create update soft delete and permanently delete a user"() {
        given:
        Long createdUserId = null
        def uniqueEmail = faker.internet().emailAddress()
        def initialName = faker.name().fullName()
        def userToCreate = new User()
                .setName(initialName)
                .setEmail(uniqueEmail)
                .setRole("end-user")

        when: "creating user"
        def createdResponse = adminUserClient.createUser(new UserRequest(userToCreate)).block()
        createdUserId = createdResponse?.getUser()?.getId()

        then:
        noExceptionThrown()
        verifyAll(createdResponse) {
            user != null
            user.id != null
            user.email == uniqueEmail
            user.name == initialName
        }

        when: "updating user"
        def updatedName = faker.name().fullName()
        def userToUpdate = new User().setName(updatedName)
        def updatedResponse = adminUserClient.updateUser(createdUserId, new UserRequest(userToUpdate)).block()

        then:
        noExceptionThrown()
        verifyAll(updatedResponse) {
            user != null
            user.id == createdUserId
            user.name == updatedName
        }

        when: "soft deleting user"
        def deletedResponse = adminUserClient.deleteUser(createdUserId).block()

        then:
        noExceptionThrown()
        verifyAll(deletedResponse) {
            user != null
            user.id == createdUserId
        }

        when: "showing soft deleted user"
        def showDeletedResponse = adminUserClient.showDeletedUser(createdUserId).block()

        then:
        noExceptionThrown()
        verifyAll(showDeletedResponse) {
            deletedUser != null
            deletedUser.id == createdUserId
        }

        when: "permanently deleting user"
        def purgeResponse = adminUserClient.permanentlyDeleteUser(createdUserId).block()

        then:
        noExceptionThrown()
        verifyAll(purgeResponse) {
            deletedUser != null
            deletedUser.id == createdUserId
        }

        cleanup:
        if (createdUserId != null) {
            try {
                adminUserClient.deleteUser(createdUserId).block()
            } catch (Exception ignored) {
            }
            try {
                adminUserClient.permanentlyDeleteUser(createdUserId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "can create or update user"() {
        given:
        Long userId = null
        def email = faker.internet().emailAddress()
        def firstName = faker.name().fullName()
        def secondName = faker.name().fullName()

        when: "creating user via create or update"
        def createResponse = adminUserClient.createOrUpdateUser(
                new UserRequest(new User().setName(firstName).setEmail(email).setRole("end-user"))
        ).block()
        userId = createResponse?.getUser()?.getId()

        then:
        noExceptionThrown()
        verifyAll(createResponse) {
            user != null
            user.id != null
            user.email == email
            user.name == firstName
        }

        when: "updating existing user via create or update"
        def updateResponse = adminUserClient.createOrUpdateUser(
                new UserRequest(new User().setName(secondName).setEmail(email))
        ).block()

        then:
        noExceptionThrown()
        verifyAll(updateResponse) {
            user != null
            user.id == userId
            user.name == secondName
        }

        cleanup:
        if (userId != null) {
            try {
                adminUserClient.deleteUser(userId).block()
            } catch (Exception ignored) {
            }
            try {
                adminUserClient.permanentlyDeleteUser(userId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "can update many users with uniform or distinct attributes"() {
        given:
        Long user1Id = null
        Long user2Id = null
        def email1 = faker.internet().emailAddress()
        def email2 = faker.internet().emailAddress()
        def user1Response = adminUserClient.createUser(
                new UserRequest(new User().setName(faker.name().fullName()).setEmail(email1).setRole("end-user"))
        ).block()
        def user2Response = adminUserClient.createUser(
                new UserRequest(new User().setName(faker.name().fullName()).setEmail(email2).setRole("end-user"))
        ).block()
        user1Id = user1Response?.getUser()?.getId()
        user2Id = user2Response?.getUser()?.getId()

        when: "updating many users with uniform attributes"
        def uniformResponse = adminUserClient.updateManyUsers(
                [user1Id, user2Id],
                new UserRequest(new User().setNotes("Bulk updated notes"))
        ).block()

        then:
        noExceptionThrown()
        verifyAll(uniformResponse) {
            jobStatus != null
            jobStatus.id != null
            jobStatus.status != null
        }

        when: "updating many users with distinct attributes"
        def distinctResponse = adminUserClient.updateManyUsers([
                new User().setId(user1Id).setDetails("First user detail"),
                new User().setId(user2Id).setDetails("Second user detail")
        ]).block()

        then:
        noExceptionThrown()
        verifyAll(distinctResponse) {
            jobStatus != null
            jobStatus.id != null
            jobStatus.status != null
        }

        cleanup:
        [user1Id, user2Id].each { id ->
            if (id != null) {
                try {
                    adminUserClient.deleteUser(id).block()
                } catch (Exception ignored) {
                }
                try {
                    adminUserClient.permanentlyDeleteUser(id).block()
                } catch (Exception ignored) {
                }
            }
        }
    }

    def "can show user related info"() {
        setup:
        assert adminUserId != null : "adminUserId must be populated by setupSpec"

        when:
        def response = adminUserClient.showUserRelated(adminUserId).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            userRelated != null
            userRelated.assignedTickets != null
            userRelated.requestedTickets != null
        }
    }

    def "can list deleted users"() {
        when:
        def response = adminUserClient.listDeletedUsers().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            deletedUsers != null
        }
    }

    def "can count deleted users"() {
        when:
        def response = adminUserClient.countDeletedUsers().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            count != null
            count.value != null
        }
    }

    def "calling user client with bad url throws HttpClientException"() {
        when:
        badUrlUserClient.showCurrentUser().block()

        then:
        thrown(HttpClientException)
    }
}
