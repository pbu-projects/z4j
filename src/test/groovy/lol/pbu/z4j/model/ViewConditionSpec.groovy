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
import spock.lang.Shared
import spock.lang.Specification

class ViewConditionSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewCondition with constructors and builder"() {
        when:
        ViewCondition direct = new ViewCondition("status", "less_than", "solved")
        ViewCondition noArg = new ViewCondition()
        ViewCondition built = ViewConditionBuilder.builder()
                .field("status")
                .operator("less_than")
                .value("solved")
                .build()

        then:
        verifyAll(direct) {
            field() == "status"
            operator() == "less_than"
            value() == "solved"
        }
        verifyAll(noArg) {
            field() == null
            operator() == null
            value() == null
        }
        verifyAll(built) {
            it == direct
            field() == "status"
            operator() == "less_than"
            value() == "solved"
        }
    }

    def "should update ViewCondition using withers"() {
        given:
        ViewCondition initial = new ViewCondition()

        when:
        ViewCondition updated = initial
                .withField("priority")
                .withOperator("is")
                .withValue("urgent")

        then:
        verifyAll(updated) {
            field() == "priority"
            operator() == "is"
            value() == "urgent"
        }
    }

    def "should round-trip serialize and deserialize ViewCondition"() {
        given:
        ViewCondition condition = new ViewCondition("group_id", "is", "24000932")

        when:
        String json = objectMapper.writeValueAsString(condition)
        Map map = objectMapper.readValue(json, Map.class)
        ViewCondition deserialized = objectMapper.readValue(json, ViewCondition.class)

        then:
        verifyAll(map) {
            get("field") == "group_id"
            get("operator") == "is"
            get("value") == "24000932"
        }
        verifyAll(deserialized) {
            it == condition
            field() == "group_id"
            operator() == "is"
            value() == "24000932"
        }
    }

    def "should verify ViewCondition constants"() {
        expect:
        verifyAll {
            ViewCondition.JSON_PROPERTY_FIELD == "field"
            ViewCondition.JSON_PROPERTY_OPERATOR == "operator"
            ViewCondition.JSON_PROPERTY_VALUE == "value"
        }
    }
}
