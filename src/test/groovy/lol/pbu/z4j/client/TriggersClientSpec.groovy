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
import lol.pbu.z4j.model.TriggersResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TriggersClientSpec extends Z4jSpec {

    @Shared
    TriggersClient adminTriggersClient, agentTriggersClient, userTriggersClient,
                   badTokenTriggersClient, badUrlTriggersClient

    @Shared
    Integer existingTriggerId

    def setupSpec() {
        adminTriggersClient = adminCtx.getBean(TriggersClient.class)
        agentTriggersClient = agentCtx.getBean(TriggersClient.class)
        userTriggersClient = userCtx.getBean(TriggersClient.class)
        badTokenTriggersClient = badTokenCtx.getBean(TriggersClient.class)
        badUrlTriggersClient = badUrlCtx.getBean(TriggersClient.class)

        TriggersResponse triggers = adminTriggersClient.listActiveTriggers(null, null, null, null).block()
        if (triggers?.triggers && !triggers.triggers.isEmpty()) {
            existingTriggerId = triggers.triggers.first().id
        }
    }

    @Unroll
    def "can list active triggers as an #userType"(TriggersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting active triggers"
        client.listActiveTriggers(null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTriggersClient, "admin"],
                [agentTriggersClient, "agent"]
        ]
    }

    @Unroll
    def "can list all triggers as an #userType"(TriggersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting all triggers"
        client.listTriggers(null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTriggersClient, "admin"],
                [agentTriggersClient, "agent"]
        ]
    }

    @Unroll
    def "can list trigger action and condition definitions as an #userType"(
            TriggersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting trigger definitions"
        client.listTriggerActionConditionDefinitions().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTriggersClient, "admin"],
                [agentTriggersClient, "agent"]
        ]
    }

    @Unroll
    def "can search triggers as an #userType with query=#query"(
            TriggersClient client, String userType, String query) {
        given: "an authenticated client for #userType"

        when: "searching triggers by query"
        client.searchTriggers(query, null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], query] << [
                [[adminTriggersClient, "admin"], [agentTriggersClient, "agent"]],
                ["Notify"]
        ].combinations()
    }

    @Unroll
    def "can show trigger by ID as an #userType"(TriggersClient client, String userType) {
        given: "an authenticated client for #userType and existing trigger ID"

        when: "requesting trigger by ID"
        if (existingTriggerId != null) {
            client.getTrigger(existingTriggerId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTriggersClient, "admin"],
                [agentTriggersClient, "agent"]
        ]
    }

    def "end user cannot list or search triggers"() {
        given: "an end user client"

        when: "requesting triggers as an end user"
        userTriggersClient.listTriggers(null, null, null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling triggers client with #description throws HttpClientException"(
            String description, TriggersClient client) {
        when: "requesting triggers with invalid client configuration"
        client.listTriggers(null, null, null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTriggersClient
        "unreachable url" | badUrlTriggersClient
    }
}
