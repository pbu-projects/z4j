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

import io.micronaut.serde.ObjectMapper
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class PostsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PostsResponse with canonical constructor and builder"() {
        given:
        Post item = new Post("Community Announcement").setId(101L)
        List<Post> list = [item]

        when:
        PostsResponse direct = new PostsResponse(list)
        PostsResponse built = PostsResponseBuilder.builder()
                .posts(list)
                .build()

        then:
        verifyAll(direct) {
            posts() == list
            posts().size() == 1
            posts().first().id == 101L
            posts().first().title == "Community Announcement"
        }
        verifyAll(built) {
            posts() == list
            posts().size() == 1
            posts().first().id == 101L
            posts().first().title == "Community Announcement"
        }
    }

    def "should update posts using record wither and addPostsItem method"() {
        given:
        Post first = new Post("Old Post").setId(1L)
        Post second = new Post("New Post").setId(2L)
        PostsResponse response = new PostsResponse([first])

        when:
        PostsResponse withNewList = response.withPosts([second])
        PostsResponse withAdded = response.addPostsItem(second)
        PostsResponse fromNull = new PostsResponse(null).addPostsItem(first)

        then:
        verifyAll(response) {
            posts().size() == 1
            posts().first().id == 1L
        }
        verifyAll(withNewList) {
            posts().size() == 1
            posts().first().id == 2L
        }
        verifyAll(withAdded) {
            posts().size() == 2
            posts().first().id == 1L
            posts().last().id == 2L
        }
        verifyAll(fromNull) {
            posts().size() == 1
            posts().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize PostsResponse"() {
        given:
        Post item = new Post("Release Notes").setDetails("Version 1.0 is live").setId(555L)
        PostsResponse response = new PostsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostsResponse deserialized = objectMapper.readValue(json, PostsResponse.class)

        then:
        verifyAll(map) {
            containsKey("posts")
            get("posts") instanceof List
            ((List) get("posts")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            posts() != null
            posts().size() == 1
            posts().first().id == 555L
            posts().first().title == "Release Notes"
        }
    }

    def "should handle null posts component in constructor, wither, and serde"() {
        when:
        PostsResponse response = new PostsResponse(null)
        Post item = new Post("Maintenance Notice").setId(99L)
        PostsResponse updated = response.withPosts([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostsResponse deserialized = objectMapper.readValue(json, PostsResponse.class)

        then:
        verifyAll(response) {
            posts() == null
        }
        verifyAll(updated) {
            posts() != null
            posts().size() == 1
            posts().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("posts")
        }
        verifyAll(deserialized) {
            it == response
            posts() == null
        }
    }
}
