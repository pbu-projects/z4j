package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ArticleSpec extends Z4jSpec {

    @Unroll
    def "should add #propertyName via #methodName and assign it the value of #property"() {
        given:
        def article = new Article(faker.locality().toString(),
                faker.number().randomNumber(),
                faker.book().title())
        article."$propertyName" == null

        when:
        article."$methodName"(property)

        then:
        article."$propertyName".size() == 1
        article."$propertyName".getAt(0) == property

        where:
        propertyName     | methodName              | property
        'contentTagIds'  | 'addContentTagIdsItem'  | "tag1"
        'labelNames'     | 'addLabelNamesItem'     | "label1"
        'userSegmentIds' | 'addUserSegmentIdsItem' | "segment1"
    }

    @Unroll
    def "add #property to #propertyName via #methodName. Property #propertyName already contains #existingProperty"() {
        given:
        def article = new Article(faker.locality().toString(),
                faker.number().randomNumber(),
                faker.book().title())
        article."$propertyName" = existingProperty.clone()

        when:
        article."$methodName"(property)

        then:
        article."$propertyName".size() == (existingProperty.size() + 1)
        article."$propertyName".containsAll(existingProperty)
        article."$propertyName".contains(property)


        where:
        propertyName     | methodName              | existingProperty | property
        'contentTagIds'  | 'addContentTagIdsItem'  | ["tag1"]         | "tag2"
        'labelNames'     | 'addLabelNamesItem'     | ["label1"]       | "label2"
        'userSegmentIds' | 'addUserSegmentIdsItem' | ["segment1"]     | "segment2"
    }
}
