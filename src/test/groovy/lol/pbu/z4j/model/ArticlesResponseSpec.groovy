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
