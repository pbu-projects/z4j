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
import lol.pbu.z4j.model.LoginPostUserParameter;
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
public interface AccessClient {

    /**
     * {@summary Login}
     * <p>Tries to log in with zendesk_o_auth, basic, and challenge_token strategies (default Warden strategies)</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Get("/access/login")
    Mono<Void> loginGet();

    /**
     * {@summary Login with password}
     * <p>Logins with email and password and redirects</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param user <p>Provides user email and password for password login</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Post("/access/login")
    Mono<Void> loginPost(
        @PathVariable("user") @NotNull @Valid LoginPostUserParameter user
    );
}