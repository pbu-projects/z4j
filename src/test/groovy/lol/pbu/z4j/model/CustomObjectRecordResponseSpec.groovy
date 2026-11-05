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

class CustomObjectRecordResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomObjectRecordResponse with canonical constructor and builder"() {
        given:
        CustomObjectRecord expected = new CustomObjectRecord().withId("rec_101").withName("Primary Server")

        when:
        CustomObjectRecordResponse direct = new CustomObjectRecordResponse(expected)
        CustomObjectRecordResponse built = CustomObjectRecordResponseBuilder.builder().customObjectRecord(expected).build()

        then:
        verifyAll(direct) {
            customObjectRecord == expected
            customObjectRecord.id == "rec_101"
            customObjectRecord.name == "Primary Server"
        }
        verifyAll(built) {
            customObjectRecord == expected
            customObjectRecord.id == "rec_101"
            customObjectRecord.name == "Primary Server"
        }
    }

    def "should update customObjectRecord using record wither"() {
        given:
        CustomObjectRecord original = new CustomObjectRecord().withId("rec_1").withName("Original Record")
        CustomObjectRecord updatedRecord = new CustomObjectRecord().withId("rec_2").withName("Updated Record")
        CustomObjectRecordResponse response = new CustomObjectRecordResponse(original)

        when:
        CustomObjectRecordResponse updated = response.withCustomObjectRecord(updatedRecord)

        then:
        verifyAll(response) {
            customObjectRecord == original
            customObjectRecord.name == "Original Record"
        }
        verifyAll(updated) {
            customObjectRecord == updatedRecord
            customObjectRecord.name == "Updated Record"
        }
    }

    def "should round-trip serialize and deserialize CustomObjectRecordResponse"() {
        given:
        CustomObjectRecord expected = new CustomObjectRecord().withId("rec_555").withName("Database Cluster")
        CustomObjectRecordResponse response = new CustomObjectRecordResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectRecordResponse deserialized = objectMapper.readValue(json, CustomObjectRecordResponse.class)

        then:
        verifyAll(map) {
            containsKey("custom_object_record")
            get("custom_object_record") instanceof Map
            ((Map) get("custom_object_record")).id == "rec_555"
            ((Map) get("custom_object_record")).name == "Database Cluster"
        }
        verifyAll(deserialized) {
            it == response
            customObjectRecord != null
            customObjectRecord.id == "rec_555"
            customObjectRecord.name == "Database Cluster"
        }
    }

    def "should handle null customObjectRecord component in constructor and wither"() {
        when:
        CustomObjectRecordResponse response = new CustomObjectRecordResponse(null)
        CustomObjectRecord replacement = new CustomObjectRecord().withId("rec_99").withName("Backup Unit")
        CustomObjectRecordResponse updated = response.withCustomObjectRecord(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectRecordResponse deserialized = objectMapper.readValue(json, CustomObjectRecordResponse.class)

        then:
        verifyAll(response) {
            customObjectRecord() == null
        }
        verifyAll(updated) {
            customObjectRecord() == replacement
            customObjectRecord().id == "rec_99"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("custom_object_record")
        }
        verifyAll(deserialized) {
            it == response
            customObjectRecord() == null
        }
    }
}
