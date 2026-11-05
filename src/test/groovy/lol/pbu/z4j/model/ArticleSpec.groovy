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

class ArticleSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Article with constructors and builder"() {
        when:
        Article direct = new Article(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                1001L,
                "Article Title",
                2001L,
                "<p>Article Body</p>",
                false,
                ["tag1", "tag2"],
                "2026-01-01T00:00:00Z",
                false,
                "2026-01-02T00:00:00Z",
                "https://example.zendesk.com/hc/articles/1",
                1L,
                ["label1"],
                false,
                ["fr"],
                10L,
                true,
                3001L,
                "en-us",
                "2026-01-03T00:00:00Z",
                "https://example.zendesk.com/api/v2/help_center/articles/1.json",
                4001L,
                ["seg1", "seg2"],
                5L,
                3L
        )
        Article noArg = new Article()
        Article built = ArticleBuilder.builder()
                .localeAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .permissionGroupId(1001L)
                .title("Article Title")
                .authorId(2001L)
                .body("<p>Article Body</p>")
                .commentsDisabled(false)
                .contentTagIds(["tag1", "tag2"])
                .createdAt("2026-01-01T00:00:00Z")
                .draft(false)
                .editedAt("2026-01-02T00:00:00Z")
                .htmlUrl("https://example.zendesk.com/hc/articles/1")
                .id(1L)
                .labelNames(["label1"])
                .outdated(false)
                .outdatedLocales(["fr"])
                .position(10L)
                .promoted(true)
                .sectionId(3001L)
                .sourceLocale("en-us")
                .updatedAt("2026-01-03T00:00:00Z")
                .url("https://example.zendesk.com/api/v2/help_center/articles/1.json")
                .userSegmentId(4001L)
                .userSegmentIds(["seg1", "seg2"])
                .voteCount(5L)
                .voteSum(3L)
                .build()

        then:
        verifyAll(direct) {
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            permissionGroupId() == 1001L
            title() == "Article Title"
            authorId() == 2001L
            body() == "<p>Article Body</p>"
            commentsDisabled() == false
            contentTagIds() == ["tag1", "tag2"]
            createdAt() == "2026-01-01T00:00:00Z"
            draft() == false
            editedAt() == "2026-01-02T00:00:00Z"
            htmlUrl() == "https://example.zendesk.com/hc/articles/1"
            id() == 1L
            labelNames() == ["label1"]
            outdated() == false
            outdatedLocales() == ["fr"]
            position() == 10L
            promoted() == true
            sectionId() == 3001L
            sourceLocale() == "en-us"
            updatedAt() == "2026-01-03T00:00:00Z"
            url() == "https://example.zendesk.com/api/v2/help_center/articles/1.json"
            userSegmentId() == 4001L
            userSegmentIds() == ["seg1", "seg2"]
            voteCount() == 5L
            voteSum() == 3L
        }
        verifyAll(noArg) {
            localeAbbreviation() == null
            permissionGroupId() == null
            title() == null
            authorId() == null
            body() == null
            commentsDisabled() == null
            contentTagIds() == null
            createdAt() == null
            draft() == null
            editedAt() == null
            htmlUrl() == null
            id() == null
            labelNames() == null
            outdated() == null
            outdatedLocales() == null
            position() == null
            promoted() == null
            sectionId() == null
            sourceLocale() == null
            updatedAt() == null
            url() == null
            userSegmentId() == null
            userSegmentIds() == null
            voteCount() == null
            voteSum() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Article with record withers"() {
        given:
        Article initial = new Article()

        when:
        Article updated = initial
                .withLocaleAbbreviation(LocaleAbbreviation.FRENCH)
                .withPermissionGroupId(999L)
                .withTitle("Nouveau Titre")
                .withAuthorId(888L)
                .withBody("<p>Contenu</p>")
                .withCommentsDisabled(true)
                .withContentTagIds(["ct1"])
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withDraft(true)
                .withEditedAt("2026-02-02T00:00:00Z")
                .withHtmlUrl("https://example.zendesk.com/hc/articles/2")
                .withId(2L)
                .withLabelNames(["etiquette"])
                .withOutdated(true)
                .withOutdatedLocales(["de"])
                .withPosition(20L)
                .withPromoted(false)
                .withSectionId(777L)
                .withSourceLocale("fr")
                .withUpdatedAt("2026-02-03T00:00:00Z")
                .withUrl("https://example.zendesk.com/api/v2/help_center/articles/2.json")
                .withUserSegmentId(666L)
                .withUserSegmentIds(["us1"])
                .withVoteCount(10L)
                .withVoteSum(8L)

        then:
        verifyAll(updated) {
            localeAbbreviation() == LocaleAbbreviation.FRENCH
            permissionGroupId() == 999L
            title() == "Nouveau Titre"
            authorId() == 888L
            body() == "<p>Contenu</p>"
            commentsDisabled() == true
            contentTagIds() == ["ct1"]
            createdAt() == "2026-02-01T00:00:00Z"
            draft() == true
            editedAt() == "2026-02-02T00:00:00Z"
            htmlUrl() == "https://example.zendesk.com/hc/articles/2"
            id() == 2L
            labelNames() == ["etiquette"]
            outdated() == true
            outdatedLocales() == ["de"]
            position() == 20L
            promoted() == false
            sectionId() == 777L
            sourceLocale() == "fr"
            updatedAt() == "2026-02-03T00:00:00Z"
            url() == "https://example.zendesk.com/api/v2/help_center/articles/2.json"
            userSegmentId() == 666L
            userSegmentIds() == ["us1"]
            voteCount() == 10L
            voteSum() == 8L
        }
    }

    def "should add items with helper methods when list is null or non-null"() {
        given:
        Article initial = new Article()

        when:
        Article step1 = initial
                .addContentTagIdsItem("tagA")
                .addLabelNamesItem("labelA")
                .addUserSegmentIdsItem("segA")

        Article step2 = step1
                .addContentTagIdsItem("tagB")
                .addLabelNamesItem("labelB")
                .addUserSegmentIdsItem("segB")

        then:
        verifyAll(step1) {
            contentTagIds() == ["tagA"]
            labelNames() == ["labelA"]
            userSegmentIds() == ["segA"]
        }
        verifyAll(step2) {
            contentTagIds() == ["tagA", "tagB"]
            labelNames() == ["labelA", "labelB"]
            userSegmentIds() == ["segA", "segB"]
        }
    }

    def "should round-trip serialize and deserialize Article"() {
        given:
        Article article = new Article(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                101L,
                "Serialization Article",
                202L,
                "<p>Serialization Body</p>",
                false,
                ["tag1"],
                "2026-03-01T00:00:00Z",
                false,
                "2026-03-02T00:00:00Z",
                "https://example.com/hc/1",
                303L,
                ["lbl"],
                false,
                ["es"],
                1L,
                true,
                404L,
                "en-us",
                "2026-03-03T00:00:00Z",
                "https://example.com/api/1.json",
                505L,
                ["seg1"],
                12L,
                10L
        )

        when:
        String json = objectMapper.writeValueAsString(article)
        Map map = objectMapper.readValue(json, Map.class)
        Article deserialized = objectMapper.readValue(json, Article.class)

        then:
        verifyAll(map) {
            get("locale") == "en-us"
            get("permission_group_id") == 101
            get("title") == "Serialization Article"
            get("author_id") == 202
            get("body") == "<p>Serialization Body</p>"
            get("comments_disabled") == false
            get("content_tag_ids") == ["tag1"]
            get("created_at") == "2026-03-01T00:00:00Z"
            get("draft") == false
            get("edited_at") == "2026-03-02T00:00:00Z"
            get("html_url") == "https://example.com/hc/1"
            get("id") == 303
            get("label_names") == ["lbl"]
            get("outdated") == false
            get("outdated_locales") == ["es"]
            get("position") == 1
            get("promoted") == true
            get("section_id") == 404
            get("source_locale") == "en-us"
            get("updated_at") == "2026-03-03T00:00:00Z"
            get("url") == "https://example.com/api/1.json"
            get("user_segment_id") == 505
            get("user_segment_ids") == ["seg1"]
            get("vote_count") == 12
            get("vote_sum") == 10
        }
        verifyAll(deserialized) {
            it == article
        }
    }
}
