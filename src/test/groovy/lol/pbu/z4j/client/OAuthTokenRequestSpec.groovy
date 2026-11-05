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
package lol.pbu.z4j.client

import io.micronaut.serde.ObjectMapper
import spock.lang.Specification

class OAuthTokenRequestSpec extends Specification {

    def "default constructor initializes default grant type"() {
        when:
        def request = new OAuthTokenRequest()

        then:
        verifyAll(request) {
            grantType == "client_credentials"
            grantType() == "client_credentials"
            clientId == null
            clientSecret == null
            scope == null
        }
    }

    def "three argument constructor sets client details with default grant type"() {
        when:
        def request = new OAuthTokenRequest("test-id", "test-secret", "read write")

        then:
        verifyAll(request) {
            grantType == "client_credentials"
            clientId == "test-id"
            clientSecret == "test-secret"
            scope == "read write"
        }
    }

    def "builder and withers allow full customization"() {
        when:
        def request = OAuthTokenRequest.builder()
                .grantType("custom_grant")
                .clientId("builder-id")
                .clientSecret("builder-secret")
                .scope("admin")
                .build()

        and:
        def modified = request.withClientId("updated-id")

        then:
        verifyAll(request) {
            grantType == "custom_grant"
            clientId == "builder-id"
            clientSecret == "builder-secret"
            scope == "admin"
        }
        verifyAll(modified) {
            grantType == "custom_grant"
            clientId == "updated-id"
            clientSecret == "builder-secret"
            scope == "admin"
        }
    }

    def "serializes to JSON with expected property names"() {
        given:
        def mapper = ObjectMapper.getDefault()
        def request = new OAuthTokenRequest("id-123", "secret-456", "tickets:read")

        when:
        def json = mapper.writeValueAsString(request)

        then:
        verifyAll {
            json.contains('"grant_type":"client_credentials"')
            json.contains('"client_id":"id-123"')
            json.contains('"client_secret":"secret-456"')
            json.contains('"scope":"tickets:read"')
        }
    }
}
