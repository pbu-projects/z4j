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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Payload data for updating existing tickets.
 * Unset fields are omitted, empty lists serialize as {@code []}, and {@code clear*()} methods
 * send an explicit {@code null}. Calling {@code with*()} or {@code add*Item()} on a cleared
 * field resets its cleared state so a later {@code with*(null)} omits the field.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketInputBase.JSON_PROPERTY_ADDITIONAL_COLLABORATORS,
        TicketInputBase.JSON_PROPERTY_ADDITIONAL_TAGS,
        TicketInputBase.JSON_PROPERTY_ASSIGNEE_EMAIL,
        TicketInputBase.JSON_PROPERTY_ASSIGNEE_ID,
        TicketInputBase.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS,
        TicketInputBase.JSON_PROPERTY_COLLABORATOR_IDS,
        TicketInputBase.JSON_PROPERTY_COMMENT,
        TicketInputBase.JSON_PROPERTY_CUSTOM_FIELDS,
        TicketInputBase.JSON_PROPERTY_CUSTOM_STATUS_ID,
        TicketInputBase.JSON_PROPERTY_DUE_AT,
        TicketInputBase.JSON_PROPERTY_EMAIL_CCS,
        TicketInputBase.JSON_PROPERTY_EXTERNAL_ID,
        TicketInputBase.JSON_PROPERTY_FOLLOWERS,
        TicketInputBase.JSON_PROPERTY_GROUP_ID,
        TicketInputBase.JSON_PROPERTY_ORGANIZATION_ID,
        TicketInputBase.JSON_PROPERTY_PRIORITY,
        TicketInputBase.JSON_PROPERTY_PROBLEM_ID,
        TicketInputBase.JSON_PROPERTY_REMOVE_TAGS,
        TicketInputBase.JSON_PROPERTY_REQUESTER_ID,
        TicketInputBase.JSON_PROPERTY_SAFE_UPDATE,
        TicketInputBase.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
        TicketInputBase.JSON_PROPERTY_STATUS,
        TicketInputBase.JSON_PROPERTY_SUBJECT,
        TicketInputBase.JSON_PROPERTY_TAGS,
        TicketInputBase.JSON_PROPERTY_TYPE,
        TicketInputBase.JSON_PROPERTY_UPDATED_STAMP,
        TicketUpdateInput.JSON_PROPERTY_TICKET_FORM_ID
})
public record TicketUpdateInput(
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_ADDITIONAL_COLLABORATORS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@Valid Collaborator> additionalCollaborators,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_ADDITIONAL_TAGS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull String> additionalTags,
        @Nullable @Email @JsonProperty(TicketInputBase.JSON_PROPERTY_ASSIGNEE_EMAIL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String assigneeEmail,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_ASSIGNEE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long assigneeId,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull Long> attributeValueIds,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_COLLABORATOR_IDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull Long> collaboratorIds,
        @Nullable @Valid @JsonProperty(TicketInputBase.JSON_PROPERTY_COMMENT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketComment comment,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_CUSTOM_FIELDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@Valid TicketCustomField> customFields,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_CUSTOM_STATUS_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long customStatusId,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_DUE_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime dueAt,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_EMAIL_CCS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@Valid EmailCC> emailCcs,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_EXTERNAL_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String externalId,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_FOLLOWERS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@Valid Follower> followers,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_GROUP_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long groupId,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_ORGANIZATION_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long organizationId,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_PRIORITY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketUpdateInputPriority priority,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_PROBLEM_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long problemId,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_REMOVE_TAGS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull String> removeTags,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_REQUESTER_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long requesterId,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_SAFE_UPDATE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean safeUpdate,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_SHARING_AGREEMENT_IDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull Long> sharingAgreementIds,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_STATUS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketUpdateInputStatus status,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_SUBJECT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String subject,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_TAGS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull String> tags,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketUpdateInputType type,
        @Nullable @JsonProperty(TicketInputBase.JSON_PROPERTY_UPDATED_STAMP) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedStamp,
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long ticketFormId,
        @Nullable @JsonIgnore Set<String> clearedFields
) implements TicketInputBase, TicketUpdateInputBuilder.With {

    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";

    public TicketUpdateInput {
        clearedFields = clearedFields == null ? Set.of() : Set.copyOf(clearedFields);
    }

    public TicketUpdateInput() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Set.of());
    }

    public TicketUpdateInput(
            List<Collaborator> additionalCollaborators,
            List<String> additionalTags,
            String assigneeEmail,
            Long assigneeId,
            List<Long> attributeValueIds,
            List<Long> collaboratorIds,
            TicketComment comment,
            List<TicketCustomField> customFields,
            Long customStatusId,
            ZonedDateTime dueAt,
            List<EmailCC> emailCcs,
            String externalId,
            List<Follower> followers,
            Long groupId,
            Long organizationId,
            TicketUpdateInputPriority priority,
            Long problemId,
            List<String> removeTags,
            Long requesterId,
            Boolean safeUpdate,
            List<Long> sharingAgreementIds,
            TicketUpdateInputStatus status,
            String subject,
            List<String> tags,
            TicketUpdateInputType type,
            ZonedDateTime updatedStamp,
            Long ticketFormId
    ) {
        this(additionalCollaborators, additionalTags, assigneeEmail, assigneeId, attributeValueIds, collaboratorIds,
                comment, customFields, customStatusId, dueAt, emailCcs, externalId, followers, groupId,
                organizationId, priority, problemId, removeTags, requesterId, safeUpdate, sharingAgreementIds,
                status, subject, tags, type, updatedStamp, ticketFormId, Set.of());
    }

    public TicketUpdateInput addAdditionalCollaboratorsItem(Collaborator additionalCollaboratorsItem) {
        List<Collaborator> newItems = additionalCollaborators == null ? new ArrayList<>() : new ArrayList<>(additionalCollaborators);
        newItems.add(additionalCollaboratorsItem);
        return withAdditionalCollaborators(newItems);
    }

    public TicketUpdateInput addAttributeValueIdsItem(Long attributeValueIdsItem) {
        List<Long> newItems = attributeValueIds == null ? new ArrayList<>() : new ArrayList<>(attributeValueIds);
        newItems.add(attributeValueIdsItem);
        return withAttributeValueIds(newItems);
    }

    public TicketUpdateInput addCollaboratorIdsItem(Long collaboratorIdsItem) {
        List<Long> newItems = collaboratorIds == null ? new ArrayList<>() : new ArrayList<>(collaboratorIds);
        newItems.add(collaboratorIdsItem);
        return withCollaboratorIds(newItems);
    }

    public TicketUpdateInput addCustomFieldsItem(TicketCustomField customFieldsItem) {
        List<TicketCustomField> newItems = customFields == null ? new ArrayList<>() : new ArrayList<>(customFields);
        newItems.add(customFieldsItem);
        return withCustomFields(newItems);
    }

    public TicketUpdateInput addEmailCcsItem(EmailCC emailCcsItem) {
        List<EmailCC> newItems = emailCcs == null ? new ArrayList<>() : new ArrayList<>(emailCcs);
        newItems.add(emailCcsItem);
        return withEmailCcs(newItems);
    }

    public TicketUpdateInput addFollowersItem(Follower followersItem) {
        List<Follower> newItems = followers == null ? new ArrayList<>() : new ArrayList<>(followers);
        newItems.add(followersItem);
        return withFollowers(newItems);
    }

    public TicketUpdateInput addSharingAgreementIdsItem(Long sharingAgreementIdsItem) {
        List<Long> newItems = sharingAgreementIds == null ? new ArrayList<>() : new ArrayList<>(sharingAgreementIds);
        newItems.add(sharingAgreementIdsItem);
        return withSharingAgreementIds(newItems);
    }

    public TicketUpdateInput addTagsItem(String tagsItem) {
        List<String> newItems = tags == null ? new ArrayList<>() : new ArrayList<>(tags);
        newItems.add(tagsItem);
        return withTags(newItems);
    }

    public TicketUpdateInput addAdditionalTagsItem(String additionalTagsItem) {
        List<String> newItems = additionalTags == null ? new ArrayList<>() : new ArrayList<>(additionalTags);
        newItems.add(additionalTagsItem);
        return withAdditionalTags(newItems);
    }

    public TicketUpdateInput addRemoveTagsItem(String removeTagsItem) {
        List<String> newItems = removeTags == null ? new ArrayList<>() : new ArrayList<>(removeTags);
        newItems.add(removeTagsItem);
        return withRemoveTags(newItems);
    }

    @Override
    public TicketUpdateInput withAssigneeEmail(@Nullable String assigneeEmail) {
        return TicketUpdateInputBuilder.With.super.withAssigneeEmail(assigneeEmail)
                .unmarkCleared(JSON_PROPERTY_ASSIGNEE_EMAIL);
    }

    @Override
    public TicketUpdateInput withAssigneeId(@Nullable Long assigneeId) {
        return TicketUpdateInputBuilder.With.super.withAssigneeId(assigneeId)
                .unmarkCleared(JSON_PROPERTY_ASSIGNEE_ID);
    }

    @Override
    public TicketUpdateInput withCollaboratorIds(@Nullable List<Long> collaboratorIds) {
        return TicketUpdateInputBuilder.With.super.withCollaboratorIds(collaboratorIds)
                .unmarkCleared(JSON_PROPERTY_COLLABORATOR_IDS);
    }

    @Override
    public TicketUpdateInput withCustomFields(@Nullable List<TicketCustomField> customFields) {
        return TicketUpdateInputBuilder.With.super.withCustomFields(customFields)
                .unmarkCleared(JSON_PROPERTY_CUSTOM_FIELDS);
    }

    @Override
    public TicketUpdateInput withCustomStatusId(@Nullable Long customStatusId) {
        return TicketUpdateInputBuilder.With.super.withCustomStatusId(customStatusId)
                .unmarkCleared(JSON_PROPERTY_CUSTOM_STATUS_ID);
    }

    public TicketUpdateInput withCustomStatusId(@Nullable Integer customStatusId) {
        return withCustomStatusId(customStatusId != null ? customStatusId.longValue() : null);
    }

    @Override
    public TicketUpdateInput withDueAt(@Nullable ZonedDateTime dueAt) {
        return TicketUpdateInputBuilder.With.super.withDueAt(dueAt)
                .unmarkCleared(JSON_PROPERTY_DUE_AT);
    }

    @Override
    public TicketUpdateInput withEmailCcs(@Nullable List<EmailCC> emailCcs) {
        return TicketUpdateInputBuilder.With.super.withEmailCcs(emailCcs)
                .unmarkCleared(JSON_PROPERTY_EMAIL_CCS);
    }

    @Override
    public TicketUpdateInput withExternalId(@Nullable String externalId) {
        return TicketUpdateInputBuilder.With.super.withExternalId(externalId)
                .unmarkCleared(JSON_PROPERTY_EXTERNAL_ID);
    }

    @Override
    public TicketUpdateInput withFollowers(@Nullable List<Follower> followers) {
        return TicketUpdateInputBuilder.With.super.withFollowers(followers)
                .unmarkCleared(JSON_PROPERTY_FOLLOWERS);
    }

    @Override
    public TicketUpdateInput withGroupId(@Nullable Long groupId) {
        return TicketUpdateInputBuilder.With.super.withGroupId(groupId)
                .unmarkCleared(JSON_PROPERTY_GROUP_ID);
    }

    @Override
    public TicketUpdateInput withOrganizationId(@Nullable Long organizationId) {
        return TicketUpdateInputBuilder.With.super.withOrganizationId(organizationId)
                .unmarkCleared(JSON_PROPERTY_ORGANIZATION_ID);
    }

    @Override
    public TicketUpdateInput withPriority(@Nullable TicketUpdateInputPriority priority) {
        return TicketUpdateInputBuilder.With.super.withPriority(priority)
                .unmarkCleared(JSON_PROPERTY_PRIORITY);
    }

    @Override
    public TicketUpdateInput withProblemId(@Nullable Long problemId) {
        return TicketUpdateInputBuilder.With.super.withProblemId(problemId)
                .unmarkCleared(JSON_PROPERTY_PROBLEM_ID);
    }

    @Override
    public TicketUpdateInput withTags(@Nullable List<String> tags) {
        return TicketUpdateInputBuilder.With.super.withTags(tags)
                .unmarkCleared(JSON_PROPERTY_TAGS);
    }

    @Override
    public TicketUpdateInput withTicketFormId(@Nullable Long ticketFormId) {
        return TicketUpdateInputBuilder.With.super.withTicketFormId(ticketFormId)
                .unmarkCleared(JSON_PROPERTY_TICKET_FORM_ID);
    }

    @Override
    public TicketUpdateInput withType(@Nullable TicketUpdateInputType type) {
        return TicketUpdateInputBuilder.With.super.withType(type)
                .unmarkCleared(JSON_PROPERTY_TYPE);
    }

    public TicketUpdateInput clearAssigneeEmail() {
        return withAssigneeEmail(null).markCleared(JSON_PROPERTY_ASSIGNEE_EMAIL);
    }

    public TicketUpdateInput clearAssigneeId() {
        return withAssigneeId(null).markCleared(JSON_PROPERTY_ASSIGNEE_ID);
    }

    public TicketUpdateInput clearCollaboratorIds() {
        return withCollaboratorIds(null).markCleared(JSON_PROPERTY_COLLABORATOR_IDS);
    }

    public TicketUpdateInput clearCustomFields() {
        return withCustomFields(null).markCleared(JSON_PROPERTY_CUSTOM_FIELDS);
    }

    public TicketUpdateInput clearCustomStatusId() {
        return withCustomStatusId((Long) null).markCleared(JSON_PROPERTY_CUSTOM_STATUS_ID);
    }

    public TicketUpdateInput clearDueAt() {
        return withDueAt(null).markCleared(JSON_PROPERTY_DUE_AT);
    }

    public TicketUpdateInput clearEmailCcs() {
        return withEmailCcs(null).markCleared(JSON_PROPERTY_EMAIL_CCS);
    }

    public TicketUpdateInput clearExternalId() {
        return withExternalId(null).markCleared(JSON_PROPERTY_EXTERNAL_ID);
    }

    public TicketUpdateInput clearFollowers() {
        return withFollowers(null).markCleared(JSON_PROPERTY_FOLLOWERS);
    }

    public TicketUpdateInput clearGroupId() {
        return withGroupId(null).markCleared(JSON_PROPERTY_GROUP_ID);
    }

    public TicketUpdateInput clearOrganizationId() {
        return withOrganizationId(null).markCleared(JSON_PROPERTY_ORGANIZATION_ID);
    }

    public TicketUpdateInput clearPriority() {
        return withPriority(null).markCleared(JSON_PROPERTY_PRIORITY);
    }

    public TicketUpdateInput clearProblemId() {
        return withProblemId(null).markCleared(JSON_PROPERTY_PROBLEM_ID);
    }

    public TicketUpdateInput clearTags() {
        return withTags(null).markCleared(JSON_PROPERTY_TAGS);
    }

    public TicketUpdateInput clearTicketFormId() {
        return withTicketFormId(null).markCleared(JSON_PROPERTY_TICKET_FORM_ID);
    }

    public TicketUpdateInput clearType() {
        return withType(null).markCleared(JSON_PROPERTY_TYPE);
    }

    private TicketUpdateInput markCleared(String jsonProperty) {
        return withClearedFields(PartialUpdateSerde.addClearedField(clearedFields, jsonProperty));
    }

    private TicketUpdateInput unmarkCleared(String jsonProperty) {
        return clearedFields.contains(jsonProperty)
                ? withClearedFields(PartialUpdateSerde.removeClearedField(clearedFields, jsonProperty))
                : this;
    }
}
