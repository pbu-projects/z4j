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
import lol.pbu.z4j.model.SharingAgreementResponse;
import lol.pbu.z4j.model.SharingAgreementsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SharingAgreementsClient {

    /**
     * {@summary Create Sharing Agreement}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/sharing_agreements")
    Mono<@Valid SharingAgreementResponse> createSharingAgreement();

    /**
     * {@summary Delete a Sharing Agreement}
     * <p>Deletes a sharing agreement.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param sharingAgreementId <p>The ID of the sharing agreement</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/sharing_agreements/{sharing_agreement_id}")
    Mono<Void> deleteSharingAgreement(
        @PathVariable("sharing_agreement_id") @NotNull Long sharingAgreementId
    );

    /**
     * {@summary List Sharing Agreements}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/sharing_agreements")
    Mono<@Valid SharingAgreementsResponse> listSharingAgreements();

    /**
     * {@summary Show a Sharing Agreement}
     * <p>Returns a sharing agreement for your account.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param sharingAgreementId <p>The ID of the sharing agreement</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/sharing_agreements/{sharing_agreement_id}")
    Mono<@Valid SharingAgreementResponse> showSharingAgreement(
        @PathVariable("sharing_agreement_id") @NotNull Long sharingAgreementId
    );

    /**
     * {@summary Update a Sharing Agreement}
     * <p>Returns an updated sharing agreement. Only <code>status</code> is allowed to be updated.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param sharingAgreementId <p>The ID of the sharing agreement</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/sharing_agreements/{sharing_agreement_id}")
    Mono<@Valid SharingAgreementResponse> updateSharingAgreement(
        @PathVariable("sharing_agreement_id") @NotNull Long sharingAgreementId
    );
}