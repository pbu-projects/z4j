package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ArticleRequestArticleSpec extends Z4jSpec {

    @Unroll
    def "should create ArticleRequestArticle with correct properties"() {
        given:
        def locale = faker.nation().language()
        def permissionGroupId = faker.number().randomNumber()
        def title = faker.book().title()
        def userSegmentId = faker.number().randomNumber()

        when:
        def articleRequestArticle = new ArticleRequestArticle(locale, permissionGroupId, title, userSegmentId)

        then:
        articleRequestArticle.locale == locale
        articleRequestArticle.permissionGroupId == permissionGroupId
        articleRequestArticle.title == title
        articleRequestArticle.userSegmentId == userSegmentId
    }
}
