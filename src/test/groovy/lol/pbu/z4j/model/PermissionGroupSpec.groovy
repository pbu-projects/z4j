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

class PermissionGroupSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create PermissionGroup with constructors and builder"() {
        when:
        PermissionGroup direct = new PermissionGroup(
                true,
                "2026-10-01T10:00:00Z",
                [10L, 20L],
                101L,
                "Admins",
                [30L],
                "2026-10-02T12:00:00Z"
        )
        PermissionGroup noArg = new PermissionGroup()
        PermissionGroup nameOnly = new PermissionGroup("Staff")
        PermissionGroup custom = new PermissionGroup("Editors", [100L], [200L])
        PermissionGroup built = PermissionGroupBuilder.builder()
                .builtIn(true)
                .createdAt("2026-10-01T10:00:00Z")
                .edit([10L, 20L])
                .id(101L)
                .name("Admins")
                .publish([30L])
                .updatedAt("2026-10-02T12:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            builtIn() == true
            createdAt() == "2026-10-01T10:00:00Z"
            edit() == [10L, 20L]
            id() == 101L
            name() == "Admins"
            publish() == [30L]
            updatedAt() == "2026-10-02T12:00:00Z"
        }
        verifyAll(noArg) {
            builtIn() == null
            createdAt() == null
            edit() == null
            id() == null
            name() == null
            publish() == null
            updatedAt() == null
        }
        verifyAll(nameOnly) {
            name() == "Staff"
            edit() == null
            publish() == null
        }
        verifyAll(custom) {
            name() == "Editors"
            edit() == [100L]
            publish() == [200L]
        }
        verifyAll(built) {
            it == direct
            name() == "Admins"
            id() == 101L
        }
    }

    def "should update PermissionGroup using withers"() {
        given:
        PermissionGroup initial = new PermissionGroup()

        when:
        PermissionGroup updated = initial
                .withBuiltIn(false)
                .withCreatedAt("2026-09-01T08:00:00Z")
                .withEdit([5L])
                .withId(505L)
                .withName("Custom Tier")
                .withPublish([6L])
                .withUpdatedAt("2026-09-02T09:00:00Z")

        then:
        verifyAll(updated) {
            builtIn() == false
            createdAt() == "2026-09-01T08:00:00Z"
            edit() == [5L]
            id() == 505L
            name() == "Custom Tier"
            publish() == [6L]
            updatedAt() == "2026-09-02T09:00:00Z"
        }
    }

    def "should add items to edit and publish lists"() {
        given:
        PermissionGroup group = new PermissionGroup("Agents")

        when: "adding to null lists"
        PermissionGroup withFirstEdit = group.addEditItem(11L)
        PermissionGroup withFirstPublish = group.addPublishItem(22L)

        then:
        verifyAll(withFirstEdit) {
            edit() != null
            edit().size() == 1
            edit().first() == 11L
        }
        verifyAll(withFirstPublish) {
            publish() != null
            publish().size() == 1
            publish().first() == 22L
        }

        when: "adding to populated lists"
        PermissionGroup withSecondEdit = withFirstEdit.addEditItem(12L)
        PermissionGroup withSecondPublish = withFirstPublish.addPublishItem(23L)

        then:
        verifyAll(withSecondEdit) {
            edit().size() == 2
            edit() == [11L, 12L]
        }
        verifyAll(withSecondPublish) {
            publish().size() == 2
            publish() == [22L, 23L]
        }
    }

    def "should round-trip serialize and deserialize PermissionGroup"() {
        given:
        PermissionGroup group = new PermissionGroup()
                .withId(707L)
                .withName("Support Leads")
                .withBuiltIn(true)
                .withEdit([1L])
                .withPublish([2L])

        when:
        String json = objectMapper.writeValueAsString(group)
        Map map = objectMapper.readValue(json, Map.class)
        PermissionGroup deserialized = objectMapper.readValue(json, PermissionGroup.class)

        then:
        verifyAll(map) {
            get("id") == 707
            get("name") == "Support Leads"
            get("built_in") == true
            get("edit") == [1]
            get("publish") == [2]
        }
        verifyAll(deserialized) {
            it == group
            id() == 707L
            name() == "Support Leads"
            builtIn() == true
            edit() == [1L]
            publish() == [2L]
        }
    }

    def "should verify PermissionGroup constants"() {
        expect:
        verifyAll {
            PermissionGroup.JSON_PROPERTY_BUILT_IN == "built_in"
            PermissionGroup.JSON_PROPERTY_CREATED_AT == "created_at"
            PermissionGroup.JSON_PROPERTY_EDIT == "edit"
            PermissionGroup.JSON_PROPERTY_ID == "id"
            PermissionGroup.JSON_PROPERTY_NAME == "name"
            PermissionGroup.JSON_PROPERTY_PUBLISH == "publish"
            PermissionGroup.JSON_PROPERTY_UPDATED_AT == "updated_at"
        }
    }
}
