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

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.filter.ClientFilterChain
import lol.pbu.z4j.ratelimit.RateLimitConfiguration
import lol.pbu.z4j.ratelimit.RateLimitTracker
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

class RateLimitFilterSpec extends Specification {

    def "filter delays request when approaching limit and auto wait is enabled"() {
        given:
        def tracker = new RateLimitTracker() {
            @Override
            boolean isApproachingLimit(int threshold) {
                return true
            }
        }
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(true)
        config.setApproachThreshold(1)
        config.setWaitDurationSeconds(5) // use 5 seconds for tests

        def filter = new RateLimitFilter(tracker, config)

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/users"
        request.getMethodName() >> "GET"
        request.getUri() >> URI.create("https://z.com/api/v2/users")

        def chain = Mock(ClientFilterChain)
        def response = Mock(HttpResponse)

        def headers = Mock(HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers
        response.getStatus() >> HttpStatus.OK

        chain.proceed(request) >> Flux.just(response)

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
            boolean isApproachingLimit(int threshold) {
                return true
            }
        }
        def config = new RateLimitConfiguration()
        config.setAutoWaitEnabled(false)
        config.setApproachThreshold(1)
        config.setWaitDurationSeconds(5)

        def filter = new RateLimitFilter(tracker, config)

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/users"
        request.getMethodName() >> "GET"
        request.getUri() >> URI.create("https://z.com/api/v2/users")

        def chain = Mock(ClientFilterChain)
        def response = Mock(HttpResponse)

        def headers = Mock(HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers
        response.getStatus() >> HttpStatus.OK

        chain.proceed(request) >> Flux.just(response)

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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/tickets"
        request.getMethodName() >> "GET"
        request.getUri() >> URI.create("https://z.com/api/v2/tickets")

        def response = Mock(HttpResponse)
        def headers = Mock(HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers
        response.getStatus() >> HttpStatus.OK

        def chain = Mock(ClientFilterChain)
        chain.proceed(request) >> Flux.just(response)

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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/articles"
        request.getMethodName() >> "GET"
        request.getUri() >> URI.create("https://z.com/api/v2/articles")

        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "1")
        def exception429 = new HttpClientResponseException("Rate limit exceeded", response429)
        def responseOk = HttpResponse.ok("success")

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/sections"
        request.getMethodName() >> "GET"
        request.getUri() >> URI.create("https://z.com/api/v2/sections")

        def cloudflareHtml = "<html><head><title>400 Bad Request</title></head><body><center><h1>400 Bad Request</h1></center><hr><center>cloudflare</center></body></html>"
        def response400 = HttpResponse.status(HttpStatus.BAD_REQUEST)
                .header("Server", "cloudflare")
                .body(cloudflareHtml)

        def exception400 = new HttpClientResponseException("Client ':zendesk': " + cloudflareHtml, response400)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/tickets"
        request.getMethodName() >> "POST"
        request.getUri() >> URI.create("https://z.com/api/v2/tickets")

        def response400 = HttpResponse.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\":\"RecordInvalid\",\"description\":\"Record validation failed\"}")

        def exception400 = new HttpClientResponseException("Validation error", response400)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/articles"
        request.getMethodName() >> "GET"
        request.getUri() >> URI.create("https://z.com/api/v2/articles")

        def response429 = HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "0")
        def exception429 = new HttpClientResponseException("Rate limit exceeded", response429)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/articles"
        request.getMethodName() >> "GET"
        request.getUri() >> URI.create("https://z.com/api/v2/articles")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/users/me"
        request.getMethodName() >> "GET"
        request.getMethod() >> HttpMethod.GET
        request.getUri() >> URI.create("https://z.com/api/v2/users/me")

        def response401 = HttpResponse.status(HttpStatus.UNAUTHORIZED)
        def exception401 = new HttpClientResponseException("Unauthorized", response401)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/tickets"
        request.getMethodName() >> "GET"
        request.getMethod() >> HttpMethod.GET
        request.getUri() >> URI.create("https://z.com/api/v2/tickets")

        def response403 = HttpResponse.status(HttpStatus.FORBIDDEN)
        def exception403 = new HttpClientResponseException("Forbidden", response403)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/tickets"
        request.getMethodName() >> "POST"
        request.getMethod() >> HttpMethod.POST
        request.getUri() >> URI.create("https://z.com/api/v2/tickets")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/tickets"
        request.getMethodName() >> "POST"
        request.getMethod() >> HttpMethod.POST
        request.getUri() >> URI.create("https://z.com/api/v2/tickets")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> path
        request.getMethodName() >> method.name()
        request.getMethod() >> method
        request.getUri() >> URI.create("https://z.com" + path)

        def response503 = HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
        def exception503 = new HttpClientResponseException("Service Unavailable", response503)
        def responseOk = HttpResponse.ok("recovered")

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/tickets/1"
        request.getMethodName() >> "GET"
        request.getMethod() >> HttpMethod.GET
        request.getUri() >> URI.create("https://z.com/api/v2/tickets/1")

        def response500 = HttpResponse.serverError()
        def exception500 = new HttpClientResponseException("Server error", response500)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/help_center/en-us/articles/123/attachments"
        request.getMethodName() >> "POST"
        request.getMethod() >> HttpMethod.POST
        request.getUri() >> URI.create("https://z.com/api/v2/help_center/en-us/articles/123/attachments")

        def response409 = HttpResponse.status(HttpStatus.CONFLICT)
        def exception409 = new HttpClientResponseException("Conflict", response409)
        def responseOk = HttpResponse.created(HttpResponse.ok())

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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

        def request = Mock(MutableHttpRequest)
        request.getPath() >> "/api/v2/help_center/en-us/articles/123/attachments"
        request.getMethodName() >> "POST"
        request.getMethod() >> HttpMethod.POST
        request.getUri() >> URI.create("https://z.com/api/v2/help_center/en-us/articles/123/attachments")

        def response409 = HttpResponse.status(HttpStatus.CONFLICT)
        def exception409 = new HttpClientResponseException("Conflict", response409)

        def chain = Mock(ClientFilterChain)
        def attempts = 0
        chain.proceed(request) >> {
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
}
