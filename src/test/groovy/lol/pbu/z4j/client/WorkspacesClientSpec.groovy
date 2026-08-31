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
import lol.pbu.z4j.model.WorkspaceResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class WorkspacesClientSpec extends Z4jSpec {

    @Shared
    WorkspacesClient adminWorkspacesClient, agentWorkspacesClient, userWorkspacesClient,
                     badTokenWorkspacesClient, badUrlWorkspacesClient

    @Shared
    Long existingWorkspaceId

    def setupSpec() {
        adminWorkspacesClient = adminCtx.getBean(WorkspacesClient.class)
        agentWorkspacesClient = agentCtx.getBean(WorkspacesClient.class)
        userWorkspacesClient = userCtx.getBean(WorkspacesClient.class)
        badTokenWorkspacesClient = badTokenCtx.getBean(WorkspacesClient.class)
        badUrlWorkspacesClient = badUrlCtx.getBean(WorkspacesClient.class)

        WorkspaceResponse workspaces = adminWorkspacesClient.listWorkspaces().block()
        if (workspaces?.workspaces && !workspaces.workspaces.isEmpty()) {
            existingWorkspaceId = workspaces.workspaces.first().id
        }
    }

    @Unroll
    def "can list workspaces as an #userType"(
            WorkspacesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting workspaces list"
        client.listWorkspaces().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminWorkspacesClient, "admin"],
                [agentWorkspacesClient, "agent"]
        ]
    }

    def "can show workspace by ID as an admin"() {
        given: "an authenticated admin client and existing workspace ID"

        when: "requesting workspace by ID"
        if (existingWorkspaceId != null) {
            adminWorkspacesClient.showWorkspace(existingWorkspaceId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "end user cannot list workspaces"() {
        given: "an end user client"

        when: "requesting workspaces as an end user"
        userWorkspacesClient.listWorkspaces().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling workspaces client with #description throws HttpClientException"(
            String description, WorkspacesClient client) {
        when: "requesting workspaces with invalid client configuration"
        client.listWorkspaces().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenWorkspacesClient
        "unreachable url" | badUrlWorkspacesClient
    }
}
