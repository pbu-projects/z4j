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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("admin")
class ResellerClientSpec extends Z4jSpec {

    @Shared
    ResellerClient adminResellerClient, agentResellerClient, userResellerClient,
                   badTokenResellerClient, badUrlResellerClient

    def setupSpec() {
        adminResellerClient = adminCtx.getBean(ResellerClient.class)
        agentResellerClient = agentCtx.getBean(ResellerClient.class)
        userResellerClient = userCtx.getBean(ResellerClient.class)
        badTokenResellerClient = badTokenCtx.getBean(ResellerClient.class)
        badUrlResellerClient = badUrlCtx.getBean(ResellerClient.class)
    }

    @Unroll
    def "can verify subdomain availability as #userType"(
            ResellerClient client, String userType) {
        given: "an authenticated client for #userType"
        String randomSubdomain = "pbutest" + UUID.randomUUID().toString().replace("-", "").substring(0, 8)

        when: "verifying subdomain availability"
        client.verifySubdomainAvailability(randomSubdomain).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminResellerClient, "admin"],
                [agentResellerClient, "agent"],
                [userResellerClient, "end user"]
        ]
    }

    @Unroll
    def "calling reseller client with #description throws HttpClientException"(
            String description, ResellerClient client) {
        given:
        String randomSubdomain = "pbutest" + UUID.randomUUID().toString().replace("-", "").substring(0, 8)

        when: "verifying subdomain availability with invalid client configuration"
        client.verifySubdomainAvailability(randomSubdomain).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenResellerClient
        "unreachable url" | badUrlResellerClient
    }
}
