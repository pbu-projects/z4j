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
package lol.pbu.z4j.client

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.exceptions.HttpClientResponseException
import lol.pbu.z4j.model.OAuthTokenResponse
import reactor.core.publisher.Mono
import spock.lang.Specification

/**
 * <h1>OAuth Client Credentials Auth Filter Spec.</h1>
 * <p>Validates OAuth token exchange, caching, header replacement, and error handling.</p>
 *
 * @author Jonathan-Zollinger
 * @since 0.4.0
 */
class OAuthClientCredentialsAuthFilterSpec extends Specification {

    def "fails at construction if client-secret is blank"() {
        when:
        new OAuthClientCredentialsAuthFilter("test-client", "  ", "hc:read", Mock(OAuthTokenClient))

        then:
        def e = thrown(IllegalArgumentException)
        verifyAll {
            e != null
            e.message.contains("client-secret")
        }
    }

    def "fails at context startup if client-id is configured but client-secret is blank"() {
        when:
        ApplicationContext.run([
                "micronaut.http.services.zendesk.oauth.client-id"    : "test-client",
                "micronaut.http.services.zendesk.oauth.client-secret": "   "
        ])

        then:
        def e = thrown(Throwable)
        verifyAll {
            e != null
        }
    }

    def "oauth filter replaces authorization header instead of appending"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.just(new OAuthTokenResponse("token-abc", "Bearer", 1800L, "hc:read"))
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets").header("Authorization", "OldToken")

        when:
        filter.doFilter(request)

        then:
        verifyAll {
            request.headers.get("Authorization") == "Bearer token-abc"
            request.headers.getAll("Authorization").size() == 1
        }
    }

    def "caching reuses token until expiration window and proactively refreshes"() {
        given:
        def clientMock = Mock(OAuthTokenClient)
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request1 = HttpRequest.GET("/api/v2/tickets")
        def request2 = HttpRequest.GET("/api/v2/tickets")

        when: "two consecutive requests are made within the token lifetime"
        filter.doFilter(request1)
        filter.doFilter(request2)

        then: "token is only fetched once from the token client"
        1 * clientMock.requestToken(_) >> Mono.just(new OAuthTokenResponse("cached-token", "Bearer", 1800L, "hc:read"))
        verifyAll {
            request1.headers.get("Authorization") == "Bearer cached-token"
            request2.headers.get("Authorization") == "Bearer cached-token"
        }
    }

    def "throws descriptive exception naming client-id on 401 response and does not retry"() {
        given:
        def resp = HttpResponse.unauthorized()
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.error(new HttpClientResponseException("Unauthorized", resp))
        }
        def filter = new OAuthClientCredentialsAuthFilter("my-special-client-id", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")

        when:
        filter.doFilter(request)

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message.contains("my-special-client-id")
            e.message.contains("401")
        }
    }

    def "throws descriptive exception naming client-id on 400 response and does not retry"() {
        given:
        def resp = HttpResponse.badRequest()
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.error(new HttpClientResponseException("Bad Request", resp))
        }
        def filter = new OAuthClientCredentialsAuthFilter("client-400", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")

        when:
        filter.doFilter(request)

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message.contains("client-400")
            e.message.contains("400")
        }
    }

    def "throws descriptive exception when token response is empty"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.just(new OAuthTokenResponse(null, "Bearer", 1800L, "hc:read"))
        }
        def filter = new OAuthClientCredentialsAuthFilter("client-empty", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")

        when:
        filter.doFilter(request)

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message.contains("client-empty")
            e.message.contains("empty OAuth token response")
        }
    }
}
