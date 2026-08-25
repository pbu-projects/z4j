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

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class EssentialsCardClientSpec extends Z4jSpec {

    @Shared
    EssentialsCardClient adminEssentialsClient, agentEssentialsClient, userEssentialsClient,
                         badTokenEssentialsClient, badUrlEssentialsClient

    def setupSpec() {
        adminEssentialsClient = adminCtx.getBean(EssentialsCardClient.class)
        agentEssentialsClient = agentCtx.getBean(EssentialsCardClient.class)
        userEssentialsClient = userCtx.getBean(EssentialsCardClient.class)
        badTokenEssentialsClient = badTokenCtx.getBean(EssentialsCardClient.class)
        badUrlEssentialsClient = badUrlCtx.getBean(EssentialsCardClient.class)
    }

    def "can show essentials cards as an admin"() {
        given: "an authenticated admin client"

        when: "requesting essentials cards list"
        adminEssentialsClient.showEssentialsCards().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot show essentials cards"(EssentialsCardClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting essentials cards"
        client.showEssentialsCards().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentEssentialsClient, "agent"],
                [userEssentialsClient, "end user"]
        ]
    }

    @Unroll
    def "calling essentials card client with #description throws HttpClientException"(
            String description, EssentialsCardClient client) {
        when: "requesting essentials cards with invalid client configuration"
        client.showEssentialsCards().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenEssentialsClient
        "unreachable url" | badUrlEssentialsClient
    }
}
