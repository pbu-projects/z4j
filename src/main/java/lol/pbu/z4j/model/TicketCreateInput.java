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
 * TicketCreateInput
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
        TicketCreateInput.JSON_PROPERTY_COMMENT,
        TicketCreateInput.JSON_PROPERTY_ADDITIONAL_COLLABORATORS,
        TicketCreateInput.JSON_PROPERTY_ASSIGNEE_EMAIL,
        TicketCreateInput.JSON_PROPERTY_ASSIGNEE_ID,
        TicketCreateInput.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS,
        TicketCreateInput.JSON_PROPERTY_COLLABORATOR_IDS,
        TicketCreateInput.JSON_PROPERTY_CUSTOM_FIELDS,
        TicketCreateInput.JSON_PROPERTY_CUSTOM_STATUS_ID,
        TicketCreateInput.JSON_PROPERTY_DUE_AT,
        TicketCreateInput.JSON_PROPERTY_EMAIL_CCS,
        TicketCreateInput.JSON_PROPERTY_EXTERNAL_ID,
        TicketCreateInput.JSON_PROPERTY_FOLLOWERS,
        TicketCreateInput.JSON_PROPERTY_GROUP_ID,
        TicketCreateInput.JSON_PROPERTY_ORGANIZATION_ID,
        TicketCreateInput.JSON_PROPERTY_PRIORITY,
        TicketCreateInput.JSON_PROPERTY_PROBLEM_ID,
        TicketCreateInput.JSON_PROPERTY_REQUESTER_ID,
        TicketCreateInput.JSON_PROPERTY_SAFE_UPDATE,
        TicketCreateInput.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
        TicketCreateInput.JSON_PROPERTY_STATUS,
        TicketCreateInput.JSON_PROPERTY_SUBJECT,
        TicketCreateInput.JSON_PROPERTY_TAGS,
        TicketCreateInput.JSON_PROPERTY_TYPE,
        TicketCreateInput.JSON_PROPERTY_UPDATED_STAMP,
        TicketCreateInput.JSON_PROPERTY_BRAND_ID,
        TicketCreateInput.JSON_PROPERTY_COLLABORATORS,
        TicketCreateInput.JSON_PROPERTY_EMAIL_CC_IDS,
        TicketCreateInput.JSON_PROPERTY_FOLLOWER_IDS,
        TicketCreateInput.JSON_PROPERTY_MACRO_IDS,
        TicketCreateInput.JSON_PROPERTY_RAW_SUBJECT,
        TicketCreateInput.JSON_PROPERTY_RECIPIENT,
        TicketCreateInput.JSON_PROPERTY_SUBMITTER_ID,
        TicketCreateInput.JSON_PROPERTY_TICKET_FORM_ID,
        TicketCreateInput.JSON_PROPERTY_VIA,
        TicketCreateInput.JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID,
})
@Serdeable
public class TicketCreateInput {

    public static final String JSON_PROPERTY_COMMENT = "comment";
    public static final String JSON_PROPERTY_ADDITIONAL_COLLABORATORS = "additional_collaborators";
    public static final String JSON_PROPERTY_ASSIGNEE_EMAIL = "assignee_email";
    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_ATTRIBUTE_VALUE_IDS = "attribute_value_ids";
    public static final String JSON_PROPERTY_COLLABORATOR_IDS = "collaborator_ids";
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
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_COLLABORATORS = "collaborators";
    public static final String JSON_PROPERTY_EMAIL_CC_IDS = "email_cc_ids";
    public static final String JSON_PROPERTY_FOLLOWER_IDS = "follower_ids";
    public static final String JSON_PROPERTY_MACRO_IDS = "macro_ids";
    public static final String JSON_PROPERTY_RAW_SUBJECT = "raw_subject";
    public static final String JSON_PROPERTY_RECIPIENT = "recipient";
    public static final String JSON_PROPERTY_SUBMITTER_ID = "submitter_id";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";
    public static final String JSON_PROPERTY_VIA = "via";
    public static final String JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID = "via_followup_source_id";

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_COMMENT)
    private TicketComment comment;

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
     * Enterprise only. The id of the brand this ticket is associated with
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long brandId;

    /**
     * POST requests only. Users to add as cc's when creating a ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-collaborators\">Setting Collaborators</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Collaborator> collaborators;

    /**
     * The ids of agents or end users currently CC'ed on the ticket. See <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers resources</a> in the Support Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CC_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> emailCcIds;

    /**
     * The ids of agents currently following the ticket. See <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers resources</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> followerIds;

    /**
     * POST requests only. List of macro IDs to be recorded in the ticket audit
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> macroIds;

    /**
     * The dynamic content placeholder, if present, or the \"subject\" value, if not. See <a href=\"developer.zendesk.com/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawSubject;

    /**
     * The original recipient e-mail address of the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recipient;

    /**
     * The user who submitted the ticket. The submitter always becomes the author of the first comment on the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBMITTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long submitterId;

    /**
     * Enterprise only. The id of the ticket form to render for the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketFormId;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Via via;

    /**
     * POST requests only. The id of a closed ticket when creating a follow-up ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#creating-a-follow-up-ticket\">Creating a follow-up ticket</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long viaFollowupSourceId;

    public TicketCreateInput(TicketComment comment) {
        this.comment = comment;
    }

    /**
     * Add an item to the additionalCollaborators property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addAdditionalCollaboratorsItem(Collaborator additionalCollaboratorsItem) {
        if (additionalCollaborators == null) {
            additionalCollaborators = new ArrayList<>();
        }
        additionalCollaborators.add(additionalCollaboratorsItem);
        return this;
    }

    /**
     * Add an item to the attributeValueIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addAttributeValueIdsItem(Integer attributeValueIdsItem) {
        if (attributeValueIds == null) {
            attributeValueIds = new ArrayList<>();
        }
        attributeValueIds.add(attributeValueIdsItem);
        return this;
    }

    /**
     * Add an item to the collaboratorIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addCollaboratorIdsItem(Integer collaboratorIdsItem) {
        if (collaboratorIds == null) {
            collaboratorIds = new ArrayList<>();
        }
        collaboratorIds.add(collaboratorIdsItem);
        return this;
    }

    /**
     * Add an item to the customFields property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addCustomFieldsItem(CustomField customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return this;
    }

    /**
     * Add an item to the emailCcs property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addEmailCcsItem(EmailCC emailCcsItem) {
        if (emailCcs == null) {
            emailCcs = new ArrayList<>();
        }
        emailCcs.add(emailCcsItem);
        return this;
    }

    /**
     * Add an item to the followers property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addFollowersItem(Follower followersItem) {
        if (followers == null) {
            followers = new ArrayList<>();
        }
        followers.add(followersItem);
        return this;
    }

    /**
     * Add an item to the sharingAgreementIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addSharingAgreementIdsItem(Integer sharingAgreementIdsItem) {
        if (sharingAgreementIds == null) {
            sharingAgreementIds = new ArrayList<>();
        }
        sharingAgreementIds.add(sharingAgreementIdsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

    /**
     * Add an item to the collaborators property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addCollaboratorsItem(Collaborator collaboratorsItem) {
        if (collaborators == null) {
            collaborators = new ArrayList<>();
        }
        collaborators.add(collaboratorsItem);
        return this;
    }

    /**
     * Add an item to the emailCcIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addEmailCcIdsItem(Long emailCcIdsItem) {
        if (emailCcIds == null) {
            emailCcIds = new ArrayList<>();
        }
        emailCcIds.add(emailCcIdsItem);
        return this;
    }

    /**
     * Add an item to the followerIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addFollowerIdsItem(Long followerIdsItem) {
        if (followerIds == null) {
            followerIds = new ArrayList<>();
        }
        followerIds.add(followerIdsItem);
        return this;
    }

    /**
     * Add an item to the macroIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addMacroIdsItem(Long macroIdsItem) {
        if (macroIds == null) {
            macroIds = new ArrayList<>();
        }
        macroIds.add(macroIdsItem);
        return this;
    }

}
