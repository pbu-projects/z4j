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
import lol.pbu.z4j.model.IncrementalSkillBasedRouting;
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
public interface IncrementalSkillBasedRoutingClient {

    /**
     * {@summary Incremental Attributes Values Export}
     * <p>Returns a stream of changes that occurred on routing attribute values.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Parameters</h4> <p>Optional</p> <p>| Name   | Type   | Comment | ------ | ------ | ------- | cursor | string | The <code>cursor</code> parameter is a non-human-readable argument you can use to move forward or backward in time. The cursor is a read-only URL parameter that's only available in API responses. See <a href=\"#pagination\">Pagination</a>.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/routing/attribute_values")
    Mono<@Valid IncrementalSkillBasedRouting> incrementalSkilBasedRoutingAttributeValuesExport();

    /**
     * {@summary Incremental Attributes Export}
     * <p>Returns a stream of changes that occurred on routing attributes.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Parameters</h4> <p>Optional</p> <p>| Name   | Type   | Comment | ------ | ------ | ------- | cursor | string | The <code>cursor</code> parameter is a non-human-readable argument you can use to move forward or backward in time. The cursor is a read-only URL parameter that's only available in API responses. See <a href=\"#pagination\">Pagination</a>.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/routing/attributes")
    Mono<@Valid IncrementalSkillBasedRouting> incrementalSkilBasedRoutingAttributesExport();

    /**
     * {@summary Incremental Instance Values Export}
     * <p>Returns a stream of changes that occurred on routing instance values. Changes are grouped by <code>attribute_value_id</code>, with associate type events listed alongside unassociate type events based on the unassociate event’s timestamp.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Parameters</h4> <p>Optional</p> <p>| Name   | Type   | Comment | ------ | ------ | ------- | cursor | string | The <code>cursor</code> parameter is a non-human-readable argument you can use to move forward or backward in time. The cursor is a read-only URL parameter that's only available in API responses. See <a href=\"#pagination\">Pagination</a>.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/routing/instance_values")
    Mono<@Valid IncrementalSkillBasedRouting> incrementalSkilBasedRoutingInstanceValuesExport();
}