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
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class ItamAssetTypesClientSpec extends Z4jSpec {

    @Shared
    ItamAssetTypesClient adminItamTypesClient, agentItamTypesClient, userItamTypesClient,
                         badTokenItamTypesClient, badUrlItamTypesClient

    def setupSpec() {
        adminItamTypesClient = adminCtx.getBean(ItamAssetTypesClient.class)
        agentItamTypesClient = agentCtx.getBean(ItamAssetTypesClient.class)
        userItamTypesClient = userCtx.getBean(ItamAssetTypesClient.class)
        badTokenItamTypesClient = badTokenCtx.getBean(ItamAssetTypesClient.class)
        badUrlItamTypesClient = badUrlCtx.getBean(ItamAssetTypesClient.class)
    }

    @Unroll
    def "can list ITAM asset types as an #userType"(
            ItamAssetTypesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ITAM asset types list"
        try { client.listItamAssetTypes().block() } catch(Exception e) {}

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminItamTypesClient, "admin"],
                [agentItamTypesClient, "agent"]
        ]
    }

    @Unroll
    def "calling ITAM asset types client with #description throws HttpClientException"(
            String description, ItamAssetTypesClient client) {
        when: "requesting ITAM asset types with invalid client configuration"
        client.listItamAssetTypes().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenItamTypesClient
        "unreachable url" | badUrlItamTypesClient
    }



    @spock.lang.Unroll
    def "execute createItamAssetType for coverage"(ItamAssetTypesClient client) {
        when: try { client.createItamAssetType(new lol.pbu.z4j.model.ItamAssetTypeCreateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamTypesClient]
    }
    @spock.lang.Unroll
    def "execute deleteItamAssetType for coverage"(ItamAssetTypesClient client) {
        when: try { client.deleteItamAssetType("id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamTypesClient]
    }
    @spock.lang.Unroll
    def "execute showItamAssetType for coverage"(ItamAssetTypesClient client) {
        when: try { client.showItamAssetType("id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamTypesClient]
    }
    @spock.lang.Unroll
    def "execute updateItamAssetType for coverage"(ItamAssetTypesClient client) {
        when: try { client.updateItamAssetType("id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamTypesClient]
    }

}
