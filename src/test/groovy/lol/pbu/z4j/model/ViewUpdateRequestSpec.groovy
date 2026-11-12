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

class ViewUpdateRequestSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewUpdateRequest with constructor and builder"() {
        given:
        View expectedView = new View("Updated Queue")

        when:
        ViewUpdateRequest direct = new ViewUpdateRequest(expectedView)
        ViewUpdateRequest built = ViewUpdateRequestBuilder.builder().view(expectedView).build()

        then:
        verifyAll(direct) {
            it.view() == expectedView
            it.view().title() == "Updated Queue"
        }
        verifyAll(built) {
            it == direct
            it.view() == expectedView
        }
    }

    def "should update ViewUpdateRequest using wither"() {
        given:
        View first = new View("Old Queue")
        View second = new View("New Queue")
        ViewUpdateRequest initial = new ViewUpdateRequest(first)

        when:
        ViewUpdateRequest updated = initial.withView(second)

        then:
        verifyAll(initial) {
            it.view() == first
        }
        verifyAll(updated) {
            it.view() == second
            it.view().title() == "New Queue"
        }
    }

    def "should round-trip serialize and deserialize ViewUpdateRequest"() {
        given:
        View expectedView = new View("Code Red Tickets")
                .withRestriction(new ViewRestriction("Group", [10052L, 10057L]))
                .addAllItem(new ViewCondition("priority", "is", "urgent"))
                .withOutput(new ViewOutput(["status", "requester", "assignee"]))
        ViewUpdateRequest request = new ViewUpdateRequest(expectedView)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ViewUpdateRequest deserialized = objectMapper.readValue(json, ViewUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("view")
            ((Map) get("view")).get("title") == "Code Red Tickets"
        }
        verifyAll(deserialized) {
            it == request
            it.view().title() == "Code Red Tickets"
            it.view().restriction().type() == "Group"
            it.view().all().first().value() == "urgent"
        }
    }

    def "should verify ViewUpdateRequest constants"() {
        expect:
        ViewUpdateRequest.JSON_PROPERTY_VIEW == "view"
    }
}
