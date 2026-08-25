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
import lol.pbu.z4j.model.TicketFieldsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TicketFieldsClientSpec extends Z4jSpec {

    @Shared
    TicketFieldsClient adminTicketFieldsClient, agentTicketFieldsClient, userTicketFieldsClient,
                       badTokenTicketFieldsClient, badUrlTicketFieldsClient

    @Shared
    Integer existingFieldId

    def setupSpec() {
        adminTicketFieldsClient = adminCtx.getBean(TicketFieldsClient.class)
        agentTicketFieldsClient = agentCtx.getBean(TicketFieldsClient.class)
        userTicketFieldsClient = userCtx.getBean(TicketFieldsClient.class)
        badTokenTicketFieldsClient = badTokenCtx.getBean(TicketFieldsClient.class)
        badUrlTicketFieldsClient = badUrlCtx.getBean(TicketFieldsClient.class)

        TicketFieldsResponse fields = adminTicketFieldsClient.listTicketFields(null, null).block()
        if (fields?.ticketFields && !fields.ticketFields.isEmpty()) {
            existingFieldId = fields.ticketFields.first().id
        }
    }

    @Unroll
    def "can list ticket fields for all roles as #userType"(
            TicketFieldsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ticket fields list"
        client.listTicketFields(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFieldsClient, "admin"],
                [agentTicketFieldsClient, "agent"],
                [userTicketFieldsClient, "end user"]
        ]
    }

    @Unroll
    def "can count ticket fields as an #userType"(
            TicketFieldsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting ticket fields count"
        client.countTicketFields().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFieldsClient, "admin"],
                [agentTicketFieldsClient, "agent"]
        ]
    }

    @Unroll
    def "can show ticket field by ID as an #userType"(
            TicketFieldsClient client, String userType) {
        given: "an authenticated client for #userType and existing field ID"

        when: "requesting ticket field by ID"
        if (existingFieldId != null) {
            client.showTicketfield(existingFieldId, null).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFieldsClient, "admin"],
                [agentTicketFieldsClient, "agent"]
        ]
    }

    @Unroll
    def "can show many ticket fields for all roles as #userType"(
            TicketFieldsClient client, String userType) {
        given: "an authenticated client for #userType and existing field ID"

        when: "requesting multiple ticket fields by ID list"
        if (existingFieldId != null) {
            client.showManyTicketFields(existingFieldId.toString(), null, null, null).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFieldsClient, "admin"],
                [agentTicketFieldsClient, "agent"],
                [userTicketFieldsClient, "end user"]
        ]
    }

    def "end user cannot count ticket fields"() {
        given: "an end user client"

        when: "requesting ticket fields count as an end user"
        userTicketFieldsClient.countTicketFields().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ticket fields client with #description throws HttpClientException"(
            String description, TicketFieldsClient client) {
        when: "requesting ticket fields with invalid client configuration"
        client.listTicketFields(null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTicketFieldsClient
        "unreachable url" | badUrlTicketFieldsClient
    }
}
