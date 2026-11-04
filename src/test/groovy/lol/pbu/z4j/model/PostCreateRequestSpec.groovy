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

class PostCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PostCreateRequest with canonical constructor and builder"() {
        given:
        Post expected = new Post("Community Announcement").withDetails("Exciting product updates").withId(101L)

        when:
        PostCreateRequest direct = new PostCreateRequest(expected)
        PostCreateRequest built = PostCreateRequestBuilder.builder().post(expected).build()

        then:
        verifyAll(direct) {
            post() == expected
            post().id == 101L
            post().title == "Community Announcement"
            post().details == "Exciting product updates"
        }
        verifyAll(built) {
            it == direct
            post() == expected
            post().id == 101L
            post().title == "Community Announcement"
            post().details == "Exciting product updates"
        }
    }

    def "should update post using record wither"() {
        given:
        Post original = new Post("Old Title").withId(1L)
        Post updatedPost = new Post("New Title").withId(2L)
        PostCreateRequest request = new PostCreateRequest(original)

        when:
        PostCreateRequest updated = request.withPost(updatedPost)

        then:
        verifyAll(request) {
            post() == original
            post().title == "Old Title"
        }
        verifyAll(updated) {
            post() == updatedPost
            post().title == "New Title"
        }
    }

    def "should round-trip serialize and deserialize PostCreateRequest"() {
        given:
        Post expected = new Post("Release Notes").withDetails("Version 1.0 is live").withId(555L)
        PostCreateRequest request = new PostCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        PostCreateRequest deserialized = objectMapper.readValue(json, PostCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("post")
            get("post") instanceof Map
            ((Map) get("post")).id == 555
            ((Map) get("post")).title == "Release Notes"
        }
        verifyAll(deserialized) {
            it == request
            post() != null
            post().id == 555L
            post().title == "Release Notes"
            post().details == "Version 1.0 is live"
        }
    }

    def "should handle null post component in constructor and wither"() {
        when:
        PostCreateRequest request = new PostCreateRequest(null)
        Post replacement = new Post("Maintenance Notice").withId(99L)
        PostCreateRequest updated = request.withPost(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        PostCreateRequest deserialized = objectMapper.readValue(json, PostCreateRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            post() == null
        }
    }
}
