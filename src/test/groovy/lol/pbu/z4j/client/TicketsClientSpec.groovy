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

    def "calling listTickets() #expectedTitle succeed when used with a(n) #clientType client"() {
        when:
        def response = client.listTickets(null)

        then:
        if (shouldSucceed) {
            verifyAll {
                response.status() == HttpStatus.OK
                response.body().getTickets() != null
            }
            return
        }
        thrown(HttpClientException)

        where:
        client               | clientType    | shouldSucceed | expectedTitle
        ticketsAgentClient   | "Agent"       | true          | "should"
        ticketsAdminClient   | "Admin"       | true          | "should"
        ticketBadEmailClient | "bad email"   | false         | "should not"
        ticketBadUrlClient   | "bad url"     | false         | "should not"
        ticketsUserClient    | "simple user" | false         | "should not"
    }

    def "calling showTicket() #expectedTitle succeed when used with a(n) #clientType client"() {
        when:
        def response = client.showTicket(tickets.get(0).getId())

        then:
        if (shouldSucceed) {
            response.status() == HttpStatus.OK
            response.body().getTicket() != null
            return
        }
        thrown(HttpClientException)

        where:
        client               | clientType    | shouldSucceed | expectedTitle
        ticketsAgentClient   | "Agent"       | true          | "should"
        ticketsAdminClient   | "Admin"       | true          | "should"
        ticketBadEmailClient | "bad email"   | false         | "should not"
        ticketBadUrlClient   | "bad url"     | false         | "should not"
        ticketsUserClient    | "simple user" | false         | "should not"
    }

    def "Trying to  create a ticket #expectedTitle succeed when used with a(n) #clientType client"() {
        given:
        def ticketComment = new TicketComment().setBody(faker.chuckNorris().fact())
        def createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(faker.chuckNorris().fact())
        def createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        new TicketCreateRequest()
        def response = client.createTicket(createTicketRequest)

        then:
        if (shouldSucceed) {
            verifyAll {
                response.status() == HttpStatus.CREATED
                response.body().getTicket() != null
            }
            return
        }
        thrown(HttpClientException)

        where:
        client               | clientType    | shouldSucceed | expectedTitle
        ticketsAgentClient   | "Agent"       | true          | "should"
        ticketsAdminClient   | "Admin"       | true          | "should"
        ticketBadEmailClient | "bad email"   | false         | "should not"
        ticketBadUrlClient   | "bad url"     | false         | "should not"
        ticketsUserClient    | "simple user" | false         | "should not"
    }

}
