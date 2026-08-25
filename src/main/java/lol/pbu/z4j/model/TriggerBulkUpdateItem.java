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
 * TriggerBulkUpdateItem
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TriggerBulkUpdateItem.JSON_PROPERTY_ID,
    TriggerBulkUpdateItem.JSON_PROPERTY_ACTIVE,
    TriggerBulkUpdateItem.JSON_PROPERTY_CATEGORY_ID,
    TriggerBulkUpdateItem.JSON_PROPERTY_POSITION,
})
@Serdeable
public class TriggerBulkUpdateItem {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CATEGORY_ID = "category_id";
    public static final String JSON_PROPERTY_POSITION = "position";

    /**
     * <p>The ID of the ticket trigger to update</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_ID)
    private Integer id;

    /**
     * <p>The active status of the ticket trigger (true or false)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The ID of the new category the ticket trigger is to be moved to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CATEGORY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String categoryId;

    /**
     * <p>The new position of the ticket trigger</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    public TriggerBulkUpdateItem(Integer id) {
        this.id = id;
    }

}