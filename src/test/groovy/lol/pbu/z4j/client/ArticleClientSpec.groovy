package lol.pbu.z4j.client

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ArticlesResponse
import lol.pbu.z4j.model.ListArticlesSortByParameter
import lol.pbu.z4j.model.ListArticlesSortOrderParameter
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
        allLocales = userCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.locale.toLowerCase() }
    }

    def "can use ListArticles for other tests using the '#locale' locale"(ArticleClient articleClient, String locale, ListArticlesSortByParameter sortBy, ListArticlesSortOrderParameter sortOrder, Long startTime, String labelNames) {
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
                [ListArticlesSortByParameter.values(), null].flatten(),
                [ListArticlesSortOrderParameter.values(), null].flatten(),
                [System.currentTimeMillis() / 1000 - 30000000, null].flatten(),
                [null] //TODO add article labels and validate 1 or more labels can be passed
        ].combinations()
    }
}
