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

class ViewsResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewsResponse with canonical constructor, convenience constructor, and builder"() {
        given:
        View view = new View().setId(101L).setTitle("Unassigned Tickets").setActive(true)
        List<View> expectedViews = [view]

        when:
        ViewsResponse direct = new ViewsResponse(
                expectedViews,
                1,
                "https://example.zendesk.com/api/v2/views.json?page=2",
                "https://example.zendesk.com/api/v2/views.json?page=1"
        )
        ViewsResponse convenience = new ViewsResponse(expectedViews)
        ViewsResponse built = ViewsResponseBuilder.builder()
                .views(expectedViews)
                .count(1)
                .nextPage("https://example.zendesk.com/api/v2/views.json?page=2")
                .previousPage("https://example.zendesk.com/api/v2/views.json?page=1")
                .build()

        then:
        verifyAll(direct) {
            views() == expectedViews
            views().size() == 1
            views().first().id == 101L
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/views.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/views.json?page=1"
        }
        verifyAll(convenience) {
            views() == expectedViews
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(built) {
            it == direct
            views() == expectedViews
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/views.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/views.json?page=1"
        }
    }

    def "should update ViewsResponse using record withers"() {
        given:
        View first = new View().setId(1L).setTitle("First View")
        View second = new View().setId(2L).setTitle("Second View")
        ViewsResponse response = new ViewsResponse([first], 1, "next1", "prev1")

        when:
        ViewsResponse updated = response
                .withViews([second])
                .withCount(2)
                .withNextPage("next2")
                .withPreviousPage("prev2")

        then:
        verifyAll(response) {
            views() == [first]
            count() == 1
            nextPage() == "next1"
            previousPage() == "prev1"
        }
        verifyAll(updated) {
            views() == [second]
            count() == 2
            nextPage() == "next2"
            previousPage() == "prev2"
        }
    }

    def "should round-trip serialize and deserialize ViewsResponse"() {
        given:
        View view = new View().setId(303L).setTitle("Open Support Queue").setActive(true)
        ViewsResponse response = new ViewsResponse([view], 1, "https://example.com/next", "https://example.com/prev")

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ViewsResponse deserialized = objectMapper.readValue(json, ViewsResponse.class)

        then:
        verifyAll(map) {
            containsKey("views")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
            ((List) get("views")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            views().size() == 1
            views().first().id == 303L
            views().first().title == "Open Support Queue"
            count() == 1
            nextPage() == "https://example.com/next"
            previousPage() == "https://example.com/prev"
        }
    }

    def "should handle null components in ViewsResponse"() {
        when:
        ViewsResponse empty = new ViewsResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ViewsResponse deserialized = objectMapper.readValue(json, ViewsResponse.class)

        then:
        verifyAll(empty) {
            views() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            views() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
