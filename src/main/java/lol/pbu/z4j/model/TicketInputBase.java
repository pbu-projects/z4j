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
 * Base abstract class for Ticket creation and update payload models to eliminate duplication.
 *
 * @param <T> Concrete builder/model subtype for chainable method returns
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Serdeable
@SuppressWarnings("unchecked")
public abstract class TicketInputBase<T extends TicketInputBase<T>> {

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

    @Nullable
    @JsonProperty(JSON_PROPERTY_ADDITIONAL_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@Valid Collaborator> additionalCollaborators;

    @Nullable
    @Email
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected String assigneeEmail;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected Integer assigneeId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@NotNull Integer> attributeValueIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATOR_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@NotNull Integer> collaboratorIds;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected TicketComment comment;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@Valid TicketCustomField> customFields;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected Integer customStatusId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DUE_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected ZonedDateTime dueAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CCS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@Valid EmailCC> emailCcs;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected String externalId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@Valid Follower> followers;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected Integer groupId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected Integer organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PROBLEM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected Integer problemId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected Integer requesterId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SAFE_UPDATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected Boolean safeUpdate;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARING_AGREEMENT_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@NotNull Integer> sharingAgreementIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected String subject;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected List<@NotNull String> tags;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_STAMP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    protected ZonedDateTime updatedStamp;

    public T addAdditionalCollaboratorsItem(Collaborator additionalCollaboratorsItem) {
        if (additionalCollaborators == null) {
            additionalCollaborators = new ArrayList<>();
        }
        additionalCollaborators.add(additionalCollaboratorsItem);
        return (T) this;
    }

    public T addAttributeValueIdsItem(Integer attributeValueIdsItem) {
        if (attributeValueIds == null) {
            attributeValueIds = new ArrayList<>();
        }
        attributeValueIds.add(attributeValueIdsItem);
        return (T) this;
    }

    public T addCollaboratorIdsItem(Integer collaboratorIdsItem) {
        if (collaboratorIds == null) {
            collaboratorIds = new ArrayList<>();
        }
        collaboratorIds.add(collaboratorIdsItem);
        return (T) this;
    }

    public T addCustomFieldsItem(TicketCustomField customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return (T) this;
    }

    public T addEmailCcsItem(EmailCC emailCcsItem) {
        if (emailCcs == null) {
            emailCcs = new ArrayList<>();
        }
        emailCcs.add(emailCcsItem);
        return (T) this;
    }

    public T addFollowersItem(Follower followersItem) {
        if (followers == null) {
            followers = new ArrayList<>();
        }
        followers.add(followersItem);
        return (T) this;
    }

    public T addSharingAgreementIdsItem(Integer sharingAgreementIdsItem) {
        if (sharingAgreementIds == null) {
            sharingAgreementIds = new ArrayList<>();
        }
        sharingAgreementIds.add(sharingAgreementIdsItem);
        return (T) this;
    }

    public T addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return (T) this;
    }
}
