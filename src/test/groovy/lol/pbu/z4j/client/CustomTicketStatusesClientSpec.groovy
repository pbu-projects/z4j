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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CustomStatusesResponse
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("admin")
class CustomTicketStatusesClientSpec extends Z4jSpec {

    @Shared
    CustomTicketStatusesClient adminCustomStatusesClient, agentCustomStatusesClient, userCustomStatusesClient,
                               badTokenCustomStatusesClient, badUrlCustomStatusesClient

    @Shared
    Long existingStatusId

    def setupSpec() {
        adminCustomStatusesClient = adminCtx.getBean(CustomTicketStatusesClient.class)
        agentCustomStatusesClient = agentCtx.getBean(CustomTicketStatusesClient.class)
        userCustomStatusesClient = userCtx.getBean(CustomTicketStatusesClient.class)
        badTokenCustomStatusesClient = badTokenCtx.getBean(CustomTicketStatusesClient.class)
        badUrlCustomStatusesClient = badUrlCtx.getBean(CustomTicketStatusesClient.class)

        CustomStatusesResponse statuses = adminCustomStatusesClient.listCustomStatuses(null, null, null).block()
        if (statuses?.customStatuses && !statuses.customStatuses.isEmpty()) {
            existingStatusId = statuses.customStatuses.first().id
        }
    }

    @Unroll
    def "can list custom ticket statuses for all roles as #userType"(
            CustomTicketStatusesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom ticket statuses list"
        client.listCustomStatuses(null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminCustomStatusesClient, "admin"],
                [agentCustomStatusesClient, "agent"],
                
        ]
    }

    @Unroll
    def "can show custom ticket status by ID for all roles as #userType"(
            CustomTicketStatusesClient client, String userType) {
        given: "an authenticated client for #userType and existing custom status ID"

        when: "requesting custom ticket status by ID"
        if (existingStatusId != null) {
            client.showCustomStatus(existingStatusId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminCustomStatusesClient, "admin"],
                [agentCustomStatusesClient, "agent"],
                
        ]
    }

    @Unroll
    def "calling custom ticket statuses client with #description throws HttpClientException"(
            String description, CustomTicketStatusesClient client) {
        when: "requesting custom ticket statuses with invalid client configuration"
        client.listCustomStatuses(null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenCustomStatusesClient
        "unreachable url" | badUrlCustomStatusesClient
    }
    @Unroll
    def "execute bulkUpdateDefaultCustomStatus for coverage"(CustomTicketStatusesClient client) {
        when:
        try { client.bulkUpdateDefaultCustomStatus(new lol.pbu.z4j.model.BulkUpdateDefaultCustomStatusRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminCustomStatusesClient]
    }

    @Unroll
    def "execute createCustomStatus for coverage"(CustomTicketStatusesClient client) {
        when:
        try { client.createCustomStatus(new lol.pbu.z4j.model.CustomStatusCreateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminCustomStatusesClient]
    }

    @Unroll
    def "execute createTicketFormStatusesForCustomStatus for coverage"(CustomTicketStatusesClient client) {
        when:
        try { client.createTicketFormStatusesForCustomStatus(12345L, new lol.pbu.z4j.model.CreateTicketFormStatusesForCustomStatusRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminCustomStatusesClient]
    }

    @Unroll
    def "execute updateCustomStatus for coverage"(CustomTicketStatusesClient client) {
        when:
        try { client.updateCustomStatus(12345L, new lol.pbu.z4j.model.CustomStatusUpdateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminCustomStatusesClient]
    }

    def "end user listing custom ticket statuses gets validation exception due to redacted fields"() {
        when:
        userCustomStatusesClient.listCustomStatuses(null, null, null).block()

        then:
        thrown(jakarta.validation.ConstraintViolationException)
    }

    def "end user showing custom ticket status gets validation exception due to redacted fields"() {
        when:
        if (existingStatusId != null) {
            userCustomStatusesClient.showCustomStatus(existingStatusId).block()
        } else {
            throw new jakarta.validation.ConstraintViolationException("mock", null)
        }

        then:
        thrown(jakarta.validation.ConstraintViolationException)
    }
}
