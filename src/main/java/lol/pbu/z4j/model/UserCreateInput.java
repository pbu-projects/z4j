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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.UserCreateInputIdentitiesInner;
import lol.pbu.z4j.model.UserCreateInputOrganization;
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
 * UserCreateInput
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    UserCreateInput.JSON_PROPERTY_EMAIL,
    UserCreateInput.JSON_PROPERTY_NAME,
    UserCreateInput.JSON_PROPERTY_AGENT_BRAND_IDS,
    UserCreateInput.JSON_PROPERTY_CUSTOM_ROLE_ID,
    UserCreateInput.JSON_PROPERTY_EXTERNAL_ID,
    UserCreateInput.JSON_PROPERTY_IDENTITIES,
    UserCreateInput.JSON_PROPERTY_ORGANIZATION,
    UserCreateInput.JSON_PROPERTY_ORGANIZATION_ID,
    UserCreateInput.JSON_PROPERTY_ROLE,
})
@Serdeable
public class UserCreateInput {

    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_AGENT_BRAND_IDS = "agent_brand_ids";
    public static final String JSON_PROPERTY_CUSTOM_ROLE_ID = "custom_role_id";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_IDENTITIES = "identities";
    public static final String JSON_PROPERTY_ORGANIZATION = "organization";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_ROLE = "role";

    @NotNull
    @JsonProperty(JSON_PROPERTY_EMAIL)
    private String email;

    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_BRAND_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> agentBrandIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_ROLE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long customRoleId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IDENTITIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid UserCreateInputIdentitiesInner> identities;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ORGANIZATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserCreateInputOrganization organization;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String role;

    public UserCreateInput(String email, String name) {
        this.email = email;
        this.name = name;
    }

    /**
     * Add an item to the agentBrandIds property in a chainable fashion.
     *
     * @return The same instance of UserCreateInput for chaining.
     */
    public UserCreateInput addAgentBrandIdsItem(Long agentBrandIdsItem) {
        if (agentBrandIds == null) {
            agentBrandIds = new ArrayList<>();
        }
        agentBrandIds.add(agentBrandIdsItem);
        return this;
    }

    /**
     * Add an item to the identities property in a chainable fashion.
     *
     * @return The same instance of UserCreateInput for chaining.
     */
    public UserCreateInput addIdentitiesItem(UserCreateInputIdentitiesInner identitiesItem) {
        if (identities == null) {
            identities = new ArrayList<>();
        }
        identities.add(identitiesItem);
        return this;
    }

}