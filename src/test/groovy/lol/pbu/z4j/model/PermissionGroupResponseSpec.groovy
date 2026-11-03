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

class PermissionGroupResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PermissionGroupResponse with canonical constructor and builder"() {
        given:
        PermissionGroup expected = new PermissionGroup("Managers").setId(404L)

        when:
        PermissionGroupResponse direct = new PermissionGroupResponse(expected)
        PermissionGroupResponse built = PermissionGroupResponseBuilder.builder().permissionGroup(expected).build()

        then:
        verifyAll(direct) {
            permissionGroup == expected
            permissionGroup.id == 404L
            permissionGroup.name == "Managers"
        }
        verifyAll(built) {
            permissionGroup == expected
            permissionGroup.id == 404L
            permissionGroup.name == "Managers"
        }
    }

    def "should update permission group using record wither"() {
        given:
        PermissionGroup originalGroup = new PermissionGroup("Tier 1").setId(1L)
        PermissionGroup newGroup = new PermissionGroup("Tier 2").setId(2L)
        PermissionGroupResponse response = new PermissionGroupResponse(originalGroup)

        when:
        PermissionGroupResponse updated = response.withPermissionGroup(newGroup)

        then:
        verifyAll(response) {
            permissionGroup == originalGroup
            permissionGroup.name == "Tier 1"
        }
        verifyAll(updated) {
            permissionGroup == newGroup
            permissionGroup.name == "Tier 2"
        }
    }

    def "should round-trip serialize and deserialize PermissionGroupResponse"() {
        given:
        PermissionGroup expected = new PermissionGroup("Admins").setId(999L)
        PermissionGroupResponse response = new PermissionGroupResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        PermissionGroupResponse deserialized = objectMapper.readValue(json, PermissionGroupResponse.class)

        then:
        verifyAll(deserialized) {
            it == response
            permissionGroup != null
            permissionGroup.id == 999L
            permissionGroup.name == "Admins"
        }
    }
}
