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

class TicketRelatedSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketRelated with constructors and builder"() {
        when:
        TicketRelated direct = new TicketRelated(
                "https://example.zendesk.com/api/v2/tickets/1/related.json",
                123L,
                ["PROJ-101"],
                [456L],
                false,
                2
        )
        TicketRelated noArg = new TicketRelated()
        TicketRelated built = TicketRelatedBuilder.builder()
                .url("https://example.zendesk.com/api/v2/tickets/1/related.json")
                .topicId(123L)
                .jiraIssueIds(["PROJ-101"])
                .followupSourceIds([456L])
                .fromArchive(false)
                .incidents(2)
                .build()

        then:
        verifyAll(direct) {
            url() == "https://example.zendesk.com/api/v2/tickets/1/related.json"
            topicId() == 123L
            jiraIssueIds() == ["PROJ-101"]
            followupSourceIds() == [456L]
            fromArchive() == false
            incidents() == 2
        }
        verifyAll(noArg) {
            url() == null
            topicId() == null
            jiraIssueIds() == null
            followupSourceIds() == null
            fromArchive() == null
            incidents() == null
        }
        verifyAll(built) {
            it == direct
            topicId() == 123L
            incidents() == 2
        }
    }

    def "should update TicketRelated using withers"() {
        given:
        TicketRelated initial = new TicketRelated()

        when:
        TicketRelated updated = initial
                .withUrl("https://example.zendesk.com/api/v2/tickets/2/related.json")
                .withTopicId(789L)
                .withJiraIssueIds(["JIRA-202"])
                .withFollowupSourceIds([999L])
                .withFromArchive(true)
                .withIncidents(5)

        then:
        verifyAll(updated) {
            url() == "https://example.zendesk.com/api/v2/tickets/2/related.json"
            topicId() == 789L
            jiraIssueIds() == ["JIRA-202"]
            followupSourceIds() == [999L]
            fromArchive() == true
            incidents() == 5
        }
    }

    def "should add items to list properties"() {
        given:
        TicketRelated related = new TicketRelated()

        when: "adding to null lists"
        TicketRelated withJira = related.addJiraIssueIdsItem("BUG-1")
        TicketRelated withFollowup = related.addFollowupSourceIdsItem(100L)

        then:
        verifyAll(withJira) {
            jiraIssueIds() != null
            jiraIssueIds().size() == 1
            jiraIssueIds().first() == "BUG-1"
        }
        verifyAll(withFollowup) {
            followupSourceIds() != null
            followupSourceIds().size() == 1
            followupSourceIds().first() == 100L
        }

        when: "adding to populated lists"
        TicketRelated withMoreJira = withJira.addJiraIssueIdsItem("BUG-2")
        TicketRelated withMoreFollowup = withFollowup.addFollowupSourceIdsItem(200L)

        then:
        verifyAll(withMoreJira) {
            jiraIssueIds().size() == 2
            jiraIssueIds() == ["BUG-1", "BUG-2"]
        }
        verifyAll(withMoreFollowup) {
            followupSourceIds().size() == 2
            followupSourceIds() == [100L, 200L]
        }
    }

    def "should round-trip serialize and deserialize TicketRelated"() {
        given:
        TicketRelated related = new TicketRelated()
                .withTopicId(555L)
                .withIncidents(3)
                .withFromArchive(false)

        when:
        String json = objectMapper.writeValueAsString(related)
        Map map = objectMapper.readValue(json, Map.class)
        TicketRelated deserialized = objectMapper.readValue(json, TicketRelated.class)

        then:
        verifyAll(map) {
            get("topic_id") == 555
            get("incidents") == 3
            get("from_archive") == false
        }
        verifyAll(deserialized) {
            it == related
            topicId() == 555L
            incidents() == 3
            fromArchive() == false
        }
    }

    def "should verify TicketRelated constants"() {
        expect:
        verifyAll {
            TicketRelated.JSON_PROPERTY_URL == "url"
            TicketRelated.JSON_PROPERTY_TOPIC_ID == "topic_id"
            TicketRelated.JSON_PROPERTY_JIRA_ISSUE_IDS == "jira_issue_ids"
            TicketRelated.JSON_PROPERTY_FOLLOWUP_SOURCE_IDS == "followup_source_ids"
            TicketRelated.JSON_PROPERTY_FROM_ARCHIVE == "from_archive"
            TicketRelated.JSON_PROPERTY_INCIDENTS == "incidents"
        }
    }
}
