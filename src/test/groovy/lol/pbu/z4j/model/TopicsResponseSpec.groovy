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
