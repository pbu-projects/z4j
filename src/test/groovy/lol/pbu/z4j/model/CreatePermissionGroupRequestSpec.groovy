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

class CreatePermissionGroupRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CreatePermissionGroupRequest with canonical constructor and builder"() {
        given:
        PermissionGroup expected = new PermissionGroup("Editors").setId(101L)

        when:
        CreatePermissionGroupRequest direct = new CreatePermissionGroupRequest(expected)
        CreatePermissionGroupRequest built = CreatePermissionGroupRequestBuilder.builder().permissionGroup(expected).build()

        then:
        verifyAll(direct) {
            permissionGroup() == expected
            permissionGroup().id == 101L
            permissionGroup().name == "Editors"
        }
        verifyAll(built) {
            it == direct
            permissionGroup() == expected
            permissionGroup().id == 101L
            permissionGroup().name == "Editors"
        }
    }

    def "should update permissionGroup using record wither"() {
        given:
        PermissionGroup originalGroup = new PermissionGroup("Reviewers").setId(1L)
        PermissionGroup newGroup = new PermissionGroup("Publishers").setId(2L)
        CreatePermissionGroupRequest request = new CreatePermissionGroupRequest(originalGroup)

        when:
        CreatePermissionGroupRequest updated = request.withPermissionGroup(newGroup)

        then:
        verifyAll(request) {
            permissionGroup() == originalGroup
            permissionGroup().name == "Reviewers"
        }
        verifyAll(updated) {
            permissionGroup() == newGroup
            permissionGroup().name == "Publishers"
        }
    }

    def "should round-trip serialize and deserialize CreatePermissionGroupRequest"() {
        given:
        PermissionGroup expected = new PermissionGroup("Admins").setId(555L)
        CreatePermissionGroupRequest request = new CreatePermissionGroupRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreatePermissionGroupRequest deserialized = objectMapper.readValue(json, CreatePermissionGroupRequest.class)

        then:
        verifyAll(map) {
            containsKey("permission_group")
            get("permission_group") instanceof Map
            ((Map) get("permission_group")).id == 555
            ((Map) get("permission_group")).name == "Admins"
        }
        verifyAll(deserialized) {
            it == request
            permissionGroup() != null
            permissionGroup().id == 555L
            permissionGroup().name == "Admins"
        }
    }

    def "should handle null permissionGroup component in constructor and wither"() {
        when:
        CreatePermissionGroupRequest request = new CreatePermissionGroupRequest(null)
        PermissionGroup replacement = new PermissionGroup("Created").setId(99L)
        CreatePermissionGroupRequest updated = request.withPermissionGroup(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreatePermissionGroupRequest deserialized = objectMapper.readValue(json, CreatePermissionGroupRequest.class)

        then:
        verifyAll(request) {
            permissionGroup() == null
        }
        verifyAll(updated) {
            permissionGroup() == replacement
            permissionGroup().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("permission_group")
        }
        verifyAll(deserialized) {
            it == request
            permissionGroup() == null
        }
    }
}
