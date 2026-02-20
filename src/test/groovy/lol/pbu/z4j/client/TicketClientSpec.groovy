package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientException
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.*
import spock.lang.Shared

class TicketClientSpec extends Z4jSpec {

    @Shared
    TicketClient ticketsAgentClient, ticketsAdminClient, ticketsUserClient, ticketBadEmailClient, ticketBadUrlClient

    @Shared
    List<Ticket> tickets

    @Shared
    List<Map> clientTestMatrix

    void setupSpec() {
        ticketBadEmailClient = badEmailCtx.getBean(TicketClient.class)
        ticketBadUrlClient = badUrlCtx.getBean(TicketClient.class)
        ticketsAgentClient = agentCtx.getBean(TicketClient.class)
        ticketsAdminClient = adminCtx.getBean(TicketClient.class)
        ticketsUserClient = userCtx.getBean(TicketClient.class)
        tickets = ticketsAgentClient.listTickets(null).block().getTickets()
        clientTestMatrix = [[client: ticketsAgentClient, clientType: "Agent", shouldSucceed: true, expectedTitle: "should"],
                            [client: ticketsAdminClient, clientType: "Admin", shouldSucceed: true, expectedTitle: "should"],
                            [client: ticketBadEmailClient, clientType: "bad email", shouldSucceed: false, expectedTitle: "should not"],
                            [client: ticketBadUrlClient, clientType: "bad url", shouldSucceed: false, expectedTitle: "should not"],
                            [client: ticketsUserClient, clientType: "simple user", shouldSucceed: false, expectedTitle: "should not"]]
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
        [client, clientType, shouldSucceed, expectedTitle] << clientTestMatrix
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
        [client, clientType, shouldSucceed, expectedTitle] << clientTestMatrix
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
        TicketResponse response = client.createTicket(createTicketRequest).block()

        then:
        if (shouldSucceed) {
            response.getTicket() != null
            return
        }
        thrown(HttpClientException)

        where:
        [client, clientType, shouldSucceed, expectedTitle] << clientTestMatrix
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
            response.getTicket() != null
            return
        }
        thrown(HttpClientException)

        where:
        [client, clientType, shouldSucceed, expectedTitle] << clientTestMatrix
    }

    def "calling countTickets() #expectedTitle succeed when used with a(n) #clientType client"(TicketClient client,
                                                                                               String clientType,
                                                                                               Boolean shouldSucceed,
                                                                                               String expectedTitle) {
        when:
        TicketCountResponse response = client.countTickets().block() //TODO(jonathan) rename this to getTicketCount()

        then:
        if (shouldSucceed) {
            response.getCount().getValue() > 0
            return
        }
        thrown(HttpClientException)

        where:
        [client, clientType, shouldSucceed, expectedTitle] << clientTestMatrix
    }

    def "can call listTicketFields when using a(n) #clientType client and #creator flag"(TicketClient client, String clientType, Boolean shouldSucceed, String expectedTitle, Boolean creator, Locale locale) {
        when:
        client.listTicketFields(locale.getLocale(), creator).block()

        then:
        noExceptionThrown()

        and:
        if (creator) {
            //todo: https://github.com/PeanutButter-Unicorn/z4j/issues/52
        }

        where:
        [[client, clientType, shouldSucceed, expectedTitle], creator, locale] << [
                clientTestMatrix.findAll { it.shouldSucceed }, [true, false, null], accountLocales
        ].combinations()
    }

    def "calling listTicketFields when using a(n) #clientType client and #creator flag fails"(TicketClient client, String clientType, Boolean shouldSucceed, String expectedTitle, Boolean creator, Locale locale) {
        when:
        client.listTicketFields(locale.getLocale(), creator).block()

        then:
        thrown(HttpClientException)

        where:
        [[client, clientType, shouldSucceed, expectedTitle], creator, locale] << [
                clientTestMatrix.findAll { !it.shouldSucceed }, [true, false, null], accountLocales
        ].combinations()
    }
}
