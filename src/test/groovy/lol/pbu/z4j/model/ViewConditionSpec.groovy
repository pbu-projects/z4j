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

import io.micronaut.context.ApplicationContext
import io.micronaut.serde.ObjectMapper
import io.micronaut.serde.exceptions.SerdeException
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ViewConditionSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext context = ApplicationContext.run()

    @Shared
    ObjectMapper objectMapper = context.getBean(ObjectMapper.class)

    def "should create ViewCondition with constructors and builder"() {
        when:
        ViewCondition direct = new ViewCondition("status", "less_than", "solved")
        ViewCondition typed = new ViewCondition("status", "less_than", ViewConditionValue.of("solved"))
        ViewCondition noArg = new ViewCondition()
        ViewCondition built = ViewConditionBuilder.builder()
                .field("status")
                .operator("less_than")
                .value(ViewConditionValue.of("solved"))
                .build()

        then:
        verifyAll(direct) {
            field() == "status"
            operator() == "less_than"
            value() == ViewConditionValue.of("solved")
            it == typed
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
            value() == ViewConditionValue.of("solved")
        }
    }

    def "should update ViewCondition using withers"() {
        given:
        ViewCondition initial = new ViewCondition()

        when:
        ViewCondition updated = initial
                .withField("priority")
                .withOperator("is")
                .withValue(ViewConditionValue.of("urgent"))

        then:
        verifyAll(updated) {
            field() == "priority"
            operator() == "is"
            value() == ViewConditionValue.of("urgent")
        }
    }

    def "should round-trip serialize and deserialize ViewCondition with typed values"() {
        given:
        ViewCondition condition = new ViewCondition(condField, condOperator, condValue)

        when:
        String json = objectMapper.writeValueAsString(condition)
        Map map = objectMapper.readValue(json, Map.class)
        ViewCondition deserialized = objectMapper.readValue(json, ViewCondition.class)

        then:
        verifyAll(map) {
            get("field") == condField
            get("operator") == condOperator
            get("value") == expectedJsonValue
        }
        verifyAll(deserialized) {
            field() == condField
            operator() == condOperator
            value() == condValue
        }

        where:
        condField      | condOperator | condValue                                      | expectedJsonValue
        "group_id"     | "is"         | ViewConditionValue.of("24000932")              | "24000932"
        "current_tags" | "includes"   | ViewConditionValue.of(["vip", "enterprise"])   | ["vip", "enterprise"]
        "is_public"    | "is"         | ViewConditionValue.of(true)                    | true
        "priority"     | "is"         | ViewConditionValue.of(42L)                     | 42
        "ticket_id"    | "is"         | ViewConditionValue.of(24000932000L)            | 24000932000L
        "assignee_id"  | "is"         | null                                           | null
    }

    def "should throw SerdeException on unsupported ViewConditionValue JSON types"() {
        when:
        objectMapper.readValue(json, ViewCondition.class)

        then:
        thrown(SerdeException)

        where:
        json << [
                '{"field":"status","operator":"is","value":{"nested":"object"}}',
                '{"field":"current_tags","operator":"includes","value":["vip", 123]}'
        ]
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
