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

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import java.time.Duration
import lol.pbu.z4j.SearchService
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.client.JobStatusClient
import lol.pbu.z4j.client.SearchClient
import lol.pbu.z4j.client.TicketClient
import lol.pbu.z4j.model.JobStatusResponse
import lol.pbu.z4j.model.Ticket as Z4jTicket
import lol.pbu.z4j.model.TicketComment
import lol.pbu.z4j.model.TicketCreateInput
import spock.lang.Tag
import org.zendesk.client.v2.Zendesk
import org.zendesk.client.v2.model.Ticket as CbTicket
import spock.lang.IgnoreIf
import spock.lang.Shared

@Tag("benchmark")
@IgnoreIf({ !Boolean.getBoolean("benchmark") && !Boolean.parseBoolean(System.getenv("RUN_BENCHMARK")) })
@MicronautTest
class CloudBeesBenchmarkSpec extends Z4jSpec {

    @Shared
    TicketClient ticketsClient

    @Shared
    SearchClient searchClient

    @Shared
    SearchService searchService

    @Shared
    JobStatusClient jobStatusClient

    @Shared
    Zendesk cloudBeesClient

    @Shared
    List<Long> seededTicketIds = Collections.synchronizedList(new ArrayList<Long>())

    def setupSpec() {
        ticketsClient = adminCtx.getBean(TicketClient)
        searchClient = adminCtx.getBean(SearchClient)
        searchService = adminCtx.getBean(SearchService)
        jobStatusClient = adminCtx.getBean(JobStatusClient)

        String url = System.getenv("Z4J_URL")
        String email = System.getenv("Z4J_ADMIN_EMAIL")
        String token = System.getenv("Z4J_TOKEN")

        cloudBeesClient = new Zendesk.Builder(url)
                .setUsername(email)
                .setToken(token)
                .build()
    }

    def cleanupSpec() {
        if (cloudBeesClient != null) {
            cloudBeesClient.close()
        }
    }

    def "Live Benchmark: Stream & parse 50 tickets (CloudBees vs z4j)"() {
        given: "A tagged cohort of 50 tickets in the sandbox"
        String tag = "z4j_bench_${System.currentTimeMillis()}"
        seededTicketIds = seedBenchmarkTickets(50, tag)

        when: "Fetching via CloudBees (blocking iterator with Jackson Databind)"
        System.gc()
        Thread.sleep(200)

        long memBeforeCb = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()
        long startCb = System.nanoTime()

        List<CbTicket> cbTickets = []
        for (CbTicket t : cloudBeesClient.getTicketsFromSearch("type:ticket tags:" + tag)) {
            cbTickets << t
        }
        long durationCbMs = (System.nanoTime() - startCb) / 1_000_000
        long memAfterCb = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()

        and: "Fetching via z4j (reactive Netty and Micronaut Serde records)"
        System.gc()
        Thread.sleep(200)

        long memBeforeZ4j = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()
        long startZ4j = System.nanoTime()

        List<Z4jTicket> z4jTickets = searchService.getTickets("tags:" + tag)
                .collectList()
                .block(Duration.ofMinutes(2))
        long durationZ4jMs = (System.nanoTime() - startZ4j) / 1_000_000
        long memAfterZ4j = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()

        then: "Both clients retrieved the full cohort"
        verifyAll {
            cbTickets.size() == 50
            z4jTickets.size() == 50
            cbTickets*.id.sort() == seededTicketIds.sort()
            z4jTickets*.id.sort() == seededTicketIds.sort()
        }

        String results = """
======================================================
              LIVE SANDBOX BENCHMARK
======================================================
CloudBees Duration : ${durationCbMs} ms | Delta Heap: ${String.format('%.2f', (memAfterCb - memBeforeCb) / 1024.0)} KB
z4j Duration       : ${durationZ4jMs} ms | Delta Heap: ${String.format('%.2f', (memAfterZ4j - memBeforeZ4j) / 1024.0)} KB
======================================================
"""
        println results
        System.err.println results

        File reportDir = new File("build/reports")
        reportDir.mkdirs()
        new File(reportDir, "benchmark-results.txt").write(results)

        cleanup: "Tear down seeded tickets in 1 call to keep sandbox clean"
        if (!seededTicketIds.isEmpty()) {
            ticketsClient.bulkDeleteTickets(new ArrayList<>(seededTicketIds)).block(Duration.ofMinutes(1))
            seededTicketIds.clear()
        }
    }

    List<Long> seedBenchmarkTickets(int count, String tag) {
        List<TicketCreateInput> inputs = (1..count).collect { i ->
            new TicketCreateInput(new TicketComment("Benchmark comment ${i}"))
                    .withRawSubject("Benchmark Ticket ${i} - ${tag}")
                    .withTags([tag])
        }
        JobStatusResponse job = ticketsClient.createManyTickets(inputs).block(Duration.ofMinutes(1))
        String jobId = job.jobStatus().id()

        List<Long> ids = []
        int pollAttempts = 0
        while (pollAttempts < 60) {
            JobStatusResponse status = jobStatusClient.showJobStatus(jobId).block(Duration.ofSeconds(30))
            if ("completed".equalsIgnoreCase(status.jobStatus().status())) {
                ids = status.jobStatus().getJobStatusResults().collect { it.id() }
                // Immediately register seeded IDs so cleanup deletes them if indexing fails
                seededTicketIds.addAll(ids)
                break
            }
            if ("failed".equalsIgnoreCase(status.jobStatus().status())) {
                throw new IllegalStateException("Ticket batch creation failed: " + status.jobStatus().message())
            }
            Thread.sleep(1000)
            pollAttempts++
        }

        if (ids.isEmpty()) {
            throw new IllegalStateException("Timed out waiting for ticket batch job to complete: " + jobId)
        }

        // Wait for search indexer to index the new tickets
        int indexAttempts = 0
        while (indexAttempts < 60) {
            Integer indexedCount = searchClient.count("type:ticket tags:" + tag).block(Duration.ofSeconds(30))?.count() ?: 0
            if (indexedCount >= count) {
                break
            }
            Thread.sleep(1000)
            indexAttempts++
        }

        if (indexAttempts >= 60) {
            throw new IllegalStateException("Timed out waiting for search indexer to index tagged tickets: " + tag)
        }

        return ids
    }
}
