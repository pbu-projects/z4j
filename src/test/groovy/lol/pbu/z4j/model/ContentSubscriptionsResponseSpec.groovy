package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ContentSubscriptionsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add subscriptions item"() {
        given:
        def contentSubscriptionsResponse = new ContentSubscriptionsResponse()
        contentSubscriptionsResponse.subscriptions == null
        def contentSubscription = new ContentSubscription()

        when:
        contentSubscriptionsResponse.addSubscriptionsItem(contentSubscription)

        then:
        contentSubscriptionsResponse.subscriptions.size() == 1
        contentSubscriptionsResponse.subscriptions.getAt(0) == contentSubscription
    }

    @Unroll
    def "add subscriptions item to existing list"() {
        given:
        def existingContentSubscription = new ContentSubscription()
        def contentSubscriptionsResponse = new ContentSubscriptionsResponse(subscriptions: [existingContentSubscription])
        def newContentSubscription = new ContentSubscription()

        when:
        contentSubscriptionsResponse.addSubscriptionsItem(newContentSubscription)

        then:
        contentSubscriptionsResponse.subscriptions.size() == 2
        contentSubscriptionsResponse.subscriptions.containsAll([existingContentSubscription, newContentSubscription])
    }
}
