package lol.pbu.z4j

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import lol.pbu.z4j.model.Ticket
import spock.lang.Shared

class SearchServiceSpec extends Z4jSpec {

    @Shared
    SearchService adminSearchService, agentSearchService, userSearchService

    def setupSpec() {
        adminSearchService = adminCtx.getBean(SearchService.class)
        agentSearchService = agentCtx.getBean(SearchService.class)
        userSearchService = userCtx.getBean(SearchService.class)
    }

    def "Can query tickets as #agentType using getTickets(query, pageSize)"(SearchService service, String agentType) {
        when: "The service queries for tickets"
        List<Ticket> tickets = service.getTickets("", 1000).collectList().block()

        then: "The result contains tickets"
        tickets.size() > 0

        and: "No exception is thrown"
        noExceptionThrown()

        where:
        [service, agentType] << [[adminSearchService, "admin"], [agentSearchService, "agent"]]
    }

    def "User cannot query tickets and receives forbidden"() {
        when: "A standard user attempts to query tickets"
        userSearchService.getTickets("").collectList().block()

        then: "Expect 403 Error"
        def error = thrown(HttpClientResponseException)
        error.getStatus() == HttpStatus.FORBIDDEN
    }

    def "Can query tickets as #agentType using getTickets(query)"(SearchService service, String agentType) {
        when: "The service queries for tickets"
        Ticket ticket = service.getTickets("").blockFirst()

        then: "The result contains tickets"
        null != ticket

        and: "No exception is thrown"
        noExceptionThrown()

        where:
        [service, agentType] << [[adminSearchService, "admin"], [agentSearchService, "agent"]]
    }

    def "should NOT throw exception when 'type:ticket' is included in the query"() {
        expect:
        agentSearchService.getTickets("type:ticket", 2)

        where:
        query << ["type:ticket", "type:ticket ticket_type:problem", "ticket_type:problem"]
    }

    def "Validation correctly identifies illegal type filters"(String query) {
        when:
        agentSearchService.getTickets(query, 2)

        then:
        thrown(IllegalArgumentException)

        where:
        query                    | _
        "type:user"              | _
        "type:organization"      | _
        "status:open type:group" | _
        "type:ticket type:user"  | _
    }
}
