package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class CommentsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add comments item"() {
        given:
        def commentsResponse = new CommentsResponse()
        commentsResponse.comments == null
        def comment = new Comment(faker.lorem().word(), faker.lorem().sentence())

        when:
        commentsResponse.addCommentsItem(comment)

        then:
        commentsResponse.comments.size() == 1
        commentsResponse.comments[0] == comment
    }

    @Unroll
    def "add comments item to existing list"() {
        given:
        def existingComment = new Comment(faker.lorem().word(), faker.lorem().sentence())
        def commentsResponse = new CommentsResponse(comments: [existingComment])
        def newComment = new Comment(faker.lorem().word(), faker.lorem().sentence())

        when:
        commentsResponse.addCommentsItem(newComment)

        then:
        commentsResponse.comments.size() == 2
        commentsResponse.comments.containsAll([existingComment, newComment])
    }
}
