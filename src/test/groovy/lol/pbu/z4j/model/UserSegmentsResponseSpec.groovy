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

class UserSegmentsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add user segments item"() {
        given:
        def userSegmentsResponse = new UserSegmentsResponse()
        userSegmentsResponse.userSegments == null
        def userSegment = new UserSegment(faker.lorem().word(), faker.lorem().word())

        when:
        userSegmentsResponse.addUserSegmentsItem(userSegment)

        then:
        userSegmentsResponse.userSegments.size() == 1
        userSegmentsResponse.userSegments.getAt(0) == userSegment
    }

    @Unroll
    def "add user segments item to existing list"() {
        given:
        def existingUserSegment = new UserSegment(faker.lorem().word(), faker.lorem().word())
        def userSegmentsResponse = new UserSegmentsResponse(userSegments: [existingUserSegment])
        def newUserSegment = new UserSegment(faker.lorem().word(), faker.lorem().word())

        when:
        userSegmentsResponse.addUserSegmentsItem(newUserSegment)

        then:
        userSegmentsResponse.userSegments.size() == 2
        userSegmentsResponse.userSegments.containsAll([existingUserSegment, newUserSegment])
    }
}

