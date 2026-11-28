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

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.http.client.ServiceHttpClientConfiguration;
import jakarta.inject.Singleton;
import java.time.Duration;

/**
 * Disables Micronaut's outer request-timeout wrapper for the {@code zendesk} service
 * unless explicitly configured, so rate-limit pauses inside {@link RateLimitFilter}
 * do not consume the socket {@code read-timeout}.
 */
@Singleton
public class ZendeskTimeoutCustomizer implements BeanCreatedEventListener<ServiceHttpClientConfiguration> {

    private static final String ZENDESK_SERVICE_ID = "zendesk";

    @Override
    public ServiceHttpClientConfiguration onCreated(BeanCreatedEvent<ServiceHttpClientConfiguration> event) {
        ServiceHttpClientConfiguration config = event.getBean();
        // A negative requestTimeout turns off the outer filter timeout while keeping the socket read timeout active.
        if (ZENDESK_SERVICE_ID.equals(config.getServiceId()) && config.getRequestTimeout() == null) {
            config.setRequestTimeout(Duration.ofSeconds(-1));
        }
        return config;
    }
}
