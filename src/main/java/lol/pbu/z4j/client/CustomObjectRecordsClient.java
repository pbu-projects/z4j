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
import lol.pbu.z4j.model.CountCustomObjectRecords200Response;
import lol.pbu.z4j.model.CustomObjectLimitsResponse;
import lol.pbu.z4j.model.CustomObjectRecordResponse;
import lol.pbu.z4j.model.CustomObjectRecordsBulkCreateRequest;
import lol.pbu.z4j.model.CustomObjectRecordsCreateRequest;
import lol.pbu.z4j.model.CustomObjectRecordsJobsResponse;
import lol.pbu.z4j.model.CustomObjectRecordsResponse;
import lol.pbu.z4j.model.CustomObjectRecordsUpsertRequest;
import lol.pbu.z4j.model.FilteredSearchCustomObjectRecordsRequest;
import lol.pbu.z4j.model.IncrementalCustomObjectRecordExportCursor400Response;
import lol.pbu.z4j.model.IncrementalCustomObjectRecordsResponse;
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
public interface CustomObjectRecordsClient {

    /**
     * {@summary Autocomplete Custom Object Record Search}
     * <p>Retrieves an array of custom object records that have a field value that matches the value specified in the <code>name</code> parameter.</p> <h4>Pagination</h4> <ul> <li><a href=\"/api-reference/introduction/pagination/#cursor-pagination\">Cursor pagination</a> only.</li> <li>Returns the first 10,000 records sorted by relevancy with page limits.</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param name <p>Part of a name of the record you are searching for</p> (optional)
     * @param pageBefore <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.before_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageAfter <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.after_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageSize <p>The number of records to return in the response. You can specify up to 100 records per page.</p> (optional)
     * @param fieldId <p>The id of the lookup field. If the field has a relationship filter, the filter is applied to the results. Must be used with <code>source</code> param.</p> (optional)
     * @param source <p>One of \"zen:user\", \"zen:ticket\", \"zen:organization\", or \"zen:custom_object:CUSTOM_OBJECT_KEY\". Represents the object <code>field_id</code> belongs to. Must be used with field_id param.</p> (optional)
     * @param filterDynamicValues <p>Provided values to be used with <a href=\"/api-reference/ticketing/lookup_relationships/lookup_relationships/#using-dynamic-filters\">dynamic filters</a>.</p> (optional)
     * @param requesterId <p>The id of the requester. For use with dynamic filters.</p> (optional)
     * @param assigneeId <p>The id of the selected assignee. For use with dynamic filters.</p> (optional)
     * @param organizationId <p>The id of the organization the requester belongs to. For use with dynamic filters.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/autocomplete")
    Mono<@Valid CustomObjectRecordsResponse> autocompleteCustomObjectRecordSearch(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("name") @Nullable String name,
        @QueryValue("page[before]") @Nullable String pageBefore,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable Long pageSize,
        @QueryValue("field_id") @Nullable String fieldId,
        @QueryValue("source") @Nullable String source,
        @QueryValue("filter[dynamic_values]") @Nullable @Format(FORMAT_DEEP_OBJECT) Map<String, @NotNull Long> filterDynamicValues,
        @QueryValue("requester_id") @Nullable Long requesterId,
        @QueryValue("assignee_id") @Nullable Long assigneeId,
        @QueryValue("organization_id") @Nullable Long organizationId
    );

    /**
     * {@summary Count Custom Object Records}
     * <p>Returns a total count of records for a specific custom object as well as the time the count was refreshed.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/count")
    Mono<@Valid CountCustomObjectRecords200Response> countCustomObjectRecords(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary Create Custom Object Record}
     * <p>Creates a custom object record according to all the properties described by a custom object definition. If <code>autoincrement_enabled</code> is true, record names aren't allowed in the request body because they are generated automatically. If <code>is_unique</code> is true, record names must be unique.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectRecordsCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/records")
    Mono<@Valid CustomObjectRecordResponse> createCustomObjectRecord(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @Body @Nullable @Valid CustomObjectRecordsCreateRequest customObjectRecordsCreateRequest
    );

    /**
     * {@summary Custom Object Record Bulk Jobs}
     * <p>Queues a background job to perform bulk actions on up to 100 custom object records per single request. Takes a <code>job</code> object with two nested fields: * <code>action</code>, one of:     * <code>\"create\"</code>     * <code>\"delete\"</code>     * <code>\"delete_by_external_id\"</code>     * <code>\"create_or_update_by_external_id\"</code>     * <code>\"create_or_update_by_name\"</code>     * <code>\"update\"</code> * <code>items</code>     * For a <code>\"create\"</code> action, an array of JSON objects representing the custom object records being created     * For a <code>\"delete\"</code> action, an array of strings representing Zendesk record ids     * For a <code>\"delete_by_external_id\"</code> action, an array of strings representing external ids     * For a <code>\"create_or_update_by_external_id\"</code> action, an array of JSON objects representing the custom object records being created or updated by external id     * For a <code>\"create_or_update_by_name\"</code> action, an array of JSON objects representing the custom object records being created or updated by name. The <code>is_unique</code> property on the custom object's name field must be enabled.     * For an <code>\"update\"</code> action, an array of JSON objects representing the custom object records being updated</p> <p>Note: If autonumbering is selected for the custom object's name field, record names aren't allowed in the request body because they are generated automatically. If uniqueness is enabled, the record names must be unique.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectRecordsBulkCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/jobs")
    Mono<@Valid CustomObjectRecordsJobsResponse> customObjectRecordBulkJobs(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @Body @Nullable @Valid CustomObjectRecordsBulkCreateRequest customObjectRecordsBulkCreateRequest
    );

    /**
     * {@summary Custom Object Records Limit}
     * <p>List the current count and the limit for custom object records</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/limits/record_limit")
    Mono<@Valid CustomObjectLimitsResponse> customObjectRecordsLimit();

    /**
     * {@summary Delete Custom Object Record}
     * <p>Deletes a record with the specified id</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectRecordId <p>The id of a custom object record</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}")
    Mono<Void> deleteCustomObjectRecord(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("custom_object_record_id") @NotNull String customObjectRecordId
    );

    /**
     * {@summary Delete Custom Object Record by External Id Or Name}
     * <p>Deletes a record with the specified external id or name. The <code>is_unique</code> property on the custom object's name field must be enabled in order to delete by name. External id and name cannot be used together in the same request.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param externalId <p>The external id of a custom object record</p> (required)
     * @param name <p>The name of a custom object record</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/records")
    Mono<Void> deleteCustomObjectRecordByExternalIdOrName(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("external_id") @NotNull String externalId,
        @QueryValue("name") @NotNull String name
    );

    /**
     * {@summary Filtered Search of Custom Object Records}
     * <p>Returns an array of custom object records that meet the search and filter criteria. For simple searches limited to only text fields and no complex logic, use the <a href=\"/api-reference/custom-data/custom-objects/custom_object_records/#search-custom-object-records\">Search Custom Object Records API</a> endpoint.</p> <p>Filters can contain either an individual <a href=\"#comparison-object\">comparison object</a> or an array of <a href=\"#comparison-object\">comparison objects</a> within logical namespaces.</p> <p>A filter is a JSON object that has the following properties:</p> <p>| Name      | Type   | Required | Description | --------- | ------ | -------- | ----------- | ATTRIBUTE | object | no       | A <a href=\"#comparison-object\">comparison object</a> specifying an attribute value condition to be met for records to match.<br/><br/>Examples are marked below. | $and      | array  | no       | Array of conjunctive filter objects (logical AND) | $or       | array  | no       | Array of conjunctive filter objects (logical OR)</p> <h5>Examples</h5> <p><code>js {   \"filter\": {     \"custom_object_fields.field_key\": { \"$eq\": \"value\" } // ATTRIBUTE   } }</code></p> <p><code>js // $or {   \"filter\": {     \"$or\": [       { \"custom_object_fields.field_key\": { \"$eq\": \"value\" } }, // ATTRIBUTE       { \"external_id\": { \"$eq\": \"Record123\" } } // ATTRIBUTE     ]   } }</code></p> <h4>Comparison Object</h4> <p>A comparison object defines a condition a record must meet to be considered a match. The condition is based on an attribute value or object type.</p> <p>A comparison object is a JSON object that has the following properties:</p> <p>| Name      | Type          | Required | Description | --------- | ------------- | -------- | ----------- | FIELD_KEY | string        | yes      | When filtering on a custom field, they must be namedspaced with <code>custom_object_fields.</code>. ex. <code>custom_object_fields.field_key</code><br/><br/>When filtering on a standard field, no namespace is required. The following fields are considered standard: <code>created_at</code>, <code>updated_at</code>, <code>created_by_user</code>, <code>updated_by_user</code>, <code>name</code>, <code>external_id</code> | OPERATOR  | string        | yes      | <a href=\"/documentation/custom-data/v2/searching-custom-object-records/\">Supported operators</a> vary by the value's data type | VALUE     | string, array | yes      | The value you're filtering for</p> <ul> <li>Date values should be in <a href=\"https://en.wikipedia.org/wiki/ISO_8601\">ISO 8601</a> format.</li> </ul> <h4>Pagination</h4> <ul> <li><a href=\"/api-reference/introduction/pagination/#cursor-pagination\">Cursor pagination</a> only.</li> <li>Returns the records sorted by relevancy with page limits. Without a <code>sort</code> parameter, only the first 10,000 records are returned. With a <code>sort</code> parameter, all records are returned.</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>End users (when an admin <a href=\"https://support.zendesk.com/hc/en-us/articles/6034260247066\">configures</a> the custom object to be accessible to end users)</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param query <p>The query parameter is used to search text-based fields for records that match specific query terms. The query can be multiple words or numbers. Every record that matches the beginning of any word or number in the query string is returned.<br/><br/></p> <p>Fuzzy search is supported for the following text-based field types: Text fields, Multi Line Text fields, and RegExp fields.<br/><br/></p> <p>For example, you might want to search for records related to Tesla vehicles: <code>query=Tesla</code>. In this example the API would return every record for the given custom object where any of the supported text fields contain the word 'Tesla'.<br/><br/></p> <p>You can include multiple words or numbers in your search. For example: <code>query=Tesla Honda 2020</code>. This search phrase would be URL encoded as <code>query=Tesla%20Honda%202020</code> and return every record for the custom object for which any of the supported text fields contained 'Tesla', 'Honda', or '2020'.</p> (optional)
     * @param sort <p>One of \"name\", \"created_at\", \"updated_at\", \"-name\", \"-created_at\", or \"-updated_at\". The \"-\" denotes the sort will be descending. Defaults to sorting by relevance.</p> (optional)
     * @param pageBefore <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.before_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageAfter <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.after_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageSize <p>Specifies how many records should be returned in the response. You can specify up to 100 records per page.</p> (optional)
     * @param filteredSearchCustomObjectRecordsRequest (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/records/search")
    Mono<@Valid CustomObjectRecordsResponse> filteredSearchCustomObjectRecords(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("query") @Nullable String query,
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("page[before]") @Nullable String pageBefore,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable Long pageSize,
        @Body @Nullable @Valid FilteredSearchCustomObjectRecordsRequest filteredSearchCustomObjectRecordsRequest
    );

    /**
     * {@summary Incremental Custom Object Record Export, Cursor Based}
     * <p>Returns the custom object records that changed since the start time. This endpoint supports  cursor-based incremental exports for custom object records.</p> <p>This endpoint only supports cursor-based pagination and does not support offset-based pagination. Cursor-based exports provide more consistent performance and response body sizes. For more information,  see <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#cursor-based-incremental-exports\">Cursor-based incremental exports</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents with custom object read permissions</li> </ul> <h4>Rate Limiting</h4> <p>You can make up to 10 requests per minute to this endpoint.</p> <h4>Notes</h4> <ul> <li><code>start_time</code> is only required for the initial request for the pages in the record set, then <code>cursor</code> is required for all subsequent requests</li> <li>The <code>start_time</code> must be more than 60 seconds ago</li> <li>Deleted records will have their field values replaced with \"[DELETED]\" unless excluded via filter</li> <li>Photo fields are excluded from incremental export responses</li> </ul>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param customObjectKey <p>The key identifier for the custom object</p> (required)
     * @param cursor <p>The cursor pointer to work with for all subsequent exports after the initial request</p> (optional)
     * @param perPage <p>Number of records to return per page (default 1000, maximum 1000)</p> (optional, default to 1000)
     * @param filterExcludeDeleted <p>If true, exclude deleted records from the export</p> (optional, default to false)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Bad request - Invalid parameters</p> (status code 400)
     *         or <p>Unauthorized - Authentication required</p> (status code 401)
     *         or <p>Forbidden - Insufficient permissions to access custom objects</p> (status code 403)
     *         or <p>Not found - Custom object not found or feature not enabled</p> (status code 404)
     *         or <p>Too many requests - Rate limit exceeded</p> (status code 429)
     */
    @Get("/api/v2/incremental/custom_objects/{custom_object_key}/cursor")
    Mono<@Valid IncrementalCustomObjectRecordsResponse> incrementalCustomObjectRecordExportCursor(
        @QueryValue("start_time") @NotNull Long startTime,
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("cursor") @Nullable String cursor,
        @QueryValue(value = "per_page", defaultValue = "1000") @Nullable @Min(1) @Max(1000) Long perPage,
        @QueryValue(value = "filter[exclude_deleted]", defaultValue = "false") @Nullable Boolean filterExcludeDeleted
    );

    /**
     * {@summary List Custom Object Records}
     * <p>Lists all undeleted custom object records for the specified object</p> <p>#### Pagination</p> <ul> <li><a href=\"/api-reference/introduction/pagination/#cursor-pagination\">Cursor pagination</a> only.</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param filterIds <p>Optional comma-separated list of ids to filter records by. If one or more ids are specified, only matching records are returned. The ids must be unique and are case sensitive.</p> (optional)
     * @param filterExternalIds <p>Optional comma-separated list of external ids to filter records by. If one or more ids are specified, only matching records are returned. The ids must be unique and are case sensitive.</p> (optional)
     * @param sort <p>One of <code>id</code>, <code>updated_at</code>, <code>-id</code>, or <code>-updated_at</code>. The <code>-</code> denotes the sort will be descending.</p> (optional)
     * @param pageBefore <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.before_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageAfter <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.after_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageSize <p>Specifies how many records should be returned in the response. You can specify up to 100 records per page.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records")
    Mono<@Valid CustomObjectRecordsResponse> listCustomObjectRecords(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("filter[ids]") @Nullable String filterIds,
        @QueryValue("filter[external_ids]") @Nullable String filterExternalIds,
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("page[before]") @Nullable String pageBefore,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable Long pageSize
    );

    /**
     * {@summary Search Custom Object Records}
     * <p>Returns an array of custom object records where the search query matches the values in Text Fields, Multi Line Text fields, and RegExp fields. To find records in other fields, use the <a href=\"/api-reference/custom-data/custom-objects/custom_object_records/#filtered-search-of-custom-object-records\">Filtered Search API</a> endpoint.</p> <h4>Pagination</h4> <ul> <li><a href=\"/api-reference/introduction/pagination/#cursor-pagination\">Cursor pagination</a> only.</li> <li>Returns the records sorted by relevancy with page limits. Without a <code>sort</code> parameter, only the first 10,000 records are returned. With a <code>sort</code> parameter, all records are returned.</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param query <p>The query parameter is used to search text-based fields for records that match specific query terms. The query can be multiple words or numbers. Every record that matches the beginning of any word or number in the query string is returned.<br/><br/></p> <p>Fuzzy search is supported for the following text-based field types: Text fields, Multi Line Text fields, and RegExp fields.<br/><br/></p> <p>For example, you might want to search for records related to Tesla vehicles: <code>query=Tesla</code>. In this example the API would return every record for the given custom object where any of the supported text fields contain the word 'Tesla'.<br/><br/></p> <p>You can include multiple words or numbers in your search. For example: <code>query=Tesla Honda 2020</code>. This search phrase would be URL encoded as <code>query=Tesla%20Honda%202020</code> and return every record for the custom object for which any of the supported text fields contained 'Tesla', 'Honda', or '2020'.</p> (optional)
     * @param sort <p>One of <code>name</code>, <code>created_at</code>, <code>updated_at</code>, <code>-name</code>, <code>-created_at</code>, or <code>-updated_at</code>. The <code>-</code> denotes the sort will be descending. Defaults to sorting by relevance.</p> (optional)
     * @param pageBefore <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.before_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageAfter <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.after_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageSize <p>Specifies how many records should be returned in the response. You can specify up to 100 records per page.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/search")
    Mono<@Valid CustomObjectRecordsResponse> searchCustomObjectRecords(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("query") @Nullable String query,
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("page[before]") @Nullable String pageBefore,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable Long pageSize
    );

    /**
     * {@summary Show Custom Object Record}
     * <p>Returns a custom record for a specific object using a provided id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectRecordId <p>The id of a custom object record</p> (required)
     *
     * @return <p>Custom Object Record</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}")
    Mono<@Valid CustomObjectRecordResponse> showCustomObjectRecord(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("custom_object_record_id") @NotNull String customObjectRecordId
    );

    /**
     * {@summary Update Custom Object Record}
     * <p>Updates an individual custom object record. The updating rules are as follows: * Takes a <code>custom_object_record</code> object that specifies the properties to update * The custom object fields should be nested inside a <code>custom_object_fields</code> object</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectRecordId <p>The id of a custom object record</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}")
    Mono<@Valid CustomObjectRecordResponse> updateCustomObjectRecord(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("custom_object_record_id") @NotNull String customObjectRecordId
    );

    /**
     * {@summary Create or Update Custom Object Record}
     * <p>Creates or updates a custom object record based on the provided external id or name. If a record exists for the given external id or name, updates it. Only the specified attributes are updated. Otherwise, creates a new record with the provided external id, name and other attributes. The <code>is_unique</code> property on the custom object's name field must be enabled in order to update or create by name. External id and name cannot be used together in the same request.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param externalId <p>The external id of a custom object record</p> (required)
     * @param name <p>The name of a custom object record</p> (required)
     * @param customObjectRecordsUpsertRequest (optional)
     *
     * @return <p>Success</p> (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}/records")
    Mono<@Valid CustomObjectRecordResponse> upsertCustomObjectRecordByExternalIdOrName(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("external_id") @NotNull String externalId,
        @QueryValue("name") @NotNull String name,
        @Body @Nullable @Valid CustomObjectRecordsUpsertRequest customObjectRecordsUpsertRequest
    );
}