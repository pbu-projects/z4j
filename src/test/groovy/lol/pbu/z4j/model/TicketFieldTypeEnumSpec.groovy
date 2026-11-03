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
import spock.lang.Unroll

class TicketFieldTypeEnumSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#fieldType toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            fieldType.toString() == expectedString
            fieldType.getValue() == expectedString
        }

        where:
        fieldType                               | expectedString
        TicketFieldTypeEnum.TEXT                | "text"
        TicketFieldTypeEnum.TEXT_AREA           | "textarea"
        TicketFieldTypeEnum.CHECKBOX            | "checkbox"
        TicketFieldTypeEnum.DATE                | "date"
        TicketFieldTypeEnum.INTEGER             | "integer"
        TicketFieldTypeEnum.DECIMAL             | "decimal"
        TicketFieldTypeEnum.REGEXP              | "regexp"
        TicketFieldTypeEnum.PARTIAL_CREDIT_CARD | "partial_credit_card"
        TicketFieldTypeEnum.MULTI_SELECT        | "multi-select"
        TicketFieldTypeEnum.TAGGER              | "tagger"
        TicketFieldTypeEnum.LOOKUP              | "lookup"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedFieldType"() {
        when:
        String json = objectMapper.writeValueAsString(expectedFieldType)
        TicketFieldTypeEnum deserialized = objectMapper.readValue(json, TicketFieldTypeEnum.class)

        then:
        verifyAll {
            TicketFieldTypeEnum.fromValue(stringValue) == expectedFieldType
            json == "\"${stringValue}\""
            deserialized == expectedFieldType
        }

        where:
        stringValue           | expectedFieldType
        "text"                | TicketFieldTypeEnum.TEXT
        "textarea"            | TicketFieldTypeEnum.TEXT_AREA
        "checkbox"            | TicketFieldTypeEnum.CHECKBOX
        "date"                | TicketFieldTypeEnum.DATE
        "integer"             | TicketFieldTypeEnum.INTEGER
        "decimal"             | TicketFieldTypeEnum.DECIMAL
        "regexp"              | TicketFieldTypeEnum.REGEXP
        "partial_credit_card" | TicketFieldTypeEnum.PARTIAL_CREDIT_CARD
        "multi-select"        | TicketFieldTypeEnum.MULTI_SELECT
        "tagger"              | TicketFieldTypeEnum.TAGGER
        "lookup"              | TicketFieldTypeEnum.LOOKUP
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketFieldTypeEnum.fromValue("invalid_field_type")

        then:
        thrown(IllegalArgumentException)
    }
}
