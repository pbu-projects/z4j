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
import lol.pbu.z4j.model.AssigneeFieldAssignableGroupObject;
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
 * AssigneeFieldAssignableGroupsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AssigneeFieldAssignableGroupsResponse.JSON_PROPERTY_COUNT,
    AssigneeFieldAssignableGroupsResponse.JSON_PROPERTY_GROUPS,
    AssigneeFieldAssignableGroupsResponse.JSON_PROPERTY_NEXT_PAGE,
    AssigneeFieldAssignableGroupsResponse.JSON_PROPERTY_PREVIOUS_PAGE,
})
@Serdeable
public class AssigneeFieldAssignableGroupsResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_GROUPS = "groups";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";

    /**
     * <p>Number of groups listed in <code>groups</code> property.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AssigneeFieldAssignableGroupObject> groups;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousPage;

    /**
     * Add an item to the groups property in a chainable fashion.
     *
     * @return The same instance of AssigneeFieldAssignableGroupsResponse for chaining.
     */
    public AssigneeFieldAssignableGroupsResponse addGroupsItem(AssigneeFieldAssignableGroupObject groupsItem) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(groupsItem);
        return this;
    }

}