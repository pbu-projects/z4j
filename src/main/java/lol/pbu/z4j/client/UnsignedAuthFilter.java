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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * Adds a helpful auth hint when an unsigned Zendesk request fails with HTTP 401 or 403.
 */
@ClientFilter(value = "/**", serviceId = "zendesk")
public class UnsignedAuthFilter implements HttpClientFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(UnsignedAuthFilter.class);
    // Run after all auth filters so we see whether an Authorization header was actually added.
    private static final int FILTER_ORDER = -200;
    private static final String TOKEN_PATH_SUFFIX = "/oauth/tokens";

    @Override
    public int getOrder() {
        return FILTER_ORDER;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        if (isTokenEndpointRequest(request)) {
            return chain.proceed(request);
        }
        return Flux.from((Publisher<HttpResponse<?>>) chain.proceed(request))
                .onErrorResume(HttpClientResponseException.class, ex -> handleResponseError(request, ex));
    }

    private Publisher<HttpResponse<?>> handleResponseError(MutableHttpRequest<?> request, HttpClientResponseException ex) {
        int code = ex.code();
        boolean authMissing = request.getHeaders().getAuthorization().isEmpty();
        if (authMissing && (code == HttpStatus.UNAUTHORIZED.getCode() || code == HttpStatus.FORBIDDEN.getCode())) {
            HttpStatus status = HttpStatus.valueOf(code);
            HttpResponse<?> response = ex.getResponse();
            String body = extractBody(response);
            String diagnostic = buildDiagnosticMessage(request, status, body);
            log.warn(diagnostic);
            return Flux.error(new HttpClientResponseException(diagnostic, ex, response, null));
        }
        return Flux.error(ex);
    }

    private boolean isTokenEndpointRequest(MutableHttpRequest<?> request) {
        if (request.getMethod() != HttpMethod.POST) {
            return false;
        }
        return request.getPath().endsWith(TOKEN_PATH_SUFFIX);
    }

    private String buildDiagnosticMessage(MutableHttpRequest<?> request, HttpStatus status, @Nullable String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("Zendesk returned HTTP ")
                .append(status.getCode())
                .append(' ')
                .append(status.getReason())
                .append(" for ")
                .append(request.getMethodName())
                .append(' ')
                .append(request.getPath())
                .append(". The request was sent without an Authorization header because no Zendesk credentials are active. ")
                .append("Configure one of: micronaut.http.services.zendesk.oauth.client-id + ")
                .append("micronaut.http.services.zendesk.oauth.client-secret, ")
                .append("micronaut.http.services.zendesk.oauth.token, or ")
                .append("micronaut.http.services.zendesk.email + micronaut.http.services.zendesk.token.");
        if (body != null && !body.isBlank()) {
            sb.append(" Response body: ").append(body);
        }
        return sb.toString();
    }

    @Nullable
    private String extractBody(@Nullable HttpResponse<?> response) {
        if (response == null) {
            return null;
        }
        try {
            return response.getBody(String.class).orElse(null);
        } catch (Exception _) {
            return null;
        }
    }
}
