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

class ArticleUpdateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ArticleUpdateRequest with canonical constructor and builder"() {
        given:
        Article expected = new Article().withTitle("Updated Guide").withId(102L)

        when:
        ArticleUpdateRequest direct = new ArticleUpdateRequest(expected)
        ArticleUpdateRequest built = ArticleUpdateRequestBuilder.builder().article(expected).build()

        then:
        verifyAll(direct) {
            article() == expected
            article().id == 102L
            article().title == "Updated Guide"
        }
        verifyAll(built) {
            it == direct
            article() == expected
            article().id == 102L
            article().title == "Updated Guide"
        }
    }

    def "should update article using record wither"() {
        given:
        Article originalArticle = new Article().withTitle("Old Title").withId(1L)
        Article newArticle = new Article().withTitle("New Title").withId(2L)
        ArticleUpdateRequest request = new ArticleUpdateRequest(originalArticle)

        when:
        ArticleUpdateRequest updated = request.withArticle(newArticle)

        then:
        verifyAll(request) {
            article() == originalArticle
            article().title == "Old Title"
        }
        verifyAll(updated) {
            article() == newArticle
            article().title == "New Title"
        }
    }

    def "should round-trip serialize and deserialize ArticleUpdateRequest"() {
        given:
        Article expected = new Article().withTitle("Revised Guide").withId(556L)
        ArticleUpdateRequest request = new ArticleUpdateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleUpdateRequest deserialized = objectMapper.readValue(json, ArticleUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("article")
            get("article") instanceof Map
            ((Map) get("article")).id == 556
            ((Map) get("article")).title == "Revised Guide"
        }
        verifyAll(deserialized) {
            it == request
            article() != null
            article().id == 556L
            article().title == "Revised Guide"
        }
    }

    def "should handle null article component in constructor and wither"() {
        when:
        ArticleUpdateRequest request = new ArticleUpdateRequest(null)
        Article replacement = new Article().withTitle("Restored").withId(99L)
        ArticleUpdateRequest updated = request.withArticle(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleUpdateRequest deserialized = objectMapper.readValue(json, ArticleUpdateRequest.class)

        then:
        verifyAll(request) {
            article() == null
        }
        verifyAll(updated) {
            article() == replacement
            article().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("article")
        }
        verifyAll(deserialized) {
            it == request
            article() == null
        }
    }
}
