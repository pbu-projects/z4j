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

class TicketCustomFieldSpec extends ModelSpec {

    def "should deserialize null value to TicketCustomField.Raw"() {
        given:
        String json = '{"id": 12345, "value": null}'

        when:
        TicketCustomField field = objectMapper.readValue(json, TicketCustomField.class)

        then:
        verifyAll(field) {
            it instanceof TicketCustomField.Raw
            id() == 12345L
            value() == null
        }
    }

    def "should deserialize text value to TicketCustomField.Text"() {
        given:
        String json = '{"id": 12345, "value": "some text"}'

        when:
        TicketCustomField field = objectMapper.readValue(json, TicketCustomField.class)

        then:
        verifyAll(field) {
            it instanceof TicketCustomField.Text
            id() == 12345L
            value() == "some text"
        }
    }

    def "should deserialize boolean value to TicketCustomField.Checkbox"() {
        given:
        String json = '{"id": 12345, "value": true}'

        when:
        TicketCustomField field = objectMapper.readValue(json, TicketCustomField.class)

        then:
        verifyAll(field) {
            it instanceof TicketCustomField.Checkbox
            id() == 12345L
            value() == true
        }
    }

    def "should deserialize integer number to TicketCustomField.Numeric"() {
        given:
        String json = '{"id": 12345, "value": 42}'

        when:
        TicketCustomField field = objectMapper.readValue(json, TicketCustomField.class)

        then:
        verifyAll(field) {
            it instanceof TicketCustomField.Numeric
            id() == 12345L
            value() == 42L
        }
    }

    def "should deserialize decimal number to TicketCustomField.Decimal"() {
        given:
        String json = '{"id": 12345, "value": 3.14}'

        when:
        TicketCustomField field = objectMapper.readValue(json, TicketCustomField.class)

        then:
        verifyAll(field) {
            it instanceof TicketCustomField.Decimal
            id() == 12345L
            value() == 3.14d
        }
    }

    def "should deserialize list of strings to TicketCustomField.TagList"() {
        given:
        String json = '{"id": 12345, "value": ["alpha", "beta"]}'

        when:
        TicketCustomField field = objectMapper.readValue(json, TicketCustomField.class)

        then:
        verifyAll(field) {
            it instanceof TicketCustomField.TagList
            id() == 12345L
            value() == ["alpha", "beta"]
        }
    }

    def "should deserialize unexpected types to TicketCustomField.Raw"() {
        given:
        String json = '{"id": 12345, "value": {"custom": "object"}}'

        when:
        TicketCustomField field = objectMapper.readValue(json, TicketCustomField.class)

        then:
        verifyAll(field) {
            it instanceof TicketCustomField.Raw
            id() == 12345L
            value() != null
        }
    }
}
