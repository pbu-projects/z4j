package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class UserSubscriptionsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add user subscriptions item"() {
        given:
        def userSubscriptionsResponse = new UserSubscriptionsResponse()
        userSubscriptionsResponse.userSubscriptions == null
        def userSubscription = new UserSubscription()

        when:
        userSubscriptionsResponse.addUserSubscriptionsItem(userSubscription)

        then:
        userSubscriptionsResponse.userSubscriptions.size() == 1
        userSubscriptionsResponse.userSubscriptions.getAt(0) == userSubscription
    }

    @Unroll
    def "add user subscriptions item to existing list"() {
        given:
        def existingUserSubscription = new UserSubscription()
        def userSubscriptionsResponse = new UserSubscriptionsResponse(userSubscriptions: [existingUserSubscription])
        def newUserSubscription = new UserSubscription()

        when:
        userSubscriptionsResponse.addUserSubscriptionsItem(newUserSubscription)

        then:
        userSubscriptionsResponse.userSubscriptions.size() == 2
        userSubscriptionsResponse.userSubscriptions.containsAll([existingUserSubscription, newUserSubscription])
    }
}
