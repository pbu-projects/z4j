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
import lol.pbu.z4j.model.SLAPoliciesResponse;
import lol.pbu.z4j.model.SLAPolicyFilterDefinitionResponse;
import lol.pbu.z4j.model.SLAPolicyResponse;
import static io.micronaut.core.convert.converters.MultiValuesConverterFactory.*;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SlaPoliciesClient {

    /**
     * {@summary Create SLA Policy}
     * <h4>Availability</h4> <ul> <li>Accounts on the Support Professional or Suite Growth plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/slas/policies")
    Mono<@Valid SLAPolicyResponse> createSLAPolicy();

    /**
     * {@summary Delete SLA Policy}
     * <h4>Availability</h4> <ul> <li>Accounts on the Support Professional or Suite Growth plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param slaPolicyId <p>The ID of the SLA Policy</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/slas/policies/{sla_policy_id}")
    Mono<Void> deleteSLAPolicy(
        @PathVariable("sla_policy_id") @NotNull Long slaPolicyId
    );

    /**
     * {@summary List SLA Policies}
     * <h4>Availability</h4> <ul> <li>Accounts on the Support Professional or Suite Growth plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/slas/policies")
    Mono<@Valid SLAPoliciesResponse> listSLAPolicies();

    /**
     * {@summary Reorder SLA Policies}
     * <h4>Availability</h4> <ul> <li>Accounts on the Support Professional or Suite Growth plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param slaPolicyIds <p>The IDs of the SLA Policies to reorder</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/slas/policies/reorder")
    Mono<@NotNull String> reorderSLAPolicies(
        @QueryValue("sla_policy_ids") @Nullable @Format(FORMAT_MULTI) List<@NotNull Long> slaPolicyIds
    );

    /**
     * {@summary Retrieve Supported Filter Definition Items}
     * <h4>Availability</h4> <ul> <li>Accounts on the Support Professional or Suite Growth plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/slas/policies/definitions")
    Mono<@Valid SLAPolicyFilterDefinitionResponse> retrieveSLAPolicyFilterDefinitionItems();

    /**
     * {@summary Show SLA Policy}
     * <h4>Availability</h4> <ul> <li>Accounts on the Support Professional or Suite Growth plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param slaPolicyId <p>The ID of the SLA Policy</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/slas/policies/{sla_policy_id}")
    Mono<@Valid SLAPolicyResponse> showSLAPolicy(
        @PathVariable("sla_policy_id") @NotNull Long slaPolicyId
    );

    /**
     * {@summary Update SLA Policy}
     * <p>Updates the specified policy.</p> <h4>Availability</h4> <ul> <li>Accounts on the Support Professional or Suite Growth plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param slaPolicyId <p>The ID of the SLA Policy</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/slas/policies/{sla_policy_id}")
    Mono<@Valid SLAPolicyResponse> updateSLAPolicy(
        @PathVariable("sla_policy_id") @NotNull Long slaPolicyId
    );
}