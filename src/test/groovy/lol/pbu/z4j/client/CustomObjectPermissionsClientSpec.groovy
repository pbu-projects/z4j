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
import lol.pbu.z4j.model.CustomObjectsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class CustomObjectPermissionsClientSpec extends Z4jSpec {

    @Shared
    CustomObjectPermissionsClient adminPermissionsClient, agentPermissionsClient, userPermissionsClient,
                                  badTokenPermissionsClient, badUrlPermissionsClient

    @Shared
    String customObjectKey = "zen:ticket"

    def setupSpec() {
        adminPermissionsClient = adminCtx.getBean(CustomObjectPermissionsClient.class)
        agentPermissionsClient = agentCtx.getBean(CustomObjectPermissionsClient.class)
        userPermissionsClient = userCtx.getBean(CustomObjectPermissionsClient.class)
        badTokenPermissionsClient = badTokenCtx.getBean(CustomObjectPermissionsClient.class)
        badUrlPermissionsClient = badUrlCtx.getBean(CustomObjectPermissionsClient.class)

        CustomObjectsResponse customObjects = adminCtx.getBean(CustomObjectsClient.class).listCustomObjects().block()
        if (customObjects?.customObjects && !customObjects.customObjects.isEmpty()) {
            customObjectKey = customObjects.customObjects.first().key
        }
    }

    def "can list access rules as an admin"() {
        given: "an authenticated admin client"

        when: "requesting access rules list"
        adminPermissionsClient.listAccessRules(customObjectKey).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list access rule definitions as an admin"() {
        given: "an authenticated admin client"

        when: "requesting access rule definitions list"
        adminPermissionsClient.listAccessRuleDefinitions(customObjectKey).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list permission policies as an admin"() {
        given: "an authenticated admin client"

        when: "requesting permission policies list"
        adminPermissionsClient.listPermissionPolicies(customObjectKey).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list access rules"(CustomObjectPermissionsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting access rules"
        client.listAccessRules(customObjectKey).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentPermissionsClient, "agent"],
                [userPermissionsClient, "end user"]
        ]
    }

    @Unroll
    def "calling custom object permissions client with #description throws HttpClientException"(
            String description, CustomObjectPermissionsClient client) {
        when: "requesting access rules with invalid client configuration"
        client.listAccessRules(customObjectKey).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenPermissionsClient
        "unreachable url" | badUrlPermissionsClient
    }
}
