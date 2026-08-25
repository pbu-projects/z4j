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
class CustomObjectsClientSpec extends Z4jSpec {

    @Shared
    CustomObjectsClient adminCustomObjectsClient, agentCustomObjectsClient, userCustomObjectsClient,
                        badTokenCustomObjectsClient, badUrlCustomObjectsClient

    @Shared
    String existingObjectKey

    def setupSpec() {
        adminCustomObjectsClient = adminCtx.getBean(CustomObjectsClient.class)
        agentCustomObjectsClient = agentCtx.getBean(CustomObjectsClient.class)
        userCustomObjectsClient = userCtx.getBean(CustomObjectsClient.class)
        badTokenCustomObjectsClient = badTokenCtx.getBean(CustomObjectsClient.class)
        badUrlCustomObjectsClient = badUrlCtx.getBean(CustomObjectsClient.class)

        CustomObjectsResponse objects = adminCustomObjectsClient.listCustomObjects().block()
        if (objects?.customObjects && !objects.customObjects.isEmpty()) {
            existingObjectKey = objects.customObjects.first().key
        }
    }

    @Unroll
    def "can list custom objects as an #userType"(
            CustomObjectsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom objects list"
        client.listCustomObjects().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminCustomObjectsClient, "admin"],
                [agentCustomObjectsClient, "agent"]
        ]
    }

    def "can check custom objects limit as an admin"() {
        given: "an authenticated admin client"

        when: "requesting custom objects limit"
        adminCustomObjectsClient.customObjectsLimit().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "can show custom object by key as an #userType"(
            CustomObjectsClient client, String userType) {
        given: "an authenticated client for #userType and existing object key"

        when: "requesting custom object by key"
        if (existingObjectKey != null) {
            client.showCustomObject(existingObjectKey).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminCustomObjectsClient, "admin"],
                [agentCustomObjectsClient, "agent"]
        ]
    }

    def "end user cannot list custom objects"() {
        given: "an end user client"

        when: "requesting custom objects as an end user"
        userCustomObjectsClient.listCustomObjects().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling custom objects client with #description throws HttpClientException"(
            String description, CustomObjectsClient client) {
        when: "requesting custom objects with invalid client configuration"
        client.listCustomObjects().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenCustomObjectsClient
        "unreachable url" | badUrlCustomObjectsClient
    }
}
