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
import lol.pbu.z4j.model.CustomRolesResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class CustomRolesClientSpec extends Z4jSpec {

    @Shared
    CustomRolesClient adminRolesClient, agentRolesClient, userRolesClient,
                      badTokenRolesClient, badUrlRolesClient

    @Shared
    Long existingRoleId

    def setupSpec() {
        adminRolesClient = adminCtx.getBean(CustomRolesClient.class)
        agentRolesClient = agentCtx.getBean(CustomRolesClient.class)
        userRolesClient = userCtx.getBean(CustomRolesClient.class)
        badTokenRolesClient = badTokenCtx.getBean(CustomRolesClient.class)
        badUrlRolesClient = badUrlCtx.getBean(CustomRolesClient.class)

        CustomRolesResponse roles = adminRolesClient.listCustomRoles().block()
        if (roles?.customRoles && !roles.customRoles.isEmpty()) {
            existingRoleId = roles.customRoles.first().id
        }
    }

    @Unroll
    def "can list custom roles as an #userType"(
            CustomRolesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom roles list"
        client.listCustomRoles().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRolesClient, "admin"],
                [agentRolesClient, "agent"]
        ]
    }

    @Unroll
    def "can show custom role by ID as an #userType"(
            CustomRolesClient client, String userType) {
        given: "an authenticated client for #userType and existing custom role ID"

        when: "requesting custom role by ID"
        if (existingRoleId != null) {
            client.showCustomRoleById(existingRoleId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminRolesClient, "admin"],
                [agentRolesClient, "agent"]
        ]
    }

    def "end user cannot list custom roles"() {
        given: "an end user client"

        when: "requesting custom roles as an end user"
        userRolesClient.listCustomRoles().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling custom roles client with #description throws HttpClientException"(
            String description, CustomRolesClient client) {
        when: "requesting custom roles with invalid client configuration"
        client.listCustomRoles().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenRolesClient
        "unreachable url" | badUrlRolesClient
    }
}
