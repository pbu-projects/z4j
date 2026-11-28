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

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import com.sun.net.httpserver.HttpServer
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.filter.ClientFilterChain
import jakarta.inject.Singleton
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import lol.pbu.z4j.ratelimit.RateLimitTracker
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Isolated
import spock.lang.Specification

@Isolated
class UnsignedAuthSpec extends Specification {

    def "startup diagnostic logs warning when Zendesk URL is set without auth credentials and does not fail"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(ZendeskAuthStartupWarning)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        when:
        ApplicationContext ctx = ApplicationContext.builder([
                "micronaut.http.services.zendesk.url": "https://example.zendesk.com"
        ]).deduceEnvironment(false).start()

        then:
        def warnings = appender.list.findAll { it.level == Level.WARN }
        verifyAll {
            ctx.running
            !ctx.containsBean(BasicAuthFilter)
            !ctx.containsBean(BearerAuthFilter)
            !ctx.containsBean(OAuthClientCredentialsAuthFilter)
            warnings.size() == 1
            warnings[0].formattedMessage.contains("Authorization")
            warnings[0].formattedMessage.contains("oauth.client-id")
            warnings[0].formattedMessage.contains("oauth.token")
            warnings[0].formattedMessage.contains("email")
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
    }

    def "startup diagnostic logs warning when only email is configured without token"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(ZendeskAuthStartupWarning)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        when:
        ApplicationContext ctx = ApplicationContext.builder([
                "micronaut.http.services.zendesk.url"  : "https://example.zendesk.com",
                "micronaut.http.services.zendesk.email": "agent@example.com",
                "micronaut.http.services.zendesk.token": '${UNSET_ZENDESK_TOKEN:}'
        ]).deduceEnvironment(false).start()

        then:
        def warnings = appender.list.findAll { it.level == Level.WARN }
        verifyAll {
            ctx.running
            warnings.size() == 1
            warnings[0].formattedMessage.contains("no authentication credentials are active")
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
    }

    def "startup diagnostic stays silent when Zendesk URL is not set"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(ZendeskAuthStartupWarning)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        when:
        ApplicationContext ctx = ApplicationContext.builder([:]).deduceEnvironment(false).start()

        then:
        verifyAll {
            ctx.running
            appender.list.findAll { it.level == Level.WARN }.isEmpty()
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
    }

    def "startup diagnostic stays silent when #authMode auth is configured"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(ZendeskAuthStartupWarning)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        when:
        ApplicationContext ctx = ApplicationContext.builder(
                ["micronaut.http.services.zendesk.url": "https://example.zendesk.com"] + props
        ).deduceEnvironment(false).start()

        then:
        verifyAll {
            ctx.running
            appender.list.findAll { it.level == Level.WARN }.isEmpty()
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()

        where:
        authMode            | props
        "basic"             | ["micronaut.http.services.zendesk.email": "a@b.com", "micronaut.http.services.zendesk.token": "tok"]
        "bearer"            | ["micronaut.http.services.zendesk.oauth.token": "bearer-tok"]
        "oauth-credentials" | ["micronaut.http.services.zendesk.oauth.client-id": "cid", "micronaut.http.services.zendesk.oauth.client-secret": "csec"]
    }

    def "unsigned request receiving #statusCode preserves status and body and adds actionable auth guidance"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(UnsignedAuthFilter)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        String receivedAuth = "UNSET"
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/guide/permission_groups") { exchange ->
            receivedAuth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            byte[] body = responseBody.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, contentType)
            exchange.sendResponseHeaders(statusCode, body.length == 0 ? -1 : body.length)
            if (body.length > 0) {
                exchange.responseBody.write(body)
            }
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.builder([
                "micronaut.http.services.zendesk.url": baseUrl
        ]).deduceEnvironment(false).start()
        PermissionGroupClient client = ctx.getBean(PermissionGroupClient)

        when:
        client.listPermissionGroups().block()

        then:
        def ex = thrown(HttpClientResponseException)
        def warnings = appender.list.findAll { it.level == Level.WARN }
        verifyAll {
            receivedAuth == null
            ex.code() == statusCode
            ex.status == HttpStatus.valueOf(statusCode)
            ex.response.getBody(String).orElse("") == responseBody
            ex.message.contains(String.valueOf(statusCode))
            ex.message.contains("without an Authorization header")
            ex.message.contains("oauth.client-id")
            ex.message.contains("oauth.token")
            ex.message.contains("email")
            warnings.size() == 1
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
        server?.stop(0)

        where:
        statusCode | contentType                | responseBody
        401        | MediaType.APPLICATION_JSON | '{"error":"Couldn\'t authenticate you"}'
        403        | MediaType.TEXT_HTML        | ''
        403        | MediaType.APPLICATION_JSON | '{"error":"Forbidden"}'
    }

    def "signed request receiving 401 or 403 does not emit unsigned auth diagnostic"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(UnsignedAuthFilter)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        String receivedAuth = null
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/guide/permission_groups") { exchange ->
            receivedAuth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            byte[] body = '{"error":"Forbidden"}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(403, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.builder([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "valid-format-token"
        ]).deduceEnvironment(false).start()
        PermissionGroupClient client = ctx.getBean(PermissionGroupClient)

        when:
        client.listPermissionGroups().block()

        then:
        def ex = thrown(HttpClientResponseException)
        verifyAll {
            receivedAuth == "Bearer valid-format-token"
            ex.status == HttpStatus.FORBIDDEN
            !ex.message.contains("without an Authorization header")
            appender.list.findAll { it.level == Level.WARN }.isEmpty()
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
        server?.stop(0)
    }

    def "POST /oauth/tokens request skips UnsignedAuthFilter even without credentials"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(UnsignedAuthFilter)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        String receivedAuth = "UNSET"
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/oauth/tokens") { exchange ->
            receivedAuth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            byte[] body = '{"error":"invalid_client"}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(401, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.builder([
                "micronaut.http.services.zendesk.url": baseUrl
        ]).deduceEnvironment(false).start()
        OAuthTokenClient client = ctx.getBean(OAuthTokenClient)

        when:
        client.requestToken(new OAuthTokenRequest("cid", "csec", "hc:read")).block()

        then:
        def ex = thrown(HttpClientResponseException)
        verifyAll {
            receivedAuth == null
            ex.status == HttpStatus.UNAUTHORIZED
            !ex.message.contains("without an Authorization header")
            appender.list.findAll { it.level == Level.WARN }.isEmpty()
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
        server?.stop(0)
    }

    def "OAuthClientCredentialsAuthFilter request receiving 403 does not trigger UnsignedAuthFilter"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(UnsignedAuthFilter)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        String receivedAuth = null
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/oauth/tokens") { exchange ->
            byte[] body = '{"access_token":"oauth-tok","token_type":"bearer","expires_in":3600}'
                    .getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.createContext("/api/v2/guide/permission_groups") { exchange ->
            receivedAuth = exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            byte[] body = '{"error":"Forbidden"}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(403, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.builder([
                "micronaut.http.services.zendesk.url"                : baseUrl,
                "micronaut.http.services.zendesk.oauth.client-id"    : "cid",
                "micronaut.http.services.zendesk.oauth.client-secret": "csec"
        ]).deduceEnvironment(false).start()
        PermissionGroupClient client = ctx.getBean(PermissionGroupClient)

        when:
        client.listPermissionGroups().block()

        then:
        def ex = thrown(HttpClientResponseException)
        verifyAll {
            receivedAuth == "Bearer oauth-tok"
            ex.status == HttpStatus.FORBIDDEN
            !ex.message.contains("without an Authorization header")
            appender.list.findAll { it.level == Level.WARN }.isEmpty()
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
        server?.stop(0)
    }

    def "non-standard HTTP status code on unsigned request does not throw IllegalArgumentException"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(UnsignedAuthFilter)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        def filter = new UnsignedAuthFilter()
        def request = HttpRequest.GET("/api/v2/guide/permission_groups")
        def response = Mock(HttpResponse) {
            code() >> 499
            getContentType() >> Optional.empty()
            getBody(_) >> Optional.empty()
            getHeaders() >> HttpResponse.ok().headers
            getStatus() >> { throw new IllegalArgumentException("Invalid HTTP status code: 499") }
        }
        def upstream = new HttpClientResponseException("Client error", response)
        def chain = Mock(ClientFilterChain) {
            1 * proceed(request) >> Flux.error(upstream)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def ex = thrown(HttpClientResponseException)
        verifyAll {
            ex.is(upstream)
            ex.code() == 499
            appender.list.findAll { it.level == Level.WARN }.isEmpty()
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
    }

    def "end-to-end non-standard 499 response passes through RateLimitFilter and UnsignedAuthFilter without IllegalArgumentException"() {
        given:
        def logger = (Logger) LoggerFactory.getLogger(UnsignedAuthFilter)
        def previousLevel = logger.level
        logger.setLevel(Level.WARN)
        def appender = new ListAppender<ILoggingEvent>()
        appender.start()
        logger.addAppender(appender)

        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/guide/permission_groups") { exchange ->
            byte[] body = '{"error":"Client Closed Request"}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(499, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.builder([
                "micronaut.http.services.zendesk.url": baseUrl,
                "z4j.rate-limit.auto-wait-enabled"   : "false",
                "unsigned-auth-spec.client"          : "true"
        ]).deduceEnvironment(false).start()
        HttpClient client = ctx.getBean(RawZendeskHttpClient).httpClient
        RateLimitTracker tracker = ctx.getBean(RateLimitTracker)

        when:
        Flux.from(client.exchange(HttpRequest.GET("/api/v2/guide/permission_groups"), String)).blockFirst()

        then:
        def ex = thrown(HttpClientResponseException)
        verifyAll {
            ex.code() == 499
            !ex.message.contains("without an Authorization header")
            tracker.latestSnapshot != null
            tracker.latestSnapshot.statusCode == 499
            appender.list.findAll { it.level == Level.WARN }.isEmpty()
        }

        cleanup:
        logger.detachAppender(appender)
        logger.setLevel(previousLevel)
        ctx?.close()
        server?.stop(0)
    }

    @Requires(property = "unsigned-auth-spec.client", value = "true")
    @Singleton
    static class RawZendeskHttpClient {
        final HttpClient httpClient

        RawZendeskHttpClient(@Client("zendesk") HttpClient httpClient) {
            this.httpClient = httpClient
        }
    }
}
