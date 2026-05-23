/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientException
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.*
import spock.lang.Shared

class TicketClientSpec extends Z4jSpec {

    @Shared
    TicketClient ticketsAgentClient, ticketsUserClient, ticketBadEmailClient, ticketBadUrlClient

    @Shared
    List<Ticket> tickets

    @Shared
    List<Map> clientTestMatrix

    void setupSpec() {
        ticketBadEmailClient = badEmailCtx.getBean(TicketClient.class)
        ticketBadUrlClient = badUrlCtx.getBean(TicketClient.class)
        ticketsAgentClient = agentCtx.getBean(TicketClient.class)
        ticketsAdminClient = ticketsAdminClient ?: adminCtx.getBean(TicketClient.class)
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
        client.listTicketFields(locale.getLocaleAbbreviation(), creator).block()

        then:
        noExceptionThrown()

        where:
        [[client, clientType, ignored, alsoIgnored], creator, locale] << [
                clientTestMatrix.findAll { it.shouldSucceed || it.clientType == "simple user" }, [true, false, null], accountLocales
        ].combinations()
    }

    def "calling listTicketFields when using a(n) #clientType client and #creator flag fails"(TicketClient client, String clientType, Boolean ignored, String alsoIgnored, Boolean creator, Locale locale) {
        when:
        client.listTicketFields(locale.getLocaleAbbreviation(), creator).block()

        then:
        thrown(HttpClientException)

        where:
        [[client, clientType, ignored, alsoIgnored], creator, locale] << [
                clientTestMatrix.findAll { !it.shouldSucceed && it.clientType != "simple user"}, [true, false, null], accountLocales
        ].combinations()
    }

    def "can paginate listTicketFields with cursor pagination"() {
        given:
        LocaleAbbreviation locale = accountLocales.first().getLocaleAbbreviation()
        ensureMoreThan100TicketFields(locale)

        when:
        TicketFieldsResponse page = ticketsAdminClient.listTicketFields(locale, null, null, 100).block()
        List<TicketField> allFields = []
        while (page != null) {
            if (page.getTicketFields() != null) {
                allFields.addAll(page.getTicketFields())
            }
            if (!(page.getMeta()?.getHasMore()) || page.getMeta()?.getAfterCursor() == null) {
                break
            }
            page = ticketsAdminClient.listTicketFields(locale, null, page.getMeta().getAfterCursor(), 100).block()
        }

        then:
        allFields.size() > 100
        allFields*.id.toSet().size() == allFields.size()
    }

    private void ensureMoreThan100TicketFields(LocaleAbbreviation locale) {
        TicketFieldsResponse firstPage = ticketsAdminClient.listTicketFields(locale, null, null, 100).block()
        if (firstPage.getMeta()?.getHasMore()) {
            return
        }

        int needed = Math.max(0, 101 - (firstPage.getTicketFields()?.size() ?: 0))
        (1..needed).each {
            String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
            TicketField field = new TicketField()
                    .setTitle("z4j-cursor-fixture-${entropy}")
                    .setType(TicketFieldTypeEnum.TEXT.getValue())
            ticketsAdminClient.createTicketField(new TicketFieldCreateRequest(field)).block()
        }

        int maxAttempts = 10
        for (int i = 0; i < maxAttempts; i++) {
            TicketFieldsResponse refreshed = ticketsAdminClient.listTicketFields(locale, null, null, 100).block()
            if (refreshed.getMeta()?.getHasMore()) {
                return
            }
            sleep(2000)
        }
        throw new IllegalStateException("Ticket field fixture setup did not become visible in time. Please rerun or pre-seed your sandbox.")
    }
}
