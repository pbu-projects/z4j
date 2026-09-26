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
import lol.pbu.z4j.IncrementalService.CursorIncrementalBuilder
import lol.pbu.z4j.IncrementalService.ResourceType
import lol.pbu.z4j.IncrementalService.TimeIncrementalBuilder
import lol.pbu.z4j.model.Organization
import lol.pbu.z4j.model.Ticket
import lol.pbu.z4j.model.TicketEvent
import lol.pbu.z4j.model.User
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

    def "should enforce 70 seconds cushion on since() for cursor builder"() {
        when:
        incrementalService.tickets().since(Instant.now()).flux().blockFirst()

        then:
        thrown(IllegalArgumentException)
    }

    def "should enforce 70 seconds cushion on since() for time builder"() {
        when:
        incrementalService.organizations().since(Instant.now()).flux().blockFirst()

        then:
        thrown(IllegalArgumentException)
    }

    def "perPage exceeds 1000 throws IllegalArgumentException"() {
        when:
        incrementalService.tickets().perPage(1001)

        then:
        thrown(IllegalArgumentException)

        when:
        incrementalService.organizations().perPage(1001)

        then:
        thrown(IllegalArgumentException)
    }

    def "time-based builder requires since() before fetchPage or flux"() {
        when:
        incrementalService.organizations().fetchPage().block()

        then:
        thrown(IllegalArgumentException)

        when:
        incrementalService.organizations().flux().blockFirst()

        then:
        thrown(IllegalArgumentException)
    }

    def "since with null does not throw exception"() {
        when:
        def cursorBuilder = incrementalService.tickets().since((Instant) null)
        def timeBuilder = incrementalService.organizations().since((Instant) null)

        then:
        cursorBuilder != null
        timeBuilder != null
    }

    def "since with epoch seconds works"() {
        given:
        long seconds = Instant.now().minusSeconds(120).epochSecond

        when:
        def cursorBuilder = incrementalService.tickets().since(seconds)
        def timeBuilder = incrementalService.organizations().since(seconds)

        then:
        cursorBuilder != null
        timeBuilder != null
    }

    def "can fetch single page of tickets"() {
        when:
        Instant sinceTime = Instant.now().minusSeconds(300)
        def page = incrementalService.tickets()
                .since(sinceTime)
                .perPage(2)
                .fetchPage()
                .block()

        then:
        noExceptionThrown()
        page != null
        page.getResults() != null
    }

    def "can stream tickets using micro-paging and cursor progression"() {
        given:
        createTicketForTest()
        Instant sinceTime = Instant.now().minusSeconds(300)

        when:
        def tickets = incrementalService.tickets()
                .since(sinceTime)
                .perPage(1)
                .delayBetweenPages(Duration.ofMillis(10))
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

    def "can stream users"() {
        when:
        Instant sinceTime = Instant.now().minusSeconds(300)
        def users = incrementalService.users()
                .since(sinceTime)
                .perPage(2)
                .flux()
                .take(3)
                .collectList()
                .block()

        then:
        noExceptionThrown()
        users != null
    }

    def "can fetch single page of organizations"() {
        when:
        Instant sinceTime = Instant.now().minusSeconds(3600)
        def page = incrementalService.organizations()
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
                .delayBetweenPages(Duration.ofMillis(10))
                .flux()
                .take(10)
                .collectList()
                .block()

        then:
        noExceptionThrown()
        orgs != null
    }

    def "can fetch single page of ticket events"() {
        when:
        Instant sinceTime = Instant.now().minusSeconds(3600)
        def page = incrementalService.ticketEvents()
                .since(sinceTime)
                .fetchPage()
                .block()

        then:
        noExceptionThrown()
        page != null
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

    def "after cursor sets cursor on builder"() {
        when:
        def builder = incrementalService.tickets().after("sample_cursor")

        then:
        builder != null
    }

    def "extractStartTimeFromUrl correctly extracts start_time parameter"() {
        expect:
        IncrementalService.extractStartTimeFromUrl(null) == null
        IncrementalService.extractStartTimeFromUrl("") == null
        IncrementalService.extractStartTimeFromUrl("   ") == null
        IncrementalService.extractStartTimeFromUrl("https://example.com/api/v2/incremental/orgs.json") == null
        IncrementalService.extractStartTimeFromUrl("https://example.com/api/v2/incremental/orgs.json?start_time=123456") == 123456L
        IncrementalService.extractStartTimeFromUrl("https://example.com/api/v2/incremental/orgs.json?start_time=123456&per_page=10") == 123456L
        IncrementalService.extractStartTimeFromUrl("https://example.com/api/v2/incremental/orgs.json?other_start_time=999&start_time=123456") == 123456L
        IncrementalService.extractStartTimeFromUrl("https://example.com/api/v2/incremental/orgs.json?foo=bar") == null
        IncrementalService.extractStartTimeFromUrl("https://example.com/api/v2/incremental/orgs.json?start_time=notanumber") == null
    }

    def "getRecordId extracts ID correctly across supported types"() {
        expect:
        IncrementalService.getRecordId(new Organization(id: 10L)) == 10L
        IncrementalService.getRecordId(new TicketEvent(id: 20L)) == 20L
        IncrementalService.getRecordId(new Ticket().setId(30L)) == 30L
        IncrementalService.getRecordId(new User(id: 40L)) == 40L
        IncrementalService.getRecordId("unsupported string") == null
        IncrementalService.getRecordId(null) == null
    }

    def "unsupported resource type in CursorIncrementalBuilder throws error"() {
        given:
        def builder = new CursorIncrementalBuilder(ResourceType.ORGANIZATION, incrementalService.incrementalClient)

        when:
        builder.fetchPage().block()

        then:
        thrown(IllegalStateException)
    }

    def "unsupported resource type in TimeIncrementalBuilder throws error"() {
        given:
        def builder = new TimeIncrementalBuilder(ResourceType.TICKET, incrementalService.incrementalClient)
                .since(Instant.now().minusSeconds(120))

        when:
        builder.fetchPage().block()

        then:
        thrown(IllegalStateException)
    }
}
