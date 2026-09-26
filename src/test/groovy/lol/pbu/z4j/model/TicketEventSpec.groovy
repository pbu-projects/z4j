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

class TicketEventSpec extends Z4jSpec {

    def "should instantiate and set properties on TicketEvent"() {
        given:
        def event = new TicketEvent()

        when:
        event.setId(101L)
             .setTicketId(202L)
             .setEventType("Comment")
             .setVia("web")
             .setCreatedAt("2026-09-26T06:00:00Z")
             .setAuthorId(303L)
             .setValue("Test comment")
             .setMetadata([key: "value"])

        then:
        event.id == 101L
        event.ticketId == 202L
        event.eventType == "Comment"
        event.via == "web"
        event.createdAt == "2026-09-26T06:00:00Z"
        event.authorId == 303L
        event.value == "Test comment"
        event.metadata["key"] == "value"
    }
}
