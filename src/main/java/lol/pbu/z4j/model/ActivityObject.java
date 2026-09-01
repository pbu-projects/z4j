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
import java.util.HashMap;
import java.util.Map;


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
 * ActivityObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ActivityObject.JSON_PROPERTY_ACTOR,
    ActivityObject.JSON_PROPERTY_ACTOR_ID,
    ActivityObject.JSON_PROPERTY_CREATED_AT,
    ActivityObject.JSON_PROPERTY_ID,
    ActivityObject.JSON_PROPERTY_OBJECT,
    ActivityObject.JSON_PROPERTY_TARGET,
    ActivityObject.JSON_PROPERTY_TITLE,
    ActivityObject.JSON_PROPERTY_UPDATED_AT,
    ActivityObject.JSON_PROPERTY_URL,
    ActivityObject.JSON_PROPERTY_USER,
    ActivityObject.JSON_PROPERTY_USER_ID,
    ActivityObject.JSON_PROPERTY_VERB,
})
@Serdeable
public class ActivityObject {

    public static final String JSON_PROPERTY_ACTOR = "actor";
    public static final String JSON_PROPERTY_ACTOR_ID = "actor_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_OBJECT = "object";
    public static final String JSON_PROPERTY_TARGET = "target";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER = "user";
    public static final String JSON_PROPERTY_USER_ID = "user_id";
    public static final String JSON_PROPERTY_VERB = "verb";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ACTOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserObject actor;

    /**
     * <p>The id of the user responsible for the ticket activity. An <code>actor_id</code> of \"-1\" is a Zendesk system user, such as an automations action.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long actorId;

    /**
     * <p>When the record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * <p>Automatically assigned on creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>The content of the activity. Can be a ticket, comment, or change.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OBJECT)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> object;

    /**
     * <p>The target of the activity, a ticket.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> target;

    /**
     * <p>Description of the activity</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * <p>When the record was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * <p>The API url of the activity</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_USER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserObject user;

    /**
     * <p>The id of the agent making the request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userId;

    /**
     * <p>The type of activity. Can be \"tickets.assignment\", \"tickets.comment\", or \"tickets.priority_increase\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VERB)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String verb;

}