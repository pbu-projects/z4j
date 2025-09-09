package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class PostSpec extends Z4jSpec {

    @Unroll
    def "should add content tag ids item"() {
        given:
        def post = new Post(faker.lorem().sentence())
        post.contentTagIds == null
        def contentTagId = faker.number().randomNumber()

        when:
        post.addContentTagIdsItem(contentTagId)

        then:
        post.contentTagIds.size() == 1
        post.contentTagIds[0] == contentTagId
    }

    @Unroll
    def "add content tag ids item to existing list"() {
        given:
        def existingContentTagId = faker.number().randomNumber()
        def post = new Post(faker.lorem().sentence())
        post.contentTagIds = [existingContentTagId]
        def newContentTagId = faker.number().randomNumber()

        when:
        post.addContentTagIdsItem(newContentTagId)

        then:
        post.contentTagIds.size() == 2
        post.contentTagIds.containsAll([existingContentTagId, newContentTagId])
    }
}
