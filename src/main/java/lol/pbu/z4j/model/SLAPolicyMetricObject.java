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
 * SLAPolicyMetricObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SLAPolicyMetricObject.JSON_PROPERTY_BUSINESS_HOURS,
    SLAPolicyMetricObject.JSON_PROPERTY_METRIC,
    SLAPolicyMetricObject.JSON_PROPERTY_PRIORITY,
    SLAPolicyMetricObject.JSON_PROPERTY_TARGET,
})
@Serdeable
public class SLAPolicyMetricObject {

    public static final String JSON_PROPERTY_BUSINESS_HOURS = "business_hours";
    public static final String JSON_PROPERTY_METRIC = "metric";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_TARGET = "target";

    /**
     * <p>Whether the metric targets are being measured in business hours or calendar hours</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BUSINESS_HOURS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean businessHours;

    /**
     * <p>The definition of the time that is being measured</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METRIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String metric;

    /**
     * <p>Priority that a ticket must match</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String priority;

    /**
     * <p>The time within which the end-state for a metric should be met</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long target;

}