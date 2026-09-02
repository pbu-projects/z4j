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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * TargetPivotal
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetPivotal.JSON_PROPERTY_PROJECT_ID,
    TargetPivotal.JSON_PROPERTY_STORY_TITLE,
    TargetPivotal.JSON_PROPERTY_STORY_TYPE,
    TargetPivotal.JSON_PROPERTY_TOKEN,
    TargetPivotal.JSON_PROPERTY_OWNER_BY,
    TargetPivotal.JSON_PROPERTY_REQUESTED_BY,
    TargetPivotal.JSON_PROPERTY_STORY_LABELS,
})
@Serdeable
public class TargetPivotal {

    public static final String JSON_PROPERTY_PROJECT_ID = "project_id";
    public static final String JSON_PROPERTY_STORY_TITLE = "story_title";
    public static final String JSON_PROPERTY_STORY_TYPE = "story_type";
    public static final String JSON_PROPERTY_TOKEN = "token";
    public static final String JSON_PROPERTY_OWNER_BY = "owner_by";
    public static final String JSON_PROPERTY_REQUESTED_BY = "requested_by";
    public static final String JSON_PROPERTY_STORY_LABELS = "story_labels";

    @NotNull
    @JsonProperty(JSON_PROPERTY_PROJECT_ID)
    private String projectId;

    @NotNull
    @JsonProperty(JSON_PROPERTY_STORY_TITLE)
    private String storyTitle;

    @NotNull
    @JsonProperty(JSON_PROPERTY_STORY_TYPE)
    private String storyType;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TOKEN)
    private String token;

    @Nullable
    @JsonProperty(JSON_PROPERTY_OWNER_BY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ownerBy;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTED_BY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String requestedBy;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STORY_LABELS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String storyLabels;

    public TargetPivotal(String projectId, String storyTitle, String storyType, String token) {
        this.projectId = projectId;
        this.storyTitle = storyTitle;
        this.storyType = storyType;
        this.token = token;
    }

}