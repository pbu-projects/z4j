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
@EqualsAndHashCode(callSuper = false)
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
public class TicketCreateInput extends TicketUpdateInput {

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
     * The ids of agents or end users currently CC'ed on the ticket. See <a href="https://support.zendesk.com/hc/en-us/articles/360020585233">CCs and followers resources</a> in the Support Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CC_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> emailCcIds;

    /**
     * The ids of agents currently following the ticket. See <a href="https://support.zendesk.com/hc/en-us/articles/360020585233">CCs and followers resources</a>
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
        setComment(comment);
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
