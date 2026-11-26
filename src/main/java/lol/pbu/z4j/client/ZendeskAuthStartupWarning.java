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

import io.micronaut.context.BeanContext;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs a startup warning when a Zendesk URL is configured without any active
 * authentication filter, so unsigned requests do not surprise callers later.
 */
@Singleton
public class ZendeskAuthStartupWarning {

    private static final Logger log = LoggerFactory.getLogger(ZendeskAuthStartupWarning.class);
    private static final List<String> URL_PROPERTIES = List.of(
            "micronaut.http.services.zendesk.url",
            "micronaut.http.services.zendesk.urls",
            "zendesk.url"
    );

    private final BeanContext beanContext;
    private final Environment environment;

    public ZendeskAuthStartupWarning(BeanContext beanContext, Environment environment) {
        this.beanContext = beanContext;
        this.environment = environment;
    }

    @EventListener
    public void onStartup(StartupEvent event) {
        checkAuthConfiguration();
    }

    void checkAuthConfiguration() {
        if (!hasZendeskUrl() || hasActiveAuthFilter()) {
            return;
        }
        log.warn("Zendesk URL is configured, but no authentication credentials are active. "
                + "Requests will be sent without an Authorization header and may fail with HTTP 401 or 403. "
                + "Configure one of: micronaut.http.services.zendesk.oauth.client-id + "
                + "micronaut.http.services.zendesk.oauth.client-secret, "
                + "micronaut.http.services.zendesk.oauth.token, or "
                + "micronaut.http.services.zendesk.email + micronaut.http.services.zendesk.token.");
    }

    private boolean hasZendeskUrl() {
        return URL_PROPERTIES.stream().anyMatch(this::hasNonEmptyProperty);
    }

    private boolean hasNonEmptyProperty(String propertyName) {
        return environment.getProperty(propertyName, Object.class)
                .map(value -> {
                    if (value instanceof Collection<?> items) {
                        return items.stream()
                                .anyMatch(item -> item != null && !item.toString().trim().isEmpty());
                    }
                    return !value.toString().trim().isEmpty();
                })
                .orElse(false);
    }

    private boolean hasActiveAuthFilter() {
        return beanContext.containsBean(BasicAuthFilter.class)
                || beanContext.containsBean(BearerAuthFilter.class)
                || beanContext.containsBean(OAuthClientCredentialsAuthFilter.class);
    }
}
