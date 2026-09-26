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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

/**
 * Association mapping a Zendesk custom ticket status to a specific ticket form.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.6
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Serdeable
@Generated
public class TicketFormStatus {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_CUSTOM_STATUS_ID = "custom_status_id";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long customStatusId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketFormId;

    public @Nullable String id() {
        return id;
    }

    public @Nullable Long customStatusId() {
        return customStatusId;
    }

    public @Nullable Long ticketFormId() {
        return ticketFormId;
    }
}
