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
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Saved ticket view configuration in Zendesk Support.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        View.JSON_PROPERTY_ID,
        View.JSON_PROPERTY_TITLE,
        View.JSON_PROPERTY_ACTIVE,
        View.JSON_PROPERTY_UPDATED_AT,
        View.JSON_PROPERTY_CREATED_AT,
        View.JSON_PROPERTY_DEFAULT,
        View.JSON_PROPERTY_POSITION,
        View.JSON_PROPERTY_DESCRIPTION,
        View.JSON_PROPERTY_EXECUTION,
        View.JSON_PROPERTY_CONDITIONS,
        View.JSON_PROPERTY_RESTRICTION,
        View.JSON_PROPERTY_RAW_TITLE,
        View.JSON_PROPERTY_OUTPUT,
        View.JSON_PROPERTY_ALL,
        View.JSON_PROPERTY_ANY
})
public record View(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String title,
        @Nullable @JsonProperty(JSON_PROPERTY_ACTIVE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean active,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) OffsetDateTime updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) OffsetDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_DEFAULT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean isDefault,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long position,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_EXECUTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ViewExecution execution,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_CONDITIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ViewConditions conditions,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_RESTRICTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ViewRestriction restriction,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawTitle,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_OUTPUT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ViewOutput output,
        @Nullable @JsonProperty(JSON_PROPERTY_ALL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid ViewCondition> all,
        @Nullable @JsonProperty(JSON_PROPERTY_ANY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid ViewCondition> any
) implements ViewBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_EXECUTION = "execution";
    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_RESTRICTION = "restriction";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_OUTPUT = "output";
    public static final String JSON_PROPERTY_ALL = "all";
    public static final String JSON_PROPERTY_ANY = "any";

    public View() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public View(String title) {
        this(null, title, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public View(String title, ViewConditions conditions) {
        this(null, title, null, null, null, null, null, null, null, conditions, null, null, null, null, null);
    }

    public View(
            Long id,
            String title,
            Boolean active,
            OffsetDateTime updatedAt,
            OffsetDateTime createdAt,
            Boolean isDefault,
            Long position,
            String description,
            ViewExecution execution,
            ViewConditions conditions,
            ViewRestriction restriction,
            String rawTitle
    ) {
        this(id, title, active, updatedAt, createdAt, isDefault, position, description, execution, conditions, restriction, rawTitle, null, null, null);
    }

    public View withDefault(Boolean isDefault) {
        return withIsDefault(isDefault);
    }

    public View addAllItem(ViewCondition allItem) {
        List<ViewCondition> newAll = all == null ? new ArrayList<>() : new ArrayList<>(all);
        newAll.add(allItem);
        return withAll(newAll);
    }

    public View addAnyItem(ViewCondition anyItem) {
        List<ViewCondition> newAny = any == null ? new ArrayList<>() : new ArrayList<>(any);
        newAny.add(anyItem);
        return withAny(newAny);
    }
}
