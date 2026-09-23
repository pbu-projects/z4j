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

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.simple.SimpleHttpRequest
import lol.pbu.z4j.ratelimit.RateLimitConfiguration
import lol.pbu.z4j.ratelimit.RateLimitSnapshot
import lol.pbu.z4j.ratelimit.RateLimitTracker
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification
import java.time.Duration

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
        
        def headers = Mock(io.micronaut.http.HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers

        response.getStatus() >> io.micronaut.http.HttpStatus.OK

        

        chain.proceed(request) >> Flux.just(response)

        when:
        def start = System.currentTimeMillis()
        def result = Mono.from(filter.doFilter(request, chain)).block()
        def duration = System.currentTimeMillis() - start

        then:
        result == response
        duration >= 4900 // Should have waited roughly 5 seconds
        
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
        
        def headers = Mock(io.micronaut.http.HttpHeaders)
        headers.names() >> ([] as Set)
        response.getHeaders() >> headers

        response.getStatus() >> io.micronaut.http.HttpStatus.OK

        // Even if approaching limit, should not wait because disabled
        

        chain.proceed(request) >> Flux.just(response)

        when:
        def start = System.currentTimeMillis()
        def result = Mono.from(filter.doFilter(request, chain)).block()
        def duration = System.currentTimeMillis() - start

        then:
        result == response
        duration < 1000 // Should not wait
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

        def response429 = HttpResponse.status(io.micronaut.http.HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "1")
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
        result.status == io.micronaut.http.HttpStatus.OK
        attempts == 2
        duration >= 900 // waited for the 1 second Retry-After
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

        def response429 = HttpResponse.status(io.micronaut.http.HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "0")
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
        err.status == io.micronaut.http.HttpStatus.TOO_MANY_REQUESTS
        attempts == 6 // initial attempt (attempt 0) + 5 retries (attempts 1 to 5)
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
        err.status == io.micronaut.http.HttpStatus.INTERNAL_SERVER_ERROR
        attempts == 1
    }
}
