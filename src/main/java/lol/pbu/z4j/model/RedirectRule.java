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

/**
 * URL redirect rule in Zendesk Guide.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        RedirectRule.JSON_PROPERTY_ID,
        RedirectRule.JSON_PROPERTY_BRAND_ID,
        RedirectRule.JSON_PROPERTY_REDIRECT_FROM,
        RedirectRule.JSON_PROPERTY_REDIRECT_TO,
        RedirectRule.JSON_PROPERTY_REDIRECT_STATUS,
        RedirectRule.JSON_PROPERTY_CREATED_AT,
        RedirectRule.JSON_PROPERTY_UPDATED_AT
})
public record RedirectRule(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String id,
        @Nullable @JsonProperty(JSON_PROPERTY_BRAND_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String brandId,
        @Nullable @JsonProperty(JSON_PROPERTY_REDIRECT_FROM) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String redirectFrom,
        @Nullable @JsonProperty(JSON_PROPERTY_REDIRECT_TO) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String redirectTo,
        @Nullable @JsonProperty(JSON_PROPERTY_REDIRECT_STATUS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer redirectStatus,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt
) implements RedirectRuleBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_REDIRECT_FROM = "redirect_from";
    public static final String JSON_PROPERTY_REDIRECT_TO = "redirect_to";
    public static final String JSON_PROPERTY_REDIRECT_STATUS = "redirect_status";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public RedirectRule() {
        this(null, null, null, null, null, null, null);
    }

    public RedirectRule(String redirectFrom, String redirectTo, Integer redirectStatus) {
        this(null, null, redirectFrom, redirectTo, redirectStatus, null, null);
    }
}
