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
import lol.pbu.z4j.Generated;

/**
 * RedirectRule object representing URL redirect rules in Zendesk Guide.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        RedirectRule.JSON_PROPERTY_ID,
        RedirectRule.JSON_PROPERTY_BRAND_ID,
        RedirectRule.JSON_PROPERTY_REDIRECT_FROM,
        RedirectRule.JSON_PROPERTY_REDIRECT_TO,
        RedirectRule.JSON_PROPERTY_REDIRECT_STATUS,
        RedirectRule.JSON_PROPERTY_CREATED_AT,
        RedirectRule.JSON_PROPERTY_UPDATED_AT
})
@Serdeable
@Generated
public class RedirectRule {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_REDIRECT_FROM = "redirect_from";
    public static final String JSON_PROPERTY_REDIRECT_TO = "redirect_to";
    public static final String JSON_PROPERTY_REDIRECT_STATUS = "redirect_status";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String brandId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REDIRECT_FROM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String redirectFrom;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REDIRECT_TO)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String redirectTo;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REDIRECT_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer redirectStatus;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    public RedirectRule(String redirectFrom, String redirectTo, Integer redirectStatus) {
        this.redirectFrom = redirectFrom;
        this.redirectTo = redirectTo;
        this.redirectStatus = redirectStatus;
    }
}
