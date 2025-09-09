package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ArticleAttachmentsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add article attachments item"() {
        given:
        def articleAttachmentsResponse = new ArticleAttachmentsResponse()
        articleAttachmentsResponse.articleAttachments == null
        def articleAttachment = new ArticleAttachment()

        when:
        articleAttachmentsResponse.addArticleAttachmentsItem(articleAttachment)

        then:
        articleAttachmentsResponse.articleAttachments.size() == 1
        articleAttachmentsResponse.articleAttachments[0] == articleAttachment
    }

    @Unroll
    def "add article attachments item to existing list"() {
        given:
        def existingArticleAttachment = new ArticleAttachment()
        def articleAttachmentsResponse = new ArticleAttachmentsResponse([existingArticleAttachment])
        def newArticleAttachment = new ArticleAttachment()

        when:
        articleAttachmentsResponse.addArticleAttachmentsItem(newArticleAttachment)

        then:
        articleAttachmentsResponse.articleAttachments.size() == 2
        articleAttachmentsResponse.articleAttachments.containsAll([existingArticleAttachment, newArticleAttachment])
    }
}
