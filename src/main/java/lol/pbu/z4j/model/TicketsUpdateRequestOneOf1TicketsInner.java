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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TicketsUpdateRequestOneOf1TicketsInner
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_ADDITIONAL_COLLABORATORS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_ASSIGNEE_EMAIL,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_ASSIGNEE_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_COLLABORATOR_IDS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_COMMENT,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_CUSTOM_FIELDS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_CUSTOM_STATUS_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_DUE_AT,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_EMAIL_CCS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_EXTERNAL_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_FOLLOWERS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_GROUP_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_ORGANIZATION_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_PRIORITY,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_PROBLEM_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_REQUESTER_ID,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_SAFE_UPDATE,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_STATUS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_SUBJECT,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_TAGS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_TYPE,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_UPDATED_STAMP,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_ADDITIONAL_TAGS,
    TicketsUpdateRequestOneOf1TicketsInner.JSON_PROPERTY_REMOVE_TAGS,
})
@Serdeable
public class TicketsUpdateRequestOneOf1TicketsInner {

    public static final String JSON_PROPERTY_ID = "id";
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
    public static final String JSON_PROPERTY_ADDITIONAL_TAGS = "additional_tags";
    public static final String JSON_PROPERTY_REMOVE_TAGS = "remove_tags";

    /**
     * <p>The ID of the ticket to update</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_ID)
    private Long id;

    /**
     * <p>An array of numeric IDs, emails, or objects containing name and email properties. See <a href=\"/api-reference/ticketing/tickets/tickets/#setting-collaborators\">Setting Collaborators</a>. An email notification is sent to them when the ticket is updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ADDITIONAL_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CollaboratorObject> additionalCollaborators;

    /**
     * <p>The email address of the agent to assign the ticket to</p>
     */
    @Nullable
    @Email
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assigneeEmail;

    /**
     * <p>The agent currently assigned to the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long assigneeId;

    /**
     * <p>An array of the IDs of attribute values to be associated with the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> attributeValueIds;

    /**
     * <p>The ids of users currently CC'ed on the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATOR_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> collaboratorIds;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketCommentObject comment;

    /**
     * <p>Custom fields for the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-custom-field-values\">Setting custom field values</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomFieldObject> customFields;

    /**
     * <p>The custom ticket status id of the ticket. See <a href=\"#custom-ticket-statuses\">custom ticket statuses</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long customStatusId;

    /**
     * <p>If this is a ticket of type \"task\" it has a due date.  Due date format uses <a href=\"http://en.wikipedia.org/wiki/ISO_8601\">ISO 8601</a> format.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DUE_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime dueAt;

    /**
     * <p>An array of objects that represent agent or end users email CCs to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-email-ccs\">Setting email CCs</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CCS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid EmailCCObject> emailCcs;

    /**
     * <p>An id you can use to link Zendesk Support tickets to local records</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * <p>An array of objects that represent agent followers to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-followers\">Setting followers</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid FollowerObject> followers;

    /**
     * <p>The group this ticket is assigned to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long groupId;

    /**
     * <p>The organization of the requester. You can only specify the ID of an organization associated with the requester. See <a href=\"/api-reference/ticketing/organizations/organization_memberships/\">Organization Memberships</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketCreateVoicemailTicketInputPriority priority;

    /**
     * <p>For tickets of type \"incident\", the ID of the problem the incident is linked to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PROBLEM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long problemId;

    /**
     * <p>The user who requested this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long requesterId;

    /**
     * <p>Optional boolean. Prevents updates with outdated ticket data (<code>updated_stamp</code> property required when true)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SAFE_UPDATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean safeUpdate;

    /**
     * <p>An array of the numeric IDs of sharing agreements. Note that this replaces any existing agreements</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARING_AGREEMENT_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> sharingAgreementIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputStatus status;

    /**
     * <p>The value of the subject field for this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    /**
     * <p>The array of tags applied to this ticket</p>
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
     * <p>Datetime of last update received from API. See the safe_update property</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_STAMP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedStamp;

    /**
     * <p>Tags to add to existing tags without overwriting</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ADDITIONAL_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> additionalTags;

    /**
     * <p>Tags to remove from the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REMOVE_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> removeTags;

    public TicketsUpdateRequestOneOf1TicketsInner(Long id) {
        this.id = id;
    }

    /**
     * Add an item to the additionalCollaborators property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addAdditionalCollaboratorsItem(CollaboratorObject additionalCollaboratorsItem) {
        if (additionalCollaborators == null) {
            additionalCollaborators = new ArrayList<>();
        }
        additionalCollaborators.add(additionalCollaboratorsItem);
        return this;
    }

    /**
     * Add an item to the attributeValueIds property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addAttributeValueIdsItem(Long attributeValueIdsItem) {
        if (attributeValueIds == null) {
            attributeValueIds = new ArrayList<>();
        }
        attributeValueIds.add(attributeValueIdsItem);
        return this;
    }

    /**
     * Add an item to the collaboratorIds property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addCollaboratorIdsItem(Long collaboratorIdsItem) {
        if (collaboratorIds == null) {
            collaboratorIds = new ArrayList<>();
        }
        collaboratorIds.add(collaboratorIdsItem);
        return this;
    }

    /**
     * Add an item to the customFields property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addCustomFieldsItem(CustomFieldObject customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return this;
    }

    /**
     * Add an item to the emailCcs property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addEmailCcsItem(EmailCCObject emailCcsItem) {
        if (emailCcs == null) {
            emailCcs = new ArrayList<>();
        }
        emailCcs.add(emailCcsItem);
        return this;
    }

    /**
     * Add an item to the followers property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addFollowersItem(FollowerObject followersItem) {
        if (followers == null) {
            followers = new ArrayList<>();
        }
        followers.add(followersItem);
        return this;
    }

    /**
     * Add an item to the sharingAgreementIds property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addSharingAgreementIdsItem(Long sharingAgreementIdsItem) {
        if (sharingAgreementIds == null) {
            sharingAgreementIds = new ArrayList<>();
        }
        sharingAgreementIds.add(sharingAgreementIdsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

    /**
     * Add an item to the additionalTags property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addAdditionalTagsItem(String additionalTagsItem) {
        if (additionalTags == null) {
            additionalTags = new ArrayList<>();
        }
        additionalTags.add(additionalTagsItem);
        return this;
    }

    /**
     * Add an item to the removeTags property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1TicketsInner for chaining.
     */
    public TicketsUpdateRequestOneOf1TicketsInner addRemoveTagsItem(String removeTagsItem) {
        if (removeTags == null) {
            removeTags = new ArrayList<>();
        }
        removeTags.add(removeTagsItem);
        return this;
    }

}