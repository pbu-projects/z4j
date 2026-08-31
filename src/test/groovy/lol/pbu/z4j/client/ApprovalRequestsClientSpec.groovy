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
class ApprovalRequestsClientSpec extends Z4jSpec {

    @Shared
    ApprovalRequestsClient adminApprovalsClient, agentApprovalsClient, userApprovalsClient,
                           badTokenApprovalsClient, badUrlApprovalsClient

    def setupSpec() {
        adminApprovalsClient = adminCtx.getBean(ApprovalRequestsClient.class)
        agentApprovalsClient = agentCtx.getBean(ApprovalRequestsClient.class)
        userApprovalsClient = userCtx.getBean(ApprovalRequestsClient.class)
        badTokenApprovalsClient = badTokenCtx.getBean(ApprovalRequestsClient.class)
        badUrlApprovalsClient = badUrlCtx.getBean(ApprovalRequestsClient.class)
    }

    def "end user cannot search approvals"() {
        given: "an end user client"

        when: "attempting to search approvals as an end user"
        userApprovalsClient.searchApprovals("test-instance-id").block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling approval requests client with #description throws HttpClientException"(
            String description, ApprovalRequestsClient client) {
        when: "attempting to search approvals with invalid client configuration"
        client.searchApprovals("test-instance-id").block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenApprovalsClient
        "unreachable url" | badUrlApprovalsClient
    }
}
