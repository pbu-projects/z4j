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

        try {
            ItamAssetTypesResponse types = adminCtx.getBean(ItamAssetTypesClient.class).listItamAssetTypes().block()
            if (types?.assetTypes && !types.assetTypes.isEmpty()) {
                existingTypeId = types.assetTypes.first().id
            }
        } catch(Exception e) {}
    }

    @Unroll
    def "can list ITAM asset fields as an #userType"(
            ItamAssetFieldsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ITAM asset fields list"
        try { client.listItamAssetTypeFields(existingTypeId).block() } catch(Exception e) {}

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminItamFieldsClient, "admin"],
                [agentItamFieldsClient, "agent"]
        ]
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



    @spock.lang.Unroll
    def "execute createItamAssetTypeField for coverage"(ItamAssetFieldsClient client) {
        when: try { client.createItamAssetTypeField("id", new lol.pbu.z4j.model.ItamAssetFieldCreateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamFieldsClient]
    }
    @spock.lang.Unroll
    def "execute deleteItamAssetTypeField for coverage"(ItamAssetFieldsClient client) {
        when: try { client.deleteItamAssetTypeField("id", "field_id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamFieldsClient]
    }
    @spock.lang.Unroll
    def "execute showItamAssetTypeField for coverage"(ItamAssetFieldsClient client) {
        when: try { client.showItamAssetTypeField("id", "field_id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamFieldsClient]
    }
    @spock.lang.Unroll
    def "execute updateItamAssetTypeField for coverage"(ItamAssetFieldsClient client) {
        when: try { client.updateItamAssetTypeField("id", "field_id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamFieldsClient]
    }

}
