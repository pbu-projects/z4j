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

class ContentTagsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ContentTagsResponse with canonical constructor and builder"() {
        given:
        ContentTag tag = new ContentTag().setId("01TAG1").setName("billing")
        List<ContentTag> list = [tag]
        Meta expectedMeta = MetaBuilder.builder().hasMore(true).afterCursor("after_1").build()

        when:
        ContentTagsResponse direct = new ContentTagsResponse(list, expectedMeta)
        ContentTagsResponse built = ContentTagsResponseBuilder.builder()
                .records(list)
                .meta(expectedMeta)
                .build()

        then:
        verifyAll(direct) {
            records() == list
            records().size() == 1
            records().first().id == "01TAG1"
            records().first().name == "billing"
            meta() == expectedMeta
        }
        verifyAll(built) {
            it == direct
            records() == list
            meta() == expectedMeta
        }
    }

    def "should update ContentTagsResponse using record withers and addRecordsItem method"() {
        given:
        ContentTag first = new ContentTag().setId("01TAG1").setName("billing")
        ContentTag second = new ContentTag().setId("01TAG2").setName("account")
        Meta meta1 = MetaBuilder.builder().hasMore(false).build()
        Meta meta2 = MetaBuilder.builder().hasMore(true).afterCursor("next_cursor").build()
        ContentTagsResponse response = new ContentTagsResponse([first], meta1)

        when:
        ContentTagsResponse updated = response.withRecords([second]).withMeta(meta2)
        ContentTagsResponse withAdded = response.addRecordsItem(second)
        ContentTagsResponse fromNull = new ContentTagsResponse(null, meta1).addRecordsItem(first)

        then:
        verifyAll(response) {
            records().size() == 1
            records().first().id == "01TAG1"
            meta() == meta1
        }
        verifyAll(updated) {
            records() == [second]
            meta() == meta2
        }
        verifyAll(withAdded) {
            records().size() == 2
            records().first().id == "01TAG1"
            records().last().id == "01TAG2"
            meta() == meta1
        }
        verifyAll(fromNull) {
            records().size() == 1
            records().first().id == "01TAG1"
            meta() == meta1
        }
    }

    def "should round-trip serialize and deserialize ContentTagsResponse"() {
        given:
        ContentTag tag = new ContentTag().setId("01TAG555").setName("onboarding")
        Meta expectedMeta = MetaBuilder.builder().hasMore(false).afterCursor("cursor_end").build()
        ContentTagsResponse response = new ContentTagsResponse([tag], expectedMeta)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagsResponse deserialized = objectMapper.readValue(json, ContentTagsResponse.class)

        then:
        verifyAll(map) {
            containsKey("records")
            containsKey("meta")
            ((List) get("records")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            records().size() == 1
            records().first().id == "01TAG555"
            records().first().name == "onboarding"
            meta().hasMore == false
            meta().afterCursor == "cursor_end"
        }
    }

    def "should handle null components in ContentTagsResponse"() {
        when:
        ContentTagsResponse empty = new ContentTagsResponse(null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagsResponse deserialized = objectMapper.readValue(json, ContentTagsResponse.class)

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
