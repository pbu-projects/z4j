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
 * <p>Number of minutes to the first resolution time during calendar and business hours</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketMetricObjectFirstResolutionTimeInMinutes.JSON_PROPERTY_BUSINESS,
    TicketMetricObjectFirstResolutionTimeInMinutes.JSON_PROPERTY_CALENDAR,
})
@Serdeable
public class TicketMetricObjectFirstResolutionTimeInMinutes {

    public static final String JSON_PROPERTY_BUSINESS = "business";
    public static final String JSON_PROPERTY_CALENDAR = "calendar";

    /**
     * <p>Time in business hours</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BUSINESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long business;

    /**
     * <p>Time in calendar hours</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CALENDAR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long calendar;

}