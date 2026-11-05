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
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ticket object in the Zendesk Support Api.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
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
        Ticket.JSON_PROPERTY_VOICE_COMMENT
})
public record Ticket(
        @NotNull @JsonProperty(JSON_PROPERTY_REQUESTER_ID) Long requesterId,
        @Nullable @JsonProperty(JSON_PROPERTY_ALLOW_ATTACHMENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean allowAttachments,
        @Nullable @JsonProperty(JSON_PROPERTY_ALLOW_CHANNELBACK) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean allowChannelback,
        @Nullable @JsonProperty(JSON_PROPERTY_ASSIGNEE_EMAIL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String assigneeEmail,
        @Nullable @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long assigneeId,
        @Nullable @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> attributeValueIds,
        @Nullable @JsonProperty(JSON_PROPERTY_BRAND_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long brandId,
        @Nullable @JsonProperty(JSON_PROPERTY_COLLABORATOR_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> collaboratorIds,
        @Nullable @JsonProperty(JSON_PROPERTY_COLLABORATORS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Collaborator> collaborators,
        @Nullable @JsonProperty(JSON_PROPERTY_COMMENT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object comment,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid TicketCustomField> customFields,
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long customStatusId,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @JsonProperty(JSON_PROPERTY_DUE_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime dueAt,
        @Nullable @JsonProperty(JSON_PROPERTY_EMAIL_CC_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> emailCcIds,
        @Nullable @JsonProperty(JSON_PROPERTY_EMAIL_CCS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object emailCcs,
        @Nullable @JsonProperty(JSON_PROPERTY_EXTERNAL_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String externalId,
        @Nullable @JsonProperty(JSON_PROPERTY_FOLLOWER_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> followerIds,
        @Nullable @JsonProperty(JSON_PROPERTY_FOLLOWERS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object followers,
        @Nullable @JsonProperty(JSON_PROPERTY_FOLLOWUP_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> followupIds,
        @Nullable @JsonProperty(JSON_PROPERTY_FORUM_TOPIC_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long forumTopicId,
        @Nullable @JsonProperty(JSON_PROPERTY_FROM_MESSAGING_CHANNEL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean fromMessagingChannel,
        @Nullable @JsonProperty(JSON_PROPERTY_GENERATED_TIMESTAMP) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long generatedTimestamp,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUP_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long groupId,
        @Nullable @JsonProperty(JSON_PROPERTY_HAS_INCIDENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean hasIncidents,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_IS_PUBLIC) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean isPublic,
        @Nullable @JsonProperty(JSON_PROPERTY_MACRO_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long macroId,
        @Nullable @JsonProperty(JSON_PROPERTY_MACRO_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> macroIds,
        @Nullable @JsonProperty(JSON_PROPERTY_METADATA) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object metadata,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long organizationId,
        @Nullable @JsonProperty(JSON_PROPERTY_PRIORITY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketPriority priority,
        @Nullable @JsonProperty(JSON_PROPERTY_PROBLEM_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long problemId,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_SUBJECT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawSubject,
        @Nullable @JsonProperty(JSON_PROPERTY_RECIPIENT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String recipient,
        @Nullable @JsonProperty(JSON_PROPERTY_REQUESTER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object requester,
        @Nullable @JsonProperty(JSON_PROPERTY_SAFE_UPDATE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean safeUpdate,
        @Nullable @JsonProperty(JSON_PROPERTY_SATISFACTION_RATING) @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS) Map<String, Object> satisfactionRating,
        @Nullable @JsonProperty(JSON_PROPERTY_SHARING_AGREEMENT_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> sharingAgreementIds,
        @Nullable @JsonProperty(JSON_PROPERTY_STATUS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketStatus status,
        @Nullable @JsonProperty(JSON_PROPERTY_SUBJECT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String subject,
        @Nullable @JsonProperty(JSON_PROPERTY_SUBMITTER_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long submitterId,
        @Nullable @JsonProperty(JSON_PROPERTY_TAGS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> tags,
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long ticketFormId,
        @Nullable @JsonProperty(JSON_PROPERTY_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketType type,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_STAMP) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedStamp,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_VIA) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketVia via,
        @Nullable @JsonProperty(JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long viaFollowupSourceId,
        @Nullable @JsonProperty(JSON_PROPERTY_VIA_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long viaId,
        @Nullable @JsonProperty(JSON_PROPERTY_VOICE_COMMENT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object voiceComment
) implements Exportable, TicketBuilder.With {

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

    public Ticket() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Ticket(Long requesterId) {
        this(requesterId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Ticket withCustomStatusId(Integer customStatusId) {
        return withCustomStatusId(customStatusId != null ? customStatusId.longValue() : null);
    }

    public Ticket addAttributeValueIdsItem(Long attributeValueIdsItem) {
        List<Long> newItems = attributeValueIds == null ? new ArrayList<>() : new ArrayList<>(attributeValueIds);
        newItems.add(attributeValueIdsItem);
        return withAttributeValueIds(newItems);
    }

    public Ticket addCollaboratorIdsItem(Long collaboratorIdsItem) {
        List<Long> newItems = collaboratorIds == null ? new ArrayList<>() : new ArrayList<>(collaboratorIds);
        newItems.add(collaboratorIdsItem);
        return withCollaboratorIds(newItems);
    }

    public Ticket addCollaboratorsItem(Collaborator collaboratorsItem) {
        List<Collaborator> newItems = collaborators == null ? new ArrayList<>() : new ArrayList<>(collaborators);
        newItems.add(collaboratorsItem);
        return withCollaborators(newItems);
    }

    public Ticket addCustomFieldsItem(TicketCustomField customFieldsItem) {
        List<TicketCustomField> newItems = customFields == null ? new ArrayList<>() : new ArrayList<>(customFields);
        newItems.add(customFieldsItem);
        return withCustomFields(newItems);
    }

    public Ticket addEmailCcIdsItem(Long emailCcIdsItem) {
        List<Long> newItems = emailCcIds == null ? new ArrayList<>() : new ArrayList<>(emailCcIds);
        newItems.add(emailCcIdsItem);
        return withEmailCcIds(newItems);
    }

    public Ticket addFollowerIdsItem(Long followerIdsItem) {
        List<Long> newItems = followerIds == null ? new ArrayList<>() : new ArrayList<>(followerIds);
        newItems.add(followerIdsItem);
        return withFollowerIds(newItems);
    }

    public Ticket addMacroIdsItem(Long macroIdsItem) {
        List<Long> newItems = macroIds == null ? new ArrayList<>() : new ArrayList<>(macroIds);
        newItems.add(macroIdsItem);
        return withMacroIds(newItems);
    }

    public Ticket addSharingAgreementIdsItem(Long sharingAgreementIdsItem) {
        List<Long> newItems = sharingAgreementIds == null ? new ArrayList<>() : new ArrayList<>(sharingAgreementIds);
        newItems.add(sharingAgreementIdsItem);
        return withSharingAgreementIds(newItems);
    }

    public Ticket addTagsItem(String tagsItem) {
        List<String> newItems = tags == null ? new ArrayList<>() : new ArrayList<>(tags);
        newItems.add(tagsItem);
        return withTags(newItems);
    }
}
