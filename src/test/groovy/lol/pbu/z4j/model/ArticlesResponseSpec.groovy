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
import java.net.URI
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class ArticlesResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ArticlesResponse with canonical constructor and builder"() {
        given:
        Article item = new Article()
                .setId(101L)
                .setTitle("Getting Started")
                .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .setPermissionGroupId(42L)
        List<Article> list = [item]
        Meta expectedMeta = new Meta().setHasMore(true).setAfterCursor("cursor_after").setBeforeCursor("cursor_before")
        Links expectedLinks = new Links()
                .setNext(URI.create("https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[after]=cursor_after").toURL())
                .setPrev(URI.create("https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[before]=cursor_before").toURL())

        when:
        ArticlesResponse direct = new ArticlesResponse(list, expectedMeta, expectedLinks, 42, "next_url", "prev_url", 2, 5, 10)
        ArticlesResponse built = ArticlesResponseBuilder.builder()
                .articles(list)
                .meta(expectedMeta)
                .links(expectedLinks)
                .count(42)
                .nextPage("next_url")
                .previousPage("prev_url")
                .page(2)
                .pageCount(5)
                .perPage(10)
                .build()

        then:
        verifyAll(direct) {
            articles() == list
            meta() == expectedMeta
            links() == expectedLinks
            count() == 42
            nextPage() == "next_url"
            previousPage() == "prev_url"
            page() == 2
            pageCount() == 5
            perPage() == 10
        }
        verifyAll(built) {
            it == direct
            articles() == list
            meta() == expectedMeta
            links() == expectedLinks
            count() == 42
            nextPage() == "next_url"
            previousPage() == "prev_url"
            page() == 2
            pageCount() == 5
            perPage() == 10
        }
    }

    def "should update ArticlesResponse using record withers and addArticlesItem method"() {
        given:
        Article first = new Article().setId(1L).setTitle("Article 1")
        Article second = new Article().setId(2L).setTitle("Article 2")
        Meta expectedMeta = new Meta().setHasMore(false)
        Links expectedLinks = new Links().setNext(URI.create("https://example.zendesk.com/next").toURL())
        ArticlesResponse response = new ArticlesResponse([first], null, null, null, null, null, null, null, null)

        when:
        ArticlesResponse updated = response
                .withArticles([second])
                .withMeta(expectedMeta)
                .withLinks(expectedLinks)
                .withCount(10)
                .withNextPage("https://example.zendesk.com/page2")
                .withPreviousPage("https://example.zendesk.com/page1")
                .withPage(1)
                .withPageCount(2)
                .withPerPage(5)
        ArticlesResponse withAdded = response.addArticlesItem(second)
        ArticlesResponse fromNull = new ArticlesResponse(null, expectedMeta, expectedLinks, 1, "n", "p", 1, 1, 10).addArticlesItem(first)

        then:
        verifyAll(response) {
            articles().size() == 1
            articles().first().id == 1L
            meta() == null
            links() == null
        }
        verifyAll(updated) {
            articles() == [second]
            meta() == expectedMeta
            links() == expectedLinks
            count() == 10
            nextPage() == "https://example.zendesk.com/page2"
            previousPage() == "https://example.zendesk.com/page1"
            page() == 1
            pageCount() == 2
            perPage() == 5
        }
        verifyAll(withAdded) {
            articles().size() == 2
            articles().first().id == 1L
            articles().last().id == 2L
        }
        verifyAll(fromNull) {
            articles().size() == 1
            articles().first().id == 1L
            meta() == expectedMeta
            links() == expectedLinks
            count() == 1
        }
    }

    def "should round-trip serialize and deserialize ArticlesResponse with pagination metadata"() {
        given:
        String json = '''{
  "articles": [
    {
      "id": 12345,
      "title": "Test Article"
    }
  ],
  "meta": {
    "has_more": true,
    "after_cursor": "cursor_after",
    "before_cursor": "cursor_before"
  },
  "links": {
    "next": "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[after]=cursor_after",
    "prev": "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[before]=cursor_before"
  },
  "count": 1,
  "page": 1,
  "page_count": 5,
  "per_page": 10,
  "next_page": "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page=2",
  "previous_page": null
}'''

        when:
        ArticlesResponse response = objectMapper.readValue(json, ArticlesResponse.class)
        String serialized = objectMapper.writeValueAsString(response)
        ArticlesResponse deserialized = objectMapper.readValue(serialized, ArticlesResponse.class)

        then:
        verifyAll(response) {
            articles() != null
            articles().size() == 1
            articles().first().id == 12345L
            articles().first().title == "Test Article"
            meta() != null
            meta().hasMore == true
            meta().afterCursor == "cursor_after"
            meta().beforeCursor == "cursor_before"
            links() != null
            links().next.toString() == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[after]=cursor_after"
            links().prev.toString() == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[before]=cursor_before"
            count() == 1
            page() == 1
            pageCount() == 5
            perPage() == 10
            nextPage() == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page=2"
            previousPage() == null
        }
        verifyAll(deserialized) {
            it == response
            articles().size() == 1
            count() == 1
        }
    }

    def "should handle null components in ArticlesResponse"() {
        when:
        ArticlesResponse empty = new ArticlesResponse(null, null, null, null, null, null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ArticlesResponse deserialized = objectMapper.readValue(json, ArticlesResponse.class)

        then:
        verifyAll(empty) {
            articles() == null
            meta() == null
            links() == null
            count() == null
            nextPage() == null
            previousPage() == null
            page() == null
            pageCount() == null
            perPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            articles() == null
        }
    }
}
