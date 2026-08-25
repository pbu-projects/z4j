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
 * TargetURL
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetURL.JSON_PROPERTY_ATTRIBUTE,
    TargetURL.JSON_PROPERTY_TARGET_URL,
    TargetURL.JSON_PROPERTY_METHOD,
    TargetURL.JSON_PROPERTY_PASSWORD,
    TargetURL.JSON_PROPERTY_USERNAME,
})
@Serdeable
public class TargetURL {

    public static final String JSON_PROPERTY_ATTRIBUTE = "attribute";
    public static final String JSON_PROPERTY_TARGET_URL = "target_url";
    public static final String JSON_PROPERTY_METHOD = "method";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_USERNAME = "username";

    @NotNull
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE)
    private String attribute;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TARGET_URL)
    private String targetUrl;

    /**
     * <p>\"get\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METHOD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String method;

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

    public TargetURL(String attribute, String targetUrl) {
        this.attribute = attribute;
        this.targetUrl = targetUrl;
    }

}