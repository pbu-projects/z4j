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
import spock.lang.Shared
import spock.lang.Specification

class ArticleAttachmentSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ArticleAttachment with constructors and builder"() {
        when:
        ArticleAttachment direct = new ArticleAttachment(
                101L,
                "https://example.com/attachments/1",
                202L,
                "manual.pdf",
                "User Manual.pdf",
                "https://example.com/attachments/content/1",
                "application/pdf",
                1024L,
                false,
                "2026-01-01T00:00:00Z",
                "2026-01-02T00:00:00Z"
        )
        ArticleAttachment noArg = new ArticleAttachment()
        ArticleAttachment built = ArticleAttachmentBuilder.builder()
                .id(101L)
                .url("https://example.com/attachments/1")
                .articleId(202L)
                .fileName("manual.pdf")
                .displayFileName("User Manual.pdf")
                .contentUrl("https://example.com/attachments/content/1")
                .contentType("application/pdf")
                .size(1024L)
                .inline(false)
                .createdAt("2026-01-01T00:00:00Z")
                .updatedAt("2026-01-02T00:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            url() == "https://example.com/attachments/1"
            articleId() == 202L
            fileName() == "manual.pdf"
            displayFileName() == "User Manual.pdf"
            contentUrl() == "https://example.com/attachments/content/1"
            contentType() == "application/pdf"
            size() == 1024L
            inline() == false
            createdAt() == "2026-01-01T00:00:00Z"
            updatedAt() == "2026-01-02T00:00:00Z"
        }
        verifyAll(noArg) {
            id() == null
            url() == null
            articleId() == null
            fileName() == null
            displayFileName() == null
            contentUrl() == null
            contentType() == null
            size() == null
            inline() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update ArticleAttachment with record withers"() {
        given:
        ArticleAttachment initial = new ArticleAttachment()

        when:
        ArticleAttachment updated = initial
                .withId(303L)
                .withUrl("https://example.com/attachments/2")
                .withArticleId(404L)
                .withFileName("spec.png")
                .withDisplayFileName("Architecture.png")
                .withContentUrl("https://example.com/attachments/content/2")
                .withContentType("image/png")
                .withSize(2048L)
                .withInline(true)
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withUpdatedAt("2026-02-02T00:00:00Z")

        then:
        verifyAll(updated) {
            id() == 303L
            url() == "https://example.com/attachments/2"
            articleId() == 404L
            fileName() == "spec.png"
            displayFileName() == "Architecture.png"
            contentUrl() == "https://example.com/attachments/content/2"
            contentType() == "image/png"
            size() == 2048L
            inline() == true
            createdAt() == "2026-02-01T00:00:00Z"
            updatedAt() == "2026-02-02T00:00:00Z"
        }
    }

    def "should round-trip serialize and deserialize ArticleAttachment"() {
        given:
        ArticleAttachment attachment = new ArticleAttachment(
                555L,
                "https://example.com/attachments/555",
                666L,
                "guide.pdf",
                "Guide.pdf",
                "https://example.com/content/555",
                "application/pdf",
                4096L,
                false,
                "2026-03-01T00:00:00Z",
                "2026-03-02T00:00:00Z"
        )

        when:
        String json = objectMapper.writeValueAsString(attachment)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleAttachment deserialized = objectMapper.readValue(json, ArticleAttachment.class)

        then:
        verifyAll(map) {
            get("id") == 555
            get("url") == "https://example.com/attachments/555"
            get("article_id") == 666
            get("file_name") == "guide.pdf"
            get("display_file_name") == "Guide.pdf"
            get("content_url") == "https://example.com/content/555"
            get("content_type") == "application/pdf"
            get("size") == 4096
            get("inline") == false
            get("created_at") == "2026-03-01T00:00:00Z"
            get("updated_at") == "2026-03-02T00:00:00Z"
        }
        verifyAll(deserialized) {
            it == attachment
        }
    }
}
