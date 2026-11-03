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

class CustomObjectResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomObjectResponse with canonical constructor and builder"() {
        given:
        CustomObject expected = new CustomObject("device", "Device", "Devices").setDescription("Office hardware")

        when:
        CustomObjectResponse direct = new CustomObjectResponse(expected)
        CustomObjectResponse built = CustomObjectResponseBuilder.builder().customObject(expected).build()

        then:
        verifyAll(direct) {
            customObject == expected
            customObject.key == "device"
            customObject.title == "Device"
            customObject.titlePluralized == "Devices"
        }
        verifyAll(built) {
            customObject == expected
            customObject.key == "device"
            customObject.title == "Device"
            customObject.titlePluralized == "Devices"
        }
    }

    def "should update customObject using record wither"() {
        given:
        CustomObject original = new CustomObject("car", "Car", "Cars")
        CustomObject updatedObject = new CustomObject("truck", "Truck", "Trucks")
        CustomObjectResponse response = new CustomObjectResponse(original)

        when:
        CustomObjectResponse updated = response.withCustomObject(updatedObject)

        then:
        verifyAll(response) {
            customObject == original
            customObject.key == "car"
        }
        verifyAll(updated) {
            customObject == updatedObject
            customObject.key == "truck"
        }
    }

    def "should round-trip serialize and deserialize CustomObjectResponse"() {
        given:
        CustomObject expected = new CustomObject("server", "Server", "Servers").setDescription("Cloud server")
        CustomObjectResponse response = new CustomObjectResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectResponse deserialized = objectMapper.readValue(json, CustomObjectResponse.class)

        then:
        verifyAll(map) {
            containsKey("custom_object")
            get("custom_object") instanceof Map
            ((Map) get("custom_object")).key == "server"
            ((Map) get("custom_object")).title == "Server"
        }
        verifyAll(deserialized) {
            it == response
            customObject != null
            customObject.key == "server"
            customObject.title == "Server"
            customObject.description == "Cloud server"
        }
    }

    def "should handle null customObject component in constructor and wither"() {
        when:
        CustomObjectResponse response = new CustomObjectResponse(null)
        CustomObject replacement = new CustomObject("router", "Router", "Routers")
        CustomObjectResponse updated = response.withCustomObject(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectResponse deserialized = objectMapper.readValue(json, CustomObjectResponse.class)

        then:
        verifyAll(response) {
            customObject() == null
        }
        verifyAll(updated) {
            customObject() == replacement
            customObject().key == "router"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("custom_object")
        }
        verifyAll(deserialized) {
            it == response
            customObject() == null
        }
    }
}
