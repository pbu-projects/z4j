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

import io.micronaut.context.BeanProvider;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import jakarta.inject.Inject;
import lol.pbu.z4j.model.OAuthTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>OAuth Client Credentials Auth Filter.</h1>
 * <p>Exchanges client credentials for an access token, caches it in memory, and injects bearer auth headers.</p>
 *
 * @author Jonathan-Zollinger
 * @since 0.4.0
 */
@Context
@ClientFilter(value = "/**", serviceId = "zendesk")
@Requires(property = "micronaut.http.services.zendesk.oauth.client-id", pattern = "\\S+.*")
@Requires(missingBeans = BearerAuthFilter.class)
public class OAuthClientCredentialsAuthFilter {

    private static final Logger log = LoggerFactory.getLogger(OAuthClientCredentialsAuthFilter.class);
    private static final long REFRESH_WINDOW_SECONDS = 60L;

    private final String clientId;
    private final String clientSecret;
    private final String scope;
    private final BeanProvider<OAuthTokenClient> tokenClientProvider;
    private final OAuthTokenClient directClient;

    private final ReentrantLock lock = new ReentrantLock();
    private volatile String cachedToken;
    private volatile Instant tokenExpiration = Instant.MIN;

    @Inject
    public OAuthClientCredentialsAuthFilter(
            @Property(name = "micronaut.http.services.zendesk.oauth.client-id") String clientId,
            @Property(name = "micronaut.http.services.zendesk.oauth.client-secret") String clientSecret,
            @Property(name = "micronaut.http.services.zendesk.oauth.scope", defaultValue = "hc:read hc:write") String scope,
            BeanProvider<OAuthTokenClient> tokenClientProvider
    ) {
        if (clientSecret == null || clientSecret.trim().isEmpty()) {
            throw new IllegalArgumentException("Zendesk OAuth client-secret must not be blank when client-id is configured.");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.tokenClientProvider = tokenClientProvider;
        this.directClient = null;
    }

    public OAuthClientCredentialsAuthFilter(
            String clientId,
            String clientSecret,
            String scope,
            OAuthTokenClient directClient
    ) {
        if (clientSecret == null || clientSecret.trim().isEmpty()) {
            throw new IllegalArgumentException("Zendesk OAuth client-secret must not be blank when client-id is configured.");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.tokenClientProvider = null;
        this.directClient = directClient;
    }

    @RequestFilter
    public void doFilter(MutableHttpRequest<?> request) {
        String token = obtainAccessToken();
        request.getHeaders().set("Authorization", "Bearer " + token);
    }

    public String obtainAccessToken() {
        Instant now = Instant.now();
        if (cachedToken != null && now.isBefore(tokenExpiration)) {
            return cachedToken;
        }

        lock.lock();
        try {
            now = Instant.now();
            if (cachedToken != null && now.isBefore(tokenExpiration)) {
                return cachedToken;
            }

            log.debug("Requesting new Zendesk OAuth access token for client id: {}", clientId);
            OAuthTokenRequest req = new OAuthTokenRequest(clientId, clientSecret, scope);
            try {
                OAuthTokenResponse resp = resolveTokenClient().requestToken(req).block();
                if (resp == null || resp.getAccessToken() == null) {
                    throw new IllegalStateException("Received empty OAuth token response from Zendesk for client id: " + clientId);
                }
                cachedToken = resp.getAccessToken();
                long expiresIn = resp.getExpiresIn() != null ? resp.getExpiresIn() : 1800L;
                tokenExpiration = Instant.now().plusSeconds(Math.max(0, expiresIn - REFRESH_WINDOW_SECONDS));
                return cachedToken;
            } catch (HttpClientResponseException e) {
                int status = e.getStatus().getCode();
                if (status == 400 || status == 401) {
                    throw new IllegalStateException("Zendesk refused OAuth client credentials for client id '" + clientId + "': HTTP " + status);
                }
                throw e;
            }
        } finally {
            lock.unlock();
        }
    }

    private OAuthTokenClient resolveTokenClient() {
        return directClient != null ? directClient : tokenClientProvider.get();
    }
}
