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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * TicketUpdateInput
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
        TicketInputBase.JSON_PROPERTY_ADDITIONAL_COLLABORATORS,
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
        TicketInputBase.JSON_PROPERTY_REQUESTER_ID,
        TicketInputBase.JSON_PROPERTY_SAFE_UPDATE,
        TicketInputBase.JSON_PROPERTY_SHARING_AGREEMENT_IDS,
        TicketInputBase.JSON_PROPERTY_STATUS,
        TicketInputBase.JSON_PROPERTY_SUBJECT,
        TicketInputBase.JSON_PROPERTY_TAGS,
        TicketInputBase.JSON_PROPERTY_TYPE,
        TicketInputBase.JSON_PROPERTY_UPDATED_STAMP,
})
@Serdeable
public class TicketUpdateInput extends TicketInputBase<TicketUpdateInput> {

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

}
