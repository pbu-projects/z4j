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
 * <p>The type of this identity</p>
 */
@RequiredArgsConstructor
@Getter(onMethod = @__(@JsonValue))
@Serdeable
public enum UserIdentityObjectType {

    @JsonProperty("email")
    EMAIL("email"),

    @JsonProperty("twitter")
    TWITTER("twitter"),

    @JsonProperty("facebook")
    FACEBOOK("facebook"),

    @JsonProperty("google")
    GOOGLE("google"),

    @JsonProperty("phone_number")
    PHONE_NUMBER("phone_number"),

    @JsonProperty("agent_forwarding")
    AGENT_FORWARDING("agent_forwarding"),

    @JsonProperty("any_channel")
    ANY_CHANNEL("any_channel"),

    @JsonProperty("foreign")
    FOREIGN("foreign"),

    @JsonProperty("sdk")
    SDK("sdk"),

    @JsonProperty("messaging")
    MESSAGING("messaging"),
    ;

    public static final Map<String, UserIdentityObjectType> VALUE_MAPPING = Map.copyOf(Arrays.stream(values())
        .collect(Collectors.toMap(v -> v.value, Function.identity())));

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Create this enum from a value.
     *
     * @param value The value
     *
     * @return The enum
     */
    @JsonCreator
    public static UserIdentityObjectType fromValue(String value) {
        if (!VALUE_MAPPING.containsKey(value)) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
        return VALUE_MAPPING.get(value);
    }
}