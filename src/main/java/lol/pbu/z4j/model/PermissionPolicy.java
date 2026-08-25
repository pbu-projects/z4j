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
import lol.pbu.z4j.model.PermissionPolicyRecords;
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
 * PermissionPolicy
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    PermissionPolicy.JSON_PROPERTY_ID,
    PermissionPolicy.JSON_PROPERTY_RECORDS,
    PermissionPolicy.JSON_PROPERTY_ROLE_NAME,
})
@Serdeable
public class PermissionPolicy {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_RECORDS = "records";
    public static final String JSON_PROPERTY_ROLE_NAME = "role_name";

    /**
     * <p>The policy ID (e.g., 'custom-role-123' or 'end-user')</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_RECORDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private PermissionPolicyRecords records;

    /**
     * <p>The name of the role this policy applies to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String roleName;

}