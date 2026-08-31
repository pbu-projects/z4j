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
import lol.pbu.z4j.model.RelationshipFilterDefinitionResponse;
import lol.pbu.z4j.model.UsersResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface LookupRelationshipsClient {

    /**
     * {@summary Filter Definitions}
     * <p>Returns filter definitions based on the given target type.  Target types include users (zen:user), tickets (zen:ticket), organizations (zen:organization), or custom objects (zen:custom_object:CUSTOM_OBJECT_KEY). The returned filter definitions are the options that you can use to build a custom field or ticket field's <code>relationship_filter</code>.</p>
     *
     * @param targetType <p>The target type for which you would like to see filter definitions. The options are \"zen:user\", \"zen:ticket\", \"zen:organization\", and \"zen:custom_object:CUSTOM_OBJECT_KEY\"</p> (required)
     * @param sourceType <p>The source type for which you would like to see filter definitions. The options are \"zen:user\", \"zen:ticket\", and \"zen:organization\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/relationships/definitions/{target_type}")
    Mono<@Valid RelationshipFilterDefinitionResponse> getRelationshipFilterDefinitions(
        @PathVariable("target_type") @NotNull String targetType,
        @QueryValue("source_type") @Nullable String sourceType
    );

    /**
     * {@summary Get sources by target}
     * <p>Returns a list of source objects whose values are populated with the id of a related target object.  For example, if you have a lookup field called \"Success Manager\" on a ticket, this endpoint can answer the question, \"What tickets (sources) is this user (found by <code>target_type</code> and <code>target_id</code>) assigned as the 'Success Manager' (field referenced by <code>field_id</code>)?\"</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param targetType <p>The type of object the relationship field is targeting. The options are \"zen:user\", \"zen:ticket\", \"zen:organization\", and \"zen:custom_object:CUSTOM_OBJECT_KEY\"</p> (required)
     * @param targetId <p>The id of the object the relationship field is targeting</p> (required)
     * @param fieldId <p>The id of the lookup relationship field</p> (required)
     * @param sourceType <p>The type of object the relationship field belongs to (example. ticket field belongs to a ticket object). The options are \"zen:user\", \"zen:ticket\", \"zen:organization\", and \"zen:custom_object:CUSTOM_OBJECT_KEY\"</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/{target_type}/{target_id}/relationship_fields/{field_id}/{source_type}")
    Mono<@Valid UsersResponse> getSourcesByTarget(
        @PathVariable("target_type") @NotNull String targetType,
        @PathVariable("target_id") @NotNull Long targetId,
        @PathVariable("field_id") @NotNull Long fieldId,
        @PathVariable("source_type") @NotNull String sourceType
    );
}