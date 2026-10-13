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
import spock.lang.Unroll

import java.net.URI

class ArticlesResponseSpec extends Z4jSpec {

    @Unroll
    def "should add articles item"() {
        given:
        def articlesResponse = new ArticlesResponse()
        articlesResponse.articles == null
        def article = new Article().setLocaleAbbreviation(LocaleAbbreviation.BULGARIAN).setPermissionGroupId(faker.number().randomNumber()).setTitle(faker.book().author())

        when:
        articlesResponse.addArticlesItem(article)

        then:
        articlesResponse.articles.size() == 1
        articlesResponse.articles[0] == article
    }

    @Unroll
    def "add articles item to existing list"() {
        given:
        def existingArticle = new Article().setLocaleAbbreviation(LocaleAbbreviation.BULGARIAN).setPermissionGroupId(faker.number().randomNumber()).setTitle(faker.book().author())
        def articlesResponse = new ArticlesResponse(articles: [existingArticle])
        def newArticle = new Article().setLocaleAbbreviation(LocaleAbbreviation.FRENCH).setPermissionGroupId(faker.number().randomNumber()).setTitle(faker.book().author())

        when:
        articlesResponse.addArticlesItem(newArticle)

        then:
        articlesResponse.articles.size() == 2
        articlesResponse.articles.containsAll([existingArticle, newArticle])
    }

    def "should instantiate and set pagination fields on ArticlesResponse"() {
        given:
        def response = new ArticlesResponse()
        def meta = new Meta().setHasMore(true).setAfterCursor("cursor_after").setBeforeCursor("cursor_before")
        def links = new Links().setNext(new URI("https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[after]=cursor_after").toURL())
                .setPrev(new URI("https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[before]=cursor_before").toURL())

        when:
        response.setMeta(meta)
                .setLinks(links)
                .setCount(42)
                .setPage(1)
                .setPageCount(5)
                .setPerPage(10)
                .setNextPage("https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page=2")
                .setPreviousPage("https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page=1")

        then:
        response.meta == meta
        response.links == links
        response.count == 42
        response.page == 1
        response.pageCount == 5
        response.perPage == 10
        response.nextPage == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page=2"
        response.previousPage == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page=1"
    }

    def "should round-trip serde ArticlesResponse with pagination metadata"() {
        given:
        def objectMapper = adminCtx.getBean(ObjectMapper)
        def json = '''{
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
        def response = objectMapper.readValue(json, ArticlesResponse)

        then:
        response != null
        response.articles != null
        response.articles.size() == 1
        response.articles[0].id == 12345L
        response.articles[0].title == "Test Article"
        response.meta != null
        response.meta.hasMore == true
        response.meta.afterCursor == "cursor_after"
        response.meta.beforeCursor == "cursor_before"
        response.links != null
        response.links.next.toString() == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[after]=cursor_after"
        response.links.prev.toString() == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page[before]=cursor_before"
        response.count == 1
        response.page == 1
        response.pageCount == 5
        response.perPage == 10
        response.nextPage == "https://example.zendesk.com/api/v2/help_center/en-us/articles.json?page=2"
        response.previousPage == null

        when:
        def serialized = objectMapper.writeValueAsString(response)
        def deserialized = objectMapper.readValue(serialized, ArticlesResponse)

        then:
        deserialized == response
    }
}

