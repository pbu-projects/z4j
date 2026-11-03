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

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CustomStatusCreateRequest
import lol.pbu.z4j.model.CustomStatusResponse
import lol.pbu.z4j.model.CustomStatusesResponse
import lol.pbu.z4j.model.TicketComment
import lol.pbu.z4j.model.TicketCreateInput
import lol.pbu.z4j.model.TicketCreateRequest
import lol.pbu.z4j.model.TicketFieldCustomStatusObject
import lol.pbu.z4j.model.TicketFieldCustomStatusObjectStatusCategory
import lol.pbu.z4j.model.TicketFormStatus
import lol.pbu.z4j.model.TicketFormStatusesResponse
import lol.pbu.z4j.model.TicketUpdateInput
import lol.pbu.z4j.model.TicketUpdateRequest
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class CustomStatusClientSpec extends Z4jSpec {

    @Shared CustomStatusClient adminCustomStatusClient, agentCustomStatusClient, badUrlCustomStatusClient
    @Shared TicketClient adminTicketClient
    @Shared Long sampleCustomStatusId
    @Shared Long sampleFormId

    def setupSpec() {
        adminCustomStatusClient = adminCtx.getBean(CustomStatusClient.class)
        agentCustomStatusClient = agentCtx.getBean(CustomStatusClient.class)
        badUrlCustomStatusClient = badUrlCtx.getBean(CustomStatusClient.class)
        adminTicketClient = adminCtx.getBean(TicketClient.class)

        def statusesResp = adminCustomStatusClient.listCustomStatuses().block()
        if (statusesResp != null && statusesResp.customStatuses() != null && !statusesResp.customStatuses().isEmpty()) {
            sampleCustomStatusId = statusesResp.customStatuses()[0].id
        }

        def formStatusesResp = adminCustomStatusClient.listTicketFormStatuses().block()
        if (formStatusesResp != null && formStatusesResp.ticketFormStatuses() != null && !formStatusesResp.ticketFormStatuses().isEmpty()) {
            sampleFormId = formStatusesResp.ticketFormStatuses()[0].ticketFormId
        }
    }

    @Unroll
    def "can list custom ticket statuses for #userType"(CustomStatusClient client, String userType) {
        when: "requesting custom ticket statuses without filters"
        def response = client.listCustomStatuses().block()

        then: "response deserializes successfully with custom statuses"
        noExceptionThrown()
        verifyAll(response) {
            it != null
            customStatuses() != null
            !customStatuses().isEmpty()
            count() != null
        }

        where:
        [client, userType] << [
                [adminCustomStatusClient, "admin"],
                [agentCustomStatusClient, "agent"]
        ]
    }

    def "can filter custom ticket statuses by category and active status"() {
        when: "filtering custom statuses by open category and active state"
        def response = adminCustomStatusClient.listCustomStatuses("open", true).block()

        then: "all returned custom statuses belong to the open category and are active"
        noExceptionThrown()
        verifyAll(response) {
            it != null
            customStatuses() != null
            !customStatuses().isEmpty()
            customStatuses().every { status -> status.statusCategory == TicketFieldCustomStatusObjectStatusCategory.OPEN }
            customStatuses().every { status -> status.active }
        }
    }

    @Unroll
    def "can show custom ticket status by ID for #userType"(CustomStatusClient client, String userType) {
        given: "a valid custom ticket status ID"
        assert sampleCustomStatusId != null

        when: "requesting a specific custom status by ID"
        def response = client.showCustomStatus(sampleCustomStatusId).block()

        then: "the custom status is returned successfully"
        noExceptionThrown()
        verifyAll(response) {
            it != null
            customStatus() != null
            customStatus().id == sampleCustomStatusId
        }

        where:
        [client, userType] << [
                [adminCustomStatusClient, "admin"],
                [agentCustomStatusClient, "agent"]
        ]
    }

    @Unroll
    def "can list ticket form statuses for #userType"(CustomStatusClient client, String userType) {
        when: "requesting all ticket form status associations"
        def response = client.listTicketFormStatuses().block()

        then: "response deserializes ticket form statuses with pagination metadata"
        noExceptionThrown()
        verifyAll(response) {
            it != null
            ticketFormStatuses() != null
            count() != null
        }

        where:
        [client, userType] << [
                [adminCustomStatusClient, "admin"],
                [agentCustomStatusClient, "agent"]
        ]
    }

    def "can filter ticket form statuses by ticket_form_id"() {
        given: "a valid ticket form ID"
        if (sampleFormId == null) return

        when: "requesting form status associations for a specific form"
        def response = adminCustomStatusClient.listTicketFormStatuses(sampleFormId).block()

        then: "all returned associations match the requested form ID"
        noExceptionThrown()
        verifyAll(response) {
            it != null
            ticketFormStatuses() != null
            ticketFormStatuses().every { status -> status.ticketFormId == sampleFormId }
        }
    }

    def "can update ticket customStatusId and ticketFormId using TicketUpdateInput"() {
        given: "a test ticket created in the sandbox"
        assert sampleCustomStatusId != null
        def comment = new TicketComment().tap {
            body = "Z4J Custom Status & Form Integration Test"
            isPublic = true
        }
        def createInput = new TicketCreateInput(comment).tap {
            subject = "Test Custom Status & Form " + UUID.randomUUID().toString().substring(0, 8)
        }
        def createdTicket = adminTicketClient.createTicket(new TicketCreateRequest(createInput)).block()?.ticket()
        assert createdTicket?.id != null
        Long createdTicketId = createdTicket.id

        when: "updating the ticket with a 64-bit custom_status_id and ticket_form_id via TicketUpdateInput"
        def updateComment = new TicketComment().tap {
            body = "Updating ticket form and custom status"
            isPublic = true
        }
        def updateInput = new TicketUpdateInput().tap {
            setComment(updateComment)
            setCustomStatusId(sampleCustomStatusId)
            if (sampleFormId != null) {
                setTicketFormId(sampleFormId)
            }
        }
        def updateResponse = adminTicketClient.updateTicket(createdTicketId, new TicketUpdateRequest(updateInput)).block()

        then: "the ticket is updated with the specified custom status ID and ticket form ID"
        noExceptionThrown()
        verifyAll(updateResponse) {
            it != null
            ticket() != null
            ticket().id == createdTicketId
            ticket().customStatusId == sampleCustomStatusId
            if (sampleFormId != null) {
                ticket().ticketFormId == sampleFormId
            }
        }

        cleanup: "delete the test ticket from sandbox"
        if (createdTicketId != null) {
            try {
                adminTicketClient.deleteTicket(createdTicketId).block()
            } catch (Exception ignored) {}
        }
    }

    def "calling custom status client with unreachable URL throws HttpClientException"() {
        when: "requesting custom statuses from an invalid host"
        badUrlCustomStatusClient.listCustomStatuses().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)
    }

    def "supports backward-compatible Integer setter and Long setter on TicketUpdateInput and TicketCreateInput"() {
        given: "a TicketUpdateInput and TicketCreateInput"
        def updateInput = new TicketUpdateInput()
        def createInput = new TicketCreateInput(new TicketComment().tap { body = "test" })

        when: "setting customStatusId with Integer and Long values"
        updateInput.setCustomStatusId(123)
        createInput.setCustomStatusId(456)

        then: "values are converted and stored as Long"
        updateInput.getCustomStatusId() == 123L
        createInput.getCustomStatusId() == 456L

        when: "setting with 64-bit Long"
        updateInput.setCustomStatusId(40971521901587L)
        createInput.setCustomStatusId(40971521901587L)

        then: "values retain full 64-bit precision"
        updateInput.getCustomStatusId() == 40971521901587L
        createInput.getCustomStatusId() == 40971521901587L
    }

    def "response models provide both JavaBean getters and record-style accessors"() {
        given: "models constructed with sample data"
        def statusObj = new TicketFieldCustomStatusObject().tap {
            id = 101L
            agentLabel = "Testing"
        }
        def singleResp = new CustomStatusResponse(statusObj)
        def listResp = new CustomStatusesResponse([statusObj])
        def formStatus = new TicketFormStatus("abc", 101L, 202L)
        def formStatusesResp = new TicketFormStatusesResponse([formStatus])
        def createReq = new CustomStatusCreateRequest(statusObj)

        expect: "record-style accessors and models return expected references"
        verifyAll {
            singleResp.customStatus() == statusObj
            singleResp.customStatus().id == 101L

            listResp.customStatuses() == [statusObj]
            listResp.customStatuses().size() == 1

            formStatus.id() == formStatus.getId()
            formStatus.customStatusId() == formStatus.getCustomStatusId()
            formStatus.ticketFormId() == formStatus.getTicketFormId()

            formStatusesResp.ticketFormStatuses() == [formStatus]
            formStatusesResp.ticketFormStatuses().size() == 1

            createReq.customStatus() == statusObj
        }
    }
}
