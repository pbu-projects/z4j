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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

import java.util.List;

/**
 * TicketRelated
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        TicketRelated.JSON_PROPERTY_URL,
        TicketRelated.JSON_PROPERTY_TOPIC_ID,
        TicketRelated.JSON_PROPERTY_JIRA_ISSUE_IDS,
        TicketRelated.JSON_PROPERTY_FOLLOWUP_SOURCE_IDS,
        TicketRelated.JSON_PROPERTY_FROM_ARCHIVE,
        TicketRelated.JSON_PROPERTY_INCIDENTS
})
@Serdeable
@Generated
public class TicketRelated {

    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_TOPIC_ID = "topic_id";
    public static final String JSON_PROPERTY_JIRA_ISSUE_IDS = "jira_issue_ids";
    public static final String JSON_PROPERTY_FOLLOWUP_SOURCE_IDS = "followup_source_ids";
    public static final String JSON_PROPERTY_FROM_ARCHIVE = "from_archive";
    public static final String JSON_PROPERTY_INCIDENTS = "incidents";

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TOPIC_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long topicId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_JIRA_ISSUE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<String> jiraIssueIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWUP_SOURCE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Long> followupSourceIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FROM_ARCHIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean fromArchive;

    @Nullable
    @JsonProperty(JSON_PROPERTY_INCIDENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer incidents;
}
