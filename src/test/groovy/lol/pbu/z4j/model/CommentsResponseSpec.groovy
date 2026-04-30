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

