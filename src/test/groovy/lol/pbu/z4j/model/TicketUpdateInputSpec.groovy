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

class TicketUpdateInputSpec extends Specification {

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
        ticketComment = new TicketComment("Sample update comment")
    }

    def "should create TicketUpdateInput with canonical constructor, no-arg constructor, and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)

        when:
        TicketUpdateInput direct = new TicketUpdateInput(
                [collaborator1],
                ["add_tag"],
                "assignee@example.com",
                101L,
                [1L],
                [2L],
                ticketComment,
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
                901L
        )
        TicketUpdateInput noArg = new TicketUpdateInput()
        TicketUpdateInput built = TicketUpdateInputBuilder.builder()
                .additionalCollaborators([collaborator1])
                .additionalTags(["add_tag"])
                .assigneeEmail("assignee@example.com")
                .assigneeId(101L)
                .attributeValueIds([1L])
                .collaboratorIds([2L])
                .comment(ticketComment)
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
                .ticketFormId(901L)
                .build()

        then:
        verifyAll(direct) {
            additionalCollaborators() == [collaborator1]
            additionalTags() == ["add_tag"]
            assigneeEmail() == "assignee@example.com"
            assigneeId() == 101L
            attributeValueIds() == [1L]
            collaboratorIds() == [2L]
            comment() == ticketComment
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
            ticketFormId() == 901L
        }
        verifyAll(noArg) {
            comment() == null
            subject() == null
            ticketFormId() == null
        }
        verifyAll(built) {
            it == direct
            ticketFormId() == 901L
            subject() == "Ticket subject"
        }
    }

    @Unroll
    def "should add #propertyName via #methodName to null and existing list"() {
        given:
        def base = new TicketUpdateInput()

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
        propertyName              | methodName                       | item1         | item2
        'additionalCollaborators' | 'addAdditionalCollaboratorsItem' | collaborator1 | collaborator2
        'attributeValueIds'       | 'addAttributeValueIdsItem'       | 1L            | 2L
        'collaboratorIds'         | 'addCollaboratorIdsItem'         | 3L            | 4L
        'customFields'            | 'addCustomFieldsItem'            | customField1  | customField2
        'emailCcs'                | 'addEmailCcsItem'                | emailCc1      | emailCc2
        'followers'               | 'addFollowersItem'               | follower1     | follower2
        'sharingAgreementIds'     | 'addSharingAgreementIdsItem'     | 5L            | 6L
        'tags'                    | 'addTagsItem'                    | "tag1"        | "tag2"
        'additionalTags'          | 'addAdditionalTagsItem'          | "add1"        | "add2"
        'removeTags'              | 'addRemoveTagsItem'              | "rem1"        | "rem2"
    }

    def "should handle 64-bit Long IDs and boundary values for ID fields"() {
        given:
        Long largeRequesterId = 40971521901587L
        Long boundaryProblemId = ((Long) Integer.MAX_VALUE) + 1L
        Long largeGroupId = 98765432109876L
        Long largeOrgId = 12345678901234L
        Long largeAssigneeId = 777888999000L

        when:
        def input = new TicketUpdateInput()
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
        def input = new TicketUpdateInput()
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

    def "should round-trip serialize and deserialize TicketUpdateInput"() {
        given:
        TicketUpdateInput input = new TicketUpdateInput()
                .withSubject("Test subject")
                .withTicketFormId(888L)
                .withPriority(TicketUpdateInputPriority.URGENT)
                .withStatus(TicketUpdateInputStatus.OPEN)
                .withType(TicketUpdateInputType.PROBLEM)
                .withAdditionalTags(["tag1"])
                .withRemoveTags(["tag2"])
                .withTags(["base_tag"])

        when:
        String json = objectMapper.writeValueAsString(input)
        Map map = objectMapper.readValue(json, Map.class)
        TicketUpdateInput deserialized = objectMapper.readValue(json, TicketUpdateInput.class)

        then:
        verifyAll(map) {
            get("subject") == "Test subject"
            get("ticket_form_id") == 888
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
            ticketFormId() == 888L
            priority() == TicketUpdateInputPriority.URGENT
            status() == TicketUpdateInputStatus.OPEN
            type() == TicketUpdateInputType.PROBLEM
            additionalTags() == ["tag1"]
            removeTags() == ["tag2"]
            tags() == ["base_tag"]
        }
    }

    def "should verify TicketUpdateInput and TicketInputBase constants"() {
        expect:
        verifyAll {
            TicketUpdateInput.JSON_PROPERTY_TICKET_FORM_ID == "ticket_form_id"
            TicketInputBase.JSON_PROPERTY_COMMENT == "comment"
            TicketInputBase.JSON_PROPERTY_ADDITIONAL_COLLABORATORS == "additional_collaborators"
            TicketInputBase.JSON_PROPERTY_ASSIGNEE_EMAIL == "assignee_email"
            TicketInputBase.JSON_PROPERTY_ASSIGNEE_ID == "assignee_id"
            TicketInputBase.JSON_PROPERTY_ATTRIBUTE_VALUE_IDS == "attribute_value_ids"
            TicketInputBase.JSON_PROPERTY_COLLABORATOR_IDS == "collaborator_ids"
            TicketInputBase.JSON_PROPERTY_CUSTOM_FIELDS == "custom_fields"
            TicketInputBase.JSON_PROPERTY_CUSTOM_STATUS_ID == "custom_status_id"
            TicketInputBase.JSON_PROPERTY_DUE_AT == "due_at"
            TicketInputBase.JSON_PROPERTY_EMAIL_CCS == "email_ccs"
            TicketInputBase.JSON_PROPERTY_EXTERNAL_ID == "external_id"
            TicketInputBase.JSON_PROPERTY_FOLLOWERS == "followers"
            TicketInputBase.JSON_PROPERTY_GROUP_ID == "group_id"
            TicketInputBase.JSON_PROPERTY_ORGANIZATION_ID == "organization_id"
            TicketInputBase.JSON_PROPERTY_PRIORITY == "priority"
            TicketInputBase.JSON_PROPERTY_PROBLEM_ID == "problem_id"
            TicketInputBase.JSON_PROPERTY_REQUESTER_ID == "requester_id"
            TicketInputBase.JSON_PROPERTY_SAFE_UPDATE == "safe_update"
            TicketInputBase.JSON_PROPERTY_SHARING_AGREEMENT_IDS == "sharing_agreement_ids"
            TicketInputBase.JSON_PROPERTY_STATUS == "status"
            TicketInputBase.JSON_PROPERTY_SUBJECT == "subject"
            TicketInputBase.JSON_PROPERTY_TAGS == "tags"
            TicketInputBase.JSON_PROPERTY_ADDITIONAL_TAGS == "additional_tags"
            TicketInputBase.JSON_PROPERTY_REMOVE_TAGS == "remove_tags"
            TicketInputBase.JSON_PROPERTY_TYPE == "type"
            TicketInputBase.JSON_PROPERTY_UPDATED_STAMP == "updated_stamp"
        }
    }
}
