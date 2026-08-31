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
class SkillBasedRoutingClientSpec extends Z4jSpec {

    @Shared
    SkillBasedRoutingClient adminRoutingClient, agentRoutingClient, userRoutingClient,
                            badTokenRoutingClient, badUrlRoutingClient

    @Shared
    String existingAttributeId

    @Shared
    Long adminUserId

    def setupSpec() {
        adminRoutingClient = adminCtx.getBean(SkillBasedRoutingClient.class)
        agentRoutingClient = agentCtx.getBean(SkillBasedRoutingClient.class)
        userRoutingClient = userCtx.getBean(SkillBasedRoutingClient.class)
        badTokenRoutingClient = badTokenCtx.getBean(SkillBasedRoutingClient.class)
        badUrlRoutingClient = badUrlCtx.getBean(SkillBasedRoutingClient.class)

        def me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id

        def attributes = adminRoutingClient.listAccountAttributes().block()
        if (attributes?.attributes && !attributes.attributes.isEmpty()) {
            existingAttributeId = attributes.attributes.first().id
        }
    }

    @Unroll
    def "can list account attributes as an #userType"(
            SkillBasedRoutingClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting account attributes list"
        client.listAccountAttributes().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRoutingClient, "admin"],
                [agentRoutingClient, "agent"]
        ]
    }

    @Unroll
    def "can show attribute by ID as an #userType"(
            SkillBasedRoutingClient client, String userType) {
        given: "an authenticated client for #userType and existing attribute ID"

        when: "requesting attribute by ID"
        if (existingAttributeId != null) {
            client.showAttribute(existingAttributeId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRoutingClient, "admin"]
        ]
    }

    @Unroll
    def "can list attribute values as an #userType"(
            SkillBasedRoutingClient client, String userType) {
        given: "an authenticated client for #userType and existing attribute ID"

        when: "requesting attribute values"
        if (existingAttributeId != null) {
            client.listAttributeValues(existingAttributeId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRoutingClient, "admin"],
                [agentRoutingClient, "agent"]
        ]
    }

    @Unroll
    def "can list agent attribute values as an #userType"(
            SkillBasedRoutingClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting agent attribute values"
        if (adminUserId != null) {
            client.listAGentAttributeValues(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRoutingClient, "admin"],
                [agentRoutingClient, "agent"]
        ]
    }

    def "can list routing attribute definitions as an admin"() {
        given: "an authenticated admin client"

        when: "requesting routing attribute definitions"
        adminRoutingClient.listRoutingAttributeDefinitions().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "end user cannot list account attributes"() {
        given: "an end user client"

        when: "requesting account attributes as an end user"
        userRoutingClient.listAccountAttributes().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling skill based routing client with #description throws HttpClientException"(
            String description, SkillBasedRoutingClient client) {
        when: "requesting account attributes with invalid client configuration"
        client.listAccountAttributes().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenRoutingClient
        "unreachable url" | badUrlRoutingClient
    }
}
