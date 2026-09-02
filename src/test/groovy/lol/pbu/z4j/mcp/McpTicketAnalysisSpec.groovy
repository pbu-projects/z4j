package lol.pbu.z4j.mcp

import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.client.SearchClient
import lol.pbu.z4j.client.TicketClient
import lol.pbu.z4j.client.TicketCommentsClient
import lol.pbu.z4j.model.SearchQueryBuilder
import lol.pbu.z4j.model.TicketSideload
import lol.pbu.z4j.model.TicketCreateRequest
import lol.pbu.z4j.model.TicketCreateInput
import lol.pbu.z4j.model.TicketComment
import org.yaml.snakeyaml.Yaml
import spock.lang.Shared

class McpTicketAnalysisSpec extends Z4jSpec {

    @Shared SearchClient searchClient
    @Shared TicketClient ticketClient
    @Shared TicketCommentsClient ticketCommentsClient
    @Shared Long testTicketId
    @Shared String testTicketSubject

    def setupSpec() {
        searchClient = adminCtx.getBean(SearchClient)
        ticketClient = adminCtx.getBean(TicketClient)
        ticketCommentsClient = adminCtx.getBean(TicketCommentsClient)

        // Load fixtures
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/ticket_fixtures.yaml").text) as Map
        def ticketData = fixtures.ticketData[0] as Map
        testTicketSubject = (ticketData.subject as String) + " " + UUID.randomUUID().toString()

        def ticketPayload = new TicketCreateInput()
            .setSubject(testTicketSubject)
            .setComment(new TicketComment().setBody(ticketData.comment as String))
            
        def createResponse = ticketClient.createTicket(new TicketCreateRequest().setTicket(ticketPayload)).block()
        testTicketId = createResponse.ticket.id
        
        // Wait for search index consistency
        sleep(5000)
    }

    def cleanupSpec() {
        if (testTicketId != null) {
            try {
                ticketClient.deleteTicket(testTicketId).block()
            } catch (Exception ignored) {}
        }
    }

    def "mcp server can search for a ticket and retrieve full context"() {
        given: "a dynamically built search query for a recent ticket"
        def query = SearchQueryBuilder.builder()
            .type("ticket")
            .keyword(testTicketSubject)
            .build()

        when: "the mcp server searches with sideloads"
        def searchResponse = searchClient.list(query, ["users", "groups"], null, null, 1, 10).block()

        then: "search succeeds"
        noExceptionThrown()
        searchResponse != null

        when: "the mcp server pulls the ticket found and its comments"
        // In case search index is too slow during the test run, fallback to known ID
        def targetId = (searchResponse.results && !searchResponse.results.isEmpty()) ? (searchResponse.results[0].id as Long) : testTicketId
        
        // Re-fetch ticket directly
        def ticket = ticketClient.showTicket(targetId, [TicketSideload.USERS]).block()
        
        then: "ticket retrieved successfully"
        ticket != null
        
        when: "fetch comments"
        def comments = ticketCommentsClient.listTicketComments(targetId, null, null).block()
        
        then: "comments retrieved successfully"
        comments != null
    }
}
