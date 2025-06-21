package lol.pbu.z4j.client

import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import lol.pbu.z4j.model.CreateUserSegmentRequest
import lol.pbu.z4j.model.UserSegment
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class UserSegmentsClientSpec extends Specification {

    /**
     * NOTE: there're two an undocumented defects in zendesk's documented api vs their actual behavior.
     *
     * - the user_segment object requires an ID and a name, whereas their docs say the ID is all that's required.
     * - the update user segment endpoint requires a user segment object (the createUserSegment Object can be used)
     */

    @Inject
    @Shared
    UserSegmentsClient userSegmentsClient


    def "can list user segments"() {
        when: "list user segments with query value '#segmentQuery'"
        def response = userSegmentsClient.listUserSegments(segmentQuery)

        then: "received expected 200 response"
        response.status() == HttpStatus.OK

        where:
        segmentQuery | _
        null         | _
        true         | _
        false        | _
    }

    def "can create user segment with '#userSegment' user type"() {
        when:
        def response = userSegmentsClient.createUserSegment(new CreateUserSegmentRequest(
                new UserSegment(userSegment, userSegment)))

        then: "received expected 201 response"
        response.status() == HttpStatus.CREATED

        where:
        userSegment       | _
        "signed_in_users" | _
        "staff"           | _
    }

    def "can delete user segment with '#segmentName' name"() {
        given: "create user segment on server with #userType and #name"
        def userSegment = createUserSegment(userType, segmentName)

        when:"delete user segment from previous step"
        def response = userSegmentsClient.deleteUserSegment(userSegment.getId())

        then:"received expected 204 response"
        response.status() == HttpStatus.NO_CONTENT

        where:
        userType          | segmentName            | _
        "signed_in_users" | "signed_in_users name" | _
        "staff"           | "staff name"           | _
    }

    def "can list user segment sections"() {
        given: "create user segment on server with #userType and #name"
        def userSegment = createUserSegment(userType, segmentName)

        when:"list sections with user segment from previous step"
        def response = userSegmentsClient.listUserSegmentSections(userSegment.getId())

        then: "received expected 200 response"
        response.status() == HttpStatus.OK

        where:
        userType          | segmentName            | _
        "signed_in_users" | "signed_in_users name" | _
        "staff"           | "staff name"           | _
    }

    def "can list user segment topics"() {
        given: "create user segment on server with #userType and #name"
        def userSegment = createUserSegment(userType, segmentName)

        when:"list topics with user segment from previous step"
        def response = userSegmentsClient.listUserSegmentTopics(userSegment.getId())

        then: "received expected 200 response"
        response.status() == HttpStatus.OK

        where:
        userType          | segmentName            | _
        "signed_in_users" | "signed_in_users name" | _
        "staff"           | "staff name"           | _
    }

    def "can show user segment"() {
        given: "create user segment on server with #userType and #name"
        def userSegment = createUserSegment(userType, segmentName)

        when:"show user segment with user segment ID from previous step"
        def response = userSegmentsClient.showUserSegment(userSegment.getId())

        then: "received expected 200 response"
        response.status() == HttpStatus.OK

        where:
        userType          | segmentName            | _
        "signed_in_users" | "signed_in_users name" | _
        "staff"           | "staff name"           | _
    }

    def "can update user segment"() {
        given: "create user segment on server with #userType and #name"
        def userSegment = createUserSegment(userType, segmentName)

        and: "create local iteration of user segment object with updated name"
        userSegment.setName("this is a new name")

        when:"show update segment with user segment ID from previous step"
        def response = userSegmentsClient.updateUserSegment(
                userSegment.getId(),
                new CreateUserSegmentRequest(userSegment))

        then: "received expected 200 response"
        response.status() == HttpStatus.OK

        where:
        userType          | segmentName            | _
        "signed_in_users" | "signed_in_users name" | _
        "staff"           | "staff name"           | _
    }


    /**
     * creates a user segment from the given user type and name
     * @param userType either "signed_in_users" or "staff"
     * @param name the name of the user segment
     * @return the ID of the created user segment
     */
    UserSegment createUserSegment(String userType, String name) {
        return userSegmentsClient.createUserSegment(new CreateUserSegmentRequest(
                new UserSegment(name, userType))).body().getUserSegment()
    }
}