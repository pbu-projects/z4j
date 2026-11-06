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
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import lol.pbu.z4j.model.OAuthTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

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
    private static final String DEFAULT_URL = "https://localhost";

    private final String clientId;
    private final String clientSecret;
    private final String scope;
    private final String tokenEndpointUrl;
    private final BeanProvider<OAuthTokenClient> tokenClientProvider;
    private final OAuthTokenClient directClient;

    private record CachedToken(String token, Instant expiresAt) {}

    private final AtomicReference<Mono<String>> inFlightRefresh = new AtomicReference<>();
    private final AtomicReference<CachedToken> cachedToken = new AtomicReference<>();

    @Inject
    public OAuthClientCredentialsAuthFilter(
            @Property(name = "micronaut.http.services.zendesk.oauth.client-id") String clientId,
            @Property(name = "micronaut.http.services.zendesk.oauth.client-secret") String clientSecret,
            @Property(name = "micronaut.http.services.zendesk.oauth.scope", defaultValue = "hc:read hc:write") String scope,
            @Property(name = "micronaut.http.services.zendesk.url", defaultValue = DEFAULT_URL) String zendeskUrl,
            BeanProvider<OAuthTokenClient> tokenClientProvider
    ) {
        if (clientSecret == null || clientSecret.trim().isEmpty()) {
            throw new IllegalArgumentException("Zendesk OAuth client-secret must not be blank when client-id is configured.");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.tokenEndpointUrl = formatTokenEndpointUrl(zendeskUrl);
        this.tokenClientProvider = tokenClientProvider;
        this.directClient = null;
    }

    public OAuthClientCredentialsAuthFilter(
            String clientId,
            String clientSecret,
            String scope,
            BeanProvider<OAuthTokenClient> tokenClientProvider
    ) {
        this(clientId, clientSecret, scope, DEFAULT_URL, tokenClientProvider);
    }

    public OAuthClientCredentialsAuthFilter(
            String clientId,
            String clientSecret,
            String scope,
            OAuthTokenClient directClient
    ) {
        this(clientId, clientSecret, scope, DEFAULT_URL, directClient);
    }

    public OAuthClientCredentialsAuthFilter(
            String clientId,
            String clientSecret,
            String scope,
            String zendeskUrl,
            OAuthTokenClient directClient
    ) {
        if (clientSecret == null || clientSecret.trim().isEmpty()) {
            throw new IllegalArgumentException("Zendesk OAuth client-secret must not be blank when client-id is configured.");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.tokenEndpointUrl = formatTokenEndpointUrl(zendeskUrl);
        this.tokenClientProvider = null;
        this.directClient = directClient;
    }

    @RequestFilter
    public Mono<MutableHttpRequest<?>> doFilter(MutableHttpRequest<?> request) {
        return obtainAccessTokenReactive()
                .map(token -> {
                    request.getHeaders().set("Authorization", "Bearer " + token);
                    return request;
                });
    }

    public Mono<String> obtainAccessTokenReactive() {
        CachedToken current = cachedToken.get();
        if (current != null && Instant.now().isBefore(current.expiresAt())) {
            return Mono.just(current.token());
        }
        return refreshAccessToken();
    }

    public String obtainAccessToken() {
        return obtainAccessTokenReactive().block();
    }

    private Mono<String> refreshAccessToken() {
        while (true) {
            Mono<String> currentMono = inFlightRefresh.get();
            if (currentMono != null) {
                return currentMono;
            }
            CachedToken current = cachedToken.get();
            if (current != null && Instant.now().isBefore(current.expiresAt())) {
                return Mono.just(current.token());
            }
            AtomicReference<Mono<String>> ref = new AtomicReference<>();
            Mono<String> newRefresh = fetchNewToken()
                    .doFinally(signalType -> inFlightRefresh.compareAndSet(ref.get(), null))
                    .cache();
            ref.set(newRefresh);
            if (inFlightRefresh.compareAndSet(null, newRefresh)) {
                return newRefresh;
            }
        }
    }

    private Mono<String> fetchNewToken() {
        return Mono.defer(() -> {
            log.debug("Requesting new Zendesk OAuth access token for client id: {}", clientId);
            OAuthTokenRequest req = new OAuthTokenRequest(clientId, clientSecret, scope);
            OAuthTokenClient client = resolveTokenClient();
            Mono<OAuthTokenResponse> mono = client != null ? client.requestToken(req) : null;
            return mono != null ? mono : Mono.empty();
        })
        .switchIfEmpty(Mono.error(new IllegalStateException(
                "No token endpoint was found at " + tokenEndpointUrl + ". Check the micronaut.http.services.zendesk.url setting."
        )))
        .onErrorResume(HttpClientResponseException.class, e -> {
            int status = e.getStatus().getCode();
            if (status == 404) {
                return Mono.error(new IllegalStateException(
                        "No token endpoint was found at " + tokenEndpointUrl + ". Check the micronaut.http.services.zendesk.url setting.",
                        e
                ));
            }
            if (status == 400 || status == 401) {
                return Mono.error(new IllegalStateException(
                        "Zendesk refused OAuth client credentials for client id '" + clientId + "': HTTP " + status,
                        e
                ));
            }
            return Mono.error(e);
        })
        .flatMap(resp -> {
            if (resp == null || resp.getAccessToken() == null || resp.getAccessToken().isBlank()) {
                return Mono.error(new IllegalStateException(
                        "Received empty OAuth token response from Zendesk for client id: " + clientId
                ));
            }
            long expiresIn = resp.getExpiresIn() != null ? resp.getExpiresIn() : 1800L;
            long buffer = Math.min(REFRESH_WINDOW_SECONDS, expiresIn / 2);
            Instant expiresAt = Instant.now().plusSeconds(Math.max(0, expiresIn - buffer));
            cachedToken.set(new CachedToken(resp.getAccessToken(), expiresAt));
            return Mono.just(resp.getAccessToken());
        });
    }

    private OAuthTokenClient resolveTokenClient() {
        return directClient != null ? directClient : tokenClientProvider.get();
    }

    private static String formatTokenEndpointUrl(String zendeskUrl) {
        String base = (zendeskUrl != null && !zendeskUrl.isBlank()) ? zendeskUrl.trim() : DEFAULT_URL;
        while (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        return base + "/oauth/tokens";
    }
}
