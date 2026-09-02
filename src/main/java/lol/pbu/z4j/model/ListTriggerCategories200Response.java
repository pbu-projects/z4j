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
 * ListTriggerCategories200Response
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ListTriggerCategories200Response.JSON_PROPERTY_TRIGGER_CATEGORIES,
    ListTriggerCategories200Response.JSON_PROPERTY_LINKS,
    ListTriggerCategories200Response.JSON_PROPERTY_META,
})
@Serdeable
public class ListTriggerCategories200Response {

    public static final String JSON_PROPERTY_TRIGGER_CATEGORIES = "trigger_categories";
    public static final String JSON_PROPERTY_LINKS = "links";
    public static final String JSON_PROPERTY_META = "meta";

    @Nullable
    @JsonProperty(JSON_PROPERTY_TRIGGER_CATEGORIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerCategory> triggerCategories;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_LINKS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ConversationLogResponseLinks links;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_META)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ConversationLogResponseMeta meta;

    /**
     * Add an item to the triggerCategories property in a chainable fashion.
     *
     * @return The same instance of ListTriggerCategories200Response for chaining.
     */
    public ListTriggerCategories200Response addTriggerCategoriesItem(TriggerCategory triggerCategoriesItem) {
        if (triggerCategories == null) {
            triggerCategories = new ArrayList<>();
        }
        triggerCategories.add(triggerCategoriesItem);
        return this;
    }

}