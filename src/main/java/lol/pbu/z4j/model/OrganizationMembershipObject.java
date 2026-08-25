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
 * OrganizationMembershipObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    OrganizationMembershipObject.JSON_PROPERTY_DEFAULT,
    OrganizationMembershipObject.JSON_PROPERTY_ORGANIZATION_ID,
    OrganizationMembershipObject.JSON_PROPERTY_USER_ID,
    OrganizationMembershipObject.JSON_PROPERTY_CREATED_AT,
    OrganizationMembershipObject.JSON_PROPERTY_ID,
    OrganizationMembershipObject.JSON_PROPERTY_ORGANIZATION_NAME,
    OrganizationMembershipObject.JSON_PROPERTY_UPDATED_AT,
    OrganizationMembershipObject.JSON_PROPERTY_URL,
    OrganizationMembershipObject.JSON_PROPERTY_VIEW_TICKETS,
})
@Serdeable
public class OrganizationMembershipObject {

    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_USER_ID = "user_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ORGANIZATION_NAME = "organization_name";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VIEW_TICKETS = "view_tickets";

    /**
     * <p>Denotes whether this is the default organization membership for the user. If false, returns <code>null</code></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    private Boolean _default;

    /**
     * <p>The ID of the organization associated with this user, in this membership</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer organizationId;

    /**
     * <p>The ID of the user for whom this membership belongs</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer userId;

    /**
     * <p>When this record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned when the membership is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The name of the organization associated with this user, in this membership</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String organizationName;

    /**
     * <p>When this record last got updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this membership</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>Denotes whether the user can or cannot have access to all organization's tickets.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIEW_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean viewTickets;

    public OrganizationMembershipObject(Boolean _default) {
        this._default = _default;
    }

}