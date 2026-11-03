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

class OAuthTokenResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create OAuthTokenResponse with canonical constructor and builder"() {
        when:
        OAuthTokenResponse direct = new OAuthTokenResponse("tok-101", "Bearer", 3600L, "hc:read")
        OAuthTokenResponse built = OAuthTokenResponseBuilder.builder()
                .accessToken("tok-101")
                .tokenType("Bearer")
                .expiresIn(3600L)
                .scope("hc:read")
                .build()

        then:
        verifyAll(direct) {
            accessToken() == "tok-101"
            tokenType() == "Bearer"
            expiresIn() == 3600L
            scope() == "hc:read"
        }
        verifyAll(built) {
            it == direct
            accessToken() == "tok-101"
            tokenType() == "Bearer"
            expiresIn() == 3600L
            scope() == "hc:read"
        }
    }

    def "should update components using record withers"() {
        given:
        OAuthTokenResponse original = new OAuthTokenResponse("old-tok", "Bearer", 1800L, "hc:read")

        when:
        OAuthTokenResponse updated = original
                .withAccessToken("new-tok")
                .withTokenType("MAC")
                .withExpiresIn(7200L)
                .withScope("tickets:write")

        then:
        verifyAll(original) {
            accessToken() == "old-tok"
            tokenType() == "Bearer"
            expiresIn() == 1800L
            scope() == "hc:read"
        }
        verifyAll(updated) {
            accessToken() == "new-tok"
            tokenType() == "MAC"
            expiresIn() == 7200L
            scope() == "tickets:write"
        }
    }

    def "should round-trip serialize and deserialize OAuthTokenResponse"() {
        given:
        OAuthTokenResponse response = new OAuthTokenResponse("tok-555", "Bearer", 900L, "read write")

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        OAuthTokenResponse deserialized = objectMapper.readValue(json, OAuthTokenResponse.class)

        then:
        verifyAll(map) {
            get("access_token") == "tok-555"
            get("token_type") == "Bearer"
            get("expires_in") == 900
            get("scope") == "read write"
        }
        verifyAll(deserialized) {
            it == response
            accessToken() == "tok-555"
            tokenType() == "Bearer"
            expiresIn() == 900L
            scope() == "read write"
        }
    }

    def "should handle null components in constructor, wither, and serde"() {
        when:
        OAuthTokenResponse response = new OAuthTokenResponse(null, null, null, null)
        OAuthTokenResponse updated = response.withAccessToken("tok-99")
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        OAuthTokenResponse deserialized = objectMapper.readValue(json, OAuthTokenResponse.class)

        then:
        verifyAll(response) {
            accessToken() == null
            tokenType() == null
            expiresIn() == null
            scope() == null
        }
        verifyAll(updated) {
            accessToken() == "tok-99"
            tokenType() == null
            expiresIn() == null
            scope() == null
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("access_token")
            !containsKey("token_type")
            !containsKey("expires_in")
            !containsKey("scope")
        }
        verifyAll(deserialized) {
            it == response
            accessToken() == null
            tokenType() == null
            expiresIn() == null
            scope() == null
        }
    }
}
