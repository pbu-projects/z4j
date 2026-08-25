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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.ConditionsObject;
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
 * WorkspaceInput
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    WorkspaceInput.JSON_PROPERTY_CONDITIONS,
    WorkspaceInput.JSON_PROPERTY_DESCRIPTION,
    WorkspaceInput.JSON_PROPERTY_MACROS,
    WorkspaceInput.JSON_PROPERTY_TICKET_FORM_ID,
    WorkspaceInput.JSON_PROPERTY_TITLE,
})
@Serdeable
public class WorkspaceInput {

    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_MACROS = "macros";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";
    public static final String JSON_PROPERTY_TITLE = "title";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CONDITIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ConditionsObject conditions;

    /**
     * <p>User-defined description of this workspace's purpose</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MACROS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull BigDecimal> macros;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private BigDecimal ticketFormId;

    /**
     * <p>The title of the workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * Add an item to the macros property in a chainable fashion.
     *
     * @return The same instance of WorkspaceInput for chaining.
     */
    public WorkspaceInput addMacrosItem(BigDecimal macrosItem) {
        if (macros == null) {
            macros = new ArrayList<>();
        }
        macros.add(macrosItem);
        return this;
    }

}