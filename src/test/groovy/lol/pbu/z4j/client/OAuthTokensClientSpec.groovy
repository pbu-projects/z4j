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
@spock.lang.Ignore("Sandbox limitations")
class OAuthTokensClientSpec extends Z4jSpec {

    @Shared
    OAuthTokensClient adminOAuthTokensClient, agentOAuthTokensClient, userOAuthTokensClient,
                      badTokenOAuthTokensClient, badUrlOAuthTokensClient

    def setupSpec() {
        adminOAuthTokensClient = adminCtx.getBean(OAuthTokensClient.class)
        agentOAuthTokensClient = agentCtx.getBean(OAuthTokensClient.class)
        userOAuthTokensClient = userCtx.getBean(OAuthTokensClient.class)
        badTokenOAuthTokensClient = badTokenCtx.getBean(OAuthTokensClient.class)
        badUrlOAuthTokensClient = badUrlCtx.getBean(OAuthTokensClient.class)
    }

    def "can list OAuth tokens as an admin"() {
        given: "an authenticated admin client"

        when: "requesting OAuth tokens list"
        adminOAuthTokensClient.listOAuthTokens(null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list OAuth tokens"(OAuthTokensClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting OAuth tokens"
        client.listOAuthTokens(null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentOAuthTokensClient, "agent"],
                [userOAuthTokensClient, "end user"]
        ]
    }

    @Unroll
    def "calling OAuth tokens client with #description throws HttpClientException"(
            String description, OAuthTokensClient client) {
        when: "requesting OAuth tokens with invalid client configuration"
        client.listOAuthTokens(null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenOAuthTokensClient
        "unreachable url" | badUrlOAuthTokensClient
    }
}
