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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Common interface for ticket create and update request payloads.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
public interface TicketInputBase {

    String JSON_PROPERTY_COMMENT = "comment";
    String JSON_PROPERTY_ADDITIONAL_COLLABORATORS = "additional_collaborators";
    String JSON_PROPERTY_ASSIGNEE_EMAIL = "assignee_email";
    String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    String JSON_PROPERTY_ATTRIBUTE_VALUE_IDS = "attribute_value_ids";
    String JSON_PROPERTY_COLLABORATOR_IDS = "collaborator_ids";
    String JSON_PROPERTY_CUSTOM_FIELDS = "custom_fields";
    String JSON_PROPERTY_CUSTOM_STATUS_ID = "custom_status_id";
    String JSON_PROPERTY_DUE_AT = "due_at";
    String JSON_PROPERTY_EMAIL_CCS = "email_ccs";
    String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    String JSON_PROPERTY_FOLLOWERS = "followers";
    String JSON_PROPERTY_GROUP_ID = "group_id";
    String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    String JSON_PROPERTY_PRIORITY = "priority";
    String JSON_PROPERTY_PROBLEM_ID = "problem_id";
    String JSON_PROPERTY_REQUESTER_ID = "requester_id";
    String JSON_PROPERTY_SAFE_UPDATE = "safe_update";
    String JSON_PROPERTY_SHARING_AGREEMENT_IDS = "sharing_agreement_ids";
    String JSON_PROPERTY_STATUS = "status";
    String JSON_PROPERTY_SUBJECT = "subject";
    String JSON_PROPERTY_TAGS = "tags";
    String JSON_PROPERTY_ADDITIONAL_TAGS = "additional_tags";
    String JSON_PROPERTY_REMOVE_TAGS = "remove_tags";
    String JSON_PROPERTY_TYPE = "type";
    String JSON_PROPERTY_UPDATED_STAMP = "updated_stamp";

    @Nullable
    List<Collaborator> additionalCollaborators();

    @Nullable
    List<String> additionalTags();

    @Nullable
    String assigneeEmail();

    @Nullable
    Long assigneeId();

    @Nullable
    List<Long> attributeValueIds();

    @Nullable
    List<Long> collaboratorIds();

    @Nullable
    TicketComment comment();

    @Nullable
    List<TicketCustomField> customFields();

    @Nullable
    Long customStatusId();

    @Nullable
    ZonedDateTime dueAt();

    @Nullable
    List<EmailCC> emailCcs();

    @Nullable
    String externalId();

    @Nullable
    List<Follower> followers();

    @Nullable
    Long groupId();

    @Nullable
    Long organizationId();

    @Nullable
    TicketUpdateInputPriority priority();

    @Nullable
    Long problemId();

    @Nullable
    List<String> removeTags();

    @Nullable
    Long requesterId();

    @Nullable
    Boolean safeUpdate();

    @Nullable
    List<Long> sharingAgreementIds();

    @Nullable
    TicketUpdateInputStatus status();

    @Nullable
    String subject();

    @Nullable
    List<String> tags();

    @Nullable
    TicketUpdateInputType type();

    @Nullable
    ZonedDateTime updatedStamp();
}
