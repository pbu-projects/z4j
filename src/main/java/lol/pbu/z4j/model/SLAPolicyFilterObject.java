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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>An object that describes the conditions that a ticket must match in order for an SLA policy to be applied to that ticket. See <a href=\"#filter\">Filter</a>.</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SLAPolicyFilterObject.JSON_PROPERTY_ALL,
    SLAPolicyFilterObject.JSON_PROPERTY_ANY,
})
@Serdeable
public class SLAPolicyFilterObject {

    public static final String JSON_PROPERTY_ALL = "all";
    public static final String JSON_PROPERTY_ANY = "any";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ALL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SLAPolicyFilterConditionObject> all;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ANY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SLAPolicyFilterConditionObject> any;

    /**
     * Add an item to the all property in a chainable fashion.
     *
     * @return The same instance of SLAPolicyFilterObject for chaining.
     */
    public SLAPolicyFilterObject addAllItem(SLAPolicyFilterConditionObject allItem) {
        if (all == null) {
            all = new ArrayList<>();
        }
        all.add(allItem);
        return this;
    }

    /**
     * Add an item to the any property in a chainable fashion.
     *
     * @return The same instance of SLAPolicyFilterObject for chaining.
     */
    public SLAPolicyFilterObject addAnyItem(SLAPolicyFilterConditionObject anyItem) {
        if (any == null) {
            any = new ArrayList<>();
        }
        any.add(anyItem);
        return this;
    }

}