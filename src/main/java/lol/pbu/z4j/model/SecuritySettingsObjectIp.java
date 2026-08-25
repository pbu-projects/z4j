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
 * <p>Describes IP addresses restrictions. See <a href=\"#ip-restrictions\">IP Restrictions</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SecuritySettingsObjectIp.JSON_PROPERTY_ENABLE_AGENT_IP_RESTRICTIONS,
    SecuritySettingsObjectIp.JSON_PROPERTY_IP_RANGES,
    SecuritySettingsObjectIp.JSON_PROPERTY_IP_RESTRICTION_ENABLED,
})
@Serdeable
public class SecuritySettingsObjectIp {

    public static final String JSON_PROPERTY_ENABLE_AGENT_IP_RESTRICTIONS = "enable_agent_ip_restrictions";
    public static final String JSON_PROPERTY_IP_RANGES = "ip_ranges";
    public static final String JSON_PROPERTY_IP_RESTRICTION_ENABLED = "ip_restriction_enabled";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ENABLE_AGENT_IP_RESTRICTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean enableAgentIpRestrictions;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IP_RANGES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ipRanges;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IP_RESTRICTION_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ipRestrictionEnabled;

}