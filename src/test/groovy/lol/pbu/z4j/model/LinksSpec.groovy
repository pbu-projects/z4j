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

import java.net.URI
import java.net.URL

class LinksSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Links with canonical constructor and builder"() {
        given:
        URL prevUrl = URI.create("https://example.zendesk.com/api/v2/tickets.json?page[before]=cursor_prev").toURL()
        URL nextUrl = URI.create("https://example.zendesk.com/api/v2/tickets.json?page[after]=cursor_next").toURL()

        when:
        Links direct = new Links(prevUrl, nextUrl)
        Links built = LinksBuilder.builder()
                .prev(prevUrl)
                .next(nextUrl)
                .build()

        then:
        verifyAll(direct) {
            prev() == prevUrl
            next() == nextUrl
        }
        verifyAll(built) {
            it == direct
            prev() == prevUrl
            next() == nextUrl
        }
    }

    def "should update Links using record withers"() {
        given:
        URL prevUrl1 = URI.create("https://example.zendesk.com/prev1").toURL()
        URL nextUrl1 = URI.create("https://example.zendesk.com/next1").toURL()
        URL prevUrl2 = URI.create("https://example.zendesk.com/prev2").toURL()
        URL nextUrl2 = URI.create("https://example.zendesk.com/next2").toURL()
        Links original = new Links(prevUrl1, nextUrl1)

        when:
        Links updated = original
                .withPrev(prevUrl2)
                .withNext(nextUrl2)

        then:
        verifyAll(original) {
            prev() == prevUrl1
            next() == nextUrl1
        }
        verifyAll(updated) {
            prev() == prevUrl2
            next() == nextUrl2
        }
    }

    def "should round-trip serialize and deserialize Links"() {
        given:
        URL prevUrl = URI.create("https://example.zendesk.com/prev").toURL()
        URL nextUrl = URI.create("https://example.zendesk.com/next").toURL()
        Links links = new Links(prevUrl, nextUrl)

        when:
        String json = objectMapper.writeValueAsString(links)
        Map map = objectMapper.readValue(json, Map.class)
        Links deserialized = objectMapper.readValue(json, Links.class)

        then:
        verifyAll(map) {
            get("prev") == prevUrl.toString()
            get("next") == nextUrl.toString()
        }
        verifyAll(deserialized) {
            it == links
            prev() == prevUrl
            next() == nextUrl
        }
    }

    def "should handle null components in Links constructor, wither, and serde"() {
        when:
        Links empty = new Links(null, null)
        Links built = LinksBuilder.builder().build()
        URL dummy = URI.create("https://example.zendesk.com").toURL()
        Links cleared = new Links(dummy, dummy).withPrev(null).withNext(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        Links deserialized = objectMapper.readValue(json, Links.class)

        then:
        verifyAll(empty) {
            prev() == null
            next() == null
        }
        verifyAll(built) {
            it == empty
            prev() == null
            next() == null
        }
        verifyAll(cleared) {
            it == empty
            prev() == null
            next() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            prev() == null
            next() == null
        }
    }
}
