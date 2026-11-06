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

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import java.util.Set
import java.util.concurrent.ConcurrentHashMap
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
class UserSegmentClientSpec extends Z4jSpec {

    @Shared
    UserSegmentClient userSegmentClient

    @Shared
    UserSegmentFixtures userSegmentFixtures

    @Shared
    Set<Long> createdSegmentIds = ConcurrentHashMap.newKeySet()

    @Shared
    Set<String> testSegmentNames = ConcurrentHashMap.newKeySet()

    def setupSpec() {
        userSegmentClient = adminCtx.getBean(UserSegmentClient.class)
        userSegmentFixtures = FixtureLoader.loadFixture("/fixtures/user_segment_fixtures.yaml", UserSegmentFixtures.class)
        testSegmentNames.addAll(userSegmentFixtures.getUserSegments().collect { it.getSegmentName() })
        testSegmentNames.addAll(userSegmentFixtures.getUserSegments().collect { it.getUpdatedName() })
        pruneTestSegments()
    }

    def cleanupSpec() {
        createdSegmentIds.each { id ->
            safeDelete(id)
        }
        pruneTestSegments()
    }

    def "can list user segments"() {
        when: "list user segments with query value '#segmentQuery'"
        Mono<UserSegmentsResponse> response = userSegmentClient.listUserSegments(segmentQuery)

        and: "reading response causes no error"
        response.block().userSegments()

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
        UserSegment userSegment = createUserSegment(userType, segmentName)

        then:
        noExceptionThrown()
        userSegment != null
        verifyAll(userSegment) {
            it.id() != null
            it.name() == segmentName
            it.userType() == userType
        }

        cleanup:
        safeDelete(userSegment?.id())

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can delete user segment with '#segmentName' name"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "delete user segment from previous step"
        userSegmentClient.deleteUserSegment(userSegment.id()).block()

        then:
        noExceptionThrown()

        cleanup:
        safeDelete(userSegment?.id())

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can list user segment sections"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "list sections with user segment from previous step"
        userSegmentClient.listUserSegmentSections(userSegment.id()).block()

        then:
        noExceptionThrown()

        cleanup:
        safeDelete(userSegment?.id())

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can list user segment topics"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "list topics with user segment from previous step"
        userSegmentClient.listUserSegmentTopics(userSegment.id()).block()

        then:
        noExceptionThrown()

        cleanup:
        safeDelete(userSegment?.id())

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can show user segments allowed for '#userType' user type"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "show user segment with user segment ID from previous step"
        UserSegmentResponse response = userSegmentClient.showUserSegment(userSegment.id()).block()

        then:
        noExceptionThrown()
        response != null
        verifyAll(response) {
            it.userSegment() != null
        }

        cleanup:
        safeDelete(userSegment?.id())

        where:
        [userType, segmentName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName()] }
    }

    def "can update user segment"(String userType, String segmentName, String updatedName) {
        given: "create user segment on server with #userType and #segmentName"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "create an update to the user segment object with updated name and opposite user type"
        UserSegment updated = userSegment.withName(updatedName)
        if (userType.equalsIgnoreCase("staff")) {
            updated = updated.withUserType("signed_in_users")
        } else {
            updated = updated.withUserType("staff")
        }

        and: "send the update to zendesk"
        userSegmentClient.updateUserSegment(userSegment.id(),
                new CreateUserSegmentRequest(updated)).block()

        then:
        noExceptionThrown()

        cleanup:
        safeDelete(userSegment?.id())

        where:
        [userType, segmentName, updatedName] << userSegmentFixtures.getUserSegments().collect { [it.getUserType(), it.getSegmentName(), it.getUpdatedName()] }
    }

    UserSegment createUserSegment(String userType, String name) {
        UserSegment segment = userSegmentClient.createUserSegment(
                new CreateUserSegmentRequest(new UserSegment(name, userType))).block()?.userSegment()
        if (segment?.id() != null) {
            createdSegmentIds.add(segment.id())
        }
        return segment
    }

    void safeDelete(Long id) {
        if (id == null) {
            return
        }
        try {
            userSegmentClient.deleteUserSegment(id).block()
            createdSegmentIds.remove(id)
        } catch (HttpClientResponseException e) {
            if (e.status == HttpStatus.NOT_FOUND) {
                createdSegmentIds.remove(id)
            }
        } catch (Exception ignored) {
        }
    }

    void pruneTestSegments() {
        try {
            userSegmentClient.listUserSegments(false).block()?.userSegments()?.each { segment ->
                if (segment.name() in testSegmentNames) {
                    safeDelete(segment.id())
                }
            }
        } catch (Exception ignored) {
        }
    }
}
