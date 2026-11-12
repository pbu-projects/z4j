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

class ViewOutputSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewOutput with constructors and builder"() {
        when:
        ViewOutput direct = new ViewOutput(
                ["status", "requester", "assignee"],
                "assignee",
                "desc",
                "status",
                "asc"
        )
        ViewOutput colsOnly = new ViewOutput(["status", "priority"])
        ViewOutput noArg = new ViewOutput()
        ViewOutput built = ViewOutputBuilder.builder()
                .columns(["status", "requester", "assignee"])
                .groupBy("assignee")
                .groupOrder("desc")
                .sortBy("status")
                .sortOrder("asc")
                .build()

        then:
        verifyAll(direct) {
            columns() == ["status", "requester", "assignee"]
            groupBy() == "assignee"
            groupOrder() == "desc"
            sortBy() == "status"
            sortOrder() == "asc"
        }
        verifyAll(colsOnly) {
            columns() == ["status", "priority"]
            groupBy() == null
            groupOrder() == null
            sortBy() == null
            sortOrder() == null
        }
        verifyAll(noArg) {
            columns() == null
            groupBy() == null
            groupOrder() == null
            sortBy() == null
            sortOrder() == null
        }
        verifyAll(built) {
            it == direct
            columns() == ["status", "requester", "assignee"]
        }
    }

    def "should update ViewOutput using withers, SortOrder overloads, and addColumnsItem"() {
        given:
        ViewOutput initial = new ViewOutput()

        when:
        ViewOutput withFirstCol = initial.addColumnsItem("status")
        ViewOutput withSecondCol = withFirstCol.addColumnsItem("requester")
        ViewOutput withSortOrders = withSecondCol
                .withGroupBy("assignee")
                .withGroupOrder(SortOrder.DESCENDING)
                .withSortBy("status")
                .withSortOrder(SortOrder.ASCENDING)
        ViewOutput withNullOrders = withSortOrders
                .withGroupOrder((SortOrder) null)
                .withSortOrder((SortOrder) null)

        then:
        verifyAll(withFirstCol) {
            columns() == ["status"]
        }
        verifyAll(withSecondCol) {
            columns() == ["status", "requester"]
        }
        verifyAll(withSortOrders) {
            groupBy() == "assignee"
            groupOrder() == "desc"
            sortBy() == "status"
            sortOrder() == "asc"
        }
        verifyAll(withNullOrders) {
            groupOrder() == null
            sortOrder() == null
        }
    }

    def "should round-trip serialize and deserialize ViewOutput"() {
        given:
        ViewOutput output = new ViewOutput(["status", "description"])
                .withGroupBy("status")
                .withGroupOrder("asc")
                .withSortBy("updated")
                .withSortOrder("desc")

        when:
        String json = objectMapper.writeValueAsString(output)
        Map map = objectMapper.readValue(json, Map.class)
        ViewOutput deserialized = objectMapper.readValue(json, ViewOutput.class)

        then:
        verifyAll(map) {
            get("columns") == ["status", "description"]
            get("group_by") == "status"
            get("group_order") == "asc"
            get("sort_by") == "updated"
            get("sort_order") == "desc"
        }
        verifyAll(deserialized) {
            it == output
            columns() == ["status", "description"]
            groupBy() == "status"
            sortBy() == "updated"
        }
    }

    def "should verify ViewOutput constants"() {
        expect:
        verifyAll {
            ViewOutput.JSON_PROPERTY_COLUMNS == "columns"
            ViewOutput.JSON_PROPERTY_GROUP_BY == "group_by"
            ViewOutput.JSON_PROPERTY_GROUP_ORDER == "group_order"
            ViewOutput.JSON_PROPERTY_SORT_BY == "sort_by"
            ViewOutput.JSON_PROPERTY_SORT_ORDER == "sort_order"
        }
    }
}
