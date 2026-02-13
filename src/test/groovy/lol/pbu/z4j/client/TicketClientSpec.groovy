package lol.pbu.z4j.client

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientException
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.*
import spock.lang.Shared

class TicketClientSpec extends Z4jSpec {

    @Shared
    TicketClient ticketsAgentClient, ticketsAdminClient, ticketsUserClient, ticketBadEmailClient, ticketBadUrlClient

    @Shared
    List<Ticket> tickets

    void setupSpec() {
        ticketBadEmailClient = badEmailCtx.getBean(TicketClient.class)
        ticketBadUrlClient = badUrlCtx.getBean(TicketClient.class)
        ticketsAgentClient = agentCtx.getBean(TicketClient.class)
        ticketsAdminClient = adminCtx.getBean(TicketClient.class)
        ticketsUserClient = userCtx.getBean(TicketClient.class)
        tickets = ticketsAgentClient.listTickets(null).block().getTickets()
    }

    def "calling listTickets() #expectedTitle succeed when used with a(n) #clientType client"(TicketClient client,
                                                                                              String clientType,
                                                                                              Boolean shouldSucceed,
                                                                                              String expectedTitle) {
        when:
        TicketsResponse response = client.listTickets(null).block()

        then:
        if (shouldSucceed) {
            response != null
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

    def "calling showTicket() #expectedTitle succeed when used with a(n) #clientType client"(TicketClient client,
                                                                                             String clientType,
                                                                                             Boolean shouldSucceed,
                                                                                             String expectedTitle) {
        when:
        TicketResponse response = client.showTicket(tickets.get(0).getId()).block()

        then:
        if (shouldSucceed) {
            response.getTicket() != null
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

    def "Trying to create a ticket #expectedTitle succeed when used with a(n) #clientType client"(TicketClient client,
                                                                                                  String clientType,
                                                                                                  Boolean shouldSucceed,
                                                                                                  String expectedTitle) {
        given:
        TicketComment ticketComment = new TicketComment().setBody(faker.chuckNorris().fact())
        TicketCreateInput createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(faker.chuckNorris().fact())
        TicketCreateRequest createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        HttpResponse<TicketResponse> response = client.createTicket(createTicketRequest).block() //should be Mono<TicketResponse>, not Mono<HttpResponse<TicketResponse>>

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

    def "calling updateTicket() #expectedTitle succeed when used with a(n) #clientType client"(TicketClient client,
                                                                                               String clientType,
                                                                                               Boolean shouldSucceed,
                                                                                               String expectedTitle) {
        given:
        TicketUpdateInput ticketUpdateInput = new TicketUpdateInput()
                .setComment(new TicketComment().setBody(faker.hitchhikersGuideToTheGalaxy().marvinQuote().toString()))
        TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest().setTicket(ticketUpdateInput)


        when:
        TicketUpdateResponse response = client.updateTicket(tickets.first.getId(), ticketUpdateRequest).block()

        then:
        if (shouldSucceed) {
            verifyAll {
                response.getTicket() != null
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

    def "calling countTickets() #expectedTitle succeed when used with a(n) #clientType client"(TicketClient client,
                                                                                               String clientType,
                                                                                               Boolean shouldSucceed,
                                                                                               String expectedTitle) {
        when:
        TicketCountResponse response = client.countTickets().block() //TODO(jonathan) rename this to getTicketCount()

        then:
        if (shouldSucceed) {
            verifyAll {
                response.getCount().getValue() > 0
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
