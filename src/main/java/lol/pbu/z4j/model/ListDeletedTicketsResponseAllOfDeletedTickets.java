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
import lol.pbu.z4j.model.ListDeletedTicketsResponseAllOfActor;
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
    private Integer id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_STATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousState;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

}