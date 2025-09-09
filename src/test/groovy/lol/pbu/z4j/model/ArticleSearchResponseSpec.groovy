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
