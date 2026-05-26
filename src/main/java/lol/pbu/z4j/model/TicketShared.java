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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Serdeable
public abstract class TicketShared {
    public static final String JSON_PROPERTY_ASSIGNEE_EMAIL = "assignee_email";
    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_COLLABORATORS = "collaborators";
    public static final String JSON_PROPERTY_DUE_AT = "due_at";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_RAW_SUBJECT = "raw_subject";
    public static final String JSON_PROPERTY_RECIPIENT = "recipient";
    public static final String JSON_PROPERTY_SAFE_UPDATE = "safe_update";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_SUBMITTER_ID = "submitter_id";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID = "via_followup_source_id";

    @Nullable
    @Email
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assigneeEmail;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long brandId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Collaborator> collaborators;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DUE_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime dueAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawSubject;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recipient;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SAFE_UPDATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean safeUpdate;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBMITTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long submitterId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketFormId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketType type;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long viaFollowupSourceId;

    /**
     * Add an item to the collaborators property in a chainable fashion.
     *
     * @return The same instance of TicketShared for chaining.
     */
    public TicketShared addCollaboratorsItem(Collaborator collaboratorsItem) {
        if (collaborators == null) {
            collaborators = new ArrayList<>();
        }
        collaborators.add(collaboratorsItem);
        return this;
    }
}
