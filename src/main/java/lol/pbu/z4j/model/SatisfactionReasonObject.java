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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * SatisfactionReasonObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    SatisfactionReasonObject.JSON_PROPERTY_VALUE,
    SatisfactionReasonObject.JSON_PROPERTY_CREATED_AT,
    SatisfactionReasonObject.JSON_PROPERTY_DELETED_AT,
    SatisfactionReasonObject.JSON_PROPERTY_ID,
    SatisfactionReasonObject.JSON_PROPERTY_RAW_VALUE,
    SatisfactionReasonObject.JSON_PROPERTY_REASON_CODE,
    SatisfactionReasonObject.JSON_PROPERTY_UPDATED_AT,
    SatisfactionReasonObject.JSON_PROPERTY_URL,
})
@Serdeable
public class SatisfactionReasonObject {

    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DELETED_AT = "deleted_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_RAW_VALUE = "raw_value";
    public static final String JSON_PROPERTY_REASON_CODE = "reason_code";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>Translated value of the reason in the account locale</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_VALUE)
    private String value;

    /**
     * <p>The time the reason was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The time the reason was deleted</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime deletedAt;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>The dynamic content placeholder, if present, or the current \"value\", if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawValue;

    /**
     * <p>An account-level code for referencing the reason. Custom reasons are assigned an auto-incrementing integer (non-system reason codes begin at 1000). See <a href=\"#reason-codes\">Reason codes</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REASON_CODE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long reasonCode;

    /**
     * <p>The time the reason was updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>API URL for the resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public SatisfactionReasonObject(String value) {
        this.value = value;
    }

}