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

class LabelSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Label with constructors and builder"() {
        when:
        Label direct = new Label(
                101L,
                "https://example.com/api/v2/help_center/articles/1/labels/101.json",
                "troubleshooting",
                "2026-01-01T00:00:00Z",
                "2026-01-02T00:00:00Z"
        )
        Label oneArg = new Label("faq")
        Label noArg = new Label()
        Label built = LabelBuilder.builder()
                .id(101L)
                .url("https://example.com/api/v2/help_center/articles/1/labels/101.json")
                .name("troubleshooting")
                .createdAt("2026-01-01T00:00:00Z")
                .updatedAt("2026-01-02T00:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            url() == "https://example.com/api/v2/help_center/articles/1/labels/101.json"
            name() == "troubleshooting"
            createdAt() == "2026-01-01T00:00:00Z"
            updatedAt() == "2026-01-02T00:00:00Z"
        }
        verifyAll(oneArg) {
            id() == null
            url() == null
            name() == "faq"
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(noArg) {
            id() == null
            url() == null
            name() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Label with record withers"() {
        given:
        Label initial = new Label("initial")

        when:
        Label updated = initial
                .withId(202L)
                .withUrl("https://example.com/api/labels/202.json")
                .withName("billing")
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withUpdatedAt("2026-02-02T00:00:00Z")

        then:
        verifyAll(updated) {
            id() == 202L
            url() == "https://example.com/api/labels/202.json"
            name() == "billing"
            createdAt() == "2026-02-01T00:00:00Z"
            updatedAt() == "2026-02-02T00:00:00Z"
        }
    }

    def "should round-trip serialize and deserialize Label"() {
        given:
        Label label = new Label("security")
                .withId(555L)
                .withUrl("https://example.com/api/labels/555.json")
                .withCreatedAt("2026-03-01T00:00:00Z")
                .withUpdatedAt("2026-03-02T00:00:00Z")

        when:
        String json = objectMapper.writeValueAsString(label)
        Map map = objectMapper.readValue(json, Map.class)
        Label deserialized = objectMapper.readValue(json, Label.class)

        then:
        verifyAll(map) {
            get("id") == 555
            get("url") == "https://example.com/api/labels/555.json"
            get("name") == "security"
            get("created_at") == "2026-03-01T00:00:00Z"
            get("updated_at") == "2026-03-02T00:00:00Z"
        }
        verifyAll(deserialized) {
            it == label
        }
    }
}
