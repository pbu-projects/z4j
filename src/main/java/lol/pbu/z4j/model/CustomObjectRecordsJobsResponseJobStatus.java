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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.CustomObjectRecord;
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
 * CustomObjectRecordsJobsResponseJobStatus
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomObjectRecordsJobsResponseJobStatus.JSON_PROPERTY_ID,
    CustomObjectRecordsJobsResponseJobStatus.JSON_PROPERTY_MESSAGE,
    CustomObjectRecordsJobsResponseJobStatus.JSON_PROPERTY_PROGRESS,
    CustomObjectRecordsJobsResponseJobStatus.JSON_PROPERTY_RESULTS,
    CustomObjectRecordsJobsResponseJobStatus.JSON_PROPERTY_STATUS,
    CustomObjectRecordsJobsResponseJobStatus.JSON_PROPERTY_TOTAL,
    CustomObjectRecordsJobsResponseJobStatus.JSON_PROPERTY_URL,
})
@Serdeable
public class CustomObjectRecordsJobsResponseJobStatus {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MESSAGE = "message";
    public static final String JSON_PROPERTY_PROGRESS = "progress";
    public static final String JSON_PROPERTY_RESULTS = "results";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_TOTAL = "total";
    public static final String JSON_PROPERTY_URL = "url";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MESSAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String message;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PROGRESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long progress;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RESULTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomObjectRecord> results;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TOTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long total;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * Add an item to the results property in a chainable fashion.
     *
     * @return The same instance of CustomObjectRecordsJobsResponseJobStatus for chaining.
     */
    public CustomObjectRecordsJobsResponseJobStatus addResultsItem(CustomObjectRecord resultsItem) {
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(resultsItem);
        return this;
    }

}