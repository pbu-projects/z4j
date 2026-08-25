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
import lol.pbu.z4j.model.AuditLogResponse;
import lol.pbu.z4j.model.AuditLogsResponse;
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
public interface AuditLogsClient {

    /**
     * {@summary Export Audit Logs}
     * <h4>Allowed For</h4> <ul> <li>Admins on accounts that have audit log access</li> </ul>
     *
     * @param filterSourceType <p>Filter audit logs by the source type. For example, user or rule</p> (optional)
     * @param filterSourceId <p>Filter audit logs by the source id. Requires <code>filter[source_type]</code> to also be set.</p> (optional)
     * @param filterActorId <p>Filter audit logs by the actor id</p> (optional)
     * @param filterIpAddress <p>Filter audit logs by the ip address</p> (optional)
     * @param filterCreatedAt <p>Filter audit logs by the time of creation. When used, you must specify <code>filter[created_at]</code> twice in your request, first with the start time and again with an end time</p> (optional)
     * @param filterAction <p>Filter audit logs by the action</p> (optional)
     *
     * @return <p>Accepted description</p> (status code 202)
     */
    @Post("/api/v2/audit_logs/export")
    Mono<@NotNull String> exportAuditLogs(
        @QueryValue("filter[source_type]") @Nullable String filterSourceType,
        @QueryValue("filter[source_id]") @Nullable Integer filterSourceId,
        @QueryValue("filter[actor_id]") @Nullable Integer filterActorId,
        @QueryValue("filter[ip_address]") @Nullable String filterIpAddress,
        @QueryValue("filter[created_at]") @Nullable String filterCreatedAt,
        @QueryValue("filter[action]") @Nullable String filterAction
    );

    /**
     * {@summary List Audit Logs}
     * <h4>Allowed For</h4> <ul> <li>Admins on accounts that have audit log access</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Filtering by multiple values</h4> <p>To filter by multiple values for the same field, repeat the filter parameter and append empty square brackets \"[]\" to the name of each repeated parameter. For example, to return audit logs where <code>action</code> is \"create\", \"update\", or \"destroy\":</p> <p><code>/api/v2/audit_logs.json?filter[action][]=create&amp;filter[action][]=update&amp;filter[action][]=destroy</code></p>
     *
     * @param filterSourceType <p>Filter audit logs by the source type. For example, user or rule</p> (optional)
     * @param filterSourceId <p>Filter audit logs by the source id. Requires <code>filter[source_type]</code> to also be set</p> (optional)
     * @param filterActorId <p>Filter audit logs by the actor id</p> (optional)
     * @param filterIpAddress <p>Filter audit logs by the ip address</p> (optional)
     * @param filterCreatedAt <p>Filter audit logs by the time of creation. When used, you must specify <code>filter[created_at]</code> twice in your request, first with the start time and again with an end time</p> (optional)
     * @param filterAction <p>Filter audit logs by the action</p> (optional)
     * @param sortBy <p>Offset pagination only. Sort audit logs. Default is <code>sort_by=created_at</code></p> (optional)
     * @param sortOrder <p>Offset pagination only. Sort audit logs. Default is <code>sort_order=desc</code></p> (optional)
     * @param sort <p>Cursor pagination only. Sort audit logs. Default is <code>sort=-created_at</code></p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/audit_logs")
    Mono<@Valid AuditLogsResponse> listAuditLogs(
        @QueryValue("filter[source_type]") @Nullable String filterSourceType,
        @QueryValue("filter[source_id]") @Nullable Integer filterSourceId,
        @QueryValue("filter[actor_id]") @Nullable Integer filterActorId,
        @QueryValue("filter[ip_address]") @Nullable String filterIpAddress,
        @QueryValue("filter[created_at]") @Nullable String filterCreatedAt,
        @QueryValue("filter[action]") @Nullable String filterAction,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder,
        @QueryValue("sort") @Nullable String sort
    );

    /**
     * {@summary Show Audit Log}
     * <h4>Allowed For</h4> <ul> <li>Admins on accounts that have audit-log access</li> </ul>
     *
     * @param auditLogId <p>The ID of the audit log</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/audit_logs/{audit_log_id}")
    Mono<@Valid AuditLogResponse> showAuditLog(
        @PathVariable("audit_log_id") @NotNull Integer auditLogId
    );
}