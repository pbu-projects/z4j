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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>User settings to update</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    UserSettingsUpdateRequestSettings.JSON_PROPERTY_ADMIN_CENTER,
    UserSettingsUpdateRequestSettings.JSON_PROPERTY_LOTUS,
    UserSettingsUpdateRequestSettings.JSON_PROPERTY_SHARED_VIEWS_ORDER,
})
@Serdeable
public class UserSettingsUpdateRequestSettings {

    public static final String JSON_PROPERTY_ADMIN_CENTER = "admin_center";
    public static final String JSON_PROPERTY_LOTUS = "lotus";
    public static final String JSON_PROPERTY_SHARED_VIEWS_ORDER = "shared_views_order";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ADMIN_CENTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserSettingsUpdateRequestSettingsAdminCenter adminCenter;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOTUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserSettingsUpdateRequestSettingsLotus lotus;

    /**
     * <p>Order of shared views (array of view IDs)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_VIEWS_ORDER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> sharedViewsOrder;

    /**
     * Add an item to the sharedViewsOrder property in a chainable fashion.
     *
     * @return The same instance of UserSettingsUpdateRequestSettings for chaining.
     */
    public UserSettingsUpdateRequestSettings addSharedViewsOrderItem(Long sharedViewsOrderItem) {
        if (sharedViewsOrder == null) {
            sharedViewsOrder = new ArrayList<>();
        }
        sharedViewsOrder.add(sharedViewsOrderItem);
        return this;
    }

}