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

import io.micronaut.serde.ObjectMapper
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class AuditEventsInnerSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create AuditEventsInner with constructors and builder"() {
        given:
        Via expectedVia = new Via("rule", new ViaSource(null, "trigger", null))
        List<Long> expectedRecipients = [101L, 102L]

        when:
        AuditEventsInner direct = new AuditEventsInner(
                "Comment body",
                "status",
                123L,
                "Change",
                "open",
                "new",
                expectedVia,
                "Ticket updated",
                expectedRecipients
        )
        AuditEventsInner noArg = new AuditEventsInner()
        AuditEventsInner built = AuditEventsInnerBuilder.builder()
                .body("Comment body")
                .fieldName("status")
                .id(123L)
                .type("Change")
                .value("open")
                .previousValue("new")
                .via(expectedVia)
                .subject("Ticket updated")
                .recipients(expectedRecipients)
                .build()

        then:
        verifyAll(direct) {
            it.body() == "Comment body"
            it.fieldName() == "status"
            it.id() == 123L
            it.type() == "Change"
            it.value() == "open"
            it.previousValue() == "new"
            it.via() == expectedVia
            it.subject() == "Ticket updated"
            it.recipients() == expectedRecipients
        }
        verifyAll(noArg) {
            it.body() == null
            it.fieldName() == null
            it.id() == null
            it.type() == null
            it.value() == null
            it.previousValue() == null
            it.via() == null
            it.subject() == null
            it.recipients() == null
        }
        verifyAll(built) {
            it == direct
            it.body() == "Comment body"
            it.fieldName() == "status"
            it.id() == 123L
            it.type() == "Change"
            it.value() == "open"
            it.previousValue() == "new"
            it.via() == expectedVia
            it.subject() == "Ticket updated"
            it.recipients() == expectedRecipients
        }
    }

    def "should update AuditEventsInner with record withers"() {
        given:
        Via updatedVia = new Via("mobile", null)
        AuditEventsInner initial = new AuditEventsInner()

        when:
        AuditEventsInner updated = initial
                .withBody("updated body")
                .withFieldName("priority")
                .withId(456L)
                .withType("Comment")
                .withValue("high")
                .withPreviousValue("normal")
                .withVia(updatedVia)
                .withSubject("Urgent update")
                .withRecipients([201L])

        then:
        verifyAll(updated) {
            it.body() == "updated body"
            it.fieldName() == "priority"
            it.id() == 456L
            it.type() == "Comment"
            it.value() == "high"
            it.previousValue() == "normal"
            it.via() == updatedVia
            it.subject() == "Urgent update"
            it.recipients() == [201L]
        }
    }

    def "should round-trip serialize and deserialize AuditEventsInner"() {
        given:
        Via eventVia = new Via("mail", null)
        AuditEventsInner event = new AuditEventsInner(
                "Test message",
                "assignee_id",
                789L,
                "Assignment",
                "user_1",
                "user_0",
                eventVia,
                "Assigned",
                [301L]
        )

        when:
        String json = objectMapper.writeValueAsString(event)
        Map map = objectMapper.readValue(json, Map.class)
        AuditEventsInner deserialized = objectMapper.readValue(json, AuditEventsInner.class)

        then:
        verifyAll(map) {
            containsKey("body")
            containsKey("field_name")
            containsKey("id")
            containsKey("type")
            containsKey("value")
            containsKey("previous_value")
            containsKey("via")
            containsKey("subject")
            containsKey("recipients")
        }
        verifyAll(deserialized) {
            it.body() == "Test message"
            it.fieldName() == "assignee_id"
            it.id() == 789L
            it.type() == "Assignment"
            it.value() == "user_1"
            it.previousValue() == "user_0"
            it.via() != null
            it.via().channel() == "mail"
            it.subject() == "Assigned"
            it.recipients() == [301L]
        }
    }

    def "should handle null fields in serialization"() {
        when:
        AuditEventsInner empty = new AuditEventsInner()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        AuditEventsInner deserialized = objectMapper.readValue(json, AuditEventsInner.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it.body() == null
            it.fieldName() == null
            it.id() == null
            it.type() == null
            it.value() == null
            it.previousValue() == null
            it.via() == null
            it.subject() == null
            it.recipients() == null
        }
    }
}
