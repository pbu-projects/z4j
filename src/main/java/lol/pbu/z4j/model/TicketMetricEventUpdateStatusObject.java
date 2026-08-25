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
import lol.pbu.z4j.model.TicketMetricEventBaseObjectMetric;
import lol.pbu.z4j.model.TicketMetricEventBaseObjectType;
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
 * TicketMetricEventUpdateStatusObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_DELETED,
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_ID,
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_INSTANCE_ID,
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_METRIC,
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_TICKET_ID,
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_TIME,
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_TYPE,
    TicketMetricEventUpdateStatusObject.JSON_PROPERTY_STATUS,
})
@Serdeable
public class TicketMetricEventUpdateStatusObject {

    public static final String JSON_PROPERTY_DELETED = "deleted";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_INSTANCE_ID = "instance_id";
    public static final String JSON_PROPERTY_METRIC = "metric";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_TIME = "time";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_STATUS = "status";

    /**
     * <p>If true, the event has been deleted</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean deleted;

    /**
     * <p>Automatically assigned when the record is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The instance of the metric associated with the event. See <a href=\"#instance_id\">instance_id</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INSTANCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer instanceId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_METRIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricEventBaseObjectMetric metric;

    /**
     * <p>Id of the associated ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer ticketId;

    /**
     * <p>The time the event occurred</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime time;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricEventBaseObjectType type;

    /**
     * <p>Available if <code>type</code> is <code>update_status</code>. Minutes since the metric has been open. See <a href=\"#status\">status</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object status;

}