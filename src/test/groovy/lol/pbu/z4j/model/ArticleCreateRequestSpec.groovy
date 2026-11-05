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

class ArticleCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ArticleCreateRequest with canonical constructor and builder"() {
        given:
        Article expected = new Article().setTitle("Getting Started").setId(101L)

        when:
        ArticleCreateRequest direct = new ArticleCreateRequest(expected)
        ArticleCreateRequest built = ArticleCreateRequestBuilder.builder().article(expected).build()

        then:
        verifyAll(direct) {
            article() == expected
            article().id == 101L
            article().title == "Getting Started"
        }
        verifyAll(built) {
            it == direct
            article() == expected
            article().id == 101L
            article().title == "Getting Started"
        }
    }

    def "should update article using record wither"() {
        given:
        Article originalArticle = new Article().setTitle("Draft").setId(1L)
        Article newArticle = new Article().setTitle("Published").setId(2L)
        ArticleCreateRequest request = new ArticleCreateRequest(originalArticle)

        when:
        ArticleCreateRequest updated = request.withArticle(newArticle)

        then:
        verifyAll(request) {
            article() == originalArticle
            article().title == "Draft"
        }
        verifyAll(updated) {
            article() == newArticle
            article().title == "Published"
        }
    }

    def "should round-trip serialize and deserialize ArticleCreateRequest"() {
        given:
        Article expected = new Article().setTitle("User Guide").setId(555L)
        ArticleCreateRequest request = new ArticleCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleCreateRequest deserialized = objectMapper.readValue(json, ArticleCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("article")
            get("article") instanceof Map
            ((Map) get("article")).id == 555
            ((Map) get("article")).title == "User Guide"
        }
        verifyAll(deserialized) {
            it == request
            article() != null
            article().id == 555L
            article().title == "User Guide"
        }
    }

    def "should handle null article component in constructor and wither"() {
        when:
        ArticleCreateRequest request = new ArticleCreateRequest(null)
        Article replacement = new Article().setTitle("Created").setId(99L)
        ArticleCreateRequest updated = request.withArticle(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleCreateRequest deserialized = objectMapper.readValue(json, ArticleCreateRequest.class)

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
