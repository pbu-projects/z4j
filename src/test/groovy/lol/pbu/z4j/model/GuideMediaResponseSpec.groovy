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
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class GuideMediaResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create GuideMediaResponse with canonical constructor and builder"() {
        given:
        GuideMedia expected = new GuideMedia().withId("gm-101").withName("header.png")

        when:
        GuideMediaResponse direct = new GuideMediaResponse(expected)
        GuideMediaResponse built = GuideMediaResponseBuilder.builder().media(expected).build()

        then:
        verifyAll(direct) {
            media == expected
            media.id == "gm-101"
            media.name == "header.png"
        }
        verifyAll(built) {
            media == expected
            media.id == "gm-101"
            media.name == "header.png"
        }
    }

    def "should update media using record wither"() {
        given:
        GuideMedia original = new GuideMedia().withId("gm-1").withName("banner.png")
        GuideMedia updatedMedia = new GuideMedia().withId("gm-2").withName("footer.png")
        GuideMediaResponse response = new GuideMediaResponse(original)

        when:
        GuideMediaResponse updated = response.withMedia(updatedMedia)

        then:
        verifyAll(response) {
            media == original
            media.name == "banner.png"
        }
        verifyAll(updated) {
            media == updatedMedia
            media.name == "footer.png"
        }
    }

    def "should round-trip serialize and deserialize GuideMediaResponse"() {
        given:
        GuideMedia expected = new GuideMedia().withId("gm-555").withName("diagram.svg")
        GuideMediaResponse response = new GuideMediaResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaResponse deserialized = objectMapper.readValue(json, GuideMediaResponse.class)

        then:
        verifyAll(map) {
            containsKey("media")
            get("media") instanceof Map
            ((Map) get("media")).id == "gm-555"
            ((Map) get("media")).name == "diagram.svg"
        }
        verifyAll(deserialized) {
            it == response
            media != null
            media.id == "gm-555"
            media.name == "diagram.svg"
        }
    }

    def "should deserialize GuideMediaResponse from guide_media alias property"() {
        given:
        String json = '{"guide_media":{"id":"gm-777","name":"alias-test.png"}}'

        when:
        GuideMediaResponse deserialized = objectMapper.readValue(json, GuideMediaResponse.class)

        then:
        verifyAll(deserialized) {
            media != null
            media.id == "gm-777"
            media.name == "alias-test.png"
        }
    }

    def "should handle null media component in constructor and wither"() {
        when:
        GuideMediaResponse response = new GuideMediaResponse(null)
        GuideMedia replacement = new GuideMedia().withId("gm-99").withName("icon.png")
        GuideMediaResponse updated = response.withMedia(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaResponse deserialized = objectMapper.readValue(json, GuideMediaResponse.class)

        then:
        verifyAll(response) {
            media() == null
        }
        verifyAll(updated) {
            media() == replacement
            media().id == "gm-99"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("media")
        }
        verifyAll(deserialized) {
            it == response
            media() == null
        }
    }
}
