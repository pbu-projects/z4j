package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class CommunityPostSearchResponseSpec extends Z4jSpec {

    @Unroll
    def "should add results item"() {
        given:
        def communityPostSearchResponse = new CommunityPostSearchResponse()
        communityPostSearchResponse.results == null
        def post = new Post(faker.lorem().sentence())

        when:
        communityPostSearchResponse.addResultsItem(post)

        then:
        communityPostSearchResponse.results.size() == 1
        communityPostSearchResponse.results.getAt(0) == post
    }

    @Unroll
    def "add results item to existing list"() {
        given:
        def existingPost = new Post(faker.lorem().sentence())
        def communityPostSearchResponse = new CommunityPostSearchResponse(results: [existingPost])
        def newPost = new Post(faker.lorem().sentence())

        when:
        communityPostSearchResponse.addResultsItem(newPost)

        then:
        communityPostSearchResponse.results.size() == 2
        communityPostSearchResponse.results.containsAll([existingPost, newPost])
    }
}
