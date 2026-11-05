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

class ContentTagSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ContentTag with constructors and builder"() {
        when:
        ContentTag direct = new ContentTag(
                "01ARZ3NDEK",
                "troubleshooting",
                "2026-03-22T08:00:00Z",
                "2026-03-22T09:00:00Z"
        )
        ContentTag oneArg = new ContentTag("billing")
        ContentTag noArg = new ContentTag()
        ContentTag built = ContentTagBuilder.builder()
                .id("01ARZ3NDEK")
                .name("troubleshooting")
                .createdAt("2026-03-22T08:00:00Z")
                .updatedAt("2026-03-22T09:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            id() == "01ARZ3NDEK"
            name() == "troubleshooting"
            createdAt() == "2026-03-22T08:00:00Z"
            updatedAt() == "2026-03-22T09:00:00Z"
        }
        verifyAll(oneArg) {
            id() == null
            name() == "billing"
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(noArg) {
            id() == null
            name() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update ContentTag using record withers"() {
        given:
        ContentTag initial = new ContentTag("support")

        when:
        ContentTag updated = initial
                .withId("01ARZ3NDEK2")
                .withName("customer-support")
                .withCreatedAt("2026-03-22T10:00:00Z")
                .withUpdatedAt("2026-03-22T11:00:00Z")

        then:
        verifyAll(updated) {
            id() == "01ARZ3NDEK2"
            name() == "customer-support"
            createdAt() == "2026-03-22T10:00:00Z"
            updatedAt() == "2026-03-22T11:00:00Z"
        }
    }

    def "should round-trip serialize and deserialize ContentTag"() {
        given:
        ContentTag tag = new ContentTag("security")
                .withId("01TAG999")
                .withCreatedAt("2026-03-22T12:00:00Z")

        when:
        String json = objectMapper.writeValueAsString(tag)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTag deserialized = objectMapper.readValue(json, ContentTag.class)

        then:
        verifyAll(map) {
            get("id") == "01TAG999"
            get("name") == "security"
            get("created_at") == "2026-03-22T12:00:00Z"
        }
        verifyAll(deserialized) {
            it == tag
        }
    }
}
