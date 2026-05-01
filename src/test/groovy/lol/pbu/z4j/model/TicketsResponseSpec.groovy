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

