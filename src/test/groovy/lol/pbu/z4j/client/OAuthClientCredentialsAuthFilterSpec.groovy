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

import com.sun.net.httpserver.HttpServer
import io.micronaut.context.ApplicationContext
import io.micronaut.context.BeanProvider
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.filter.ClientFilterChain
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.util.concurrent.atomic.AtomicInteger
import lol.pbu.z4j.model.OAuthTokenResponse
import lol.pbu.z4j.ratelimit.RateLimitTracker
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.core.scheduler.Schedulers
import spock.lang.Specification
import spock.lang.Unroll

/**
 * <h1>OAuth Client Credentials Auth Filter Spec.</h1>
 * <p>Validates OAuth token exchange, caching, 401 retry, header handling, and error handling.</p>
 *
 * @author Jonathan-Zollinger
 * @since 0.4.0
 */
class OAuthClientCredentialsAuthFilterSpec extends Specification {

    def "OAuthTokenClient is bound to the zendesk service id"() {
        expect:
        OAuthTokenClient.getAnnotation(Client).value() == "zendesk"
    }

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

    def "fails at construction with custom url if client-secret is blank"() {
        when:
        new OAuthClientCredentialsAuthFilter("test-client", null, "hc:read", "https://custom.zendesk.com", Mock(OAuthTokenClient))

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

    def "oauth filter preserves existing authorization header without fetching a token"() {
        given:
        def clientMock = Mock(OAuthTokenClient)
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets").header("Authorization", "CustomToken")
        def chain = Mock(ClientFilterChain) {
            1 * proceed(request) >> Flux.just(HttpResponse.ok())
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        0 * clientMock.requestToken(_)
        verifyAll {
            request.headers.get("Authorization") == "CustomToken"
            request.headers.getAll("Authorization").size() == 1
        }
    }

    def "oauth filter skips token endpoint requests to avoid recursion"() {
        given:
        def clientMock = Mock(OAuthTokenClient)
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.POST("/oauth/tokens", [:])
        def chain = Mock(ClientFilterChain) {
            1 * proceed(request) >> Flux.just(HttpResponse.ok())
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        0 * clientMock.requestToken(_)
        request.headers.getAuthorization().isEmpty()
    }

    def "oauth filter matches token endpoint path against configured zendesk url path"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.just(new OAuthTokenResponse("api-token", "Bearer", 1800L, "hc:read"))
        }
        def filter = new OAuthClientCredentialsAuthFilter(
                "test-client",
                "test-secret",
                "hc:read",
                "https://example.zendesk.com/sub/",
                clientMock
        )
        def tokenReq = HttpRequest.POST("/sub/oauth/tokens", [:])
        def apiReqWithSuffix = HttpRequest.POST("/api/v2/oauth/tokens", [:])
        def chain = Mock(ClientFilterChain) {
            1 * proceed(tokenReq) >> Flux.just(HttpResponse.ok())
            1 * proceed(apiReqWithSuffix) >> Flux.just(HttpResponse.ok())
        }

        when:
        Mono.from(filter.doFilter(tokenReq, chain)).block()
        Mono.from(filter.doFilter(apiReqWithSuffix, chain)).block()

        then:
        verifyAll {
            tokenReq.headers.getAuthorization().isEmpty()
            apiReqWithSuffix.headers.get("Authorization") == "Bearer api-token"
        }
    }

    def "caching reuses token until expiration window and proactively refreshes"() {
        given:
        def clientMock = Mock(OAuthTokenClient)
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request1 = HttpRequest.GET("/api/v2/tickets")
        def request2 = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain) {
            2 * proceed(_) >> Flux.just(HttpResponse.ok())
        }

        when: "two consecutive requests are made within the token lifetime"
        Mono.from(filter.doFilter(request1, chain)).block()
        Mono.from(filter.doFilter(request2, chain)).block()

        then: "token is only fetched once from the token client"
        1 * clientMock.requestToken(_) >> Mono.just(new OAuthTokenResponse("cached-token", "Bearer", 1800L, "hc:read"))
        verifyAll {
            request1.headers.get("Authorization") == "Bearer cached-token"
            request2.headers.get("Authorization") == "Bearer cached-token"
        }
    }

    def "concurrent requests share single in-flight token refresh"() {
        given:
        def sink = Sinks.one()
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> sink.asMono()
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request1 = HttpRequest.GET("/api/v2/tickets")
        def request2 = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain) {
            2 * proceed(_) >> Flux.just(HttpResponse.ok())
        }

        when:
        def mono1 = Mono.from(filter.doFilter(request1, chain))
        def mono2 = Mono.from(filter.doFilter(request2, chain))
        sink.tryEmitValue(new OAuthTokenResponse("shared-token", "Bearer", 1800L, "hc:read"))
        mono1.block()
        mono2.block()

        then:
        verifyAll {
            request1.headers.get("Authorization") == "Bearer shared-token"
            request2.headers.get("Authorization") == "Bearer shared-token"
        }
    }

    def "inFlightRefresh does not cache error signals for concurrent callers"() {
        given:
        def attempts = new AtomicInteger(0)
        def clientMock = Mock(OAuthTokenClient) {
            2 * requestToken(_) >> {
                if (attempts.incrementAndGet() == 1) {
                    return Mono.error(new HttpClientResponseException("Server Error", HttpResponse.serverError()))
                }
                return Mono.just(new OAuthTokenResponse("recovered-token", "Bearer", 1800L, "hc:read"))
            }
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request1 = HttpRequest.GET("/api/v2/tickets")
        def request2 = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain) {
            1 * proceed(request2) >> Flux.just(HttpResponse.ok())
        }

        when: "two callers obtain the in-flight refresh before either subscribes and the first fails"
        def mono1 = Mono.from(filter.doFilter(request1, chain))
        def mono2 = Mono.from(filter.doFilter(request2, chain))

        and: "first call fails"
        def firstError = null
        try {
            mono1.block()
        } catch (Throwable t) {
            firstError = t
        }

        and: "second concurrent caller runs after the first failure"
        mono2.block()

        then: "error is not cached and the second caller fetches a fresh token"
        verifyAll {
            firstError instanceof HttpClientResponseException
            attempts.get() == 2
            request2.headers.get("Authorization") == "Bearer recovered-token"
        }
    }

    def "invalidates cached token on 401 response and retries once"() {
        given:
        def tokenCallCount = new AtomicInteger(0)
        def clientMock = Mock(OAuthTokenClient) {
            2 * requestToken(_) >> {
                int call = tokenCallCount.incrementAndGet()
                return Mono.just(new OAuthTokenResponse("token-${call}", "Bearer", 1800L, "hc:read"))
            }
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def seenAuthHeaders = []
        def chain = Mock(ClientFilterChain) {
            2 * proceed(request) >> {
                seenAuthHeaders << request.headers.get("Authorization")
                if (seenAuthHeaders.size() == 1) {
                    return Flux.error(new HttpClientResponseException("Unauthorized", HttpResponse.unauthorized()))
                }
                return Flux.just(HttpResponse.ok())
            }
        }

        when:
        def response = Mono.from(filter.doFilter(request, chain)).block()

        then:
        verifyAll {
            response.status == HttpStatus.OK
            tokenCallCount.get() == 2
            seenAuthHeaders == ["Bearer token-1", "Bearer token-2"]
            request.headers.getAll("Authorization") == ["Bearer token-2"]
        }
    }

    def "invalidates cached token on 401 and stops after one retry if 401 persists"() {
        given:
        def tokenCallCount = new AtomicInteger(0)
        def clientMock = Mock(OAuthTokenClient) {
            2 * requestToken(_) >> {
                int call = tokenCallCount.incrementAndGet()
                return Mono.just(new OAuthTokenResponse("token-${call}", "Bearer", 1800L, "hc:read"))
            }
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def proceedCount = new AtomicInteger(0)
        def chain = Mock(ClientFilterChain) {
            2 * proceed(request) >> {
                proceedCount.incrementAndGet()
                return Flux.error(new HttpClientResponseException("Unauthorized", HttpResponse.unauthorized()))
            }
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll {
            err.status == HttpStatus.UNAUTHORIZED
            proceedCount.get() == 2
            tokenCallCount.get() == 2
        }
    }

    def "throws descriptive exception naming client-id on 401 response from token endpoint"() {
        given:
        def resp = HttpResponse.unauthorized()
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.error(new HttpClientResponseException("Unauthorized", resp))
        }
        def filter = new OAuthClientCredentialsAuthFilter("my-special-client-id", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain)

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message.contains("my-special-client-id")
            e.message.contains("401")
        }
    }

    def "throws descriptive exception naming client-id on 400 response from token endpoint"() {
        given:
        def resp = HttpResponse.badRequest()
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.error(new HttpClientResponseException("Bad Request", resp))
        }
        def filter = new OAuthClientCredentialsAuthFilter("client-400", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain)

        when:
        Mono.from(filter.doFilter(request, chain)).block()

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
        def chain = Mock(ClientFilterChain)

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message.contains("client-empty")
            e.message.contains("empty OAuth token response")
        }
    }

    def "fails with clear message pointing to token url and setting when token endpoint returns empty mono"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.empty()
        }
        def filter = new OAuthClientCredentialsAuthFilter("client-not-found", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain)

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message.contains("/oauth/tokens")
            e.message.contains("micronaut.http.services.zendesk.url")
        }
    }

    def "fails with clear message pointing to token url when client returns null mono"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> null
        }
        def filter = new OAuthClientCredentialsAuthFilter(
                "client-null-mono",
                "test-secret",
                "hc:read",
                "",
                clientMock
        )
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain)

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message == "No token endpoint was found at https://localhost/oauth/tokens. Check the micronaut.http.services.zendesk.url setting."
        }
    }

    def "fails with clear message pointing to token url when client throws 404 HttpClientResponseException"() {
        given:
        def resp = HttpResponse.notFound()
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.error(new HttpClientResponseException("Not Found", resp))
        }
        def filter = new OAuthClientCredentialsAuthFilter(
                "client-404-ex",
                "test-secret",
                "hc:read",
                "https://custom-subdomain.zendesk.com/",
                clientMock
        )
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain)

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def e = thrown(IllegalStateException)
        verifyAll {
            e != null
            e.message == "No token endpoint was found at https://custom-subdomain.zendesk.com/oauth/tokens. Check the micronaut.http.services.zendesk.url setting."
        }
    }

    def "propagates other unexpected HttpClientResponseException"() {
        given:
        def resp = HttpResponse.serverError()
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.error(new HttpClientResponseException("Internal Server Error", resp))
        }
        def filter = new OAuthClientCredentialsAuthFilter("client-500", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain)

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def e = thrown(HttpClientResponseException)
        verifyAll {
            e != null
            e.status == HttpStatus.INTERNAL_SERVER_ERROR
        }
    }

    def "oauth filter completes without blocking error on non-blocking reactive thread"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.just(new OAuthTokenResponse("token-xyz", "Bearer", 1800L, "hc:read")).hide()
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain) {
            1 * proceed(request) >> Flux.just(HttpResponse.ok())
        }

        when:
        Mono.from(filter.doFilter(request, chain))
                .subscribeOn(Schedulers.parallel())
                .block()

        then:
        noExceptionThrown()
        verifyAll {
            request.headers.get("Authorization") == "Bearer token-xyz"
        }
    }

    def "obtainAccessToken provides backward compatibility by blocking for token"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.just(new OAuthTokenResponse("legacy-token", "Bearer", 1800L, "hc:read"))
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", clientMock)

        when:
        def token = filter.obtainAccessToken()

        then:
        verifyAll {
            token == "legacy-token"
        }
    }

    def "handles token response with null expiresIn and short expiresIn"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.just(new OAuthTokenResponse("token-null-exp", "Bearer", null, "hc:read"))
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", (String) null, clientMock)
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain) {
            1 * proceed(request) >> Flux.just(HttpResponse.ok())
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        verifyAll {
            request.headers.get("Authorization") == "Bearer token-null-exp"
        }
    }

    def "handles provider based constructor resolution"() {
        given:
        def clientMock = Mock(OAuthTokenClient) {
            1 * requestToken(_) >> Mono.just(new OAuthTokenResponse("provider-token", "Bearer", 30L, "hc:read"))
        }
        def provider = Mock(BeanProvider) {
            1 * get() >> clientMock
        }
        def filter = new OAuthClientCredentialsAuthFilter("test-client", "test-secret", "hc:read", provider)
        def request = HttpRequest.GET("/api/v2/tickets")
        def chain = Mock(ClientFilterChain) {
            1 * proceed(request) >> Flux.just(HttpResponse.ok())
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        verifyAll {
            request.headers.get("Authorization") == "Bearer provider-token"
        }
    }

    def "end-to-end zendesk client refreshes expired token on 401 and succeeds"() {
        given:
        def tokenRequests = new AtomicInteger(0)
        def apiAuthHeaders = []

        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/oauth/tokens") { exchange ->
            int n = tokenRequests.incrementAndGet()
            byte[] response = "{\"access_token\":\"live-token-${n}\",\"token_type\":\"Bearer\",\"expires_in\":3600,\"scope\":\"hc:read\"}".getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.createContext("/test/zendesk/ping") { exchange ->
            String auth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            apiAuthHeaders << auth
            if (auth == "Bearer live-token-1") {
                byte[] err = '{"error":"Expired token"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(401, err.length)
                exchange.responseBody.write(err)
            } else {
                byte[] ok = "pong".getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                exchange.sendResponseHeaders(200, ok.length)
                exchange.responseBody.write(ok)
            }
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"                : baseUrl,
                "micronaut.http.services.zendesk.oauth.client-id"    : "integration-client-id",
                "micronaut.http.services.zendesk.oauth.client-secret": "integration-client-secret"
        ])
        TestZendeskPingClient client = ctx.getBean(TestZendeskPingClient)

        when:
        String result = client.ping().block()

        then:
        verifyAll {
            result == "pong"
            tokenRequests.get() == 2
            apiAuthHeaders == ["Bearer live-token-1", "Bearer live-token-2"]
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    @Unroll
    def "end-to-end #method request hitting 401 then 404 throws ZendeskWriteNotFoundException"() {
        given:
        def tokenRequests = new AtomicInteger(0)
        def apiAuthHeaders = []
        String notFoundBody = '{"error":"RecordNotFound","description":"Not found"}'

        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/oauth/tokens") { exchange ->
            int n = tokenRequests.incrementAndGet()
            byte[] response = "{\"access_token\":\"write-token-${n}\",\"token_type\":\"Bearer\",\"expires_in\":3600,\"scope\":\"hc:write\"}".getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.createContext("/test/zendesk/write") { exchange ->
            String auth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            apiAuthHeaders << auth
            if (auth == "Bearer write-token-1") {
                byte[] err = '{"error":"Expired token"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(401, err.length)
                exchange.responseBody.write(err)
            } else {
                byte[] err = notFoundBody.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(404, err.length)
                exchange.responseBody.write(err)
            }
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"                : baseUrl,
                "micronaut.http.services.zendesk.oauth.client-id"    : "integration-client-id",
                "micronaut.http.services.zendesk.oauth.client-secret": "integration-client-secret"
        ])
        TestZendeskPingClient client = ctx.getBean(TestZendeskPingClient)

        when:
        invokeWrite(client).block()

        then:
        def ex = thrown(ZendeskWriteNotFoundException)
        verifyAll {
            ex.status == HttpStatus.NOT_FOUND
            ex.code() == 404
            ex.responseBody == notFoundBody
            tokenRequests.get() == 2
            apiAuthHeaders == ["Bearer write-token-1", "Bearer write-token-2"]
        }

        cleanup:
        ctx?.close()
        server?.stop(0)

        where:
        method | invokeWrite
        "POST" | { TestZendeskPingClient c -> c.postWrite([name: "test"]) }
        "PUT"  | { TestZendeskPingClient c -> c.putWrite([name: "test"]) }
    }

    def "end-to-end GET hitting 401 then 200 with rate-limit headers updates RateLimitTracker"() {
        given:
        def tokenRequests = new AtomicInteger(0)
        def apiAuthHeaders = []

        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/oauth/tokens") { exchange ->
            int n = tokenRequests.incrementAndGet()
            byte[] response = "{\"access_token\":\"read-token-${n}\",\"token_type\":\"Bearer\",\"expires_in\":3600,\"scope\":\"hc:read\"}".getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.createContext("/test/zendesk/ping") { exchange ->
            String auth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            apiAuthHeaders << auth
            if (auth == "Bearer read-token-1") {
                byte[] err = '{"error":"Expired token"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(401, err.length)
                exchange.responseBody.write(err)
            } else {
                byte[] ok = "pong".getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                exchange.responseHeaders.add("X-Rate-Limit", "700")
                exchange.responseHeaders.add("X-Rate-Limit-Remaining", "698")
                exchange.responseHeaders.add("ratelimit-reset", "42")
                exchange.sendResponseHeaders(200, ok.length)
                exchange.responseBody.write(ok)
            }
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"                : baseUrl,
                "micronaut.http.services.zendesk.oauth.client-id"    : "integration-client-id",
                "micronaut.http.services.zendesk.oauth.client-secret": "integration-client-secret"
        ])
        TestZendeskPingClient client = ctx.getBean(TestZendeskPingClient)
        RateLimitTracker tracker = ctx.getBean(RateLimitTracker)

        when:
        String result = client.ping().block()
        def snapshot = tracker.latestSnapshot

        then:
        verifyAll {
            result == "pong"
            tokenRequests.get() == 2
            apiAuthHeaders == ["Bearer read-token-1", "Bearer read-token-2"]
            snapshot != null
            snapshot.statusCode == 200
            snapshot.requestMethod == "GET"
            snapshot.requestPath == "/test/zendesk/ping"
            snapshot.globalLimit == 700
            snapshot.globalRemaining == 698
            snapshot.globalResetSeconds == 42
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    @Client("zendesk")
    static interface TestZendeskPingClient {
        @Get(value = "/test/zendesk/ping", processes = MediaType.TEXT_PLAIN)
        Mono<String> ping()

        @Post("/test/zendesk/write")
        Mono<Map<String, Object>> postWrite(@Body Map<String, Object> payload)

        @Put("/test/zendesk/write")
        Mono<Map<String, Object>> putWrite(@Body Map<String, Object> payload)
    }
}
