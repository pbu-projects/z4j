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

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * Serializes {@link TicketUpdateInput}, keeping empty lists and explicitly cleared nulls while omitting unset fields.
 */
@Singleton
public class TicketUpdateInputSerializer implements Serializer<TicketUpdateInput> {

    private static final Argument<TicketUpdateInput> INPUT_ARG = Argument.of(TicketUpdateInput.class);
    private static final Argument<List<Collaborator>> COLLABORATOR_LIST_ARG = Argument.listOf(Collaborator.class);
    private static final Argument<List<String>> STRING_LIST_ARG = Argument.listOf(String.class);
    private static final Argument<List<Long>> LONG_LIST_ARG = Argument.listOf(Long.class);
    private static final Argument<TicketComment> COMMENT_ARG = Argument.of(TicketComment.class);
    private static final Argument<List<TicketCustomField>> CUSTOM_FIELD_LIST_ARG = Argument.listOf(TicketCustomField.class);
    private static final Argument<ZonedDateTime> ZONED_DATE_TIME_ARG = Argument.of(ZonedDateTime.class);
    private static final Argument<List<EmailCC>> EMAIL_CC_LIST_ARG = Argument.listOf(EmailCC.class);
    private static final Argument<List<Follower>> FOLLOWER_LIST_ARG = Argument.listOf(Follower.class);
    private static final Argument<TicketUpdateInputPriority> PRIORITY_ARG = Argument.of(TicketUpdateInputPriority.class);
    private static final Argument<TicketUpdateInputStatus> STATUS_ARG = Argument.of(TicketUpdateInputStatus.class);
    private static final Argument<TicketUpdateInputType> TYPE_ARG = Argument.of(TicketUpdateInputType.class);

    @Override
    public void serialize(
            @NonNull Encoder encoder,
            @NonNull EncoderContext context,
            @NonNull Argument<? extends TicketUpdateInput> type,
            @Nullable TicketUpdateInput value
    ) throws IOException {
        if (value == null) {
            encoder.encodeNull();
            return;
        }
        Set<String> cleared = value.clearedFields();
        try (Encoder obj = encoder.encodeObject(INPUT_ARG)) {
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_ADDITIONAL_COLLABORATORS, COLLABORATOR_LIST_ARG, value.additionalCollaborators(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_ADDITIONAL_TAGS, STRING_LIST_ARG, value.additionalTags(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_ASSIGNEE_EMAIL, Argument.STRING, value.assigneeEmail(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_ASSIGNEE_ID, Argument.LONG, value.assigneeId(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS, LONG_LIST_ARG, value.attributeValueIds(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_COLLABORATOR_IDS, LONG_LIST_ARG, value.collaboratorIds(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_COMMENT, COMMENT_ARG, value.comment(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_CUSTOM_FIELDS, CUSTOM_FIELD_LIST_ARG, value.customFields(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_CUSTOM_STATUS_ID, Argument.LONG, value.customStatusId(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_DUE_AT, ZONED_DATE_TIME_ARG, value.dueAt(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_EMAIL_CCS, EMAIL_CC_LIST_ARG, value.emailCcs(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_EXTERNAL_ID, Argument.STRING, value.externalId(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_FOLLOWERS, FOLLOWER_LIST_ARG, value.followers(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_GROUP_ID, Argument.LONG, value.groupId(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_ORGANIZATION_ID, Argument.LONG, value.organizationId(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_PRIORITY, PRIORITY_ARG, value.priority(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_PROBLEM_ID, Argument.LONG, value.problemId(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_REMOVE_TAGS, STRING_LIST_ARG, value.removeTags(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_REQUESTER_ID, Argument.LONG, value.requesterId(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_SAFE_UPDATE, Argument.BOOLEAN, value.safeUpdate(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_SHARING_AGREEMENT_IDS, LONG_LIST_ARG, value.sharingAgreementIds(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_STATUS, STATUS_ARG, value.status(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_SUBJECT, Argument.STRING, value.subject(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_TAGS, STRING_LIST_ARG, value.tags(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_TYPE, TYPE_ARG, value.type(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketInputBase.JSON_PROPERTY_UPDATED_STAMP, ZONED_DATE_TIME_ARG, value.updatedStamp(), cleared);
            PartialUpdateSerde.writeField(obj, context, TicketUpdateInput.JSON_PROPERTY_TICKET_FORM_ID, Argument.LONG, value.ticketFormId(), cleared);
        }
    }
}
