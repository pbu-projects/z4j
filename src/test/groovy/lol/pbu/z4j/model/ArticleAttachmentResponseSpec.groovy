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

class ArticleAttachmentResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ArticleAttachmentResponse with canonical constructor and builder"() {
        given:
        ArticleAttachment expected = new ArticleAttachment().withId(101L).withFileName("guide.pdf")

        when:
        ArticleAttachmentResponse direct = new ArticleAttachmentResponse(expected)
        ArticleAttachmentResponse built = ArticleAttachmentResponseBuilder.builder().articleAttachment(expected).build()

        then:
        verifyAll(direct) {
            articleAttachment == expected
            articleAttachment.id == 101L
            articleAttachment.fileName == "guide.pdf"
        }
        verifyAll(built) {
            articleAttachment == expected
            articleAttachment.id == 101L
            articleAttachment.fileName == "guide.pdf"
        }
    }

    def "should update article attachment using record wither"() {
        given:
        ArticleAttachment original = new ArticleAttachment().withId(1L).withFileName("draft.pdf")
        ArticleAttachment updatedAttachment = new ArticleAttachment().withId(2L).withFileName("final.pdf")
        ArticleAttachmentResponse response = new ArticleAttachmentResponse(original)

        when:
        ArticleAttachmentResponse updated = response.withArticleAttachment(updatedAttachment)

        then:
        verifyAll(response) {
            articleAttachment == original
            articleAttachment.fileName == "draft.pdf"
        }
        verifyAll(updated) {
            articleAttachment == updatedAttachment
            articleAttachment.fileName == "final.pdf"
        }
    }

    def "should round-trip serialize and deserialize ArticleAttachmentResponse"() {
        given:
        ArticleAttachment expected = new ArticleAttachment().withId(555L).withFileName("manual.pdf")
        ArticleAttachmentResponse response = new ArticleAttachmentResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleAttachmentResponse deserialized = objectMapper.readValue(json, ArticleAttachmentResponse.class)

        then:
        verifyAll(map) {
            containsKey("article_attachment")
            get("article_attachment") instanceof Map
            ((Map) get("article_attachment")).id == 555
            ((Map) get("article_attachment")).file_name == "manual.pdf"
        }
        verifyAll(deserialized) {
            it == response
            articleAttachment != null
            articleAttachment.id == 555L
            articleAttachment.fileName == "manual.pdf"
        }
    }

    def "should handle null article attachment component in constructor and wither"() {
        when:
        ArticleAttachmentResponse response = new ArticleAttachmentResponse(null)
        ArticleAttachment replacement = new ArticleAttachment().withId(99L).withFileName("spec.pdf")
        ArticleAttachmentResponse updated = response.withArticleAttachment(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleAttachmentResponse deserialized = objectMapper.readValue(json, ArticleAttachmentResponse.class)

        then:
        verifyAll(response) {
            articleAttachment() == null
        }
        verifyAll(updated) {
            articleAttachment() == replacement
            articleAttachment().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("article_attachment")
        }
        verifyAll(deserialized) {
            it == response
            articleAttachment() == null
        }
    }
}
