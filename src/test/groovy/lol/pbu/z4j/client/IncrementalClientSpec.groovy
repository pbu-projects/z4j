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

import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

import java.time.Instant

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class IncrementalClientSpec extends Z4jSpec {

    @Shared
    IncrementalClient adminClient

    @Shared
    IncrementalClient agentClient

    @Shared
    IncrementalClient userClient

    @Shared
    IncrementalClient badTokenClient

    def setupSpec() {
        adminClient = adminCtx.getBean(IncrementalClient.class)
        agentClient = agentCtx.getBean(IncrementalClient.class)
        userClient = userCtx.getBean(IncrementalClient.class)
        badTokenClient = badTokenCtx.getBean(IncrementalClient.class)
    }

    def "admin can export tickets cursor"() {
        when:
        long startTime = Instant.now().minusSeconds(120).epochSecond
        def response = adminClient.exportTickets(startTime, null, 10).block()

        then:
        noExceptionThrown()
        response != null
    }

    def "admin can export users cursor"() {
        when:
        long startTime = Instant.now().minusSeconds(120).epochSecond
        def response = adminClient.exportUsers(startTime, null, 10).block()

        then:
        noExceptionThrown()
        response != null
    }

    def "admin can export organizations time"() {
        when:
        long startTime = Instant.now().minusSeconds(120).epochSecond
        def response = adminClient.exportOrganizations(startTime, 10).block()

        then:
        noExceptionThrown()
        response != null
    }

    def "admin can export ticket events time"() {
        when:
        long startTime = Instant.now().minusSeconds(120).epochSecond
        def response = adminClient.exportTicketEvents(startTime).block()

        then:
        noExceptionThrown()
        response != null
    }

    def "agent or user cannot export incremental tickets"() {
        when:
        long startTime = Instant.now().minusSeconds(120).epochSecond
        client.exportTickets(startTime, null, 10).block()

        then:
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status == FORBIDDEN

        where:
        client << [agentClient, userClient]
    }

    def "bad token fails with client exception"() {
        when:
        long startTime = Instant.now().minusSeconds(120).epochSecond
        badTokenClient.exportTickets(startTime, null, 10).block()

        then:
        thrown(Exception)
    }
}
