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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.TicketForm
import lol.pbu.z4j.model.TicketFormCreateRequest
import org.yaml.snakeyaml.Yaml
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class TicketFormsClientSpec extends Z4jSpec {

    @Shared TicketFormsClient adminTicketFormsClient, agentTicketFormsClient, userTicketFormsClient, badUrlTicketFormsClient
    @Shared Long testFormId

    def setupSpec() {
        adminTicketFormsClient = adminCtx.getBean(TicketFormsClient.class)
        agentTicketFormsClient = agentCtx.getBean(TicketFormsClient.class)
        userTicketFormsClient = userCtx.getBean(TicketFormsClient.class)
        badUrlTicketFormsClient = badUrlCtx.getBean(TicketFormsClient.class)

        // Create test ticket form using fixture
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/ticket_form_fixtures.yaml").text) as Map
        def formData = fixtures.ticketForms[0] as Map
        def form = new TicketForm(formData.name as String + " " + UUID.randomUUID().toString().substring(0, 8))
                .withEndUserVisible(true)
        def request = new TicketFormCreateRequest(form)

        def response = adminTicketFormsClient.createTicketForm(request).block()
        testFormId = response?.ticketForm()?.id
        sleep(2000)
    }

    def cleanupSpec() {
        if (testFormId != null) {
            try {
                adminTicketFormsClient.deleteTicketForm(testFormId).block()
            } catch (Exception ignored) {}
        }
    }

    @Unroll
    def "can list ticket forms for all roles as #userType"(
            TicketFormsClient client, String userType) {
        when: "requesting ticket forms list"
        def response = client.listTicketForms(null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
        response != null
        response.ticketForms() != null

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
        when: "requesting ticket form by ID"
        def response = client.showTicketForm(testFormId).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
        verifyAll(response) {
            it != null
            ticketForm() != null
            ticketForm().id == testFormId
        }

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
        when: "requesting multiple ticket forms by ID list"
        def response = client.showManyTicketForms(testFormId.toString(), null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
        response != null
        response.ticketForms() != null

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
        "unreachable url" | badUrlTicketFormsClient
    }
}
