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
    ApplicationContext clientCtx

    def setupSpec() {
        clientCtx = ApplicationContext.run()

        File reportFile = new File("build/reports/benchmark-results.txt")
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    def cleanupSpec() {
        if (clientCtx != null) {
            clientCtx.stop()
        }
    }

    @Execution(ExecutionMode.SAME_THREAD)
    def "Memory Benchmark: Parse #count tickets (CloudBees vs z4j)"(int count) {
        given: "A pre-rendered Zendesk tickets JSON payload for #count tickets"
        byte[] payloadBytes = BenchmarkPayloads.getPayload(count)
        ThreadMXBean mxBean = (ThreadMXBean) ManagementFactory.getThreadMXBean()
        JacksonObjectMapper jacksonMapper = Zendesk.createMapper()
        SerdeObjectMapper serdeMapper = clientCtx.getBean(SerdeObjectMapper)

        when: "Deserializing via CloudBees (Jackson reflection AST)"
        System.gc()
        Thread.sleep(50)

        long tAllocBeforeJackson = mxBean.getCurrentThreadAllocatedBytes()
        JsonNode tree = jacksonMapper.readTree(payloadBytes)
        List<CbTicket> jacksonTickets = tree.get("tickets").collect { node ->
            jacksonMapper.convertValue(node, CbTicket)
        }
        long jacksonAllocKb = (mxBean.getCurrentThreadAllocatedBytes() - tAllocBeforeJackson) / 1024

        and: "Deserializing via z4j (Micronaut compile-time Serde records)"
        System.gc()
        Thread.sleep(50)

        long tAllocBeforeSerde = mxBean.getCurrentThreadAllocatedBytes()
        TicketsResponse serdeResponse = serdeMapper.readValue(payloadBytes, TicketsResponse)
        long serdeAllocKb = (mxBean.getCurrentThreadAllocatedBytes() - tAllocBeforeSerde) / 1024
        long serdeSavingsPercent = Math.round((1.0 - (serdeAllocKb / (double) jacksonAllocKb)) * 100)

        then: "Both deserializers parsed all tickets correctly"
        verifyAll {
            jacksonTickets.size() == count
            serdeResponse.tickets().size() == count
            jacksonTickets*.id.sort() == serdeResponse.tickets()*.id().sort()
        }

        String results = """
======================================================
         MEMORY BENCHMARK: ${count} TICKETS
======================================================
Payload Size: ${Math.round(payloadBytes.length / 1024.0)} KB JSON
CloudBees (Jackson reflection AST) : ${jacksonAllocKb} KB
z4j (Compile-time Serde records)   : ${serdeAllocKb} KB  (${serdeSavingsPercent}% less memory)
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
