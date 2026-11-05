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

class GuideMediaSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create GuideMedia with constructors and builder"() {
        when:
        GuideMedia direct = new GuideMedia(
                "gm-101",
                "key123",
                "banner.png",
                2048L,
                "https://example.com/media/banner.png",
                "image/png",
                1,
                "2026-01-01T00:00:00Z",
                "2026-01-02T00:00:00Z"
        )
        GuideMedia oneArg = new GuideMedia("logo.png")
        GuideMedia noArg = new GuideMedia()
        GuideMedia built = GuideMediaBuilder.builder()
                .id("gm-101")
                .accessKey("key123")
                .name("banner.png")
                .size(2048L)
                .url("https://example.com/media/banner.png")
                .contentType("image/png")
                .version(1)
                .createdAt("2026-01-01T00:00:00Z")
                .updatedAt("2026-01-02T00:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            id() == "gm-101"
            accessKey() == "key123"
            name() == "banner.png"
            size() == 2048L
            url() == "https://example.com/media/banner.png"
            contentType() == "image/png"
            version() == 1
            createdAt() == "2026-01-01T00:00:00Z"
            updatedAt() == "2026-01-02T00:00:00Z"
        }
        verifyAll(oneArg) {
            id() == null
            accessKey() == null
            name() == "logo.png"
            size() == null
            url() == null
            contentType() == null
            version() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(noArg) {
            id() == null
            accessKey() == null
            name() == null
            size() == null
            url() == null
            contentType() == null
            version() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update GuideMedia with record withers"() {
        given:
        GuideMedia initial = new GuideMedia("initial.png")

        when:
        GuideMedia updated = initial
                .withId("gm-202")
                .withAccessKey("key456")
                .withName("footer.png")
                .withSize(4096L)
                .withUrl("https://example.com/media/footer.png")
                .withContentType("image/png")
                .withVersion(2)
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withUpdatedAt("2026-02-02T00:00:00Z")

        then:
        verifyAll(updated) {
            id() == "gm-202"
            accessKey() == "key456"
            name() == "footer.png"
            size() == 4096L
            url() == "https://example.com/media/footer.png"
            contentType() == "image/png"
            version() == 2
            createdAt() == "2026-02-01T00:00:00Z"
            updatedAt() == "2026-02-02T00:00:00Z"
        }
    }

    def "should round-trip serialize and deserialize GuideMedia"() {
        given:
        GuideMedia media = new GuideMedia("diagram.svg")
                .withId("gm-555")
                .withAccessKey("key789")
                .withSize(8192L)
                .withUrl("https://example.com/media/diagram.svg")
                .withContentType("image/svg+xml")
                .withVersion(3)
                .withCreatedAt("2026-03-01T00:00:00Z")
                .withUpdatedAt("2026-03-02T00:00:00Z")

        when:
        String json = objectMapper.writeValueAsString(media)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMedia deserialized = objectMapper.readValue(json, GuideMedia.class)

        then:
        verifyAll(map) {
            get("id") == "gm-555"
            get("access_key") == "key789"
            get("name") == "diagram.svg"
            get("size") == 8192
            get("url") == "https://example.com/media/diagram.svg"
            get("content_type") == "image/svg+xml"
            get("version") == 3
            get("created_at") == "2026-03-01T00:00:00Z"
            get("updated_at") == "2026-03-02T00:00:00Z"
        }
        verifyAll(deserialized) {
            it == media
        }
    }
}
