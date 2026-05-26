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

import java.util.ArrayList;
import java.util.List;

/**
 * TicketCreateInput
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
public class TicketCreateInput extends TicketShared {

    public static final String JSON_PROPERTY_COMMENT = "comment";
    public static final String JSON_PROPERTY_ADDITIONAL_COLLABORATORS = "additional_collaborators";
    public static final String JSON_PROPERTY_ATTRIBUTE_VALUE_IDS = "attribute_value_ids";
    public static final String JSON_PROPERTY_COLLABORATOR_IDS = "collaborator_ids";
    public static final String JSON_PROPERTY_CUSTOM_FIELDS = "custom_fields";
    public static final String JSON_PROPERTY_EMAIL_CCS = "email_ccs";
    public static final String JSON_PROPERTY_FOLLOWERS = "followers";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_SHARING_AGREEMENT_IDS = "sharing_agreement_ids";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_VIA = "via";

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_COMMENT)
    private TicketComment comment;

    /**
     * An array of numeric IDs, emails, or objects containing name and email properties. See <a href="https://developer.zendesk.com/api-reference/ticketing/tickets/tickets/#setting-collaborators">Setting Collaborators</a>. An email notification is sent to them when the ticket is updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ADDITIONAL_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Collaborator> additionalCollaborators;

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
     * Custom fields for the ticket. See <a href="https://developer.zendesk.com/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-custom-field-values">Setting custom field values</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomField> customFields;

    /**
     * An array of objects that represent agent or end users email CCs to add or delete from the ticket. See <a href="https://developer.zendesk.com/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-email-ccs">Setting email CCs</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CCS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid EmailCC> emailCcs;

    /**
     * An array of objects that represent agent followers to add or delete from the ticket. See <a href="https://developer.zendesk.com/documentation/ticketing/managing-tickets/creating-and-updating-tickets/#setting-followers">Setting followers</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Follower> followers;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputPriority priority;

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

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Via via;


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
     * Add an item to the emailCcIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addEmailCcIdsItem(Long emailCcIdsItem) {
        if (super.getEmailCcIds() == null) {
            super.setEmailCcIds(new ArrayList<>());
        }
        super.getEmailCcIds().add(emailCcIdsItem);
        return this;
    }

    /**
     * Add an item to the followerIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addFollowerIdsItem(Long followerIdsItem) {
        if (super.getFollowerIds() == null) {
            super.setFollowerIds(new ArrayList<>());
        }
        super.getFollowerIds().add(followerIdsItem);
        return this;
    }

    /**
     * Add an item to the macroIds property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addMacroIdsItem(Long macroIdsItem) {
        if (super.getMacroIds() == null) {
            super.setMacroIds(new ArrayList<>());
        }
        super.getMacroIds().add(macroIdsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of TicketCreateInput for chaining.
     */
    public TicketCreateInput addTagsItem(String tagsItem) {
        if (super.getTags() == null) {
            super.setTags(new ArrayList<>());
        }
        super.getTags().add(tagsItem);
        return this;
    }

}
