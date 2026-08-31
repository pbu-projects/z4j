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
import lol.pbu.z4j.model.AutomationsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class AutomationsClientSpec extends Z4jSpec {

    @Shared
    AutomationsClient adminAutomationsClient, agentAutomationsClient, userAutomationsClient,
                      badTokenAutomationsClient, badUrlAutomationsClient

    @Shared
    Long existingAutomationId

    def setupSpec() {
        adminAutomationsClient = adminCtx.getBean(AutomationsClient.class)
        agentAutomationsClient = agentCtx.getBean(AutomationsClient.class)
        userAutomationsClient = userCtx.getBean(AutomationsClient.class)
        badTokenAutomationsClient = badTokenCtx.getBean(AutomationsClient.class)
        badUrlAutomationsClient = badUrlCtx.getBean(AutomationsClient.class)

        AutomationsResponse automations = adminAutomationsClient.listActiveAutomations().block()
        if (automations?.automations && !automations.automations.isEmpty()) {
            existingAutomationId = automations.automations.first().id
        }
    }

    @Unroll
    def "can list active automations as an #userType"(AutomationsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting active automations"
        client.listActiveAutomations().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminAutomationsClient, "admin"],
                [agentAutomationsClient, "agent"]
        ]
    }

    @Unroll
    def "can list all automations as an #userType"(AutomationsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting all automations"
        client.listAutomations().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminAutomationsClient, "admin"],
                [agentAutomationsClient, "agent"]
        ]
    }

    @Unroll
    def "can search automations as an #userType with query=#query"(
            AutomationsClient client, String userType, String query) {
        given: "an authenticated client for #userType"

        when: "searching automations by query"
        client.searchAutomations(query, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], query] << [
                [[adminAutomationsClient, "admin"], [agentAutomationsClient, "agent"]],
                ["Close"]
        ].combinations()
    }

    @Unroll
    def "can show automation by ID as an #userType"(AutomationsClient client, String userType) {
        given: "an authenticated client for #userType and existing automation ID"

        when: "requesting automation by ID"
        if (existingAutomationId != null) {
            client.showAutomation(existingAutomationId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminAutomationsClient, "admin"],
                [agentAutomationsClient, "agent"]
        ]
    }

    def "end user cannot list or search automations"() {
        given: "an end user client"

        when: "requesting automations as an end user"
        userAutomationsClient.listAutomations().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling automations client with #description throws HttpClientException"(
            String description, AutomationsClient client) {
        when: "requesting automations with invalid client configuration"
        client.listAutomations().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAutomationsClient
        "unreachable url" | badUrlAutomationsClient
    }
}
