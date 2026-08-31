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
import lol.pbu.z4j.model.TargetsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class TargetsClientSpec extends Z4jSpec {

    @Shared
    TargetsClient adminTargetsClient, agentTargetsClient, userTargetsClient,
                  badTokenTargetsClient, badUrlTargetsClient

    @Shared
    Long existingTargetId

    def setupSpec() {
        adminTargetsClient = adminCtx.getBean(TargetsClient.class)
        agentTargetsClient = agentCtx.getBean(TargetsClient.class)
        userTargetsClient = userCtx.getBean(TargetsClient.class)
        badTokenTargetsClient = badTokenCtx.getBean(TargetsClient.class)
        badUrlTargetsClient = badUrlCtx.getBean(TargetsClient.class)

        TargetsResponse targets = adminTargetsClient.listTargets().block()
        if (targets?.targets && !targets.targets.isEmpty()) {
            existingTargetId = targets.targets.first().id
        }
    }

    @Unroll
    def "can list targets as an #userType"(
            TargetsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting targets list"
        client.listTargets().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTargetsClient, "admin"],
                [agentTargetsClient, "agent"]
        ]
    }

    @Unroll
    def "can show target by ID as an #userType"(
            TargetsClient client, String userType) {
        given: "an authenticated client for #userType and existing target ID"

        when: "requesting target by ID"
        if (existingTargetId != null) {
            client.showTarget(existingTargetId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTargetsClient, "admin"],
                [agentTargetsClient, "agent"]
        ]
    }

    def "end user cannot list targets"() {
        given: "an end user client"

        when: "requesting targets as an end user"
        userTargetsClient.listTargets().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling targets client with #description throws HttpClientException"(
            String description, TargetsClient client) {
        when: "requesting targets with invalid client configuration"
        client.listTargets().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTargetsClient
        "unreachable url" | badUrlTargetsClient
    }
}
