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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * AssigneeFieldAssignableSearchAgentObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AssigneeFieldAssignableSearchAgentObject.JSON_PROPERTY_GROUP,
    AssigneeFieldAssignableSearchAgentObject.JSON_PROPERTY_GROUP_ID,
    AssigneeFieldAssignableSearchAgentObject.JSON_PROPERTY_ID,
    AssigneeFieldAssignableSearchAgentObject.JSON_PROPERTY_NAME,
    AssigneeFieldAssignableSearchAgentObject.JSON_PROPERTY_PHOTO_URL,
})
@Serdeable
public class AssigneeFieldAssignableSearchAgentObject {

    public static final String JSON_PROPERTY_GROUP = "group";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PHOTO_URL = "photo_url";

    /**
     * <p>Name of the agent's group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String group;

    /**
     * <p>Agent's Group ID</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long groupId;

    /**
     * <p>Agent ID</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Name of the agent</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>URL of Avatar</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PHOTO_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String photoUrl;

}