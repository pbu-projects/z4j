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
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class AssigneeFieldAssignableAgentsClientSpec extends Z4jSpec {

    @Shared
    AssigneeFieldAssignableAgentsClient adminAssignableAgentsClient, agentAssignableAgentsClient, userAssignableAgentsClient,
                                        badTokenAssignableAgentsClient, badUrlAssignableAgentsClient

    def setupSpec() {
        adminAssignableAgentsClient = adminCtx.getBean(AssigneeFieldAssignableAgentsClient.class)
        agentAssignableAgentsClient = agentCtx.getBean(AssigneeFieldAssignableAgentsClient.class)
        userAssignableAgentsClient = userCtx.getBean(AssigneeFieldAssignableAgentsClient.class)
        badTokenAssignableAgentsClient = badTokenCtx.getBean(AssigneeFieldAssignableAgentsClient.class)
        badUrlAssignableAgentsClient = badUrlCtx.getBean(AssigneeFieldAssignableAgentsClient.class)
    }

    @Unroll
    def "can list assignable groups on assignee field as an #userType"(
            AssigneeFieldAssignableAgentsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting assignable groups"
        client.listAssigneeFieldAssignableGroups().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminAssignableAgentsClient, "admin"],
                [agentAssignableAgentsClient, "agent"]
        ]
    }

    def "end user cannot list assignable groups on assignee field"() {
        given: "an end user client"

        when: "requesting assignable groups as an end user"
        userAssignableAgentsClient.listAssigneeFieldAssignableGroups().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling assignable agents client with #description throws HttpClientException"(
            String description, AssigneeFieldAssignableAgentsClient client) {
        when: "requesting assignable groups with invalid client configuration"
        client.listAssigneeFieldAssignableGroups().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAssignableAgentsClient
        "unreachable url" | badUrlAssignableAgentsClient
    }
}
