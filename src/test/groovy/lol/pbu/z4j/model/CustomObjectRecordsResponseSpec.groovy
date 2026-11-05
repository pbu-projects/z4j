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

class CustomObjectRecordsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomObjectRecordsResponse with canonical constructor and builder"() {
        given:
        CustomObjectRecord record = new CustomObjectRecord().withId("rec_1").withName("Laptop 1")
        List<CustomObjectRecord> list = [record]
        Map<String, Object> expectedMeta = [has_more: true, after_cursor: "cursor_1"]
        Map<String, Object> expectedLinks = [next: "https://example.zendesk.com/next"]

        when:
        CustomObjectRecordsResponse direct = new CustomObjectRecordsResponse(list, expectedMeta, expectedLinks)
        CustomObjectRecordsResponse built = CustomObjectRecordsResponseBuilder.builder()
                .customObjectRecords(list)
                .meta(expectedMeta)
                .links(expectedLinks)
                .build()

        then:
        verifyAll(direct) {
            customObjectRecords() == list
            meta() == expectedMeta
            links() == expectedLinks
        }
        verifyAll(built) {
            it == direct
            customObjectRecords() == list
            meta() == expectedMeta
            links() == expectedLinks
        }
    }

    def "should update CustomObjectRecordsResponse using record withers and addCustomObjectRecordsItem method"() {
        given:
        CustomObjectRecord record1 = new CustomObjectRecord().withId("r1").withName("R1")
        CustomObjectRecord record2 = new CustomObjectRecord().withId("r2").withName("R2")
        Map<String, Object> meta1 = [has_more: false]
        Map<String, Object> meta2 = [has_more: true]
        Map<String, Object> links1 = [prev: "prev_url"]
        Map<String, Object> links2 = [next: "next_url"]
        CustomObjectRecordsResponse response = new CustomObjectRecordsResponse([record1], meta1, links1)

        when:
        CustomObjectRecordsResponse updated = response
                .withCustomObjectRecords([record2])
                .withMeta(meta2)
                .withLinks(links2)
        CustomObjectRecordsResponse withAdded = response.addCustomObjectRecordsItem(record2)
        CustomObjectRecordsResponse fromNull = new CustomObjectRecordsResponse(null, meta1, links1).addCustomObjectRecordsItem(record1)

        then:
        verifyAll(response) {
            customObjectRecords().size() == 1
            customObjectRecords().first() == record1
            meta() == meta1
            links() == links1
        }
        verifyAll(updated) {
            customObjectRecords() == [record2]
            meta() == meta2
            links() == links2
        }
        verifyAll(withAdded) {
            customObjectRecords().size() == 2
            customObjectRecords().containsAll([record1, record2])
            meta() == meta1
            links() == links1
        }
        verifyAll(fromNull) {
            customObjectRecords().size() == 1
            customObjectRecords().first() == record1
            meta() == meta1
            links() == links1
        }
    }

    def "should round-trip serialize and deserialize CustomObjectRecordsResponse"() {
        given:
        CustomObjectRecord record = new CustomObjectRecord().withId("rec_99").withName("Asset 99")
        Map<String, Object> expectedMeta = [has_more: false]
        Map<String, Object> expectedLinks = [next: "https://example.zendesk.com/next"]
        CustomObjectRecordsResponse response = new CustomObjectRecordsResponse([record], expectedMeta, expectedLinks)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectRecordsResponse deserialized = objectMapper.readValue(json, CustomObjectRecordsResponse.class)

        then:
        verifyAll(map) {
            containsKey("custom_object_records")
            containsKey("meta")
            containsKey("links")
        }
        verifyAll(deserialized) {
            it == response
            customObjectRecords().size() == 1
            customObjectRecords().first().id == "rec_99"
            meta() == expectedMeta
            links() == expectedLinks
        }
    }

    def "should handle null components in CustomObjectRecordsResponse"() {
        when:
        CustomObjectRecordsResponse empty = new CustomObjectRecordsResponse(null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectRecordsResponse deserialized = objectMapper.readValue(json, CustomObjectRecordsResponse.class)

        then:
        verifyAll(empty) {
            customObjectRecords() == null
            meta() == null
            links() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            customObjectRecords() == null
            meta() == null
            links() == null
        }
    }
}
