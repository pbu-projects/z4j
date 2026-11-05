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

class ViaSourceToSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViaSourceTo with constructors and builder"() {
        when:
        ViaSourceTo direct = new ViaSourceTo("support@example.com", "Help Desk")
        ViaSourceTo noArg = new ViaSourceTo()
        ViaSourceTo built = ViaSourceToBuilder.builder()
                .address("support@example.com")
                .name("Help Desk")
                .build()

        then:
        verifyAll(direct) {
            address() == "support@example.com"
            name() == "Help Desk"
        }
        verifyAll(noArg) {
            address() == null
            name() == null
        }
        verifyAll(built) {
            it == direct
            address() == "support@example.com"
            name() == "Help Desk"
        }
    }

    def "should update ViaSourceTo with record withers"() {
        given:
        ViaSourceTo initial = new ViaSourceTo()

        when:
        ViaSourceTo updated = initial
                .withAddress("billing@example.com")
                .withName("Billing Support")

        then:
        verifyAll(updated) {
            address() == "billing@example.com"
            name() == "Billing Support"
        }
    }

    def "should round-trip serialize and deserialize ViaSourceTo"() {
        given:
        ViaSourceTo sourceTo = new ViaSourceTo("dest@example.com", "Destination Queue")

        when:
        String json = objectMapper.writeValueAsString(sourceTo)
        Map map = objectMapper.readValue(json, Map.class)
        ViaSourceTo deserialized = objectMapper.readValue(json, ViaSourceTo.class)

        then:
        verifyAll(map) {
            containsKey("address")
            containsKey("name")
            get("address") == "dest@example.com"
            get("name") == "Destination Queue"
        }
        verifyAll(deserialized) {
            it == sourceTo
            address() == "dest@example.com"
            name() == "Destination Queue"
        }
    }

    def "should handle null fields in serialization"() {
        when:
        ViaSourceTo empty = new ViaSourceTo()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ViaSourceTo deserialized = objectMapper.readValue(json, ViaSourceTo.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            address() == null
            name() == null
        }
    }
}
