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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Zendesk background job status tracking asynchronous progress.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@Serdeable
@Introspected
@RecordBuilder
@JsonPropertyOrder({
        JobStatus.JSON_PROPERTY_ID,
        JobStatus.JSON_PROPERTY_URL,
        JobStatus.JSON_PROPERTY_TOTAL,
        JobStatus.JSON_PROPERTY_PROGRESS,
        JobStatus.JSON_PROPERTY_STATUS,
        JobStatus.JSON_PROPERTY_MESSAGE,
        JobStatus.JSON_PROPERTY_JOB_TYPE,
        JobStatus.JSON_PROPERTY_RESULTS
})
public record JobStatus(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String id,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_TOTAL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer total,
        @Nullable @JsonProperty(JSON_PROPERTY_PROGRESS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer progress,
        @Nullable @JsonProperty(JSON_PROPERTY_STATUS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String status,
        @Nullable @JsonProperty(JSON_PROPERTY_MESSAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String message,
        @Nullable @JsonProperty(JSON_PROPERTY_JOB_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String jobType,
        @Nullable @JsonProperty(JSON_PROPERTY_RESULTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Map<String, Object>> results
) implements JobStatusBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_TOTAL = "total";
    public static final String JSON_PROPERTY_PROGRESS = "progress";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_MESSAGE = "message";
    public static final String JSON_PROPERTY_JOB_TYPE = "job_type";
    public static final String JSON_PROPERTY_RESULTS = "results";

    private static final String FIELD_ID = "id";
    private static final String FIELD_ACTION = "action";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_SUCCESS = "success";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_ERROR = "error";
    private static final String FIELD_DETAILS = "details";

    public JobStatus {
        if (status == null && message != null) {
            status = "completed";
        }
    }

    public JobStatus() {
        this(null, null, null, null, null, null, null, null);
    }

    public JobStatus(String status) {
        this(null, null, null, null, status, null, null, null);
    }

    public JobStatus(String status, String message) {
        this(null, null, null, null, status, message, null, null);
    }

    @JsonIgnore
    public @Nullable List<JobStatusResult> getJobStatusResults() {
        if (results == null) {
            return null;
        }
        return results.stream().map(JobStatus::toJobStatusResult).toList();
    }

    public JobStatus withJobStatusResults(@Nullable List<JobStatusResult> jobStatusResults) {
        if (jobStatusResults == null) {
            return withResults(null);
        }
        return withResults(new ArrayList<>(jobStatusResults.stream().map(JobStatus::toResultMap).toList()));
    }

    public JobStatus addResultsItem(Map<String, Object> resultsItem) {
        List<Map<String, Object>> newResults = results == null ? new ArrayList<>() : new ArrayList<>(results);
        newResults.add(resultsItem);
        return withResults(newResults);
    }

    private static JobStatusResult toJobStatusResult(Map<String, Object> map) {
        return new JobStatusResult(
                parseLongId(map.get(FIELD_ID)),
                getStringValue(map.get(FIELD_ACTION)),
                getStringValue(map.get(FIELD_STATUS)),
                parseBoolean(map.get(FIELD_SUCCESS)),
                getStringValue(map.get(FIELD_TITLE)),
                getStringValue(map.get(FIELD_ERROR)),
                getStringValue(map.get(FIELD_DETAILS))
        );
    }

    private static @Nullable Long parseLongId(@Nullable Object idObj) {
        if (idObj == null) {
            return null;
        }
        if (idObj instanceof Number number) {
            return number.longValue();
        }
        try {
            return Long.parseLong(idObj.toString());
        } catch (NumberFormatException _) {
            return null;
        }
    }

    private static @Nullable String getStringValue(@Nullable Object val) {
        return val != null ? String.valueOf(val) : null;
    }

    private static @Nullable Boolean parseBoolean(@Nullable Object val) {
        if (val == null) {
            return null;
        }
        return val instanceof Boolean b ? b : Boolean.parseBoolean(String.valueOf(val));
    }

    private static Map<String, Object> toResultMap(JobStatusResult res) {
        Map<String, Object> map = new LinkedHashMap<>();
        putIfNotNull(map, FIELD_ID, res.id());
        putIfNotNull(map, FIELD_ACTION, res.action());
        putIfNotNull(map, FIELD_STATUS, res.status());
        putIfNotNull(map, FIELD_SUCCESS, res.success());
        putIfNotNull(map, FIELD_TITLE, res.title());
        putIfNotNull(map, FIELD_ERROR, res.error());
        putIfNotNull(map, FIELD_DETAILS, res.details());
        return map;
    }

    private static void putIfNotNull(Map<String, Object> map, String key, @Nullable Object val) {
        if (val != null) {
            map.put(key, val);
        }
    }
}
