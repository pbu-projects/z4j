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
 * <p>Branding settings. See <a href=\"#branding\">Branding</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsBrandingObject.JSON_PROPERTY_FAVICON_URL,
    AccountSettingsBrandingObject.JSON_PROPERTY_HEADER_COLOR,
    AccountSettingsBrandingObject.JSON_PROPERTY_HEADER_LOGO_URL,
    AccountSettingsBrandingObject.JSON_PROPERTY_PAGE_BACKGROUND_COLOR,
    AccountSettingsBrandingObject.JSON_PROPERTY_TAB_BACKGROUND_COLOR,
    AccountSettingsBrandingObject.JSON_PROPERTY_TEXT_COLOR,
})
@Serdeable
public class AccountSettingsBrandingObject {

    public static final String JSON_PROPERTY_FAVICON_URL = "favicon_url";
    public static final String JSON_PROPERTY_HEADER_COLOR = "header_color";
    public static final String JSON_PROPERTY_HEADER_LOGO_URL = "header_logo_url";
    public static final String JSON_PROPERTY_PAGE_BACKGROUND_COLOR = "page_background_color";
    public static final String JSON_PROPERTY_TAB_BACKGROUND_COLOR = "tab_background_color";
    public static final String JSON_PROPERTY_TEXT_COLOR = "text_color";

    @Nullable
    @JsonProperty(JSON_PROPERTY_FAVICON_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String faviconUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HEADER_COLOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String headerColor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HEADER_LOGO_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String headerLogoUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PAGE_BACKGROUND_COLOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String pageBackgroundColor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TAB_BACKGROUND_COLOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String tabBackgroundColor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TEXT_COLOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String textColor;

}