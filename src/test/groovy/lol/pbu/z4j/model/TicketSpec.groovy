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
package lol.pbu.z4j.model

import io.micronaut.serde.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

import java.time.ZoneOffset
import java.time.ZonedDateTime

class TicketSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Ticket with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.of(2026, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)
        Collaborator collab = new Collaborator("collab@example.com", "Collab User")
        TicketCustomField customField = new TicketCustomField.Text(12345L, "custom_val")
        TicketVia testVia = new TicketVia()

        when:
        Ticket direct = new Ticket(
                1001L,
                true,
                false,
                "agent@example.com",
                2001L,
                [10L],
                3001L,
                [4001L],
                [collab],
                [body: "Ticket comment"],
                now,
                [customField],
                5001L,
                "Need assistance",
                now,
                [6001L],
                [email: "cc@example.com"],
                "ext-ticket-1",
                [7001L],
                [follower: "agent2"],
                [8001L],
                9001L,
                false,
                1730000000L,
                1111L,
                false,
                2222L,
                true,
                3333L,
                [3333L],
                [source: "web"],
                4444L,
                TicketPriority.HIGH,
                5555L,
                "Raw subject text",
                "support@example.com",
                [name: "Requester Name"],
                true,
                [score: "good"],
                [6666L],
                TicketStatus.OPEN,
                "Printer Help",
                7777L,
                ["hardware", "urgent"],
                8888L,
                TicketType.INCIDENT,
                now,
                "stamp123",
                "https://example.zendesk.com/tickets/2222",
                testVia,
                9999L,
                1212L,
                [voice: "recording"]
        )
        Ticket noArg = new Ticket()
        Ticket requesterOnly = new Ticket(1001L)
        Ticket built = TicketBuilder.builder()
                .requesterId(1001L)
                .allowAttachments(true)
                .allowChannelback(false)
                .assigneeEmail("agent@example.com")
                .assigneeId(2001L)
                .attributeValueIds([10L])
                .brandId(3001L)
                .collaboratorIds([4001L])
                .collaborators([collab])
                .comment([body: "Ticket comment"])
                .createdAt(now)
                .customFields([customField])
                .customStatusId(5001L)
                .description("Need assistance")
                .dueAt(now)
                .emailCcIds([6001L])
                .emailCcs([email: "cc@example.com"])
                .externalId("ext-ticket-1")
                .followerIds([7001L])
                .followers([follower: "agent2"])
                .followupIds([8001L])
                .forumTopicId(9001L)
                .fromMessagingChannel(false)
                .generatedTimestamp(1730000000L)
                .groupId(1111L)
                .hasIncidents(false)
                .id(2222L)
                .isPublic(true)
                .macroId(3333L)
                .macroIds([3333L])
                .metadata([source: "web"])
                .organizationId(4444L)
                .priority(TicketPriority.HIGH)
                .problemId(5555L)
                .rawSubject("Raw subject text")
                .recipient("support@example.com")
                .requester([name: "Requester Name"])
                .safeUpdate(true)
                .satisfactionRating([score: "good"])
                .sharingAgreementIds([6666L])
                .status(TicketStatus.OPEN)
                .subject("Printer Help")
                .submitterId(7777L)
                .tags(["hardware", "urgent"])
                .ticketFormId(8888L)
                .type(TicketType.INCIDENT)
                .updatedAt(now)
                .updatedStamp("stamp123")
                .url("https://example.zendesk.com/tickets/2222")
                .via(testVia)
                .viaFollowupSourceId(9999L)
                .viaId(1212L)
                .voiceComment([voice: "recording"])
                .build()

        then:
        verifyAll(direct) {
            requesterId() == 1001L
            allowAttachments() == true
            allowChannelback() == false
            assigneeEmail() == "agent@example.com"
            assigneeId() == 2001L
            attributeValueIds() == [10L]
            brandId() == 3001L
            collaboratorIds() == [4001L]
            collaborators() == [collab]
            comment() == [body: "Ticket comment"]
            createdAt() == now
            customFields() == [customField]
            customStatusId() == 5001L
            description() == "Need assistance"
            dueAt() == now
            emailCcIds() == [6001L]
            emailCcs() == [email: "cc@example.com"]
            externalId() == "ext-ticket-1"
            followerIds() == [7001L]
            followers() == [follower: "agent2"]
            followupIds() == [8001L]
            forumTopicId() == 9001L
            fromMessagingChannel() == false
            generatedTimestamp() == 1730000000L
            groupId() == 1111L
            hasIncidents() == false
            id() == 2222L
            isPublic() == true
            macroId() == 3333L
            macroIds() == [3333L]
            metadata() == [source: "web"]
            organizationId() == 4444L
            priority() == TicketPriority.HIGH
            problemId() == 5555L
            rawSubject() == "Raw subject text"
            recipient() == "support@example.com"
            requester() == [name: "Requester Name"]
            safeUpdate() == true
            satisfactionRating() == [score: "good"]
            sharingAgreementIds() == [6666L]
            status() == TicketStatus.OPEN
            subject() == "Printer Help"
            submitterId() == 7777L
            tags() == ["hardware", "urgent"]
            ticketFormId() == 8888L
            type() == TicketType.INCIDENT
            updatedAt() == now
            updatedStamp() == "stamp123"
            url() == "https://example.zendesk.com/tickets/2222"
            via() == testVia
            viaFollowupSourceId() == 9999L
            viaId() == 1212L
            voiceComment() == [voice: "recording"]
        }
        verifyAll(noArg) {
            requesterId() == null
            id() == null
            subject() == null
        }
        verifyAll(requesterOnly) {
            requesterId() == 1001L
            id() == null
        }
        verifyAll(built) {
            it == direct
            requesterId() == 1001L
            id() == 2222L
            subject() == "Printer Help"
        }
    }

    def "should update Ticket using withers and overloaded withCustomStatusId"() {
        given:
        Ticket initial = new Ticket()

        when:
        Ticket updated = initial
                .withRequesterId(123L)
                .withId(456L)
                .withSubject("New Ticket")
                .withStatus(TicketStatus.PENDING)
                .withPriority(TicketPriority.LOW)
                .withCustomStatusId(Integer.valueOf(789))
        Ticket withNullCustomStatus = updated.withCustomStatusId((Integer) null)

        then:
        verifyAll(updated) {
            requesterId() == 123L
            id() == 456L
            subject() == "New Ticket"
            status() == TicketStatus.PENDING
            priority() == TicketPriority.LOW
            customStatusId() == 789L
        }
        verifyAll(withNullCustomStatus) {
            customStatusId() == null
        }
    }

    def "should add items to null and populated lists"() {
        given:
        Ticket ticket = new Ticket()
        Collaborator collab1 = new Collaborator("c1@example.com", "C1")
        Collaborator collab2 = new Collaborator("c2@example.com", "C2")
        TicketCustomField field1 = new TicketCustomField.Text(1L, "f1")
        TicketCustomField field2 = new TicketCustomField.Text(2L, "f2")

        when: "adding to null lists"
        Ticket t1 = ticket
                .addAttributeValueIdsItem(10L)
                .addCollaboratorIdsItem(20L)
                .addCollaboratorsItem(collab1)
                .addCustomFieldsItem(field1)
                .addEmailCcIdsItem(30L)
                .addFollowerIdsItem(40L)
                .addMacroIdsItem(50L)
                .addSharingAgreementIdsItem(60L)
                .addTagsItem("tag1")

        then:
        verifyAll(t1) {
            attributeValueIds() == [10L]
            collaboratorIds() == [20L]
            collaborators() == [collab1]
            customFields() == [field1]
            emailCcIds() == [30L]
            followerIds() == [40L]
            macroIds() == [50L]
            sharingAgreementIds() == [60L]
            tags() == ["tag1"]
        }

        when: "adding to populated lists"
        Ticket t2 = t1
                .addAttributeValueIdsItem(11L)
                .addCollaboratorIdsItem(21L)
                .addCollaboratorsItem(collab2)
                .addCustomFieldsItem(field2)
                .addEmailCcIdsItem(31L)
                .addFollowerIdsItem(41L)
                .addMacroIdsItem(51L)
                .addSharingAgreementIdsItem(61L)
                .addTagsItem("tag2")

        then:
        verifyAll(t2) {
            attributeValueIds() == [10L, 11L]
            collaboratorIds() == [20L, 21L]
            collaborators() == [collab1, collab2]
            customFields() == [field1, field2]
            emailCcIds() == [30L, 31L]
            followerIds() == [40L, 41L]
            macroIds() == [50L, 51L]
            sharingAgreementIds() == [60L, 61L]
            tags() == ["tag1", "tag2"]
        }
    }

    def "should round-trip serialize and deserialize Ticket"() {
        given:
        Ticket ticket = new Ticket()
                .withRequesterId(999L)
                .withId(888L)
                .withSubject("Round trip ticket")
                .withStatus(TicketStatus.CLOSED)
                .withTags(["test"])

        when:
        String json = objectMapper.writeValueAsString(ticket)
        Map map = objectMapper.readValue(json, Map.class)
        Ticket deserialized = objectMapper.readValue(json, Ticket.class)

        then:
        verifyAll(map) {
            get("requester_id") == 999
            get("id") == 888
            get("subject") == "Round trip ticket"
            get("status") == "closed"
            get("tags") == ["test"]
        }
        verifyAll(deserialized) {
            it == ticket
            requesterId() == 999L
            id() == 888L
            subject() == "Round trip ticket"
            status() == TicketStatus.CLOSED
            tags() == ["test"]
        }
    }

    def "should verify Ticket constants"() {
        expect:
        verifyAll {
            Ticket.JSON_PROPERTY_REQUESTER_ID == "requester_id"
            Ticket.JSON_PROPERTY_ALLOW_ATTACHMENTS == "allow_attachments"
            Ticket.JSON_PROPERTY_ALLOW_CHANNELBACK == "allow_channelback"
            Ticket.JSON_PROPERTY_ASSIGNEE_EMAIL == "assignee_email"
            Ticket.JSON_PROPERTY_ASSIGNEE_ID == "assignee_id"
            Ticket.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS == "attribute_value_ids"
            Ticket.JSON_PROPERTY_BRAND_ID == "brand_id"
            Ticket.JSON_PROPERTY_COLLABORATOR_IDS == "collaborator_ids"
            Ticket.JSON_PROPERTY_COLLABORATORS == "collaborators"
            Ticket.JSON_PROPERTY_COMMENT == "comment"
            Ticket.JSON_PROPERTY_CREATED_AT == "created_at"
            Ticket.JSON_PROPERTY_CUSTOM_FIELDS == "custom_fields"
            Ticket.JSON_PROPERTY_CUSTOM_STATUS_ID == "custom_status_id"
            Ticket.JSON_PROPERTY_DESCRIPTION == "description"
            Ticket.JSON_PROPERTY_DUE_AT == "due_at"
            Ticket.JSON_PROPERTY_EMAIL_CC_IDS == "email_cc_ids"
            Ticket.JSON_PROPERTY_EMAIL_CCS == "email_ccs"
            Ticket.JSON_PROPERTY_EXTERNAL_ID == "external_id"
            Ticket.JSON_PROPERTY_FOLLOWER_IDS == "follower_ids"
            Ticket.JSON_PROPERTY_FOLLOWERS == "followers"
            Ticket.JSON_PROPERTY_FOLLOWUP_IDS == "followup_ids"
            Ticket.JSON_PROPERTY_FORUM_TOPIC_ID == "forum_topic_id"
            Ticket.JSON_PROPERTY_FROM_MESSAGING_CHANNEL == "from_messaging_channel"
            Ticket.JSON_PROPERTY_GENERATED_TIMESTAMP == "generated_timestamp"
            Ticket.JSON_PROPERTY_GROUP_ID == "group_id"
            Ticket.JSON_PROPERTY_HAS_INCIDENTS == "has_incidents"
            Ticket.JSON_PROPERTY_ID == "id"
            Ticket.JSON_PROPERTY_IS_PUBLIC == "is_public"
            Ticket.JSON_PROPERTY_MACRO_ID == "macro_id"
            Ticket.JSON_PROPERTY_MACRO_IDS == "macro_ids"
            Ticket.JSON_PROPERTY_METADATA == "metadata"
            Ticket.JSON_PROPERTY_ORGANIZATION_ID == "organization_id"
            Ticket.JSON_PROPERTY_PRIORITY == "priority"
            Ticket.JSON_PROPERTY_PROBLEM_ID == "problem_id"
            Ticket.JSON_PROPERTY_RAW_SUBJECT == "raw_subject"
            Ticket.JSON_PROPERTY_RECIPIENT == "recipient"
            Ticket.JSON_PROPERTY_REQUESTER == "requester"
            Ticket.JSON_PROPERTY_SAFE_UPDATE == "safe_update"
            Ticket.JSON_PROPERTY_SATISFACTION_RATING == "satisfaction_rating"
            Ticket.JSON_PROPERTY_SHARING_AGREEMENT_IDS == "sharing_agreement_ids"
            Ticket.JSON_PROPERTY_STATUS == "status"
            Ticket.JSON_PROPERTY_SUBJECT == "subject"
            Ticket.JSON_PROPERTY_SUBMITTER_ID == "submitter_id"
            Ticket.JSON_PROPERTY_TAGS == "tags"
            Ticket.JSON_PROPERTY_TICKET_FORM_ID == "ticket_form_id"
            Ticket.JSON_PROPERTY_TYPE == "type"
            Ticket.JSON_PROPERTY_UPDATED_AT == "updated_at"
            Ticket.JSON_PROPERTY_UPDATED_STAMP == "updated_stamp"
            Ticket.JSON_PROPERTY_URL == "url"
            Ticket.JSON_PROPERTY_VIA == "via"
            Ticket.JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID == "via_followup_source_id"
            Ticket.JSON_PROPERTY_VIA_ID == "via_id"
            Ticket.JSON_PROPERTY_VOICE_COMMENT == "voice_comment"
        }
    }
}
