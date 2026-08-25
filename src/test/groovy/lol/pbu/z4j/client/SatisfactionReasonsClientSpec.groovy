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
import lol.pbu.z4j.model.SatisfactionReasonsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class SatisfactionReasonsClientSpec extends Z4jSpec {

    @Shared
    SatisfactionReasonsClient adminSatisfactionReasonsClient, agentSatisfactionReasonsClient, userSatisfactionReasonsClient,
                              badTokenSatisfactionReasonsClient, badUrlSatisfactionReasonsClient

    @Shared
    Integer existingReasonId

    def setupSpec() {
        adminSatisfactionReasonsClient = adminCtx.getBean(SatisfactionReasonsClient.class)
        agentSatisfactionReasonsClient = agentCtx.getBean(SatisfactionReasonsClient.class)
        userSatisfactionReasonsClient = userCtx.getBean(SatisfactionReasonsClient.class)
        badTokenSatisfactionReasonsClient = badTokenCtx.getBean(SatisfactionReasonsClient.class)
        badUrlSatisfactionReasonsClient = badUrlCtx.getBean(SatisfactionReasonsClient.class)

        SatisfactionReasonsResponse response = adminSatisfactionReasonsClient.listSatisfactionRatingReasons().block()
        if (response?.reasons && !response.reasons.isEmpty()) {
            existingReasonId = response.reasons.first().id
        }
    }

    def "can list satisfaction rating reasons as an admin"() {
        given: "an admin client"

        when: "requesting satisfaction reasons"
        adminSatisfactionReasonsClient.listSatisfactionRatingReasons().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show satisfaction rating reason as an admin"() {
        given: "an admin client and existing reason ID"

        when: "requesting a satisfaction reason by ID"
        if (existingReasonId != null) {
            adminSatisfactionReasonsClient.showSatisfactionRatings(existingReasonId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "non-admin #userType cannot list satisfaction rating reasons"(
            SatisfactionReasonsClient client, String userType) {
        given: "a non-admin client for #userType"

        when: "requesting satisfaction reasons"
        client.listSatisfactionRatingReasons().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentSatisfactionReasonsClient, "agent"],
                [userSatisfactionReasonsClient, "end user"]
        ]
    }

    @Unroll
    def "calling satisfaction reasons with #description throws HttpClientException"(
            String description, SatisfactionReasonsClient client) {
        when: "requesting satisfaction reasons with invalid client configuration"
        client.listSatisfactionRatingReasons().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSatisfactionReasonsClient
        "unreachable url" | badUrlSatisfactionReasonsClient
    }
}
