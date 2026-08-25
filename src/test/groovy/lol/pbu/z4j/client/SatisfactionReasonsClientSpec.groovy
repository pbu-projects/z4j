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
class SatisfactionReasonsClientSpec extends Z4jSpec {

    @Shared
    SatisfactionReasonsClient adminCsatReasonsClient, agentCsatReasonsClient, userCsatReasonsClient,
                              badTokenCsatReasonsClient, badUrlCsatReasonsClient

    @Shared
    Integer existingReasonId

    def setupSpec() {
        adminCsatReasonsClient = adminCtx.getBean(SatisfactionReasonsClient.class)
        agentCsatReasonsClient = agentCtx.getBean(SatisfactionReasonsClient.class)
        userCsatReasonsClient = userCtx.getBean(SatisfactionReasonsClient.class)
        badTokenCsatReasonsClient = badTokenCtx.getBean(SatisfactionReasonsClient.class)
        badUrlCsatReasonsClient = badUrlCtx.getBean(SatisfactionReasonsClient.class)

        def reasons = adminCsatReasonsClient.listSatisfactionRatingReasons().block()
        if (reasons?.reasons && !reasons.reasons.isEmpty()) {
            existingReasonId = reasons.reasons.first().id
        }
    }

    def "can list satisfaction rating reasons as an admin"() {
        given: "an authenticated admin client"

        when: "requesting satisfaction rating reasons list"
        adminCsatReasonsClient.listSatisfactionRatingReasons().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show satisfaction reason by ID as an admin"() {
        given: "an authenticated admin client and existing reason ID"

        when: "requesting satisfaction reason by ID"
        if (existingReasonId != null) {
            adminCsatReasonsClient.showSatisfactionRatings(existingReasonId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list satisfaction rating reasons"(SatisfactionReasonsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting satisfaction rating reasons"
        client.listSatisfactionRatingReasons().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentCsatReasonsClient, "agent"],
                [userCsatReasonsClient, "end user"]
        ]
    }

    @Unroll
    def "calling satisfaction rating reasons client with #description throws HttpClientException"(
            String description, SatisfactionReasonsClient client) {
        when: "requesting satisfaction rating reasons with invalid client configuration"
        client.listSatisfactionRatingReasons().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenCsatReasonsClient
        "unreachable url" | badUrlCsatReasonsClient
    }
}
