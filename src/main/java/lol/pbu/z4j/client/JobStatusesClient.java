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
package lol.pbu.z4j.client;

import io.micronaut.http.annotation.*;
import io.micronaut.core.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.core.convert.format.Format;
import lol.pbu.z4j.model.JobStatusResponse;
import lol.pbu.z4j.model.JobStatusesResponse;
import reactor.core.publisher.Mono;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface JobStatusesClient {

    /**
     * {@summary List Job Statuses}
     * <p>Shows the statuses for background jobs. Statuses are sorted first by completion date and then by creation date in descending order.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/job_statuses")
    Mono<@Valid JobStatusesResponse> listJobStatuses();

    /**
     * {@summary Show Job Status}
     * <p>Shows the status of a background job.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param jobStatusId <p>the Id of the Job status</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/job_statuses/{job_status_id}")
    Mono<@Valid JobStatusResponse> showJobStatus(
        @PathVariable("job_status_id") @NotNull String jobStatusId
    );

    /**
     * {@summary Show Many Job Statuses}
     * <p>Accepts a comma-separated list of job status ids.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>Comma-separated list of job status ids.</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/job_statuses/show_many")
    Mono<@Valid JobStatusesResponse> showManyJobStatuses(
        @QueryValue("ids") @NotNull String ids
    );
}