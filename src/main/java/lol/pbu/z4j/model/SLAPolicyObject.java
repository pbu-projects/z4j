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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.SLAPolicyFilterObject;
import lol.pbu.z4j.model.SLAPolicyMetricObject;
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
 * SLAPolicyObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    SLAPolicyObject.JSON_PROPERTY_FILTER,
    SLAPolicyObject.JSON_PROPERTY_TITLE,
    SLAPolicyObject.JSON_PROPERTY_CREATED_AT,
    SLAPolicyObject.JSON_PROPERTY_DESCRIPTION,
    SLAPolicyObject.JSON_PROPERTY_ID,
    SLAPolicyObject.JSON_PROPERTY_POLICY_METRICS,
    SLAPolicyObject.JSON_PROPERTY_POSITION,
    SLAPolicyObject.JSON_PROPERTY_UPDATED_AT,
    SLAPolicyObject.JSON_PROPERTY_URL,
})
@Serdeable
public class SLAPolicyObject {

    public static final String JSON_PROPERTY_FILTER = "filter";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POLICY_METRICS = "policy_metrics";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_FILTER)
    private SLAPolicyFilterObject filter;

    /**
     * <p>The title of the SLA policy</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>The time the SLA policy was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The description of the SLA policy</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Array of <a href=\"#policy-metric\">Policy Metric</a> objects</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POLICY_METRICS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SLAPolicyMetricObject> policyMetrics;

    /**
     * <p>Position of the SLA policy that determines the order they will be matched. If not specified, the SLA policy is added as the last position</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * <p>The time of the last update of the SLA policy</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>URL of the SLA policy record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public SLAPolicyObject(SLAPolicyFilterObject filter, String title) {
        this.filter = filter;
        this.title = title;
    }

    /**
     * Add an item to the policyMetrics property in a chainable fashion.
     *
     * @return The same instance of SLAPolicyObject for chaining.
     */
    public SLAPolicyObject addPolicyMetricsItem(SLAPolicyMetricObject policyMetricsItem) {
        if (policyMetrics == null) {
            policyMetrics = new ArrayList<>();
        }
        policyMetrics.add(policyMetricsItem);
        return this;
    }

}