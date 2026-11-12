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

class ViewConditionsSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewConditions with constructors and builder"() {
        given:
        ViewCondition allCond = new ViewCondition("status", "less_than", "solved")
        ViewCondition anyCond = new ViewCondition("priority", "is", "high")

        when:
        ViewConditions direct = new ViewConditions([allCond], [anyCond])
        ViewConditions allOnly = new ViewConditions([allCond])
        ViewConditions noArg = new ViewConditions()
        ViewConditions built = ViewConditionsBuilder.builder()
                .all([allCond])
                .any([anyCond])
                .build()

        then:
        verifyAll(direct) {
            it.all() == [allCond]
            it.any() == [anyCond]
        }
        verifyAll(allOnly) {
            it.all() == [allCond]
            it.any() == null
        }
        verifyAll(noArg) {
            it.all() == null
            it.any() == null
        }
        verifyAll(built) {
            it == direct
            it.all() == [allCond]
            it.any() == [anyCond]
        }
    }

    def "should update ViewConditions using withers and add helpers"() {
        given:
        ViewCondition c1 = new ViewCondition("status", "is", "open")
        ViewCondition c2 = new ViewCondition("priority", "is", "urgent")
        ViewConditions initial = new ViewConditions()

        when:
        ViewConditions withFirst = initial.addAllItem(c1).addAnyItem(c2)
        ViewConditions withSecond = withFirst.addAllItem(c2).addAnyItem(c1)
        ViewConditions replaced = withSecond.withAll([c2]).withAny([c1])

        then:
        verifyAll(withFirst) {
            it.all() == [c1]
            it.any() == [c2]
        }
        verifyAll(withSecond) {
            it.all() == [c1, c2]
            it.any() == [c2, c1]
        }
        verifyAll(replaced) {
            it.all() == [c2]
            it.any() == [c1]
        }
    }

    def "should round-trip serialize and deserialize ViewConditions"() {
        given:
        ViewCondition allCond = new ViewCondition("status", "is", "open")
        ViewCondition anyCond = new ViewCondition("current_tags", "includes", "vip")
        ViewConditions conditions = new ViewConditions([allCond], [anyCond])

        when:
        String json = objectMapper.writeValueAsString(conditions)
        Map map = objectMapper.readValue(json, Map.class)
        ViewConditions deserialized = objectMapper.readValue(json, ViewConditions.class)

        then:
        verifyAll(map) {
            containsKey("all")
            containsKey("any")
        }
        verifyAll(deserialized) {
            it == conditions
            it.all().size() == 1
            it.all().first().field() == "status"
            it.any().size() == 1
            it.any().first().value() == "vip"
        }
    }

    def "should verify ViewConditions constants"() {
        expect:
        verifyAll {
            ViewConditions.JSON_PROPERTY_ALL == "all"
            ViewConditions.JSON_PROPERTY_ANY == "any"
        }
    }
}
