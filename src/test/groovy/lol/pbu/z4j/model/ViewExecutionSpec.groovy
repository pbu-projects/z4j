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

class ViewExecutionSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewExecution with constructors and builder"() {
        given:
        ViewColumn groupCol = new ViewColumn("status", "Status").withOrder("asc")
        ViewColumn sortCol = new ViewColumn("updated", "Updated").withOrder("desc")
        ViewColumn customCol = new ViewColumn("5", "Account").withType("text")

        when:
        ViewExecution direct = new ViewExecution(
                "status",
                "asc",
                "updated",
                "desc",
                groupCol,
                sortCol,
                [groupCol, sortCol],
                [groupCol],
                [customCol]
        )
        ViewExecution noArg = new ViewExecution()
        ViewExecution built = ViewExecutionBuilder.builder()
                .groupBy("status")
                .groupOrder("asc")
                .sortBy("updated")
                .sortOrder("desc")
                .group(groupCol)
                .sort(sortCol)
                .columns([groupCol, sortCol])
                .fields([groupCol])
                .customFields([customCol])
                .build()

        then:
        verifyAll(direct) {
            groupBy() == "status"
            groupOrder() == "asc"
            sortBy() == "updated"
            sortOrder() == "desc"
            group() == groupCol
            sort() == sortCol
            columns() == [groupCol, sortCol]
            fields() == [groupCol]
            customFields() == [customCol]
        }
        verifyAll(noArg) {
            groupBy() == null
            groupOrder() == null
            sortBy() == null
            sortOrder() == null
            group() == null
            sort() == null
            columns() == null
            fields() == null
            customFields() == null
        }
        verifyAll(built) {
            it == direct
            groupBy() == "status"
            sortBy() == "updated"
        }
    }

    def "should update ViewExecution using withers and add helpers"() {
        given:
        ViewColumn col1 = new ViewColumn("status", "Status")
        ViewColumn col2 = new ViewColumn("priority", "Priority")
        ViewExecution initial = new ViewExecution()

        when:
        ViewExecution withFirst = initial
                .addColumnsItem(col1)
                .addFieldsItem(col1)
                .addCustomFieldsItem(col2)
        ViewExecution withSecond = withFirst
                .addColumnsItem(col2)
                .addFieldsItem(col2)
                .addCustomFieldsItem(col1)
        ViewExecution updated = withSecond
                .withGroupBy("assignee")
                .withGroupOrder("desc")
                .withSortBy("priority")
                .withSortOrder("asc")
                .withGroup(col1)
                .withSort(col2)

        then:
        verifyAll(withFirst) {
            columns() == [col1]
            fields() == [col1]
            customFields() == [col2]
        }
        verifyAll(withSecond) {
            columns() == [col1, col2]
            fields() == [col1, col2]
            customFields() == [col2, col1]
        }
        verifyAll(updated) {
            groupBy() == "assignee"
            groupOrder() == "desc"
            sortBy() == "priority"
            sortOrder() == "asc"
            group() == col1
            sort() == col2
        }
    }

    def "should round-trip serialize and deserialize ViewExecution"() {
        given:
        ViewColumn col = new ViewColumn("status", "Status")
        ViewExecution execution = new ViewExecution()
                .withGroupBy("status")
                .withGroupOrder("asc")
                .withSortBy("updated")
                .withSortOrder("desc")
                .withColumns([col])

        when:
        String json = objectMapper.writeValueAsString(execution)
        Map map = objectMapper.readValue(json, Map.class)
        ViewExecution deserialized = objectMapper.readValue(json, ViewExecution.class)

        then:
        verifyAll(map) {
            get("group_by") == "status"
            get("group_order") == "asc"
            get("sort_by") == "updated"
            get("sort_order") == "desc"
            containsKey("columns")
        }
        verifyAll(deserialized) {
            it == execution
            groupBy() == "status"
            columns().size() == 1
            columns().first().id() == "status"
        }
    }

    def "should verify ViewExecution constants"() {
        expect:
        verifyAll {
            ViewExecution.JSON_PROPERTY_GROUP_BY == "group_by"
            ViewExecution.JSON_PROPERTY_GROUP_ORDER == "group_order"
            ViewExecution.JSON_PROPERTY_SORT_BY == "sort_by"
            ViewExecution.JSON_PROPERTY_SORT_ORDER == "sort_order"
            ViewExecution.JSON_PROPERTY_GROUP == "group"
            ViewExecution.JSON_PROPERTY_SORT == "sort"
            ViewExecution.JSON_PROPERTY_COLUMNS == "columns"
            ViewExecution.JSON_PROPERTY_FIELDS == "fields"
            ViewExecution.JSON_PROPERTY_CUSTOM_FIELDS == "custom_fields"
        }
    }
}
