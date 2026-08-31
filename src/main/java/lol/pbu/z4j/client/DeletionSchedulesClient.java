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
import lol.pbu.z4j.model.CreateDeletionScheduleRequest;
import lol.pbu.z4j.model.ListDeletionSchedules200Response;
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
public interface DeletionSchedulesClient {

    /**
     * {@summary Create Deletion Schedule}
     * <p>Creates a new deletion schedule.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param createDeletionScheduleRequest (required)
     *
     * @return <p>Success Response</p> (status code 201)
     */
    @Post("/api/v2/deletion_schedules")
    Mono<@Valid CreateDeletionScheduleRequest> createDeletionSchedule(
        @Body @NotNull @Valid CreateDeletionScheduleRequest createDeletionScheduleRequest
    );

    /**
     * {@summary Delete Deletion Schedule}
     * <p>Deletes a deletion schedule by its id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param deletionScheduleId <p>The id of the deletion schedule</p> (required)
     *
     * @return <p>Success Response</p> (status code 204)
     */
    @Delete("/api/v2/deletion_schedules/{deletion_schedule_id}")
    Mono<Void> deleteDeletionSchedule(
        @PathVariable("deletion_schedule_id") @NotNull Long deletionScheduleId
    );

    /**
     * {@summary Get Deletion Schedule}
     * <p>Gets a deletion schedule by its id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param deletionScheduleId <p>The id of the deletion schedule</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/deletion_schedules/{deletion_schedule_id}")
    Mono<@Valid CreateDeletionScheduleRequest> getDeletionSchedule(
        @PathVariable("deletion_schedule_id") @NotNull Long deletionScheduleId
    );

    /**
     * {@summary List Deletion Schedules}
     * <p>Lists all deletion schedules for the account. Deletion schedules are used to automatically delete data from the account after a certain period of time.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/deletion_schedules")
    Mono<@Valid ListDeletionSchedules200Response> listDeletionSchedules();

    /**
     * {@summary Update Deletion Schedule}
     * <p>Updates a deletion schedule by its id.</p> <p><strong>Note</strong>: Updating a condition updates the conditions array, clearing all existing values of the array. Include all your conditions when updating any condition.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param deletionScheduleId <p>The id of the deletion schedule</p> (required)
     * @param createDeletionScheduleRequest (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Put("/api/v2/deletion_schedules/{deletion_schedule_id}")
    Mono<@Valid CreateDeletionScheduleRequest> updateDeletionSchedule(
        @PathVariable("deletion_schedule_id") @NotNull Long deletionScheduleId,
        @Body @NotNull @Valid CreateDeletionScheduleRequest createDeletionScheduleRequest
    );
}