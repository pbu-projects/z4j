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

class TopicsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add topics item"() {
        given:
        def topicsResponse = new TopicsResponse()
        topicsResponse.topics == null
        def topic = new Topic(faker.lorem().word())

        when:
        topicsResponse.addTopicsItem(topic)

        then:
        topicsResponse.topics.size() == 1
        topicsResponse.topics.getAt(0) == topic
    }

    @Unroll
    def "add topics item to existing list"() {
        given:
        def existingTopic = new Topic(faker.lorem().word())
        def topicsResponse = new TopicsResponse(topics: [existingTopic])
        def newTopic = new Topic(faker.lorem().word())

        when:
        topicsResponse.addTopicsItem(newTopic)

        then:
        topicsResponse.topics.size() == 2
        topicsResponse.topics.containsAll([existingTopic, newTopic])
    }
}

