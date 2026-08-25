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
import lol.pbu.z4j.model.AuditObjectEventsInnerValue;
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
 * AuditObjectEventsInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AuditObjectEventsInner.JSON_PROPERTY_BODY,
    AuditObjectEventsInner.JSON_PROPERTY_FIELD_NAME,
    AuditObjectEventsInner.JSON_PROPERTY_ID,
    AuditObjectEventsInner.JSON_PROPERTY_TYPE,
    AuditObjectEventsInner.JSON_PROPERTY_VALUE,
})
@Serdeable
public class AuditObjectEventsInner {

    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_FIELD_NAME = "field_name";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_VALUE = "value";

    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FIELD_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String fieldName;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AuditObjectEventsInnerValue value;

}