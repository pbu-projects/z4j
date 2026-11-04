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

class CustomFieldOptionSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create CustomFieldOption with constructors and builder"() {
        when:
        CustomFieldOption direct = new CustomFieldOption("Option A", "opt_a", 101L, 1, "Raw Option A", "https://example.com/opt/1")
        CustomFieldOption convenience = new CustomFieldOption("Option B", "opt_b")
        CustomFieldOption built = CustomFieldOptionBuilder.builder()
                .name("Option A")
                .value("opt_a")
                .id(101L)
                .position(1)
                .rawName("Raw Option A")
                .url("https://example.com/opt/1")
                .build()

        then:
        verifyAll(direct) {
            name() == "Option A"
            value() == "opt_a"
            id() == 101L
            position() == 1
            rawName() == "Raw Option A"
            url() == "https://example.com/opt/1"
        }
        verifyAll(convenience) {
            name() == "Option B"
            value() == "opt_b"
            id() == null
            position() == null
            rawName() == null
            url() == null
        }
        verifyAll(built) {
            it == direct
            name() == "Option A"
            value() == "opt_a"
            id() == 101L
            position() == 1
            rawName() == "Raw Option A"
            url() == "https://example.com/opt/1"
        }
    }

    def "should update CustomFieldOption with record withers"() {
        given:
        CustomFieldOption initial = new CustomFieldOption("Old Name", "old_val")

        when:
        CustomFieldOption updated = initial
                .withName("New Name")
                .withValue("new_val")
                .withId(202L)
                .withPosition(2)
                .withRawName("New Raw")
                .withUrl("https://example.com/opt/2")

        then:
        verifyAll(updated) {
            name() == "New Name"
            value() == "new_val"
            id() == 202L
            position() == 2
            rawName() == "New Raw"
            url() == "https://example.com/opt/2"
        }
    }

    def "should round-trip serialize and deserialize CustomFieldOption"() {
        given:
        CustomFieldOption option = new CustomFieldOption("Feature Request", "feature", 303L, 3, "Feature Req", "https://example.com/opt/3")

        when:
        String json = objectMapper.writeValueAsString(option)
        Map map = objectMapper.readValue(json, Map.class)
        CustomFieldOption deserialized = objectMapper.readValue(json, CustomFieldOption.class)

        then:
        verifyAll(map) {
            containsKey("name")
            containsKey("value")
            containsKey("id")
            containsKey("position")
            containsKey("raw_name")
            containsKey("url")
            get("name") == "Feature Request"
            get("value") == "feature"
        }
        verifyAll(deserialized) {
            it == option
            name() == "Feature Request"
            value() == "feature"
            id() == 303L
            position() == 3
            rawName() == "Feature Req"
            url() == "https://example.com/opt/3"
        }
    }

    def "should handle optional null fields in serialization"() {
        when:
        CustomFieldOption option = new CustomFieldOption("Simple", "simple")
        String json = objectMapper.writeValueAsString(option)
        Map map = objectMapper.readValue(json, Map.class)
        CustomFieldOption deserialized = objectMapper.readValue(json, CustomFieldOption.class)

        then:
        verifyAll(map) {
            containsKey("name")
            containsKey("value")
            !containsKey("id")
            !containsKey("position")
            !containsKey("raw_name")
            !containsKey("url")
        }
        verifyAll(deserialized) {
            it == option
            name() == "Simple"
            value() == "simple"
            id() == null
            position() == null
            rawName() == null
            url() == null
        }
    }
}
