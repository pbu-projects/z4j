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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * TargetClickatell
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetClickatell.JSON_PROPERTY_API_ID,
    TargetClickatell.JSON_PROPERTY_PASSWORD,
    TargetClickatell.JSON_PROPERTY_TO,
    TargetClickatell.JSON_PROPERTY_USERNAME,
    TargetClickatell.JSON_PROPERTY_ATTRIBUTE,
    TargetClickatell.JSON_PROPERTY_FROM,
    TargetClickatell.JSON_PROPERTY_METHOD,
    TargetClickatell.JSON_PROPERTY_TARGET_URL,
    TargetClickatell.JSON_PROPERTY_US_SMALL_BUSINESS_ACCOUNT,
})
@Serdeable
public class TargetClickatell {

    public static final String JSON_PROPERTY_API_ID = "api_id";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_TO = "to";
    public static final String JSON_PROPERTY_USERNAME = "username";
    public static final String JSON_PROPERTY_ATTRIBUTE = "attribute";
    public static final String JSON_PROPERTY_FROM = "from";
    public static final String JSON_PROPERTY_METHOD = "method";
    public static final String JSON_PROPERTY_TARGET_URL = "target_url";
    public static final String JSON_PROPERTY_US_SMALL_BUSINESS_ACCOUNT = "us_small_business_account";

    @NotNull
    @JsonProperty(JSON_PROPERTY_API_ID)
    private String apiId;

    /**
     * <p>only writable</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    private String password;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TO)
    private String to;

    @NotNull
    @JsonProperty(JSON_PROPERTY_USERNAME)
    private String username;

    /**
     * <p>Read-only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String attribute;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FROM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String from;

    /**
     * <p>Read-only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METHOD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String method;

    /**
     * <p>Read-only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String targetUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_US_SMALL_BUSINESS_ACCOUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String usSmallBusinessAccount;

    public TargetClickatell(String apiId, String password, String to, String username) {
        this.apiId = apiId;
        this.password = password;
        this.to = to;
        this.username = username;
    }

}