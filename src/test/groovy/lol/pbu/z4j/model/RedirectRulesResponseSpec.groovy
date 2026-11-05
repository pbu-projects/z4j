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

class RedirectRulesResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create RedirectRulesResponse and sync records and redirectRules across all compact constructor branches"() {
        given:
        RedirectRule rule1 = new RedirectRule().setId("01R1").setRedirectFrom("/old").setRedirectTo("/new").setRedirectStatus(301)
        RedirectRule rule2 = new RedirectRule().setId("01R2").setRedirectFrom("/legacy").setRedirectTo("/modern").setRedirectStatus(302)
        Meta expectedMeta = new Meta().setHasMore(false)

        when:
        RedirectRulesResponse onlyRecords = new RedirectRulesResponse([rule1], null, expectedMeta)
        RedirectRulesResponse onlyRedirectRules = new RedirectRulesResponse(null, [rule2], expectedMeta)
        RedirectRulesResponse bothProvided = new RedirectRulesResponse([rule1], [rule2], expectedMeta)
        RedirectRulesResponse bothNull = new RedirectRulesResponse(null, null, null)
        RedirectRulesResponse built = RedirectRulesResponseBuilder.builder()
                .records([rule1])
                .redirectRules([rule1])
                .meta(expectedMeta)
                .build()

        then:
        verifyAll(onlyRecords) {
            records() == [rule1]
            redirectRules() == [rule1]
            meta() == expectedMeta
        }
        verifyAll(onlyRedirectRules) {
            records() == [rule2]
            redirectRules() == [rule2]
            meta() == expectedMeta
        }
        verifyAll(bothProvided) {
            records() == [rule1]
            redirectRules() == [rule2]
            meta() == expectedMeta
        }
        verifyAll(bothNull) {
            records() == null
            redirectRules() == null
            meta() == null
        }
        verifyAll(built) {
            it == onlyRecords
            records() == [rule1]
            redirectRules() == [rule1]
            meta() == expectedMeta
        }
    }

    def "should update RedirectRulesResponse using record withers and addRecordsItem method"() {
        given:
        RedirectRule first = new RedirectRule().setId("01R1").setRedirectFrom("/a").setRedirectTo("/b").setRedirectStatus(301)
        RedirectRule second = new RedirectRule().setId("01R2").setRedirectFrom("/c").setRedirectTo("/d").setRedirectStatus(302)
        Meta meta1 = new Meta().setHasMore(false)
        Meta meta2 = new Meta().setHasMore(true).setAfterCursor("cursor_next")
        RedirectRulesResponse response = new RedirectRulesResponse([first], [first], meta1)

        when:
        RedirectRulesResponse updated = response
                .withRecords([second])
                .withRedirectRules([second])
                .withMeta(meta2)
        RedirectRulesResponse withAdded = response.addRecordsItem(second)
        RedirectRulesResponse fromNull = new RedirectRulesResponse(null, null, meta1).addRecordsItem(first)

        then:
        verifyAll(response) {
            records() == [first]
            redirectRules() == [first]
            meta() == meta1
        }
        verifyAll(updated) {
            records() == [second]
            redirectRules() == [second]
            meta() == meta2
        }
        verifyAll(withAdded) {
            records() == [first, second]
            redirectRules() == [first]
            meta() == meta1
        }
        verifyAll(fromNull) {
            records() == [first]
            redirectRules() == [first]
            meta() == meta1
        }
    }

    def "should round-trip serialize and deserialize RedirectRulesResponse"() {
        given:
        RedirectRule rule = new RedirectRule().setId("01R99").setRedirectFrom("/from").setRedirectTo("/to").setRedirectStatus(301)
        Meta expectedMeta = new Meta().setHasMore(false)
        RedirectRulesResponse response = new RedirectRulesResponse([rule], null, expectedMeta)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        RedirectRulesResponse deserialized = objectMapper.readValue(json, RedirectRulesResponse.class)
        RedirectRulesResponse fromRedirectRulesJson = objectMapper.readValue(
                '{"redirect_rules":[{"id":"01R99","redirect_from":"/from","redirect_to":"/to","redirect_status":301}]}',
                RedirectRulesResponse.class
        )

        then:
        verifyAll(map) {
            containsKey("records")
            containsKey("redirect_rules")
            containsKey("meta")
        }
        verifyAll(deserialized) {
            it == response
            records().size() == 1
            records().first().id == "01R99"
            redirectRules().size() == 1
            redirectRules().first().id == "01R99"
            meta().hasMore == false
        }
        verifyAll(fromRedirectRulesJson) {
            records().size() == 1
            records().first().id == "01R99"
            redirectRules().size() == 1
            redirectRules().first().id == "01R99"
        }
    }
}
