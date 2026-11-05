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
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.client.JobStatusClient
import lol.pbu.z4j.client.TicketClient
import lol.pbu.z4j.model.JobStatusResponse
import lol.pbu.z4j.model.Ticket as Z4jTicket
import lol.pbu.z4j.model.TicketComment
import lol.pbu.z4j.model.TicketCreateInput
import org.spockframework.runtime.model.parallel.ExecutionMode
import org.zendesk.client.v2.Zendesk
import org.zendesk.client.v2.model.Ticket as CbTicket
import spock.lang.Execution
import spock.lang.IgnoreIf
import spock.lang.Isolated
import spock.lang.Shared
import spock.lang.Tag

@Tag("benchmark")
@Isolated
@Execution(ExecutionMode.SAME_THREAD)
@IgnoreIf({ !Boolean.getBoolean("benchmark") && !Boolean.parseBoolean(System.getenv("RUN_BENCHMARK")) })
@MicronautTest
class CloudBeesBenchmarkSpec extends Z4jSpec {

    @Shared
    TicketClient ticketsClient

    @Shared
    JobStatusClient jobStatusClient

    @Shared
    Zendesk cloudBeesClient

    List<Long> seededTicketIds = Collections.synchronizedList(new ArrayList<Long>())

    def setupSpec() {
        ticketsClient = adminCtx.getBean(TicketClient)
        jobStatusClient = adminCtx.getBean(JobStatusClient)

        String url = System.getenv("Z4J_URL")
        String email = System.getenv("Z4J_ADMIN_EMAIL")
        String token = System.getenv("Z4J_TOKEN")

        cloudBeesClient = new Zendesk.Builder(url)
                .setUsername(email)
                .setToken(token)
                .build()

        // Warm up connection pools, thread groups, and deserializers for both clients
        try {
            cloudBeesClient.getTickets(1L)
        } catch (Exception ignored) {}

        try {
            ticketsClient.showMultipleTickets(List.of(1L)).block(Duration.ofSeconds(15))
        } catch (Exception ignored) {}

        System.gc()
        Thread.sleep(500)

        File reportFile = new File("build/reports/benchmark-results.txt")
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    def cleanupSpec() {
        if (cloudBeesClient != null) {
            cloudBeesClient.close()
        }
    }

    @Execution(ExecutionMode.SAME_THREAD)
    def "Live Benchmark: Fetch & parse #count tickets (CloudBees vs z4j)"(int count) {
        given: "A tagged cohort of #count tickets in the sandbox"
        String tag = "z4j_bench_${count}_${System.currentTimeMillis()}"
        seededTicketIds = seedBenchmarkTickets(count, tag)

        when: "Fetching via CloudBees (blocking show_many with Jackson Databind)"
        System.gc()
        Thread.sleep(200)

        long memBeforeCb = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()
        long startCb = System.nanoTime()

        long firstCbId = seededTicketIds[0]
        long[] restCbIds = (seededTicketIds.size() > 1 ? seededTicketIds[1..-1] : []) as long[]
        List<CbTicket> cbTickets = cloudBeesClient.getTickets(firstCbId, restCbIds)
        long durationCbMs = (System.nanoTime() - startCb) / 1_000_000
        long memAfterCb = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()

        and: "Fetching via z4j (reactive Netty show_many and Micronaut Serde records)"
        System.gc()
        Thread.sleep(200)

        long memBeforeZ4j = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()
        long startZ4j = System.nanoTime()

        List<Z4jTicket> z4jTickets = ticketsClient.showMultipleTickets(seededTicketIds)
                .block(Duration.ofMinutes(1))
                .tickets()
        long durationZ4jMs = (System.nanoTime() - startZ4j) / 1_000_000
        long memAfterZ4j = Runtime.runtime.totalMemory() - Runtime.runtime.freeMemory()

        then: "Both clients retrieved the full cohort"
        verifyAll {
            cbTickets.size() == count
            z4jTickets.size() == count
            cbTickets*.id.sort() == seededTicketIds.sort()
            z4jTickets*.id.sort() == seededTicketIds.sort()
        }

        String results = """
======================================================
         LIVE SANDBOX BENCHMARK: ${count} TICKETS
======================================================
CloudBees Duration : ${durationCbMs} ms | Delta Heap: ${String.format('%.2f', (memAfterCb - memBeforeCb) / 1024.0)} KB
z4j Duration       : ${durationZ4jMs} ms | Delta Heap: ${String.format('%.2f', (memAfterZ4j - memBeforeZ4j) / 1024.0)} KB
======================================================
"""
        println results
        saveReport(results)

        cleanup: "Tear down seeded tickets in 1 call to keep sandbox clean"
        if (!seededTicketIds.isEmpty()) {
            ticketsClient.bulkDeleteTickets(new ArrayList<>(seededTicketIds)).block(Duration.ofMinutes(1))
            seededTicketIds.clear()
        }

        where:
        count << [25, 50, 100]
    }

    List<Long> seedBenchmarkTickets(int count, String tag) {
        List<TicketCreateInput> inputs = (1..count).collect { i ->
            new TicketCreateInput(new TicketComment("Benchmark comment ${i}"))
                    .withRawSubject("Benchmark Ticket ${i} - ${tag}")
                    .withTags([tag])
        }
        JobStatusResponse job = ticketsClient.createManyTickets(inputs).block(Duration.ofMinutes(1))
        String jobId = job.jobStatus().id()

        List<Long> ids = (1..60).findResult {
            Thread.sleep(1000)
            JobStatusResponse status = jobStatusClient.showJobStatus(jobId).block(Duration.ofSeconds(30))
            if ("completed".equalsIgnoreCase(status.jobStatus().status())) {
                List<Long> completedIds = status.jobStatus().getJobStatusResults().collect { it.id() }
                // Immediately register seeded IDs so cleanup deletes them if needed
                seededTicketIds.addAll(completedIds)
                return completedIds
            }
            if ("failed".equalsIgnoreCase(status.jobStatus().status())) {
                throw new IllegalStateException("Ticket batch creation failed: " + status.jobStatus().message())
            }
            return null
        }

        if (!ids) {
            throw new IllegalStateException("Timed out waiting for ticket batch job to complete: " + jobId)
        }

        return ids
    }

    void saveReport(String results) {
        File reportDir = new File("build/reports")
        reportDir.mkdirs()
        new File(reportDir, "benchmark-results.txt") << results
    }
}
