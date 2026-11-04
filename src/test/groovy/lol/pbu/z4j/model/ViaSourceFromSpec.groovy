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
import spock.lang.Shared
import spock.lang.Specification

class ViaSourceFromSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViaSourceFrom with constructors and builder"() {
        when:
        ViaSourceFrom direct = new ViaSourceFrom("alice@example.com", 123L, "Alice", "Customer")
        ViaSourceFrom noArg = new ViaSourceFrom()
        ViaSourceFrom built = ViaSourceFromBuilder.builder()
                .address("alice@example.com")
                .id(123L)
                .name("Alice")
                .title("Customer")
                .build()

        then:
        verifyAll(direct) {
            address() == "alice@example.com"
            id() == 123L
            name() == "Alice"
            title() == "Customer"
        }
        verifyAll(noArg) {
            address() == null
            id() == null
            name() == null
            title() == null
        }
        verifyAll(built) {
            it == direct
            address() == "alice@example.com"
            id() == 123L
            name() == "Alice"
            title() == "Customer"
        }
    }

    def "should update ViaSourceFrom with record withers"() {
        given:
        ViaSourceFrom initial = new ViaSourceFrom()

        when:
        ViaSourceFrom updated = initial
                .withAddress("bob@example.com")
                .withId(456L)
                .withName("Bob")
                .withTitle("Support Agent")

        then:
        verifyAll(updated) {
            address() == "bob@example.com"
            id() == 456L
            name() == "Bob"
            title() == "Support Agent"
        }
    }

    def "should round-trip serialize and deserialize ViaSourceFrom"() {
        given:
        ViaSourceFrom sourceFrom = new ViaSourceFrom("charlie@example.com", 789L, "Charlie", "Team Lead")

        when:
        String json = objectMapper.writeValueAsString(sourceFrom)
        Map map = objectMapper.readValue(json, Map.class)
        ViaSourceFrom deserialized = objectMapper.readValue(json, ViaSourceFrom.class)

        then:
        verifyAll(map) {
            containsKey("address")
            containsKey("id")
            containsKey("name")
            containsKey("title")
            get("address") == "charlie@example.com"
            get("id") == 789
            get("name") == "Charlie"
            get("title") == "Team Lead"
        }
        verifyAll(deserialized) {
            it == sourceFrom
            address() == "charlie@example.com"
            id() == 789L
            name() == "Charlie"
            title() == "Team Lead"
        }
    }

    def "should handle null fields in serialization"() {
        when:
        ViaSourceFrom empty = new ViaSourceFrom()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ViaSourceFrom deserialized = objectMapper.readValue(json, ViaSourceFrom.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            address() == null
            id() == null
            name() == null
            title() == null
        }
    }
}
