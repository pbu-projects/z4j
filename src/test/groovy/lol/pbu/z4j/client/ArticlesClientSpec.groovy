package lol.pbu.z4j.client


import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ArticlesResponse
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class ArticlesClientSpec extends Z4jSpec {

    @Shared
    ArticlesClient adminArticlesClient, agentArticlesClient, userArticlesClient

    @Shared
    List<String> allLocales

    def setupSpec() {
        adminArticlesClient = adminCtx.getBean(ArticlesClient.class)
        agentArticlesClient = agentCtx.getBean(ArticlesClient.class)
        userArticlesClient = userCtx.getBean(ArticlesClient.class)
        allLocales = userCtx.getBean(LocalesClient.class).listLocales().block().locales.collect { it.locale.toLowerCase() }
    }

    @Unroll
    def "can use ListArticles for other tests using the '#locale' locale"(ArticlesClient articlesClient, String locale) {
        // https://github.com/PeanutButter-Unicorn/z4j/issues/31
        when: "query articles list for the '#locale' locale"
        Mono<ArticlesResponse> response = articlesClient.listArticles(locale, null, null, null, null)

        then:
        ArticlesResponse articlesResponse = response.block()

        then: "validate the returned article objects have an ID that other tests will require"
        if (articlesResponse.articles.size() > 0) {
            // this tests ability needed in https://github.com/PeanutButter-Unicorn/z4j/issues/30
            articlesResponse.articles.forEach { article -> article.id != null }
        }

        where:
        [articlesClient, locale] << [[adminArticlesClient, agentArticlesClient, userArticlesClient],
                                     allLocales].combinations()
    }
}
