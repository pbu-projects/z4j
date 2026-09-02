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

/**
 * CustomObjectCreateInput
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomObjectCreateInput.JSON_PROPERTY_KEY,
    CustomObjectCreateInput.JSON_PROPERTY_TITLE,
    CustomObjectCreateInput.JSON_PROPERTY_TITLE_PLURALIZED,
})
@Serdeable
public class CustomObjectCreateInput {

    public static final String JSON_PROPERTY_KEY = "key";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TITLE_PLURALIZED = "title_pluralized";

    /**
     * <p>Unique identifier. Writable on create only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String key;

    /**
     * <p>Display name for the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * <p>Pluralized version of the object's title</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE_PLURALIZED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String titlePluralized;

}