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
class SatisfactionRatingsClientSpec extends Z4jSpec {

    @Shared
    SatisfactionRatingsClient adminCsatClient, agentCsatClient, userCsatClient,
                              badTokenCsatClient, badUrlCsatClient

    @Shared
    Integer existingRatingId

    def setupSpec() {
        adminCsatClient = adminCtx.getBean(SatisfactionRatingsClient.class)
        agentCsatClient = agentCtx.getBean(SatisfactionRatingsClient.class)
        userCsatClient = userCtx.getBean(SatisfactionRatingsClient.class)
        badTokenCsatClient = badTokenCtx.getBean(SatisfactionRatingsClient.class)
        badUrlCsatClient = badUrlCtx.getBean(SatisfactionRatingsClient.class)

        def ratings = adminCsatClient.listSatisfactionRatings().block()
        if (ratings?.satisfactionRatings && !ratings.satisfactionRatings.isEmpty()) {
            existingRatingId = ratings.satisfactionRatings.first().id
        }
    }

    def "can count satisfaction ratings as an admin"() {
        given: "an authenticated admin client"

        when: "requesting satisfaction ratings count"
        adminCsatClient.countSatisfactionRatings().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list satisfaction ratings as an admin"() {
        given: "an authenticated admin client"

        when: "requesting satisfaction ratings list"
        adminCsatClient.listSatisfactionRatings().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show satisfaction rating by ID as an admin"() {
        given: "an authenticated admin client and existing rating ID"

        when: "requesting satisfaction rating by ID"
        if (existingRatingId != null) {
            adminCsatClient.showSatisfactionRating(existingRatingId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list satisfaction ratings"(SatisfactionRatingsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting satisfaction ratings"
        client.listSatisfactionRatings().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentCsatClient, "agent"],
                [userCsatClient, "end user"]
        ]
    }

    @Unroll
    def "calling satisfaction ratings client with #description throws HttpClientException"(
            String description, SatisfactionRatingsClient client) {
        when: "requesting satisfaction ratings with invalid client configuration"
        client.listSatisfactionRatings().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenCsatClient
        "unreachable url" | badUrlCsatClient
    }
}
