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

class PermissionGroupsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PermissionGroupsResponse with canonical constructor and builder"() {
        given:
        PermissionGroup item = new PermissionGroup("Managers").withId(101L)
        List<PermissionGroup> list = [item]

        when:
        PermissionGroupsResponse direct = new PermissionGroupsResponse(list)
        PermissionGroupsResponse built = PermissionGroupsResponseBuilder.builder()
                .permissionGroups(list)
                .build()

        then:
        verifyAll(direct) {
            permissionGroups() == list
            permissionGroups().size() == 1
            permissionGroups().first().id == 101L
            permissionGroups().first().name == "Managers"
        }
        verifyAll(built) {
            permissionGroups() == list
            permissionGroups().size() == 1
            permissionGroups().first().id == 101L
            permissionGroups().first().name == "Managers"
        }
    }

    def "should update permissionGroups using record wither and addPermissionGroupsItem method"() {
        given:
        PermissionGroup first = new PermissionGroup("Tier 1").withId(1L)
        PermissionGroup second = new PermissionGroup("Tier 2").withId(2L)
        PermissionGroupsResponse response = new PermissionGroupsResponse([first])

        when:
        PermissionGroupsResponse withNewList = response.withPermissionGroups([second])
        PermissionGroupsResponse withAdded = response.addPermissionGroupsItem(second)
        PermissionGroupsResponse fromNull = new PermissionGroupsResponse(null).addPermissionGroupsItem(first)

        then:
        verifyAll(response) {
            permissionGroups().size() == 1
            permissionGroups().first().id == 1L
        }
        verifyAll(withNewList) {
            permissionGroups().size() == 1
            permissionGroups().first().id == 2L
        }
        verifyAll(withAdded) {
            permissionGroups().size() == 2
            permissionGroups().first().id == 1L
            permissionGroups().last().id == 2L
        }
        verifyAll(fromNull) {
            permissionGroups().size() == 1
            permissionGroups().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize PermissionGroupsResponse"() {
        given:
        PermissionGroup item = new PermissionGroup("Admins").withId(555L)
        PermissionGroupsResponse response = new PermissionGroupsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PermissionGroupsResponse deserialized = objectMapper.readValue(json, PermissionGroupsResponse.class)

        then:
        verifyAll(map) {
            containsKey("permission_groups")
            get("permission_groups") instanceof List
            ((List) get("permission_groups")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            permissionGroups() != null
            permissionGroups().size() == 1
            permissionGroups().first().id == 555L
            permissionGroups().first().name == "Admins"
        }
    }

    def "should handle null permissionGroups component in constructor, wither, and serde"() {
        when:
        PermissionGroupsResponse response = new PermissionGroupsResponse(null)
        PermissionGroup item = new PermissionGroup("Staff").withId(99L)
        PermissionGroupsResponse updated = response.withPermissionGroups([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PermissionGroupsResponse deserialized = objectMapper.readValue(json, PermissionGroupsResponse.class)

        then:
        verifyAll(response) {
            permissionGroups() == null
        }
        verifyAll(updated) {
            permissionGroups() != null
            permissionGroups().size() == 1
            permissionGroups().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("permission_groups")
        }
        verifyAll(deserialized) {
            it == response
            permissionGroups() == null
        }
    }
}
