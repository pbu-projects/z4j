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
 * TargetCommonFields
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetCommonFields.JSON_PROPERTY_TITLE,
    TargetCommonFields.JSON_PROPERTY_TYPE,
    TargetCommonFields.JSON_PROPERTY_ACTIVE,
    TargetCommonFields.JSON_PROPERTY_CREATED_AT,
    TargetCommonFields.JSON_PROPERTY_ID,
})
@Serdeable
public class TargetCommonFields {

    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";

    /**
     * <p>A name for the target</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>A pre-defined target, such as \"basecamp_target\". See the additional attributes for the type that follow</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TYPE)
    private String type;

    /**
     * <p>Whether or not the target is activated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The time the target was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    public TargetCommonFields(String title, String type) {
        this.title = title;
        this.type = type;
    }

}