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
import lol.pbu.z4j.model.ListEndUserIdentitiesTypeParameter;
import lol.pbu.z4j.model.ListUserIdentitiesTypeParameter;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.UserIdentitiesResponse;
import lol.pbu.z4j.model.UserIdentityResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface UserIdentitiesClient {

    /**
     * {@summary Create End User Identity}
     * <p>Adds an identity to an end user's profile.</p> <p>Supported identity types for end users:</p> <p>| Type             | Example | | ---------------- | ------- | | email            | <code>{ \"type\" : \"email\", \"value\" : \"someone@example.com\" }</code> | | phone_number     | <code>{ \"type\" : \"phone_number\", \"value\" : \"+1 555-123-4567\" }</code> |</p> <h4>Allowed For</h4> <ul> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param type <p>Filters results by one or more identity types using the format <code>?type[]={type}&amp;type[]={type}</code></p> (optional)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/end_users/{user_id}/identities")
    Mono<@Valid UserIdentityResponse> createEndUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("type[]") @Nullable ListEndUserIdentitiesTypeParameter type
    );

    /**
     * {@summary Create Identity}
     * <p>Adds an identity to a user's profile. An agent can add an identity to any user profile.</p> <p>Supported identity types:</p> <p>| Type             | Example | | ---------------- | ------- | | email            | <code>{ \"type\" : \"email\", \"value\" : \"someone@example.com\" }</code> | | twitter          | <code>{ \"type\" : \"twitter\", \"value\" : \"screen_name\" }</code> | | facebook         | <code>{ \"type\" : \"facebook\", \"value\" : \"855769377321\" }</code> | | google           | <code>{ \"type\" : \"google\", \"value\" : \"example@gmail.com\" }</code> | | agent_forwarding | <code>{ \"type\" : \"agent_forwarding\", \"value\" : \"+1 555-123-4567\" }</code> | | phone_number     | <code>{ \"type\" : \"phone_number\", \"value\" : \"+1 555-123-4567\" }</code> |</p> <p>To create an identity without sending out a verification email, include a <code>\"skip_verify_email\": true</code> property. The <code>\"skip_verify_email\": true</code> property does not apply when updating your own agent profile. A welcome or verification email will be sent regardless of this setting.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param type <p>Filters results by one or more identity types using the format <code>?type[]={type}&amp;type[]={type}</code></p> (optional)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/users/{user_id}/identities")
    Mono<@Valid UserIdentityResponse> createUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("type[]") @Nullable ListUserIdentitiesTypeParameter type
    );

    /**
     * {@summary Delete End User Identity}
     * <p>Deletes the identity for a given end user.</p> <p>In certain cases, a phone number associated with an identity is still visible on the user profile after the identity has been deleted via API.</p> <h4>Allowed For</h4> <ul> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/end_users/{user_id}/identities/{user_identity_id}")
    Mono<Void> deleteEndUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Delete Identity}
     * <p>Deletes the identity for a given user. In certain cases, a phone number associated with an identity is still visible on the user profile after the identity has been deleted via API. You can remove the phone number from the user profile by updating the <code>phone</code> attribute of the user to an empty string. See <a href=\"/api-reference/ticketing/users/users/#update-user\">Update User via API</a> for details and examples.</p> <p>Deleting identities with type <code>messaging</code> could break messaging functionality. For example, an agent may stop being able to send messages via the messaging channel.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/users/{user_id}/identities/{user_identity_id}")
    Mono<Void> deleteUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary List End User Identities}
     * <p>Returns a list of identities for the given end user.</p> <p>End users can only list email and phone number identities.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page for cursor pagination.</p> <h4>Allowed For</h4> <ul> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param type <p>Filters results by one or more identity types using the format <code>?type[]={type}&amp;type[]={type}</code></p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/end_users/{user_id}/identities")
    Mono<@Valid UserIdentitiesResponse> listEndUserIdentities(
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("type[]") @Nullable ListEndUserIdentitiesTypeParameter type
    );

    /**
     * {@summary List Identities}
     * <p>Returns a list of identities for the given user.</p> <p>Use the first endpoint if authenticating as an agent. Use the second if authenticating as an end user. End users can only list email and phone number identities.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page for cursor pagination.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param type <p>Filters results by one or more identity types using the format <code>?type[]={type}&amp;type[]={type}</code></p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/identities")
    Mono<@Valid UserIdentitiesResponse> listUserIdentities(
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("type[]") @Nullable ListUserIdentitiesTypeParameter type
    );

    /**
     * {@summary Make End User Identity Primary}
     * <p>Sets the specified identity as primary for the end user. This is a collection-level operation and the correct behavior for an API client is to subsequently reload the entire collection.</p> <p>An end user can only make an email identity primary if the email is verified.</p> <h4>Allowed For</h4> <ul> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/end_users/{user_id}/identities/{user_identity_id}/make_primary")
    Mono<@Valid UserIdentitiesResponse> makeEndUserIdentityPrimary(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Make Identity Primary}
     * <p>Sets the specified identity as primary. To change other attributes, use the <a href=\"#update-identity\">Update  Identity</a> endpoint. This is a collection-level operation and the correct behavior for an API client is to subsequently reload the entire collection.</p> <p>The first endpoint is the preferred option if authenticating as an agent. If authenticating as an end user, you can only use the second endpoint. In addition, an end user can only make an email identity primary if the email is verified.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/identities/{user_identity_id}/make_primary")
    Mono<@Valid UserIdentitiesResponse> makeUserIdentityPrimary(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Request End User Verification}
     * <p>Sends the end user a verification email with a link to verify ownership of the email address.</p> <h4>Allowed For</h4> <ul> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/end_users/{user_id}/identities/{user_identity_id}/request_verification")
    Mono<@NotNull String> requestEndUserVerification(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Request User Verification}
     * <p>Sends the user a verification email with a link to verify ownership of the email address.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success description</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/identities/{user_identity_id}/request_verification")
    Mono<@NotNull String> requestUserVerfication(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Show End User Identity}
     * <p>Shows the identity with the given id for a given end user.</p> <p>End users can only view email or phone number identity.</p> <h4>Allowed For</h4> <ul> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/end_users/{user_id}/identities/{user_identity_id}")
    Mono<@Valid UserIdentityResponse> showEndUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Show Identity}
     * <p>Shows the identity with the given id for a given user.</p> <p>Use the first endpoint if authenticating as an agent. Use the second if authenticating as an end user. End users can only view email or phone number identity.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>Verified end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/identities/{user_identity_id}")
    Mono<@Valid UserIdentityResponse> showUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Update Identity}
     * <p>This endpoint allows you to:</p> <ul> <li>Set the specified identity as verified (by setting <code>verified</code> to \"true\" or <code>verification_method</code> to \"low\")</li> <li>Unverify a verified identity (by setting <code>verified</code> to \"false\" or <code>verification_method</code> to \"none\")</li> <li>Update the <code>value</code> property of the specified identity</li> </ul> <p>You can't change an identity's <code>primary</code> attribute with this endpoint. You must use the <a href=\"#make-identity-primary\">Make Identity Primary</a> endpoint instead.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/identities/{user_identity_id}")
    Mono<@Valid UserIdentityResponse> updateUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );

    /**
     * {@summary Verify Identity}
     * <p>Sets the specified identity as verified.</p> <p>For security reasons, you can't use this endpoint to update the email identity of the account owner. To verify the person's identity, send a verification email. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4408828975130\">Verifying the account owner's email address</a> in Zendesk help.</p> <p>If <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882246298-Creating-organizations#topic_nxl_vdt_bc\">automatic mapping of users to organizations using the email domain</a> is enabled and the user is not already a member of an organization, they will be automatically added to the organization associated with the email domain once the email identity is verified.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userIdentityId <p>The ID of the user identity</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/identities/{user_identity_id}/verify")
    Mono<@Valid UserIdentityResponse> verifyUserIdentity(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("user_identity_id") @NotNull Integer userIdentityId
    );
}