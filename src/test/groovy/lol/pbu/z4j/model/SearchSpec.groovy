package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class SearchSpec extends Z4jSpec {

    @Unroll
    def "should create Search with correct properties"() {
        given:
        // SearchResultsInner is an interface, so we use a concrete implementation.
        // Assuming 'Article' is one such implementation.
        def article1 = new Article(faker.locality().toString(),
                faker.number().randomNumber(),
                faker.book().title())

        def article2 = new Article(faker.locality().toString(),
                faker.number().randomNumber(),
                faker.book().title())

        List<SearchResultsInner> searchResults = [article1, article2]

        when:
        def search = new Search(searchResults)

        then:
        search.results == searchResults
        search.results.size() == 2
    }

    @Unroll
    def "should add item to results"() {
        given:
        def initialArticle = new Article(faker.locality().toString(),
                faker.number().randomNumber(),
                faker.book().title())

        List<SearchResultsInner> initialResults = [initialArticle]
        def search = new Search(initialResults)

        Article newResult = new Article(faker.locality().toString(),
                faker.number().randomNumber(),
                faker.book().title())

        when:
        def returnedSearch = search.addResultsItem(newResult)

        then:
        "the add method should be chainable"
        returnedSearch == search

        and: "the new item should be added to the list"
        search.results.size() == 2
        search.results.contains(newResult)
    }
}