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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Rules settings for triggers, macros, views, and automations. See <a href=\"#rules\">Rules</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsRuleObject.JSON_PROPERTY_MACRO_MOST_USED,
    AccountSettingsRuleObject.JSON_PROPERTY_MACRO_ORDER,
    AccountSettingsRuleObject.JSON_PROPERTY_SKILL_BASED_FILTERED_VIEWS,
    AccountSettingsRuleObject.JSON_PROPERTY_USING_SKILL_BASED_ROUTING,
})
@Serdeable
public class AccountSettingsRuleObject {

    public static final String JSON_PROPERTY_MACRO_MOST_USED = "macro_most_used";
    public static final String JSON_PROPERTY_MACRO_ORDER = "macro_order";
    public static final String JSON_PROPERTY_SKILL_BASED_FILTERED_VIEWS = "skill_based_filtered_views";
    public static final String JSON_PROPERTY_USING_SKILL_BASED_ROUTING = "using_skill_based_routing";

    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_MOST_USED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean macroMostUsed;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_ORDER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String macroOrder;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SKILL_BASED_FILTERED_VIEWS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> skillBasedFilteredViews;

    @Nullable
    @JsonProperty(JSON_PROPERTY_USING_SKILL_BASED_ROUTING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean usingSkillBasedRouting;

    /**
     * Add an item to the skillBasedFilteredViews property in a chainable fashion.
     *
     * @return The same instance of AccountSettingsRuleObject for chaining.
     */
    public AccountSettingsRuleObject addSkillBasedFilteredViewsItem(Map<String, Object> skillBasedFilteredViewsItem) {
        if (skillBasedFilteredViews == null) {
            skillBasedFilteredViews = new ArrayList<>();
        }
        skillBasedFilteredViews.add(skillBasedFilteredViewsItem);
        return this;
    }

}