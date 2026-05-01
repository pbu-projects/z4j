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

