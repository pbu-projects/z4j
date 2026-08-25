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
import lol.pbu.z4j.model.GroupObject
import lol.pbu.z4j.model.GroupResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class GroupsClientSpec extends Z4jSpec {

    @Shared
    GroupsClient adminGroupsClient, agentGroupsClient, userGroupsClient,
                 badTokenGroupsClient, badUrlGroupsClient

    @Shared
    Integer adminUserId

    def setupSpec() {
        adminGroupsClient = adminCtx.getBean(GroupsClient.class)
        agentGroupsClient = agentCtx.getBean(GroupsClient.class)
        userGroupsClient = userCtx.getBean(GroupsClient.class)
        badTokenGroupsClient = badTokenCtx.getBean(GroupsClient.class)
        badUrlGroupsClient = badUrlCtx.getBean(GroupsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id
    }

    @Unroll
    def "can count groups as an #userType"(GroupsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting group count"
        client.countGroups().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminGroupsClient, "admin"],
                [agentGroupsClient, "agent"]
        ]
    }

    @Unroll
    def "can count user groups as an #userType"(GroupsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user groups count"
        if (adminUserId != null) {
            client.countUserGroups(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminGroupsClient, "admin"],
                [agentGroupsClient, "agent"]
        ]
    }

    @Unroll
    def "can list groups as an #userType with excludeDeleted=#excludeDeleted"(
            GroupsClient client, String userType, Boolean excludeDeleted) {
        given: "an authenticated client for #userType"

        when: "requesting groups list"
        client.listGroups(excludeDeleted).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], excludeDeleted] << [
                [[adminGroupsClient, "admin"], [agentGroupsClient, "agent"]],
                [null, true, false]
        ].combinations()
    }

    @Unroll
    def "can list user groups as an #userType with excludeDeleted=#excludeDeleted"(
            GroupsClient client, String userType, Boolean excludeDeleted) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user groups list"
        if (adminUserId != null) {
            client.listUserGroups(adminUserId, excludeDeleted).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], excludeDeleted] << [
                [[adminGroupsClient, "admin"], [agentGroupsClient, "agent"]],
                [null, true, false]
        ].combinations()
    }

    @Unroll
    def "can list assignable groups as an #userType"(GroupsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting assignable groups"
        client.listAssignableGroups().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminGroupsClient, "admin"],
                [agentGroupsClient, "agent"]
        ]
    }

    def "can perform group CRUD lifecycle as an admin"() {
        given: "a group payload with unique name"
        String groupName = faker.company().name() + " " + UUID.randomUUID().toString().substring(0, 8)
        GroupResponse createRequest = new GroupResponse(new GroupObject(groupName).setDescription("Test group created by z4j"))
        Integer createdGroupId = null

        when: "creating a new group as admin"
        GroupResponse created = adminGroupsClient.createGroup(createRequest).block()
        createdGroupId = created?.group?.id

        then: "group is created successfully"
        noExceptionThrown()
        createdGroupId != null

        when: "retrieving the created group by ID"
        adminGroupsClient.showGroupById(createdGroupId).block()

        then: "group details deserialize successfully"
        noExceptionThrown()

        when: "updating the group description"
        GroupResponse updateRequest = new GroupResponse(new GroupObject(groupName).setDescription("Updated description"))
        adminGroupsClient.updateGroup(createdGroupId, updateRequest).block()

        then: "group updates successfully"
        noExceptionThrown()

        cleanup: "delete the created test group from the instance"
        if (createdGroupId != null) {
            try {
                adminGroupsClient.deleteGroup(createdGroupId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "end user cannot list or count groups"() {
        given: "an end user client"

        when: "requesting groups count as an end user"
        userGroupsClient.countGroups().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling groups client with #description throws HttpClientException"(
            String description, GroupsClient client) {
        when: "requesting groups with invalid client configuration"
        client.countGroups().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenGroupsClient
        "unreachable url" | badUrlGroupsClient
    }
}
