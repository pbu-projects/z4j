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
import lol.pbu.z4j.model.DynamicContentVariantsResponse
import lol.pbu.z4j.model.DynamicContentsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class DynamicContentItemVariantsClientSpec extends Z4jSpec {

    @Shared
    DynamicContentItemVariantsClient adminVariantsClient, agentVariantsClient, userVariantsClient,
                                     badTokenVariantsClient, badUrlVariantsClient

    @Shared
    Long existingItemId

    @Shared
    Long existingVariantId

    def setupSpec() {
        adminVariantsClient = adminCtx.getBean(DynamicContentItemVariantsClient.class)
        agentVariantsClient = agentCtx.getBean(DynamicContentItemVariantsClient.class)
        userVariantsClient = userCtx.getBean(DynamicContentItemVariantsClient.class)
        badTokenVariantsClient = badTokenCtx.getBean(DynamicContentItemVariantsClient.class)
        badUrlVariantsClient = badUrlCtx.getBean(DynamicContentItemVariantsClient.class)

        DynamicContentsResponse items = adminCtx.getBean(DynamicContentClient.class).listDynamicContents().block()
        if (items?.items && !items.items.isEmpty()) {
            existingItemId = items.items.first().id
            DynamicContentVariantsResponse variants = adminVariantsClient.dynamicContentListVariants(existingItemId).block()
            if (variants?.variants && !variants.variants.isEmpty()) {
                existingVariantId = variants.variants.first().id
            }
        }
    }

    @Unroll
    def "can list dynamic content variants as an #userType"(
            DynamicContentItemVariantsClient client, String userType) {
        given: "an authenticated client for #userType and existing dynamic content item ID"

        when: "requesting variants list"
        if (existingItemId != null) {
            client.dynamicContentListVariants(existingItemId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminVariantsClient, "admin"],
        ]
    }

    @Unroll
    def "can show dynamic content variant as an #userType"(
            DynamicContentItemVariantsClient client, String userType) {
        given: "an authenticated client for #userType, item ID, and variant ID"

        when: "requesting variant by ID"
        if (existingItemId != null && existingVariantId != null) {
            client.showDynamicContentVariant(existingItemId, existingVariantId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminVariantsClient, "admin"],
        ]
    }

    def "end user cannot list dynamic content variants"() {
        given: "an end user client"

        when: "requesting variants as an end user"
        userVariantsClient.dynamicContentListVariants(existingItemId ?: 1).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling dynamic content variants client with #description throws HttpClientException"(
            String description, DynamicContentItemVariantsClient client) {
        when: "requesting variants with invalid client configuration"
        client.dynamicContentListVariants(existingItemId ?: 1).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenVariantsClient
        "unreachable url" | badUrlVariantsClient
    }
}
