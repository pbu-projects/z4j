package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ArticleRequestSpec extends Z4jSpec {

    @Unroll
    def "should create ArticleRequest with correct properties"() {
        given:
        def articleRequestArticle = new ArticleRequestArticle(
                faker.book().title(),
                faker.number().randomNumber(),
                faker.lorem().paragraph(),
                faker.number().randomNumber()
        )

        when:
        def articleRequest = new ArticleRequest(articleRequestArticle)

        then:
        articleRequest.article == articleRequestArticle
    }
}
