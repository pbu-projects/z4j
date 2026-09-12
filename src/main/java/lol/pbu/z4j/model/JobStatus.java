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

import java.util.List;
import java.util.Map;
import lol.pbu.z4j.Generated;

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
}
