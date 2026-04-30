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

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ArticlesResponseSpec extends Z4jSpec {

    @Unroll
    def "should add articles item"() {
        given:
        def articlesResponse = new ArticlesResponse()
        articlesResponse.articles == null
        def article = new Article(faker.book().title(), faker.number().randomNumber(), faker.book().author())

        when:
        articlesResponse.addArticlesItem(article)

        then:
        articlesResponse.articles.size() == 1
        articlesResponse.articles.getAt(0) == article
    }

    @Unroll
    def "add articles item to existing list"() {
        given:
        def existingArticle = new Article(faker.book().title(), faker.number().randomNumber(), faker.book().author())
        def articlesResponse = new ArticlesResponse(articles: [existingArticle])
        def newArticle = new Article(faker.book().title(), faker.number().randomNumber(), faker.book().author())

        when:
        articlesResponse.addArticlesItem(newArticle)

        then:
        articlesResponse.articles.size() == 2
        articlesResponse.articles.containsAll([existingArticle, newArticle])
    }
}

