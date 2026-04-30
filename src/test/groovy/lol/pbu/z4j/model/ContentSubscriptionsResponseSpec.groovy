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

