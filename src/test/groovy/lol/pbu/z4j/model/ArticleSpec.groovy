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

