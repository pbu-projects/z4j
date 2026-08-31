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
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class AssigneeFieldAssignableGroupsClientSpec extends Z4jSpec {

    @Shared
    AssigneeFieldAssignableGroupsClient adminAssignableGroupsClient, agentAssignableGroupsClient, userAssignableGroupsClient,
                                        badTokenAssignableGroupsClient, badUrlAssignableGroupsClient

    def setupSpec() {
        adminAssignableGroupsClient = adminCtx.getBean(AssigneeFieldAssignableGroupsClient.class)
        agentAssignableGroupsClient = agentCtx.getBean(AssigneeFieldAssignableGroupsClient.class)
        userAssignableGroupsClient = userCtx.getBean(AssigneeFieldAssignableGroupsClient.class)
        badTokenAssignableGroupsClient = badTokenCtx.getBean(AssigneeFieldAssignableGroupsClient.class)
        badUrlAssignableGroupsClient = badUrlCtx.getBean(AssigneeFieldAssignableGroupsClient.class)
    }

    @Unroll
    def "can autocomplete assignable groups and agents as an #userType"(
            AssigneeFieldAssignableGroupsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting assignable groups and agents autocomplete"
        client.listAssigneeFieldAssignableGroupsAndAgentsSearch("Support").block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminAssignableGroupsClient, "admin"],
                [agentAssignableGroupsClient, "agent"]
        ]
    }

    def "end user cannot autocomplete assignable groups and agents"() {
        given: "an end user client"

        when: "requesting assignable groups as an end user"
        userAssignableGroupsClient.listAssigneeFieldAssignableGroupsAndAgentsSearch("Support").block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling assignable groups client with #description throws HttpClientException"(
            String description, AssigneeFieldAssignableGroupsClient client) {
        when: "requesting assignable groups with invalid client configuration"
        client.listAssigneeFieldAssignableGroupsAndAgentsSearch("Support").block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAssignableGroupsClient
        "unreachable url" | badUrlAssignableGroupsClient
    }
}
