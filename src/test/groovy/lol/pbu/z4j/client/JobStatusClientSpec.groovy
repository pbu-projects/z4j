package lol.pbu.z4j.client

import groovy.util.logging.Slf4j
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.*

@Slf4j
class JobStatusClientSpec extends Z4jSpec {

    void setupSpec() {
        jobStatusClient = jobStatusClient ?: adminCtx.getBean(JobStatusClient.class)
    }

    void "can query and paginate job statuses"(JobStatusClient client) {
        given:
        JobStatusesResponse statusesResponse = client.getJobStatuses(null, null).block()
        if (null == statusesResponse.getJobStatuses() || statusesResponse.getJobStatuses().isEmpty()) {
            createTicketsForTests(5)
        }

        when:
        statusesResponse = client.getJobStatuses(2, null).block()
        int rounds = 1
        while (statusesResponse.getMeta().hasMore) {
            statusesResponse = client.getJobStatuses(2, statusesResponse.getMeta().getAfterCursor()).block()
            rounds++
        }

        then:
        noExceptionThrown()

        and:
        rounds > 1

        where:
        client          | _
        jobStatusClient | _
    }

    void "can query a completed status"() {
        given:
        JobStatusesResponse statusesResponse = jobStatusClient.getJobStatuses(null, null).block()
        if (null == statusesResponse.getJobStatuses() || statusesResponse.getJobStatuses().isEmpty()) {
            createTicketsForTests(1)
        }
        String jobId = statusesResponse.getJobStatuses().get(0).getId()

        when:
        jobStatusClient.getJobStatus(jobId).block()

        then:
        noExceptionThrown()
    }

    void "can query a long running status"() {
        given:
        ticketsAdminClient = ticketsAdminClient ?: adminCtx.getBean(TicketClient.class)

        when:
        JobStatusResponse manyTicketsRequest = ticketsAdminClient.createManyTickets(getTicketInputs(100)).block()
        while (manyTicketsRequest.getJobStatus().getStatus() != "completed") {
            sleep(50)
            manyTicketsRequest = jobStatusClient.getJobStatus(manyTicketsRequest.getJobStatus().getId()).block()
            log.debug("job status is {}", manyTicketsRequest.getJobStatus().getStatus())
        }
        then:
        noExceptionThrown()
    }

    void "can query a failed status"() {
        given:
        ticketsAdminClient = ticketsAdminClient ?: adminCtx.getBean(TicketClient.class)
        List<TicketCreateInput> tickets = getTicketInputs(3).getTickets()
        tickets.forEach { ticket ->
            ticket.setCustomFields(List.of(new TicketCustomFieldString(id: 0L, value: faker.chuckNorris().fact())))
        }

        when:
        JobStatusResponse manyTicketsRequest = ticketsAdminClient.createManyTickets(new TicketsCreateRequest(tickets)).block()

        then:
        noExceptionThrown()

        when:
        while (manyTicketsRequest.getJobStatus().getStatus() != "failed") {
            sleep(1000)
            manyTicketsRequest = jobStatusClient.getJobStatus(manyTicketsRequest.getJobStatus().getId()).block()
        }

        then:
        noExceptionThrown()

    }
}
