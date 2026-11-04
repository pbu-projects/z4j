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
import net.datafaker.Faker
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZoneOffset
import java.time.ZonedDateTime

class TicketCreateInputSpec extends Specification {

    @Shared
    Faker faker = new Faker()
    @Shared
    Collaborator collaborator1, collaborator2
    @Shared
    TicketCustomField customField1, customField2
    @Shared
    EmailCC emailCc1, emailCc2
    @Shared
    Follower follower1, follower2
    @Shared
    TicketComment ticketComment
    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    void setupSpec() {
        collaborator1 = new Collaborator(faker.internet().emailAddress(), faker.name().fullName())
        collaborator2 = new Collaborator(faker.internet().emailAddress(), faker.name().fullName())
        customField1 = new TicketCustomField.Text(faker.number().randomNumber(), faker.lorem().word())
        customField2 = new TicketCustomField.Text(faker.number().randomNumber(), faker.lorem().word())
        emailCc1 = EmailCCBuilder.builder().userId(faker.number().randomNumber()).build()
        emailCc2 = EmailCCBuilder.builder().userId(faker.number().randomNumber()).build()
        follower1 = FollowerBuilder.builder().userId(String.valueOf(faker.number().randomNumber())).build()
        follower2 = FollowerBuilder.builder().userId(String.valueOf(faker.number().randomNumber())).build()
        ticketComment = new TicketComment("Sample comment body")
    }

    def "should create TicketCreateInput with canonical constructor, no-arg constructor, 1-arg constructor, and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)
        Via expectedVia = new Via()
        TicketRequester expectedRequester = new TicketRequester("Jane Doe", "jane@example.com")

        when:
        TicketCreateInput direct = new TicketCreateInput(
                ticketComment,
                [collaborator1],
                ["add_tag"],
                "assignee@example.com",
                101L,
                [1L],
                [2L],
                [customField1],
                301L,
                now,
                [emailCc1],
                "ext_123",
                [follower1],
                401L,
                501L,
                TicketUpdateInputPriority.HIGH,
                601L,
                ["rem_tag"],
                701L,
                true,
                [801L],
                TicketUpdateInputStatus.OPEN,
                "Ticket subject",
                ["tag1"],
                TicketUpdateInputType.INCIDENT,
                now,
                901L,
                [collaborator2],
                [1001L],
                [1101L],
                [1201L],
                "Raw subject",
                "recipient@example.com",
                expectedRequester,
                1301L,
                1401L,
                expectedVia,
                1501L
        )
        TicketCreateInput noArg = new TicketCreateInput()
        TicketCreateInput commentOnly = new TicketCreateInput(ticketComment)
        TicketCreateInput built = TicketCreateInputBuilder.builder()
                .comment(ticketComment)
                .additionalCollaborators([collaborator1])
                .additionalTags(["add_tag"])
                .assigneeEmail("assignee@example.com")
                .assigneeId(101L)
                .attributeValueIds([1L])
                .collaboratorIds([2L])
                .customFields([customField1])
                .customStatusId(301L)
                .dueAt(now)
                .emailCcs([emailCc1])
                .externalId("ext_123")
                .followers([follower1])
                .groupId(401L)
                .organizationId(501L)
                .priority(TicketUpdateInputPriority.HIGH)
                .problemId(601L)
                .removeTags(["rem_tag"])
                .requesterId(701L)
                .safeUpdate(true)
                .sharingAgreementIds([801L])
                .status(TicketUpdateInputStatus.OPEN)
                .subject("Ticket subject")
                .tags(["tag1"])
                .type(TicketUpdateInputType.INCIDENT)
                .updatedStamp(now)
                .brandId(901L)
                .collaborators([collaborator2])
                .emailCcIds([1001L])
                .followerIds([1101L])
                .macroIds([1201L])
                .rawSubject("Raw subject")
                .recipient("recipient@example.com")
                .requester(expectedRequester)
                .submitterId(1301L)
                .ticketFormId(1401L)
                .via(expectedVia)
                .viaFollowupSourceId(1501L)
                .build()

        then:
        verifyAll(direct) {
            comment() == ticketComment
            additionalCollaborators() == [collaborator1]
            additionalTags() == ["add_tag"]
            assigneeEmail() == "assignee@example.com"
            assigneeId() == 101L
            attributeValueIds() == [1L]
            collaboratorIds() == [2L]
            customFields() == [customField1]
            customStatusId() == 301L
            dueAt() == now
            emailCcs() == [emailCc1]
            externalId() == "ext_123"
            followers() == [follower1]
            groupId() == 401L
            organizationId() == 501L
            priority() == TicketUpdateInputPriority.HIGH
            problemId() == 601L
            removeTags() == ["rem_tag"]
            requesterId() == 701L
            safeUpdate() == true
            sharingAgreementIds() == [801L]
            status() == TicketUpdateInputStatus.OPEN
            subject() == "Ticket subject"
            tags() == ["tag1"]
            type() == TicketUpdateInputType.INCIDENT
            updatedStamp() == now
            brandId() == 901L
            collaborators() == [collaborator2]
            emailCcIds() == [1001L]
            followerIds() == [1101L]
            macroIds() == [1201L]
            rawSubject() == "Raw subject"
            recipient() == "recipient@example.com"
            requester == expectedRequester
            submitterId() == 1301L
            ticketFormId() == 1401L
            via == expectedVia
            viaFollowupSourceId() == 1501L
        }
        verifyAll(noArg) {
            comment() == null
            subject() == null
        }
        verifyAll(commentOnly) {
            comment() == ticketComment
            subject() == null
        }
        verifyAll(built) {
            it == direct
            brandId() == 901L
            subject() == "Ticket subject"
        }
    }

    @Unroll
    def "should add #propertyName via #methodName to null and existing list"() {
        given:
        def base = new TicketCreateInput(ticketComment)

        when: "adding to null list"
        def withFirst = base."$methodName"(item1)

        then:
        verifyAll(withFirst) {
            it."$propertyName"().size() == 1
            it."$propertyName"().get(0) == item1
        }

        when: "adding to existing list"
        def withSecond = withFirst."$methodName"(item2)

        then:
        verifyAll(withSecond) {
            it."$propertyName"().size() == 2
            it."$propertyName"().get(0) == item1
            it."$propertyName"().get(1) == item2
        }

        where:
        propertyName              | methodName                       | item1          | item2
        'additionalCollaborators' | 'addAdditionalCollaboratorsItem' | collaborator1  | collaborator2
        'attributeValueIds'       | 'addAttributeValueIdsItem'       | 1L             | 2L
        'collaboratorIds'         | 'addCollaboratorIdsItem'         | 3L             | 4L
        'customFields'            | 'addCustomFieldsItem'            | customField1   | customField2
        'emailCcs'                | 'addEmailCcsItem'                | emailCc1       | emailCc2
        'followers'               | 'addFollowersItem'               | follower1      | follower2
        'sharingAgreementIds'     | 'addSharingAgreementIdsItem'     | 5L             | 6L
        'tags'                    | 'addTagsItem'                    | "tag1"         | "tag2"
        'additionalTags'          | 'addAdditionalTagsItem'          | "add1"         | "add2"
        'removeTags'              | 'addRemoveTagsItem'              | "rem1"         | "rem2"
        'collaborators'           | 'addCollaboratorsItem'           | collaborator1  | collaborator2
        'emailCcIds'              | 'addEmailCcIdsItem'              | 7L             | 8L
        'followerIds'             | 'addFollowerIdsItem'             | 9L             | 10L
        'macroIds'                | 'addMacroIdsItem'                | 11L            | 12L
    }

    def "should handle 64-bit Long IDs and boundary values for ID fields"() {
        given:
        Long largeRequesterId = 40971521901587L
        Long boundaryProblemId = ((Long) Integer.MAX_VALUE) + 1L
        Long largeGroupId = 98765432109876L
        Long largeOrgId = 12345678901234L
        Long largeAssigneeId = 777888999000L

        when:
        def input = new TicketCreateInput(ticketComment)
                .withRequesterId(largeRequesterId)
                .withProblemId(boundaryProblemId)
                .withGroupId(largeGroupId)
                .withOrganizationId(largeOrgId)
                .withAssigneeId(largeAssigneeId)

        then:
        verifyAll(input) {
            requesterId() == largeRequesterId
            problemId() == boundaryProblemId
            groupId() == largeGroupId
            organizationId() == largeOrgId
            assigneeId() == largeAssigneeId
        }
    }

    def "should support withCustomStatusId Integer and Long overloads"() {
        when:
        def input = new TicketCreateInput()
                .withCustomStatusId(123)

        then:
        verifyAll(input) {
            customStatusId() == 123L
        }

        when:
        def withLong = input.withCustomStatusId(40971521901587L)

        then:
        verifyAll(withLong) {
            customStatusId() == 40971521901587L
        }

        when:
        def withNull = input.withCustomStatusId((Integer) null)

        then:
        verifyAll(withNull) {
            customStatusId() == null
        }
    }

    def "should round-trip serialize and deserialize TicketCreateInput"() {
        given:
        TicketRequester req = new TicketRequester("Jane Doe", "jane@example.com")
        TicketCreateInput input = new TicketCreateInput(ticketComment)
                .withSubject("Test subject")
                .withRawSubject("Raw subject")
                .withRequester(req)
                .withPriority(TicketUpdateInputPriority.URGENT)
                .withStatus(TicketUpdateInputStatus.OPEN)
                .withType(TicketUpdateInputType.PROBLEM)
                .withAdditionalTags(["tag1"])
                .withRemoveTags(["tag2"])
                .withTags(["base_tag"])

        when:
        String json = objectMapper.writeValueAsString(input)
        Map map = objectMapper.readValue(json, Map.class)
        TicketCreateInput deserialized = objectMapper.readValue(json, TicketCreateInput.class)

        then:
        verifyAll(map) {
            get("subject") == "Test subject"
            get("raw_subject") == "Raw subject"
            get("priority") == "urgent"
            get("status") == "open"
            get("type") == "problem"
            get("additional_tags") == ["tag1"]
            get("remove_tags") == ["tag2"]
            get("tags") == ["base_tag"]
        }
        verifyAll(deserialized) {
            it == input
            subject() == "Test subject"
            rawSubject() == "Raw subject"
            priority() == TicketUpdateInputPriority.URGENT
            status() == TicketUpdateInputStatus.OPEN
            type() == TicketUpdateInputType.PROBLEM
            additionalTags() == ["tag1"]
            removeTags() == ["tag2"]
            tags() == ["base_tag"]
        }
    }

    def "should verify TicketCreateInput constants"() {
        expect:
        verifyAll {
            TicketCreateInput.JSON_PROPERTY_BRAND_ID == "brand_id"
            TicketCreateInput.JSON_PROPERTY_COLLABORATORS == "collaborators"
            TicketCreateInput.JSON_PROPERTY_EMAIL_CC_IDS == "email_cc_ids"
            TicketCreateInput.JSON_PROPERTY_FOLLOWER_IDS == "follower_ids"
            TicketCreateInput.JSON_PROPERTY_MACRO_IDS == "macro_ids"
            TicketCreateInput.JSON_PROPERTY_RAW_SUBJECT == "raw_subject"
            TicketCreateInput.JSON_PROPERTY_RECIPIENT == "recipient"
            TicketCreateInput.JSON_PROPERTY_REQUESTER == "requester"
            TicketCreateInput.JSON_PROPERTY_SUBMITTER_ID == "submitter_id"
            TicketCreateInput.JSON_PROPERTY_TICKET_FORM_ID == "ticket_form_id"
            TicketCreateInput.JSON_PROPERTY_VIA == "via"
            TicketCreateInput.JSON_PROPERTY_VIA_FOLLOWUP_SOURCE_ID == "via_followup_source_id"
        }
    }
}
