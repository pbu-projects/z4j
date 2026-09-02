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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>The metric being tracked</p>
 */
@RequiredArgsConstructor
@Getter(onMethod = @__(@JsonValue))
@Serdeable
public enum TicketMetricEventBaseObjectMetric {

    @JsonProperty("agent_work_time")
    AGENT_WORK_TIME("agent_work_time"),

    @JsonProperty("pausable_update_time")
    PAUSABLE_UPDATE_TIME("pausable_update_time"),

    @JsonProperty("periodic_update_time")
    PERIODIC_UPDATE_TIME("periodic_update_time"),

    @JsonProperty("reply_time")
    REPLY_TIME("reply_time"),

    @JsonProperty("requester_wait_time")
    REQUESTER_WAIT_TIME("requester_wait_time"),

    @JsonProperty("resolution_time")
    RESOLUTION_TIME("resolution_time"),

    @JsonProperty("group_ownership_time")
    GROUP_OWNERSHIP_TIME("group_ownership_time"),
    ;

    public static final Map<String, TicketMetricEventBaseObjectMetric> VALUE_MAPPING = Map.copyOf(Arrays.stream(values())
        .collect(Collectors.toMap(v -> v.value, Function.identity())));

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Create this enum from a value.
     *
     * @param value The value
     *
     * @return The enum
     */
    @JsonCreator
    public static TicketMetricEventBaseObjectMetric fromValue(String value) {
        if (!VALUE_MAPPING.containsKey(value)) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
        return VALUE_MAPPING.get(value);
    }
}