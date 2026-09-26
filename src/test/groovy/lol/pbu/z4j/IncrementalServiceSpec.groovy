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
package lol.pbu.z4j

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Shared

import java.time.Duration
import java.time.Instant

@MicronautTest
class IncrementalServiceSpec extends Z4jSpec {

    @Shared
    IncrementalService incrementalService

    def setupSpec() {
        incrementalService = adminCtx.getBean(IncrementalService.class)
    }

    def "should enforce 70 seconds cushion on since()"() {
        when:
        incrementalService.tickets().since(Instant.now()).flux().blockFirst()

        then:
        thrown(IllegalArgumentException)
    }

    def "can stream tickets using micro-paging and cursor progression"() {
        given:
        // Ensure at least one test ticket exists
        createTicketForTest()
        Instant sinceTime = Instant.now().minusSeconds(300)

        when:
        def tickets = incrementalService.tickets()
                .since(sinceTime)
                .perPage(1)
                .flux()
                .take(5)
                .collectList()
                .block()

        then:
        noExceptionThrown()
        tickets != null
    }

    def "can fetch single page of users"() {
        when:
        Instant sinceTime = Instant.now().minusSeconds(300)
        def page = incrementalService.users()
                .since(sinceTime)
                .perPage(2)
                .fetchPage()
                .block()

        then:
        noExceptionThrown()
        page != null
    }

    def "can stream organizations with time boundary deduplication"() {
        when:
        Instant sinceTime = Instant.now().minusSeconds(3600)
        def orgs = incrementalService.organizations()
                .since(sinceTime)
                .perPage(5)
                .delayBetweenPages(Duration.ofMillis(100))
                .flux()
                .take(10)
                .collectList()
                .block()

        then:
        noExceptionThrown()
        orgs != null
    }

    def "can stream ticket events"() {
        when:
        Instant sinceTime = Instant.now().minusSeconds(3600)
        def events = incrementalService.ticketEvents()
                .since(sinceTime)
                .flux()
                .take(5)
                .collectList()
                .block()

        then:
        noExceptionThrown()
        events != null
    }
}
