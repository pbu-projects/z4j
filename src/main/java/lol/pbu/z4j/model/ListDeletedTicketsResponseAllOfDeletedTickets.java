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

/**
 * ListDeletedTicketsResponseAllOfDeletedTickets
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ListDeletedTicketsResponseAllOfDeletedTickets.JSON_PROPERTY_ACTOR,
    ListDeletedTicketsResponseAllOfDeletedTickets.JSON_PROPERTY_DELETED_AT,
    ListDeletedTicketsResponseAllOfDeletedTickets.JSON_PROPERTY_ID,
    ListDeletedTicketsResponseAllOfDeletedTickets.JSON_PROPERTY_PREVIOUS_STATE,
    ListDeletedTicketsResponseAllOfDeletedTickets.JSON_PROPERTY_SUBJECT,
})
@Serdeable
public class ListDeletedTicketsResponseAllOfDeletedTickets {

    public static final String JSON_PROPERTY_ACTOR = "actor";
    public static final String JSON_PROPERTY_DELETED_AT = "deleted_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_PREVIOUS_STATE = "previous_state";
    public static final String JSON_PROPERTY_SUBJECT = "subject";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ACTOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ListDeletedTicketsResponseAllOfActor actor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String deletedAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_STATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousState;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

}