package lol.pbu.z4j.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.JobStatus;
import lol.pbu.z4j.model.JobStatusesResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Zendesk Job Statii...? Job Statuses</h1>
 * <ul>
 *     <li>{@link #getJobStatuses}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@Client("zendesk")
public interface JobStatusClient {

    /**
     * <h1>Shows the statuses for all background jobs.</h1>
     * <p>Statuses are sorted first by completion date and then by creation date in descending order.</p>
     * <h3>Pagination</h3>
     * <p>Cursor-based pagination is supported</p>
     *
     * @return 200 response (status code 200)
     */
    @Get("api/v2/job_statuses/")
    Mono<JobStatusesResponse> getJobStatuses(
            @QueryValue("page[size]") @Nullable @Max(1000) Integer pageSize,
            @QueryValue("page[after]") @Nullable String pageAfter
    );

    /**
     * <h1>Get the status of a given job</h1>
     *
     * @param jobId String identifier for the job, ie "V3-c461432ed4ee6c3b7657e64a959bbeb0"
     * @return 200 response (status code 200)
     */
    @Get("api/v2/job_statuses/{jobId}")
    Mono<JobStatus> getJobStatus(@NotNull String jobId);
}
