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

class CustomObjectSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create CustomObject with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-03-12T10:00:00Z")

        when:
        CustomObject direct = new CustomObject(
                "device",
                "Device",
                "Devices",
                "Office hardware asset",
                "Raw Device",
                "Raw Devices",
                "Raw description",
                true,
                true,
                false,
                "user_101",
                "user_202",
                "https://example.com/custom_objects/device",
                now,
                now
        )
        CustomObject threeArg = new CustomObject("server", "Server", "Servers")
        CustomObject noArg = new CustomObject()
        CustomObject built = CustomObjectBuilder.builder()
                .key("device")
                .title("Device")
                .titlePluralized("Devices")
                .description("Office hardware asset")
                .rawTitle("Raw Device")
                .rawTitlePluralized("Raw Devices")
                .rawDescription("Raw description")
                .includeInListView(true)
                .allowsAttachments(true)
                .allowsPhotos(false)
                .createdByUserId("user_101")
                .updatedByUserId("user_202")
                .url("https://example.com/custom_objects/device")
                .createdAt(now)
                .updatedAt(now)
                .build()

        then:
        verifyAll(direct) {
            key() == "device"
            title() == "Device"
            titlePluralized() == "Devices"
            description() == "Office hardware asset"
            rawTitle() == "Raw Device"
            rawTitlePluralized() == "Raw Devices"
            rawDescription() == "Raw description"
            includeInListView() == true
            allowsAttachments() == true
            allowsPhotos() == false
            createdByUserId() == "user_101"
            updatedByUserId() == "user_202"
            url() == "https://example.com/custom_objects/device"
            createdAt() == now
            updatedAt() == now
        }
        verifyAll(threeArg) {
            key() == "server"
            title() == "Server"
            titlePluralized() == "Servers"
            description() == null
            createdByUserId() == null
        }
        verifyAll(noArg) {
            key() == null
            title() == null
            titlePluralized() == null
            description() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update CustomObject using record withers"() {
        given:
        CustomObject initial = new CustomObject("asset", "Asset", "Assets")
        ZonedDateTime now = ZonedDateTime.parse("2026-03-12T14:00:00Z")

        when:
        CustomObject updated = initial
                .withKey("vehicle")
                .withTitle("Vehicle")
                .withTitlePluralized("Vehicles")
                .withDescription("Company vehicle")
                .withRawTitle("Raw Vehicle")
                .withRawTitlePluralized("Raw Vehicles")
                .withRawDescription("Raw vehicle desc")
                .withIncludeInListView(false)
                .withAllowsAttachments(false)
                .withAllowsPhotos(true)
                .withCreatedByUserId("user_303")
                .withUpdatedByUserId("user_404")
                .withUrl("https://example.com/custom_objects/vehicle")
                .withCreatedAt(now)
                .withUpdatedAt(now)

        then:
        verifyAll(updated) {
            key() == "vehicle"
            title() == "Vehicle"
            titlePluralized() == "Vehicles"
            description() == "Company vehicle"
            rawTitle() == "Raw Vehicle"
            rawTitlePluralized() == "Raw Vehicles"
            rawDescription() == "Raw vehicle desc"
            includeInListView() == false
            allowsAttachments() == false
            allowsPhotos() == true
            createdByUserId() == "user_303"
            updatedByUserId() == "user_404"
            url() == "https://example.com/custom_objects/vehicle"
            createdAt() == now
            updatedAt() == now
        }
    }

    def "should round-trip serialize and deserialize CustomObject"() {
        given:
        CustomObject object = new CustomObject("license", "Software License", "Software Licenses")
                .withDescription("Track enterprise software keys")
                .withIncludeInListView(true)
                .withAllowsAttachments(true)
                .withCreatedByUserId("admin_user")

        when:
        String json = objectMapper.writeValueAsString(object)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObject deserialized = objectMapper.readValue(json, CustomObject.class)

        then:
        verifyAll(map) {
            get("key") == "license"
            get("title") == "Software License"
            get("title_pluralized") == "Software Licenses"
            get("description") == "Track enterprise software keys"
            get("include_in_list_view") == true
            get("allows_attachments") == true
            get("created_by_user_id") == "admin_user"
        }
        verifyAll(deserialized) {
            it == object
        }
    }
}
