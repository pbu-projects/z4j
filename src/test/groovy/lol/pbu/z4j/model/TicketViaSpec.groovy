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

class TicketViaSpec extends Z4jSpec {

    @Unroll
    def "should put source item"() {
        given:
        def ticketVia = new TicketVia()
        ticketVia.source == null
        def key = faker.lorem().word()
        def value = faker.lorem().sentence()

        when:
        ticketVia.putSourceItem(key, value)

        then:
        ticketVia.source.size() == 1
        ticketVia.source.get(key) == value
    }

    @Unroll
    def "put source item to existing map"() {
        given:
        def existingKey = faker.lorem().word()
        def existingValue = faker.lorem().sentence()
        def ticketVia = new TicketVia(source: [(existingKey): existingValue])
        def newKey = faker.lorem().word()
        def newValue = faker.lorem().sentence()

        when:
        ticketVia.putSourceItem(newKey, newValue)

        then:
        ticketVia.source.size() == 2
        ticketVia.source.get(existingKey) == existingValue
        ticketVia.source.get(newKey) == newValue
    }
}

