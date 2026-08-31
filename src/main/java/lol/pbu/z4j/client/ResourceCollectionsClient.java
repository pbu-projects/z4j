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
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.ResourceCollectionResponse;
import lol.pbu.z4j.model.ResourceCollectionsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface ResourceCollectionsClient {

    /**
     * {@summary Create Resource Collection}
     * <p>Creates a resource collection from a provided <code>payload</code> object. The <code>payload</code> object is specified the same way as the content of a requirements.json file in a Zendesk app. See <a href=\"/documentation/apps/app-developer-guide/apps_requirements/\">Specifying Apps Requirements</a> in the Zendesk Apps framework docs.</p> <p>The response includes a <a href=\"/api-reference/ticketing/ticket-management/job_statuses/\">job status</a> for creation of the specified resources.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/resource_collections")
    Mono<@Valid JobStatusResponse> createResourceCollection();

    /**
     * {@summary Delete Resource Collection}
     * <p>Deletes a specified resource collection.</p> <p>The response includes a <a href=\"/api-reference/ticketing/ticket-management/job_statuses/\">job status</a> for deletion of the collection's resources.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param resourceCollectionId <p>The id of the resource collection</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/resource_collections/{resource_collection_id}")
    Mono<@Valid JobStatusResponse> deleteResourceCollection(
        @PathVariable("resource_collection_id") @NotNull Long resourceCollectionId
    );

    /**
     * {@summary List Resource Collections}
     * <p>Lists resource collections for the account.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/resource_collections")
    Mono<@Valid ResourceCollectionsResponse> listResourceCollections();

    /**
     * {@summary Show Resource Collection}
     * <p>Retrieves details for a specified resource collection.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param resourceCollectionId <p>The id of the resource collection</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/resource_collections/{resource_collection_id}")
    Mono<@Valid ResourceCollectionResponse> retrieveResourceCollection(
        @PathVariable("resource_collection_id") @NotNull Long resourceCollectionId
    );

    /**
     * {@summary Update Resource Collection}
     * <p>Updates a resource collection using a provided <code>payload</code> object. The <code>payload</code> object  is specified the same way as the content of a requirements.json file in a Zendesk app. See <a href=\"/documentation/apps/app-developer-guide/apps_requirements/\">Specifying Apps Requirements</a> in the Zendesk Apps framework docs.</p> <p>The response includes a <a href=\"/api-reference/ticketing/ticket-management/job_statuses/\">job status</a> for the resource updates.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param resourceCollectionId <p>The id of the resource collection</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/resource_collections/{resource_collection_id}")
    Mono<@Valid JobStatusResponse> updateResourceCollection(
        @PathVariable("resource_collection_id") @NotNull Long resourceCollectionId
    );
}