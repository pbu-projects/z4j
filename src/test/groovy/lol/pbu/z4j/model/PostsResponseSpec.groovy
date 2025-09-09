package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class PostsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add posts item"() {
        given:
        def postsResponse = new PostsResponse()
        postsResponse.posts == null
        def post = new Post(faker.lorem().sentence())

        when:
        postsResponse.addPostsItem(post)

        then:
        postsResponse.posts.size() == 1
        postsResponse.posts.getAt(0) == post
    }

    @Unroll
    def "add posts item to existing list"() {
        given:
        def existingPost = new Post(faker.lorem().sentence())
        def postsResponse = new PostsResponse(posts: [existingPost])
        def newPost = new Post(faker.lorem().sentence())

        when:
        postsResponse.addPostsItem(newPost)

        then:
        postsResponse.posts.size() == 2
        postsResponse.posts.containsAll([existingPost, newPost])
    }
}
