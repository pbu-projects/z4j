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

class RedirectRuleCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create RedirectRuleCreateRequest with constructors and builder"() {
        given:
        RedirectRule expected = new RedirectRule("/old-path", "/new-path", 301).setId("rule_101")

        when:
        RedirectRuleCreateRequest byParams = new RedirectRuleCreateRequest("/old-path", "/new-path", 301)
        RedirectRuleCreateRequest direct = new RedirectRuleCreateRequest(expected)
        RedirectRuleCreateRequest built = RedirectRuleCreateRequestBuilder.builder().redirectRule(expected).build()

        then:
        verifyAll(byParams) {
            redirectRule() != null
            redirectRule().redirectFrom == "/old-path"
            redirectRule().redirectTo == "/new-path"
            redirectRule().redirectStatus == 301
        }
        verifyAll(direct) {
            redirectRule() == expected
            redirectRule().id == "rule_101"
            redirectRule().redirectFrom == "/old-path"
            redirectRule().redirectTo == "/new-path"
            redirectRule().redirectStatus == 301
        }
        verifyAll(built) {
            it == direct
            redirectRule() == expected
            redirectRule().id == "rule_101"
            redirectRule().redirectFrom == "/old-path"
            redirectRule().redirectTo == "/new-path"
            redirectRule().redirectStatus == 301
        }
    }

    def "should update redirectRule using record wither"() {
        given:
        RedirectRule original = new RedirectRule().setId("rule_1").setRedirectFrom("/v1").setRedirectTo("/v2")
        RedirectRule updatedRule = new RedirectRule().setId("rule_2").setRedirectFrom("/v2").setRedirectTo("/v3")
        RedirectRuleCreateRequest request = new RedirectRuleCreateRequest(original)

        when:
        RedirectRuleCreateRequest updated = request.withRedirectRule(updatedRule)

        then:
        verifyAll(request) {
            redirectRule() == original
            redirectRule().redirectFrom == "/v1"
        }
        verifyAll(updated) {
            redirectRule() == updatedRule
            redirectRule().redirectFrom == "/v2"
        }
    }

    def "should round-trip serialize and deserialize RedirectRuleCreateRequest"() {
        given:
        RedirectRule expected = new RedirectRule()
                .setId("rule_555")
                .setRedirectFrom("/legacy-docs")
                .setRedirectTo("/current-docs")
                .setRedirectStatus(302)
        RedirectRuleCreateRequest request = new RedirectRuleCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        RedirectRuleCreateRequest deserialized = objectMapper.readValue(json, RedirectRuleCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("redirect_rule")
            get("redirect_rule") instanceof Map
            ((Map) get("redirect_rule")).id == "rule_555"
            ((Map) get("redirect_rule")).redirect_from == "/legacy-docs"
        }
        verifyAll(deserialized) {
            it == request
            redirectRule() != null
            redirectRule().id == "rule_555"
            redirectRule().redirectFrom == "/legacy-docs"
            redirectRule().redirectTo == "/current-docs"
            redirectRule().redirectStatus == 302
        }
    }

    def "should handle null redirectRule component in constructor and wither"() {
        when:
        RedirectRuleCreateRequest request = new RedirectRuleCreateRequest(null)
        RedirectRule replacement = new RedirectRule().setId("rule_99").setRedirectFrom("/start").setRedirectTo("/finish")
        RedirectRuleCreateRequest updated = request.withRedirectRule(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        RedirectRuleCreateRequest deserialized = objectMapper.readValue(json, RedirectRuleCreateRequest.class)

        then:
        verifyAll(request) {
            redirectRule() == null
        }
        verifyAll(updated) {
            redirectRule() == replacement
            redirectRule().id == "rule_99"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("redirect_rule")
        }
        verifyAll(deserialized) {
            it == request
            redirectRule() == null
        }
    }
}
