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

class ViewRestrictionSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewRestriction with constructors and builder"() {
        when:
        ViewRestriction direct = new ViewRestriction("Group", 10L, [10L, 20L])
        ViewRestriction singleId = new ViewRestriction("User", 42L)
        ViewRestriction multipleIds = new ViewRestriction("Group", [100L, 200L])
        ViewRestriction noArg = new ViewRestriction()
        ViewRestriction built = ViewRestrictionBuilder.builder()
                .type("Group")
                .id(10L)
                .ids([10L, 20L])
                .build()

        then:
        verifyAll(direct) {
            type() == "Group"
            id() == 10L
            ids() == [10L, 20L]
        }
        verifyAll(singleId) {
            type() == "User"
            id() == 42L
            ids() == null
        }
        verifyAll(multipleIds) {
            type() == "Group"
            id() == null
            ids() == [100L, 200L]
        }
        verifyAll(noArg) {
            type() == null
            id() == null
            ids() == null
        }
        verifyAll(built) {
            it == direct
            type() == "Group"
            id() == 10L
        }
    }

    def "should update ViewRestriction using withers and addIdsItem"() {
        given:
        ViewRestriction initial = new ViewRestriction()

        when:
        ViewRestriction withFirst = initial.withType("Group").addIdsItem(1L)
        ViewRestriction withSecond = withFirst.addIdsItem(2L).withId(1L)

        then:
        verifyAll(withFirst) {
            type() == "Group"
            ids() == [1L]
        }
        verifyAll(withSecond) {
            type() == "Group"
            id() == 1L
            ids() == [1L, 2L]
        }
    }

    def "should round-trip serialize and deserialize ViewRestriction"() {
        given:
        ViewRestriction restriction = new ViewRestriction("Group", [10052L, 10057L])

        when:
        String json = objectMapper.writeValueAsString(restriction)
        Map map = objectMapper.readValue(json, Map.class)
        ViewRestriction deserialized = objectMapper.readValue(json, ViewRestriction.class)

        then:
        verifyAll(map) {
            get("type") == "Group"
            get("ids") == [10052, 10057]
        }
        verifyAll(deserialized) {
            it == restriction
            type() == "Group"
            ids() == [10052L, 10057L]
        }
    }

    def "should verify ViewRestriction constants"() {
        expect:
        verifyAll {
            ViewRestriction.JSON_PROPERTY_TYPE == "type"
            ViewRestriction.JSON_PROPERTY_ID == "id"
            ViewRestriction.JSON_PROPERTY_IDS == "ids"
        }
    }
}
