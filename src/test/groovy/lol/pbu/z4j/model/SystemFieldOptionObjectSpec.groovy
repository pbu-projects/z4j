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

class SystemFieldOptionObjectSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create SystemFieldOptionObject with canonical constructor and builder"() {
        when:
        SystemFieldOptionObject direct = new SystemFieldOptionObject("Open", "open")
        SystemFieldOptionObject built = SystemFieldOptionObjectBuilder.builder()
                .name("Open")
                .value("open")
                .build()

        then:
        verifyAll(direct) {
            name() == "Open"
            value() == "open"
        }
        verifyAll(built) {
            it == direct
            name() == "Open"
            value() == "open"
        }
    }

    def "should update SystemFieldOptionObject using record withers"() {
        given:
        SystemFieldOptionObject original = new SystemFieldOptionObject("Open", "open")

        when:
        SystemFieldOptionObject updated = original
                .withName("Pending")
                .withValue("pending")

        then:
        verifyAll(original) {
            name() == "Open"
            value() == "open"
        }
        verifyAll(updated) {
            name() == "Pending"
            value() == "pending"
        }
    }

    def "should round-trip serialize and deserialize SystemFieldOptionObject"() {
        given:
        SystemFieldOptionObject option = new SystemFieldOptionObject("Solved", "solved")

        when:
        String json = objectMapper.writeValueAsString(option)
        Map map = objectMapper.readValue(json, Map.class)
        SystemFieldOptionObject deserialized = objectMapper.readValue(json, SystemFieldOptionObject.class)

        then:
        verifyAll(map) {
            get("name") == "Solved"
            get("value") == "solved"
        }
        verifyAll(deserialized) {
            it == option
            name() == "Solved"
            value() == "solved"
        }
    }

    def "should handle null components in SystemFieldOptionObject constructor, wither, and serde"() {
        when:
        SystemFieldOptionObject empty = new SystemFieldOptionObject(null, null)
        SystemFieldOptionObject built = SystemFieldOptionObjectBuilder.builder().build()
        SystemFieldOptionObject cleared = new SystemFieldOptionObject("name", "value")
                .withName(null)
                .withValue(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        SystemFieldOptionObject deserialized = objectMapper.readValue(json, SystemFieldOptionObject.class)

        then:
        verifyAll(empty) {
            name() == null
            value() == null
        }
        verifyAll(built) {
            it == empty
            name() == null
            value() == null
        }
        verifyAll(cleared) {
            it == empty
            name() == null
            value() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            name() == null
            value() == null
        }
    }
}
