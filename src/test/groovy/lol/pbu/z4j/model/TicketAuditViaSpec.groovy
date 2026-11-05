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

class TicketAuditViaSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketAuditVia with constructors and builder"() {
        given:
        Map<String, Object> expectedSource = [rel: "rule", id: 10]

        when:
        TicketAuditVia direct = new TicketAuditVia("rule", expectedSource)
        TicketAuditVia noArg = new TicketAuditVia()
        TicketAuditVia built = TicketAuditViaBuilder.builder()
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

    def "should update TicketAuditVia with record withers"() {
        given:
        TicketAuditVia initial = new TicketAuditVia()

        when:
        TicketAuditVia updated = initial
                .withChannel("web")
                .withSource([agent: "browser"])

        then:
        verifyAll(updated) {
            it.channel() == "web"
            it.source() == [agent: "browser"]
        }
    }

    def "should round-trip serialize and deserialize TicketAuditVia"() {
        given:
        TicketAuditVia via = new TicketAuditVia("mobile", [from: "ios"])

        when:
        String json = objectMapper.writeValueAsString(via)
        Map map = objectMapper.readValue(json, Map.class)
        TicketAuditVia deserialized = objectMapper.readValue(json, TicketAuditVia.class)

        then:
        verifyAll(map) {
            containsKey("channel")
            containsKey("source")
            get("channel") == "mobile"
            get("source") == [from: "ios"]
        }
        verifyAll(deserialized) {
            it == via
            it.channel() == "mobile"
            it.source() == [from: "ios"]
        }
    }

    def "should handle null fields in serialization"() {
        when:
        TicketAuditVia empty = new TicketAuditVia()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketAuditVia deserialized = objectMapper.readValue(json, TicketAuditVia.class)

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
