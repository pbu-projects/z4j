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
package lol.pbu.z4j.client
import org.junit.jupiter.api.Tag

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.fixture.FixtureLoader
import lol.pbu.z4j.fixture.UserSegmentFixtures
import lol.pbu.z4j.model.CreateUserSegmentRequest
import lol.pbu.z4j.model.UserSegment
import lol.pbu.z4j.model.UserSegmentResponse
import lol.pbu.z4j.model.UserSegmentsResponse
import reactor.core.publisher.Mono
import spock.lang.Shared

@MicronautTest
@Tag("users")
class UserSegmentClientSpec extends Z4jSpec {

    @Shared
    UserSegmentClient userSegmentClient

    @Shared
    UserSegmentFixtures userSegmentFixtures

    def setupSpec() {
        userSegmentClient = adminCtx.getBean(UserSegmentClient.class)
        userSegmentFixtures = FixtureLoader.loadFixture("/fixtures/user_segment_fixtures.yaml", UserSegmentFixtures.class)
    }

    def "can list user segments"() {
        when: "list user segments with query value '#segmentQuery'"
        Mono<UserSegmentsResponse> response = userSegmentClient.listUserSegments(segmentQuery)

        and: "reading response causes no error"
        response.block().getUserSegments()

        then:
        noExceptionThrown()

        where:
        segmentQuery | _
        null         | _
        true         | _
        false        | _
    }

    def "can create user segment named '#segmentName' with '#userType' user type"(String userType, String segmentName) {
        when:
        UserSegment userSegment = userSegmentClient.createUserSegment(
                new CreateUserSegmentRequest(new UserSegment(segmentName, userType))).block().getUserSegment()

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can delete user segment with '#segmentName' name"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "delete user segment from previous step"
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can list user segment sections"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "list sections with user segment from previous step"
        userSegmentClient.listUserSegmentSections(userSegment.getId()).block()

        then:
        noExceptionThrown()

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can list user segment topics"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "list topics with user segment from previous step"
        userSegmentClient.listUserSegmentTopics(userSegment.getId()).block()

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can show user segments allowed for '#userType' user type"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        def userSegment = createUserSegment(userType, segmentName)

        when: "show user segment with user segment ID from previous step"
        UserSegmentResponse response = userSegmentClient.showUserSegment(userSegment.getId()).block()

        and: "query resultant userSegments"
        response.getUserSegment()

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can update user segment"(String userType, String segmentName, String updatedName) {
        given: "create user segment on server with #userType and #segmentName"
        def userSegment = createUserSegment(userType, segmentName)

        when: "create an update to the user segment object with updated name and opposite user type"
        userSegment.setName(updatedName)
        if (userType.equalsIgnoreCase("staff")) {
            userSegment.setUserType("signed_in_users")
        } else {
            userSegment.setUserType("staff")
        }

        and: "send the update to zendesk"
        userSegmentClient.updateUserSegment(userSegment.getId(),
                new CreateUserSegmentRequest(userSegment)).block()

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        [userType, segmentName, updatedName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName(), it.getUpdatedName()] }
    }

    UserSegment createUserSegment(String userType, String name) {
        return userSegmentClient.createUserSegment(new CreateUserSegmentRequest(new UserSegment(name, userType))).block().getUserSegment()
    }
}


