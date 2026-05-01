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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ticket
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
        Ticket.JSON_PROPERTY_REQUESTER_ID,
        Ticket.JSON_PROPERTY_ALLOW_ATTACHMENTS,
        Ticket.JSON_PROPERTY_ALLOW_CHANNELBACK,
        Ticket.JSON_PROPERTY_ASSIGNEE_EMAIL,
        Ticket.JSON_PROPERTY_ASSIGNEE_ID,
        Ticket.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS,
        Ticket.JSON_PROPERTY_BRAND_ID,
        Ticket.JSON_PROPERTY_COLLABORATOR_IDS,
        Ticket.JSON_PROPERTY_COLLABORATORS,
        Ticket.JSON_PROPERTY_COMMENT,
        Ticket.JSON_PROPERTY_CREATED_AT,
        Ticket.JSON_PROPERTY_CUSTOM_FIELDS,
        Ticket.JSON_PROPERTY_CUSTOM_STATUS_ID,
        Ticket.JSON_PROPERTY_DESCRIPTION,
        Ticket.JSON_PROPERTY_DUE_AT,
        Ticket.JSON_PROPERTY_EMAIL_CC_IDS,
        Ticket.JSON_PROPERTY_EMAIL_CCS,
        Ticket.JSON_PROPERTY_EXTERNAL_ID,
        Ticket.JSON_PROPERTY_FOLLOWER_IDS,
        Ticket.JSON_PROPERTY_FOLLOWERS,
        Ticket.JSON_PROPERTY_FOLLOWUP_IDS,
        Ticket.JSON_PROPERTY_FORUM_TOPIC_ID,
        Ticket.JSON_PROPERTY_FROM_MESSAGING_CHANNEL,
        Ticket.JSON_PROPERTY_GENERATED_TIMESTAMP,
        Ticket.JSON_PROPERTY_GROUP_ID,
        Ticket.JSON_PROPERTY_HAS_INCIDENTS,
        Ticket.JSON_PROPERTY_ID,
        Ticket.JSON_PROPERTY_IS_PUBLIC,
        Ticket.JSON_PROPERTY_MACRO_ID,
        Ticket.JSON_PROPERTY_MACRO_IDS,
        Ticket.JSON_PROPERTY_METADATA,
        Ticket.JSON_PROPERTY_ORGANIZATION_ID,
        Ticket.JSON_PROPERTY_PRIORITY,
        Ticket.JSON_PROPERTY_PROBLEM_ID,
        Ticket.JSON_PROPERTY_RAW_SUBJECT,
        Ticket.JSON_PROPERTY_RECIPIENT,
        Ticket.JSON_PROPERTY_REQUESTER,
        Ticket.JSON_PROPERTY_SAFE_UPDATE,
        Ticket.JSON_PROPERTY_SATISFACTION_RATING,
        Ticket.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
        Ticket.JSON_PROPERTY_STATUS,
        Ticket.JSON_PROPERTY_SUBJECT,
        Ticket.JSON_PROPERTY_SUBMITTER_ID,
        Ticket.JSON_PROPERTY_TAGS,
        Ticket.JSON_PROPERTY_TICKET_FORM_ID,
        Ticket.JSON_PROPERTY_TYPE,
        Ticket.JSON_PROPERTY_UPDATED_AT,
        Ticket.JSON_PROPERTY_UPDATED_STAMP,
        Ticket.JSON_PROPERTY_URL,
        Ticket.JSON_PROPERTY_VIA,
        Ticket.JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID,
        Ticket.JSON_PROPERTY_VIA_ID,
        Ticket.JSON_PROPERTY_VOICE_COMMENT,
})
@Serdeable
public class Ticket {

    public static final String JSON_PROPERTY_REQUESTER_ID = "requester_id";
    public static final String JSON_PROPERTY_ALLOW_ATTACHMENTS = "allow_attachments";
    public static final String JSON_PROPERTY_ALLOW_CHANNELBACK = "allow_channelback";
    public static final String JSON_PROPERTY_ASSIGNEE_EMAIL = "assignee_email";
    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_ATTRIBUTE_VALUE_IDS = "attribute_value_ids";
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_COLLABORATOR_IDS = "collaborator_ids";
    public static final String JSON_PROPERTY_COLLABORATORS = "collaborators";
    public static final String JSON_PROPERTY_COMMENT = "comment";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CUSTOM_FIELDS = "custom_fields";
    public static final String JSON_PROPERTY_CUSTOM_STATUS_ID = "custom_status_id";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_DUE_AT = "due_at";
    public static final String JSON_PROPERTY_EMAIL_CC_IDS = "email_cc_ids";
    public static final String JSON_PROPERTY_EMAIL_CCS = "email_ccs";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_FOLLOWER_IDS = "follower_ids";
    public static final String JSON_PROPERTY_FOLLOWERS = "followers";
    public static final String JSON_PROPERTY_FOLLOWUP_IDS = "followup_ids";
    public static final String JSON_PROPERTY_FORUM_TOPIC_ID = "forum_topic_id";
    public static final String JSON_PROPERTY_FROM_MESSAGING_CHANNEL = "from_messaging_channel";
    public static final String JSON_PROPERTY_GENERATED_TIMESTAMP = "generated_timestamp";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";
    public static final String JSON_PROPERTY_HAS_INCIDENTS = "has_incidents";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IS_PUBLIC = "is_public";
    public static final String JSON_PROPERTY_MACRO_ID = "macro_id";
    public static final String JSON_PROPERTY_MACRO_IDS = "macro_ids";
    public static final String JSON_PROPERTY_METADATA = "metadata";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_PROBLEM_ID = "problem_id";
    public static final String JSON_PROPERTY_RAW_SUBJECT = "raw_subject";
    public static final String JSON_PROPERTY_RECIPIENT = "recipient";
    public static final String JSON_PROPERTY_REQUESTER = "requester";
    public static final String JSON_PROPERTY_SAFE_UPDATE = "safe_update";
    public static final String JSON_PROPERTY_SATISFACTION_RATING = "satisfaction_rating";
    public static final String JSON_PROPERTY_SHARING_AGREEMENT_IDS = "sharing_agreement_ids";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_SUBMITTER_ID = "submitter_id";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_UPDATED_STAMP = "updated_stamp";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VIA = "via";
    public static final String JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID = "via_followup_source_id";
    public static final String JSON_PROPERTY_VIA_ID = "via_id";
    public static final String JSON_PROPERTY_VOICE_COMMENT = "voice_comment";

    /**
     * The user who requested this ticket
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    private Long requesterId;

    /**
     * Permission for agents to add attachments to a comment. Defaults to true
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowAttachments;

    /**
     * Is false if channelback is disabled, true otherwise. Only applicable for channels framework ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_CHANNELBACK)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowChannelback;

    /**
     * Write only. The email address of the agent to assign the ticket to
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assigneeEmail;

    /**
     * The agent currently assigned to the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long assigneeId;

    /**
     * Write only. An array of the IDs of attribute values to be associated with the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> attributeValueIds;

    /**
     * The id of the brand this ticket is associated with. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4408829476378\">Setting up multiple brands</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long brandId;

    /**
     * The ids of users currently CC'ed on the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATOR_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> collaboratorIds;

    /**
     * POST requests only. Users to add as cc's when creating a ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-collaborators\">Setting Collaborators</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Collaborator> collaborators;

    /**
     * Write only. An object that adds a comment to the ticket. See <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/ticket_comments/\">Ticket comments</a>. To include an attachment with the comment, see <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#attaching-files\">Attaching files</a>. A ticket can contain up to 5000 comments in total, including both public and private comments. Once this limit is reached, any additional attempts to add comments results in a 422 error. The ticket can still be updated in other ways, provided that no new comments are added.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object comment;

    /**
     * When this record was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * Custom fields for the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-custom-field-values\">Setting custom field values</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketCustomFieldsInner> customFields;

    /**
     * The custom ticket status id of the ticket. See <a href=\"#custom-ticket-statuses\">custom ticket statuses</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long customStatusId;

    /**
     * Read-only first comment on the ticket. When <a href=\"#create-ticket\">creating a ticket</a>, use <code>comment</code> to set the description. See <a href=\"#description-and-first-comment\">Description and first comment</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * If this is a ticket of type \"task\" it has a due date.  Due date format uses <a href=\"http://en.wikipedia.org/wiki/ISO_8601\">ISO 8601</a> format
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DUE_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime dueAt;

    /**
     * The ids of agents or end users currently CC'ed on the ticket. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CC_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> emailCcIds;

    /**
     * Write only. An array of objects that represents agent or end users email CCs to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-email-ccs\">Setting email CCs</a>. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CCS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object emailCcs;

    /**
     * An id you can use to link Zendesk Support tickets to local records
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * The ids of agents currently following the ticket. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> followerIds;

    /**
     * Write only. An array of objects that represents agent followers to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-followers\">Setting followers</a>. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object followers;

    /**
     * The ids of the followups created from this ticket. Ids are only visible once the ticket is closed
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWUP_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> followupIds;

    /**
     * The topic in the Zendesk Web portal this ticket originated from, if any. The Web portal is deprecated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FORUM_TOPIC_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long forumTopicId;

    /**
     * If true, the ticket's <a href=\"/documentation/ticketing/reference-guides/via-object-reference/\">via type</a> is a messaging channel.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FROM_MESSAGING_CHANNEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean fromMessagingChannel;

    /**
     * A Unix timestamp that represents the most accurate reading of when this record was last updated. It is updated for all ticket updates, including system updates
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GENERATED_TIMESTAMP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long generatedTimestamp;

    /**
     * The group this ticket is assigned to
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long groupId;

    /**
     * Is true if a ticket is a problem type and has one or more incidents linked to it. Otherwise, the value is false.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HAS_INCIDENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean hasIncidents;

    /**
     * Automatically assigned when the ticket is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * Is true if any comments are public, false otherwise
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isPublic;

    /**
     * Write only. A macro ID to be recorded in the ticket audit
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long macroId;

    /**
     * POST requests only. List of macro IDs to be recorded in the ticket audit
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> macroIds;

    /**
     * Write only. Metadata for the audit. In the <code>audit</code> object, the data is specified in the <code>custom</code> property of the <code>metadata</code> object. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-metadata\">Setting Metadata</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METADATA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object metadata;

    /**
     * The organization of the requester. You can only specify the ID of an organization associated with the requester. See <a href=\"developer.zendesk.com/api-reference/ticketing/organizations/organization_memberships/\">Organization Memberships</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketPriority priority;

    /**
     * For tickets of type \"incident\", the ID of the problem the incident is linked to
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PROBLEM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long problemId;

    /**
     * The dynamic content placeholder, if present, or the \"subject\" value, if not. See <a href=\"developer.zendesk.com/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawSubject;

    /**
     * The original recipient e-mail address of the ticket. Notification emails for the ticket are sent from this address
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recipient;

    /**
     * Write only. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#creating-a-ticket-with-a-new-requester\">Creating a ticket with a new requester</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object requester;

    /**
     * Write only. Optional boolean. When true and an <code>update_stamp</code> date is included, protects against ticket update collisions and returns a message to let you know if one occurs. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#protecting-against-ticket-update-collisions\">Protecting against ticket update collisions</a>. A value of false has the same effect as true. Omit the property to force the updates to not be safe
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SAFE_UPDATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean safeUpdate;

    /**
     * The satisfaction rating of the ticket, if it exists, or the state of satisfaction, \"offered\" or \"unoffered\". The value is null for plan types that don't support CSAT
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SATISFACTION_RATING)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> satisfactionRating;

    /**
     * The ids of the sharing agreements used for this ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARING_AGREEMENT_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> sharingAgreementIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketStatus status;

    /**
     * The value of the subject field for this ticket. See <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/tickets/#subject\">Subject</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    /**
     * The user who submitted the ticket. The submitter always becomes the author of the first comment on the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBMITTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long submitterId;

    /**
     * The array of tags applied to this ticket. Unless otherwise specified, the <a href=\"developer.zendesk.com/api-reference/ticketing/ticket-management/tags/#set-tags\">set tag</a> behavior is used, which overwrites and replaces existing tags
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    /**
     * Enterprise only. The id of the ticket form to render for the ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketFormId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketType type;

    /**
     * When this record last got updated. It is updated only if the update generates a <a href=\"#incremental-ticket-event-export\">ticket event</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * Write only. Datetime of last update received from API. See the <code>safe_update</code> property
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_STAMP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedStamp;

    /**
     * The API url of this ticket
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketVia via;

    /**
     * POST requests only. The id of a closed ticket when creating a follow-up ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#creating-a-follow-up-ticket\">Creating a follow-up ticket</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long viaFollowupSourceId;

    /**
     * Write only. For more information, see the <a href=\"/documentation/ticketing/reference-guides/via-object-reference/\">Via object reference</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIA_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long viaId;

    /**
     * Write only. See <a href=\"developer.zendesk.com/api-reference/voice/talk-partner-edition-api/reference/#creating-voicemail-tickets\">Creating voicemail ticket</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VOICE_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object voiceComment;

    public Ticket(Long requesterId) {
        this.requesterId = requesterId;
    }

    /**
     * Add an item to the attributeValueIds property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addAttributeValueIdsItem(Long attributeValueIdsItem) {
        if (attributeValueIds == null) {
            attributeValueIds = new ArrayList<>();
        }
        attributeValueIds.add(attributeValueIdsItem);
        return this;
    }

    /**
     * Add an item to the collaboratorIds property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addCollaboratorIdsItem(Long collaboratorIdsItem) {
        if (collaboratorIds == null) {
            collaboratorIds = new ArrayList<>();
        }
        collaboratorIds.add(collaboratorIdsItem);
        return this;
    }

    /**
     * Add an item to the collaborators property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addCollaboratorsItem(Collaborator collaboratorsItem) {
        if (collaborators == null) {
            collaborators = new ArrayList<>();
        }
        collaborators.add(collaboratorsItem);
        return this;
    }

    /**
     * Add an item to the customFields property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addCustomFieldsItem(TicketCustomFieldsInner customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return this;
    }

    /**
     * Add an item to the emailCcIds property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addEmailCcIdsItem(Long emailCcIdsItem) {
        if (emailCcIds == null) {
            emailCcIds = new ArrayList<>();
        }
        emailCcIds.add(emailCcIdsItem);
        return this;
    }

    /**
     * Add an item to the followerIds property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addFollowerIdsItem(Long followerIdsItem) {
        if (followerIds == null) {
            followerIds = new ArrayList<>();
        }
        followerIds.add(followerIdsItem);
        return this;
    }

    /**
     * Add an item to the macroIds property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addMacroIdsItem(Long macroIdsItem) {
        if (macroIds == null) {
            macroIds = new ArrayList<>();
        }
        macroIds.add(macroIdsItem);
        return this;
    }

    /**
     * Add an item to the sharingAgreementIds property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addSharingAgreementIdsItem(Long sharingAgreementIdsItem) {
        if (sharingAgreementIds == null) {
            sharingAgreementIds = new ArrayList<>();
        }
        sharingAgreementIds.add(sharingAgreementIdsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of Ticket for chaining.
     */
    public Ticket addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

}
