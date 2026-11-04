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

class ViaSourceSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViaSource with constructors and builder"() {
        given:
        ViaSourceFrom expectedFrom = new ViaSourceFrom("sender@example.com", 1L, "Sender", "User")
        ViaSourceTo expectedTo = new ViaSourceTo("receiver@example.com", "Receiver")

        when:
        ViaSource direct = new ViaSource(expectedFrom, "trigger", expectedTo)
        ViaSource noArg = new ViaSource()
        ViaSource built = ViaSourceBuilder.builder()
                .from(expectedFrom)
                .rel("trigger")
                .to(expectedTo)
                .build()

        then:
        verifyAll(direct) {
            it.from() == expectedFrom
            it.rel() == "trigger"
            it.to() == expectedTo
        }
        verifyAll(noArg) {
            it.from() == null
            it.rel() == null
            it.to() == null
        }
        verifyAll(built) {
            it == direct
            it.from() == expectedFrom
            it.rel() == "trigger"
            it.to() == expectedTo
        }
    }

    def "should update ViaSource with record withers"() {
        given:
        ViaSourceFrom initialFrom = new ViaSourceFrom("agent@example.com", 2L, "Agent", "Staff")
        ViaSourceTo initialTo = new ViaSourceTo("queue@example.com", "Main Queue")
        ViaSource initial = new ViaSource()

        when:
        ViaSource updated = initial
                .withFrom(initialFrom)
                .withRel("macro")
                .withTo(initialTo)

        then:
        verifyAll(updated) {
            it.from() == initialFrom
            it.rel() == "macro"
            it.to() == initialTo
        }
    }

    def "should round-trip serialize and deserialize ViaSource"() {
        given:
        ViaSourceFrom sampleFrom = new ViaSourceFrom("client@example.com", 99L, "Client", "Customer")
        ViaSourceTo sampleTo = new ViaSourceTo("support@example.com", "Support Team")
        ViaSource source = new ViaSource(sampleFrom, "rule", sampleTo)

        when:
        String json = objectMapper.writeValueAsString(source)
        Map map = objectMapper.readValue(json, Map.class)
        ViaSource deserialized = objectMapper.readValue(json, ViaSource.class)

        then:
        verifyAll(map) {
            containsKey("from")
            containsKey("rel")
            containsKey("to")
            get("rel") == "rule"
        }
        verifyAll(deserialized) {
            it == source
            it.from() != null
            it.from().address() == "client@example.com"
            it.rel() == "rule"
            it.to() != null
            it.to().address() == "support@example.com"
        }
    }

    def "should handle null fields in serialization"() {
        when:
        ViaSource empty = new ViaSource()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ViaSource deserialized = objectMapper.readValue(json, ViaSource.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            it.from() == null
            it.rel() == null
            it.to() == null
        }
    }
}
