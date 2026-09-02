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

/**
 * TargetHTTP
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetHTTP.JSON_PROPERTY_CONTENT_TYPE,
    TargetHTTP.JSON_PROPERTY_METHOD,
    TargetHTTP.JSON_PROPERTY_TARGET_URL,
    TargetHTTP.JSON_PROPERTY_PASSWORD,
    TargetHTTP.JSON_PROPERTY_USERNAME,
})
@Serdeable
public class TargetHTTP {

    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_METHOD = "method";
    public static final String JSON_PROPERTY_TARGET_URL = "target_url";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_USERNAME = "username";

    /**
     * <p>\"application/json\", \"application/xml\", or \"application/x-www-form-urlencoded\"</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    private String contentType;

    /**
     * <p>\"get\", \"patch\", \"put\", \"post\", or \"delete\"</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_METHOD)
    private String method;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TARGET_URL)
    private String targetUrl;

    /**
     * <p>only writable</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String password;

    @Nullable
    @JsonProperty(JSON_PROPERTY_USERNAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String username;

    public TargetHTTP(String contentType, String method, String targetUrl) {
        this.contentType = contentType;
        this.method = method;
        this.targetUrl = targetUrl;
    }

}