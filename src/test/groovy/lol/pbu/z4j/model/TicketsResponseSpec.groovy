package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add tickets item"() {
        given:
        def ticketsResponse = new TicketsResponse()
        ticketsResponse.tickets == null
        def ticket = new Ticket(faker.number().randomNumber())

        when:
        ticketsResponse.addTicketsItem(ticket)

        then:
        ticketsResponse.tickets.size() == 1
        ticketsResponse.tickets.getAt(0) == ticket
    }

    @Unroll
    def "add tickets item to existing list"() {
        given:
        def existingTicket = new Ticket(faker.number().randomNumber())
        def ticketsResponse = new TicketsResponse(tickets: [existingTicket])
        def newTicket = new Ticket(faker.number().randomNumber())

        when:
        ticketsResponse.addTicketsItem(newTicket)

        then:
        ticketsResponse.tickets.size() == 2
        ticketsResponse.tickets.containsAll([existingTicket, newTicket])
    }
}
