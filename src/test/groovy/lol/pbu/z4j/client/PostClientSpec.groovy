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

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.fixture.FixtureLoader
import lol.pbu.z4j.fixture.PostFixtures
import lol.pbu.z4j.fixture.TopicFixtures
import lol.pbu.z4j.model.*
import spock.lang.Shared

@MicronautTest
class PostClientSpec extends Z4jSpec {

    @Shared
    PostClient adminPostClient, agentPostClient, userPostClient

    @Shared
    TopicClient adminTopicClient

    @Shared
    PostFixtures postFixtures

    @Shared
    Long sharedTopicId

    @Shared
    Long sharedPostId

    def setupSpec() {
        adminPostClient = adminCtx.getBean(PostClient.class)
        agentPostClient = agentCtx.getBean(PostClient.class)
        userPostClient = userCtx.getBean(PostClient.class)
        adminTopicClient = adminCtx.getBean(TopicClient.class)
        postFixtures = FixtureLoader.loadFixture("/fixtures/post_fixtures.yaml", PostFixtures.class)
        TopicFixtures topicFixtures = FixtureLoader.loadFixture("/fixtures/topic_fixtures.yaml", TopicFixtures.class)
        // Create a shared topic for post tests
        def topicFixture = topicFixtures.getTopics().first()
        def topicReq = new TopicCreateRequest().setTopic(new Topic(topicFixture.getName() + " PostClientSpec").setDescription(topicFixture.getDescription()))
        sharedTopicId = adminTopicClient.createTopic(topicReq).block().topic().getId()
        // Create a shared post for read tests
        def postFixture = postFixtures.getPosts().first()
        def postReq = new PostCreateRequest().setPost(new Post(postFixture.getTitle()).setTopicId(sharedTopicId).setDetails(postFixture.getDetails()))
        sharedPostId = adminPostClient.createPost(postReq).block().post().id
    }

    def cleanupSpec() {
        try {
            adminPostClient.deletePost(sharedPostId).block()
        } catch (Exception ignored) {}
        try {
            adminTopicClient.deleteTopic(sharedTopicId).block()
        } catch (Exception ignored) {}
    }

    def "can list posts as a #userType user"(PostClient postClient, String userType) {
        when:
        def response = postClient.listPosts().block()

        then:
        noExceptionThrown()

        and:
        response != null

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"]
        ]
    }

    def "can list posts by topic as a #userType user"(PostClient postClient, String userType) {
        when:
        def response = postClient.listPostsByTopic(sharedTopicId).block()

        then:
        noExceptionThrown()

        and:
        response != null

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"],
                [userPostClient, "user"]
        ]
    }

    def "can show a post as a #userType user"(PostClient postClient, String userType) {
        when:
        def response = postClient.showPost(sharedPostId).block()

        then:
        noExceptionThrown()

        and:
        response.post().id == sharedPostId

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"],
                [userPostClient, "user"]
        ]
    }

    def "can search posts as a #userType user"(PostClient postClient, String userType) {
        when:
        postClient.searchPosts("Test Post").block()

        then:
        noExceptionThrown()

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"],
                [userPostClient, "user"]
        ]
    }

    def "can create and delete a post as an #userType user"(PostClient postClient, String userType, String title, String details) {
        given:
        def createReq = new PostCreateRequest().setPost(new Post(title).setTopicId(sharedTopicId).setDetails(details))

        when:
        def response = postClient.createPost(createReq).block()

        then:
        noExceptionThrown()

        and:
        response.post().title == title

        cleanup:
        try {
            if (response?.post()?.id != null) {
                adminPostClient.deletePost(response.post().id).block()
            }
        } catch (Exception ignored) {}

        where:
        [[postClient, userType], [title, details]] << [
                [[adminPostClient, "admin"], [agentPostClient, "agent"]],
                postFixtures.getPosts().tail().collect { [it.getTitle(), it.getDetails()] }
        ].combinations()
    }

    def "can update a post as an #userType user"(PostClient postClient, String userType, String title, String details, String updatedTitle) {
        given:
        def createReq = new PostCreateRequest().setPost(new Post(title).setTopicId(sharedTopicId).setDetails(details))
        def created = adminPostClient.createPost(createReq).block().post()

        when:
        def updateReq = new PostUpdateRequest().setPost(new Post(updatedTitle))
        def response = postClient.updatePost(created.id, updateReq).block()

        then:
        noExceptionThrown()

        and:
        response.post().title == updatedTitle

        cleanup:
        try {
            if (created?.id != null) {
                adminPostClient.deletePost(created.id).block()
            }
        } catch (Exception ignored) {}

        where:
        [[postClient, userType], [title, details, updatedTitle]] << [
                [[adminPostClient, "admin"], [agentPostClient, "agent"]],
                postFixtures.getPosts().tail().collect { [it.getTitle(), it.getDetails(), it.getUpdatedTitle()] }
        ].combinations()
    }

    def "can create and delete a post comment as an #userType user"(PostClient postClient, String userType) {
        given:
        def createReq = new PostCommentCreateRequest().setComment(
                new PostComment("Automated test comment").setPostId(sharedPostId).setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )

        when:
        def response = postClient.createPostComment(sharedPostId, createReq).block()

        then:
        noExceptionThrown()

        and:
        response.comment().postId == sharedPostId

        cleanup:
        try {
            if (response?.comment()?.id != null) {
                adminPostClient.deletePostComment(sharedPostId, response.comment().id).block()
            }
        } catch (Exception ignored) {}

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"]
        ]
    }

    def "can list comments for a post as a #userType user"(PostClient postClient, String userType) {
        when:
        def response = postClient.listPostComments(sharedPostId).block()

        then:
        noExceptionThrown()

        and:
        response != null

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"],
                [userPostClient, "user"]
        ]
    }

    def "can show a post comment as a #userType user"(PostClient postClient, String userType) {
        given: "a comment exists"
        def createReq = new PostCommentCreateRequest().setComment(
                new PostComment("Show test comment").setPostId(sharedPostId).setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def created = adminPostClient.createPostComment(sharedPostId, createReq).block().comment()

        when:
        def response = postClient.showPostComment(sharedPostId, created.id).block()

        then:
        noExceptionThrown()

        and:
        response.comment().id == created.id

        cleanup:
        try {
            if (created?.id != null) {
                adminPostClient.deletePostComment(sharedPostId, created.id).block()
            }
        } catch (Exception ignored) {}

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"],
                [userPostClient, "user"]
        ]
    }

    def "can update a post comment as an #userType user"(PostClient postClient, String userType) {
        given:
        def createReq = new PostCommentCreateRequest().setComment(
                new PostComment("Original comment body").setPostId(sharedPostId).setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def created = adminPostClient.createPostComment(sharedPostId, createReq).block().comment()

        when:
        def updateReq = new PostCommentUpdateRequest().setComment(
                new PostComment("Updated comment body").setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def response = postClient.updatePostComment(sharedPostId, created.id, updateReq).block()

        then:
        noExceptionThrown()

        and:
        response.comment().body == "Updated comment body"

        cleanup:
        try {
            if (created?.id != null) {
                adminPostClient.deletePostComment(sharedPostId, created.id).block()
            }
        } catch (Exception ignored) {}

        where:
        [postClient, userType] << [
                [adminPostClient, "admin"],
                [agentPostClient, "agent"]
        ]
    }
}
