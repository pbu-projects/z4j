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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.CustomRoleObject;
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
 * CustomRolesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(CustomRolesResponse.JSON_PROPERTY_CUSTOM_ROLES)
@Serdeable
public class CustomRolesResponse {

    public static final String JSON_PROPERTY_CUSTOM_ROLES = "custom_roles";

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_ROLES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomRoleObject> customRoles;

    /**
     * Add an item to the customRoles property in a chainable fashion.
     *
     * @return The same instance of CustomRolesResponse for chaining.
     */
    public CustomRolesResponse addCustomRolesItem(CustomRoleObject customRolesItem) {
        if (customRoles == null) {
            customRoles = new ArrayList<>();
        }
        customRoles.add(customRolesItem);
        return this;
    }

}