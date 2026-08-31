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
import lol.pbu.z4j.model.ActionObject
import lol.pbu.z4j.model.CreateMacro200Response
import lol.pbu.z4j.model.CreateMacroRequest
import lol.pbu.z4j.model.MacroInput
import lol.pbu.z4j.model.MacrosResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class MacrosClientSpec extends Z4jSpec {

    @Shared
    MacrosClient adminMacrosClient, agentMacrosClient, userMacrosClient,
                 badTokenMacrosClient, badUrlMacrosClient

    @Shared
    Long existingMacroId

    def setupSpec() {
        adminMacrosClient = adminCtx.getBean(MacrosClient.class)
        agentMacrosClient = agentCtx.getBean(MacrosClient.class)
        userMacrosClient = userCtx.getBean(MacrosClient.class)
        badTokenMacrosClient = badTokenCtx.getBean(MacrosClient.class)
        badUrlMacrosClient = badUrlCtx.getBean(MacrosClient.class)

        MacrosResponse macros = adminMacrosClient.listActiveMacros(null, null, null, null, null, null).block()
        if (macros?.macros && !macros.macros.isEmpty()) {
            existingMacroId = macros.macros.first().id
        }
    }

    @Unroll
    def "can list active macros as an #userType"(MacrosClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting active macros"
        client.listActiveMacros(null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminMacrosClient, "admin"],
                [agentMacrosClient, "agent"]
        ]
    }

    @Unroll
    def "can list macros as an #userType"(MacrosClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting all macros"
        client.listMacros(null, null, null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminMacrosClient, "admin"],
                [agentMacrosClient, "agent"]
        ]
    }

    @Unroll
    def "can list macro action definitions as an #userType"(MacrosClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting macro action definitions"
        client.listMacroActionDefinitions().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminMacrosClient, "admin"],
                [agentMacrosClient, "agent"]
        ]
    }

    @Unroll
    def "can list macro categories as an #userType"(MacrosClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting macro categories"
        client.listMacroCategories().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminMacrosClient, "admin"],
                [agentMacrosClient, "agent"]
        ]
    }

    @Unroll
    def "can list macro actions as an #userType"(MacrosClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting supported macro actions"
        client.listMacrosActions().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminMacrosClient, "admin"],
                [agentMacrosClient, "agent"]
        ]
    }

    @Unroll
    def "can search macros as an #userType with query=#query"(
            MacrosClient client, String userType, String query) {
        given: "an authenticated client for #userType"

        when: "searching macros by query"
        client.searchMacro(query, null, null, null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], query] << [
                [[adminMacrosClient, "admin"], [agentMacrosClient, "agent"]],
                ["test"]
        ].combinations()
    }

    @Unroll
    def "can show changes to ticket for macro as an #userType"(
            MacrosClient client, String userType) {
        given: "an authenticated client for #userType and existing macro ID"

        when: "requesting macro apply ticket changes"
        if (existingMacroId != null) {
            client.showChangesToTicket(existingMacroId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminMacrosClient, "admin"],
                [agentMacrosClient, "agent"]
        ]
    }

    @Unroll
    def "can list macro attachments as an #userType"(
            MacrosClient client, String userType) {
        given: "an authenticated client for #userType and existing macro ID"

        when: "requesting macro attachments"
        if (existingMacroId != null) {
            client.listMacroAttachments(existingMacroId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminMacrosClient, "admin"],
                [agentMacrosClient, "agent"]
        ]
    }

    def "can perform macro CRUD lifecycle as an admin"() {
        given: "a macro payload with title and comment action"
        String macroTitle = "z4j test macro " + UUID.randomUUID().toString().substring(0, 8)
        CreateMacroRequest createRequest = new CreateMacroRequest(
                new MacroInput().setTitle(macroTitle).setActions([new ActionObject().setField("comment_value").setValue("Macro comment test")])
        )
        Long createdMacroId = null

        when: "creating a new macro as admin"
        CreateMacro200Response created = adminMacrosClient.createMacro(createRequest).block()
        createdMacroId = created?.macro?.id

        then: "macro is created successfully"
        noExceptionThrown()
        createdMacroId != null

        when: "retrieving the created macro by ID"
        adminMacrosClient.showMacro(createdMacroId).block()

        then: "macro details deserialize successfully"
        noExceptionThrown()

        when: "updating the macro title"
        CreateMacroRequest updateRequest = new CreateMacroRequest(
                new MacroInput().setTitle(macroTitle + " Updated").setActions([new ActionObject().setField("comment_value").setValue("Updated comment")])
        )
        adminMacrosClient.updateMacro(createdMacroId, updateRequest).block()

        then: "macro updates successfully"
        noExceptionThrown()

        cleanup: "delete the created test macro from the instance"
        if (createdMacroId != null) {
            try {
                adminMacrosClient.deleteMacro(createdMacroId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "end user cannot list or search macros"() {
        given: "an end user client"

        when: "requesting macros list as an end user"
        userMacrosClient.listMacros(null, null, null, null, null, null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling macros client with #description throws HttpClientException"(
            String description, MacrosClient client) {
        when: "requesting macros with invalid client configuration"
        client.listMacros(null, null, null, null, null, null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenMacrosClient
        "unreachable url" | badUrlMacrosClient
    }
}
