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
 * <p>Account statistics settings. See <a href=\"#statistics\">Statistics</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsStatisticsObject.JSON_PROPERTY_FORUM,
    AccountSettingsStatisticsObject.JSON_PROPERTY_RULE_USAGE,
    AccountSettingsStatisticsObject.JSON_PROPERTY_SEARCH,
})
@Serdeable
public class AccountSettingsStatisticsObject {

    public static final String JSON_PROPERTY_FORUM = "forum";
    public static final String JSON_PROPERTY_RULE_USAGE = "rule_usage";
    public static final String JSON_PROPERTY_SEARCH = "search";

    @Nullable
    @JsonProperty(JSON_PROPERTY_FORUM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean forum;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RULE_USAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ruleUsage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SEARCH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean search;

}