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
import lol.pbu.z4j.model.ResolveOrganizationNames200Response;
import lol.pbu.z4j.model.ResolveOrganizationNames400Response;
import lol.pbu.z4j.model.ResolveOrganizationNames404Response;
import lol.pbu.z4j.model.ResolveOrganizationNames422Response;
import lol.pbu.z4j.model.ResolveOrganizationNamesRequest;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface InternalClient {

    /**
     * {@summary Resolve Organization Names to IDs (Internal)}
     * <p><strong>Internal API - For Zendesk services only</strong></p> <p>Batch resolve organization names to their corresponding IDs for a given account. Returns a structured response containing matched organization names with their IDs and a list of unmatched names.</p> <p>This endpoint is designed for efficient bulk lookups, avoiding N+1 queries when importing or processing multiple organization references.</p> <h4>Features</h4> <ul> <li>Case-insensitive matching (e.g., \"ACME\" matches \"Acme\")</li> <li>Whitespace normalization (leading/trailing spaces ignored)</li> <li>Preserves original input names in response keys</li> <li>Maximum 1,000 organization names per request</li> <li>Account-scoped results</li> </ul> <h4>Allowed For</h4> <ul> <li>Subsystem users: <code>support_importer</code></li> </ul> <h4>Use Cases</h4> <ul> <li>Bulk user imports with organization associations</li> <li>Data migration workflows</li> <li>Batch processing of organization references</li> </ul> <h4>Performance Considerations</h4> <ul> <li>Requests with &gt; 1,000 names will return 400 error</li> <li>Database query timeout will return 422 error</li> <li>Optimal performance with 100-500 names per request</li> </ul>
     *
     * @param resolveOrganizationNamesRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Bad Request - Invalid input or too many names</p> (status code 400)
     *         or <p>Not Found - Feature flag disabled</p> (status code 404)
     *         or <p>Unprocessable Entity - Request timeout</p> (status code 422)
     *         or <p>Internal Server Error</p> (status code 500)
     */
    @Post("/api/v2/internal/organizations/resolve_by_names")
    Mono<@Valid ResolveOrganizationNames200Response> resolveOrganizationNames(
        @Body @NotNull @Valid ResolveOrganizationNamesRequest resolveOrganizationNamesRequest
    );
}