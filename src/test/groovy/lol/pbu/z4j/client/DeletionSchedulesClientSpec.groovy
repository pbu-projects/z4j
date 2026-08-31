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
class DeletionSchedulesClientSpec extends Z4jSpec {

    @Shared
    DeletionSchedulesClient adminSchedulesClient, agentSchedulesClient, userSchedulesClient,
                            badTokenSchedulesClient, badUrlSchedulesClient

    def setupSpec() {
        adminSchedulesClient = adminCtx.getBean(DeletionSchedulesClient.class)
        agentSchedulesClient = agentCtx.getBean(DeletionSchedulesClient.class)
        userSchedulesClient = userCtx.getBean(DeletionSchedulesClient.class)
        badTokenSchedulesClient = badTokenCtx.getBean(DeletionSchedulesClient.class)
        badUrlSchedulesClient = badUrlCtx.getBean(DeletionSchedulesClient.class)
    }

    def "can list deletion schedules as an admin"() {
        given: "an authenticated admin client"

        when: "requesting deletion schedules list"
        adminSchedulesClient.listDeletionSchedules().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list deletion schedules"(DeletionSchedulesClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting deletion schedules"
        client.listDeletionSchedules().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentSchedulesClient, "agent"],
                [userSchedulesClient, "end user"]
        ]
    }

    @Unroll
    def "calling deletion schedules client with #description throws HttpClientException"(
            String description, DeletionSchedulesClient client) {
        when: "requesting deletion schedules with invalid client configuration"
        client.listDeletionSchedules().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSchedulesClient
        "unreachable url" | badUrlSchedulesClient
    }
}
