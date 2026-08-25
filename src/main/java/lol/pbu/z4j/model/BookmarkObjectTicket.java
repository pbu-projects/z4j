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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lol.pbu.z4j.model.ApprovalTicketDetailsCustomFieldsInner;
import lol.pbu.z4j.model.CollaboratorObject;
import lol.pbu.z4j.model.TicketObjectPriority;
import lol.pbu.z4j.model.TicketObjectStatus;
import lol.pbu.z4j.model.TicketObjectType;
import lol.pbu.z4j.model.TicketObjectVia;
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
 * BookmarkObjectTicket
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    BookmarkObjectTicket.JSON_PROPERTY_REQUESTER_ID,
    BookmarkObjectTicket.JSON_PROPERTY_ALLOW_ATTACHMENTS,
    BookmarkObjectTicket.JSON_PROPERTY_ALLOW_CHANNELBACK,
    BookmarkObjectTicket.JSON_PROPERTY_ASSIGNEE_EMAIL,
    BookmarkObjectTicket.JSON_PROPERTY_ASSIGNEE_ID,
    BookmarkObjectTicket.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS,
    BookmarkObjectTicket.JSON_PROPERTY_BRAND_ID,
    BookmarkObjectTicket.JSON_PROPERTY_COLLABORATOR_IDS,
    BookmarkObjectTicket.JSON_PROPERTY_COLLABORATORS,
    BookmarkObjectTicket.JSON_PROPERTY_COMMENT,
    BookmarkObjectTicket.JSON_PROPERTY_CREATED_AT,
    BookmarkObjectTicket.JSON_PROPERTY_CUSTOM_FIELDS,
    BookmarkObjectTicket.JSON_PROPERTY_CUSTOM_STATUS_ID,
    BookmarkObjectTicket.JSON_PROPERTY_DESCRIPTION,
    BookmarkObjectTicket.JSON_PROPERTY_DUE_AT,
    BookmarkObjectTicket.JSON_PROPERTY_EMAIL_CC_IDS,
    BookmarkObjectTicket.JSON_PROPERTY_EMAIL_CCS,
    BookmarkObjectTicket.JSON_PROPERTY_ENCODED_ID,
    BookmarkObjectTicket.JSON_PROPERTY_EXTERNAL_ID,
    BookmarkObjectTicket.JSON_PROPERTY_FOLLOWER_IDS,
    BookmarkObjectTicket.JSON_PROPERTY_FOLLOWERS,
    BookmarkObjectTicket.JSON_PROPERTY_FOLLOWUP_IDS,
    BookmarkObjectTicket.JSON_PROPERTY_FORUM_TOPIC_ID,
    BookmarkObjectTicket.JSON_PROPERTY_FROM_MESSAGING_CHANNEL,
    BookmarkObjectTicket.JSON_PROPERTY_GENERATED_TIMESTAMP,
    BookmarkObjectTicket.JSON_PROPERTY_GROUP_ID,
    BookmarkObjectTicket.JSON_PROPERTY_HAS_INCIDENTS,
    BookmarkObjectTicket.JSON_PROPERTY_ID,
    BookmarkObjectTicket.JSON_PROPERTY_IS_PUBLIC,
    BookmarkObjectTicket.JSON_PROPERTY_MACRO_ID,
    BookmarkObjectTicket.JSON_PROPERTY_MACRO_IDS,
    BookmarkObjectTicket.JSON_PROPERTY_METADATA,
    BookmarkObjectTicket.JSON_PROPERTY_ORGANIZATION_ID,
    BookmarkObjectTicket.JSON_PROPERTY_PRIORITY,
    BookmarkObjectTicket.JSON_PROPERTY_PROBLEM_ID,
    BookmarkObjectTicket.JSON_PROPERTY_RAW_SUBJECT,
    BookmarkObjectTicket.JSON_PROPERTY_RECIPIENT,
    BookmarkObjectTicket.JSON_PROPERTY_REQUESTER,
    BookmarkObjectTicket.JSON_PROPERTY_SAFE_UPDATE,
    BookmarkObjectTicket.JSON_PROPERTY_SATISFACTION_RATING,
    BookmarkObjectTicket.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
    BookmarkObjectTicket.JSON_PROPERTY_STATUS,
    BookmarkObjectTicket.JSON_PROPERTY_SUBJECT,
    BookmarkObjectTicket.JSON_PROPERTY_SUBMITTER_ID,
    BookmarkObjectTicket.JSON_PROPERTY_TAGS,
    BookmarkObjectTicket.JSON_PROPERTY_TICKET_FORM_ID,
    BookmarkObjectTicket.JSON_PROPERTY_TYPE,
    BookmarkObjectTicket.JSON_PROPERTY_UPDATED_AT,
    BookmarkObjectTicket.JSON_PROPERTY_UPDATED_STAMP,
    BookmarkObjectTicket.JSON_PROPERTY_URL,
    BookmarkObjectTicket.JSON_PROPERTY_VIA,
    BookmarkObjectTicket.JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID,
    BookmarkObjectTicket.JSON_PROPERTY_VIA_ID,
    BookmarkObjectTicket.JSON_PROPERTY_VOICE_COMMENT,
})
@Serdeable
public class BookmarkObjectTicket {

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
    public static final String JSON_PROPERTY_ENCODED_ID = "encoded_id";
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
     * <p>The user who requested this ticket</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    private Integer requesterId;

    /**
     * <p>Permission for agents to add add attachments to a comment. Defaults to true</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowAttachments;

    /**
     * <p>Is false if channelback is disabled, true otherwise. Only applicable for channels framework ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_CHANNELBACK)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowChannelback;

    /**
     * <p>Write only. The email address of the agent to assign the ticket to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assigneeEmail;

    /**
     * <p>The agent currently assigned to the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer assigneeId;

    /**
     * <p>Write only. An array of the IDs of attribute values to be associated with the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> attributeValueIds;

    /**
     * <p>The id of the brand this ticket is associated with. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4408829476378\">Setting up multiple brands</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer brandId;

    /**
     * <p>The ids of users currently CC'ed on the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATOR_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> collaboratorIds;

    /**
     * <p>POST requests only. Users to add as cc's when creating a ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-collaborators\">Setting Collaborators</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CollaboratorObject> collaborators;

    /**
     * <p>Write only. An object that adds a comment to the ticket. See <a href=\"/api-reference/ticketing/tickets/ticket_comments/\">Ticket comments</a>. To include an attachment with the comment, see <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#attaching-files\">Attaching files</a>. A ticket can contain up to 5000 comments in total, including both public and private comments. Once this limit is reached, any additional attempts to add comments results in a 422 error. The ticket can still be updated in other ways, provided that no new comments are added.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object comment;

    /**
     * <p>When this record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Custom fields for the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-custom-field-values\">Setting custom field values</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ApprovalTicketDetailsCustomFieldsInner> customFields;

    /**
     * <p>The custom ticket status id of the ticket. See <a href=\"#custom-ticket-statuses\">custom ticket statuses</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer customStatusId;

    /**
     * <p>Read-only first comment on the ticket. When <a href=\"#create-ticket\">creating a ticket</a>, use <code>comment</code> to set the description. See <a href=\"#description-and-first-comment\">Description and first comment</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>If this is a ticket of type \"task\" it has a due date.  Due date format uses <a href=\"http://en.wikipedia.org/wiki/ISO_8601\">ISO 8601</a> format</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DUE_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime dueAt;

    /**
     * <p>The ids of agents or end users currently CC'ed on the ticket. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CC_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> emailCcIds;

    /**
     * <p>Write only. An array of objects that represents agent or end users email CCs to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-email-ccs\">Setting email CCs</a>. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CCS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object emailCcs;

    /**
     * <p>An encoded string representing the ticket's unique identifier</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ENCODED_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String encodedId;

    /**
     * <p>An id you can use to link Zendesk Support tickets to local records</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * <p>The ids of agents currently following the ticket. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> followerIds;

    /**
     * <p>Write only. An array of objects that represents agent followers to add or delete from the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-followers\">Setting followers</a>. Ignored when <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers</a> is not enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object followers;

    /**
     * <p>The ids of the followups created from this ticket. Ids are only visible once the ticket is closed</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWUP_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> followupIds;

    /**
     * <p>The topic in the Zendesk Web portal this ticket originated from, if any. The Web portal is deprecated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FORUM_TOPIC_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer forumTopicId;

    /**
     * <p>If true, the ticket's <a href=\"/documentation/ticketing/reference-guides/via-object-reference/\">via type</a> is a messaging channel.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FROM_MESSAGING_CHANNEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean fromMessagingChannel;

    /**
     * <p>A Unix timestamp that represents the most accurate reading of when this record was last updated. It is updated for all ticket updates, including system updates</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GENERATED_TIMESTAMP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer generatedTimestamp;

    /**
     * <p>The group this ticket is assigned to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer groupId;

    /**
     * <p>Is true if a ticket is a problem type and has one or more incidents linked to it. Otherwise, the value is false.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HAS_INCIDENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean hasIncidents;

    /**
     * <p>Automatically assigned when the ticket is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Is true if any comments are public, false otherwise</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isPublic;

    /**
     * <p>Write only. A macro ID to be recorded in the ticket audit</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer macroId;

    /**
     * <p>POST requests only. List of macro IDs to be recorded in the ticket audit</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> macroIds;

    /**
     * <p>Write only. Metadata for the audit. In the <code>audit</code> object, the data is specified in the <code>custom</code> property of the <code>metadata</code> object. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-metadata\">Setting Metadata</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METADATA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object metadata;

    /**
     * <p>The organization of the requester. You can only specify the ID of an organization associated with the requester. See <a href=\"/api-reference/ticketing/organizations/organization_memberships/\">Organization Memberships</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketObjectPriority priority;

    /**
     * <p>For tickets of type \"incident\", the ID of the problem the incident is linked to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PROBLEM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer problemId;

    /**
     * <p>The dynamic content placeholder, if present, or the \"subject\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawSubject;

    /**
     * <p>The original recipient e-mail address of the ticket. Notification emails for the ticket are sent from this address</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recipient;

    /**
     * <p>Write only. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#creating-a-ticket-with-a-new-requester\">Creating a ticket with a new requester</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object requester;

    /**
     * <p>Write only. Optional boolean. When true and an <code>update_stamp</code> date is included, protects against ticket update collisions and returns a message to let you know if one occurs. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#protecting-against-ticket-update-collisions\">Protecting against ticket update collisions</a>. A value of false has the same effect as true. Omit the property to force the updates to not be safe</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SAFE_UPDATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean safeUpdate;

    /**
     * <p>The satisfaction rating of the ticket, if it exists, or the state of satisfaction, \"offered\" or \"unoffered\". The value is null for plan types that don't support CSAT</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SATISFACTION_RATING)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> satisfactionRating;

    /**
     * <p>The ids of the sharing agreements used for this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARING_AGREEMENT_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> sharingAgreementIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketObjectStatus status;

    /**
     * <p>The value of the subject field for this ticket. See <a href=\"/api-reference/ticketing/tickets/tickets/#subject\">Subject</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    /**
     * <p>The user who submitted the ticket. The submitter always becomes the author of the first comment on the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBMITTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer submitterId;

    /**
     * <p>The array of tags applied to this ticket. Unless otherwise specified, the <a href=\"/api-reference/ticketing/ticket-management/tags/#set-tags\">set tag</a> behavior is used, which overwrites and replaces existing tags</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    /**
     * <p>Enterprise only. The id of the ticket form to render for the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer ticketFormId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketObjectType type;

    /**
     * <p>When this record last got updated. It is updated only if the update generates a <a href=\"#incremental-ticket-event-export\">ticket event</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>Write only. Datetime of last update received from API. See the <code>safe_update</code> property</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_STAMP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedStamp;

    /**
     * <p>The API url of this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketObjectVia via;

    /**
     * <p>POST requests only. The id of a closed ticket when creating a follow-up ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#creating-a-follow-up-ticket\">Creating a follow-up ticket</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer viaFollowupSourceId;

    /**
     * <p>Write only. For more information, see the <a href=\"/documentation/ticketing/reference-guides/via-object-reference/\">Via object reference</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIA_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer viaId;

    /**
     * <p>Write only. See <a href=\"/api-reference/voice/talk-partner-edition-api/basics/#creating-voicemail-tickets\">Creating voicemail ticket</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VOICE_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object voiceComment;

    public BookmarkObjectTicket(Integer requesterId) {
        this.requesterId = requesterId;
    }

    /**
     * Add an item to the attributeValueIds property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addAttributeValueIdsItem(Integer attributeValueIdsItem) {
        if (attributeValueIds == null) {
            attributeValueIds = new ArrayList<>();
        }
        attributeValueIds.add(attributeValueIdsItem);
        return this;
    }

    /**
     * Add an item to the collaboratorIds property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addCollaboratorIdsItem(Integer collaboratorIdsItem) {
        if (collaboratorIds == null) {
            collaboratorIds = new ArrayList<>();
        }
        collaboratorIds.add(collaboratorIdsItem);
        return this;
    }

    /**
     * Add an item to the collaborators property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addCollaboratorsItem(CollaboratorObject collaboratorsItem) {
        if (collaborators == null) {
            collaborators = new ArrayList<>();
        }
        collaborators.add(collaboratorsItem);
        return this;
    }

    /**
     * Add an item to the customFields property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addCustomFieldsItem(ApprovalTicketDetailsCustomFieldsInner customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return this;
    }

    /**
     * Add an item to the emailCcIds property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addEmailCcIdsItem(Integer emailCcIdsItem) {
        if (emailCcIds == null) {
            emailCcIds = new ArrayList<>();
        }
        emailCcIds.add(emailCcIdsItem);
        return this;
    }

    /**
     * Add an item to the followerIds property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addFollowerIdsItem(Integer followerIdsItem) {
        if (followerIds == null) {
            followerIds = new ArrayList<>();
        }
        followerIds.add(followerIdsItem);
        return this;
    }

    /**
     * Add an item to the macroIds property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addMacroIdsItem(Integer macroIdsItem) {
        if (macroIds == null) {
            macroIds = new ArrayList<>();
        }
        macroIds.add(macroIdsItem);
        return this;
    }

    /**
     * Add an item to the sharingAgreementIds property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addSharingAgreementIdsItem(Integer sharingAgreementIdsItem) {
        if (sharingAgreementIds == null) {
            sharingAgreementIds = new ArrayList<>();
        }
        sharingAgreementIds.add(sharingAgreementIdsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of BookmarkObjectTicket for chaining.
     */
    public BookmarkObjectTicket addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

}