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
import lol.pbu.z4j.model.ItamAssetTypesResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
@spock.lang.Ignore("ITAM not enabled in Sandbox")
class ItamAssetFieldsClientSpec extends Z4jSpec {

    @Shared
    ItamAssetFieldsClient adminItamFieldsClient, agentItamFieldsClient, userItamFieldsClient,
                          badTokenItamFieldsClient, badUrlItamFieldsClient

    @Shared
    String existingTypeId = "default"

    def setupSpec() {
        adminItamFieldsClient = adminCtx.getBean(ItamAssetFieldsClient.class)
        agentItamFieldsClient = agentCtx.getBean(ItamAssetFieldsClient.class)
        userItamFieldsClient = userCtx.getBean(ItamAssetFieldsClient.class)
        badTokenItamFieldsClient = badTokenCtx.getBean(ItamAssetFieldsClient.class)
        badUrlItamFieldsClient = badUrlCtx.getBean(ItamAssetFieldsClient.class)

        ItamAssetTypesResponse types = adminCtx.getBean(ItamAssetTypesClient.class).listItamAssetTypes().block()
        if (types?.assetTypes && !types.assetTypes.isEmpty()) {
            existingTypeId = types.assetTypes.first().id
        }
    }

    @Unroll
    def "can list ITAM asset fields as an #userType"(
            ItamAssetFieldsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ITAM asset fields list"
        client.listItamAssetTypeFields(existingTypeId).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminItamFieldsClient, "admin"],
                [agentItamFieldsClient, "agent"]
        ]
    }

    def "end user cannot list ITAM asset fields"() {
        given: "an end user client"

        when: "requesting ITAM asset fields as an end user"
        userItamFieldsClient.listItamAssetTypeFields(existingTypeId).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ITAM asset fields client with #description throws HttpClientException"(
            String description, ItamAssetFieldsClient client) {
        when: "requesting ITAM asset fields with invalid client configuration"
        client.listItamAssetTypeFields(existingTypeId).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenItamFieldsClient
        "unreachable url" | badUrlItamFieldsClient
    }
}
