package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class PostCommentsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add comments item"() {
        given:
        def postCommentsResponse = new PostCommentsResponse()
        postCommentsResponse.comments == null
        def postComment = new PostComment(faker.lorem().sentence())

        when:
        postCommentsResponse.addCommentsItem(postComment)

        then:
        postCommentsResponse.comments.size() == 1
        postCommentsResponse.comments.getAt(0) == postComment
    }

    @Unroll
    def "add comments item to existing list"() {
        given:
        def existingPostComment = new PostComment(faker.lorem().sentence())
        def postCommentsResponse = new PostCommentsResponse(comments: [existingPostComment])
        def newPostComment = new PostComment(faker.lorem().sentence())

        when:
        postCommentsResponse.addCommentsItem(newPostComment)

        then:
        postCommentsResponse.comments.size() == 2
        postCommentsResponse.comments.containsAll([existingPostComment, newPostComment])
    }
}
