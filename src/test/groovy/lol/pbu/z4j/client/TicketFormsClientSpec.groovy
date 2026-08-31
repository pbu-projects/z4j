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
import lol.pbu.z4j.model.TicketFormsResponse
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("ticketing")
class TicketFormsClientSpec extends Z4jSpec {

    @Shared
    TicketFormsClient adminTicketFormsClient, agentTicketFormsClient, userTicketFormsClient,
                      badTokenTicketFormsClient, badUrlTicketFormsClient

    @Shared
    Long existingFormId

    def setupSpec() {
        adminTicketFormsClient = adminCtx.getBean(TicketFormsClient.class)
        agentTicketFormsClient = agentCtx.getBean(TicketFormsClient.class)
        userTicketFormsClient = userCtx.getBean(TicketFormsClient.class)
        badTokenTicketFormsClient = badTokenCtx.getBean(TicketFormsClient.class)
        badUrlTicketFormsClient = badUrlCtx.getBean(TicketFormsClient.class)

        TicketFormsResponse forms = adminTicketFormsClient.listTicketForms(null, null, null, null).block()
        if (forms?.ticketForms && !forms.ticketForms.isEmpty()) {
            existingFormId = forms.ticketForms.first().id
        }
    }

    @Unroll
    def "can list ticket forms for all roles as #userType"(
            TicketFormsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ticket forms list"
        client.listTicketForms(null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFormsClient, "admin"],
                [agentTicketFormsClient, "agent"],
                [userTicketFormsClient, "end user"]
        ]
    }

    @Unroll
    def "can show ticket form by ID for all roles as #userType"(
            TicketFormsClient client, String userType) {
        given: "an authenticated client for #userType and existing form ID"

        when: "requesting ticket form by ID"
        if (existingFormId != null) {
            client.showTicketForm(existingFormId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFormsClient, "admin"],
                [agentTicketFormsClient, "agent"],
                [userTicketFormsClient, "end user"]
        ]
    }

    @Unroll
    def "can show many ticket forms for all roles as #userType"(
            TicketFormsClient client, String userType) {
        given: "an authenticated client for #userType and existing form ID"

        when: "requesting multiple ticket forms by ID list"
        if (existingFormId != null) {
            client.showManyTicketForms(existingFormId.toString(), null, null, null, null).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFormsClient, "admin"],
                [agentTicketFormsClient, "agent"],
                [userTicketFormsClient, "end user"]
        ]
    }

    @Unroll
    def "can list ticket form statuses for all roles as #userType"(
            TicketFormsClient client, String userType) {
        given: "an authenticated client for #userType and existing form ID"

        when: "requesting statuses for a ticket form"
        if (existingFormId != null) {
            client.ticketFormTicketFormStatuses(existingFormId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFormsClient, "admin"],
                [agentTicketFormsClient, "agent"],
                [userTicketFormsClient, "end user"]
        ]
    }

    @Unroll
    def "calling ticket forms client with #description throws HttpClientException"(
            String description, TicketFormsClient client) {
        when: "requesting ticket forms with invalid client configuration"
        client.listTicketForms(null, null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTicketFormsClient
        "unreachable url" | badUrlTicketFormsClient
    }
}
