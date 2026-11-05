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

class TicketRequesterSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should construct TicketRequester via convenience constructors"() {
        when:
        def emailOnly = new TicketRequester("test@example.com")
        def nameAndEmail = new TicketRequester("Alice", "alice@example.com")
        def full = new TicketRequester("Bob", "bob@example.com", 123L)

        then:
        verifyAll(emailOnly) {
            name() == null
            email() == "test@example.com"
            localeId() == null
        }
        verifyAll(nameAndEmail) {
            name() == "Alice"
            email() == "alice@example.com"
            localeId() == null
        }
        verifyAll(full) {
            name() == "Bob"
            email() == "bob@example.com"
            localeId() == 123L
        }
    }

    def "should support record builder and withers"() {
        when:
        def requester = TicketRequesterBuilder.builder()
                .name("Taylor")
                .email("taylor@example.com")
                .localeId(42L)
                .build()

        def updated = requester.withName("Morgan")

        then:
        verifyAll(requester) {
            name() == "Taylor"
            email() == "taylor@example.com"
            localeId() == 42L
        }
        verifyAll(updated) {
            name() == "Morgan"
            email() == "taylor@example.com"
            localeId() == 42L
        }
    }

    def "should serialize TicketRequester ignoring null fields"() {
        given:
        def requester = new TicketRequester("user@example.com")

        when:
        String json = objectMapper.writeValueAsString(requester)
        Map map = objectMapper.readValue(json, Map.class)

        then:
        verifyAll(map) {
            it == [email: "user@example.com"]
            !containsKey("name")
            !containsKey("locale_id")
        }
    }

    def "should serialize and deserialize full TicketRequester"() {
        given:
        def requester = new TicketRequester("Charlie", "charlie@example.com", 1L)

        when:
        String json = objectMapper.writeValueAsString(requester)
        Map map = objectMapper.readValue(json, Map.class)
        def deserialized = objectMapper.readValue(json, TicketRequester.class)

        then:
        map == [name: "Charlie", email: "charlie@example.com", locale_id: 1L]
        verifyAll(deserialized) {
            it == requester
            name() == "Charlie"
            email() == "charlie@example.com"
            localeId() == 1L
        }
    }
}
