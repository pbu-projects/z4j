package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.TicketFormObject
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class TicketFormsClientSpec extends Z4jSpec {

    @Shared TicketFormsClient adminTicketFormsClient, agentTicketFormsClient, userTicketFormsClient, badTokenTicketFormsClient, badUrlTicketFormsClient
    @Shared Long testFormId

    def setupSpec() {
        adminTicketFormsClient = adminCtx.getBean(TicketFormsClient.class)
        agentTicketFormsClient = agentCtx.getBean(TicketFormsClient.class)
        userTicketFormsClient = userCtx.getBean(TicketFormsClient.class)
        badTokenTicketFormsClient = badTokenCtx.getBean(TicketFormsClient.class)
        badUrlTicketFormsClient = badUrlCtx.getBean(TicketFormsClient.class)

        // Create test ticket form
        def formObject = new TicketFormObject().setName("Test Form " + UUID.randomUUID().toString().substring(0, 8))
        def request = [ticket_form: formObject]

        def response = adminTicketFormsClient.createTicketForm(request).block()
        testFormId = response.ticketForm.id
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
        when: "requesting ticket form by ID"
        client.showTicketForm(testFormId).block()

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
        when: "requesting multiple ticket forms by ID list"
        client.showManyTicketForms(testFormId.toString(), null, null, null, null).block()

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
        when: "requesting statuses for a ticket form"
        client.ticketFormTicketFormStatuses(testFormId).block()

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
        "unreachable url" | badUrlTicketFormsClient
    }
}
