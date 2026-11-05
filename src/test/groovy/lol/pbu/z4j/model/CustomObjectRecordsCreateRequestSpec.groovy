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

class CustomObjectRecordsCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomObjectRecordsCreateRequest with canonical constructor and builder"() {
        given:
        CustomObjectRecord expected = new CustomObjectRecord().setId("rec_101").setName("Laptop Asset").setCustomObjectKey("device")

        when:
        CustomObjectRecordsCreateRequest direct = new CustomObjectRecordsCreateRequest(expected)
        CustomObjectRecordsCreateRequest built = CustomObjectRecordsCreateRequestBuilder.builder()
                .customObjectRecord(expected)
                .build()

        then:
        verifyAll(direct) {
            customObjectRecord() == expected
            customObjectRecord().id == "rec_101"
            customObjectRecord().name == "Laptop Asset"
            customObjectRecord().customObjectKey == "device"
        }
        verifyAll(built) {
            it == direct
            customObjectRecord() == expected
            customObjectRecord().id == "rec_101"
            customObjectRecord().name == "Laptop Asset"
            customObjectRecord().customObjectKey == "device"
        }
    }

    def "should update customObjectRecord using record wither"() {
        given:
        CustomObjectRecord original = new CustomObjectRecord().setId("rec_1").setName("Old Asset")
        CustomObjectRecord replacement = new CustomObjectRecord().setId("rec_2").setName("New Asset")
        CustomObjectRecordsCreateRequest request = new CustomObjectRecordsCreateRequest(original)

        when:
        CustomObjectRecordsCreateRequest updated = request.withCustomObjectRecord(replacement)

        then:
        verifyAll(request) {
            customObjectRecord() == original
            customObjectRecord().name == "Old Asset"
        }
        verifyAll(updated) {
            customObjectRecord() == replacement
            customObjectRecord().name == "New Asset"
        }
    }

    def "should round-trip serialize and deserialize CustomObjectRecordsCreateRequest"() {
        given:
        CustomObjectRecord expected = new CustomObjectRecord().setId("rec_555").setName("Monitor Asset").setCustomObjectKey("hardware")
        CustomObjectRecordsCreateRequest request = new CustomObjectRecordsCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectRecordsCreateRequest deserialized = objectMapper.readValue(json, CustomObjectRecordsCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("custom_object_record")
            get("custom_object_record") instanceof Map
            ((Map) get("custom_object_record")).id == "rec_555"
            ((Map) get("custom_object_record")).name == "Monitor Asset"
        }
        verifyAll(deserialized) {
            it == request
            customObjectRecord() != null
            customObjectRecord().id == "rec_555"
            customObjectRecord().name == "Monitor Asset"
            customObjectRecord().customObjectKey == "hardware"
        }
    }

    def "should handle null customObjectRecord component in constructor, wither, and serde"() {
        given:
        CustomObjectRecord replacement = new CustomObjectRecord().setId("rec_99").setName("Backup Asset")

        when:
        CustomObjectRecordsCreateRequest request = new CustomObjectRecordsCreateRequest(null)
        CustomObjectRecordsCreateRequest updated = request.withCustomObjectRecord(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectRecordsCreateRequest deserialized = objectMapper.readValue(json, CustomObjectRecordsCreateRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            customObjectRecord() == null
        }
    }
}
