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
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

class TicketCreateInputSpec extends Z4jSpec {

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
    ObjectMapper objectMapper

    void setupSpec() {
        collaborator1 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        collaborator2 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        customField1 = new TicketCustomField.Text(faker.number().randomNumber(), faker.lorem().word())
        customField2 = new TicketCustomField.Text(faker.number().randomNumber(), faker.lorem().word())
        emailCc1 = new EmailCC(userId: faker.number().randomNumber())
        emailCc2 = new EmailCC(userId: faker.number().randomNumber())
        follower1 = new Follower(userId: faker.number().randomNumber())
        follower2 = new Follower(userId: faker.number().randomNumber())
        ticketComment = new TicketComment(body: faker.lorem().sentence())
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "should add #propertyName via #methodName and assign it the value of #property"() {
        given:
        def ticketCreateInput = new TicketCreateInput(ticketComment)
        ticketCreateInput."$propertyName" == null

        when:
        ticketCreateInput."$methodName"(property)

        then:
        ticketCreateInput."$propertyName".size() == 1
        ticketCreateInput."$propertyName".getAt(0) == property

        where:
        propertyName              | methodName                       | property
        'additionalCollaborators' | 'addAdditionalCollaboratorsItem' | collaborator1
        'attributeValueIds'       | 'addAttributeValueIdsItem'       | 1L
        'collaboratorIds'         | 'addCollaboratorIdsItem'         | 2L
        'customFields'            | 'addCustomFieldsItem'            | customField1
        'emailCcs'                | 'addEmailCcsItem'                | emailCc1
        'followers'               | 'addFollowersItem'               | follower1
        'sharingAgreementIds'     | 'addSharingAgreementIdsItem'     | 3L
        'tags'                    | 'addTagsItem'                    | "tag1"
        'additionalTags'          | 'addAdditionalTagsItem'          | "add1"
        'removeTags'              | 'addRemoveTagsItem'              | "rem1"
        'collaborators'           | 'addCollaboratorsItem'           | collaborator2
        'emailCcIds'              | 'addEmailCcIdsItem'              | 4L
        'followerIds'             | 'addFollowerIdsItem'             | 5L
        'macroIds'                | 'addMacroIdsItem'                | 6L
    }

    @Unroll
    def "add #property to #propertyName via #methodName. Property #propertyName already contains #existingProperty"() {
        given:
        def ticketCreateInput = new TicketCreateInput(ticketComment)
        ticketCreateInput."$propertyName" = existingProperty.clone()

        when:
        ticketCreateInput."$methodName"(property)

        then:
        ticketCreateInput."$propertyName".size() == (existingProperty.size() + 1)
        ticketCreateInput."$propertyName".containsAll(existingProperty)
        ticketCreateInput."$propertyName".contains(property)

        where:
        propertyName              | methodName                       | existingProperty | property
        'additionalCollaborators' | 'addAdditionalCollaboratorsItem' | [collaborator1]  | collaborator2
        'attributeValueIds'       | 'addAttributeValueIdsItem'       | [10L]            | 1L
        'collaboratorIds'         | 'addCollaboratorIdsItem'         | [20L]            | 2L
        'customFields'            | 'addCustomFieldsItem'            | [customField1]   | customField2
        'emailCcs'                | 'addEmailCcsItem'                | [emailCc1]       | emailCc2
        'followers'               | 'addFollowersItem'               | [follower1]      | follower2
        'sharingAgreementIds'     | 'addSharingAgreementIdsItem'     | [30L]            | 3L
        'tags'                    | 'addTagsItem'                    | ["existing"]     | "tag"
        'additionalTags'          | 'addAdditionalTagsItem'          | ["existing_add"] | "new_add"
        'removeTags'              | 'addRemoveTagsItem'              | ["existing_rem"] | "new_rem"
        'collaborators'           | 'addCollaboratorsItem'           | [collaborator2]  | collaborator1
        'emailCcIds'              | 'addEmailCcIdsItem'              | [40L]            | 4L
        'followerIds'             | 'addFollowerIdsItem'             | [50L]            | 5L
        'macroIds'                | 'addMacroIdsItem'                | [60L]            | 6L
    }

    def "should handle 64-bit Long IDs and boundary values for ID fields"() {
        given:
        Long largeRequesterId = 40971521901587L
        Long boundaryProblemId = ((Long) Integer.MAX_VALUE) + 1L
        Long largeGroupId = 98765432109876L
        Long largeOrgId = 12345678901234L
        Long largeAssigneeId = 777888999000L
        def input = new TicketCreateInput(ticketComment)

        when:
        input.setRequesterId(largeRequesterId)
        input.setProblemId(boundaryProblemId)
        input.setGroupId(largeGroupId)
        input.setOrganizationId(largeOrgId)
        input.setAssigneeId(largeAssigneeId)

        then:
        input.getRequesterId() == largeRequesterId
        input.getProblemId() == boundaryProblemId
        input.getGroupId() == largeGroupId
        input.getOrganizationId() == largeOrgId
        input.getAssigneeId() == largeAssigneeId
    }

    def "should allow clearing IDs with null unambiguously"() {
        given:
        def input = new TicketCreateInput(ticketComment)
                .setRequesterId(12345L)
                .setProblemId(67890L)
                .setAssigneeId(11111L)
                .setGroupId(22222L)
                .setOrganizationId(33333L)

        when:
        input.setRequesterId(null)
        input.setProblemId(null)
        input.setAssigneeId(null)
        input.setGroupId(null)
        input.setOrganizationId(null)

        then:
        input.getRequesterId() == null
        input.getProblemId() == null
        input.getAssigneeId() == null
        input.getGroupId() == null
        input.getOrganizationId() == null
    }

    def "should retain numeric fidelity during JSON serialization and deserialization for 64-bit IDs"() {
        given:
        Long largeRequesterId = 40971521901587L
        Long largeProblemId = 98765432109876L
        Long largeGroupId = 555666777888L
        Long largeOrgId = 999888777666L
        Long largeAssigneeId = 444333222111L
        def input = new TicketCreateInput(ticketComment)
                .setRequesterId(largeRequesterId)
                .setProblemId(largeProblemId)
                .setGroupId(largeGroupId)
                .setOrganizationId(largeOrgId)
                .setAssigneeId(largeAssigneeId)

        when:
        String json = objectMapper.writeValueAsString(input)
        Map parsedMap = objectMapper.readValue(json, Map.class)
        def deserialized = objectMapper.readValue(json, TicketCreateInput.class)

        then:
        parsedMap["requester_id"] == largeRequesterId
        parsedMap["problem_id"] == largeProblemId
        parsedMap["group_id"] == largeGroupId
        parsedMap["organization_id"] == largeOrgId
        parsedMap["assignee_id"] == largeAssigneeId
        deserialized.getRequesterId() == largeRequesterId
        deserialized.getProblemId() == largeProblemId
        deserialized.getGroupId() == largeGroupId
        deserialized.getOrganizationId() == largeOrgId
        deserialized.getAssigneeId() == largeAssigneeId
    }

    def "should serialize and deserialize additional_tags and remove_tags directly and within TicketCreateRequest"() {
        given:
        def input = new TicketCreateInput(ticketComment)
                .addAdditionalTagsItem("tag_to_add")
                .addRemoveTagsItem("tag_to_remove")
                .addTagsItem("base_tag")

        when: "serializing TicketCreateInput directly"
        String json = objectMapper.writeValueAsString(input)
        Map parsedMap = objectMapper.readValue(json, Map.class)
        def deserialized = objectMapper.readValue(json, TicketCreateInput.class)

        then:
        parsedMap["additional_tags"] == ["tag_to_add"]
        parsedMap["remove_tags"] == ["tag_to_remove"]
        parsedMap["tags"] == ["base_tag"]
        deserialized.getAdditionalTags() == ["tag_to_add"]
        deserialized.getRemoveTags() == ["tag_to_remove"]
        deserialized.getTags() == ["base_tag"]

        when: "serializing within TicketCreateRequest wrapper"
        def req = new TicketCreateRequest(input)
        String reqJson = objectMapper.writeValueAsString(req)
        Map reqMap = objectMapper.readValue(reqJson, Map.class)

        then:
        reqMap["ticket"] instanceof Map
        ((Map) reqMap["ticket"])["additional_tags"] == ["tag_to_add"]
        ((Map) reqMap["ticket"])["remove_tags"] == ["tag_to_remove"]
        ((Map) reqMap["ticket"])["tags"] == ["base_tag"]
    }
}
