package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketsCreateRequestSpec extends Z4jSpec {

    @Unroll
    def "should add tickets item"() {
        given:
        def ticketsCreateRequest = new TicketsCreateRequest()
        ticketsCreateRequest.tickets == null
        def ticketCreateInput = new TicketCreateInput(new TicketComment(body: faker.lorem().sentence()))

        when:
        ticketsCreateRequest.addTicketsItem(ticketCreateInput)

        then:
        ticketsCreateRequest.tickets.size() == 1
        ticketsCreateRequest.tickets.getAt(0) == ticketCreateInput
    }

    @Unroll
    def "add tickets item to existing list"() {
        given:
        def existingTicketCreateInput = new TicketCreateInput(new TicketComment(body: faker.lorem().sentence()))
        def ticketsCreateRequest = new TicketsCreateRequest(tickets: [existingTicketCreateInput])
        def newTicketCreateInput = new TicketCreateInput(new TicketComment(body: faker.lorem().sentence()))

        when:
        ticketsCreateRequest.addTicketsItem(newTicketCreateInput)

        then:
        ticketsCreateRequest.tickets.size() == 2
        ticketsCreateRequest.tickets.containsAll([existingTicketCreateInput, newTicketCreateInput])
    }
}
