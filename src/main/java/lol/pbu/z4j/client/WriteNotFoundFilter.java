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

import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import java.util.Set;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

/**
 * Turns HTTP 404 errors on write requests into {@link ZendeskWriteNotFoundException} so Micronaut's
 * Mono transformer does not swallow them as empty results.
 */
@ClientFilter(value = "/**", serviceId = "zendesk")
public class WriteNotFoundFilter implements HttpClientFilter, Ordered {

    private static final int FILTER_ORDER = 100;
    private static final Set<HttpMethod> WRITE_METHODS = Set.of(
            HttpMethod.POST,
            HttpMethod.PUT,
            HttpMethod.PATCH,
            HttpMethod.DELETE
    );

    @Override
    public int getOrder() {
        return FILTER_ORDER;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        if (!WRITE_METHODS.contains(request.getMethod())) {
            return chain.proceed(request);
        }
        return Flux.from(chain.proceed(request))
                .onErrorResume(HttpClientResponseException.class, ex -> {
                    if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                        return Flux.error(new ZendeskWriteNotFoundException(request, ex));
                    }
                    return Flux.error(ex);
                });
    }
}
