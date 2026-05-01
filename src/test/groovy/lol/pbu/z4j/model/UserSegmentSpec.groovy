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

