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

class OrganizationSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Organization with constructors and builder"() {
        when:
        Organization direct = new Organization(101L)
        Organization noArg = new Organization()
        Organization built = OrganizationBuilder.builder()
                .id(101L)
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
        }
        verifyAll(noArg) {
            id() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
        }
    }

    def "should update Organization using withers"() {
        given:
        Organization initial = new Organization()

        when:
        Organization updated = initial.withId(202L)

        then:
        verifyAll(updated) {
            id() == 202L
        }
    }

    def "should round-trip serialize and deserialize Organization"() {
        given:
        Organization org = new Organization().withId(303L)

        when:
        String json = objectMapper.writeValueAsString(org)
        Map map = objectMapper.readValue(json, Map.class)
        Organization deserialized = objectMapper.readValue(json, Organization.class)

        then:
        verifyAll(map) {
            get("id") == 303
        }
        verifyAll(deserialized) {
            it == org
            id() == 303L
        }
    }

    def "should verify Organization constants"() {
        expect:
        verifyAll {
            Organization.JSON_PROPERTY_ID == "id"
        }
    }
}
