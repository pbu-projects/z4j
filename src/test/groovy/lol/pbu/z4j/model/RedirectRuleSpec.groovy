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

class RedirectRuleSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create RedirectRule with constructors and builder"() {
        when:
        RedirectRule direct = new RedirectRule(
                "rule_101",
                "brand_202",
                "/old-docs",
                "/new-docs",
                301,
                "2026-03-20T10:00:00Z",
                "2026-03-20T12:00:00Z"
        )
        RedirectRule threeArg = new RedirectRule("/v1/guide", "/v2/guide", 302)
        RedirectRule noArg = new RedirectRule()
        RedirectRule built = RedirectRuleBuilder.builder()
                .id("rule_101")
                .brandId("brand_202")
                .redirectFrom("/old-docs")
                .redirectTo("/new-docs")
                .redirectStatus(301)
                .createdAt("2026-03-20T10:00:00Z")
                .updatedAt("2026-03-20T12:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            id() == "rule_101"
            brandId() == "brand_202"
            redirectFrom() == "/old-docs"
            redirectTo() == "/new-docs"
            redirectStatus() == 301
            createdAt() == "2026-03-20T10:00:00Z"
            updatedAt() == "2026-03-20T12:00:00Z"
        }
        verifyAll(threeArg) {
            id() == null
            brandId() == null
            redirectFrom() == "/v1/guide"
            redirectTo() == "/v2/guide"
            redirectStatus() == 302
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(noArg) {
            id() == null
            brandId() == null
            redirectFrom() == null
            redirectTo() == null
            redirectStatus() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update RedirectRule using record withers"() {
        given:
        RedirectRule initial = new RedirectRule("/source", "/target", 301)

        when:
        RedirectRule updated = initial
                .withId("rule_303")
                .withBrandId("brand_404")
                .withRedirectFrom("/updated-source")
                .withRedirectTo("/updated-target")
                .withRedirectStatus(307)
                .withCreatedAt("2026-03-20T14:00:00Z")
                .withUpdatedAt("2026-03-20T15:00:00Z")

        then:
        verifyAll(updated) {
            id() == "rule_303"
            brandId() == "brand_404"
            redirectFrom() == "/updated-source"
            redirectTo() == "/updated-target"
            redirectStatus() == 307
            createdAt() == "2026-03-20T14:00:00Z"
            updatedAt() == "2026-03-20T15:00:00Z"
        }
    }

    def "should round-trip serialize and deserialize RedirectRule"() {
        given:
        RedirectRule rule = new RedirectRule("/legacy-page", "/modern-page", 301)
                .withId("rule_555")
                .withBrandId("brand_99")

        when:
        String json = objectMapper.writeValueAsString(rule)
        Map map = objectMapper.readValue(json, Map.class)
        RedirectRule deserialized = objectMapper.readValue(json, RedirectRule.class)

        then:
        verifyAll(map) {
            get("id") == "rule_555"
            get("brand_id") == "brand_99"
            get("redirect_from") == "/legacy-page"
            get("redirect_to") == "/modern-page"
            get("redirect_status") == 301
        }
        verifyAll(deserialized) {
            it == rule
        }
    }
}
