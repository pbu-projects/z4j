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

class EmailCCSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should construct EmailCC with Long userId"() {
        when:
        EmailCC emailCC = new EmailCC(EmailCCAllOfAction.PUT, "user@example.com", 12345L, "Test User")

        then:
        verifyAll(emailCC) {
            action == EmailCCAllOfAction.PUT
            userEmail == "user@example.com"
            userId == 12345L
            userName == "Test User"
        }
    }

    def "should support chaining setters on EmailCC"() {
        given:
        EmailCC emailCC = new EmailCC()

        when:
        emailCC.setAction(EmailCCAllOfAction.PUT)
                .setUserEmail("user@example.com")
                .setUserId(99999L)
                .setUserName("Test User")

        then:
        verifyAll(emailCC) {
            action == EmailCCAllOfAction.PUT
            userEmail == "user@example.com"
            userId == 99999L
            userName == "Test User"
        }
    }

    def "should handle null userId in constructor and setter without ambiguity"() {
        when:
        EmailCC emailCC = new EmailCC(EmailCCAllOfAction.PUT, "user@example.com", null, "Test User")
        emailCC.setUserId(null)

        then:
        verifyAll(emailCC) {
            userId == null
        }
    }

    def "should serialize EmailCC to JSON"() {
        given:
        EmailCC emailCC = new EmailCC(EmailCCAllOfAction.PUT, "user@example.com", 12345L, "Test User")

        when:
        String json = objectMapper.writeValueAsString(emailCC)
        Map map = objectMapper.readValue(json, Map.class)

        then:
        verifyAll(map) {
            it["action"] == "put"
            it["user_email"] == "user@example.com"
            it["user_id"] == 12345
            it["user_name"] == "Test User"
        }
    }

    def "should deserialize EmailCC from JSON with numeric user_id"() {
        given:
        String json = '{"action":"put","user_email":"user@example.com","user_id":12345,"user_name":"Test User"}'

        when:
        EmailCC emailCC = objectMapper.readValue(json, EmailCC.class)

        then:
        verifyAll(emailCC) {
            action == EmailCCAllOfAction.PUT
            userEmail == "user@example.com"
            userId == 12345L
            userName == "Test User"
        }
    }

    def "should deserialize EmailCC from JSON with string user_id"() {
        given:
        String json = '{"action":"put","user_email":"user@example.com","user_id":"12345","user_name":"Test User"}'

        when:
        EmailCC emailCC = objectMapper.readValue(json, EmailCC.class)

        then:
        verifyAll(emailCC) {
            action == EmailCCAllOfAction.PUT
            userEmail == "user@example.com"
            userId == 12345L
            userName == "Test User"
        }
    }
}
