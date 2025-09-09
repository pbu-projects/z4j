package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

class TicketSpec extends Z4jSpec {

    @Shared
    Collaborator collaborator1, collaborator2

    @Shared
    TicketCustomFieldsInner customField1, customField2

    void setupSpec() {
        collaborator1 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        collaborator2 = new Collaborator(name: faker.name().fullName(), email: faker.internet().emailAddress())
        customField1 = new TicketCustomFieldsInner(id: faker.number().randomNumber())
        customField2 = new TicketCustomFieldsInner(id: faker.number().randomNumber())
    }

    @Unroll
    def "should add #propertyName via #methodName and assign it the value of #property"() {
        given:
        def ticket = new Ticket(faker.number().randomNumber())
        ticket."$propertyName" == null

        when:
        ticket."$methodName"(property)

        then:
        ticket."$propertyName".size() == 1
        ticket."$propertyName".getAt(0) == property

        where:
        propertyName          | methodName                   | property
        'attributeValueIds'   | 'addAttributeValueIdsItem'   | 1L
        'collaboratorIds'     | 'addCollaboratorIdsItem'     | 2L
        'collaborators'       | 'addCollaboratorsItem'       | collaborator1
        'customFields'        | 'addCustomFieldsItem'        | customField1
        'emailCcIds'          | 'addEmailCcIdsItem'          | 3L
        'followerIds'         | 'addFollowerIdsItem'         | 4L
        'macroIds'            | 'addMacroIdsItem'            | 5L
        'sharingAgreementIds' | 'addSharingAgreementIdsItem' | 6L
        'tags'                | 'addTagsItem'                | 'tag'
    }

    @Unroll
    def "add #property to #propertyName via #methodName. Property #propertyName already contains #existingProperty"() {
        given:
        def ticket = new Ticket(faker.number().randomNumber())
        ticket."$propertyName" = existingProperty.clone()

        when:
        ticket."$methodName"(property)

        then:
        ticket."$propertyName".size() == (existingProperty.size() + 1)
        ticket."$propertyName".containsAll(existingProperty)
        ticket."$propertyName".contains(property)


        where:
        propertyName          | methodName                   | existingProperty | property
        'attributeValueIds'   | 'addAttributeValueIdsItem'   | [10L]            | 1L
        'collaboratorIds'     | 'addCollaboratorIdsItem'     | [20L]            | 2L
        'collaborators'       | 'addCollaboratorsItem'       | [collaborator1]  | collaborator2
        'customFields'        | 'addCustomFieldsItem'        | [customField1]   | customField2
        'emailCcIds'          | 'addEmailCcIdsItem'          | [30L]            | 3L
        'followerIds'         | 'addFollowerIdsItem'         | [40L]            | 4L
        'macroIds'            | 'addMacroIdsItem'            | [50L]            | 5L
        'sharingAgreementIds' | 'addSharingAgreementIdsItem' | [60L]            | 6L
        'tags'                | 'addTagsItem'                | ['existing']     | 'tag'
    }
}
