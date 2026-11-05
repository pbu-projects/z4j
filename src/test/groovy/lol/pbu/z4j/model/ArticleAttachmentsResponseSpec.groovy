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

class ArticleAttachmentsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ArticleAttachmentsResponse with canonical constructor and builder"() {
        given:
        ArticleAttachment item = new ArticleAttachment().withId(101L).withFileName("guide.pdf")
        List<ArticleAttachment> list = [item]

        when:
        ArticleAttachmentsResponse direct = new ArticleAttachmentsResponse(list)
        ArticleAttachmentsResponse built = ArticleAttachmentsResponseBuilder.builder()
                .articleAttachments(list)
                .build()

        then:
        verifyAll(direct) {
            articleAttachments() == list
            articleAttachments().size() == 1
            articleAttachments().first().id == 101L
            articleAttachments().first().fileName == "guide.pdf"
        }
        verifyAll(built) {
            articleAttachments() == list
            articleAttachments().size() == 1
            articleAttachments().first().id == 101L
            articleAttachments().first().fileName == "guide.pdf"
        }
    }

    def "should update article attachments using record wither and addArticleAttachmentsItem method"() {
        given:
        ArticleAttachment first = new ArticleAttachment().withId(1L).withFileName("draft.pdf")
        ArticleAttachment second = new ArticleAttachment().withId(2L).withFileName("final.pdf")
        ArticleAttachmentsResponse response = new ArticleAttachmentsResponse([first])

        when:
        ArticleAttachmentsResponse withNewList = response.withArticleAttachments([second])
        ArticleAttachmentsResponse withAdded = response.addArticleAttachmentsItem(second)
        ArticleAttachmentsResponse fromNull = new ArticleAttachmentsResponse(null).addArticleAttachmentsItem(first)

        then:
        verifyAll(response) {
            articleAttachments().size() == 1
            articleAttachments().first().id == 1L
        }
        verifyAll(withNewList) {
            articleAttachments().size() == 1
            articleAttachments().first().id == 2L
        }
        verifyAll(withAdded) {
            articleAttachments().size() == 2
            articleAttachments().first().id == 1L
            articleAttachments().last().id == 2L
        }
        verifyAll(fromNull) {
            articleAttachments().size() == 1
            articleAttachments().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize ArticleAttachmentsResponse"() {
        given:
        ArticleAttachment item = new ArticleAttachment().withId(555L).withFileName("manual.pdf")
        ArticleAttachmentsResponse response = new ArticleAttachmentsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleAttachmentsResponse deserialized = objectMapper.readValue(json, ArticleAttachmentsResponse.class)

        then:
        verifyAll(map) {
            containsKey("article_attachments")
            get("article_attachments") instanceof List
            ((List) get("article_attachments")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            articleAttachments() != null
            articleAttachments().size() == 1
            articleAttachments().first().id == 555L
            articleAttachments().first().fileName == "manual.pdf"
        }
    }

    def "should handle null article attachments component in constructor, wither, and serde"() {
        when:
        ArticleAttachmentsResponse response = new ArticleAttachmentsResponse(null)
        ArticleAttachment item = new ArticleAttachment().withId(99L).withFileName("spec.pdf")
        ArticleAttachmentsResponse updated = response.withArticleAttachments([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleAttachmentsResponse deserialized = objectMapper.readValue(json, ArticleAttachmentsResponse.class)

        then:
        verifyAll(response) {
            articleAttachments() == null
        }
        verifyAll(updated) {
            articleAttachments() != null
            articleAttachments().size() == 1
            articleAttachments().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("article_attachments")
        }
        verifyAll(deserialized) {
            it == response
            articleAttachments() == null
        }
    }
}
