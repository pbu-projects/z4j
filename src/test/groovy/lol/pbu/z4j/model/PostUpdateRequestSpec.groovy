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

class PostUpdateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PostUpdateRequest with canonical constructor and builder"() {
        given:
        Post expected = new Post("Updated Announcement").setDetails("More product updates").setId(101L)

        when:
        PostUpdateRequest direct = new PostUpdateRequest(expected)
        PostUpdateRequest built = PostUpdateRequestBuilder.builder().post(expected).build()

        then:
        verifyAll(direct) {
            post() == expected
            post().id == 101L
            post().title == "Updated Announcement"
            post().details == "More product updates"
        }
        verifyAll(built) {
            it == direct
            post() == expected
            post().id == 101L
            post().title == "Updated Announcement"
            post().details == "More product updates"
        }
    }

    def "should update post using record wither"() {
        given:
        Post original = new Post("Old Title").setId(1L)
        Post updatedPost = new Post("New Title").setId(2L)
        PostUpdateRequest request = new PostUpdateRequest(original)

        when:
        PostUpdateRequest updated = request.withPost(updatedPost)

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

    def "should round-trip serialize and deserialize PostUpdateRequest"() {
        given:
        Post expected = new Post("Release Notes v2").setDetails("Version 2.0 is live").setId(555L)
        PostUpdateRequest request = new PostUpdateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        PostUpdateRequest deserialized = objectMapper.readValue(json, PostUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("post")
            get("post") instanceof Map
            ((Map) get("post")).id == 555
            ((Map) get("post")).title == "Release Notes v2"
        }
        verifyAll(deserialized) {
            it == request
            post() != null
            post().id == 555L
            post().title == "Release Notes v2"
            post().details == "Version 2.0 is live"
        }
    }

    def "should handle null post component in constructor and wither"() {
        when:
        PostUpdateRequest request = new PostUpdateRequest(null)
        Post replacement = new Post("Maintenance Notice").setId(99L)
        PostUpdateRequest updated = request.withPost(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        PostUpdateRequest deserialized = objectMapper.readValue(json, PostUpdateRequest.class)

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
