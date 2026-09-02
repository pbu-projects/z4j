package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.TicketCreateRequest
import lol.pbu.z4j.model.TicketCreateInput
import lol.pbu.z4j.model.TicketComment
import spock.lang.Shared
import spock.lang.Unroll
import org.yaml.snakeyaml.Yaml

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TicketCommentsClientSpec extends Z4jSpec {

    @Shared TicketCommentsClient adminTicketCommentsClient, agentTicketCommentsClient, userTicketCommentsClient, badTokenTicketCommentsClient, badUrlTicketCommentsClient
    @Shared TicketClient ticketClient
    @Shared Long testTicketId

    def setupSpec() {
        adminTicketCommentsClient = adminCtx.getBean(TicketCommentsClient.class)
        agentTicketCommentsClient = agentCtx.getBean(TicketCommentsClient.class)
        userTicketCommentsClient = userCtx.getBean(TicketCommentsClient.class)
        badTokenTicketCommentsClient = badTokenCtx.getBean(TicketCommentsClient.class)
        badUrlTicketCommentsClient = badUrlCtx.getBean(TicketCommentsClient.class)
        ticketClient = adminCtx.getBean(TicketClient.class)

        // Load fixtures
        def yaml = new Yaml()
        def fixtureFile = new File("src/test/resources/fixtures/ticket_fixtures.yaml")
        def fixtures = yaml.load(fixtureFile.text) as Map
        def ticketData = fixtures.ticketData[0] as Map

        // Create test ticket
        def ticketPayload = new TicketCreateInput()
            .setSubject(ticketData.subject as String + " " + UUID.randomUUID().toString())
            .setComment(new TicketComment().setBody(ticketData.comment as String))
            
        def createResponse = ticketClient.createTicket(new TicketCreateRequest().setTicket(ticketPayload)).block()
        testTicketId = createResponse.ticket.id
        
        // Wait briefly for index consistency (eventual consistency)
        sleep(2000)
    }

    def cleanupSpec() {
        if (testTicketId != null) {
            try {
                ticketClient.deleteTicket(testTicketId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    @Unroll
    def "can count ticket comments as an #userType"(TicketCommentsClient client, String userType) {
        when: "requesting ticket comments count"
        client.countTicketComments(testTicketId).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTicketCommentsClient, "admin"],
                [agentTicketCommentsClient, "agent"]
        ]
    }

    @Unroll
    def "can list ticket comments as an #userType with includeInlineImages=#includeInlineImages"(
            TicketCommentsClient client, String userType, Boolean includeInlineImages) {
        when: "requesting ticket comments list"
        client.listTicketComments(testTicketId, includeInlineImages, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], includeInlineImages] << [
                [[adminTicketCommentsClient, "admin"], [agentTicketCommentsClient, "agent"]],
                [null, true, false]
        ].combinations()
    }

    def "end user cannot list ticket comments"() {
        when: "requesting ticket comments count as an end user"
        userTicketCommentsClient.countTicketComments(testTicketId).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling ticket comments client with #description throws HttpClientException"(
            String description, TicketCommentsClient client) {
        when: "requesting ticket comments with invalid client configuration"
        client.countTicketComments(testTicketId).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTicketCommentsClient
        "unreachable url" | badUrlTicketCommentsClient
    }
}
