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

class ViewColumnSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewColumn with constructors and builder"() {
        when:
        ViewColumn direct = new ViewColumn(
                "status",
                "Status",
                "tagger",
                "https://example.zendesk.com/api/v2/ticket_fields/1.json",
                true,
                true,
                "desc"
        )
        ViewColumn twoArg = new ViewColumn("subject", "Subject")
        ViewColumn noArg = new ViewColumn()
        ViewColumn built = ViewColumnBuilder.builder()
                .id("status")
                .title("Status")
                .type("tagger")
                .url("https://example.zendesk.com/api/v2/ticket_fields/1.json")
                .filterable(true)
                .sortable(true)
                .order("desc")
                .build()

        then:
        verifyAll(direct) {
            id() == "status"
            title() == "Status"
            type() == "tagger"
            url() == "https://example.zendesk.com/api/v2/ticket_fields/1.json"
            filterable() == true
            sortable() == true
            order() == "desc"
        }
        verifyAll(twoArg) {
            id() == "subject"
            title() == "Subject"
            type() == null
            url() == null
            filterable() == null
            sortable() == null
            order() == null
        }
        verifyAll(noArg) {
            id() == null
            title() == null
            type() == null
            url() == null
            filterable() == null
            sortable() == null
            order() == null
        }
        verifyAll(built) {
            it == direct
            id() == "status"
            title() == "Status"
        }
    }

    def "should update ViewColumn using withers"() {
        given:
        ViewColumn initial = new ViewColumn()

        when:
        ViewColumn updated = initial
                .withId("priority")
                .withTitle("Priority")
                .withType("priority")
                .withUrl("https://example.zendesk.com/api/v2/ticket_fields/2.json")
                .withFilterable(true)
                .withSortable(false)
                .withOrder("asc")

        then:
        verifyAll(updated) {
            id() == "priority"
            title() == "Priority"
            type() == "priority"
            url() == "https://example.zendesk.com/api/v2/ticket_fields/2.json"
            filterable() == true
            sortable() == false
            order() == "asc"
        }
    }

    def "should round-trip serialize and deserialize ViewColumn"() {
        given:
        ViewColumn column = new ViewColumn("updated", "Updated")
                .withFilterable(true)
                .withSortable(true)
                .withOrder("desc")

        when:
        String json = objectMapper.writeValueAsString(column)
        Map map = objectMapper.readValue(json, Map.class)
        ViewColumn deserialized = objectMapper.readValue(json, ViewColumn.class)

        then:
        verifyAll(map) {
            get("id") == "updated"
            get("title") == "Updated"
            get("filterable") == true
            get("sortable") == true
            get("order") == "desc"
        }
        verifyAll(deserialized) {
            it == column
            id() == "updated"
            title() == "Updated"
            filterable() == true
            sortable() == true
            order() == "desc"
        }
    }

    def "should verify ViewColumn constants"() {
        expect:
        verifyAll {
            ViewColumn.JSON_PROPERTY_ID == "id"
            ViewColumn.JSON_PROPERTY_TITLE == "title"
            ViewColumn.JSON_PROPERTY_TYPE == "type"
            ViewColumn.JSON_PROPERTY_URL == "url"
            ViewColumn.JSON_PROPERTY_FILTERABLE == "filterable"
            ViewColumn.JSON_PROPERTY_SORTABLE == "sortable"
            ViewColumn.JSON_PROPERTY_ORDER == "order"
        }
    }
}
