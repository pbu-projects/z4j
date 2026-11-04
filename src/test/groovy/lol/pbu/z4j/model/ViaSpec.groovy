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

class ViaSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Via with constructors and builder"() {
        given:
        ViaSource expectedSource = new ViaSource(new ViaSourceFrom("user@example.com", 1L, "Alice", "Agent"), "rule", new ViaSourceTo("support@example.com", "Support"))

        when:
        Via direct = new Via("rule", expectedSource)
        Via noArg = new Via()
        Via built = ViaBuilder.builder()
                .channel("rule")
                .source(expectedSource)
                .build()

        then:
        verifyAll(direct) {
            it.channel() == "rule"
            it.source() == expectedSource
        }
        verifyAll(noArg) {
            it.channel() == null
            it.source() == null
        }
        verifyAll(built) {
            it == direct
            it.channel() == "rule"
            it.source() == expectedSource
        }
    }

    def "should update Via with record withers"() {
        given:
        Via initial = new Via()
        ViaSource updatedSource = new ViaSource(null, "trigger", null)

        when:
        Via updated = initial
                .withChannel("web")
                .withSource(updatedSource)

        then:
        verifyAll(updated) {
            it.channel() == "web"
            it.source() == updatedSource
        }
    }

    def "should round-trip serialize and deserialize Via"() {
        given:
        ViaSource sampleSource = new ViaSource(null, "macro", null)
        Via via = new Via("web", sampleSource)

        when:
        String json = objectMapper.writeValueAsString(via)
        Map map = objectMapper.readValue(json, Map.class)
        Via deserialized = objectMapper.readValue(json, Via.class)

        then:
        verifyAll(map) {
            containsKey("channel")
            containsKey("source")
            get("channel") == "web"
        }
        verifyAll(deserialized) {
            it == via
            it.channel() == "web"
            it.source() != null
            it.source().rel() == "macro"
        }
    }

    def "should handle null fields in serialization"() {
        when:
        Via empty = new Via()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        Via deserialized = objectMapper.readValue(json, Via.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            it.channel() == null
            it.source() == null
        }
    }
}
