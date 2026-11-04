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

class CollaboratorSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Collaborator with canonical constructor and builder"() {
        when:
        Collaborator direct = new Collaborator("collab@example.com", "Collab Person")
        Collaborator built = CollaboratorBuilder.builder()
                .email("collab@example.com")
                .name("Collab Person")
                .build()

        then:
        verifyAll(direct) {
            email() == "collab@example.com"
            name() == "Collab Person"
        }
        verifyAll(built) {
            it == direct
            email() == "collab@example.com"
            name() == "Collab Person"
        }
    }

    def "should update Collaborator using record withers"() {
        given:
        Collaborator original = new Collaborator("first@example.com", "First Name")

        when:
        Collaborator updated = original
                .withEmail("second@example.com")
                .withName("Second Name")

        then:
        verifyAll(original) {
            email() == "first@example.com"
            name() == "First Name"
        }
        verifyAll(updated) {
            email() == "second@example.com"
            name() == "Second Name"
        }
    }

    def "should round-trip serialize and deserialize Collaborator"() {
        given:
        Collaborator collaborator = new Collaborator("team@example.com", "Team Member")

        when:
        String json = objectMapper.writeValueAsString(collaborator)
        Map map = objectMapper.readValue(json, Map.class)
        Collaborator deserialized = objectMapper.readValue(json, Collaborator.class)

        then:
        verifyAll(map) {
            get("email") == "team@example.com"
            get("name") == "Team Member"
        }
        verifyAll(deserialized) {
            it == collaborator
            email() == "team@example.com"
            name() == "Team Member"
        }
    }

    def "should handle null components in Collaborator constructor, wither, and serde"() {
        when:
        Collaborator empty = new Collaborator(null, null)
        Collaborator built = CollaboratorBuilder.builder().build()
        Collaborator cleared = new Collaborator("a@example.com", "a").withEmail(null).withName(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        Collaborator deserialized = objectMapper.readValue(json, Collaborator.class)

        then:
        verifyAll(empty) {
            email() == null
            name() == null
        }
        verifyAll(built) {
            it == empty
            email() == null
            name() == null
        }
        verifyAll(cleared) {
            it == empty
            email() == null
            name() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            email() == null
            name() == null
        }
    }
}
