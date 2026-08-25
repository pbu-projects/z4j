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
import java.time.ZonedDateTime;
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
 * GroupObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    GroupObject.JSON_PROPERTY_NAME,
    GroupObject.JSON_PROPERTY_CREATED_AT,
    GroupObject.JSON_PROPERTY_DEFAULT,
    GroupObject.JSON_PROPERTY_DELETED,
    GroupObject.JSON_PROPERTY_DESCRIPTION,
    GroupObject.JSON_PROPERTY_ID,
    GroupObject.JSON_PROPERTY_IS_PUBLIC,
    GroupObject.JSON_PROPERTY_UPDATED_AT,
    GroupObject.JSON_PROPERTY_URL,
})
@Serdeable
public class GroupObject {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DELETED = "deleted";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IS_PUBLIC = "is_public";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The name of the group</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>The time the group was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>If the group is the default one for the account</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>Deleted groups get marked as such</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean deleted;

    /**
     * <p>The description of the group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned when creating groups</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>If true, the group is public. If false, the group is private. You can't change a private group to a public group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isPublic;

    /**
     * <p>The time of the last update of the group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of the group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public GroupObject(String name) {
        this.name = name;
    }

}