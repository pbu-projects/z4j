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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

import java.util.Map;

/**
 * DeletedTicket
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        DeletedTicket.JSON_PROPERTY_ID,
        DeletedTicket.JSON_PROPERTY_SUBJECT,
        DeletedTicket.JSON_PROPERTY_DESCRIPTION,
        DeletedTicket.JSON_PROPERTY_ACTOR,
        DeletedTicket.JSON_PROPERTY_DELETED_AT,
        DeletedTicket.JSON_PROPERTY_PREVIOUS_STATE
})
@Serdeable
@Generated
public class DeletedTicket {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ACTOR = "actor";
    public static final String JSON_PROPERTY_DELETED_AT = "deleted_at";
    public static final String JSON_PROPERTY_PREVIOUS_STATE = "previous_state";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> actor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String deletedAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_STATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousState;
}
