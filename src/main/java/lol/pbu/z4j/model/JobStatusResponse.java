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
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

@Getter
@Setter
@Accessors(chain = true)
@Serdeable
@Generated
public class JobStatusResponse {

    @Nullable
    @JsonProperty("job_status")
    private JobStatus jobStatus;

    @Nullable
    @JsonProperty("message")
    private String message;

    public JobStatusResponse() {
    }

    @Creator
    @JsonCreator
    public JobStatusResponse(
            @JsonProperty("job_status") @Nullable JobStatus jobStatus,
            @JsonProperty("message") @Nullable String message
    ) {
        if (jobStatus != null) {
            this.jobStatus = jobStatus;
        } else {
            this.jobStatus = new JobStatus().setStatus("completed");
            if (message != null) {
                this.jobStatus.setMessage(message);
            }
        }
        this.message = message;
    }

    public JobStatusResponse(JobStatus jobStatus) {
        this(jobStatus, null);
    }

    public JobStatus getJobStatus() {
        if (jobStatus == null) {
            jobStatus = new JobStatus().setStatus("completed");
            if (message != null) {
                jobStatus.setMessage(message);
            }
        }
        return jobStatus;
    }
}
