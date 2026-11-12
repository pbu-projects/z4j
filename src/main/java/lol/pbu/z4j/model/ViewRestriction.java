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
import java.util.ArrayList;
import java.util.List;

/**
 * Access restriction settings defining which group or user can view a Zendesk view.
 *
 * @author Jonathan-Zollinger
 * @since 0.4.3
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        ViewRestriction.JSON_PROPERTY_TYPE,
        ViewRestriction.JSON_PROPERTY_ID,
        ViewRestriction.JSON_PROPERTY_IDS
})
public record ViewRestriction(
        @Nullable @JsonProperty(JSON_PROPERTY_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String type,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Long> ids
) implements ViewRestrictionBuilder.With {

    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IDS = "ids";

    public ViewRestriction() {
        this(null, null, null);
    }

    public ViewRestriction(String type, Long id) {
        this(type, id, null);
    }

    public ViewRestriction(String type, List<Long> ids) {
        this(type, null, ids);
    }

    public ViewRestriction addIdsItem(Long idItem) {
        List<Long> newIds = ids == null ? new ArrayList<>() : new ArrayList<>(ids);
        newIds.add(idItem);
        return withIds(newIds);
    }
}
