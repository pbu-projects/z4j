/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

