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
 * <p>Google Apps configuration. See <a href=\"#g-suite\">G Suite</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsGoogleAppsObject.JSON_PROPERTY_HAS_GOOGLE_APPS,
    AccountSettingsGoogleAppsObject.JSON_PROPERTY_HAS_GOOGLE_APPS_ADMIN,
})
@Serdeable
public class AccountSettingsGoogleAppsObject {

    public static final String JSON_PROPERTY_HAS_GOOGLE_APPS = "has_google_apps";
    public static final String JSON_PROPERTY_HAS_GOOGLE_APPS_ADMIN = "has_google_apps_admin";

    @Nullable
    @JsonProperty(JSON_PROPERTY_HAS_GOOGLE_APPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean hasGoogleApps;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HAS_GOOGLE_APPS_ADMIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean hasGoogleAppsAdmin;

}