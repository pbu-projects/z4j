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

import java.util.HashMap;
import java.util.Map;

/**
 * FeaturesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(FeaturesResponse.JSON_PROPERTY_FEATURES)
@Serdeable
public class FeaturesResponse {

    public static final String JSON_PROPERTY_FEATURES = "features";

    /**
     * <p>A hash of feature names to their enabled status</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FEATURES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Map<String, @Valid FeaturesResponseFeaturesValue> features;

    /**
     * Set the value for the key for the features map property in a chainable fashion.
     *
     * @return The same instance of FeaturesResponse for chaining.
     */
    public FeaturesResponse putFeaturesItem(String key, FeaturesResponseFeaturesValue featuresItem) {
        if (features == null) {
            features = new HashMap<>();
        }
        features.put(key, featuresItem);
        return this;
    }

}