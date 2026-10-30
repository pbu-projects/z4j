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
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.exceptions.HttpClientResponseException
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.util.concurrent.atomic.AtomicInteger
import lol.pbu.z4j.model.TicketComment
import lol.pbu.z4j.model.TicketCreateInput
import lol.pbu.z4j.model.TicketCreateRequest
import lol.pbu.z4j.model.TicketResponse
import spock.lang.Specification

class ZendeskClientRetrySpec extends Specification {

    def "all 27 z4j client interfaces do not have Retryable annotation"() {
        given:
        def clientClasses = [
                ArticleAttachmentClient,
                ArticleClient,
                ArticleCommentClient,
                ArticleLabelClient,
                AttachmentClient,
                CategoryClient,
                ContentTagClient,
                CustomObjectRecordsClient,
                CustomObjectsClient,
                CustomStatusClient,
                GuideMediaClient,
                IncrementalClient,
                JobStatusClient,
                LocaleClient,
                PermissionGroupClient,
                PostClient,
                RedirectRuleClient,
                SearchClient,
                SectionClient,
                TicketClient,
                TicketFormsClient,
                TopicClient,
                TranslationClient,
                UserClient,
                UserSegmentClient,
                ViewClient,
                VoteClient
        ]

        expect:
        verifyAll {
            clientClasses.size() == 27
            clientClasses.every { Class<?> clazz ->
                clazz.getAnnotations().every { ann ->
                    ann.annotationType().simpleName != "Retryable"
                }
            }
        }
    }

    def "client propagates 401 Unauthorized immediately without retrying"() {
        given:
        AtomicInteger requestCount = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/tickets/100") { exchange ->
            requestCount.incrementAndGet()
            byte[] response = "{\"error\":\"Invalid credentials\"}".getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(401, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "bad-token"
        ])
        TicketClient client = ctx.getBean(TicketClient)

        when:
        client.showTicket(100L).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll {
            err.status == HttpStatus.UNAUTHORIZED
            requestCount.get() == 1
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "client propagates 403 Forbidden immediately without retrying"() {
        given:
        AtomicInteger requestCount = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/tickets/200") { exchange ->
            requestCount.incrementAndGet()
            byte[] response = "{\"error\":\"Forbidden\"}".getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(403, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "limited-token"
        ])
        TicketClient client = ctx.getBean(TicketClient)

        when:
        client.showTicket(200L).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll {
            err.status == HttpStatus.FORBIDDEN
            requestCount.get() == 1
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "client does not retry failed POST create requests"() {
        given:
        AtomicInteger requestCount = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/tickets") { exchange ->
            requestCount.incrementAndGet()
            byte[] response = "{\"error\":\"Internal Server Error\"}".getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(500, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "valid-token"
        ])
        TicketClient client = ctx.getBean(TicketClient)

        TicketComment comment = new TicketComment().setBody("Test body")
        TicketCreateInput input = new TicketCreateInput(comment).setRawSubject("Test subject")
        TicketCreateRequest request = new TicketCreateRequest(input)

        when:
        client.createTicket(request).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll {
            err.status == HttpStatus.INTERNAL_SERVER_ERROR
            requestCount.get() == 1
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "client retries on HTTP 429 using Retry-After header and succeeds"() {
        given:
        AtomicInteger requestCount = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/tickets/300") { exchange ->
            int attempt = requestCount.incrementAndGet()
            if (attempt == 1) {
                byte[] errorBytes = "{\"error\":\"Too Many Requests\"}".getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.responseHeaders.add("Retry-After", "1")
                exchange.sendResponseHeaders(429, errorBytes.length)
                exchange.responseBody.write(errorBytes)
                exchange.close()
            } else {
                byte[] successBytes = "{\"ticket\":{\"id\":300,\"subject\":\"Recovered Ticket\",\"requester_id\":12345}}".getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(200, successBytes.length)
                exchange.responseBody.write(successBytes)
                exchange.close()
            }
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "valid-token"
        ])
        TicketClient client = ctx.getBean(TicketClient)

        when:
        TicketResponse response = client.showTicket(300L).block()

        then:
        verifyAll {
            response != null
            response.ticket.id == 300L
            requestCount.get() == 2
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }
}
