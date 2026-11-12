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

class ViewCreateRequestSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewCreateRequest with constructor and builder"() {
        given:
        View expectedView = new View("Open Support Queue")
                .addAllItem(new ViewCondition("status", "is", "open"))

        when:
        ViewCreateRequest direct = new ViewCreateRequest(expectedView)
        ViewCreateRequest built = ViewCreateRequestBuilder.builder().view(expectedView).build()

        then:
        verifyAll(direct) {
            it.view() == expectedView
            it.view().title() == "Open Support Queue"
        }
        verifyAll(built) {
            it == direct
            it.view() == expectedView
        }
    }

    def "should update ViewCreateRequest using wither"() {
        given:
        View first = new View("First View")
        View second = new View("Second View")
        ViewCreateRequest initial = new ViewCreateRequest(first)

        when:
        ViewCreateRequest updated = initial.withView(second)

        then:
        verifyAll(initial) {
            it.view() == first
        }
        verifyAll(updated) {
            it.view() == second
            it.view().title() == "Second View"
        }
    }

    def "should round-trip serialize and deserialize ViewCreateRequest"() {
        given:
        View expectedView = new View("Urgent Tickets")
                .withActive(true)
                .withConditions(new ViewConditions([new ViewCondition("priority", "is", "urgent")]))
                .withOutput(new ViewOutput(["status", "priority", "requester"]))
        ViewCreateRequest request = new ViewCreateRequest(expectedView)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ViewCreateRequest deserialized = objectMapper.readValue(json, ViewCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("view")
            ((Map) get("view")).get("title") == "Urgent Tickets"
        }
        verifyAll(deserialized) {
            it == request
            it.view().title() == "Urgent Tickets"
            it.view().conditions().all().first().field() == "priority"
            it.view().output().columns() == ["status", "priority", "requester"]
        }
    }

    def "should verify ViewCreateRequest constants"() {
        expect:
        ViewCreateRequest.JSON_PROPERTY_VIEW == "view"
    }
}
