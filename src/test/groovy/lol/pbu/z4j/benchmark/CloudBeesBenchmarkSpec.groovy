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

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper as JacksonObjectMapper
import com.sun.management.ThreadMXBean
import io.micronaut.context.ApplicationContext
import io.micronaut.serde.ObjectMapper as SerdeObjectMapper
import java.lang.management.ManagementFactory
import java.time.Duration
import lol.pbu.z4j.client.TicketClient
import lol.pbu.z4j.model.Ticket as Z4jTicket
import lol.pbu.z4j.model.TicketsResponse
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

    def setupSpec() {
        mockServer = new MockZendeskHttpServer()
        mockServer.start()

        clientCtx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"  : mockServer.url,
                "micronaut.http.services.zendesk.email": "benchmark@example.com",
                "micronaut.http.services.zendesk.token": "mock-token"
        ])
        ticketsClient = clientCtx.getBean(TicketClient)

        File reportFile = new File("build/reports/benchmark-results.txt")
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    def cleanupSpec() {
        if (clientCtx != null) {
            clientCtx.stop()
        }
        if (mockServer != null) {
            mockServer.stop()
        }
    }

    @Execution(ExecutionMode.SAME_THREAD)
    def "Memory Benchmark: Fetch & parse #count tickets (CloudBees vs z4j)"(int count) {
        given: "A list of #count ticket IDs to request over HTTP"
        List<Long> ticketIds = (1..count).collect { (long) (1000 + it) }
        long firstId = ticketIds[0]
        long[] restIds = (ticketIds.size() > 1 ? ticketIds[1..-1] : []) as long[]
        ThreadMXBean mxBean = (ThreadMXBean) ManagementFactory.getThreadMXBean()

        when: "Fetching via CloudBees starting cold"
        System.gc()
        Thread.sleep(100)

        long allocBeforeCb = mxBean.getTotalThreadAllocatedBytes()
        Zendesk cbClient = new Zendesk.Builder(mockServer.url)
                .setUsername("benchmark@example.com")
                .setToken("mock-token")
                .build()
        List<CbTicket> cbTickets = cbClient.getTickets(firstId, restIds)
        long allocAfterCb = mxBean.getTotalThreadAllocatedBytes()
        long cbAllocKb = (allocAfterCb - allocBeforeCb) / 1024
        cbClient.close()

        and: "Fetching via z4j starting cold"
        System.gc()
        Thread.sleep(100)

        long allocBeforeZ4j = mxBean.getTotalThreadAllocatedBytes()
        List<Z4jTicket> z4jTickets = ticketsClient.showMultipleTickets(ticketIds)
                .block(Duration.ofSeconds(5))
                .tickets()
        long allocAfterZ4j = mxBean.getTotalThreadAllocatedBytes()
        long z4jAllocKb = (allocAfterZ4j - allocBeforeZ4j) / 1024

        and: "Benchmarking pure in-memory JSON deserialization without HTTP transport"
        byte[] payloadBytes = MockZendeskHttpServer.PREGENERATED_RESPONSES.get(count)
        JacksonObjectMapper jacksonMapper = Zendesk.createMapper()
        SerdeObjectMapper serdeMapper = clientCtx.getBean(SerdeObjectMapper)

        long tAllocBeforeJackson = mxBean.getCurrentThreadAllocatedBytes()
        JsonNode tree = jacksonMapper.readTree(payloadBytes)
        List<CbTicket> jacksonTickets = tree.get("tickets").collect { node ->
            jacksonMapper.convertValue(node, CbTicket)
        }
        long jacksonAllocKb = (mxBean.getCurrentThreadAllocatedBytes() - tAllocBeforeJackson) / 1024

        long tAllocBeforeSerde = mxBean.getCurrentThreadAllocatedBytes()
        TicketsResponse serdeResponse = serdeMapper.readValue(payloadBytes, TicketsResponse)
        long serdeAllocKb = (mxBean.getCurrentThreadAllocatedBytes() - tAllocBeforeSerde) / 1024
        long serdeSavingsPercent = Math.round((1.0 - (serdeAllocKb / (double) jacksonAllocKb)) * 100)

        then: "Both clients and deserializers retrieve the full cohort matching all IDs"
        verifyAll {
            cbTickets.size() == count
            z4jTickets.size() == count
            jacksonTickets.size() == count
            serdeResponse.tickets().size() == count
            cbTickets*.id.sort() == ticketIds.sort()
            z4jTickets*.id.sort() == ticketIds.sort()
        }

        String results = """
======================================================
         MEMORY BENCHMARK: ${count} TICKETS
======================================================
1. Pure Deserialization (Payload: ${Math.round(payloadBytes.length / 1024.0)} KB JSON):
   CloudBees (Jackson reflection AST) : ${jacksonAllocKb} KB
   z4j (Compile-time Serde records)   : ${serdeAllocKb} KB  (${serdeSavingsPercent}% less memory)

2. HTTP Transport Pipeline (Socket to Object):
   CloudBees (AsyncHttpClient + Jackson) : ${cbAllocKb} KB
   z4j (Netty Reactive + Serde records)  : ${z4jAllocKb} KB
   *Note: Netty pre-allocates pooled byte buffer arenas on I/O threads.
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
