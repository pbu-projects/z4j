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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lol.pbu.z4j.Generated;

/**
 * <h1>Zendesk Background Job Status Lifecycle.</h1>
 * <p>When calling asynchronous or batch endpoints in Zendesk (such as {@code TicketClient.createManyTickets}),
 * Zendesk returns an immediate {@link JobStatusResponse} wrapping this {@link JobStatus} with status "queued" or "working".</p>
 *
 * <h3>Working with Background Jobs:</h3>
 * <ol>
 *     <li>Extract the job status ID from the initial response:
 *         <pre>{@code
 *         JobStatusResponse response = ticketClient.createManyTickets(createRequest).block();
 *         String jobId = response.getJobStatus().getId();
 *         }</pre>
 *     </li>
 *     <li>Poll {@code JobStatusClient.showJobStatus(jobId)} until {@link #getStatus()} is {@code "completed"} or {@code "failed"}:
 *         <pre>{@code
 *         JobStatus status = jobStatusClient.showJobStatus(jobId).block().getJobStatus();
 *         while ("queued".equals(status.getStatus()) || "working".equals(status.getStatus())) {
 *             Thread.sleep(1000);
 *             status = jobStatusClient.showJobStatus(jobId).block().getJobStatus();
 *         }
 *         }</pre>
 *     </li>
 *     <li>Access child operation results via {@link #getJobStatusResults()}:
 *         <pre>{@code
 *         List<JobStatusResult> results = status.getJobStatusResults();
 *         for (JobStatusResult result : results) {
 *             Long ticketId = result.getId();
 *             Boolean success = result.getSuccess();
 *         }
 *         }</pre>
 *     </li>
 * </ol>
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        "id",
        "url",
        "total",
        "progress",
        "status",
        "message",
        "job_type",
        "results"
})
@Serdeable
@Introspected
@Generated
public class JobStatus {

    @Nullable
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty("total")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer total;

    @Nullable
    @JsonProperty("progress")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer progress;

    @Nullable
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    @Nullable
    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String message;

    @Nullable
    @JsonProperty("job_type")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String jobType;

    @Nullable
    @JsonProperty("results")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> results;

    private static final String FIELD_ID = "id";
    private static final String FIELD_ACTION = "action";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_SUCCESS = "success";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_ERROR = "error";
    private static final String FIELD_DETAILS = "details";

    /**
     * Converts the raw map results to strongly-typed {@link JobStatusResult} objects.
     *
     * @return List of typed {@link JobStatusResult} entries, or null if results are not present
     */
    @JsonIgnore
    public @Nullable List<JobStatusResult> getJobStatusResults() {
        if (results == null) {
            return null;
        }
        return results.stream().map(JobStatus::toJobStatusResult).toList();
    }

    private static JobStatusResult toJobStatusResult(Map<String, Object> map) {
        JobStatusResult res = new JobStatusResult();
        res.setId(parseLongId(map.get(FIELD_ID)));
        res.setAction(getStringValue(map.get(FIELD_ACTION)));
        res.setStatus(getStringValue(map.get(FIELD_STATUS)));
        res.setSuccess(parseBoolean(map.get(FIELD_SUCCESS)));
        res.setTitle(getStringValue(map.get(FIELD_TITLE)));
        res.setError(getStringValue(map.get(FIELD_ERROR)));
        res.setDetails(getStringValue(map.get(FIELD_DETAILS)));
        return res;
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

    /**
     * Sets typed {@link JobStatusResult} entries by converting them into the underlying results map.
     *
     * @param jobStatusResults the list of typed job status results
     * @return this {@link JobStatus} instance
     */
    @JsonIgnore
    public JobStatus setJobStatusResults(@Nullable List<JobStatusResult> jobStatusResults) {
        if (jobStatusResults == null) {
            this.results = null;
            return this;
        }
        this.results = new ArrayList<>(jobStatusResults.stream().map(JobStatus::toResultMap).toList());
        return this;
    }

    private static Map<String, Object> toResultMap(JobStatusResult res) {
        Map<String, Object> map = new LinkedHashMap<>();
        putIfNotNull(map, FIELD_ID, res.getId());
        putIfNotNull(map, FIELD_ACTION, res.getAction());
        putIfNotNull(map, FIELD_STATUS, res.getStatus());
        putIfNotNull(map, FIELD_SUCCESS, res.getSuccess());
        putIfNotNull(map, FIELD_TITLE, res.getTitle());
        putIfNotNull(map, FIELD_ERROR, res.getError());
        putIfNotNull(map, FIELD_DETAILS, res.getDetails());
        return map;
    }

    private static void putIfNotNull(Map<String, Object> map, String key, @Nullable Object val) {
        if (val != null) {
            map.put(key, val);
        }
    }
}
