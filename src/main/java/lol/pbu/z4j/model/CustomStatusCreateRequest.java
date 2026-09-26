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
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

/**
 * Request payload for creating or updating a Zendesk custom ticket status.
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
public class CustomStatusCreateRequest {

    public static final String JSON_PROPERTY_CUSTOM_STATUS = "custom_status";

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketFieldCustomStatusObject customStatus;

    /**
     * Record-style accessor for modern syntax compatibility.
     *
     * @return the custom status payload
     */
    public TicketFieldCustomStatusObject customStatus() {
        return customStatus;
    }
}
