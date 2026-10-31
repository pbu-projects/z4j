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
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.ClientFilter
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets

class ZendeskFilterSpec extends Specification {

    def "ZendeskHeaderFilter sets default Accept and Content-Type headers when missing"() {
        given:
        def filter = new ZendeskHeaderFilter()
        def request = HttpRequest.DELETE("/api/v2/tickets/123")
        request.headers.remove(HttpHeaders.ACCEPT)
        request.headers.remove(HttpHeaders.CONTENT_TYPE)

        when:
        filter.doFilter(request)

        then:
        verifyAll {
            request.headers.get(HttpHeaders.ACCEPT) == MediaType.APPLICATION_JSON
            request.headers.get(HttpHeaders.CONTENT_TYPE) == MediaType.APPLICATION_JSON
        }
    }

    def "ZendeskHeaderFilter preserves existing Accept and Content-Type headers"() {
        given:
        def filter = new ZendeskHeaderFilter()
        def request = HttpRequest.POST("/api/v2/uploads", "binary".bytes)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_TYPE)

        when:
        filter.doFilter(request)

        then:
        verifyAll {
            request.headers.get(HttpHeaders.ACCEPT) == MediaType.TEXT_PLAIN
            request.headers.get(HttpHeaders.CONTENT_TYPE) == MediaType.APPLICATION_OCTET_STREAM
        }
    }

    def "BasicAuthFilter sets Basic Authorization header without modifying Content-Type and preserves existing Authorization"() {
        given:
        def filter = new BasicAuthFilter("agent@example.com", "secret-token")
        def requestWithoutAuth = HttpRequest.GET("/api/v2/tickets")
        def requestWithAuth = HttpRequest.GET("/api/v2/tickets").header(HttpHeaders.AUTHORIZATION, "Custom token")
        def expectedEncoded = Base64.encoder.encodeToString("agent@example.com/token:secret-token".getBytes(StandardCharsets.UTF_8))

        when:
        filter.doFilter(requestWithoutAuth)
        filter.doFilter(requestWithAuth)

        then:
        verifyAll {
            requestWithoutAuth.headers.get(HttpHeaders.AUTHORIZATION) == "Basic ${expectedEncoded}"
            requestWithoutAuth.contentType.isEmpty()
            requestWithAuth.headers.getAll(HttpHeaders.AUTHORIZATION) == ["Custom token"]
        }
    }

    def "BearerAuthFilter sets Bearer Authorization header and preserves existing Authorization"() {
        given:
        def filter = new BearerAuthFilter("oauth-access-token-123")
        def requestWithoutAuth = HttpRequest.GET("/api/v2/tickets")
        def requestWithAuth = HttpRequest.GET("/api/v2/tickets").header(HttpHeaders.AUTHORIZATION, "Custom token")

        when:
        filter.doFilter(requestWithoutAuth)
        filter.doFilter(requestWithAuth)

        then:
        verifyAll {
            requestWithoutAuth.headers.get(HttpHeaders.AUTHORIZATION) == "Bearer oauth-access-token-123"
            requestWithAuth.headers.getAll(HttpHeaders.AUTHORIZATION) == ["Custom token"]
        }
    }

    def "all z4j client filters are scoped to the zendesk serviceId"() {
        expect:
        verifyAll {
            BasicAuthFilter.getAnnotation(ClientFilter).serviceId() == ["zendesk"] as String[]
            BearerAuthFilter.getAnnotation(ClientFilter).serviceId() == ["zendesk"] as String[]
            OAuthClientCredentialsAuthFilter.getAnnotation(ClientFilter).serviceId() == ["zendesk"] as String[]
            ZendeskHeaderFilter.getAnnotation(ClientFilter).serviceId() == ["zendesk"] as String[]
            RateLimitFilter.getAnnotation(ClientFilter).serviceId() == ["zendesk"] as String[]
        }
    }

    def "BasicAuthFilter is active and BearerAuthFilter is inactive when oauth token is empty placeholder"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.email"      : "admin@example.com",
                "micronaut.http.services.zendesk.token"      : "api-token-xyz",
                "micronaut.http.services.zendesk.oauth.token": '${UNSET_ZENDESK_OAUTH_TOKEN:}'
        ])

        expect:
        verifyAll {
            ctx.containsBean(BasicAuthFilter)
            !ctx.containsBean(BearerAuthFilter)
            ctx.containsBean(ZendeskHeaderFilter)
        }

        cleanup:
        ctx?.close()
    }

    def "BasicAuthFilter is inactive and OAuthClientCredentialsAuthFilter is active when client credentials are configured"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"                : "http://localhost:8080",
                "micronaut.http.services.zendesk.email"              : "admin@example.com",
                "micronaut.http.services.zendesk.token"              : "api-token-xyz",
                "micronaut.http.services.zendesk.oauth.client-id"    : "oauth-client-id",
                "micronaut.http.services.zendesk.oauth.client-secret": "oauth-client-secret"
        ])

        expect:
        verifyAll {
            !ctx.containsBean(BasicAuthFilter)
            ctx.containsBean(OAuthClientCredentialsAuthFilter)
            ctx.containsBean(ZendeskHeaderFilter)
        }

        cleanup:
        ctx?.close()
    }

    def "neither BasicAuthFilter nor BearerAuthFilter is active when all auth properties are empty placeholders"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.email"      : '${UNSET_ZENDESK_EMAIL:}',
                "micronaut.http.services.zendesk.token"      : '${UNSET_ZENDESK_TOKEN:}',
                "micronaut.http.services.zendesk.oauth.token": '${UNSET_ZENDESK_OAUTH_TOKEN:}'
        ])

        expect:
        verifyAll {
            !ctx.containsBean(BasicAuthFilter)
            !ctx.containsBean(BearerAuthFilter)
            ctx.containsBean(ZendeskHeaderFilter)
        }

        cleanup:
        ctx?.close()
    }

    def "z4j filters apply to zendesk client and do not leak to other HTTP services"() {
        given:
        String lastZendeskAuth = null
        String lastZendeskAccept = null
        String lastZendeskContentType = null
        String lastOtherAuth = null

        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/test/zendesk/void-delete") { exchange ->
            lastZendeskAuth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            lastZendeskAccept = exchange.requestHeaders.getFirst(HttpHeaders.ACCEPT)
            lastZendeskContentType = exchange.requestHeaders.getFirst(HttpHeaders.CONTENT_TYPE)
            exchange.sendResponseHeaders(204, -1)
            exchange.close()
        }
        server.createContext("/test/other/ping") { exchange ->
            lastOtherAuth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            byte[] body = "ok".getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
            exchange.sendResponseHeaders(200, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext clientCtx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "zendesk-oauth-token",
                "micronaut.http.services.other.url"          : baseUrl
        ])
        TestZendeskVoidClient zendeskClient = clientCtx.getBean(TestZendeskVoidClient)
        TestOtherServiceClient otherClient = clientCtx.getBean(TestOtherServiceClient)

        when: "calling a Mono<Void> delete method on the zendesk client and a GET on another service"
        zendeskClient.deleteSomething().block()
        String otherResponse = otherClient.checkOther().block()

        then: "zendesk request receives Bearer auth and restored Accept header, while other service does not receive zendesk auth"
        verifyAll {
            clientCtx.containsBean(BearerAuthFilter)
            !clientCtx.containsBean(BasicAuthFilter)
            lastZendeskAuth == "Bearer zendesk-oauth-token"
            lastZendeskAccept == MediaType.APPLICATION_JSON
            lastZendeskContentType == MediaType.APPLICATION_JSON
            otherResponse == "ok"
            lastOtherAuth == null
        }

        cleanup:
        clientCtx?.close()
        server?.stop(0)
    }

    @Client("zendesk")
    static interface TestZendeskVoidClient {
        @Delete("/test/zendesk/void-delete")
        Mono<Void> deleteSomething()
    }

    @Client("other")
    static interface TestOtherServiceClient {
        @Get(value = "/test/other/ping", processes = MediaType.TEXT_PLAIN)
        Mono<String> checkOther()
    }
}
