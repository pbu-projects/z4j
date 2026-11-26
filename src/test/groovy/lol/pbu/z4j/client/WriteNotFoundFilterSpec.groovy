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
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import lol.pbu.z4j.model.ArticleBuilder
import lol.pbu.z4j.model.ArticleUpdateRequest
import lol.pbu.z4j.model.LocaleAbbreviation
import lol.pbu.z4j.ratelimit.RateLimitTracker
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class WriteNotFoundFilterSpec extends Specification {

    static final String WRITE_NOT_FOUND_BODY = '{"error":"RecordNotFound","description":"Not found"}'

    @Shared
    HttpServer server

    @Shared
    @AutoCleanup
    ApplicationContext clientCtx

    @Shared
    ArticleClient articleClient

    @Shared
    WriteNotFoundProbeClient probeClient

    @Shared
    RateLimitTracker rateLimitTracker

    void setupSpec() {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        def writeNotFoundHandler = { exchange ->
            byte[] body = WRITE_NOT_FOUND_BODY.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.responseHeaders.add("X-Rate-Limit-Remaining", "699")
            exchange.sendResponseHeaders(404, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.createContext("/api/v2/help_center/articles/999999", writeNotFoundHandler)
        server.createContext("/api/v2/help_center/en-us/articles/999999", writeNotFoundHandler)
        server.createContext("/test/write-not-found", writeNotFoundHandler)
        server.start()

        String baseUrl = "http://127.0.0.1:${server.address.port}"
        clientCtx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "test-oauth-token",
                "write-not-found-spec.client"                : "true"
        ])
        articleClient = clientCtx.getBean(ArticleClient)
        probeClient = clientCtx.getBean(WriteNotFoundProbeClient)
        rateLimitTracker = clientCtx.getBean(RateLimitTracker)
    }

    void cleanupSpec() {
        server?.stop(0)
    }

    def "ArticleClient.updateArticleNoLocale fails with 404 status and Zendesk body instead of empty Mono"() {
        given:
        def request = new ArticleUpdateRequest(
                ArticleBuilder.builder()
                        .permissionGroupId(1L)
                        .title("Updated title")
                        .build()
        )

        when:
        articleClient.updateArticleNoLocale(999999L, request).block()

        then:
        def ex = thrown(ZendeskWriteNotFoundException)
        verifyAll {
            ex.status == HttpStatus.NOT_FOUND
            ex.code() == 404
            ex.responseBody == WRITE_NOT_FOUND_BODY
            ex.message.contains("404")
            ex.message.contains("RecordNotFound")
            ex.response.getBody(String).orElse(null) == WRITE_NOT_FOUND_BODY
            rateLimitTracker.latestSnapshot != null
            rateLimitTracker.latestSnapshot.statusCode == 404
        }
    }

    def "declarative Mono<Void> delete on 404 fails with status and body"() {
        when:
        articleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, 999999L).block()

        then:
        def ex = thrown(ZendeskWriteNotFoundException)
        verifyAll {
            ex.status == HttpStatus.NOT_FOUND
            ex.code() == 404
            ex.responseBody == WRITE_NOT_FOUND_BODY
        }
    }

    def "POST, PUT, PATCH, and DELETE 404 responses all fail with status and body while GET 404 stays empty"() {
        expect:
        StepVerifier.create(probeClient.postMissing([name: "test"]))
                .expectErrorSatisfies { Throwable t ->
                    verifyAll {
                        t instanceof ZendeskWriteNotFoundException
                        ((ZendeskWriteNotFoundException) t).code() == 404
                        ((ZendeskWriteNotFoundException) t).status == HttpStatus.NOT_FOUND
                        ((ZendeskWriteNotFoundException) t).responseBody == WRITE_NOT_FOUND_BODY
                    }
                }
                .verify()

        StepVerifier.create(probeClient.putMissing([name: "test"]))
                .expectErrorSatisfies { Throwable t ->
                    verifyAll {
                        t instanceof ZendeskWriteNotFoundException
                        ((ZendeskWriteNotFoundException) t).code() == 404
                        ((ZendeskWriteNotFoundException) t).status == HttpStatus.NOT_FOUND
                        ((ZendeskWriteNotFoundException) t).responseBody == WRITE_NOT_FOUND_BODY
                    }
                }
                .verify()

        StepVerifier.create(probeClient.patchMissing([name: "test"]))
                .expectErrorSatisfies { Throwable t ->
                    verifyAll {
                        t instanceof ZendeskWriteNotFoundException
                        ((ZendeskWriteNotFoundException) t).code() == 404
                        ((ZendeskWriteNotFoundException) t).status == HttpStatus.NOT_FOUND
                        ((ZendeskWriteNotFoundException) t).responseBody == WRITE_NOT_FOUND_BODY
                    }
                }
                .verify()

        StepVerifier.create(probeClient.deleteMissing())
                .expectErrorSatisfies { Throwable t ->
                    verifyAll {
                        t instanceof ZendeskWriteNotFoundException
                        ((ZendeskWriteNotFoundException) t).code() == 404
                        ((ZendeskWriteNotFoundException) t).status == HttpStatus.NOT_FOUND
                        ((ZendeskWriteNotFoundException) t).responseBody == WRITE_NOT_FOUND_BODY
                    }
                }
                .verify()

        and: "GET 404 behavior remains unchanged as Mono.empty()"
        StepVerifier.create(articleClient.showArticleNoLocale(999999L))
                .expectComplete()
                .verify()

        StepVerifier.create(probeClient.getMissing())
                .expectComplete()
                .verify()
    }

    def "ZendeskWriteNotFoundException handles null response safely"() {
        given:
        def request = HttpRequest.PUT("/api/v2/articles/999999", [:])
        def cause = new HttpClientResponseException("Not Found", HttpResponse.notFound()) {
            @Override
            HttpResponse<?> getResponse() {
                return null
            }
        }

        when:
        def ex = new ZendeskWriteNotFoundException(request, cause)

        then:
        verifyAll(ex) {
            response == null
            responseBody == null
            status == HttpStatus.NOT_FOUND
            code() == 404
            message.contains("404")
            message.contains("PUT /api/v2/articles/999999")
        }
    }

    @Requires(property = "write-not-found-spec.client", value = "true")
    @Client("zendesk")
    static interface WriteNotFoundProbeClient {
        @Post("/test/write-not-found")
        Mono<Map<String, Object>> postMissing(@Body Map<String, Object> payload)

        @Put("/test/write-not-found")
        Mono<Map<String, Object>> putMissing(@Body Map<String, Object> payload)

        @Patch("/test/write-not-found")
        Mono<Map<String, Object>> patchMissing(@Body Map<String, Object> payload)

        @Delete("/test/write-not-found")
        Mono<Map<String, Object>> deleteMissing()

        @Get("/test/write-not-found")
        Mono<Map<String, Object>> getMissing()
    }
}
