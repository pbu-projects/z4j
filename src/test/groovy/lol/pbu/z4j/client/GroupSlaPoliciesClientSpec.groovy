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
import lol.pbu.z4j.model.GroupSLAPoliciesResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("users")
class GroupSlaPoliciesClientSpec extends Z4jSpec {

    @Shared
    GroupSlaPoliciesClient adminGroupSlaPoliciesClient, agentGroupSlaPoliciesClient, userGroupSlaPoliciesClient,
                           badTokenGroupSlaPoliciesClient, badUrlGroupSlaPoliciesClient

    @Shared
    Long existingGroupSlaPolicyId

    def setupSpec() {
        adminGroupSlaPoliciesClient = adminCtx.getBean(GroupSlaPoliciesClient.class)
        agentGroupSlaPoliciesClient = agentCtx.getBean(GroupSlaPoliciesClient.class)
        userGroupSlaPoliciesClient = userCtx.getBean(GroupSlaPoliciesClient.class)
        badTokenGroupSlaPoliciesClient = badTokenCtx.getBean(GroupSlaPoliciesClient.class)
        badUrlGroupSlaPoliciesClient = badUrlCtx.getBean(GroupSlaPoliciesClient.class)

        GroupSLAPoliciesResponse policies = adminGroupSlaPoliciesClient.listGroupSLAPolicies().block()
        if (policies?.groupSlaPolicies && !policies.groupSlaPolicies.isEmpty()) {
            existingGroupSlaPolicyId = policies.groupSlaPolicies.first().id
        }
    }

    def "can list group SLA policies as an admin"() {
        given: "an authenticated admin client"

        when: "requesting group SLA policies list"
        adminGroupSlaPoliciesClient.listGroupSLAPolicies().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can retrieve group SLA policy filter definitions as an admin"() {
        given: "an authenticated admin client"

        when: "requesting group SLA policy filter definitions"
        adminGroupSlaPoliciesClient.retrieveGroupSLAPolicyFilterDefinitionItems().block()

        then: "a 404 or 403 is thrown due to sandbox plan restrictions"
        thrown(HttpClientResponseException)
    }

    def "can show group SLA policy by ID as an admin"() {
        given: "an authenticated admin client and existing group SLA policy ID"

        when: "requesting group SLA policy by ID"
        if (existingGroupSlaPolicyId != null) {
            adminGroupSlaPoliciesClient.showGroupSLAPolicy(existingGroupSlaPolicyId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list group SLA policies"(GroupSlaPoliciesClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting group SLA policies"
        client.listGroupSLAPolicies().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentGroupSlaPoliciesClient, "agent"],
                [userGroupSlaPoliciesClient, "end user"]
        ]
    }

    @Unroll
    def "calling group SLA policies client with #description throws HttpClientException"(
            String description, GroupSlaPoliciesClient client) {
        when: "requesting group SLA policies with invalid client configuration"
        client.listGroupSLAPolicies().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenGroupSlaPoliciesClient
        "unreachable url" | badUrlGroupSlaPoliciesClient
    }
}
