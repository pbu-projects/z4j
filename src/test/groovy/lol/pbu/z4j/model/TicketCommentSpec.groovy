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

class TicketCommentSpec extends Z4jSpec {

    @Unroll
    def "should add uploads item"() {
        given:
        def ticketComment = new TicketComment()
        ticketComment.uploads == null
        def upload = faker.lorem().word()

        when:
        ticketComment.addUploadsItem(upload)

        then:
        ticketComment.uploads.size() == 1
        ticketComment.uploads.getAt(0) == upload
    }

    @Unroll
    def "add uploads item to existing list"() {
        given:
        def existingUpload = faker.lorem().word()
        def ticketComment = new TicketComment(uploads: [existingUpload])
        def newUpload = faker.lorem().word()

        when:
        ticketComment.addUploadsItem(newUpload)

        then:
        ticketComment.uploads.size() == 2
        ticketComment.uploads.containsAll([existingUpload, newUpload])
    }
}

