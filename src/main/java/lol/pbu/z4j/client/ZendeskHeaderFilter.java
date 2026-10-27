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

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;

/**
 * Sets default JSON headers on outgoing Zendesk client requests, including restoring
 * the Accept header when Micronaut removes it on void return types.
 *
 * @author Jonathan Zollinger
 * @since 0.4.0
 */
@ClientFilter(value = "/**", serviceId = "zendesk")
public class ZendeskHeaderFilter {

    @RequestFilter
    public void doFilter(MutableHttpRequest<?> request) {
        if (request.getHeaders().accept().isEmpty()) {
            request.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        }
        if (request.getContentType().isEmpty()) {
            request.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        }
    }
}
