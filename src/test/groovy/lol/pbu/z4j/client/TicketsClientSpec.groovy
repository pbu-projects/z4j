package lol.pbu.z4j.client

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientException
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.Ticket
import lol.pbu.z4j.model.TicketComment
import lol.pbu.z4j.model.TicketCreateInput
import lol.pbu.z4j.model.TicketCreateRequest
import spock.lang.Shared

class TicketsClientSpec extends Z4jSpec {
    @Shared
    TicketsClient ticketsAgentClient, ticketsAdminClient, ticketsUserClient, ticketBadEmailClient, ticketBadUrlClient

    @Shared
    List<Ticket> tickets

    void setupSpec() {
        ticketBadEmailClient = badEmailCtx.getBean(TicketsClient.class)
        ticketBadUrlClient = badUrlCtx.getBean(TicketsClient.class)
        ticketsAgentClient = agentCtx.getBean(TicketsClient.class)
        ticketsAdminClient = adminCtx.getBean(TicketsClient.class)
        ticketsUserClient = userCtx.getBean(TicketsClient.class)
        tickets = ticketsAgentClient.listTickets(null).body().getTickets()
    }

    def "can list tickets as agent or admin"() {
        when:
        def response = client.listTickets(null)

        then:
        response.status() == HttpStatus.OK

        and:
        response.body().getTickets() != null

        where:
        client             | _
        ticketsAgentClient | _
        ticketsAdminClient | _
    }

    def "Listing tickets fail with unsupported queries as expected"() {
        when:
        client.listTickets(null)

        then:
        thrown(HttpClientException)

        where:
        client               | _
        ticketBadEmailClient | _
        ticketBadUrlClient   | _
        ticketsUserClient    | _
    }


    def "can get a ticket"() {
        when:
        def response = client.showTicket(tickets.get(0).getId())

        then:
        response.status() == HttpStatus.OK

        and:
        response.body().getTicket() != null

        where:
        client             | _
        ticketsAgentClient | _
        ticketsAdminClient | _
    }

    def "can get a ticket"() {
        when:
        client.showTicket(tickets.get(0).getId())

        then:
        thrown(HttpClientException)

        where:
        client               | _
        ticketBadEmailClient | _
        ticketBadUrlClient   | _
        ticketsUserClient    | _
    }

    def "can create a ticket"() {
        given:
        def ticketComment = new TicketComment().setBody(faker.chuckNorris().fact())
        def createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(faker.chuckNorris().fact())
        def createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        new TicketCreateRequest()
        def response = client.createTicket(createTicketRequest)

        then:
        response.status() == HttpStatus.CREATED

        and:
        response.body().getTicket() != null

        where:
        client             | _
        ticketsAgentClient | _
        ticketsAdminClient | _
    }

    def "can create a ticket"() {
        given:
        def ticketComment = new TicketComment().setBody(faker.chuckNorris().fact())
        def createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(faker.chuckNorris().fact())
        def createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        client.createTicket(createTicketRequest)

        then:
        thrown(HttpClientException)

        where:
        client               | _
        ticketBadEmailClient | _
        ticketBadUrlClient   | _
        ticketsUserClient    | _
    }
}
