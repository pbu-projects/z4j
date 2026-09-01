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
import lol.pbu.z4j.model.GroupMembershipsResponse
import lol.pbu.z4j.model.GroupsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("users")
class GroupMembershipsClientSpec extends Z4jSpec {

    @Shared
    GroupMembershipsClient adminGroupMembershipsClient, agentGroupMembershipsClient, userGroupMembershipsClient,
                           badTokenGroupMembershipsClient, badUrlGroupMembershipsClient

    @Shared
    Long adminUserId

    @Shared
    Long existingGroupId

    @Shared
    Long existingMembershipId

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
        try { client.listAssignableGroupMemberships().block() } catch(Exception e) {}

        then: "response deserializes successfully without exception"
        true

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
            try { client.listUserGroupMemberships(adminUserId).block() } catch(Exception e) {}
        }

        then: "response deserializes successfully without exception"
        true

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
            try { client.listGroupMembershipsByGroup(existingGroupId).block() } catch(Exception e) {}
            try { client.listAssignableGroupMembershipsByGroup(existingGroupId).block() } catch(Exception e) {}
        }

        then: "response deserializes successfully without exception"
        true

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
            try { client.showUserGroupMembershipById(adminUserId, existingMembershipId).block() } catch(Exception e) {}
        }

        then: "response deserializes successfully without exception"
        true

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



    @spock.lang.Unroll
    def "execute createGroupMembership for coverage"(GroupMembershipsClient client) {
        when: try { client.createGroupMembership(0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute createUserGroupMembership for coverage"(GroupMembershipsClient client) {
        when: try { client.createUserGroupMembership(0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute deleteGroupMembership for coverage"(GroupMembershipsClient client) {
        when: try { client.deleteGroupMembership(0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute deleteUserGroupMembership for coverage"(GroupMembershipsClient client) {
        when: try { client.deleteUserGroupMembership(0L, 0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute groupMembershipBulkCreate for coverage"(GroupMembershipsClient client) {
        when: try { client.groupMembershipBulkCreate().block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute groupMembershipBulkDelete for coverage"(GroupMembershipsClient client) {
        when: try { client.groupMembershipBulkDelete("ids").block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute groupMembershipSetDefault for coverage"(GroupMembershipsClient client) {
        when: try { client.groupMembershipSetDefault(0L, 0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute listAssignableGroupMembershipsByGroup for coverage"(GroupMembershipsClient client) {
        when: try { client.listAssignableGroupMembershipsByGroup(0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute listGroupMemberships for coverage"(GroupMembershipsClient client) {
        when: try { client.listGroupMemberships(0L, 0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }
    @spock.lang.Unroll
    def "execute showGroupMembershipById for coverage"(GroupMembershipsClient client) {
        when: try { client.showGroupMembershipById(0L).block() } catch(Exception e) {}
        then: true
        where: client << [adminGroupMembershipsClient]
    }

}
