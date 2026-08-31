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
import lol.pbu.z4j.model.CustomObjectsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class CustomObjectFieldsClientSpec extends Z4jSpec {

    @Shared
    CustomObjectFieldsClient adminObjectFieldsClient, agentObjectFieldsClient, userObjectFieldsClient,
                             badTokenObjectFieldsClient, badUrlObjectFieldsClient

    @Shared
    String customObjectKey = "zen:ticket"

    def setupSpec() {
        adminObjectFieldsClient = adminCtx.getBean(CustomObjectFieldsClient.class)
        agentObjectFieldsClient = agentCtx.getBean(CustomObjectFieldsClient.class)
        userObjectFieldsClient = userCtx.getBean(CustomObjectFieldsClient.class)
        badTokenObjectFieldsClient = badTokenCtx.getBean(CustomObjectFieldsClient.class)
        badUrlObjectFieldsClient = badUrlCtx.getBean(CustomObjectFieldsClient.class)

        CustomObjectsResponse customObjects = adminCtx.getBean(CustomObjectsClient.class).listCustomObjects().block()
        if (customObjects?.customObjects && !customObjects.customObjects.isEmpty()) {
            customObjectKey = customObjects.customObjects.first().key
        }
    }

    @Unroll
    def "can list custom object fields as an #userType"(
            CustomObjectFieldsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object fields list"
        client.listCustomObjectFields(customObjectKey, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectFieldsClient, "admin"],
                [agentObjectFieldsClient, "agent"]
        ]
    }

    @Unroll
    def "can get custom object fields limit as an #userType"(
            CustomObjectFieldsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object fields limit"
        client.customObjectFieldsLimit(customObjectKey).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectFieldsClient, "admin"],
                [agentObjectFieldsClient, "agent"]
        ]
    }

    def "end user cannot list custom object fields"() {
        given: "an end user client"

        when: "requesting custom object fields as an end user"
        userObjectFieldsClient.listCustomObjectFields(customObjectKey, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling custom object fields client with #description throws HttpClientException"(
            String description, CustomObjectFieldsClient client) {
        when: "requesting custom object fields with invalid client configuration"
        client.listCustomObjectFields(customObjectKey, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenObjectFieldsClient
        "unreachable url" | badUrlObjectFieldsClient
    }
}
