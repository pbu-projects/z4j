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

class ViewResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ViewResponse with canonical constructor and builder"() {
        given:
        View expected = new View().withId(101L).withTitle("Unassigned Tickets").withActive(true)

        when:
        ViewResponse direct = new ViewResponse(expected)
        ViewResponse built = ViewResponseBuilder.builder().view(expected).build()

        then:
        verifyAll(direct) {
            view == expected
            view.id == 101L
            view.title == "Unassigned Tickets"
            view.active == true
        }
        verifyAll(built) {
            view == expected
            view.id == 101L
            view.title == "Unassigned Tickets"
            view.active == true
        }
    }

    def "should update view using record wither"() {
        given:
        View original = new View().withId(1L).withTitle("Old View")
        View updatedView = new View().withId(2L).withTitle("New View")
        ViewResponse response = new ViewResponse(original)

        when:
        ViewResponse updated = response.withView(updatedView)

        then:
        verifyAll(response) {
            view == original
            view.title == "Old View"
        }
        verifyAll(updated) {
            view == updatedView
            view.title == "New View"
        }
    }

    def "should round-trip serialize and deserialize ViewResponse"() {
        given:
        View expected = new View().withId(555L).withTitle("My Open Tickets").withActive(true)
        ViewResponse response = new ViewResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ViewResponse deserialized = objectMapper.readValue(json, ViewResponse.class)

        then:
        verifyAll(map) {
            containsKey("view")
            get("view") instanceof Map
            ((Map) get("view")).id == 555
            ((Map) get("view")).title == "My Open Tickets"
        }
        verifyAll(deserialized) {
            it == response
            view != null
            view.id == 555L
            view.title == "My Open Tickets"
            view.active == true
        }
    }

    def "should handle null view component in constructor and wither"() {
        when:
        ViewResponse response = new ViewResponse(null)
        View replacement = new View().withId(99L).withTitle("Pending Review")
        ViewResponse updated = response.withView(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ViewResponse deserialized = objectMapper.readValue(json, ViewResponse.class)

        then:
        verifyAll(response) {
            view() == null
        }
        verifyAll(updated) {
            view() == replacement
            view().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("view")
        }
        verifyAll(deserialized) {
            it == response
            view() == null
        }
    }
}
