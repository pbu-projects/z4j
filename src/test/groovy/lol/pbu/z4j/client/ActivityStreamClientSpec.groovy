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
import lol.pbu.z4j.model.ActivitiesResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class ActivityStreamClientSpec extends Z4jSpec {

    @Shared
    ActivityStreamClient adminActivityStreamClient, agentActivityStreamClient, userActivityStreamClient,
                         badTokenActivityStreamClient, badUrlActivityStreamClient

    @Shared
    Integer existingActivityId

    def setupSpec() {
        adminActivityStreamClient = adminCtx.getBean(ActivityStreamClient.class)
        agentActivityStreamClient = agentCtx.getBean(ActivityStreamClient.class)
        userActivityStreamClient = userCtx.getBean(ActivityStreamClient.class)
        badTokenActivityStreamClient = badTokenCtx.getBean(ActivityStreamClient.class)
        badUrlActivityStreamClient = badUrlCtx.getBean(ActivityStreamClient.class)

        ActivitiesResponse response = adminActivityStreamClient.listActivities(null).block()
        if (response?.activities && !response.activities.isEmpty()) {
            existingActivityId = response.activities.first().id
        }
    }

    @Unroll
    def "can count activities as an #userType"(ActivityStreamClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting activity count"
        client.countActivities().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminActivityStreamClient, "admin"],
                [agentActivityStreamClient, "agent"]
        ]
    }

    @Unroll
    def "can list activities as an #userType with since=#since"(
            ActivityStreamClient client, String userType, String since) {
        given: "an authenticated client for #userType"

        when: "requesting activities list"
        client.listActivities(since).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], since] << [
                [[adminActivityStreamClient, "admin"], [agentActivityStreamClient, "agent"]],
                [null]
        ].combinations()
    }

    @Unroll
    def "can show activity as an #userType"(ActivityStreamClient client, String userType) {
        given: "an authenticated client for #userType and existing activity ID"

        when: "requesting a specific activity by ID"
        if (existingActivityId != null) {
            client.showActivity(existingActivityId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminActivityStreamClient, "admin"],
                [agentActivityStreamClient, "agent"]
        ]
    }

    def "end user cannot count activities"() {
        given: "an end user client"

        when: "requesting activity count as an end user"
        userActivityStreamClient.countActivities().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    def "end user cannot list activities"() {
        given: "an end user client"

        when: "requesting activities list as an end user"
        userActivityStreamClient.listActivities(null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling activity stream with #description throws HttpClientException"(
            String description, ActivityStreamClient client) {
        when: "requesting activity stream with invalid client configuration"
        client.countActivities().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenActivityStreamClient
        "unreachable url" | badUrlActivityStreamClient
    }
}
