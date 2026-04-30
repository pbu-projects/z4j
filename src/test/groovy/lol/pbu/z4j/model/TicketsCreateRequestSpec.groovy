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

