package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

class TicketCreateInputSpec extends Z4jSpec {

    @Shared
    Collaborator collaborator1, collaborator2
    @Shared
    CustomField customField1, customField2
    @Shared
    EmailCC emailCc1, emailCc2
    @Shared
    Follower follower1, follower2
    @Shared
    TicketComment ticketComment

    void setupSpec() {
        collaborator1 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        collaborator2 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        customField1 = new CustomField()
        customField2 = new CustomField()
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
        propertyName              | methodName                       | property
        'additionalCollaborators' | 'addAdditionalCollaboratorsItem' | collaborator1
        'attributeValueIds'       | 'addAttributeValueIdsItem'       | 1
        'collaboratorIds'         | 'addCollaboratorIdsItem'         | 2
        'customFields'            | 'addCustomFieldsItem'            | customField1
        'emailCcs'                | 'addEmailCcsItem'                | emailCc1
        'followers'               | 'addFollowersItem'               | follower1
        'sharingAgreementIds'     | 'addSharingAgreementIdsItem'     | 3
        'tags'                    | 'addTagsItem'                    | "tag1"
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
        'attributeValueIds'       | 'addAttributeValueIdsItem'       | [10]             | 1
        'collaboratorIds'         | 'addCollaboratorIdsItem'         | [20]             | 2
        'customFields'            | 'addCustomFieldsItem'            | [customField1]   | customField2
        'emailCcs'                | 'addEmailCcsItem'                | [emailCc1]       | emailCc2
        'followers'               | 'addFollowersItem'               | [follower1]      | follower2
        'sharingAgreementIds'     | 'addSharingAgreementIdsItem'     | [30]             | 3
        'tags'                    | 'addTagsItem'                    | ["existing"]     | "tag"
        'collaborators'           | 'addCollaboratorsItem'           | [collaborator2]  | collaborator1
        'emailCcIds'              | 'addEmailCcIdsItem'              | [40L]            | 4L
        'followerIds'             | 'addFollowerIdsItem'             | [50L]            | 5L
        'macroIds'                | 'addMacroIdsItem'                | [60L]            | 6L
    }
}
