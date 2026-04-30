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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TicketUpdateInput
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        TicketUpdateInput.JSON_PROPERTY_ADDITIONAL_COLLABORATORS,
        TicketUpdateInput.JSON_PROPERTY_ASSIGNEE_EMAIL,
        TicketUpdateInput.JSON_PROPERTY_ASSIGNEE_ID,
        TicketUpdateInput.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS,
        TicketUpdateInput.JSON_PROPERTY_COLLABORATOR_IDS,
        TicketUpdateInput.JSON_PROPERTY_COMMENT,
        TicketUpdateInput.JSON_PROPERTY_CUSTOM_FIELDS,
        TicketUpdateInput.JSON_PROPERTY_CUSTOM_STATUS_ID,
        TicketUpdateInput.JSON_PROPERTY_DUE_AT,
        TicketUpdateInput.JSON_PROPERTY_EMAIL_CCS,
        TicketUpdateInput.JSON_PROPERTY_EXTERNAL_ID,
        TicketUpdateInput.JSON_PROPERTY_FOLLOWERS,
        TicketUpdateInput.JSON_PROPERTY_GROUP_ID,
        TicketUpdateInput.JSON_PROPERTY_ORGANIZATION_ID,
        TicketUpdateInput.JSON_PROPERTY_PRIORITY,
        TicketUpdateInput.JSON_PROPERTY_PROBLEM_ID,
        TicketUpdateInput.JSON_PROPERTY_REQUESTER_ID,
        TicketUpdateInput.JSON_PROPERTY_SAFE_UPDATE,
        TicketUpdateInput.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
        TicketUpdateInput.JSON_PROPERTY_STATUS,
        TicketUpdateInput.JSON_PROPERTY_SUBJECT,
        TicketUpdateInput.JSON_PROPERTY_TAGS,
        TicketUpdateInput.JSON_PROPERTY_TYPE,
        TicketUpdateInput.JSON_PROPERTY_UPDATED_STAMP,
})
@Serdeable
public class TicketUpdateInput {

    public static final String JSON_PROPERTY_ADDITIONAL_COLLABORATORS = "additional_collaborators";
    public static final String JSON_PROPERTY_ASSIGNEE_EMAIL = "assignee_email";
    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_ATTRIBUTE_VALUE_IDS = "attribute_value_ids";
    public static final String JSON_PROPERTY_COLLABORATOR_IDS = "collaborator_ids";
    public static final String JSON_PROPERTY_COMMENT = "comment";
    public static final String JSON_PROPERTY_CUSTOM_FIELDS = "custom_fields";
    public static final String JSON_PROPERTY_CUSTOM_STATUS_ID = "custom_status_id";
    public static final String JSON_PROPERTY_DUE_AT = "due_at";
    public static final String JSON_PROPERTY_EMAIL_CCS = "email_ccs";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_FOLLOWERS = "followers";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_PROBLEM_ID = "problem_id";
    public static final String JSON_PROPERTY_REQUESTER_ID = "requester_id";
    public static final String JSON_PROPERTY_SAFE_UPDATE = "safe_update";
    public static final String JSON_PROPERTY_SHARING_AGREEMENT_IDS = "sharing_agreement_ids";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_UPDATED_STAMP = "updated_stamp";

    /**
     * An array of numeric IDs, emails, or objects containing name and email properties. See <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/tickets/#setting-collaborators\">Setting Collaborators</a>. An email notification is sent to them when the ticket is updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ADDITIONAL_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Collaborator> additionalCollaborators;

    /**
     * The email address of the agent to assign the ticket to
     */
    @Nullable
    @Email
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assigneeEmail;

    /**
     * The agent currently assigned to the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer assigneeId;

    /**
     * An array of the IDs of attribute values to be associated with the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> attributeValueIds;

    /**
     * The ids of users currently CC'ed on the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATOR_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> collaboratorIds;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketComment comment;

    /**
     * Custom fields for the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-custom-field-values\">Setting custom field values</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomField> customFields;

    /**
     * The custom ticket status id of the ticket. See <a href=\"#custom-ticket-statuses\">custom ticket statuses</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer customStatusId;

    /**
     * If this is a ticket of type \"task\" it has a due date.  Due date format uses <a href=\"http://en.wikipedia.org/wiki/ISO_8601\">ISO 8601</a> format.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DUE_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime dueAt;

    /**
     * An array of objects that represent agent or end users email CCs to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-email-ccs\">Setting email CCs</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CCS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid EmailCC> emailCcs;

    /**
     * An id you can use to link Zendesk Support tickets to local records
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * An array of objects that represent agent followers to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-followers\">Setting followers</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Follower> followers;

    /**
     * The group this ticket is assigned to
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer groupId;

    /**
     * The organization of the requester. You can only specify the ID of an organization associated with the requester. See <a href=\"developer.zendesk.com/api-reference/ticketing/organizations/organization_memberships/\">Organization Memberships</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputPriority priority;

    /**
     * For tickets of type \"incident\", the ID of the problem the incident is linked to
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PROBLEM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer problemId;

    /**
     * The user who requested this ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer requesterId;

    /**
     * Optional boolean. Prevents updates with outdated ticket data (`updated_stamp` property required when true)
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SAFE_UPDATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean safeUpdate;

    /**
     * An array of the numeric IDs of sharing agreements. Note that this replaces any existing agreements
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARING_AGREEMENT_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> sharingAgreementIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputStatus status;

    /**
     * The value of the subject field for this ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    /**
     * The array of tags applied to this ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputType type;

    /**
     * Datetime of last update received from API. See the safe_update property
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_STAMP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedStamp;

    /**
     * Add an item to the additionalCollaborators property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addAdditionalCollaboratorsItem(Collaborator additionalCollaboratorsItem) {
        if (additionalCollaborators == null) {
            additionalCollaborators = new ArrayList<>();
        }
        additionalCollaborators.add(additionalCollaboratorsItem);
        return this;
    }

    /**
     * Add an item to the attributeValueIds property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addAttributeValueIdsItem(Integer attributeValueIdsItem) {
        if (attributeValueIds == null) {
            attributeValueIds = new ArrayList<>();
        }
        attributeValueIds.add(attributeValueIdsItem);
        return this;
    }

    /**
     * Add an item to the collaboratorIds property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addCollaboratorIdsItem(Integer collaboratorIdsItem) {
        if (collaboratorIds == null) {
            collaboratorIds = new ArrayList<>();
        }
        collaboratorIds.add(collaboratorIdsItem);
        return this;
    }

    /**
     * Add an item to the customFields property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addCustomFieldsItem(CustomField customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return this;
    }

    /**
     * Add an item to the emailCcs property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addEmailCcsItem(EmailCC emailCcsItem) {
        if (emailCcs == null) {
            emailCcs = new ArrayList<>();
        }
        emailCcs.add(emailCcsItem);
        return this;
    }

    /**
     * Add an item to the followers property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addFollowersItem(Follower followersItem) {
        if (followers == null) {
            followers = new ArrayList<>();
        }
        followers.add(followersItem);
        return this;
    }

    /**
     * Add an item to the sharingAgreementIds property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addSharingAgreementIdsItem(Integer sharingAgreementIdsItem) {
        if (sharingAgreementIds == null) {
            sharingAgreementIds = new ArrayList<>();
        }
        sharingAgreementIds.add(sharingAgreementIdsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of TicketUpdateInput for chaining.
     */
    public TicketUpdateInput addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

}
