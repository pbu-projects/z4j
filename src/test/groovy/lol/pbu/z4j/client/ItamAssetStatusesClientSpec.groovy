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
class ItamAssetStatusesClientSpec extends Z4jSpec {

    @Shared
    ItamAssetStatusesClient adminItamStatusesClient, agentItamStatusesClient, userItamStatusesClient,
                            badTokenItamStatusesClient, badUrlItamStatusesClient

    @Shared
    String existingStatusId

    def setupSpec() {
        adminItamStatusesClient = adminCtx.getBean(ItamAssetStatusesClient.class)
        agentItamStatusesClient = agentCtx.getBean(ItamAssetStatusesClient.class)
        userItamStatusesClient = userCtx.getBean(ItamAssetStatusesClient.class)
        badTokenItamStatusesClient = badTokenCtx.getBean(ItamAssetStatusesClient.class)
        badUrlItamStatusesClient = badUrlCtx.getBean(ItamAssetStatusesClient.class)

        def statuses = adminItamStatusesClient.listItamStatuses().block()
        if (statuses?.statuses && !statuses.statuses.isEmpty()) {
            existingStatusId = statuses.statuses.first().id
        }
    }

    @Unroll
    def "can list ITAM asset statuses as an #userType"(
            ItamAssetStatusesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ITAM asset statuses list"
        client.listItamStatuses().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminItamStatusesClient, "admin"],
                [agentItamStatusesClient, "agent"]
        ]
    }

    @Unroll
    def "can show ITAM asset status by ID as an #userType"(
            ItamAssetStatusesClient client, String userType) {
        given: "an authenticated client for #userType and existing status ID"

        when: "requesting ITAM asset status by ID"
        if (existingStatusId != null) {
            client.showItamStatus(existingStatusId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminItamStatusesClient, "admin"],
                [agentItamStatusesClient, "agent"]
        ]
    }

    def "end user cannot list ITAM asset statuses"() {
        given: "an end user client"

        when: "requesting ITAM asset statuses as an end user"
        userItamStatusesClient.listItamStatuses().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ITAM asset statuses client with #description throws HttpClientException"(
            String description, ItamAssetStatusesClient client) {
        when: "requesting ITAM asset statuses with invalid client configuration"
        client.listItamStatuses().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenItamStatusesClient
        "unreachable url" | badUrlItamStatusesClient
    }
}
