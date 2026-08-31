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
import lol.pbu.z4j.model.SupportAddressResponse;
import lol.pbu.z4j.model.SupportAddressesResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SupportAddressesClient {

    /**
     * {@summary Create Support Address}
     * <p>Adds a Zendesk or external support address to your account.</p> <p>To add a Zendesk address, use the following syntax: <code>{local-part}@{accountname}.zendesk.com</code>. Example: 'sales-team@example.zendesk.com'. The <a href=\"https://en.wikipedia.org/wiki/Email_address#Local-part\">local-part</a> can be anything you like.</p> <p>To add an external email address such as help@omniwearshop.com, the email must already exist and you must set up forwarding on your email server. The exact steps depend on your mail server. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203663266\">Forwarding incoming email to Zendesk Support</a>. After setting up forwarding, run the <a href=\"#verify-support-address-forwarding\">Verify Support Address Forwarding</a> endpoint. The address won't work in Zendesk Support until it's been verified.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents with permission to manage channels and extensions. See the system permissions in <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026-Creating-custom-roles-and-assigning-agents-Enterprise-#topic_cxn_hig_bd\">Creating custom roles and assigning agents (Enterprise)</a> in the Support Help Center</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/recipient_addresses")
    Mono<@Valid SupportAddressResponse> createSupportAddress();

    /**
     * {@summary Delete Support Address}
     * <p>Deletes a support address.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents with permission to manage channels and extensions. See the system permissions in <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026-Creating-custom-roles-and-assigning-agents-Enterprise-#topic_cxn_hig_bd\">Creating custom roles and assigning agents (Enterprise)</a> in the Support Help Center</li> </ul>
     *
     * @param supportAddressId <p>The ID of the support address</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/recipient_addresses/{support_address_id}")
    Mono<Void> deleteRecipientAddress(
        @PathVariable("support_address_id") @NotNull Long supportAddressId
    );

    /**
     * {@summary List Support Addresses}
     * <p>Lists all the support addresses for the account.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/recipient_addresses")
    Mono<@Valid SupportAddressesResponse> listSupportAddresses();

    /**
     * {@summary Show Support Address}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param supportAddressId <p>The ID of the support address</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/recipient_addresses/{support_address_id}")
    Mono<@Valid SupportAddressResponse> showSupportAddress(
        @PathVariable("support_address_id") @NotNull Long supportAddressId
    );

    /**
     * {@summary Update Support Address}
     * <p>Updates an existing support address for your account.</p> <p>You can't use this endpoint to update a support address's <code>email</code> property. Instead, you can create a new address using the <a href=\"#create-support-address\">Create Support Address</a> endpoint.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents with permission to manage channels and extensions. See the system permissions in <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026-Creating-custom-roles-and-assigning-agents-Enterprise-#topic_cxn_hig_bd\">Creating custom roles and assigning agents (Enterprise)</a> in the Support Help Center</li> </ul>
     *
     * @param supportAddressId <p>The ID of the support address</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/recipient_addresses/{support_address_id}")
    Mono<@Valid SupportAddressResponse> updateSupportAddress(
        @PathVariable("support_address_id") @NotNull Long supportAddressId
    );

    /**
     * {@summary Verify Support Address Forwarding}
     * <p>Sends a test email to the specified support address to verify that email forwarding for the address works. An external support address won't work in Zendesk Support until it's verified.</p> <p><strong>Note</strong>: You don't need to verify Zendesk system support addresses.</p> <p>The endpoint takes the following body: <code>{\"type\": \"forwarding\"}</code>. The value of the <code>type</code> property defaults to \"forwarding\" if none is specified, but the values \"spf\" and \"dns\" are also accepted.</p> <p>Use this endpoint after <a href=\"#create-support-address\">adding</a> an external support address to Zendesk Support and setting up forwarding on your email server. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203663266\">Forwarding incoming email to Zendesk Support</a>.</p> <p>The endpoint doesn't return the results of the test. Instead, use the <a href=\"#show-support-address\">Show Support Address</a> endpoint to check that the <code>forwarding_status</code> property is \"verified\".</p> <p>Other verification checks can also be performed using this API. These include SPF checks and DNS checks.</p> <p>When calling the endpoint with <code>type</code> set to \"spf\", it will queries the DNS records to check that the SPF records for Zendesk are present for outbound emails.</p> <p>When calling the endpoint with <code>type</code> set to \"dns\", it runs checks on your CNAME records to make sure they are set up properly in your DNS.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents with permission to manage channels and extensions. See the system permissions in <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026-Creating-custom-roles-and-assigning-agents-Enterprise-#topic_cxn_hig_bd\">Creating custom roles and assigning agents (Enterprise)</a> in the Support Help Center</li> </ul>
     *
     * @param supportAddressId <p>The ID of the support address</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/recipient_addresses/{support_address_id}/verify")
    Mono<@NotNull String> verifySupportAddressForwarding(
        @PathVariable("support_address_id") @NotNull Long supportAddressId
    );
}