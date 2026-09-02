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
import java.util.Map;

/**
 * OrganizationObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    OrganizationObject.JSON_PROPERTY_NAME,
    OrganizationObject.JSON_PROPERTY_CREATED_AT,
    OrganizationObject.JSON_PROPERTY_DETAILS,
    OrganizationObject.JSON_PROPERTY_DOMAIN_NAMES,
    OrganizationObject.JSON_PROPERTY_EXTERNAL_ID,
    OrganizationObject.JSON_PROPERTY_GROUP_ID,
    OrganizationObject.JSON_PROPERTY_ID,
    OrganizationObject.JSON_PROPERTY_NOTES,
    OrganizationObject.JSON_PROPERTY_ORGANIZATION_FIELDS,
    OrganizationObject.JSON_PROPERTY_SHARED_COMMENTS,
    OrganizationObject.JSON_PROPERTY_SHARED_TICKETS,
    OrganizationObject.JSON_PROPERTY_TAGS,
    OrganizationObject.JSON_PROPERTY_UPDATED_AT,
    OrganizationObject.JSON_PROPERTY_URL,
})
@Serdeable
public class OrganizationObject {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DETAILS = "details";
    public static final String JSON_PROPERTY_DOMAIN_NAMES = "domain_names";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NOTES = "notes";
    public static final String JSON_PROPERTY_ORGANIZATION_FIELDS = "organization_fields";
    public static final String JSON_PROPERTY_SHARED_COMMENTS = "shared_comments";
    public static final String JSON_PROPERTY_SHARED_TICKETS = "shared_tickets";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>A unique name for the organization</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>The time the organization was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * <p>Any details obout the organization, such as the address</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DETAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String details;

    /**
     * <p>An array of domain names associated with this organization</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DOMAIN_NAMES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> domainNames;

    /**
     * <p>A unique external id to associate organizations to an external record. The id is case-insensitive. For example, \"company1\" and \"Company1\" are considered the same</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * <p>New tickets from users in this organization are automatically put in this group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long groupId;

    /**
     * <p>Automatically assigned when the organization is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Any notes you have about the organization</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String notes;

    /**
     * <p>Custom fields for this organization. See <a href=\"/api-reference/ticketing/organizations/organizations/#custom-organization-fields\">Custom organization fields</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Map<String, @Valid OrganizationObjectOrganizationFieldsValue> organizationFields;

    /**
     * <p>End users in this organization are able to comment on each other's tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sharedComments;

    /**
     * <p>End users in this organization are able to see each other's tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sharedTickets;

    /**
     * <p>The tags of the organization</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    /**
     * <p>The time of the last update of the organization</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * <p>The API url of this organization</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public OrganizationObject(String name) {
        this.name = name;
    }

    /**
     * Add an item to the domainNames property in a chainable fashion.
     *
     * @return The same instance of OrganizationObject for chaining.
     */
    public OrganizationObject addDomainNamesItem(String domainNamesItem) {
        if (domainNames == null) {
            domainNames = new ArrayList<>();
        }
        domainNames.add(domainNamesItem);
        return this;
    }

    /**
     * Set the value for the key for the organizationFields map property in a chainable fashion.
     *
     * @return The same instance of OrganizationObject for chaining.
     */
    public OrganizationObject putOrganizationFieldsItem(String key, OrganizationObjectOrganizationFieldsValue organizationFieldsItem) {
        if (organizationFields == null) {
            organizationFields = new HashMap<>();
        }
        organizationFields.put(key, organizationFieldsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of OrganizationObject for chaining.
     */
    public OrganizationObject addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

}