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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * TicketRelatedInformation
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketRelatedInformation.JSON_PROPERTY_FOLLOWUP_SOURCE_IDS,
    TicketRelatedInformation.JSON_PROPERTY_FROM_ARCHIVE,
    TicketRelatedInformation.JSON_PROPERTY_INCIDENTS,
    TicketRelatedInformation.JSON_PROPERTY_JIRA_ISSUE_IDS,
    TicketRelatedInformation.JSON_PROPERTY_TOPIC_ID,
})
@Serdeable
public class TicketRelatedInformation {

    public static final String JSON_PROPERTY_FOLLOWUP_SOURCE_IDS = "followup_source_ids";
    public static final String JSON_PROPERTY_FROM_ARCHIVE = "from_archive";
    public static final String JSON_PROPERTY_INCIDENTS = "incidents";
    public static final String JSON_PROPERTY_JIRA_ISSUE_IDS = "jira_issue_ids";
    public static final String JSON_PROPERTY_TOPIC_ID = "topic_id";

    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWUP_SOURCE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> followupSourceIds;

    /**
     * <p>Is true if the current ticket is archived</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FROM_ARCHIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean fromArchive;

    /**
     * <p>A count of related incident occurrences</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INCIDENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long incidents;

    @Nullable
    @JsonProperty(JSON_PROPERTY_JIRA_ISSUE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> jiraIssueIds;

    /**
     * <p>Related topic in the Web portal (deprecated feature)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TOPIC_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String topicId;

    /**
     * Add an item to the followupSourceIds property in a chainable fashion.
     *
     * @return The same instance of TicketRelatedInformation for chaining.
     */
    public TicketRelatedInformation addFollowupSourceIdsItem(String followupSourceIdsItem) {
        if (followupSourceIds == null) {
            followupSourceIds = new ArrayList<>();
        }
        followupSourceIds.add(followupSourceIdsItem);
        return this;
    }

    /**
     * Add an item to the jiraIssueIds property in a chainable fashion.
     *
     * @return The same instance of TicketRelatedInformation for chaining.
     */
    public TicketRelatedInformation addJiraIssueIdsItem(String jiraIssueIdsItem) {
        if (jiraIssueIds == null) {
            jiraIssueIds = new ArrayList<>();
        }
        jiraIssueIds.add(jiraIssueIdsItem);
        return this;
    }

}