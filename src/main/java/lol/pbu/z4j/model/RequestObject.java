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
import java.util.List;
import lol.pbu.z4j.model.MacroApplyTicketResponseResultTicketFields;
import lol.pbu.z4j.model.TicketAuditViaObject;
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
 * RequestObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    RequestObject.JSON_PROPERTY_SUBJECT,
    RequestObject.JSON_PROPERTY_ASSIGNEE_ID,
    RequestObject.JSON_PROPERTY_CAN_BE_SOLVED_BY_ME,
    RequestObject.JSON_PROPERTY_COLLABORATOR_IDS,
    RequestObject.JSON_PROPERTY_CREATED_AT,
    RequestObject.JSON_PROPERTY_CUSTOM_FIELDS,
    RequestObject.JSON_PROPERTY_CUSTOM_STATUS_ID,
    RequestObject.JSON_PROPERTY_DESCRIPTION,
    RequestObject.JSON_PROPERTY_DUE_AT,
    RequestObject.JSON_PROPERTY_EMAIL_CC_IDS,
    RequestObject.JSON_PROPERTY_FOLLOWUP_SOURCE_ID,
    RequestObject.JSON_PROPERTY_GROUP_ID,
    RequestObject.JSON_PROPERTY_ID,
    RequestObject.JSON_PROPERTY_IS_PUBLIC,
    RequestObject.JSON_PROPERTY_ORGANIZATION_ID,
    RequestObject.JSON_PROPERTY_PRIORITY,
    RequestObject.JSON_PROPERTY_RECIPIENT,
    RequestObject.JSON_PROPERTY_REQUESTER_ID,
    RequestObject.JSON_PROPERTY_SOLVED,
    RequestObject.JSON_PROPERTY_STATUS,
    RequestObject.JSON_PROPERTY_TICKET_FORM_ID,
    RequestObject.JSON_PROPERTY_TYPE,
    RequestObject.JSON_PROPERTY_UPDATED_AT,
    RequestObject.JSON_PROPERTY_URL,
    RequestObject.JSON_PROPERTY_VIA,
})
@Serdeable
public class RequestObject {

    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_CAN_BE_SOLVED_BY_ME = "can_be_solved_by_me";
    public static final String JSON_PROPERTY_COLLABORATOR_IDS = "collaborator_ids";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CUSTOM_FIELDS = "custom_fields";
    public static final String JSON_PROPERTY_CUSTOM_STATUS_ID = "custom_status_id";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_DUE_AT = "due_at";
    public static final String JSON_PROPERTY_EMAIL_CC_IDS = "email_cc_ids";
    public static final String JSON_PROPERTY_FOLLOWUP_SOURCE_ID = "followup_source_id";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IS_PUBLIC = "is_public";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_RECIPIENT = "recipient";
    public static final String JSON_PROPERTY_REQUESTER_ID = "requester_id";
    public static final String JSON_PROPERTY_SOLVED = "solved";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VIA = "via";

    /**
     * <p>The value of the subject field for this request if the subject field is visible to end users; a truncated version of the description otherwise</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    private String subject;

    /**
     * <p>The id of the assignee if the field is visible to end users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long assigneeId;

    /**
     * <p>If true, an end user can mark the request as solved. See <a href=\"/api-reference/ticketing/tickets/ticket-requests/#update-request\">Update Request</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CAN_BE_SOLVED_BY_ME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean canBeSolvedByMe;

    /**
     * <p>The ids of users currently CC'ed on the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATOR_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> collaboratorIds;

    /**
     * <p>When this record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Custom fields for the request. See <a href=\"/api-reference/ticketing/tickets/tickets/#setting-custom-field-values\">Setting custom field values</a> in the Tickets doc</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid MacroApplyTicketResponseResultTicketFields> customFields;

    /**
     * <p>The custom ticket status id of the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long customStatusId;

    /**
     * <p>Read-only first comment on the request. When <a href=\"#create-request\">creating a request</a>, use <code>comment</code> to set the description</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>When the task is due (only applies if the request is of type \"task\")</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DUE_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime dueAt;

    /**
     * <p>The ids of users who are currently email CCs on the ticket. See <a href=\"https://support.zendesk.com/hc/en-us/articles/360020585233\">CCs and followers resources</a> in the Support Help Center</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CC_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> emailCcIds;

    /**
     * <p>The id of the original ticket if this request is a follow-up ticket. See <a href=\"#create-request\">Create Request</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWUP_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long followupSourceId;

    /**
     * <p>The id of the assigned group if the field is visible to end users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long groupId;

    /**
     * <p>Automatically assigned when creating requests</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Is true if any comments are public, false otherwise</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isPublic;

    /**
     * <p>The organization of the requester</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationId;

    /**
     * <p>The priority of the request, \"low\", \"normal\", \"high\", \"urgent\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String priority;

    /**
     * <p>The original recipient e-mail address of the request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recipient;

    /**
     * <p>The id of the requester</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long requesterId;

    /**
     * <p>Whether or not request is solved (an end user can set this if \"can_be_solved_by_me\", above, is true for that user)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOLVED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean solved;

    /**
     * <p>The state of the request, \"new\", \"open\", \"pending\", \"hold\", \"solved\", \"closed\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    /**
     * <p>The numeric id of the ticket form associated with this request if the form is visible to end users - only applicable for enterprise accounts</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketFormId;

    /**
     * <p>The type of the request, \"question\", \"incident\", \"problem\", \"task\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    /**
     * <p>When this record last got updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketAuditViaObject via;

    public RequestObject(String subject) {
        this.subject = subject;
    }

    /**
     * Add an item to the customFields property in a chainable fashion.
     *
     * @return The same instance of RequestObject for chaining.
     */
    public RequestObject addCustomFieldsItem(MacroApplyTicketResponseResultTicketFields customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return this;
    }

}