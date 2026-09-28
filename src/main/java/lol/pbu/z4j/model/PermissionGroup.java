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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

import java.util.ArrayList;
import java.util.List;

/**
 * Management Permission Group in Zendesk Guide.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        PermissionGroup.JSON_PROPERTY_BUILT_IN,
        PermissionGroup.JSON_PROPERTY_CREATED_AT,
        PermissionGroup.JSON_PROPERTY_EDIT,
        PermissionGroup.JSON_PROPERTY_ID,
        PermissionGroup.JSON_PROPERTY_NAME,
        PermissionGroup.JSON_PROPERTY_PUBLISH,
        PermissionGroup.JSON_PROPERTY_UPDATED_AT
})
@Serdeable
@Generated
public class PermissionGroup {

    public static final String JSON_PROPERTY_BUILT_IN = "built_in";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_EDIT = "edit";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PUBLISH = "publish";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * Whether the permission group is built-in. Built-in permission groups cannot be modified or deleted.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BUILT_IN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean builtIn;

    /**
     * When the permission group was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The ids of the user segments that have edit privileges
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EDIT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Long> edit;

    /**
     * Automatically assigned when the permission group is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The name of the permission group
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * The ids of the user segments that have publish privileges
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PUBLISH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Long> publish;

    /**
     * When the permission group was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    public PermissionGroup(String name) {
        this.name = name;
    }

    public PermissionGroup(String name, List<Long> edit, List<Long> publish) {
        this.name = name;
        this.edit = edit;
        this.publish = publish;
    }

    /**
     * Add an item to the edit property in a chainable fashion.
     *
     * @return The same instance of PermissionGroup for chaining.
     */
    public PermissionGroup addEditItem(Long editItem) {
        if (edit == null) {
            edit = new ArrayList<>();
        }
        edit.add(editItem);
        return this;
    }

    /**
     * Add an item to the publish property in a chainable fashion.
     *
     * @return The same instance of PermissionGroup for chaining.
     */
    public PermissionGroup addPublishItem(Long publishItem) {
        if (publish == null) {
            publish = new ArrayList<>();
        }
        publish.add(publishItem);
        return this;
    }

}
