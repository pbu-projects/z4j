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
import java.time.ZonedDateTime;
import lol.pbu.z4j.model.CustomRoleConfigurationObject;
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
 * CustomRoleObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    CustomRoleObject.JSON_PROPERTY_NAME,
    CustomRoleObject.JSON_PROPERTY_ROLE_TYPE,
    CustomRoleObject.JSON_PROPERTY_CONFIGURATION,
    CustomRoleObject.JSON_PROPERTY_CREATED_AT,
    CustomRoleObject.JSON_PROPERTY_DESCRIPTION,
    CustomRoleObject.JSON_PROPERTY_ID,
    CustomRoleObject.JSON_PROPERTY_TEAM_MEMBER_COUNT,
    CustomRoleObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class CustomRoleObject {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_ROLE_TYPE = "role_type";
    public static final String JSON_PROPERTY_CONFIGURATION = "configuration";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_TEAM_MEMBER_COUNT = "team_member_count";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>Name of the custom role</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>The user's role. 0 stands for a custom agent, 1 for a light agent, 2 for a chat agent, 3 for a contributor, 4 for an admin and 5 for a billing admin. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4409155971354-Understanding-standard-agent-roles-in-Zendesk-Support\">Understanding standard agent roles in Zendesk Support</a> in Zendesk help</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long roleType;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CONFIGURATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private CustomRoleConfigurationObject _configuration;

    /**
     * <p>The time the record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>A description of the role</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned on creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>The number of team members assigned to this role</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TEAM_MEMBER_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long teamMemberCount;

    /**
     * <p>The time the record was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    public CustomRoleObject(String name) {
        this.name = name;
    }

}