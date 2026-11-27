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
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.exceptions.ReadTimeoutException
import io.micronaut.http.filter.ClientFilterChain
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import lol.pbu.z4j.ratelimit.RateLimitConfiguration
import lol.pbu.z4j.ratelimit.RateLimitSnapshot
import lol.pbu.z4j.ratelimit.RateLimitTracker
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

class RateLimitFilterSpec extends Specification {

    def "filter delays request when approaching limit and auto wait is enabled"() {
        given:
        def tracker = new RateLimitTracker() {
            @Override
            boolean isApproachingLimit(int threshold, Duration fallbackWait) {
                return true
            }
        }
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(true)
        config.setApproachThreshold(1)
        config.setWaitDurationSeconds(5) // use 5 seconds for tests

        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/users")

        def chain = Mock(ClientFilterChain)
        def response = Mock(HttpResponse)

        def headers = Mock(HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers
        response.getStatus() >> HttpStatus.OK

        chain.proceed(_ as MutableHttpRequest) >> Flux.just(response)

        when:
        def start = System.currentTimeMillis()
        def result = Mono.from(filter.doFilter(request, chain)).block()
        def duration = System.currentTimeMillis() - start

        then:
        verifyAll {
            result == response
            duration >= 4900 // Should have waited roughly 5 seconds
        }
    }

    def "filter does not delay request when auto wait is disabled"() {
        given:
        def tracker = new RateLimitTracker() {
            @Override
            boolean isApproachingLimit(int threshold, Duration fallbackWait) {
                return true
            }
        }
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setApproachThreshold(1)
        config.setWaitDurationSeconds(5)

        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/users")

        def chain = Mock(ClientFilterChain)
        def response = Mock(HttpResponse)

        def headers = Mock(HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers
        response.getStatus() >> HttpStatus.OK

        chain.proceed(_ as MutableHttpRequest) >> Flux.just(response)

        when:
        def start = System.currentTimeMillis()
        def result = Mono.from(filter.doFilter(request, chain)).block()
        def duration = System.currentTimeMillis() - start

        then:
        verifyAll {
            result == response
            duration < 1000 // Should not wait
        }
    }

    def "filter paces outbound requests when requestsPerSecond is configured"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setRequestsPerSecond(5.0) // 200ms spacing between requests
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/tickets")

        def response = Mock(HttpResponse)
        def headers = Mock(HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers
        response.getStatus() >> HttpStatus.OK

        def chain = Mock(ClientFilterChain)
        chain.proceed(_ as MutableHttpRequest) >> Flux.just(response)

        when: "two requests are sent sequentially"
        def start = System.currentTimeMillis()
        def result1 = Mono.from(filter.doFilter(request, chain)).block()
        def result2 = Mono.from(filter.doFilter(request, chain)).block()
        def totalDuration = System.currentTimeMillis() - start

        then:
        verifyAll {
            result1 == response
            result2 == response
            totalDuration >= 180
        }
    }

    def "calculatePacingDelay returns zero when rate limit is disabled"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setRequestsPerSecond(0.0)
        def filter = new RateLimitFilter(tracker, config)

        expect:
        filter.calculatePacingDelay().isZero()
    }

    def "filter retries on HTTP 429 using Retry-After header and succeeds on retry"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/articles")

        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "1")
        def exception429 = new HttpClientResponseException("Rate limit exceeded", response429)
        def responseOk = HttpResponse.ok("success")

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            if (attempts == 1) {
                return Flux.error(exception429)
            } else {
                return Flux.just(responseOk)
            }
        }

        when:
        def start = System.currentTimeMillis()
        def result = Mono.from(filter.doFilter(request, chain)).block()
        def duration = System.currentTimeMillis() - start

        then:
        verifyAll {
            result.status == HttpStatus.OK
            attempts == 2
            duration >= 900 // waited for the 1 second Retry-After
        }
    }

    def "filter maps Cloudflare 400 HTML block to 403 Forbidden without retrying"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/sections")

        def cloudflareHtml = "<html><head><title>400 Bad Request</title></head><body><center><h1>400 Bad Request</h1></center><hr><center>cloudflare</center></body></html>"
        def response400 = HttpResponse.status(HttpStatus.BAD_REQUEST)
                .header("Server", "cloudflare")
                .body(cloudflareHtml)

        def exception400 = new HttpClientResponseException("Client ':zendesk': " + cloudflareHtml, response400)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception400)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.FORBIDDEN
            attempts == 1
        }
    }

    def "filter does not retry normal 400 Bad Request that is not a Cloudflare block"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.POST("https://z.com/api/v2/tickets", "{}")

        def response400 = HttpResponse.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\":\"RecordInvalid\",\"description\":\"Record validation failed\"}")

        def exception400 = new HttpClientResponseException("Validation error", response400)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception400)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.BAD_REQUEST
            attempts == 1
        }
    }

    def "filter terminates retry and propagates exception when 5 attempts are exceeded"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setWaitDurationSeconds(0)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/articles")

        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "0")
        def exception429 = new HttpClientResponseException("Rate limit exceeded", response429)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception429)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.TOO_MANY_REQUESTS
            attempts == 6 // initial attempt (attempt 0) + 5 retries (attempts 1 to 5)
        }
    }

    def "filter propagates non-429 exceptions immediately without retrying"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/articles")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception500)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.INTERNAL_SERVER_ERROR
            attempts == 1
        }
    }

    def "filter propagates 401 Unauthorized immediately without retry"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/users/me")

        def response401 = HttpResponse.status(HttpStatus.UNAUTHORIZED)
        def exception401 = new HttpClientResponseException("Unauthorized", response401)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception401)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.UNAUTHORIZED
            attempts == 1
        }
    }

    def "filter propagates 403 Forbidden immediately without retry"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/tickets")

        def response403 = HttpResponse.status(HttpStatus.FORBIDDEN)
        def exception403 = new HttpClientResponseException("Forbidden", response403)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception403)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.FORBIDDEN
            attempts == 1
        }
    }

    def "filter does not retry failed POST create requests"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.POST("https://z.com/api/v2/tickets", "{}")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception500)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.INTERNAL_SERVER_ERROR
            attempts == 1
        }
    }

    def "filter does not retry failed POST create requests even when transient retries are configured"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setMaxTransientRetries(3)
        config.setTransientRetryDelaySeconds(0)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.POST("https://z.com/api/v2/tickets", "{}")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception500)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.INTERNAL_SERVER_ERROR
            attempts == 1
        }
    }

    def "filter retries idempotent #method request on transient 5xx error when configured"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setMaxTransientRetries(2)
        config.setTransientRetryDelaySeconds(0)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.create(method, "https://z.com" + path)

        def response503 = HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
        def exception503 = new HttpClientResponseException("Service Unavailable", response503)
        def responseOk = HttpResponse.ok("recovered")

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            if (attempts == 1) {
                return Flux.error(exception503)
            } else {
                return Flux.just(responseOk)
            }
        }

        when:
        def result = Mono.from(filter.doFilter(request, chain)).block()

        then:
        verifyAll {
            result.status == HttpStatus.OK
            attempts == 2
        }

        where:
        method            | path
        HttpMethod.GET    | "/api/v2/tickets/1"
        HttpMethod.PUT    | "/api/v2/tickets/1"
        HttpMethod.DELETE | "/api/v2/tickets/1"
    }

    def "filter terminates transient retry when maximum attempts are reached"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setMaxTransientRetries(2)
        config.setTransientRetryDelaySeconds(0)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/tickets/1")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception500)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.INTERNAL_SERVER_ERROR
            attempts == 3 // initial attempt + 2 retries
        }
    }

    def "filter retries on HTTP 409 Conflict and succeeds on retry"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.POST("https://z.com/api/v2/help_center/en-us/articles/123/attachments", "{}")

        def response409 = HttpResponse.status(HttpStatus.CONFLICT)
        def exception409 = new HttpClientResponseException("Conflict", response409)
        def responseOk = HttpResponse.created(HttpResponse.ok())

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            if (attempts == 1) {
                return Flux.error(exception409)
            } else {
                return Flux.just(responseOk)
            }
        }

        when:
        def result = Mono.from(filter.doFilter(request, chain)).block()

        then:
        verifyAll {
            result.status == HttpStatus.CREATED
            attempts == 2
        }
    }

    def "filter terminates 409 Conflict retry and propagates exception when 2 attempts are exceeded"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.POST("https://z.com/api/v2/help_center/en-us/articles/123/attachments", "{}")

        def response409 = HttpResponse.status(HttpStatus.CONFLICT)
        def exception409 = new HttpClientResponseException("Conflict", response409)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(_ as MutableHttpRequest) >> {
            attempts++
            return Flux.error(exception409)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.CONFLICT
            attempts == 3 // initial attempt + 2 retries
        }
    }

    def "filter coordinates shared retryAfterUntil across concurrent requests"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def req1 = HttpRequest.GET("https://z.com/api/v2/tickets/1")
        def req2 = HttpRequest.GET("https://z.com/api/v2/tickets/2")

        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "2")
        def exception429 = new HttpClientResponseException("Rate limit exceeded", response429)
        def responseOk = HttpResponse.ok("ok")

        def chain = Mock(ClientFilterChain)
        def req1Attempts = 0
        def req2ProceedTime = 0L
        chain.proceed(_ as MutableHttpRequest) >> { MutableHttpRequest passed ->
            if (passed.path.endsWith("/1")) {
                req1Attempts++
                return req1Attempts == 1 ? Flux.error(exception429) : Flux.just(responseOk)
            }
            req2ProceedTime = System.currentTimeMillis()
            return Flux.just(responseOk)
        }

        when: "first request hits 429 and second request starts immediately after"
        def start = System.currentTimeMillis()
        def mono1 = Mono.from(filter.doFilter(req1, chain)).subscribe()
        // Wait briefly so req1 receives 429 and records retryAfterUntil
        Thread.sleep(150)
        def result2 = Mono.from(filter.doFilter(req2, chain)).block()
        def elapsed = req2ProceedTime - start

        then: "second request was gated by shared retryAfterUntil before proceeding"
        verifyAll {
            result2.status == HttpStatus.OK
            elapsed >= 1800
        }

        cleanup:
        mono1?.dispose()
    }

    def "shouldAutoWait uses ratelimit-reset instead of blind 60s waitDurationSeconds"() {
        given:
        def tracker = new RateLimitTracker()
        tracker.recordSnapshot(RateLimitSnapshot.builder()
                .statusCode(200)
                .globalRemaining(0)
                .globalResetSeconds(1)
                .timestamp(Instant.now())
                .build())
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(true)
        config.setApproachThreshold(1)
        config.setWaitDurationSeconds(60)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/users")
        def response = HttpResponse.ok("ok")
        def chain = Mock(ClientFilterChain) {
            1 * proceed(_ as MutableHttpRequest) >> Flux.just(response)
        }

        when:
        def start = System.currentTimeMillis()
        def result = Mono.from(filter.doFilter(request, chain)).block()
        def duration = System.currentTimeMillis() - start

        then: "waited roughly 1s from ratelimit-reset rather than 60s"
        verifyAll {
            result == response
            duration >= 850
            duration < 5000
        }
    }

    def "filter applies pacing on retries as well as initial attempts"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setRequestsPerSecond(4.0) // 250ms per request
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/articles")
        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "0")
        def exception429 = new HttpClientResponseException("Rate limit exceeded", response429)
        def responseOk = HttpResponse.ok("ok")

        def sendTimes = []
        def chain = Mock(ClientFilterChain) {
            3 * proceed(_ as MutableHttpRequest) >> {
                sendTimes << System.currentTimeMillis()
                if (sendTimes.size() < 3) {
                    return Flux.error(exception429)
                }
                return Flux.just(responseOk)
            }
        }

        when:
        def result = Mono.from(filter.doFilter(request, chain)).block()

        then:
        verifyAll {
            result.status == HttpStatus.OK
            sendTimes.size() == 3
            (sendTimes[1] - sendTimes[0]) >= 220
            (sendTimes[2] - sendTimes[1]) >= 220
        }
    }

    def "filter copies MutableHttpRequest per retry so mutations do not leak across attempts"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def original = HttpRequest.POST("https://z.com/api/v2/tickets", '{"subject":"test"}')
                .header("X-Original", "keep")
        original.setAttribute("service_id", "zendesk")

        def seenRequests = []
        def seenAttemptHeaders = []
        def seenChannelAttr = []
        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "0")
        def exception429 = new HttpClientResponseException("Rate limit exceeded", response429)

        def chain = Mock(ClientFilterChain) {
            2 * proceed(_ as MutableHttpRequest) >> { MutableHttpRequest attemptReq ->
                seenRequests << attemptReq
                seenAttemptHeaders << attemptReq.headers.get("X-Mutated")
                seenChannelAttr << attemptReq.getAttribute("netty_channel", String).orElse(null)
                attemptReq.header("X-Mutated", "leaked")
                attemptReq.setAttribute("netty_channel", "stale-channel")
                if (seenRequests.size() == 1) {
                    return Flux.error(exception429)
                }
                return Flux.just(HttpResponse.ok("ok"))
            }
        }

        when:
        def result = Mono.from(filter.doFilter(original, chain)).block()

        then:
        verifyAll {
            result.status == HttpStatus.OK
            seenRequests.size() == 2
            !seenRequests[0].is(original)
            !seenRequests[1].is(original)
            !seenRequests[0].is(seenRequests[1])
            seenAttemptHeaders == [null, null]
            seenChannelAttr == [null, null]
            original.headers.get("X-Original") == "keep"
            original.headers.get("X-Mutated") == null
            original.getAttribute("service_id", String).orElse(null) == "zendesk"
            seenRequests[1].headers.get("X-Original") == "keep"
            seenRequests[1].getAttribute("service_id", String).orElse(null) == "zendesk"
        }
    }

    def "filter supports HTTP-date Retry-After headers as well as integer seconds"() {
        given:
        def fixedNow = Instant.parse("2026-05-01T12:00:00Z")
        def clock = Clock.fixed(fixedNow, ZoneOffset.UTC)
        def tracker = new RateLimitTracker(clock)
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config, clock)

        def futureDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(
                ZonedDateTime.ofInstant(fixedNow.plusSeconds(1), ZoneOffset.UTC))
        def pastDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(
                ZonedDateTime.ofInstant(fixedNow.minusSeconds(10), ZoneOffset.UTC))

        def futureHeaders = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("Retry-After", futureDate).headers
        def pastHeaders = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("Retry-After", pastDate).headers
        def intHeaders = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("Retry-After", "7").headers

        expect:
        verifyAll {
            filter.parseRetryAfterDuration(futureHeaders) == Duration.ofSeconds(1)
            filter.parseRetryAfterDuration(pastHeaders) == Duration.ZERO
            filter.parseRetryAfterDuration(intHeaders) == Duration.ofSeconds(7)
        }

        when: "429 response returns RFC 1123 HTTP-date Retry-After"
        def capturedSnapshots = []
        tracker.addListener { snapshot -> capturedSnapshots << snapshot }
        def request = HttpRequest.GET("https://z.com/api/v2/tickets")
        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", futureDate)
        def exception429 = new HttpClientResponseException("Too many requests", response429)
        def attempts = 0
        def chain = Mock(ClientFilterChain) {
            2 * proceed(_ as MutableHttpRequest) >> {
                attempts++
                return attempts == 1 ? Flux.error(exception429) : Flux.just(HttpResponse.ok("ok"))
            }
        }
        def start = System.currentTimeMillis()
        def res = Mono.from(filter.doFilter(request, chain)).block()
        def elapsed = System.currentTimeMillis() - start

        then:
        verifyAll {
            res.status == HttpStatus.OK
            attempts == 2
            elapsed >= 900
            capturedSnapshots.size() == 2
            capturedSnapshots[0].statusCode == 429
            capturedSnapshots[0].retryAfterSeconds == 1
            capturedSnapshots[1].statusCode == 200
        }
    }

    def "filter preserves cf-ray and diagnostic headers and body when mapping Cloudflare block"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        def filter = new RateLimitFilter(tracker, config)

        def request = HttpRequest.GET("https://z.com/api/v2/sections")
        def cloudflareHtml = "<html><head><title>400 Bad Request</title></head><body><center>cloudflare</center></body></html>"
        def response400 = HttpResponse.status(HttpStatus.BAD_REQUEST)
                .header("Server", "cloudflare")
                .header("cf-ray", "8abc123456def-SJC")
                .header("Content-Type", MediaType.TEXT_HTML)
                .body(cloudflareHtml)
        def exception400 = new HttpClientResponseException("Client ':zendesk': Bad Request", response400)

        def chain = Mock(ClientFilterChain) {
            1 * proceed(_ as MutableHttpRequest) >> Flux.error(exception400)
        }

        when:
        Mono.from(filter.doFilter(request, chain)).block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll(err) {
            status == HttpStatus.FORBIDDEN
            response.headers.get("cf-ray") == "8abc123456def-SJC"
            response.headers.get("Server") == "cloudflare"
            response.getBody(String).orElse(null) == cloudflareHtml
        }
    }

    def "rate-limit auto-wait longer than read-timeout succeeds without ReadTimeoutException"() {
        given:
        AtomicInteger callCount = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/locales") { exchange ->
            callCount.incrementAndGet()
            byte[] body = '{"locales":[]}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.responseHeaders.add("X-Rate-Limit", "700")
            exchange.responseHeaders.add("X-Rate-Limit-Remaining", "0")
            exchange.responseHeaders.add("ratelimit-reset", "2")
            exchange.sendResponseHeaders(200, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"         : baseUrl,
                "micronaut.http.services.zendesk.oauth.token" : "valid-token",
                "micronaut.http.services.zendesk.read-timeout": "1s",
                "z4j.rate-limit.auto-wait-enabled"            : "true",
                "z4j.rate-limit.approach-threshold"           : "1",
                "z4j.rate-limit.wait-duration-seconds"        : "2"
        ])
        LocaleClient client = ctx.getBean(LocaleClient)

        when: "first call drains quota and second call waits 2s (> 1s read-timeout)"
        client.listLocales().block()
        def start = System.currentTimeMillis()
        def second = client.listLocales().block()
        def secondElapsed = System.currentTimeMillis() - start

        then:
        verifyAll {
            second != null
            callCount.get() == 2
            secondElapsed >= 1500
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "HTTP 429 Retry-After longer than read-timeout succeeds without ReadTimeoutException while stalled read still times out"() {
        given:
        AtomicInteger retryCalls = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/locales") { exchange ->
            int n = retryCalls.incrementAndGet()
            if (n == 1) {
                byte[] body = '{"error":"Too Many Requests"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.responseHeaders.add("Retry-After", "2")
                exchange.sendResponseHeaders(429, body.length)
                exchange.responseBody.write(body)
                exchange.close()
            } else if (n == 2) {
                byte[] body = '{"locales":[]}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(200, body.length)
                exchange.responseBody.write(body)
                exchange.close()
            } else {
                // Stall longer than 1s read-timeout to prove socket read-timeout still fires
                Thread.sleep(1600)
                byte[] body = '{"locales":[]}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(200, body.length)
                exchange.responseBody.write(body)
                exchange.close()
            }
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"         : baseUrl,
                "micronaut.http.services.zendesk.oauth.token" : "valid-token",
                "micronaut.http.services.zendesk.read-timeout": "1s",
                "z4j.rate-limit.auto-wait-enabled"            : "false"
        ])
        LocaleClient client = ctx.getBean(LocaleClient)

        when: "429 Retry-After 2s exceeds 1s read-timeout"
        def recovered = client.listLocales().block()

        then: "retry waits and succeeds"
        verifyAll {
            recovered != null
            retryCalls.get() == 2
        }

        when: "a real network stall exceeds 1s read-timeout"
        client.listLocales().block()

        then: "socket ReadTimeoutException still fires"
        thrown(ReadTimeoutException)

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "real client stops at 6 wire requests on persistent HTTP 429"() {
        given:
        AtomicInteger wireRequests = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/locales") { exchange ->
            wireRequests.incrementAndGet()
            byte[] body = '{"error":"Too Many Requests"}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.responseHeaders.add("Retry-After", "0")
            exchange.sendResponseHeaders(429, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "valid-token",
                "z4j.rate-limit.auto-wait-enabled"           : "false"
        ])
        LocaleClient client = ctx.getBean(LocaleClient)

        when:
        client.listLocales().block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll {
            err.status == HttpStatus.TOO_MANY_REQUESTS
            wireRequests.get() == 6
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "real client stops at 3 wire requests on persistent HTTP 409"() {
        given:
        AtomicInteger wireRequests = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/locales") { exchange ->
            wireRequests.incrementAndGet()
            byte[] body = '{"error":"Conflict"}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(409, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "valid-token",
                "z4j.rate-limit.auto-wait-enabled"           : "false"
        ])
        LocaleClient client = ctx.getBean(LocaleClient)

        when:
        client.listLocales().block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll {
            err.status == HttpStatus.CONFLICT
            wireRequests.get() == 3
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "real client stops at 2 wire requests on persistent HTTP 503 when max transient retries is 1"() {
        given:
        AtomicInteger wireRequests = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/locales") { exchange ->
            wireRequests.incrementAndGet()
            byte[] body = '{"error":"Service Unavailable"}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(503, body.length)
            exchange.responseBody.write(body)
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"          : baseUrl,
                "micronaut.http.services.zendesk.oauth.token"  : "valid-token",
                "z4j.rate-limit.auto-wait-enabled"             : "false",
                "z4j.rate-limit.max-transient-retries"         : "1",
                "z4j.rate-limit.transient-retry-delay-seconds" : "1"
        ])
        LocaleClient client = ctx.getBean(LocaleClient)

        when:
        client.listLocales().block()

        then:
        def err = thrown(HttpClientResponseException)
        verifyAll {
            err.status == HttpStatus.SERVICE_UNAVAILABLE
            wireRequests.get() == 2
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "real client notifies RateLimitListener once per wire response across 429 retries"() {
        given:
        AtomicInteger wireRequests = new AtomicInteger(0)
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/locales") { exchange ->
            int attempt = wireRequests.incrementAndGet()
            if (attempt <= 2) {
                byte[] body = '{"error":"Too Many Requests"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.responseHeaders.add("Retry-After", "0")
                exchange.sendResponseHeaders(429, body.length)
                exchange.responseBody.write(body)
                exchange.close()
            } else {
                byte[] body = '{"locales":[]}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.responseHeaders.add("X-Rate-Limit-Remaining", "699")
                exchange.sendResponseHeaders(200, body.length)
                exchange.responseBody.write(body)
                exchange.close()
            }
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : baseUrl,
                "micronaut.http.services.zendesk.oauth.token": "valid-token",
                "z4j.rate-limit.auto-wait-enabled"           : "false"
        ])
        def listenerSnapshots = new CopyOnWriteArrayList<RateLimitSnapshot>()
        ctx.getBean(RateLimitTracker).addListener { snapshot -> listenerSnapshots << snapshot }
        LocaleClient client = ctx.getBean(LocaleClient)

        when:
        def result = client.listLocales().block()

        then:
        verifyAll {
            result != null
            wireRequests.get() == 3
            listenerSnapshots*.statusCode == [429, 429, 200]
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "OAuthClientCredentialsAuthFilter and RateLimitFilter preserve Authorization header across 429 retry"() {
        given:
        AtomicInteger tokenRequests = new AtomicInteger(0)
        def wireAuthHeaders = new CopyOnWriteArrayList<String>()
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/oauth/tokens") { exchange ->
            tokenRequests.incrementAndGet()
            byte[] response = '{"access_token":"oauth-retry-token","token_type":"Bearer","expires_in":3600,"scope":"hc:read"}'
                    .getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.createContext("/api/v2/locales") { exchange ->
            wireAuthHeaders << exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            if (wireAuthHeaders.size() == 1) {
                byte[] body = '{"error":"Too Many Requests"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.responseHeaders.add("Retry-After", "0")
                exchange.sendResponseHeaders(429, body.length)
                exchange.responseBody.write(body)
            } else {
                byte[] body = '{"locales":[]}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(200, body.length)
                exchange.responseBody.write(body)
            }
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"                : baseUrl,
                "micronaut.http.services.zendesk.oauth.client-id"    : "cid",
                "micronaut.http.services.zendesk.oauth.client-secret": "csec",
                "z4j.rate-limit.auto-wait-enabled"                   : "false"
        ])
        LocaleClient client = ctx.getBean(LocaleClient)

        when:
        def result = client.listLocales().block()

        then:
        verifyAll {
            result != null
            tokenRequests.get() == 1
            wireAuthHeaders == ["Bearer oauth-retry-token", "Bearer oauth-retry-token"]
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "OAuthClientCredentialsAuthFilter and RateLimitFilter refresh OAuth token on 429 then 401 then 200"() {
        given:
        AtomicInteger tokenRequests = new AtomicInteger(0)
        def wireAuthHeaders = new CopyOnWriteArrayList<String>()
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/oauth/tokens") { exchange ->
            int n = tokenRequests.incrementAndGet()
            byte[] response = "{\"access_token\":\"oauth-token-${n}\",\"token_type\":\"Bearer\",\"expires_in\":3600,\"scope\":\"hc:read\"}"
                    .getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.createContext("/api/v2/locales") { exchange ->
            wireAuthHeaders << exchange.requestHeaders.getFirst(HttpHeaders.AUTHORIZATION)
            int attempt = wireAuthHeaders.size()
            if (attempt == 1) {
                byte[] body = '{"error":"Too Many Requests"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.responseHeaders.add("Retry-After", "0")
                exchange.sendResponseHeaders(429, body.length)
                exchange.responseBody.write(body)
            } else if (attempt == 2) {
                byte[] body = '{"error":"Unauthorized"}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(401, body.length)
                exchange.responseBody.write(body)
            } else {
                byte[] body = '{"locales":[]}'.getBytes(StandardCharsets.UTF_8)
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                exchange.sendResponseHeaders(200, body.length)
                exchange.responseBody.write(body)
            }
            exchange.close()
        }
        server.start()
        String baseUrl = "http://127.0.0.1:${server.address.port}"

        ApplicationContext ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"                : baseUrl,
                "micronaut.http.services.zendesk.oauth.client-id"    : "cid",
                "micronaut.http.services.zendesk.oauth.client-secret": "csec",
                "z4j.rate-limit.auto-wait-enabled"                   : "false"
        ])
        LocaleClient client = ctx.getBean(LocaleClient)

        when:
        def result = client.listLocales().block()

        then:
        verifyAll {
            result != null
            tokenRequests.get() == 2
            wireAuthHeaders == ["Bearer oauth-token-1", "Bearer oauth-token-1", "Bearer oauth-token-2"]
        }

        cleanup:
        ctx?.close()
        server?.stop(0)
    }

    def "handleResponse skips endpoint rate limit header when headers.get returns null"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        def filter = new RateLimitFilter(tracker, config)
        def captured = []
        tracker.addListener { snapshot -> captured << snapshot }

        def headers = Mock(HttpHeaders) {
            names() >> (["zendesk-ratelimit-null-header", "zendesk-ratelimit-tickets-show"] as Set)
            get("zendesk-ratelimit-null-header") >> null
            get("zendesk-ratelimit-tickets-show") >> "total=200; remaining=199; resets=15"
            get(_ as String) >> null
        }
        def response = Mock(HttpResponse) {
            code() >> 200
            getHeaders() >> headers
        }
        def request = HttpRequest.GET("https://z.com/api/v2/tickets/1")

        when:
        filter.handleResponse(request, response)

        then:
        noExceptionThrown()
        verifyAll {
            captured.size() == 1
            captured[0].endpointLimits.size() == 1
            captured[0].endpointLimits["tickets-show"]?.remaining == 199L
            !captured[0].endpointLimits.containsKey("null-header")
        }
    }

    def "handleResponse safely handles null response, null request, and empty headers"() {
        given:
        def tracker = new RateLimitTracker()
        def config = new RateLimitConfiguration()
        def filter = new RateLimitFilter(tracker, config)
        def captured = []
        tracker.addListener { snapshot -> captured << snapshot }

        def responseWithEmptyHeaders = HttpResponse.ok()
        def requestWithUri = HttpRequest.GET("https://z.com/api/v2/tickets")

        when:
        filter.handleResponse(requestWithUri, null)
        filter.handleResponse(requestWithUri, responseWithEmptyHeaders)
        filter.handleResponse(null, responseWithEmptyHeaders)

        then:
        noExceptionThrown()
        verifyAll {
            captured.size() == 2
            captured[0].statusCode == 200
            captured[0].requestMethod == "GET"
            captured[0].requestPath == "/api/v2/tickets"
            captured[0].requestUri == "https://z.com/api/v2/tickets"
            captured[0].globalLimit == null
            captured[0].endpointLimits.isEmpty()
            captured[1].statusCode == 200
            captured[1].requestMethod == null
            captured[1].requestPath == null
            captured[1].requestUri == null
            captured[1].globalLimit == null
            captured[1].endpointLimits.isEmpty()
        }
    }
}
