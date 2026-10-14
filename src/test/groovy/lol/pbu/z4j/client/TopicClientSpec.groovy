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

import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.fixture.FixtureLoader
import lol.pbu.z4j.fixture.TopicFixtures
import lol.pbu.z4j.model.*
import spock.lang.Shared

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TopicClientSpec extends Z4jSpec {

    @Shared
    TopicClient adminTopicClient, agentTopicClient, userTopicClient

    @Shared
    TopicFixtures topicFixtures

    @Shared
    Long sharedTopicId

    def setupSpec() {
        adminTopicClient = adminCtx.getBean(TopicClient.class)
        agentTopicClient = agentCtx.getBean(TopicClient.class)
        userTopicClient = userCtx.getBean(TopicClient.class)
        topicFixtures = FixtureLoader.loadFixture("/fixtures/topic_fixtures.yaml", TopicFixtures.class)
        // Create a shared topic for read tests
        def fixture = topicFixtures.getTopics().first()
        def createReq = new TopicCreateRequest().setTopic(new Topic(fixture.getName()).setDescription(fixture.getDescription()))
        sharedTopicId = adminTopicClient.createTopic(createReq).block().getTopic().getId()
    }

    def cleanupSpec() {
        try {
            adminTopicClient.deleteTopic(sharedTopicId).block()
        } catch (Exception ignored) {}
    }

    def "can list topics as a #userType user"(TopicClient topicClient, String userType) {
        when:
        def response = topicClient.listTopics().block()

        then:
        noExceptionThrown()

        and:
        response != null

        where:
        [topicClient, userType] << [
                [adminTopicClient, "admin"],
                [agentTopicClient, "agent"],
                [userTopicClient, "user"]
        ]
    }

    def "can show a topic as a #userType user"(TopicClient topicClient, String userType) {
        when:
        def response = topicClient.showTopic(sharedTopicId).block()

        then:
        noExceptionThrown()

        and:
        response.getTopic().getId() == sharedTopicId

        where:
        [topicClient, userType] << [
                [adminTopicClient, "admin"],
                [agentTopicClient, "agent"],
                [userTopicClient, "user"]
        ]
    }

    def "can create a topic as an admin"(String name, String description) {
        given:
        def createReq = new TopicCreateRequest().setTopic(new Topic(name).setDescription(description))

        when:
        def response = adminTopicClient.createTopic(createReq).block()

        then:
        noExceptionThrown()

        and:
        response.getTopic().getName() == name

        cleanup:
        try {
            adminTopicClient.deleteTopic(response.getTopic().getId()).block()
        } catch (Exception ignored) {}

        where:
        [name, description] << topicFixtures.getTopics().tail().collect { [it.getName(), it.getDescription()] }
    }

    def "cannot create a topic as a #userType user"(TopicClient topicClient, String userType) {
        given:
        def createReq = new TopicCreateRequest().setTopic(new Topic("Unauthorized Topic").setDescription("Should not be created"))

        when:
        topicClient.createTopic(createReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)

        and:
        error.getStatus() == FORBIDDEN

        where:
        [topicClient, userType] << [
                [agentTopicClient, "agent"],
                [userTopicClient, "user"]
        ]
    }

    def "can update a topic as an admin"(String name, String description, String updatedName) {
        given:
        def createReq = new TopicCreateRequest().setTopic(new Topic(name).setDescription(description))
        def created = adminTopicClient.createTopic(createReq).block().getTopic()

        when:
        def updateReq = new TopicUpdateRequest().setTopic(new Topic(updatedName))
        def response = adminTopicClient.updateTopic(created.getId(), updateReq).block()

        then:
        noExceptionThrown()

        and:
        response.getTopic().getName() == updatedName

        cleanup:
        try {
            adminTopicClient.deleteTopic(created.getId()).block()
        } catch (Exception ignored) {}

        where:
        [name, description, updatedName] << topicFixtures.getTopics().tail().collect { [it.getName(), it.getDescription(), it.getUpdatedName()] }
    }

    def "cannot update a topic as a #userType user"(TopicClient topicClient, String userType) {
        given:
        def updateReq = new TopicUpdateRequest().setTopic(new Topic("Unauthorized Update"))

        when:
        topicClient.updateTopic(sharedTopicId, updateReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)

        and:
        error.getStatus() == FORBIDDEN

        where:
        [topicClient, userType] << [
                [agentTopicClient, "agent"],
                [userTopicClient, "user"]
        ]
    }

    def "can delete a topic as an admin"(String name, String description) {
        given:
        def createReq = new TopicCreateRequest().setTopic(new Topic(name).setDescription(description))
        def created = adminTopicClient.createTopic(createReq).block().getTopic()

        when:
        adminTopicClient.deleteTopic(created.getId()).block()

        then:
        noExceptionThrown()

        where:
        [name, description] << topicFixtures.getTopics().tail().collect { [it.getName(), it.getDescription()] }
    }

    def "cannot delete a topic as a #userType user"(TopicClient topicClient, String userType) {
        when:
        topicClient.deleteTopic(sharedTopicId).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)

        and:
        error.getStatus() == FORBIDDEN

        where:
        [topicClient, userType] << [
                [agentTopicClient, "agent"],
                [userTopicClient, "user"]
        ]
    }
}
