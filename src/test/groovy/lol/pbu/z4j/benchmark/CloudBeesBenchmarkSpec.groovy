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
package lol.pbu.z4j.benchmark

import io.micronaut.context.ApplicationContext
import java.time.Duration
import lol.pbu.z4j.client.TicketClient
import lol.pbu.z4j.model.Ticket as Z4jTicket
import org.spockframework.runtime.model.parallel.ExecutionMode
import org.zendesk.client.v2.Zendesk
import org.zendesk.client.v2.model.Ticket as CbTicket
import spock.lang.Execution
import spock.lang.IgnoreIf
import spock.lang.Isolated
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Tag

@Tag("benchmark")
@Isolated
@Execution(ExecutionMode.SAME_THREAD)
@IgnoreIf({ !Boolean.getBoolean("benchmark") && !Boolean.parseBoolean(System.getenv("RUN_BENCHMARK")) })
class CloudBeesBenchmarkSpec extends Specification {

    @Shared
    MockZendeskHttpServer mockServer

    @Shared
    ApplicationContext clientCtx

    @Shared
    TicketClient ticketsClient

    @Shared
    Zendesk cloudBeesClient

    def setupSpec() {
        mockServer = new MockZendeskHttpServer()
        mockServer.start()

        clientCtx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"  : mockServer.url,
                "micronaut.http.services.zendesk.email": "benchmark@example.com",
                "micronaut.http.services.zendesk.token": "mock-token"
        ])
        ticketsClient = clientCtx.getBean(TicketClient)

        cloudBeesClient = new Zendesk.Builder(mockServer.url)
                .setUsername("benchmark@example.com")
                .setToken("mock-token")
                .build()

        // Thorough warmup: 20 rounds of 25 tickets to warm up JIT, caches, and connection pools
        List<Long> warmupIds = (1..25).collect { (long) (1000 + it) }
        long firstWarmupId = warmupIds[0]
        long[] restWarmupIds = warmupIds[1..-1] as long[]

        20.times {
            cloudBeesClient.getTickets(firstWarmupId, restWarmupIds)
            ticketsClient.showMultipleTickets(warmupIds).block(Duration.ofSeconds(5))
        }

        System.gc()
        Thread.sleep(300)

        File reportFile = new File("build/reports/benchmark-results.txt")
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    def cleanupSpec() {
        if (cloudBeesClient != null) {
            cloudBeesClient.close()
        }
        if (clientCtx != null) {
            clientCtx.stop()
        }
        if (mockServer != null) {
            mockServer.stop()
        }
    }

    @Execution(ExecutionMode.SAME_THREAD)
    def "Live Benchmark: Fetch & parse #count tickets (CloudBees vs z4j)"(int count) {
        given: "A list of #count ticket IDs to request over HTTP"
        List<Long> ticketIds = (1..count).collect { (long) (1000 + it) }
        long firstId = ticketIds[0]
        long[] restIds = (ticketIds.size() > 1 ? ticketIds[1..-1] : []) as long[]
        int iterations = 10

        when: "Fetching via CloudBees (blocking show_many with Jackson Databind) across multiple iterations"
        System.gc()
        Thread.sleep(100)

        long memBeforeCb = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()
        long startCb = System.nanoTime()

        List<CbTicket> cbTickets = []
        iterations.times {
            cbTickets = cloudBeesClient.getTickets(firstId, restIds)
        }
        long durationCbMs = (System.nanoTime() - startCb) / (1_000_000 * iterations)
        long memAfterCb = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()

        and: "Fetching via z4j (reactive Netty show_many and Micronaut Serde records) across multiple iterations"
        System.gc()
        Thread.sleep(100)

        long memBeforeZ4j = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()
        long startZ4j = System.nanoTime()

        List<Z4jTicket> z4jTickets = []
        iterations.times {
            z4jTickets = ticketsClient.showMultipleTickets(ticketIds)
                    .block(Duration.ofSeconds(5))
                    .tickets()
        }
        long durationZ4jMs = (System.nanoTime() - startZ4j) / (1_000_000 * iterations)
        long memAfterZ4j = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()

        then: "Both clients retrieved the full cohort matching all requested IDs"
        verifyAll {
            cbTickets.size() == count
            z4jTickets.size() == count
            cbTickets*.id.sort() == ticketIds.sort()
            z4jTickets*.id.sort() == ticketIds.sort()
        }

        String results = """
======================================================
            BENCHMARK: ${count} TICKETS
======================================================
CloudBees Duration : ${durationCbMs} ms/call | Delta Heap: ${String.format('%.2f', Math.max(0.0, (memAfterCb - memBeforeCb) / 1024.0))} KB
z4j Duration       : ${durationZ4jMs} ms/call | Delta Heap: ${String.format('%.2f', Math.max(0.0, (memAfterZ4j - memBeforeZ4j) / 1024.0))} KB
======================================================
"""
        println results
        saveReport(results)

        where:
        count << [25, 50, 100]
    }

    void saveReport(String results) {
        File reportDir = new File("build/reports")
        reportDir.mkdirs()
        new File(reportDir, "benchmark-results.txt") << results
    }
}
