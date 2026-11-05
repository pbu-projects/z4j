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

import io.micronaut.core.type.Argument
import io.micronaut.serde.ObjectMapper
import java.net.URI
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class ExportResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ExportResponse with canonical constructor and builder"() {
        given:
        Ticket ticket = new Ticket().setId(101L).setSubject("Exported Ticket")
        List<Ticket> expectedResults = [ticket]
        Meta expectedMeta = new Meta().setHasMore(true).setAfterCursor("cursor_1")
        Links expectedLinks = new Links().setNext(URI.create("https://example.zendesk.com/api/v2/search/export?page[after]=cursor_1").toURL())

        when:
        ExportResponse<Ticket> direct = new ExportResponse<>(expectedResults, "type:ticket", expectedMeta, expectedLinks)
        ExportResponse<Ticket> built = ExportResponseBuilder.<Ticket>builder()
                .results(expectedResults)
                .facets("type:ticket")
                .meta(expectedMeta)
                .links(expectedLinks)
                .build()

        then:
        verifyAll(direct) {
            results() == expectedResults
            facets() == "type:ticket"
            meta() == expectedMeta
            links() == expectedLinks
        }
        verifyAll(built) {
            it == direct
            results() == expectedResults
            facets() == "type:ticket"
            meta() == expectedMeta
            links() == expectedLinks
        }
    }

    def "should update ExportResponse using record withers"() {
        given:
        Ticket ticket1 = new Ticket().setId(1L)
        Ticket ticket2 = new Ticket().setId(2L)
        Meta meta1 = new Meta().setHasMore(false)
        Meta meta2 = new Meta().setHasMore(true).setAfterCursor("after_2")
        Links links1 = new Links().setPrev(URI.create("https://example.zendesk.com/prev").toURL())
        Links links2 = new Links().setNext(URI.create("https://example.zendesk.com/next").toURL())
        ExportResponse<Ticket> response = new ExportResponse<>([ticket1], "facets_1", meta1, links1)

        when:
        ExportResponse<Ticket> updated = response
                .withResults([ticket2])
                .withFacets("facets_2")
                .withMeta(meta2)
                .withLinks(links2)

        then:
        verifyAll(response) {
            results() == [ticket1]
            facets() == "facets_1"
            meta() == meta1
            links() == links1
        }
        verifyAll(updated) {
            results() == [ticket2]
            facets() == "facets_2"
            meta() == meta2
            links() == links2
        }
    }

    def "should round-trip serialize and deserialize ExportResponse"() {
        given:
        Ticket ticket = new Ticket().setId(555L).setSubject("Search Export")
        Meta expectedMeta = new Meta().setHasMore(false).setAfterCursor("cursor_555")
        Links expectedLinks = new Links().setNext(URI.create("https://example.zendesk.com/next").toURL())
        ExportResponse<Ticket> response = new ExportResponse<>([ticket], "ticket_facets", expectedMeta, expectedLinks)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ExportResponse<Ticket> deserialized = objectMapper.readValue(json, Argument.of(ExportResponse.class, Ticket.class))

        then:
        verifyAll(map) {
            containsKey("results")
            containsKey("facets")
            containsKey("meta")
            containsKey("links")
        }
        verifyAll(deserialized) {
            results().size() == 1
            results().first().id == 555L
            results().first().subject == "Search Export"
            facets() == "ticket_facets"
            meta() == expectedMeta
            links() == expectedLinks
        }
    }

    def "should handle null components in ExportResponse"() {
        when:
        ExportResponse<Ticket> empty = new ExportResponse<>(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        ExportResponse<Ticket> deserialized = objectMapper.readValue(json, Argument.of(ExportResponse.class, Ticket.class))

        then:
        verifyAll(empty) {
            results() == null
            facets() == null
            meta() == null
            links() == null
        }
        verifyAll(deserialized) {
            it == empty
            results() == null
            facets() == null
            meta() == null
            links() == null
        }
    }
}
