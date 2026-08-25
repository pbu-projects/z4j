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
 * TicketFormStatusObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketFormStatusObject.JSON_PROPERTY_CUSTOM_STATUS_ID,
    TicketFormStatusObject.JSON_PROPERTY_ID,
    TicketFormStatusObject.JSON_PROPERTY_TICKET_FORM_ID,
})
@Serdeable
public class TicketFormStatusObject {

    public static final String JSON_PROPERTY_CUSTOM_STATUS_ID = "custom_status_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";

    /**
     * <p>The id of the associated custom status</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer customStatusId;

    /**
     * <p>Automatically assigned when creating a ticket form</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>The id of the associated ticket form</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer ticketFormId;

}