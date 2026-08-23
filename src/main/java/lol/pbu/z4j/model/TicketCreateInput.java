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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonPropertyOrder({
        TicketInputBase.JSON_PROPERTY_COMMENT,
        TicketInputBase.JSON_PROPERTY_ADDITIONAL_COLLABORATORS,
        TicketInputBase.JSON_PROPERTY_ASSIGNEE_EMAIL,
        TicketInputBase.JSON_PROPERTY_ASSIGNEE_ID,
        TicketInputBase.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS,
        TicketInputBase.JSON_PROPERTY_COLLABORATOR_IDS,
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
        TicketInputBase.JSON_PROPERTY_REQUESTER_ID,
        TicketInputBase.JSON_PROPERTY_SAFE_UPDATE,
        TicketInputBase.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
        TicketInputBase.JSON_PROPERTY_STATUS,
        TicketInputBase.JSON_PROPERTY_SUBJECT,
        TicketInputBase.JSON_PROPERTY_TAGS,
        TicketInputBase.JSON_PROPERTY_TYPE,
        TicketInputBase.JSON_PROPERTY_UPDATED_STAMP,
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
public class TicketCreateInput extends TicketInputBase<TicketCreateInput> {

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

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputPriority priority;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputStatus status;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketUpdateInputType type;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long brandId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Collaborator> collaborators;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CC_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> emailCcIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> followerIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> macroIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawSubject;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recipient;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBMITTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long submitterId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketFormId;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Via via;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long viaFollowupSourceId;

    public TicketCreateInput(TicketComment comment) {
        this.comment = comment;
    }

    public TicketCreateInput addCollaboratorsItem(Collaborator collaboratorsItem) {
        if (collaborators == null) {
            collaborators = new ArrayList<>();
        }
        collaborators.add(collaboratorsItem);
        return this;
    }

    public TicketCreateInput addEmailCcIdsItem(Long emailCcIdsItem) {
        if (emailCcIds == null) {
            emailCcIds = new ArrayList<>();
        }
        emailCcIds.add(emailCcIdsItem);
        return this;
    }

    public TicketCreateInput addFollowerIdsItem(Long followerIdsItem) {
        if (followerIds == null) {
            followerIds = new ArrayList<>();
        }
        followerIds.add(followerIdsItem);
        return this;
    }

    public TicketCreateInput addMacroIdsItem(Long macroIdsItem) {
        if (macroIds == null) {
            macroIds = new ArrayList<>();
        }
        macroIds.add(macroIdsItem);
        return this;
    }
}
