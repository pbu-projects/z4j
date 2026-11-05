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
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Related metadata and cross-reference identifiers for a ticket.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketRelated.JSON_PROPERTY_URL,
        TicketRelated.JSON_PROPERTY_TOPIC_ID,
        TicketRelated.JSON_PROPERTY_JIRA_ISSUE_IDS,
        TicketRelated.JSON_PROPERTY_FOLLOWUP_SOURCE_IDS,
        TicketRelated.JSON_PROPERTY_FROM_ARCHIVE,
        TicketRelated.JSON_PROPERTY_INCIDENTS
})
public record TicketRelated(
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_TOPIC_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long topicId,
        @Nullable @JsonProperty(JSON_PROPERTY_JIRA_ISSUE_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<String> jiraIssueIds,
        @Nullable @JsonProperty(JSON_PROPERTY_FOLLOWUP_SOURCE_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Long> followupSourceIds,
        @Nullable @JsonProperty(JSON_PROPERTY_FROM_ARCHIVE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean fromArchive,
        @Nullable @JsonProperty(JSON_PROPERTY_INCIDENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer incidents
) implements TicketRelatedBuilder.With {

    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_TOPIC_ID = "topic_id";
    public static final String JSON_PROPERTY_JIRA_ISSUE_IDS = "jira_issue_ids";
    public static final String JSON_PROPERTY_FOLLOWUP_SOURCE_IDS = "followup_source_ids";
    public static final String JSON_PROPERTY_FROM_ARCHIVE = "from_archive";
    public static final String JSON_PROPERTY_INCIDENTS = "incidents";

    public TicketRelated() {
        this(null, null, null, null, null, null);
    }

    public TicketRelated addJiraIssueIdsItem(String jiraIssueIdsItem) {
        List<String> newItems = jiraIssueIds == null ? new ArrayList<>() : new ArrayList<>(jiraIssueIds);
        newItems.add(jiraIssueIdsItem);
        return withJiraIssueIds(newItems);
    }

    public TicketRelated addFollowupSourceIdsItem(Long followupSourceIdsItem) {
        List<Long> newItems = followupSourceIds == null ? new ArrayList<>() : new ArrayList<>(followupSourceIds);
        newItems.add(followupSourceIdsItem);
        return withFollowupSourceIds(newItems);
    }
}
