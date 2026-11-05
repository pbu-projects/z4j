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

import io.micronaut.serde.ObjectMapper
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

class TicketViaSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    @Unroll
    def "should put source item when source is null"() {
        given:
        TicketVia ticketVia = new TicketVia()
        def key = faker.lorem().word()
        def value = faker.lorem().sentence()

        when:
        TicketVia updated = ticketVia.putSourceItem(key, value)

        then:
        verifyAll(updated) {
            source() != null
            source().size() == 1
            source().get(key) == value
        }
    }

    @Unroll
    def "should put source item to existing map"() {
        given:
        def existingKey = faker.lorem().word()
        def existingValue = faker.lorem().sentence()
        TicketVia ticketVia = new TicketVia([(existingKey): existingValue])
        def newKey = faker.lorem().word()
        def newValue = faker.lorem().sentence()

        when:
        TicketVia updated = ticketVia.putSourceItem(newKey, newValue)

        then:
        verifyAll(updated) {
            source() != null
            source().size() == 2
            source().get(existingKey) == existingValue
            source().get(newKey) == newValue
        }
    }

    def "should create TicketVia with constructors and builder"() {
        given:
        Map<String, Object> map = [agent: "mobile-app"]

        when:
        TicketVia direct = new TicketVia("mobile", map)
        TicketVia noArg = new TicketVia()
        TicketVia mapArg = new TicketVia(map)
        TicketVia built = TicketViaBuilder.builder()
                .channel("mobile")
                .source(map)
                .build()

        then:
        verifyAll(direct) {
            channel() == "mobile"
            source() == map
        }
        verifyAll(noArg) {
            channel() == null
            source() == null
        }
        verifyAll(mapArg) {
            channel() == null
            source() == map
        }
        verifyAll(built) {
            it == direct
            channel() == "mobile"
            source() == map
        }
    }

    def "should update TicketVia with record withers"() {
        given:
        TicketVia initial = new TicketVia()

        when:
        TicketVia updated = initial
                .withChannel("api")
                .withSource([client: "postman"])

        then:
        verifyAll(updated) {
            channel() == "api"
            source() == [client: "postman"]
        }
    }

    def "should round-trip serialize and deserialize TicketVia"() {
        given:
        TicketVia via = new TicketVia("web", [rel: "ticket_form"])

        when:
        String json = objectMapper.writeValueAsString(via)
        Map map = objectMapper.readValue(json, Map.class)
        TicketVia deserialized = objectMapper.readValue(json, TicketVia.class)

        then:
        verifyAll(map) {
            containsKey("channel")
            containsKey("source")
            get("channel") == "web"
            get("source") == [rel: "ticket_form"]
        }
        verifyAll(deserialized) {
            it == via
            channel() == "web"
            source() == [rel: "ticket_form"]
        }
    }

    def "should handle null fields in serialization"() {
        when:
        TicketVia empty = new TicketVia()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketVia deserialized = objectMapper.readValue(json, TicketVia.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            channel() == null
            source() == null
        }
    }
}
