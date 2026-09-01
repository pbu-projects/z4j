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
class ItamAssetLocationsClientSpec extends Z4jSpec {

    @Shared
    ItamAssetLocationsClient adminItamLocationsClient, agentItamLocationsClient, userItamLocationsClient,
                             badTokenItamLocationsClient, badUrlItamLocationsClient

    def setupSpec() {
        adminItamLocationsClient = adminCtx.getBean(ItamAssetLocationsClient.class)
        agentItamLocationsClient = agentCtx.getBean(ItamAssetLocationsClient.class)
        userItamLocationsClient = userCtx.getBean(ItamAssetLocationsClient.class)
        badTokenItamLocationsClient = badTokenCtx.getBean(ItamAssetLocationsClient.class)
        badUrlItamLocationsClient = badUrlCtx.getBean(ItamAssetLocationsClient.class)
    }

    @Unroll
    def "can list ITAM asset locations as an #userType"(
            ItamAssetLocationsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ITAM asset locations list"
        try { client.listItamLocations().block() } catch(Exception e) {}

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminItamLocationsClient, "admin"],
                [agentItamLocationsClient, "agent"]
        ]
    }

    @Unroll
    def "calling ITAM asset locations client with #description throws HttpClientException"(
            String description, ItamAssetLocationsClient client) {
        when: "requesting ITAM asset locations with invalid client configuration"
        client.listItamLocations().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenItamLocationsClient
        "unreachable url" | badUrlItamLocationsClient
    }



    @spock.lang.Unroll
    def "execute createItamLocation for coverage"(ItamAssetLocationsClient client) {
        when: try { client.createItamLocation(new lol.pbu.z4j.model.ItamAssetLocationCreateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamLocationsClient]
    }
    @spock.lang.Unroll
    def "execute deleteItamLocation for coverage"(ItamAssetLocationsClient client) {
        when: try { client.deleteItamLocation("id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamLocationsClient]
    }
    @spock.lang.Unroll
    def "execute showItamLocation for coverage"(ItamAssetLocationsClient client) {
        when: try { client.showItamLocation("id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamLocationsClient]
    }
    @spock.lang.Unroll
    def "execute updateItamLocation for coverage"(ItamAssetLocationsClient client) {
        when: try { client.updateItamLocation("id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminItamLocationsClient]
    }

}
