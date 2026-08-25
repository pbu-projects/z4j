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
 * TargetGetSatisfaction
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetGetSatisfaction.JSON_PROPERTY_ACCOUNT_NAME,
    TargetGetSatisfaction.JSON_PROPERTY_EMAIL,
    TargetGetSatisfaction.JSON_PROPERTY_PASSWORD,
    TargetGetSatisfaction.JSON_PROPERTY_TARGET_URL,
})
@Serdeable
public class TargetGetSatisfaction {

    public static final String JSON_PROPERTY_ACCOUNT_NAME = "account_name";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_TARGET_URL = "target_url";

    @NotNull
    @JsonProperty(JSON_PROPERTY_ACCOUNT_NAME)
    private String accountName;

    @NotNull
    @JsonProperty(JSON_PROPERTY_EMAIL)
    private String email;

    /**
     * <p>only writable</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    private String password;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String targetUrl;

    public TargetGetSatisfaction(String accountName, String email, String password) {
        this.accountName = accountName;
        this.email = email;
        this.password = password;
    }

}