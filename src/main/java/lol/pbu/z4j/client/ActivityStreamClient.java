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
import lol.pbu.z4j.model.ActivitiesCountResponse;
import lol.pbu.z4j.model.ActivitiesResponse;
import lol.pbu.z4j.model.ActivityResponse;
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
public interface ActivityStreamClient {

    /**
     * {@summary Count Activities}
     * <p>Returns an approximate count of ticket activities in the last 30 days affecting the agent making the request. If the count exceeds 100,000, the count will return a cached result. This cached result will update every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Count of ticket activities</p> (status code 200)
     */
    @Get("/api/v2/activities/count")
    Mono<@Valid ActivitiesCountResponse> countActivities();

    /**
     * {@summary List Activities}
     * <p>Lists ticket activities in the last 30 days affecting the agent making the request. Also sideloads the following arrays of user records:</p> <ul> <li>actors - All actors involved in the listed activities</li> <li>users - All users involved in the listed activities</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param since <p>A UTC time in ISO 8601 format to return ticket activities since said date.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/activities")
    Mono<@Valid ActivitiesResponse> listActivities(
        @QueryValue("since") @Nullable String since
    );

    /**
     * {@summary Show Activity}
     * <p>Lists a specific activity.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param activityId <p>The activity ID</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/activities/{activity_id}")
    Mono<@Valid ActivityResponse> showActivity(
        @PathVariable("activity_id") @NotNull Integer activityId
    );
}