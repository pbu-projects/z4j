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
import lol.pbu.z4j.model.SatisfactionRatingsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class SatisfactionRatingsClientSpec extends Z4jSpec {

    @Shared
    SatisfactionRatingsClient adminSatisfactionRatingsClient, agentSatisfactionRatingsClient, userSatisfactionRatingsClient,
                              badTokenSatisfactionRatingsClient, badUrlSatisfactionRatingsClient

    @Shared
    Integer existingSatisfactionRatingId

    def setupSpec() {
        adminSatisfactionRatingsClient = adminCtx.getBean(SatisfactionRatingsClient.class)
        agentSatisfactionRatingsClient = agentCtx.getBean(SatisfactionRatingsClient.class)
        userSatisfactionRatingsClient = userCtx.getBean(SatisfactionRatingsClient.class)
        badTokenSatisfactionRatingsClient = badTokenCtx.getBean(SatisfactionRatingsClient.class)
        badUrlSatisfactionRatingsClient = badUrlCtx.getBean(SatisfactionRatingsClient.class)

        SatisfactionRatingsResponse response = adminSatisfactionRatingsClient.listSatisfactionRatings().block()
        if (response?.satisfactionRatings && !response.satisfactionRatings.isEmpty()) {
            existingSatisfactionRatingId = response.satisfactionRatings.first().id
        }
    }

    def "can count satisfaction ratings as an admin"() {
        given: "an admin client"

        when: "requesting satisfaction ratings count"
        adminSatisfactionRatingsClient.countSatisfactionRatings().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list satisfaction ratings as an admin"() {
        given: "an admin client"

        when: "requesting satisfaction ratings list"
        adminSatisfactionRatingsClient.listSatisfactionRatings().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show satisfaction rating as an admin"() {
        given: "an admin client and existing rating ID"

        when: "requesting a specific satisfaction rating by ID"
        if (existingSatisfactionRatingId != null) {
            adminSatisfactionRatingsClient.showSatisfactionRating(existingSatisfactionRatingId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "non-admin #userType cannot list satisfaction ratings"(
            SatisfactionRatingsClient client, String userType) {
        given: "a non-admin client for #userType"

        when: "requesting satisfaction ratings"
        client.listSatisfactionRatings().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentSatisfactionRatingsClient, "agent"],
                [userSatisfactionRatingsClient, "end user"]
        ]
    }

    @Unroll
    def "calling satisfaction ratings with #description throws HttpClientException"(
            String description, SatisfactionRatingsClient client) {
        when: "requesting satisfaction ratings with invalid client configuration"
        client.countSatisfactionRatings().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSatisfactionRatingsClient
        "unreachable url" | badUrlSatisfactionRatingsClient
    }
}
