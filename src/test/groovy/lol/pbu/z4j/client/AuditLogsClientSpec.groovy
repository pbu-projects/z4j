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
import lol.pbu.z4j.model.AuditLogsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class AuditLogsClientSpec extends Z4jSpec {

    @Shared
    AuditLogsClient adminAuditLogsClient, agentAuditLogsClient, userAuditLogsClient,
                    badTokenAuditLogsClient, badUrlAuditLogsClient

    @Shared
    Long existingAuditLogId

    def setupSpec() {
        adminAuditLogsClient = adminCtx.getBean(AuditLogsClient.class)
        agentAuditLogsClient = agentCtx.getBean(AuditLogsClient.class)
        userAuditLogsClient = userCtx.getBean(AuditLogsClient.class)
        badTokenAuditLogsClient = badTokenCtx.getBean(AuditLogsClient.class)
        badUrlAuditLogsClient = badUrlCtx.getBean(AuditLogsClient.class)

        AuditLogsResponse logs = adminAuditLogsClient.listAuditLogs(null, null, null, null, null, null, null, null, null).block()
        if (logs?.auditLogs && !logs.auditLogs.isEmpty()) {
            existingAuditLogId = logs.auditLogs.first().id
        }
    }

    def "can list audit logs as an admin"() {
        given: "an authenticated admin client"

        when: "requesting audit logs list"
        adminAuditLogsClient.listAuditLogs(null, null, null, null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show audit log by ID as an admin"() {
        given: "an authenticated admin client and existing audit log ID"

        when: "requesting audit log by ID"
        if (existingAuditLogId != null) {
            adminAuditLogsClient.showAuditLog(existingAuditLogId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list audit logs"(AuditLogsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting audit logs"
        client.listAuditLogs(null, null, null, null, null, null, null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentAuditLogsClient, "agent"],
                [userAuditLogsClient, "end user"]
        ]
    }

    @Unroll
    def "calling audit logs client with #description throws HttpClientException"(
            String description, AuditLogsClient client) {
        when: "requesting audit logs with invalid client configuration"
        client.listAuditLogs(null, null, null, null, null, null, null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAuditLogsClient
        "unreachable url" | badUrlAuditLogsClient
    }
}
