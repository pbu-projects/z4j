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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * UserInput
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Getter
@Setter
@JsonPropertyOrder({
    UserInput.JSON_PROPERTY_EMAIL,
    UserInput.JSON_PROPERTY_NAME,
    UserInput.JSON_PROPERTY_AGENT_BRAND_IDS,
    UserInput.JSON_PROPERTY_CUSTOM_ROLE_ID,
    UserInput.JSON_PROPERTY_EXTERNAL_ID,
    UserInput.JSON_PROPERTY_IDENTITIES,
    UserInput.JSON_PROPERTY_ORGANIZATION,
    UserInput.JSON_PROPERTY_ORGANIZATION_ID,
    UserInput.JSON_PROPERTY_ROLE,
    UserInput.JSON_PROPERTY_PASSWORD,
    UserInput.JSON_PROPERTY_ID,
})
@Serdeable
@io.micronaut.core.annotation.Introspected
public class UserInput extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_AGENT_BRAND_IDS = "agent_brand_ids";
    public static final String JSON_PROPERTY_CUSTOM_ROLE_ID = "custom_role_id";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_IDENTITIES = "identities";
    public static final String JSON_PROPERTY_ORGANIZATION = "organization";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_ROLE = "role";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_ID = "id";

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

    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String password;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    public UserInput(String email, String name) {
        this.email = email; this.put("email", email);
        this.name = name; this.put("name", name);
    }

    /**
     * Add an item to the agentBrandIds property in a chainable fashion.
     *
     * @return The same instance of UserInput for chaining.
     */
    public UserInput addAgentBrandIdsItem(Long agentBrandIdsItem) {
        if (agentBrandIds == null) {
            agentBrandIds = new ArrayList<>();
        }
        agentBrandIds.add(agentBrandIdsItem);
        return this;
    }

    /**
     * Add an item to the identities property in a chainable fashion.
     *
     * @return The same instance of UserInput for chaining.
     */
    public UserInput addIdentitiesItem(UserCreateInputIdentitiesInner identitiesItem) {
        if (identities == null) {
            identities = new ArrayList<>();
        }
        identities.add(identitiesItem);
        return this;
    }

}