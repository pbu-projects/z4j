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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.*;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with User Segments in Zendesk.</h1>
 * <ul>
 *     <li>Create User Segment {@link #createUserSegment}</li>
 *     <li>Delete User Segment {@link #deleteUserSegment}</li>
 *     <li>List Sections with User Segment {@link #listUserSegmentSections}</li>
 *     <li>List Topics with User Segment {@link #listUserSegmentTopics}</li>
 *     <li>List User Segments {@link #listUserSegments}</li>
 *     <li>Show User Segment {@link #showUserSegment}</li>
 *     <li>Update User Segment {@link #updateUserSegment}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Retryable
@Client("zendesk")
public interface UserSegmentClient {

    /**
     * <h1>{@summary Create User Segment}</h1>
     * <h4>Allowed for Help Center managers</h4>
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
     * <h1>{@summary Delete User Segment}</h1>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return Response when the use segment was deleted (status code 204)
     */
    @Delete("/api/v2/help_center/user_segments/{user_segment_id}")
    Mono<Void> deleteUserSegment(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * <h1>{@summary List Sections with User Segment}</h1>
     * Lists the sections that use the specified user segment.<br><br>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments/{user_segment_id}/sections")
    Mono<@Valid SectionsResponse> listUserSegmentSections(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * <h1>{@summary List Topics with User Segment}</h1>
     * <p>Lists the topics that use the specified user segment.</p> 
     *
     * Pagination is not currently supported. Open an issue with us to let us know you'd like this supported!
     * <br>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments/{user_segment_id}/topics")
    Mono<@Valid TopicsResponse> listUserSegmentTopics(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * <h1>{@summary List User Segments}</h1>
     * <p>Some user segments can only be applied to sections and topics on certain Guide plans. 
     * For instance, user segments with a <code>user_type</code> of <code>&quot;staff&quot;</code> cannot be applied to sections and topics on accounts on the Guide     Lite plan or the Suite Team plan. <br>     
     *
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param builtIn Only built_in user segments if true, only custom user segments if false (optional)
     * @return OK response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments")
    Mono<@Valid UserSegmentsResponse> listUserSegments(
            @QueryValue("built_in") @Nullable Boolean builtIn
    );

    /**
     * <h1>{@summary Show User Segment}</h1>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param userSegmentId The unique ID of the user segment (required)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/user_segments/{user_segment_id}")
    Mono<@Valid UserSegmentResponse> showUserSegment(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId
    );

    /**
     * <h1>{@summary Update User Segment}</h1>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param userSegmentId            The unique ID of the user segment (required)
     * @param createUserSegmentRequest (optional)
     * @return OK Response (status code 200)
     * or Bad request Response (status code 400)
     */
    @Put("/api/v2/help_center/user_segments/{user_segment_id}")
    Mono<@Valid UserSegmentResponse> updateUserSegment(
            @PathVariable("user_segment_id") @NotNull Long userSegmentId,
            @Body @NotNull @Valid CreateUserSegmentRequest createUserSegmentRequest
    );
}
