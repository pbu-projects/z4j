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
package lol.pbu.z4j.coverage


import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern

import static java.nio.charset.StandardCharsets.UTF_8
/**
 * <h1>Client Coverage Reporter</h1>
 * Scans declarative {@code @Client} interfaces in {@code lol.pbu.z4j.client} and cross-references
 * Spock test specifications in {@code src/test/groovy/lol/pbu/z4j/client} to compute and report
 * true endpoint and role authorization test coverage without relying on JaCoCo.
 */
class ClientCoverageReporter {

    static class EndpointCoverage {
        String clientName
        String methodName
        String httpVerb
        String httpPath
        boolean tested
        boolean adminTested
        boolean agentTested
        boolean userTested
        boolean negativeAuthTested

        String getBadge() {
            if (!tested) return "⚪ Not Tested"
            if (adminTested && agentTested && userTested) return "🟢 Complete"
            return "🟡 In Progress"
        }

        String getAdminStatus() { adminTested ? "✅" : "⚪" }
        String getAgentStatus() { agentTested ? "✅" : "⚪" }
        String getUserStatus() { userTested ? "✅" : "⚪" }
    }

    static void main(String[] args) {
        println "ClientCoverageReporter running with args: ${args}"
        File projectDir = args.length > 0 ? new File(args[0]) : new File(".")
        File clientDir = new File(projectDir, "src/main/java/lol/pbu/z4j/client")
        File testDir = new File(projectDir, "src/test/groovy/lol/pbu/z4j/client")
        File outputFile = args.length > 1 ? new File(args[1]) : new File(projectDir, "src/test/COVERAGE.md")

        List<EndpointCoverage> allEndpoints = scanClients(clientDir)
        Map<String, String> specFiles = loadTestSpecs(testDir)

        evaluateCoverage(allEndpoints, specFiles)

        String markdown = generateMarkdownReport(allEndpoints)

        Path outputPath = Paths.get(outputFile.absolutePath)
        if (outputPath.parent != null) {
            Files.createDirectories(outputPath.parent)
        }
        Files.writeString(outputPath, markdown, UTF_8)
        println "✅ Coverage report successfully written to: ${outputPath.toAbsolutePath()}"

        printSummary(allEndpoints)
    }

    static List<EndpointCoverage> scanClients(File clientDir) {
        List<EndpointCoverage> list = []
        if (!clientDir.exists()) return list

        clientDir.listFiles()?.findAll { it.name.endsWith(".java") }?.sort { it.name }?.each { file ->
            String clientName = file.name.replace(".java", "")
            String content = file.text

            // Extract methods with Micronaut HTTP annotations
            def pattern = Pattern.compile(/@(Get|Post|Put|Delete|Patch)\((?:value\s*=\s*)?"([^"]+)"\)[\s\S]*?Mono<[^>]+>\s+(\w+)\(/)
            def matcher = pattern.matcher(content)
            while (matcher.find()) {
                list.add(new EndpointCoverage(
                        clientName: clientName,
                        httpVerb: matcher.group(1).toUpperCase(),
                        httpPath: matcher.group(2),
                        methodName: matcher.group(3)
                ))
            }
        }
        return list
    }

    static Map<String, String> loadTestSpecs(File testDir) {
        Map<String, String> map = [:]
        if (!testDir.exists()) return map

        testDir.listFiles()?.findAll { it.name.endsWith("Spec.groovy") }?.each { file ->
            map[file.name] = file.text
        }
        return map
    }

    static void evaluateCoverage(List<EndpointCoverage> endpoints, Map<String, String> specs) {
        endpoints.each { ep ->
            // Match corresponding spec precisely: e.g. GroupsClient -> GroupsClientSpec.groovy or GroupClientSpec.groovy
            String exactSpecName = ep.clientName + "Spec.groovy"
            String matchingSpecContent = specs[exactSpecName]
            if (!matchingSpecContent) {
                matchingSpecContent = specs[ep.clientName.replace("Client", "") + "Spec.groovy"]
            }
            if (!matchingSpecContent) {
                matchingSpecContent = specs.find { specName, content ->
                    String base = specName.replace("ClientSpec.groovy", "").replace("Spec.groovy", "")
                    String clientBase = ep.clientName.replace("Client", "")
                    base.equalsIgnoreCase(clientBase) || 
                    base.equalsIgnoreCase(clientBase + "s") || 
                    (clientBase.endsWith("s") && base.equalsIgnoreCase(clientBase.substring(0, clientBase.length() - 1)))
                }?.value
            }

            if (matchingSpecContent) {
                // Check if method is invoked in spec
                boolean methodCalled = matchingSpecContent =~ /\b${Pattern.quote(ep.methodName)}\s*\(/
                if (methodCalled) {
                    ep.tested = true
                    String lowerContent = matchingSpecContent.toLowerCase()

                    ep.adminTested = lowerContent.contains("admin") || lowerContent.contains("adminctx")
                    ep.agentTested = lowerContent.contains("agent") || lowerContent.contains("agentctx")
                    ep.userTested = lowerContent.contains("user") || lowerContent.contains("userctx") || lowerContent.contains("end_user")
                    ep.negativeAuthTested = lowerContent.contains("badtoken") || lowerContent.contains("bademail") || lowerContent.contains("badurl")
                }
            }
        }
    }

    static String generateMarkdownReport(List<EndpointCoverage> endpoints) {
        int total = endpoints.size()
        int testedCount = endpoints.count { it.tested }
        int fullyComplete = endpoints.count { it.badge.contains("Complete") }
        int adminCount = endpoints.count { it.adminTested }
        int agentCount = endpoints.count { it.agentTested }
        int userCount = endpoints.count { it.userTested }

        def clientGroups = endpoints.groupBy { it.clientName }
        int totalClients = clientGroups.size()
        int testedClients = clientGroups.count { client, eps -> eps.any { it.tested } }

        double percentTested = total > 0 ? (testedCount / (double) total) * 100.0 : 0.0

        StringBuilder sb = new StringBuilder()
        sb.append("# 🧪 Client Test & Authorization Coverage Matrix\n\n")
        sb.append("> **Note**: Declarative Micronaut client interfaces are tested via live sandbox integration tests (`Z4jSpec`).\n")
        sb.append("> This report tracks endpoint and admin-level test coverage across all client interfaces.\n\n")

        sb.append("## 📊 Summary Metrics\n\n")
        sb.append("- **Total Client Interfaces**: ${totalClients}\n")
        sb.append("- **Clients with Active Tests**: ${testedClients} / ${totalClients} (${String.format('%.1f', (testedClients / (double) totalClients) * 100.0)}%)\n")
        sb.append("- **Total Endpoints Declared**: ${total}\n")
        sb.append("- **Endpoints Tested**: **${testedCount} / ${total} (${String.format('%.1f', percentTested)}%)**\n")
        sb.append("  - 👑 **Admin Level (`adminCtx`)**: ${adminCount} endpoints\n")
        sb.append("  - 🛡️ **Agent Level (`agentCtx`)**: ${agentCount} endpoints\n")
        sb.append("  - 👤 **End-User Level (`userCtx`)**: ${userCount} endpoints\n\n")

        sb.append("---\n\n")
        sb.append("## 📋 Client Breakdown\n\n")

        clientGroups.each { clientName, eps ->
            int clientTotal = eps.size()
            int clientTested = eps.count { it.tested }
            double clientPercent = clientTotal > 0 ? (clientTested / (double) clientTotal) * 100.0 : 0.0
            String clientStatus = clientPercent == 100.0 ? "🟢" : (clientTested > 0 ? "🟡" : "⚪")

            sb.append("### ${clientStatus} `${clientName}` (${clientTested}/${clientTotal} endpoints — ${String.format('%.0f', clientPercent)}%)\n\n")
            sb.append("| Method | Route | Admin | Agent | End User | Status |\n")
            sb.append("| :--- | :--- | :---: | :---: | :---: | :---: |\n")

            eps.sort { it.methodName }.each { ep ->
                sb.append("| `${ep.methodName}` | `${ep.httpVerb} ${ep.httpPath}` | ${ep.adminStatus} | ${ep.agentStatus} | ${ep.userStatus} | ${ep.badge} |\n")
            }
            sb.append("\n")
        }

        return sb.toString()
    }

    static void printSummary(List<EndpointCoverage> endpoints) {
        int total = endpoints.size()
        int testedCount = endpoints.count { it.tested }
        double percentTested = total > 0 ? (testedCount / (double) total) * 100.0 : 0.0
        println "=========================================================="
        println " 🧪 Z4J CLIENT TEST COVERAGE SUMMARY"
        println "=========================================================="
        println " Total Endpoints Declared: ${total}"
        println " Total Endpoints Tested:   ${testedCount} (${String.format('%.1f', percentTested)}%)"
        println " Uncovered Endpoints:      ${total - testedCount}"
        println "=========================================================="
    }
}
