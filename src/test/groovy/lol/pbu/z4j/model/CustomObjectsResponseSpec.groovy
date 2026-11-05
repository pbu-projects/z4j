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

class CustomObjectsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomObjectsResponse with canonical constructor and builder"() {
        given:
        CustomObject obj = new CustomObject("asset", "Asset", "Assets")
        List<CustomObject> list = [obj]

        when:
        CustomObjectsResponse direct = new CustomObjectsResponse(list)
        CustomObjectsResponse built = CustomObjectsResponseBuilder.builder()
                .customObjects(list)
                .build()

        then:
        verifyAll(direct) {
            customObjects() == list
            customObjects().size() == 1
            customObjects().first().key == "asset"
        }
        verifyAll(built) {
            it == direct
            customObjects() == list
        }
    }

    def "should update CustomObjectsResponse using record wither and addCustomObjectsItem method"() {
        given:
        CustomObject obj1 = new CustomObject("k1", "T1", "P1")
        CustomObject obj2 = new CustomObject("k2", "T2", "P2")
        CustomObjectsResponse response = new CustomObjectsResponse([obj1])

        when:
        CustomObjectsResponse withNewList = response.withCustomObjects([obj2])
        CustomObjectsResponse withAdded = response.addCustomObjectsItem(obj2)
        CustomObjectsResponse fromNull = new CustomObjectsResponse(null).addCustomObjectsItem(obj1)

        then:
        verifyAll(response) {
            customObjects().size() == 1
            customObjects().first() == obj1
        }
        verifyAll(withNewList) {
            customObjects() == [obj2]
        }
        verifyAll(withAdded) {
            customObjects().size() == 2
            customObjects().containsAll([obj1, obj2])
        }
        verifyAll(fromNull) {
            customObjects().size() == 1
            customObjects().first() == obj1
        }
    }

    def "should round-trip serialize and deserialize CustomObjectsResponse"() {
        given:
        CustomObject obj = new CustomObject("device", "Device", "Devices")
        CustomObjectsResponse response = new CustomObjectsResponse([obj])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectsResponse deserialized = objectMapper.readValue(json, CustomObjectsResponse.class)

        then:
        verifyAll(map) {
            containsKey("custom_objects")
            ((List) get("custom_objects")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            customObjects().size() == 1
            customObjects().first().key == "device"
        }
    }

    def "should handle null customObjects component in constructor, wither, and serde"() {
        when:
        CustomObjectsResponse empty = new CustomObjectsResponse(null)
        CustomObject obj = new CustomObject("k1", "T1", "P1")
        CustomObjectsResponse updated = empty.withCustomObjects([obj])
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectsResponse deserialized = objectMapper.readValue(json, CustomObjectsResponse.class)

        then:
        verifyAll(empty) {
            customObjects() == null
        }
        verifyAll(updated) {
            customObjects() == [obj]
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            customObjects() == null
        }
    }
}
