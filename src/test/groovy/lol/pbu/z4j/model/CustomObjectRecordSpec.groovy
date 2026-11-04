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

import java.time.ZonedDateTime

class CustomObjectRecordSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create CustomObjectRecord with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-03-15T09:00:00Z")
        Map<String, Object> expectedFields = [serial_number: "SN-999", model: "Latitude"]
        Map<String, Object> expectedPhoto = [url: "https://example.com/laptop.jpg"]

        when:
        CustomObjectRecord direct = new CustomObjectRecord(
                "rec_101",
                "Laptop 101",
                "device",
                expectedFields,
                "ext_99",
                expectedPhoto,
                "user_1",
                "user_2",
                "https://example.com/records/rec_101",
                now,
                now
        )
        CustomObjectRecord noArg = new CustomObjectRecord()
        CustomObjectRecord built = CustomObjectRecordBuilder.builder()
                .id("rec_101")
                .name("Laptop 101")
                .customObjectKey("device")
                .customObjectFields(expectedFields)
                .externalId("ext_99")
                .photo(expectedPhoto)
                .createdByUserId("user_1")
                .updatedByUserId("user_2")
                .url("https://example.com/records/rec_101")
                .createdAt(now)
                .updatedAt(now)
                .build()

        then:
        verifyAll(direct) {
            id() == "rec_101"
            name() == "Laptop 101"
            customObjectKey() == "device"
            customObjectFields() == expectedFields
            externalId() == "ext_99"
            photo() == expectedPhoto
            createdByUserId() == "user_1"
            updatedByUserId() == "user_2"
            url() == "https://example.com/records/rec_101"
            createdAt() == now
            updatedAt() == now
        }
        verifyAll(noArg) {
            id() == null
            name() == null
            customObjectKey() == null
            customObjectFields() == null
            externalId() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update CustomObjectRecord using record withers"() {
        given:
        CustomObjectRecord initial = new CustomObjectRecord().withId("rec_1")
        ZonedDateTime now = ZonedDateTime.parse("2026-03-15T11:00:00Z")
        Map<String, Object> updatedFields = [ip: "192.168.1.1"]
        Map<String, Object> updatedPhoto = [thumb: "https://example.com/thumb.jpg"]

        when:
        CustomObjectRecord updated = initial
                .withId("rec_2")
                .withName("Router 2")
                .withCustomObjectKey("network_device")
                .withCustomObjectFields(updatedFields)
                .withExternalId("ext_200")
                .withPhoto(updatedPhoto)
                .withCreatedByUserId("admin_1")
                .withUpdatedByUserId("admin_2")
                .withUrl("https://example.com/records/rec_2")
                .withCreatedAt(now)
                .withUpdatedAt(now)

        then:
        verifyAll(updated) {
            id() == "rec_2"
            name() == "Router 2"
            customObjectKey() == "network_device"
            customObjectFields() == updatedFields
            externalId() == "ext_200"
            photo() == updatedPhoto
            createdByUserId() == "admin_1"
            updatedByUserId() == "admin_2"
            url() == "https://example.com/records/rec_2"
            createdAt() == now
            updatedAt() == now
        }
    }

    def "should put custom object fields item defensively"() {
        given:
        CustomObjectRecord fromNull = new CustomObjectRecord()
        CustomObjectRecord fromExisting = new CustomObjectRecord()
                .withCustomObjectFields([initial_key: "initial_value"])

        when:
        CustomObjectRecord updatedNull = fromNull.putCustomObjectFieldsItem("k1", "v1")
        CustomObjectRecord updatedExisting = fromExisting.putCustomObjectFieldsItem("k2", "v2")

        then:
        verifyAll(updatedNull) {
            customObjectFields().size() == 1
            customObjectFields()["k1"] == "v1"
        }
        verifyAll(updatedExisting) {
            customObjectFields().size() == 2
            customObjectFields()["initial_key"] == "initial_value"
            customObjectFields()["k2"] == "v2"
        }
    }

    def "should round-trip serialize and deserialize CustomObjectRecord"() {
        given:
        CustomObjectRecord record = new CustomObjectRecord()
                .withId("rec_888")
                .withName("Conference Room Display")
                .withCustomObjectKey("display")
                .withExternalId("asset-888")
                .withCustomObjectFields([resolution: "4K", location: "Room 101"])

        when:
        String json = objectMapper.writeValueAsString(record)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectRecord deserialized = objectMapper.readValue(json, CustomObjectRecord.class)

        then:
        verifyAll(map) {
            get("id") == "rec_888"
            get("name") == "Conference Room Display"
            get("custom_object_key") == "display"
            get("external_id") == "asset-888"
            get("custom_object_fields") instanceof Map
            ((Map) get("custom_object_fields")).resolution == "4K"
        }
        verifyAll(deserialized) {
            it == record
        }
    }
}
