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
import lol.pbu.z4j.model.EssentialsCardResponse;
import lol.pbu.z4j.model.EssentialsCardsResponse;
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
public interface EssentialsCardClient {

    /**
     * {@summary Delete Essentials Card}
     * <p>Delete the essentials card for an object type.</p> <h4>Allowed For</h4> <ul> <li>Admins and agents</li> </ul>
     *
     * @param objectType <p>Essentials card type. Example: <code>zen:user</code> refers user type</p> (required)
     *
     * @return <p>Success response</p> (status code 204)
     */
    @Delete("/api/v2/object_layouts/{object_type}/essentials_card")
    Mono<Void> deleteEssentialsCard(
        @PathVariable("object_type") @NotNull String objectType
    );

    /**
     * {@summary Show Essentials Card}
     * <p>Gets the essentials card for an object type.</p> <h4>Allowed For</h4> <ul> <li>Admins and agents</li> </ul>
     *
     * @param objectType <p>Essentials card type. Example: <code>zen:user</code> refers user type</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/object_layouts/{object_type}/essentials_card")
    Mono<@Valid EssentialsCardResponse> showEssentialsCard(
        @PathVariable("object_type") @NotNull String objectType
    );

    /**
     * {@summary List of Essentials Cards}
     * <p>Gets the list of essentials cards.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/object_layouts/essentials_cards")
    Mono<@Valid EssentialsCardsResponse> showEssentialsCards();

    /**
     * {@summary Update Essentials Card}
     * <p>Updates the essentials card for an object type.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param objectType <p>Essentials card type. Example: <code>zen:user</code> refers user type</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/object_layouts/{object_type}/essentials_card")
    Mono<@Valid EssentialsCardResponse> updateEssentialsCard(
        @PathVariable("object_type") @NotNull String objectType
    );
}