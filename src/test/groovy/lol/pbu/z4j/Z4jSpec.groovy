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

import groovy.util.logging.Slf4j
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import lol.pbu.z4j.client.JobStatusClient
import lol.pbu.z4j.client.LocaleClient
import lol.pbu.z4j.client.SearchClient
import lol.pbu.z4j.client.TicketClient
import lol.pbu.z4j.model.*
import net.datafaker.Faker
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

@Slf4j
@MicronautTest
@SuppressWarnings("GroovyAssignabilityCheck")
class Z4jSpec extends Specification {

    @Shared
    ApplicationContext adminCtx, agentCtx, userCtx, badTokenCtx, badEmailCtx, badUrlCtx

    @Shared
    SearchClient adminSearchClient

    @Shared
    JobStatusClient jobStatusClient

    @Shared
    TicketClient ticketsAdminClient

    @Shared
    List<Locale> accountLocales

    @Shared
    List<TicketField> ticketFields

    @Shared
    Faker faker

    void setupSpec() {
        adminCtx = getCtx("Z4J_ADMIN_EMAIL")
        agentCtx = getCtx("Z4J_AGENT_EMAIL")
        userCtx = getCtx("Z4J_END_USER_EMAIL")
        badTokenCtx = getCtx("Z4J_ADMIN_EMAIL", ["micronaut.http.services.zendesk.token": "this-is-an-invalid-token"])
        badEmailCtx = getCtx("Z4J_ADMIN_EMAIL", ["micronaut.http.services.zendesk.email": "this-is-an-invalid-email"])
        badUrlCtx = getCtx("Z4J_ADMIN_EMAIL", ["micronaut.http.services.zendesk.url": "https://fake-url.lol"])
        faker = new Faker()
        accountLocales = adminCtx.getBean(LocaleClient.class).listLocales().block().locales
    }

    void cleanupSpec() {
        adminCtx?.stop()
        agentCtx?.stop()
        userCtx?.stop()
        badTokenCtx?.stop()
        badEmailCtx?.stop()
        badUrlCtx?.stop()
    }

    TicketResponse createTicketForTest() {
        ticketsAdminClient = ticketsAdminClient ?: adminCtx.getBean(TicketClient.class)
        TicketComment ticketComment = new TicketComment().setBody(faker.chuckNorris().fact())
        TicketCreateInput createTicketInput = new TicketCreateInput(ticketComment)
        createTicketInput.setRawSubject(faker.chuckNorris().fact())
        TicketCreateRequest createTicketRequest = new TicketCreateRequest(createTicketInput)
        return ticketsAdminClient.createTicket(createTicketRequest).block()
    }

    static ApplicationContext getCtx(String authUser) {
        return getCtx(authUser, [:])
    }

    /**
     * create an ApplicationContext for z4j testing with the given env vars.
     *
     * @param authUser environment variable whose value holds the user's email address
     * @return ApplicationContext with the specified environment variables
     */
    static ApplicationContext getCtx(String authUser, Map<String, String> properties) {
        ["MICRONAUT_HTTP_SERVICES_ZENDESK_EMAIL",
         "MICRONAUT_HTTP_SERVICES_ZENDESK_TOKEN",
         "MICRONAUT_HTTP_SERVICES_ZENDESK_URL"].each { envVar ->
            if (null != System.getenv(envVar)) {
                throw new IllegalStateException("$envVar is set. Do not define this variable in testing.")
            }
        }
        ["Z4J_URL", authUser].each { envVar ->
            if (null == System.getenv(envVar)) {
                throw new IllegalStateException("$envVar environment variable is not set.")
            }
        }
        properties = ["micronaut.http.services.zendesk.email": System.getenv(authUser),
                      "micronaut.http.services.zendesk.url"  : System.getenv("Z4J_URL"),
                      "micronaut.http.services.zendesk.token": System.getenv("Z4J_TOKEN")
        ] + properties
        ApplicationContext.builder(EmbeddedServer).properties(properties).build().start()
    }

    /**
     * Recursively creates batches of tickets to populate the Zendesk instance with data.
     * This is useful for testing Job Statuses or Search functionality where multiple
     * records are required. It polls the Job Status API until the batch is completed.
     *
     * @param jobs The number of batches (3 tickets per batch unless otherwise specified) to create.
     */
    void createTicketsForTests(int jobs, @Min(2) @Max(100) int batchSize = 3) {
        if (jobs <= 0) {
            return
        }
        log.debug("creating tickets in {}", getSpecificationContext().getCurrentIteration().getDisplayName())
        ticketsAdminClient = ticketsAdminClient ?: adminCtx.getBean(TicketClient.class)
        JobStatusResponse jobResponse = ticketsAdminClient.createManyTickets(getTicketInputs(batchSize)).block()
        jobStatusClient = jobStatusClient ?: adminCtx.getBean(JobStatusClient.class)
        jobStatusClient.getJobStatus(jobResponse.getJobStatus().getId())
                .map(JobStatusResponse::getJobStatus)
                .filter { it.getStatus() == "completed" }
                .repeatWhenEmpty { it.delayElements(Duration.ofMillis(500)).take(10) }
                .block()
        createTicketsForTests(jobs - 1)
    }

    TicketsCreateRequest getTicketInputs(@Min(2) @Max(100) int batchSize) {
        List<TicketCreateInput> inputs = (1..batchSize).collect {
            new TicketCreateInput(new TicketComment().setBody(faker.chuckNorris().fact()))
                    .setRawSubject(faker.book().title())
        }
        return new TicketsCreateRequest().setTickets(inputs)
    }
}
