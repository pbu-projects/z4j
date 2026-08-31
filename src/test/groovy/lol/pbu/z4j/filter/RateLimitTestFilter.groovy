package lol.pbu.z4j.filter

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.ClientFilter
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.exceptions.ReadTimeoutException
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import reactor.util.retry.Retry

import java.time.Duration

@ClientFilter("/**")
class RateLimitTestFilter implements HttpClientFilter {

    @Override
    Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        return Mono.from(chain.proceed(request))
                .flatMap { response ->
                    if (response.status().code == 429) {
                        return Mono.error(new RateLimitRetryException("Rate limit (429) encountered in test for ${request.path}"))
                    }
                    return Mono.just(response)
                }
                .retryWhen(Retry.fixedDelay(5, Duration.ofMinutes(1)).jitter(0.5d)
                        .filter { throwable ->
                            if (throwable instanceof RateLimitRetryException) {
                                System.err.println(throwable.message + ". Sleeping and retrying...")
                                return true
                            }
                            if (throwable instanceof ReadTimeoutException) {
                                System.err.println("ReadTimeoutException encountered in test for ${request.path}. Retrying...")
                                return true
                            }
                            return false
                        })
    }

    static class RateLimitRetryException extends RuntimeException {
        RateLimitRetryException(String message) {
            super(message)
        }
    }
}
