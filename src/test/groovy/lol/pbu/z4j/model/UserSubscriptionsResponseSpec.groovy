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

