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

class GuideMediasResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create GuideMediasResponse with canonical constructor and builder"() {
        given:
        GuideMedia media = new GuideMedia().withId("01MEDIA1").withName("banner.png")
        List<GuideMedia> list = [media]
        Meta expectedMeta = MetaBuilder.builder().hasMore(true).afterCursor("cursor_1").build()

        when:
        GuideMediasResponse direct = new GuideMediasResponse(list, expectedMeta)
        GuideMediasResponse built = GuideMediasResponseBuilder.builder()
                .records(list)
                .meta(expectedMeta)
                .build()

        then:
        verifyAll(direct) {
            records() == list
            records().size() == 1
            records().first().id == "01MEDIA1"
            records().first().name == "banner.png"
            meta() == expectedMeta
        }
        verifyAll(built) {
            it == direct
            records() == list
            meta() == expectedMeta
        }
    }

    def "should update GuideMediasResponse using record withers and addRecordsItem method"() {
        given:
        GuideMedia first = new GuideMedia().withId("01M1").withName("one.png")
        GuideMedia second = new GuideMedia().withId("01M2").withName("two.png")
        Meta meta1 = MetaBuilder.builder().hasMore(false).build()
        Meta meta2 = MetaBuilder.builder().hasMore(true).afterCursor("after_2").build()
        GuideMediasResponse response = new GuideMediasResponse([first], meta1)

        when:
        GuideMediasResponse updated = response.withRecords([second]).withMeta(meta2)
        GuideMediasResponse withAdded = response.addRecordsItem(second)
        GuideMediasResponse fromNull = new GuideMediasResponse(null, meta1).addRecordsItem(first)

        then:
        verifyAll(response) {
            records().size() == 1
            records().first() == first
            meta() == meta1
        }
        verifyAll(updated) {
            records() == [second]
            meta() == meta2
        }
        verifyAll(withAdded) {
            records().size() == 2
            records().containsAll([first, second])
            meta() == meta1
        }
        verifyAll(fromNull) {
            records().size() == 1
            records().first() == first
            meta() == meta1
        }
    }

    def "should round-trip serialize and deserialize GuideMediasResponse"() {
        given:
        GuideMedia media = new GuideMedia().withId("01M555").withName("logo.svg")
        Meta expectedMeta = MetaBuilder.builder().hasMore(false).build()
        GuideMediasResponse response = new GuideMediasResponse([media], expectedMeta)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediasResponse deserialized = objectMapper.readValue(json, GuideMediasResponse.class)

        then:
        verifyAll(map) {
            containsKey("records")
            containsKey("meta")
            ((List) get("records")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            records().size() == 1
            records().first().id == "01M555"
            records().first().name == "logo.svg"
            meta().hasMore == false
        }
    }

    def "should handle null components in GuideMediasResponse"() {
        when:
        GuideMediasResponse empty = new GuideMediasResponse(null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediasResponse deserialized = objectMapper.readValue(json, GuideMediasResponse.class)

        then:
        verifyAll(empty) {
            records() == null
            meta() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            records() == null
            meta() == null
        }
    }
}
