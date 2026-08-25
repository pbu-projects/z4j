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
import lol.pbu.z4j.model.GroupSLAPoliciesResponse;
import lol.pbu.z4j.model.GroupSLAPolicyFilterDefinitionResponse;
import lol.pbu.z4j.model.GroupSLAPolicyResponse;
import reactor.core.publisher.Mono;
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
public interface GroupSlaPoliciesClient {

    /**
     * {@summary Create Group SLA Policy}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/group_slas/policies")
    Mono<@Valid GroupSLAPolicyResponse> createGroupSLAPolicy();

    /**
     * {@summary Delete Group SLA Policy}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param groupSlaPolicyId <p>The id of the Group SLA policy</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/group_slas/policies/{group_sla_policy_id}")
    Mono<Void> deleteGroupSLAPolicy(
        @PathVariable("group_sla_policy_id") @NotNull Integer groupSlaPolicyId
    );

    /**
     * {@summary List Group SLA Policies}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/group_slas/policies")
    Mono<@Valid GroupSLAPoliciesResponse> listGroupSLAPolicies();

    /**
     * {@summary Reorder Group SLA Policies}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param groupSlaPolicyIds <p>The ids of the Group SLA policies to reorder</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/group_slas/policies/reorder")
    Mono<@NotNull String> reorderGroupSLAPolicies(
        @QueryValue("group_sla_policy_ids") @Nullable @Format(FORMAT_MULTI) List<@NotNull String> groupSlaPolicyIds
    );

    /**
     * {@summary Retrieve Supported Filter Definition Items}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/group_slas/policies/definitions")
    Mono<@Valid GroupSLAPolicyFilterDefinitionResponse> retrieveGroupSLAPolicyFilterDefinitionItems();

    /**
     * {@summary Show Group SLA Policy}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param groupSlaPolicyId <p>The id of the Group SLA policy</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/group_slas/policies/{group_sla_policy_id}")
    Mono<@Valid GroupSLAPolicyResponse> showGroupSLAPolicy(
        @PathVariable("group_sla_policy_id") @NotNull Integer groupSlaPolicyId
    );

    /**
     * {@summary Update Group SLA Policy}
     * <p>Updates the specified policy.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param groupSlaPolicyId <p>The id of the Group SLA policy</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/group_slas/policies/{group_sla_policy_id}")
    Mono<@Valid GroupSLAPolicyResponse> updateGroupSLAPolicy(
        @PathVariable("group_sla_policy_id") @NotNull Integer groupSlaPolicyId
    );
}