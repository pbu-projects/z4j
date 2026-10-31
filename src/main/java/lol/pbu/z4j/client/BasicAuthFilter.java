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

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adds a Basic Auth header to outgoing Zendesk client requests when an email
 * and API token are configured and no OAuth bearer filter is active.
 *
 * @author Jonathan Zollinger
 * @since 0.0.1
 */
@ClientFilter(value = "/**", serviceId = "zendesk")
@Requires(property = "micronaut.http.services.zendesk.email", pattern = "\\S+.*")
@Requires(property = "micronaut.http.services.zendesk.token", pattern = "\\S+.*")
@Requires(missingBeans = {BearerAuthFilter.class, OAuthClientCredentialsAuthFilter.class})
public class BasicAuthFilter {

    private static final Logger log = LoggerFactory.getLogger(BasicAuthFilter.class);

    private final String email;
    private final String token;

    public BasicAuthFilter(@Property(name = "micronaut.http.services.zendesk.email") String email,
                           @Property(name = "micronaut.http.services.zendesk.token") String token) {
        this.email = email;
        this.token = token;
    }

    @RequestFilter
    public void doFilter(MutableHttpRequest<?> request) {
        if (request.getHeaders().getAuthorization().isEmpty()) {
            log.debug("Add basic auth header: Basic {}/token:<token>", email);
            request.basicAuth(email + "/token", token);
        }
    }
}
