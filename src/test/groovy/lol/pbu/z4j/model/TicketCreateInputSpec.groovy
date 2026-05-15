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

import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

class TicketCreateInputSpec extends Z4jSpec {

    @Shared
    Collaborator collaborator1, collaborator2
    @Shared
    TicketCustomFieldsInner customField1, customField2
    @Shared
    EmailCC emailCc1, emailCc2
    @Shared
    Follower follower1, follower2
    @Shared
    TicketComment ticketComment

    void setupSpec() {
        collaborator1 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        collaborator2 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        customField1 = new TicketCustomFieldBoolean()
        customField2 = new TicketCustomFieldStringArray()
        emailCc1 = new EmailCC(userId: faker.number().randomNumber())
        emailCc2 = new EmailCC(userId: faker.number().randomNumber())
        follower1 = new Follower(userId: faker.number().randomNumber())
        follower2 = new Follower(userId: faker.number().randomNumber())
        ticketComment = new TicketComment(body: faker.lorem().sentence())
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
        propertyName              | methodName                  | property
        'additionalCollaborators' | 'addAdditionalCollaborator' | collaborator1
        'attributeValueIds'       | 'addAttributeValueId'       | 1
        'collaboratorIds'         | 'addCollaboratorId'         | 2
        'customFields'            | 'addCustomField'            | customField1
        'emailCcs'                | 'addEmailCc'                | emailCc1
        'followers'               | 'addFollower'               | follower1
        'sharingAgreementIds'     | 'addSharingAgreementId'     | 3
        'tags'                    | 'addTag'                    | "tag1"
        'collaborators'           | 'addCollaborator'           | collaborator2
        'emailCcIds'              | 'addEmailCcId'              | 4L
        'followerIds'             | 'addFollowerId'             | 5L
        'macroIds'                | 'addMacroId'                | 6L
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
        propertyName              | methodName                  | existingProperty | property
        'additionalCollaborators' | 'addAdditionalCollaborator' | [collaborator1]  | collaborator2
        'attributeValueIds'       | 'addAttributeValueId'       | [10]             | 1
        'collaboratorIds'         | 'addCollaboratorId'         | [20]             | 2
        'customFields'            | 'addCustomField'            | [customField1]   | customField2
        'emailCcs'                | 'addEmailCc'                | [emailCc1]       | emailCc2
        'followers'               | 'addFollower'               | [follower1]      | follower2
        'sharingAgreementIds'     | 'addSharingAgreementId'     | [30]             | 3
        'tags'                    | 'addTag'                    | ["existing"]     | "tag"
        'collaborators'           | 'addCollaborator'           | [collaborator2]  | collaborator1
        'emailCcIds'              | 'addEmailCcId'              | [40L]            | 4L
        'followerIds'             | 'addFollowerId'             | [50L]            | 5L
        'macroIds'                | 'addMacroId'                | [60L]            | 6L
    }
}

