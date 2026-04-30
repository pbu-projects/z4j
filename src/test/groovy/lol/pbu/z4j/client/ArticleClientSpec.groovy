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
package lol.pbu.z4j.client

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ArticlesResponse
import lol.pbu.z4j.model.SortArticleBy
import lol.pbu.z4j.model.SortOrder
import reactor.core.publisher.Mono
import spock.lang.Shared

@MicronautTest
class ArticleClientSpec extends Z4jSpec {

    @Shared
    ArticleClient adminArticleClient, agentArticleClient, userArticleClient

    @Shared
    List<String> allLocales

    def setupSpec() {
        adminArticleClient = adminCtx.getBean(ArticleClient.class)
        agentArticleClient = agentCtx.getBean(ArticleClient.class)
        userArticleClient = userCtx.getBean(ArticleClient.class)
        allLocales = userCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.localeName.toLowerCase() }
    }

    def "can use ListArticles for other tests using the '#locale' locale"(ArticleClient articleClient, String locale, SortArticleBy sortBy, SortOrder sortOrder, Long startTime, String labelNames) {
        // https://github.com/PeanutButter-Unicorn/z4j/issues/31
        when: "query articles list for the '#locale' locale"
        Mono<ArticlesResponse> response = articleClient.listArticles(locale, sortBy, sortOrder, startTime, labelNames)

        then:
        ArticlesResponse articlesResponse = response.block()

        then: "validate the returned article objects have an ID that other tests will require"
        if (articlesResponse.articles.size() > 0) {
            // this tests ability needed in https://github.com/PeanutButter-Unicorn/z4j/issues/30
            articlesResponse.articles.forEach { article -> article.id != null }
        }

        where:
        [articleClient, locale, sortBy, sortOrder, startTime, labelNames] << [
                [adminArticleClient, agentArticleClient, userArticleClient],
                allLocales,
                [SortArticleBy.values(), null].flatten(),
                [SortOrder.values(), null].flatten(),
                [System.currentTimeMillis() / 1000 - 30000000, null].flatten(),
                [null] //TODO add article labels and validate 1 or more labels can be passed
        ].combinations()
    }
}

