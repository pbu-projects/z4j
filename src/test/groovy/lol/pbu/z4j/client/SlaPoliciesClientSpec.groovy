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
import lol.pbu.z4j.model.SLAPoliciesResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class SlaPoliciesClientSpec extends Z4jSpec {

    @Shared
    SlaPoliciesClient adminSlaPoliciesClient, agentSlaPoliciesClient, userSlaPoliciesClient,
                      badTokenSlaPoliciesClient, badUrlSlaPoliciesClient

    @Shared
    Long existingSlaPolicyId

    def setupSpec() {
        adminSlaPoliciesClient = adminCtx.getBean(SlaPoliciesClient.class)
        agentSlaPoliciesClient = agentCtx.getBean(SlaPoliciesClient.class)
        userSlaPoliciesClient = userCtx.getBean(SlaPoliciesClient.class)
        badTokenSlaPoliciesClient = badTokenCtx.getBean(SlaPoliciesClient.class)
        badUrlSlaPoliciesClient = badUrlCtx.getBean(SlaPoliciesClient.class)

        SLAPoliciesResponse policies = adminSlaPoliciesClient.listSLAPolicies().block()
        if (policies?.slaPolicies && !policies.slaPolicies.isEmpty()) {
            existingSlaPolicyId = policies.slaPolicies.first().id
        }
    }

    def "can list SLA policies as an admin"() {
        given: "an authenticated admin client"

        when: "requesting SLA policies list"
        adminSlaPoliciesClient.listSLAPolicies().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can retrieve SLA policy filter definitions as an admin"() {
        given: "an authenticated admin client"

        when: "requesting SLA policy filter definitions"
        adminSlaPoliciesClient.retrieveSLAPolicyFilterDefinitionItems().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show SLA policy by ID as an admin"() {
        given: "an authenticated admin client and existing SLA policy ID"

        when: "requesting SLA policy by ID"
        if (existingSlaPolicyId != null) {
            adminSlaPoliciesClient.showSLAPolicy(existingSlaPolicyId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list SLA policies"(SlaPoliciesClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting SLA policies"
        client.listSLAPolicies().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentSlaPoliciesClient, "agent"],
                [userSlaPoliciesClient, "end user"]
        ]
    }

    @Unroll
    def "calling SLA policies client with #description throws HttpClientException"(
            String description, SlaPoliciesClient client) {
        when: "requesting SLA policies with invalid client configuration"
        client.listSLAPolicies().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSlaPoliciesClient
        "unreachable url" | badUrlSlaPoliciesClient
    }
}
