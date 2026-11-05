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

class ViewExecuteResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewExecuteResponse with canonical constructor and builder"() {
        given:
        ViewRow row = new ViewRow(new Ticket().withId(10L))
        List<ViewRow> expectedRows = [row]
        List<Object> expectedColumns = [["id": "subject", "title": "Subject"]]
        List<User> expectedUsers = [new User().withId(20L).withName("Agent")]
        List<Group> expectedGroups = [new Group()]
        List<Organization> expectedOrgs = [new Organization(30L)]

        when:
        ViewExecuteResponse direct = new ViewExecuteResponse(
                expectedRows,
                expectedColumns,
                expectedUsers,
                expectedGroups,
                expectedOrgs,
                1,
                "https://example.zendesk.com/api/v2/views/1/execute.json?page=2",
                "https://example.zendesk.com/api/v2/views/1/execute.json?page=1"
        )
        ViewExecuteResponse built = ViewExecuteResponseBuilder.builder()
                .rows(expectedRows)
                .columns(expectedColumns)
                .users(expectedUsers)
                .groups(expectedGroups)
                .organizations(expectedOrgs)
                .count(1)
                .nextPage("https://example.zendesk.com/api/v2/views/1/execute.json?page=2")
                .previousPage("https://example.zendesk.com/api/v2/views/1/execute.json?page=1")
                .build()

        then:
        verifyAll(direct) {
            rows() == expectedRows
            columns() == expectedColumns
            users() == expectedUsers
            groups() == expectedGroups
            organizations() == expectedOrgs
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/views/1/execute.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/views/1/execute.json?page=1"
        }
        verifyAll(built) {
            it == direct
            rows() == expectedRows
            columns() == expectedColumns
            users() == expectedUsers
            groups() == expectedGroups
            organizations() == expectedOrgs
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/views/1/execute.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/views/1/execute.json?page=1"
        }
    }

    def "should update ViewExecuteResponse using record withers"() {
        given:
        ViewRow row1 = new ViewRow(new Ticket().withId(1L))
        ViewRow row2 = new ViewRow(new Ticket().withId(2L))
        User user1 = new User().withId(10L)
        User user2 = new User().withId(20L)
        Group group1 = new Group()
        Group group2 = new Group()
        Organization org1 = new Organization(100L)
        Organization org2 = new Organization(200L)
        ViewExecuteResponse response = new ViewExecuteResponse(
                [row1], ["col1"], [user1], [group1], [org1], 1, "next1", "prev1"
        )

        when:
        ViewExecuteResponse updated = response
                .withRows([row2])
                .withColumns(["col2"])
                .withUsers([user2])
                .withGroups([group2])
                .withOrganizations([org2])
                .withCount(2)
                .withNextPage("next2")
                .withPreviousPage("prev2")

        then:
        verifyAll(response) {
            rows() == [row1]
            columns() == ["col1"]
            users() == [user1]
            groups() == [group1]
            organizations() == [org1]
            count() == 1
            nextPage() == "next1"
            previousPage() == "prev1"
        }
        verifyAll(updated) {
            rows() == [row2]
            columns() == ["col2"]
            users() == [user2]
            groups() == [group2]
            organizations() == [org2]
            count() == 2
            nextPage() == "next2"
            previousPage() == "prev2"
        }
    }

    def "should round-trip serialize and deserialize ViewExecuteResponse"() {
        given:
        ViewRow row = new ViewRow(new Ticket().withId(55L))
        User user = new User().withId(66L).withName("Support User")
        Organization org = new Organization(77L)
        ViewExecuteResponse response = new ViewExecuteResponse(
                [row],
                [["id": "subject"]],
                [user],
                null,
                [org],
                1,
                "https://example.com/next",
                "https://example.com/prev"
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ViewExecuteResponse deserialized = objectMapper.readValue(json, ViewExecuteResponse.class)

        then:
        verifyAll(map) {
            containsKey("rows")
            containsKey("columns")
            containsKey("users")
            containsKey("organizations")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
        }
        verifyAll(deserialized) {
            rows().size() == 1
            rows().first().ticket.id == 55L
            columns() == [["id": "subject"]]
            users().size() == 1
            users().first().id == 66L
            groups() == null
            organizations().size() == 1
            organizations().first().id == 77L
            count() == 1
            nextPage() == "https://example.com/next"
            previousPage() == "https://example.com/prev"
        }
    }

    def "should handle null components in ViewExecuteResponse"() {
        when:
        ViewExecuteResponse empty = new ViewExecuteResponse(null, null, null, null, null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ViewExecuteResponse deserialized = objectMapper.readValue(json, ViewExecuteResponse.class)

        then:
        verifyAll(empty) {
            rows() == null
            columns() == null
            users() == null
            groups() == null
            organizations() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            rows() == null
            columns() == null
            users() == null
            groups() == null
            organizations() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
