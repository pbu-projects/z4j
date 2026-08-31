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
 * <p>Zendesk Chat settings. See <a href=\"#chat\">Chat</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsChatObject.JSON_PROPERTY_AVAILABLE,
    AccountSettingsChatObject.JSON_PROPERTY_ENABLED,
    AccountSettingsChatObject.JSON_PROPERTY_INTEGRATED,
    AccountSettingsChatObject.JSON_PROPERTY_MAXIMUM_REQUEST_COUNT,
    AccountSettingsChatObject.JSON_PROPERTY_WELCOME_MESSAGE,
})
@Serdeable
public class AccountSettingsChatObject {

    public static final String JSON_PROPERTY_AVAILABLE = "available";
    public static final String JSON_PROPERTY_ENABLED = "enabled";
    public static final String JSON_PROPERTY_INTEGRATED = "integrated";
    public static final String JSON_PROPERTY_MAXIMUM_REQUEST_COUNT = "maximum_request_count";
    public static final String JSON_PROPERTY_WELCOME_MESSAGE = "welcome_message";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AVAILABLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean available;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean enabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_INTEGRATED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean integrated;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAXIMUM_REQUEST_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long maximumRequestCount;

    @Nullable
    @JsonProperty(JSON_PROPERTY_WELCOME_MESSAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String welcomeMessage;

}