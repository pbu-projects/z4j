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
import lol.pbu.z4j.model.UserPasswordRequirementsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface UserPasswordsClient {

    /**
     * {@summary Change Your Password}
     * <p>You can only change your own password. Nobody can change the password of another user because it requires knowing the user's existing password. However, an admin can set a new password for another user without knowing the existing password. See <a href=\"#set-a-users-password\">Set a User's Password</a> above.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>End Users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success description</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/password")
    Mono<@NotNull String> changeOwnPassword(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary List password requirements}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> <li>End Users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/password/requirements")
    Mono<@Valid UserPasswordRequirementsResponse> getUserPasswordRequirements(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Set a User's Password}
     * <p>An admin can set a user's password only if the setting is enabled in Zendesk Support under <strong>Settings</strong> &gt; <strong>Security</strong> &gt; <strong>Global</strong>. The setting is off by default. Only the account owner can access and change this setting.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success description</p> (status code 200)
     */
    @Post("/api/v2/users/{user_id}/password")
    Mono<@NotNull String> setUserPassword(
        @PathVariable("user_id") @NotNull Long userId
    );
}