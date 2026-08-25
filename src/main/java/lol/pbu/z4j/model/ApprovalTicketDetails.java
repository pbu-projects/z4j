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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.ApprovalRequestUser;
import lol.pbu.z4j.model.ApprovalTicketDetailsCustomFieldsInner;
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
 * <p>Details of the ticket associated with this approval request</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ApprovalTicketDetails.JSON_PROPERTY_CUSTOM_FIELDS,
    ApprovalTicketDetails.JSON_PROPERTY_ID,
    ApprovalTicketDetails.JSON_PROPERTY_PRIORITY,
    ApprovalTicketDetails.JSON_PROPERTY_REQUESTER,
    ApprovalTicketDetails.JSON_PROPERTY_STATUS,
})
@Serdeable
public class ApprovalTicketDetails {

    public static final String JSON_PROPERTY_CUSTOM_FIELDS = "custom_fields";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_REQUESTER = "requester";
    public static final String JSON_PROPERTY_STATUS = "status";

    /**
     * <p>Custom fields for the ticket. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-updating-tickets#setting-custom-field-values\">Setting custom field values</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ApprovalTicketDetailsCustomFieldsInner> customFields;

    /**
     * <p>Unique identifier for the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Priority of the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String priority;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_REQUESTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ApprovalRequestUser requester;

    /**
     * <p>Status of the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    /**
     * Add an item to the customFields property in a chainable fashion.
     *
     * @return The same instance of ApprovalTicketDetails for chaining.
     */
    public ApprovalTicketDetails addCustomFieldsItem(ApprovalTicketDetailsCustomFieldsInner customFieldsItem) {
        if (customFields == null) {
            customFields = new ArrayList<>();
        }
        customFields.add(customFieldsItem);
        return this;
    }

}