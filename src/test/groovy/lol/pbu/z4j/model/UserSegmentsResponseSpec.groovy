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
