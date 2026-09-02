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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * ApprovalRequestDecision
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ApprovalRequestDecision.JSON_PROPERTY_DECIDED_AT,
    ApprovalRequestDecision.JSON_PROPERTY_DECIDED_BY_USER,
    ApprovalRequestDecision.JSON_PROPERTY_DECISION_NOTES,
    ApprovalRequestDecision.JSON_PROPERTY_ID,
    ApprovalRequestDecision.JSON_PROPERTY_STATUS,
})
@Serdeable
public class ApprovalRequestDecision {

    public static final String JSON_PROPERTY_DECIDED_AT = "decided_at";
    public static final String JSON_PROPERTY_DECIDED_BY_USER = "decided_by_user";
    public static final String JSON_PROPERTY_DECISION_NOTES = "decision_notes";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_STATUS = "status";

    /**
     * <p>The time the decision was made</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DECIDED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime decidedAt;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_DECIDED_BY_USER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ApprovalRequestUser decidedByUser;

    /**
     * <p>Notes for the decision</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DECISION_NOTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String decisionNotes;

    /**
     * <p>Unique identifier for the decision</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Status of the decision</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

}