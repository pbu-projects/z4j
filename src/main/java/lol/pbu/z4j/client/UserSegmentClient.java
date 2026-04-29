package lol.pbu.z4j.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.*;
import reactor.core.publisher.Mono;

@Retryable
@Client("zendesk")
public interface UserSegmentClient {

    /**
     * {@summary Create User Segment}
     * (Allowed for Help Center managers)
     *
     * @param createUserSegmentRequest (optional)
     * @return Created response (status code 201)
     * or Bad request response (status code 400)
     */
    @Post("/api/v2/help_center/user_segments")
    Mono<@Valid UserSegmentResponse> createUserSegment(
            @Body @Nullable @Valid CreateUserSegmentRequest createUserSegmentRequest
    );

    /**
     * {@summary Delete User Segment}
     * (Allowed for Help Center managers)
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return Response when the use rsegment was deleted (status code 204)
     */
    @Delete("/api/v2/help_center/user_segments/{user_segment_id}")
    Mono<Void> deleteUserSegment(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * {@summary List Sections with User Segment}
     * Lists the sections that use the specified user segment.<br><br>  (Allowed for Help Center managers)
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments/{user_segment_id}/sections")
    Mono<@Valid SectionsResponse> listUserSegmentSections(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * {@summary List Topics with User Segment}
     * <p>Lists the topics that use the specified user segment.</p> <p>This endpoint supports pagination as described in <a href='developer.zendesk.com/api-reference/help_center/help-center-api/help-center-api/#pagination'>Pagination</a>.</p> <h4 id=\"allowed-for'>Allowed for</h4> <ul>     <li>Help Center managers</li> </ul> <h4 id=\"pagination'>Pagination</h4> <ul>     <li>Cursor pagination (recommended)</li>     <li>Offset pagination</li> </ul> <p>See <a href='developer.zendesk.com/api-reference/introduction/pagination/'>Pagination</a>.</p>
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments/{user_segment_id}/topics")
    Mono<@Valid TopicsResponse> listUserSegmentTopics(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * {@summary List User Segments}
     * <p>Some user segments can only be applied to sections and topics on certain Guide plans. For instance, user     segments with a <code>user_type</code> of <code>&quot;staff&quot;</code> cannot be applied to sections and topics on accounts on the Guide     Lite plan or the Suite Team plan. <br>     (Allowed for Help Center managers)</p>
     *
     * @param builtIn Only built_in user segments if true, only custom user segments if false (optional)
     * @return OK response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments")
    Mono<@Valid UserSegmentsResponse> listUserSegments(
            @QueryValue("built_in") @Nullable Boolean builtIn
    );

    /**
     * {@summary Show User Segment}
     * (Allowed for Help Center managers)
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments/{user_segment_id}")
    Mono<@Valid UserSegmentResponse> showUserSegment(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * {@summary Update User Segment}
     * (Allowed for Help Center managers)
     *
     * @param userSegmentId            The unique ID of the user segment (required)
     * @param createUserSegmentRequest (optional)
     * @return OK Response (status code 200)
     * or Bad request Response (status code 400)
     */
    @Put("/api/v2/help_center/user_segments/{user_segment_id}")
    Mono<@Valid UserSegmentResponse> updateUserSegment(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId,
            @Body @Nullable @Valid CreateUserSegmentRequest createUserSegmentRequest
    );
}