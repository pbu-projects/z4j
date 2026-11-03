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

class ArticleResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ArticleResponse with canonical constructor and builder"() {
        given:
        Article expected = new Article().setTitle("Getting Started").setId(101L)

        when:
        ArticleResponse direct = new ArticleResponse(expected)
        ArticleResponse built = ArticleResponseBuilder.builder().article(expected).build()

        then:
        verifyAll(direct) {
            article == expected
            article.id == 101L
            article.title == "Getting Started"
        }
        verifyAll(built) {
            article == expected
            article.id == 101L
            article.title == "Getting Started"
        }
    }

    def "should update article using record wither"() {
        given:
        Article originalArticle = new Article().setTitle("Draft").setId(1L)
        Article newArticle = new Article().setTitle("Published").setId(2L)
        ArticleResponse response = new ArticleResponse(originalArticle)

        when:
        ArticleResponse updated = response.withArticle(newArticle)

        then:
        verifyAll(response) {
            article == originalArticle
            article.title == "Draft"
        }
        verifyAll(updated) {
            article == newArticle
            article.title == "Published"
        }
    }

    def "should round-trip serialize and deserialize ArticleResponse"() {
        given:
        Article expected = new Article().setTitle("User Guide").setId(555L)
        ArticleResponse response = new ArticleResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        ArticleResponse deserialized = objectMapper.readValue(json, ArticleResponse.class)

        then:
        verifyAll(deserialized) {
            it == response
            article != null
            article.id == 555L
            article.title == "User Guide"
        }
    }
}
