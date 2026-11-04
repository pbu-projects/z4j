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

class EmailCCSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create EmailCC with canonical constructor and builder"() {
        when:
        EmailCC direct = new EmailCC(EmailCCAllOfAction.PUT, "user@example.com", 12345L, "Test User")
        EmailCC built = EmailCCBuilder.builder()
                .action(EmailCCAllOfAction.PUT)
                .userEmail("user@example.com")
                .userId(12345L)
                .userName("Test User")
                .build()

        then:
        verifyAll(direct) {
            action() == EmailCCAllOfAction.PUT
            userEmail() == "user@example.com"
            userId() == 12345L
            userName() == "Test User"
        }
        verifyAll(built) {
            it == direct
            action() == EmailCCAllOfAction.PUT
            userEmail() == "user@example.com"
            userId() == 12345L
            userName() == "Test User"
        }
    }

    def "should update EmailCC using record withers"() {
        given:
        EmailCC original = new EmailCC(EmailCCAllOfAction.PUT, "user@example.com", 12345L, "Test User")

        when:
        EmailCC updated = original
                .withAction(EmailCCAllOfAction.DELETE)
                .withUserEmail("other@example.com")
                .withUserId(67890L)
                .withUserName("Other User")

        then:
        verifyAll(original) {
            action() == EmailCCAllOfAction.PUT
            userEmail() == "user@example.com"
            userId() == 12345L
            userName() == "Test User"
        }
        verifyAll(updated) {
            action() == EmailCCAllOfAction.DELETE
            userEmail() == "other@example.com"
            userId() == 67890L
            userName() == "Other User"
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
            get("action") == "put"
            get("user_email") == "user@example.com"
            get("user_id") == 12345
            get("user_name") == "Test User"
        }
    }

    def "should deserialize EmailCC from JSON with numeric user_id"() {
        given:
        String json = '{"action":"put","user_email":"user@example.com","user_id":12345,"user_name":"Test User"}'

        when:
        EmailCC emailCC = objectMapper.readValue(json, EmailCC.class)

        then:
        verifyAll(emailCC) {
            action() == EmailCCAllOfAction.PUT
            userEmail() == "user@example.com"
            userId() == 12345L
            userName() == "Test User"
        }
    }

    def "should deserialize EmailCC from JSON with string user_id"() {
        given:
        String json = '{"action":"put","user_email":"user@example.com","user_id":"12345","user_name":"Test User"}'

        when:
        EmailCC emailCC = objectMapper.readValue(json, EmailCC.class)

        then:
        verifyAll(emailCC) {
            action() == EmailCCAllOfAction.PUT
            userEmail() == "user@example.com"
            userId() == 12345L
            userName() == "Test User"
        }
    }

    def "should handle null components in EmailCC constructor, wither, and serde"() {
        when:
        EmailCC empty = new EmailCC(null, null, null, null)
        EmailCC built = EmailCCBuilder.builder().build()
        EmailCC cleared = new EmailCC(EmailCCAllOfAction.PUT, "user@example.com", 12345L, "Test User")
                .withAction(null)
                .withUserEmail(null)
                .withUserId(null)
                .withUserName(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        EmailCC deserialized = objectMapper.readValue(json, EmailCC.class)

        then:
        verifyAll(empty) {
            action() == null
            userEmail() == null
            userId() == null
            userName() == null
        }
        verifyAll(built) {
            it == empty
            action() == null
            userEmail() == null
            userId() == null
            userName() == null
        }
        verifyAll(cleared) {
            it == empty
            action() == null
            userEmail() == null
            userId() == null
            userName() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            action() == null
            userEmail() == null
            userId() == null
            userName() == null
        }
    }
}
