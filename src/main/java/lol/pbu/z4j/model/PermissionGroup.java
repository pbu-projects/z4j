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
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Management Permission Group in Zendesk Guide.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        PermissionGroup.JSON_PROPERTY_BUILT_IN,
        PermissionGroup.JSON_PROPERTY_CREATED_AT,
        PermissionGroup.JSON_PROPERTY_EDIT,
        PermissionGroup.JSON_PROPERTY_ID,
        PermissionGroup.JSON_PROPERTY_NAME,
        PermissionGroup.JSON_PROPERTY_PUBLISH,
        PermissionGroup.JSON_PROPERTY_UPDATED_AT
})
public record PermissionGroup(
        @Nullable @JsonProperty(JSON_PROPERTY_BUILT_IN) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean builtIn,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_EDIT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Long> edit,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @NotNull @JsonProperty(JSON_PROPERTY_NAME) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_PUBLISH) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Long> publish,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt
) implements PermissionGroupBuilder.With {

    public static final String JSON_PROPERTY_BUILT_IN = "built_in";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_EDIT = "edit";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PUBLISH = "publish";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public PermissionGroup() {
        this(null, null, null, null, null, null, null);
    }

    public PermissionGroup(String name) {
        this(null, null, null, null, name, null, null);
    }

    public PermissionGroup(String name, List<Long> edit, List<Long> publish) {
        this(null, null, edit, null, name, publish, null);
    }

    public PermissionGroup addEditItem(Long editItem) {
        List<Long> newEdit = edit == null ? new ArrayList<>() : new ArrayList<>(edit);
        newEdit.add(editItem);
        return withEdit(newEdit);
    }

    public PermissionGroup addPublishItem(Long publishItem) {
        List<Long> newPublish = publish == null ? new ArrayList<>() : new ArrayList<>(publish);
        newPublish.add(publishItem);
        return withPublish(newPublish);
    }
}
