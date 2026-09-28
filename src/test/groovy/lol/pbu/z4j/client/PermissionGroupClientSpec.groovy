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

import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CreatePermissionGroupRequest
import lol.pbu.z4j.model.PermissionGroup
import lol.pbu.z4j.model.PermissionGroupResponse
import lol.pbu.z4j.model.PermissionGroupsResponse
import spock.lang.Shared

import java.util.UUID

import static io.micronaut.http.HttpStatus.FORBIDDEN
import static io.micronaut.http.HttpStatus.UNAUTHORIZED

@MicronautTest
class PermissionGroupClientSpec extends Z4jSpec {

    @Shared
    PermissionGroupClient adminPermissionGroupClient

    @Shared
    PermissionGroupClient agentPermissionGroupClient

    @Shared
    PermissionGroupClient userPermissionGroupClient

    @Shared
    UserSegmentClient userSegmentClient

    @Shared
    List<Long> staffUserSegmentIds = []

    def setupSpec() {
        adminPermissionGroupClient = adminCtx.getBean(PermissionGroupClient.class)
        agentPermissionGroupClient = agentCtx.getBean(PermissionGroupClient.class)
        userPermissionGroupClient = userCtx.getBean(PermissionGroupClient.class)
        userSegmentClient = adminCtx.getBean(UserSegmentClient.class)

        def segments = userSegmentClient.listUserSegments(null).block().getUserSegments()
        if (segments != null) {
            staffUserSegmentIds = segments.findAll { it.getUserType() == "staff" }.collect { it.getId() }
        }
    }

    def "can list permission groups as admin"() {
        when: "admin lists permission groups"
        PermissionGroupsResponse response = adminPermissionGroupClient.listPermissionGroups().block()

        then: "response is received without error"
        noExceptionThrown()
        response != null
        response.getPermissionGroups() != null
        !response.getPermissionGroups().isEmpty()
    }

    def "can show existing permission group as admin"() {
        given: "an existing permission group from the list"
        PermissionGroupsResponse listResponse = adminPermissionGroupClient.listPermissionGroups().block()
        Long existingId = listResponse.getPermissionGroups().first().getId()

        when: "showing the permission group by id"
        PermissionGroupResponse response = adminPermissionGroupClient.showPermissionGroup(existingId).block()

        then: "response contains the expected permission group"
        noExceptionThrown()
        response != null
        response.getPermissionGroup() != null
        response.getPermissionGroup().getId() == existingId
    }

    def "can create, update, and delete permission group as admin"() {
        given: "a new permission group with unique name"
        String groupName = "Test PG " + UUID.randomUUID().toString()
        PermissionGroup newGroup = new PermissionGroup(groupName)
        if (!staffUserSegmentIds.isEmpty()) {
            newGroup.setPublish([staffUserSegmentIds.first()])
        }
        CreatePermissionGroupRequest createRequest = new CreatePermissionGroupRequest(newGroup)
        Long createdId = null

        when: "creating the permission group"
        PermissionGroupResponse createResponse = adminPermissionGroupClient.createPermissionGroup(createRequest).block()
        if (createResponse != null && createResponse.getPermissionGroup() != null) {
            createdId = createResponse.getPermissionGroup().getId()
        }

        then: "permission group is created"
        noExceptionThrown()
        createResponse != null
        createResponse.getPermissionGroup() != null
        createdId != null
        createResponse.getPermissionGroup().getName() == groupName

        when: "showing the created permission group"
        PermissionGroupResponse showResponse = adminPermissionGroupClient.showPermissionGroup(createdId).block()

        then: "the retrieved permission group matches"
        noExceptionThrown()
        showResponse != null
        showResponse.getPermissionGroup().getId() == createdId
        showResponse.getPermissionGroup().getName() == groupName

        when: "updating the permission group"
        String updatedName = "Updated PG " + UUID.randomUUID().toString()
        PermissionGroup updateGroup = new PermissionGroup(updatedName)
        CreatePermissionGroupRequest updateRequest = new CreatePermissionGroupRequest(updateGroup)
        PermissionGroupResponse updateResponse = adminPermissionGroupClient.updatePermissionGroup(createdId, updateRequest).block()

        then: "the permission group is updated"
        noExceptionThrown()
        updateResponse != null
        updateResponse.getPermissionGroup().getName() == updatedName

        when: "deleting the permission group"
        adminPermissionGroupClient.deletePermissionGroup(createdId).block()

        then: "delete succeeds without error"
        noExceptionThrown()

        cleanup: "ensure cleanup of created permission group"
        if (createdId != null) {
            try {
                adminPermissionGroupClient.deletePermissionGroup(createdId).block()
            } catch (Exception ignored) {}
        }
    }

    def "cannot create permission group as #role user"(PermissionGroupClient client, String role) {
        given: "a create request with unique name"
        CreatePermissionGroupRequest request = new CreatePermissionGroupRequest(
                new PermissionGroup("Unauthorized PG " + UUID.randomUUID().toString())
        )

        when: "unauthorized client attempts to create permission group"
        client.createPermissionGroup(request).block()

        then: "HTTP 401 Unauthorized or 403 Forbidden is thrown"
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.getStatus() == UNAUTHORIZED || e.getStatus() == FORBIDDEN

        where:
        [client, role] << [
                [agentPermissionGroupClient, "agent"],
                [userPermissionGroupClient, "user"]
        ]
    }

    def "cannot delete permission group as #role user"(PermissionGroupClient client, String role) {
        when: "unauthorized client attempts to delete a permission group"
        client.deletePermissionGroup(1L).block()

        then: "HTTP 401 Unauthorized or 403 Forbidden is thrown"
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.getStatus() == UNAUTHORIZED || e.getStatus() == FORBIDDEN

        where:
        [client, role] << [
                [agentPermissionGroupClient, "agent"],
                [userPermissionGroupClient, "user"]
        ]
    }
}
