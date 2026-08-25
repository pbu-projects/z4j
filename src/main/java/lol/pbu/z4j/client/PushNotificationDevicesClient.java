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
import lol.pbu.z4j.model.PushNotificationDevicesRequest;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface PushNotificationDevicesClient {

    /**
     * {@summary Bulk Unregister Push Notification Devices}
     * <p>Unregisters the mobile devices that are receiving push notifications. Specify the devices as an array of mobile device tokens.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param pushNotificationDevicesRequest (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Post("/api/v2/push_notification_devices/destroy_many")
    Mono<@NotNull String> pushNotificationDevices(
        @Body @Nullable @Valid PushNotificationDevicesRequest pushNotificationDevicesRequest
    );
}