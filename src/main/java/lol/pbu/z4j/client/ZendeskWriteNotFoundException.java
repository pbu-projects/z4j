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
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpResponseProvider;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.http.client.exceptions.HttpClientResponseException;

/**
 * Raised when a write request receives HTTP 404 from Zendesk so callers get the status and body
 * instead of an empty Mono.
 */
public class ZendeskWriteNotFoundException extends HttpClientException implements HttpResponseProvider {

    @Nullable
    private final HttpResponse<?> response;
    @Nullable
    private final String responseBody;

    public ZendeskWriteNotFoundException(HttpRequest<?> request, HttpClientResponseException cause) {
        this(request, cause, cause.getResponse());
    }

    private ZendeskWriteNotFoundException(
            HttpRequest<?> request,
            HttpClientResponseException cause,
            @Nullable HttpResponse<?> response
    ) {
        this(request, cause, response, extractBody(response));
    }

    private ZendeskWriteNotFoundException(
            HttpRequest<?> request,
            HttpClientResponseException cause,
            @Nullable HttpResponse<?> response,
            @Nullable String responseBody
    ) {
        super(buildMessage(request, response, responseBody), cause);
        this.response = response;
        this.responseBody = responseBody;
    }

    @Override
    @Nullable
    public HttpResponse<?> getResponse() {
        return response;
    }

    public HttpStatus getStatus() {
        return response != null ? response.getStatus() : HttpStatus.NOT_FOUND;
    }

    public int code() {
        return response != null ? response.code() : HttpStatus.NOT_FOUND.getCode();
    }

    @Nullable
    public String getResponseBody() {
        return responseBody;
    }

    private static String buildMessage(
            HttpRequest<?> request,
            @Nullable HttpResponse<?> response,
            @Nullable String body
    ) {
        int statusCode = response != null ? response.code() : HttpStatus.NOT_FOUND.getCode();
        String reason = response != null ? response.reason() : HttpStatus.NOT_FOUND.getReason();
        String base = "Zendesk returned HTTP " + statusCode + " " + reason
                + " for " + request.getMethodName() + " " + request.getPath();
        if (body != null && !body.isBlank()) {
            return base + ": " + body;
        }
        return base;
    }

    @Nullable
    private static String extractBody(@Nullable HttpResponse<?> response) {
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
