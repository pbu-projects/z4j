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

class ArticleSearchResponseSpec extends Z4jSpec {

    @Unroll
    def "should add results item"() {
        given:
        def articleSearchResponse = new ArticleSearchResponse()
        articleSearchResponse.results == null
        def article = new Article(faker.book().title(), faker.number().randomNumber(), faker.book().author())

        when:
        articleSearchResponse.addResultsItem(article)

        then:
        articleSearchResponse.results.size() == 1
        articleSearchResponse.results[0] == article
    }

    @Unroll
    def "add results item to existing list"() {
        given:
        def existingArticle = new Article(faker.book().title(), faker.number().randomNumber(), faker.book().author())
        def articleSearchResponse = new ArticleSearchResponse(results: [existingArticle])
        def newArticle = new Article(faker.book().title(), faker.number().randomNumber(), faker.book().author())

        when:
        articleSearchResponse.addResultsItem(newArticle)

        then:
        articleSearchResponse.results.size() == 2
        articleSearchResponse.results.containsAll([existingArticle, newArticle])
    }
}

