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

