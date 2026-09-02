package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.TicketField
import lol.pbu.z4j.model.TicketFieldCreateRequest
import lol.pbu.z4j.model.TicketFieldTypeEnum
import org.yaml.snakeyaml.Yaml
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TicketFieldsClientSpec extends Z4jSpec {

    @Shared TicketFieldsClient adminTicketFieldsClient, agentTicketFieldsClient, userTicketFieldsClient, badTokenTicketFieldsClient, badUrlTicketFieldsClient
    @Shared Long testFieldId

    def setupSpec() {
        adminTicketFieldsClient = adminCtx.getBean(TicketFieldsClient.class)
        agentTicketFieldsClient = agentCtx.getBean(TicketFieldsClient.class)
        userTicketFieldsClient = userCtx.getBean(TicketFieldsClient.class)
        badTokenTicketFieldsClient = badTokenCtx.getBean(TicketFieldsClient.class)
        badUrlTicketFieldsClient = badUrlCtx.getBean(TicketFieldsClient.class)

        // Create test ticket field
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/ticket_field_fixtures.yaml").text) as Map
        def fieldData = fixtures.ticketFields[0] as Map
        def field = new TicketField(fieldData.title as String, fieldData.type as String)
        def request = new TicketFieldCreateRequest().setTicketField(field)

        def response = adminTicketFieldsClient.createTicketField(request).block()
        testFieldId = response.ticketField.id
        sleep(2000)
    }

    def cleanupSpec() {
        if (testFieldId != null) {
            try {
                adminTicketFieldsClient.deleteTicketField(testFieldId, null).block()
            } catch (Exception ignored) {}
        }
    }

    @Unroll
    def "can list ticket fields for all roles as #userType"(
            TicketFieldsClient client, String userType) {
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
        when: "requesting ticket field by ID"
        client.showTicketfield(testFieldId, null).block()

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
        when: "requesting multiple ticket fields by ID list"
        client.showManyTicketFields(testFieldId.toString(), null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketFieldsClient, "admin"],
                [agentTicketFieldsClient, "agent"]
        ]
    }

    def "end user cannot count ticket fields"() {
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
