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

class RedirectRuleResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create RedirectRuleResponse with canonical constructor and builder"() {
        given:
        RedirectRule expected = new RedirectRule()
                .setId("rule_101")
                .setRedirectFrom("/old-path")
                .setRedirectTo("/new-path")
                .setRedirectStatus(301)

        when:
        RedirectRuleResponse direct = new RedirectRuleResponse(expected)
        RedirectRuleResponse built = RedirectRuleResponseBuilder.builder().redirectRule(expected).build()

        then:
        verifyAll(direct) {
            redirectRule == expected
            redirectRule.id == "rule_101"
            redirectRule.redirectFrom == "/old-path"
            redirectRule.redirectTo == "/new-path"
            redirectRule.redirectStatus == 301
        }
        verifyAll(built) {
            redirectRule == expected
            redirectRule.id == "rule_101"
            redirectRule.redirectFrom == "/old-path"
            redirectRule.redirectTo == "/new-path"
            redirectRule.redirectStatus == 301
        }
    }

    def "should update redirectRule using record wither"() {
        given:
        RedirectRule original = new RedirectRule().setId("rule_1").setRedirectFrom("/v1").setRedirectTo("/v2")
        RedirectRule updatedRule = new RedirectRule().setId("rule_2").setRedirectFrom("/v2").setRedirectTo("/v3")
        RedirectRuleResponse response = new RedirectRuleResponse(original)

        when:
        RedirectRuleResponse updated = response.withRedirectRule(updatedRule)

        then:
        verifyAll(response) {
            redirectRule == original
            redirectRule.redirectFrom == "/v1"
        }
        verifyAll(updated) {
            redirectRule == updatedRule
            redirectRule.redirectFrom == "/v2"
        }
    }

    def "should round-trip serialize and deserialize RedirectRuleResponse"() {
        given:
        RedirectRule expected = new RedirectRule()
                .setId("rule_555")
                .setRedirectFrom("/legacy-docs")
                .setRedirectTo("/current-docs")
                .setRedirectStatus(302)
        RedirectRuleResponse response = new RedirectRuleResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        RedirectRuleResponse deserialized = objectMapper.readValue(json, RedirectRuleResponse.class)

        then:
        verifyAll(map) {
            containsKey("redirect_rule")
            get("redirect_rule") instanceof Map
            ((Map) get("redirect_rule")).id == "rule_555"
            ((Map) get("redirect_rule")).redirect_from == "/legacy-docs"
        }
        verifyAll(deserialized) {
            it == response
            redirectRule != null
            redirectRule.id == "rule_555"
            redirectRule.redirectFrom == "/legacy-docs"
            redirectRule.redirectTo == "/current-docs"
            redirectRule.redirectStatus == 302
        }
    }

    def "should handle null redirectRule component in constructor and wither"() {
        when:
        RedirectRuleResponse response = new RedirectRuleResponse(null)
        RedirectRule replacement = new RedirectRule().setId("rule_99").setRedirectFrom("/start").setRedirectTo("/finish")
        RedirectRuleResponse updated = response.withRedirectRule(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        RedirectRuleResponse deserialized = objectMapper.readValue(json, RedirectRuleResponse.class)

        then:
        verifyAll(response) {
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
            it == response
            redirectRule() == null
        }
    }
}
