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

class CustomObjectsCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomObjectsCreateRequest with canonical constructor and builder"() {
        given:
        CustomObject expected = new CustomObject().setKey("software_license").setTitle("Software License").setTitlePluralized("Software Licenses")

        when:
        CustomObjectsCreateRequest direct = new CustomObjectsCreateRequest(expected)
        CustomObjectsCreateRequest built = CustomObjectsCreateRequestBuilder.builder()
                .customObject(expected)
                .build()

        then:
        verifyAll(direct) {
            customObject() == expected
            customObject().key == "software_license"
            customObject().title == "Software License"
            customObject().titlePluralized == "Software Licenses"
        }
        verifyAll(built) {
            it == direct
            customObject() == expected
            customObject().key == "software_license"
            customObject().title == "Software License"
            customObject().titlePluralized == "Software Licenses"
        }
    }

    def "should update customObject using record wither"() {
        given:
        CustomObject original = new CustomObject().setKey("old_key").setTitle("Old Object")
        CustomObject replacement = new CustomObject().setKey("new_key").setTitle("New Object")
        CustomObjectsCreateRequest request = new CustomObjectsCreateRequest(original)

        when:
        CustomObjectsCreateRequest updated = request.withCustomObject(replacement)

        then:
        verifyAll(request) {
            customObject() == original
            customObject().title == "Old Object"
        }
        verifyAll(updated) {
            customObject() == replacement
            customObject().title == "New Object"
        }
    }

    def "should round-trip serialize and deserialize CustomObjectsCreateRequest"() {
        given:
        CustomObject expected = new CustomObject().setKey("shipment").setTitle("Shipment").setTitlePluralized("Shipments")
        CustomObjectsCreateRequest request = new CustomObjectsCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectsCreateRequest deserialized = objectMapper.readValue(json, CustomObjectsCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("custom_object")
            get("custom_object") instanceof Map
            ((Map) get("custom_object")).key == "shipment"
            ((Map) get("custom_object")).title == "Shipment"
        }
        verifyAll(deserialized) {
            it == request
            customObject() != null
            customObject().key == "shipment"
            customObject().title == "Shipment"
            customObject().titlePluralized == "Shipments"
        }
    }

    def "should handle null customObject component in constructor, wither, and serde"() {
        given:
        CustomObject replacement = new CustomObject().setKey("replacement_key").setTitle("Replacement")

        when:
        CustomObjectsCreateRequest request = new CustomObjectsCreateRequest(null)
        CustomObjectsCreateRequest updated = request.withCustomObject(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectsCreateRequest deserialized = objectMapper.readValue(json, CustomObjectsCreateRequest.class)

        then:
        verifyAll(request) {
            customObject() == null
        }
        verifyAll(updated) {
            customObject() == replacement
            customObject().key == "replacement_key"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("custom_object")
        }
        verifyAll(deserialized) {
            it == request
            customObject() == null
        }
    }
}
