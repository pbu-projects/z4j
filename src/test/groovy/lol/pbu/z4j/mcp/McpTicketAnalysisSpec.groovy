package lol.pbu.z4j.mcp

import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.client.SearchClient
import lol.pbu.z4j.client.TicketClient
import lol.pbu.z4j.client.TicketCommentsClient
import lol.pbu.z4j.model.SearchQueryBuilder
import lol.pbu.z4j.model.TicketSideload
import spock.lang.Shared

class McpTicketAnalysisSpec extends Z4jSpec {

    @Shared SearchClient searchClient
    @Shared TicketClient ticketClient
    @Shared TicketCommentsClient ticketCommentsClient

    def setupSpec() {
        searchClient = adminCtx.getBean(SearchClient)
        ticketClient = adminCtx.getBean(TicketClient)
        ticketCommentsClient = adminCtx.getBean(TicketCommentsClient)
    }

    def "mcp server can search for a ticket and retrieve full context"() {
        given: "a dynamically built search query for a recent ticket"
        def query = SearchQueryBuilder.builder()
            .type("ticket")
            .statusLessThan("solved")
            .build()

        when: "the mcp server searches with sideloads"
        def searchResponse = searchClient.list(query, ["users", "groups"], null, null, 1, 10).block()

        then: "search succeeds"
        noExceptionThrown()
        searchResponse != null

        when: "the mcp server pulls the first ticket found and its comments"
        if (searchResponse.results && !searchResponse.results.isEmpty()) {
            // Note: Results is a list of generic Objects in the base SearchResponse, we'll cast safely or just assume id
            def ticketId = searchResponse.results[0].id as Long
            
            // Re-fetch ticket directly
            def ticket = ticketClient.showTicket(ticketId, [TicketSideload.USERS]).block()
            assert ticket != null
            
            // Fetch comments
            def comments = ticketCommentsClient.listTicketComments(ticketId, null, null).block()
            assert comments != null
        }

        then: "context retrieval completes successfully"
        noExceptionThrown()
    }
}
