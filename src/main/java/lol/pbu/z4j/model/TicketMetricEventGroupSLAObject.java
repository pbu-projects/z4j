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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * TicketMetricEventGroupSLAObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_DELETED,
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_ID,
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_INSTANCE_ID,
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_METRIC,
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_TICKET_ID,
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_TIME,
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_TYPE,
    TicketMetricEventGroupSLAObject.JSON_PROPERTY_GROUP_SLA,
})
@Serdeable
public class TicketMetricEventGroupSLAObject {

    public static final String JSON_PROPERTY_DELETED = "deleted";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_INSTANCE_ID = "instance_id";
    public static final String JSON_PROPERTY_METRIC = "metric";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_TIME = "time";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_GROUP_SLA = "group_sla";

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
    private Long id;

    /**
     * <p>The instance of the metric associated with the event. See <a href=\"#instance_id\">instance_id</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INSTANCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long instanceId;

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
    private Long ticketId;

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
     * <p>Available if <code>type</code> is \"apply_group_sla\". The Group SLA policy and target being enforced on the ticket and metric in question, if any. See <a href=\"#group_sla\">group_sla</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_SLA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object groupSla;

}