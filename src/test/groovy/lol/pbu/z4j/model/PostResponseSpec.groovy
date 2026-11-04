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

class PostResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PostResponse with canonical constructor and builder"() {
        given:
        Post expected = new Post("Community Announcement").withDetails("Exciting product updates").withId(101L)

        when:
        PostResponse direct = new PostResponse(expected)
        PostResponse built = PostResponseBuilder.builder().post(expected).build()

        then:
        verifyAll(direct) {
            post == expected
            post.id == 101L
            post.title == "Community Announcement"
            post.details == "Exciting product updates"
        }
        verifyAll(built) {
            post == expected
            post.id == 101L
            post.title == "Community Announcement"
            post.details == "Exciting product updates"
        }
    }

    def "should update post using record wither"() {
        given:
        Post original = new Post("Old Title").withId(1L)
        Post updatedPost = new Post("New Title").withId(2L)
        PostResponse response = new PostResponse(original)

        when:
        PostResponse updated = response.withPost(updatedPost)

        then:
        verifyAll(response) {
            post == original
            post.title == "Old Title"
        }
        verifyAll(updated) {
            post == updatedPost
            post.title == "New Title"
        }
    }

    def "should round-trip serialize and deserialize PostResponse"() {
        given:
        Post expected = new Post("Release Notes").withDetails("Version 1.0 is live").withId(555L)
        PostResponse response = new PostResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostResponse deserialized = objectMapper.readValue(json, PostResponse.class)

        then:
        verifyAll(map) {
            containsKey("post")
            get("post") instanceof Map
            ((Map) get("post")).id == 555
            ((Map) get("post")).title == "Release Notes"
        }
        verifyAll(deserialized) {
            it == response
            post != null
            post.id == 555L
            post.title == "Release Notes"
            post.details == "Version 1.0 is live"
        }
    }

    def "should handle null post component in constructor and wither"() {
        when:
        PostResponse response = new PostResponse(null)
        Post replacement = new Post("Maintenance Notice").withId(99L)
        PostResponse updated = response.withPost(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostResponse deserialized = objectMapper.readValue(json, PostResponse.class)

        then:
        verifyAll(response) {
            post() == null
        }
        verifyAll(updated) {
            post() == replacement
            post().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("post")
        }
        verifyAll(deserialized) {
            it == response
            post() == null
        }
    }
}
