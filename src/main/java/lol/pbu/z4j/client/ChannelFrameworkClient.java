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
import lol.pbu.z4j.model.ChannelFrameworkPushResultsResponse;
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
public interface ChannelFrameworkClient {

    /**
     * {@summary Push Content to Support}
     * <p>Pushes Channel framework content to Zendesk.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Request parameters</h4> <p>The POST request takes a JSON object parameter which contains data about all the resources that the client is pushing.</p> <p>| Name               | Type      | Required  | Comments | ------------------ | ----------| --------- | ------------------- | instance_push_id   | string    | yes       | The account ID where data will be pushed. This was passed to the integration service when the administrator set up the account | request_id         | string    | no        | A unique identifier for the push request | external_resources | array     | yes       | The <a href=\"#external_resource-object\">resources</a> to push</p> <h4>external_resource object</h4> <p>| Name               | Type                               | Max length | Mandatory | Comments |------------------- | ---------------------------------- |------------| --------- | ---------- | external_id        | string                             | 255        | yes       | Unique identifier of the external resource. Must be ASCII characters | internal_note      | boolean                            |            | no        | If true creates a new internal note comment | message            | string                             | 65535      | yes       | Text to be converted to a ticket or comment | html_message       | string                             | 65535      | no        | HTML version of message | parent_id          | string                             | 511        | no        | Unique identifier of the external resource for which this is a response. Used to choose the correct thread. Responses may include <code>parent_id</code> or <code>thread_id</code>, but not both. See <a href=\"/documentation/channel_framework/understanding-the-channel-framework/pull_endpoint/#conversation-threads\">Conversation threads</a> | thread_id          | string                             | 255        | no        | Arbitrary identifier of the thread to which this item should belong. Responses may include <code>parent_id</code> or <code>thread_id</code>, but not both. See <a href=\"/documentation/channel_framework/understanding-the-channel-framework/pull_endpoint/#conversation-threads\">Conversation threads</a> | created_at         | string                             |            | yes       | When the resource was created in the origin system, as an ISO 8601 extended format date-time. Example: '2015-09-08T22:48:09Z' | author             | object                             |            | yes       | See <a href=\"#author-object\">author object</a> below | display_info       | array                              |            | no        | Array of integration-specific data used by apps to modify the agent UI. See <a href=\"#display_info-object\">display_info object</a> below | allow_channelback  | boolean                            |            | no        | If false, prevents the agent from making additional comments on the message in the Zendesk interface | fields             | array                              |            | no        | Array of ticket fields to set in Zendesk and their values. See <a href=\"#fields-array\">fields array</a> | file_urls          | array                              | 10         | no        | Array of files to be imported into Zendesk. See <a href=\"/documentation/channel_framework/understanding-the-channel-framework/pull_endpoint/#file-urls\">file urls</a> in the Channel framework docs</p> <h4>author object</h4> <p>| Name        | Type   | Max chars | Mandatory | Comments |------------ | ------ |---------- |---------- |----------- | external_id | string | 255       | yes       | Unique identifier of the user in the origin service | name        | string | 255       | no        | If not supplied, defaults to external id | image_url   | string | 255       | no        | URL to an image for the user | locale      | String | 255       | no        | The user's locale. Must be one of the supported <a href=\"/api-reference/ticketing/account-configuration/locales/#list-available-public-locales\">locales</a> in Zendesk | fields      | array  |           | no        | Array of items containing user field identifier ('id') and value of field ('value'.)  For system fields ('notes' or 'details'), the identifier is the English name. For custom fields, the identifier may be the ID or the name</p> <h4>display_info object</h4> <p>| Name | Type   | Max chars | Mandatory | Comments |----- | ------ |---------- |---------- |----------- | type | string | 255       | yes       | Globally unique type identifier defined by the integration origin service. Examples: a GUID or URI | data | string | 65535     | yes       | JSON data containing display hints</p> <h4>fields array</h4> <p>The <code>fields</code> array lists ticket fields to set in Zendesk and their values. Each item consists of a field identifier (<code>id</code>) and a value (<code>value</code>) for the field. For Zendesk system fields such as <code>subject</code>, the identifier is the English name. For custom fields, the identifier may be a field ID or a name. See <a href=\"/api-reference/ticketing/tickets/ticket_fields/\">Ticket Fields</a>.</p> <p>The <code>fields</code> array can only set ticket values on ticket creation, not on ticket updates.</p> <h4>Response format</h4> <p>The response is a JSON object containing a single key:</p> <p>| Name      | Type     | Comments | --------- | -------- | ------------------- | results   | array    | An array of <a href=\"#result-object\">result objects</a></p> <p>The <code>results</code> array contains an entry for each item in the incoming <code>external_resources</code> array, in the same order.  For example, if you call <code>push</code> with 3 external resources, a successful response will include <code>results</code> with three entries, corresponding to your 3 resources.</p> <h4>result object</h4> <p>| Name                 | Type                           | Comments | -------------------- | ------------------------------ | ------------------- | external_resource_id | string                         | The external ID of the resource, as passed in | status               | object                         | The status of the import for the indicated resource. See <a href=\"#status-object\">status object</a></p> <h4>status object</h4> <p>| Name        | Type   | Comments | ----------- | ------ | ------------------- | code        | string | A code indicating the status of the import of the resource, as described in <a href=\"#status-codes\">status codes</a> | description | string | In the case of an exception, a description of the exception. Otherwise, not present.</p> <h4>status codes</h4> <p>| Key                                       | Description | ----------------------------------------- | ---------------- | success                                   | The external resource was successfully converted to a ticket or comment | already_imported                          | Reimport of the external resource was skipped due to a pre-existing ticket or comment for the resource | could_not_locate_parent_external_resource | The parent resource, as identified by parent_id in the <a href=\"#request-parameters\">request</a>, could not be found. The unrecognized parent ID is returned in the description of the <a href=\"#status-object\">status</a> | processing_error                          | An internal exception occurred while processing the resource. See <code>description</code> in the <a href=\"#status-object\">status object</a> | halted                                    | This resource was not processed because processing of previous resources failed</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/any_channel/push")
    Mono<@Valid ChannelFrameworkPushResultsResponse> pushContentToSupport();

    /**
     * {@summary Report Channelback Error to Zendesk}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Request parameters</h4> <p>The POST request takes a JSON object parameter which contains information about the problematic <a href=\"/documentation/channel_framework/understanding-the-channel-framework/channelback/\">channelback</a>.</p> <p>| Name               | Type      | Required  | Comments | ------------------ | ----------| --------- | ------------------- | instance_push_id   | string    | yes       | The ID of the account to which data will be pushed.  This was passed to the integration service when the administrator set up the account | external_id        | string    | yes       | Unique identifier of the external resource from the original channelback (string) | description        | string    | no        | A human readable description of the error | request_id         | string    | no        | A unique identifier for the request</p> <h4>Response format</h4> <p>The response does not include a response body</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/any_channel/channelback/report_error")
    Mono<@NotNull String> reportChannelbackError();

    /**
     * {@summary Validate Token}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Request parameters</h4> <p>The POST request takes a JSON object parameter which contains the token to be validated.</p> <p>| Name               | Type      | Required  | Comments | ------------------ | ----------| --------- | ------------------- | instance_push_id   | string    | yes       | The ID of the account to which data will be pushed. This was passed to the integration service when the administrator set up the account | request_id         | string    | no        | A unique identifier for the push request</p> <h4>Response format</h4> <p>The response body is empty.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/any_channel/validate_token")
    Mono<@NotNull String> validateToken();
}