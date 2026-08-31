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
import lol.pbu.z4j.model.DynamicContentsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class DynamicContentClientSpec extends Z4jSpec {

    @Shared
    DynamicContentClient adminDynamicContentClient, agentDynamicContentClient, userDynamicContentClient,
                         badTokenDynamicContentClient, badUrlDynamicContentClient

    @Shared
    Long existingItemId

    def setupSpec() {
        adminDynamicContentClient = adminCtx.getBean(DynamicContentClient.class)
        agentDynamicContentClient = agentCtx.getBean(DynamicContentClient.class)
        userDynamicContentClient = userCtx.getBean(DynamicContentClient.class)
        badTokenDynamicContentClient = badTokenCtx.getBean(DynamicContentClient.class)
        badUrlDynamicContentClient = badUrlCtx.getBean(DynamicContentClient.class)

        DynamicContentsResponse items = adminDynamicContentClient.listDynamicContents().block()
        if (items?.items && !items.items.isEmpty()) {
            existingItemId = items.items.first().id
        }
    }

    @Unroll
    def "can list dynamic content items as an #userType"(
            DynamicContentClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting dynamic content items list"
        client.listDynamicContents().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminDynamicContentClient, "admin"],
                [agentDynamicContentClient, "agent"]
        ]
    }

    @Unroll
    def "can show dynamic content item by ID as an #userType"(
            DynamicContentClient client, String userType) {
        given: "an authenticated client for #userType and existing item ID"

        when: "requesting dynamic content item by ID"
        if (existingItemId != null) {
            client.showDynamicContentItem(existingItemId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminDynamicContentClient, "admin"],
                [agentDynamicContentClient, "agent"]
        ]
    }

    @Unroll
    def "can show many dynamic contents as an #userType"(
            DynamicContentClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting many dynamic content items"
        client.showManyDynamicContents(null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminDynamicContentClient, "admin"],
                [agentDynamicContentClient, "agent"]
        ]
    }

    def "end user cannot list dynamic content items"() {
        given: "an end user client"

        when: "requesting dynamic content items as an end user"
        userDynamicContentClient.listDynamicContents().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling dynamic content client with #description throws HttpClientException"(
            String description, DynamicContentClient client) {
        when: "requesting dynamic content items with invalid client configuration"
        client.listDynamicContents().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenDynamicContentClient
        "unreachable url" | badUrlDynamicContentClient
    }
}
