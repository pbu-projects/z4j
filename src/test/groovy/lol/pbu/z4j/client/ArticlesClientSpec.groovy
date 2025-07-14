package lol.pbu.z4j.client

import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
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
        allLocales = userCtx.getBean(LocalesClient.class).listLocales().body().locales.collect { it.locale.toLowerCase() }
    }

    @Unroll
    def "can use ListArticles for other tests using the '#locale' locale"() {
        // https://github.com/PeanutButter-Unicorn/z4j/issues/31
        when: "query articles list for the '#locale' locale"
        def response = articlesClient.listArticles(locale, null, null, null, null)

        then: "received expected 200 response"
        response.status() == HttpStatus.OK

        and: "articles object is not null (even if empty)"
        response.body().articles != null

        then: "validate the returned article objects have an ID that other tests will require"
        if (response.body().articles.size() > 0) {
            // this tests ability needed in https://github.com/PeanutButter-Unicorn/z4j/issues/30
            response.body().articles.forEach { article -> article.id != null }
        }

        where:
        [articlesClient, locale] << [
                [adminArticlesClient, agentArticlesClient, userArticlesClient],
                allLocales
        ].combinations()
    }
}
