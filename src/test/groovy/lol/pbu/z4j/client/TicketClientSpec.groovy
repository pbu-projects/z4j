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

    def "calling listTickets() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.listTickets(null).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling listTickets() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.listTickets(null).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "calling showTicket() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.showTicket(tickets.get(0).getId()).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling showTicket() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.showTicket(tickets.get(0).getId()).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "Trying to create a ticket succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketComment ticketComment = new TicketComment().setBody(faker.chuckNorris().fact())
        TicketCreateInput createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(faker.chuckNorris().fact())
        TicketCreateRequest createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        client.createTicket(createTicketRequest).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "Trying to create a ticket fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketComment ticketComment = new TicketComment().setBody(faker.chuckNorris().fact())
        TicketCreateInput createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(faker.chuckNorris().fact())
        TicketCreateRequest createTicketRequest = new TicketCreateRequest(createTicketInput)

        when:
        client.createTicket(createTicketRequest).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "calling updateTicket() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketUpdateInput ticketUpdateInput = new TicketUpdateInput()
                .setComment(new TicketComment().setBody(faker.hitchhikersGuideToTheGalaxy().marvinQuote().toString()))
        TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest().setTicket(ticketUpdateInput)


        when:
        client.updateTicket(tickets.first.getId(), ticketUpdateRequest).block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling updateTicket() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        given:
        TicketUpdateInput ticketUpdateInput = new TicketUpdateInput()
                .setComment(new TicketComment().setBody(faker.hitchhikersGuideToTheGalaxy().marvinQuote().toString()))
        TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest().setTicket(ticketUpdateInput)


        when:
        client.updateTicket(tickets.first.getId(), ticketUpdateRequest).block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "calling countTickets() succeeds when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.getTicketCount().block()

        then:
        noExceptionThrown()

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { it.shouldSucceed }
    }

    def "calling countTickets() fails when used with a(n) #clientType client"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored) {
        when:
        client.getTicketCount().block()

        then:
        thrown(HttpClientException)

        where:
        [client, clientType, ignored, alsoIgnored] << clientTestMatrix.findAll { !it.shouldSucceed }
    }

    def "can call listTicketFields when using a(n) #clientType client and #creator flag"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored, Boolean creator, Locale locale) {
        when:
        client.listTicketFields(locale.getLocale(), creator).block()

        then:
        noExceptionThrown()

        and:
        if (creator) {
            //todo: https://github.com/PeanutButter-Unicorn/z4j/issues/52
        }

        where:
        [[client, clientType, ignored, alsoIgnored], creator, locale] << [
                clientTestMatrix.findAll { it.shouldSucceed || it.clientType == "simple user" }, [true, false, null], accountLocales
        ].combinations()
    }

    def "calling listTicketFields when using a(n) #clientType client and #creator flag fails"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored, Boolean creator, Locale locale) {
        when:
        client.listTicketFields(locale.getLocale(), creator).block()

        then:
        thrown(HttpClientException)

        where:
        [[client, clientType, ignored, alsoIgnored], creator, locale] << [
                clientTestMatrix.findAll { !it.shouldSucceed && it.clientType != "simple user"}, [true, false, null], accountLocales
        ].combinations()
    }
}
