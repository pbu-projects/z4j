package lol.pbu.z4j.client


import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ArticlesResponse
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Unroll

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

    @Unroll
    def "can use ListArticles for other tests using the '#locale' locale"(ArticleClient ArticleClient, String locale) {
        // https://github.com/PeanutButter-Unicorn/z4j/issues/31
        when: "query articles list for the '#locale' locale"
        Mono<ArticlesResponse> response = ArticleClient.listArticles(locale, null, null, null, null)

        then:
        ArticlesResponse articlesResponse = response.block()

        then: "validate the returned article objects have an ID that other tests will require"
        if (articlesResponse.articles.size() > 0) {
            // this tests ability needed in https://github.com/PeanutButter-Unicorn/z4j/issues/30
            articlesResponse.articles.forEach { article -> article.id != null }
        }

        where:
        [ArticleClient, locale] << [[adminArticleClient, agentArticleClient, userArticleClient],
                                     allLocales].combinations()
    }
}
