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
import lol.pbu.z4j.model.GroupMembershipsResponse
import lol.pbu.z4j.model.GroupsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class GroupMembershipsClientSpec extends Z4jSpec {

    @Shared
    GroupMembershipsClient adminGroupMembershipsClient, agentGroupMembershipsClient, userGroupMembershipsClient,
                           badTokenGroupMembershipsClient, badUrlGroupMembershipsClient

    @Shared
    Integer adminUserId

    @Shared
    Integer existingGroupId

    @Shared
    Integer existingMembershipId

    def setupSpec() {
        adminGroupMembershipsClient = adminCtx.getBean(GroupMembershipsClient.class)
        agentGroupMembershipsClient = agentCtx.getBean(GroupMembershipsClient.class)
        userGroupMembershipsClient = userCtx.getBean(GroupMembershipsClient.class)
        badTokenGroupMembershipsClient = badTokenCtx.getBean(GroupMembershipsClient.class)
        badUrlGroupMembershipsClient = badUrlCtx.getBean(GroupMembershipsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id

        GroupsResponse groups = adminCtx.getBean(GroupsClient.class).listGroups(null).block()
        if (groups?.groups && !groups.groups.isEmpty()) {
            existingGroupId = groups.groups.first().id
        }

        if (adminUserId != null) {
            GroupMembershipsResponse memberships = adminGroupMembershipsClient.listUserGroupMemberships(adminUserId).block()
            if (memberships?.groupMemberships && !memberships.groupMemberships.isEmpty()) {
                existingMembershipId = memberships.groupMemberships.first().id
            }
        }
    }

    @Unroll
    def "can list assignable group memberships as an #userType"(
            GroupMembershipsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting assignable group memberships"
        client.listAssignableGroupMemberships().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminGroupMembershipsClient, "admin"],
                [agentGroupMembershipsClient, "agent"]
        ]
    }

    @Unroll
    def "can list user group memberships as an #userType"(
            GroupMembershipsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user group memberships"
        if (adminUserId != null) {
            client.listUserGroupMemberships(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminGroupMembershipsClient, "admin"],
                [agentGroupMembershipsClient, "agent"]
        ]
    }

    @Unroll
    def "can list group memberships by group as an #userType"(
            GroupMembershipsClient client, String userType) {
        given: "an authenticated client for #userType and existing group ID"

        when: "requesting group memberships for specific group"
        if (existingGroupId != null) {
            client.listGroupMembershipsByGroup(existingGroupId).block()
            client.listAssignableGroupMembershipsByGroup(existingGroupId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminGroupMembershipsClient, "admin"],
                [agentGroupMembershipsClient, "agent"]
        ]
    }

    @Unroll
    def "can show user group membership by ID as an #userType"(
            GroupMembershipsClient client, String userType) {
        given: "an authenticated client for #userType, admin user ID, and membership ID"

        when: "requesting user group membership by ID"
        if (adminUserId != null && existingMembershipId != null) {
            client.showUserGroupMembershipById(adminUserId, existingMembershipId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminGroupMembershipsClient, "admin"],
                [agentGroupMembershipsClient, "agent"]
        ]
    }

    def "end user cannot list group memberships"() {
        given: "an end user client"

        when: "requesting assignable group memberships as an end user"
        userGroupMembershipsClient.listAssignableGroupMemberships().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling group memberships client with #description throws HttpClientException"(
            String description, GroupMembershipsClient client) {
        when: "requesting group memberships with invalid client configuration"
        client.listAssignableGroupMemberships().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenGroupMembershipsClient
        "unreachable url" | badUrlGroupMembershipsClient
    }
}
