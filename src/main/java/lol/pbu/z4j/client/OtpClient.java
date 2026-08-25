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
import lol.pbu.z4j.model.OtpSettingResponse;
import lol.pbu.z4j.model.UpdateOtpSettingOneTimePasswordParameter;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OtpClient {

    /**
     * {@summary Destroy OTP setting}
     * <p>Removes current user OTP setting</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/auth/api/one_time_password")
    Mono<Void> removeOtpSetting();

    /**
     * {@summary Show OTP setting}
     * <p>Presents current user OTP setting</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>Current user OTP setting response</p> (status code 200)
     */
    @Get("/auth/api/one_time_password")
    Mono<@Valid OtpSettingResponse> showOtpSetting();

    /**
     * {@summary Update OTP setting}
     * <p>Updates current user OTP setting</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param oneTimePassword <p>Provides configuration for user OTP setting</p> (required)
     *
     * @return <p>Current user OTP setting response</p> (status code 200)
     */
    @Patch("/auth/api/one_time_password")
    Mono<@Valid OtpSettingResponse> updateOtpSetting(
        @QueryValue("one_time_password") @NotNull @Valid UpdateOtpSettingOneTimePasswordParameter oneTimePassword
    );
}