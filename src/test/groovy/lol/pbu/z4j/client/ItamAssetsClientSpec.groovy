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
class ItamAssetsClientSpec extends Z4jSpec {

    @Shared
    ItamAssetsClient adminItamAssetsClient, agentItamAssetsClient, userItamAssetsClient,
                     badTokenItamAssetsClient, badUrlItamAssetsClient

    def setupSpec() {
        adminItamAssetsClient = adminCtx.getBean(ItamAssetsClient.class)
        agentItamAssetsClient = agentCtx.getBean(ItamAssetsClient.class)
        userItamAssetsClient = userCtx.getBean(ItamAssetsClient.class)
        badTokenItamAssetsClient = badTokenCtx.getBean(ItamAssetsClient.class)
        badUrlItamAssetsClient = badUrlCtx.getBean(ItamAssetsClient.class)
    }

    @Unroll
    def "can list ITAM assets as an #userType"(
            ItamAssetsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ITAM assets list"
        client.listItamAssets().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminItamAssetsClient, "admin"],
                [agentItamAssetsClient, "agent"]
        ]
    }

    def "end user cannot list ITAM assets"() {
        given: "an end user client"

        when: "requesting ITAM assets as an end user"
        userItamAssetsClient.listItamAssets().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ITAM assets client with #description throws HttpClientException"(
            String description, ItamAssetsClient client) {
        when: "requesting ITAM assets with invalid client configuration"
        client.listItamAssets().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenItamAssetsClient
        "unreachable url" | badUrlItamAssetsClient
    }
}
