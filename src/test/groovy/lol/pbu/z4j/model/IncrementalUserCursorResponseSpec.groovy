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

class IncrementalUserCursorResponseSpec extends Z4jSpec {

    def "should instantiate and set properties on IncrementalUserCursorResponse"() {
        given:
        def response = new IncrementalUserCursorResponse()
        def user = new User()
        user.setId(456L)

        when:
        response.setUsers([user])
              .setCursor("user_cursor")
              .setAfterCursor("user_after")
              .setBeforeCursor("user_before")
              .setEndOfStream(true)
              .setCount(1)

        then:
        response.users.size() == 1
        response.users[0].id == 456L
        response.cursor == "user_cursor"
        response.afterCursor == "user_after"
        response.beforeCursor == "user_before"
        response.endOfStream == true
        response.count == 1
    }
}
