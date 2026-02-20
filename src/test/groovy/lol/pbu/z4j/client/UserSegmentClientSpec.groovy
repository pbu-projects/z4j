package lol.pbu.z4j.client

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CreateUserSegmentRequest
import lol.pbu.z4j.model.SectionsResponse
import lol.pbu.z4j.model.TopicsResponse
import lol.pbu.z4j.model.UserSegment
import lol.pbu.z4j.model.UserSegmentResponse
import lol.pbu.z4j.model.UserSegmentsResponse
import reactor.core.publisher.Mono
import spock.lang.Shared

@MicronautTest
class UserSegmentClientSpec extends Z4jSpec {

    //TODO: usertype needs to be an enum, not a string (it can only be "signed_in_users" or "staff")

    /**
     * NOTE: there're two an undocumented defects in zendesk's documented api vs their actual behavior.
     *
     * - the user_segment object requires an ID and a name, whereas their docs say the ID is all that's required.
     * - the update user segment endpoint requires a user segment object (the createUserSegment Object can be used)*/

    @Shared
    UserSegmentClient userSegmentClient

    def setupSpec() {
        userSegmentClient = adminCtx.getBean(UserSegmentClient.class)
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
        userType          | segmentName          | _
        "signed_in_users" | faker.cat().name()   | _
        "staff"           | faker.movie().name() | _
    }

    def "can delete user segment with '#segmentName' name"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #name"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "delete user segment from previous step"
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        userType          | segmentName          | _
        "signed_in_users" | faker.cat().name()   | _
        "staff"           | faker.movie().name() | _
    }

    def "can list user segment sections"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #name"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "list sections with user segment from previous step"
        userSegmentClient.listUserSegmentSections(userSegment.getId()).block()

        then:
        noExceptionThrown()

        where:
        userType          | segmentName          | _
        "signed_in_users" | faker.cat().name()   | _
        "staff"           | faker.movie().name() | _
    }

    def "can list user segment topics"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #name"
        UserSegment userSegment = createUserSegment(userType, segmentName)

        when: "list topics with user segment from previous step"
        userSegmentClient.listUserSegmentTopics(userSegment.getId()).block()

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        userType          | segmentName          | _
        "signed_in_users" | faker.cat().name()   | _
        "staff"           | faker.movie().name() | _
    }

    def "can show user segments allowed for '#userType' user type"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #name"
        def userSegment = createUserSegment(userType, segmentName)

        when: "show user segment with user segment ID from previous step"
        UserSegmentResponse response = userSegmentClient.showUserSegment(userSegment.getId()).block()

        and: "query resultant userSegments"
        response.getUserSegment() // don't test more than this api client. testing Zendesk's behavior is out of scope

        then:
        noExceptionThrown()

        cleanup:
        userSegmentClient.deleteUserSegment(userSegment.getId()).block()

        where:
        userType          | segmentName          | _
        "signed_in_users" | faker.cat().name()   | _
        "staff"           | faker.movie().name() | _
    }

    def "can update user segment"(String userType, String segmentName) {
        given: "create user segment on server with #userType and #name"
        def userSegment = createUserSegment(userType, segmentName)

        when: "create an update to the user segment object with updated name and opposite user type"
        userSegment.setName(faker.studioGhibli().movie())
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
        userType          | segmentName          | _
        "signed_in_users" | faker.cat().name()   | _
        "staff"           | faker.movie().name() | _
    }


    /**
     * creates a user segment from the given user type and name
     * @param userType either "signed_in_users" or "staff"
     * @param name the name of the user segment
     * @return the ID of the created user segment
     */
    UserSegment createUserSegment(String userType, String name) {
        return userSegmentClient.createUserSegment(new CreateUserSegmentRequest(new UserSegment(name, userType))).block().getUserSegment()
    }
}