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
class IncrementalExportClientSpec extends Z4jSpec {

    @Shared
    IncrementalExportClient adminExportClient, agentExportClient, userExportClient,
                            badTokenExportClient, badUrlExportClient

    def setupSpec() {
        adminExportClient = adminCtx.getBean(IncrementalExportClient.class)
        agentExportClient = agentCtx.getBean(IncrementalExportClient.class)
        userExportClient = userCtx.getBean(IncrementalExportClient.class)
        badTokenExportClient = badTokenCtx.getBean(IncrementalExportClient.class)
        badUrlExportClient = badUrlCtx.getBean(IncrementalExportClient.class)
    }

    def "can export incremental organizations as an admin"() {
        given: "an authenticated admin client and start timestamp"
        int oneWeekAgo = (System.currentTimeMillis() / 1000L - 604800) as int

        when: "requesting incremental organization export"
        adminExportClient.incrementalOrganizationExport(oneWeekAgo, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can export incremental ticket events as an admin"() {
        given: "an authenticated admin client and start timestamp"
        int oneWeekAgo = (System.currentTimeMillis() / 1000L - 604800) as int

        when: "requesting incremental ticket events export"
        adminExportClient.incrementalTicketEvents(oneWeekAgo, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot export incremental organizations"(IncrementalExportClient client, String userType) {
        given: "an unauthorized client for #userType"
        int oneWeekAgo = (System.currentTimeMillis() / 1000L - 604800) as int

        when: "requesting incremental organization export"
        client.incrementalOrganizationExport(oneWeekAgo, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentExportClient, "agent"],
                [userExportClient, "end user"]
        ]
    }

    @Unroll
    def "calling incremental export client with #description throws HttpClientException"(
            String description, IncrementalExportClient client) {
        given:
        int oneWeekAgo = (System.currentTimeMillis() / 1000L - 604800) as int

        when: "requesting incremental export with invalid client configuration"
        client.incrementalOrganizationExport(oneWeekAgo, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenExportClient
        "unreachable url" | badUrlExportClient
    }
}
