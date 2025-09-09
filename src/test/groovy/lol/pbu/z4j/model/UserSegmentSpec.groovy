package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class UserSegmentSpec extends Z4jSpec {

    @Unroll
    def "should add #propertyName via #methodName and assign it the value of #property"() {
        given:
        def userSegment = new UserSegment(faker.lorem().word(), faker.lorem().word())
        userSegment."$propertyName" == null

        when:
        userSegment."$methodName"(property)

        then:
        userSegment."$propertyName".size() == 1
        userSegment."$propertyName".getAt(0) == property

        where:
        propertyName      | methodName               | property
        'addedUserIds'    | 'addAddedUserIdsItem'    | 1L
        'groupIds'        | 'addGroupIdsItem'        | 2L
        'orTags'          | 'addOrTagsItem'          | "or_tag1"
        'organizationIds' | 'addOrganizationIdsItem' | 3L
        'tags'            | 'addTagsItem'            | "tag1"
    }

    @Unroll
    def "add #property to #propertyName via #methodName. Property #propertyName already contains #existingProperty"() {
        given:
        def userSegment = new UserSegment(faker.lorem().word(), faker.lorem().word())
        userSegment."$propertyName" = existingProperty.clone()

        when:
        userSegment."$methodName"(property)

        then:
        userSegment."$propertyName".size() == (existingProperty.size() + 1)
        userSegment."$propertyName".containsAll(existingProperty)
        userSegment."$propertyName".contains(property)


        where:
        propertyName      | methodName               | existingProperty | property
        'addedUserIds'    | 'addAddedUserIdsItem'    | [10L]            | 1L
        'groupIds'        | 'addGroupIdsItem'        | [20L]            | 2L
        'orTags'          | 'addOrTagsItem'          | ["or_tag1"]      | "or_tag2"
        'organizationIds' | 'addOrganizationIdsItem' | [30L]            | 3L
        'tags'            | 'addTagsItem'            | ["tag1"]         | "tag2"
    }
}
