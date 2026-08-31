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
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.SatisfactionRatingResponse;
import lol.pbu.z4j.model.SatisfactionRatingsCountResponse;
import lol.pbu.z4j.model.SatisfactionRatingsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SatisfactionRatingsClient {

    /**
     * {@summary Count Satisfaction Ratings}
     * <p>Returns an approximate count of satisfaction ratings in the account. If the count exceeds 100,000, the count will return a cached result. This cached result will update every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Count of satisfaction ratings</p> (status code 200)
     */
    @Get("/api/v2/satisfaction_ratings/count")
    Mono<@Valid SatisfactionRatingsCountResponse> countSatisfactionRatings();

    /**
     * {@summary Create a Satisfaction Rating}
     * <p>Creates a CSAT rating for a solved ticket, or for a ticket that was previously solved and then reopened.</p> <p>Only the end user listed as the ticket requester can create a satisfaction rating for the ticket.</p> <p>Only \"good\" and \"bad\" are valid values for the score when creating a rating. Other states, like \"offered\", are not valid and will result in a 422 error.</p> <h4>Allowed For</h4> <ul> <li>End user who requested the ticket</li> </ul> <p>The end user must be a verified user.</p>
     *
     * @param ticketId <p>The id of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/tickets/{ticket_id}/satisfaction_rating")
    Mono<@Valid SatisfactionRatingResponse> createTicketSatisfactionRating(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary List Satisfaction Ratings}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Filters</h4> <p>| Parameter  | Value | ---------- | ----- | score      | offered, unoffered, received, received_with_comment, received_without_comment,<br/>good, good_with_comment, good_without_comment,<br/>bad, bad_with_comment, bad_without_comment | start_time | Time of the oldest satisfaction rating, as a <a href=\"https://www.epochconverter.com/\">Unix epoch time</a> | end_time   | Time of the most recent satisfaction rating, as a <a href=\"https://www.epochconverter.com/\">Unix epoch time</a></p> <p>If you specify an unqualified score such as <code>good</code>, the results include all the records with and without comments.</p> <p>Examples:</p> <ul> <li><code>/api/v2/satisfaction_ratings.json?score=bad</code></li> <li><code>/api/v2/satisfaction_ratings.json?score=bad&amp;start_time=1498151194</code></li> <li><code>/api/v2/satisfaction_ratings.json?start_time=1340384793&amp;end_time=1371920793</code></li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/satisfaction_ratings")
    Mono<@Valid SatisfactionRatingsResponse> listSatisfactionRatings();

    /**
     * {@summary Show Satisfaction Rating}
     * <p>Returns a specific satisfaction rating. You can get the id from the <a href=\"#list-satisfaction-ratings\">List Satisfaction Ratings</a> endpoint.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param satisfactionRatingId <p>The id of the satisfaction rating to retrieve</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/satisfaction_ratings/{satisfaction_rating_id}")
    Mono<@Valid SatisfactionRatingResponse> showSatisfactionRating(
        @PathVariable("satisfaction_rating_id") @NotNull Long satisfactionRatingId
    );
}