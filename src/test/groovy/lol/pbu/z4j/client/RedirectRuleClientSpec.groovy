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

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.RedirectRule
import lol.pbu.z4j.model.RedirectRuleCreateRequest
import lol.pbu.z4j.model.RedirectRuleResponse
import lol.pbu.z4j.model.RedirectRulesResponse
import spock.lang.Shared

@MicronautTest
class RedirectRuleClientSpec extends Z4jSpec {

    @Shared
    RedirectRuleClient adminRedirectRuleClient, agentRedirectRuleClient, userRedirectRuleClient, badUrlRedirectRuleClient

    def setupSpec() {
        adminRedirectRuleClient = adminCtx.getBean(RedirectRuleClient.class)
        agentRedirectRuleClient = agentCtx.getBean(RedirectRuleClient.class)
        userRedirectRuleClient = userCtx.getBean(RedirectRuleClient.class)
        badUrlRedirectRuleClient = badUrlCtx.getBean(RedirectRuleClient.class)
    }

    def "can list redirect rules"() {
        when: "searching redirect rules"
        RedirectRulesResponse response = adminRedirectRuleClient.searchRedirectRules().block()

        then: "response is returned"
        noExceptionThrown()
        assert response != null
        verifyAll(response) {
            records != null
        }
    }

    def "can create, show, and delete redirect rule"() {
        given: "a unique redirect from path"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String expectedFrom = "/hc/en-us/articles/${entropy}"
        String expectedTo = "/hc/en-us"
        Integer expectedStatus = 301
        String ruleId = null

        when: "creating redirect rule"
        adminRedirectRuleClient.createRedirectRule(
                new RedirectRuleCreateRequest(expectedFrom, expectedTo, expectedStatus)
        ).block()

        and: "finding created redirect rule in list"
        RedirectRulesResponse listResp = adminRedirectRuleClient.searchRedirectRules().block()
        RedirectRule createdRule = listResp?.records?.find { it.redirectFrom == expectedFrom }
        ruleId = createdRule?.id

        then: "rule is created successfully"
        noExceptionThrown()
        ruleId != null
        assert createdRule != null
        verifyAll(createdRule) {
            redirectFrom == expectedFrom
            redirectTo == expectedTo
            redirectStatus == expectedStatus
        }

        when: "showing redirect rule"
        RedirectRuleResponse shown = adminRedirectRuleClient.showRedirectRule(ruleId).block()

        then: "rule matches created rule"
        noExceptionThrown()
        assert shown?.redirectRule != null
        verifyAll(shown.redirectRule) {
            id == ruleId
            redirectFrom == expectedFrom
            redirectTo == expectedTo
            redirectStatus == expectedStatus
        }

        when: "deleting redirect rule"
        adminRedirectRuleClient.deleteRedirectRule(ruleId).block()

        then: "no exception is thrown"
        noExceptionThrown()

        cleanup: "defensive cleanup"
        if (ruleId != null) {
            try {
                adminRedirectRuleClient.deleteRedirectRule(ruleId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can set redirect rule using convenience alias"() {
        given: "a unique redirect from path"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String expectedFrom = "/hc/en-us/articles/set-${entropy}"
        String expectedTo = "/hc/en-us"
        Integer expectedStatus = 301
        String ruleId = null

        when: "setting redirect rule via convenience alias"
        adminRedirectRuleClient.setRedirectRule(
                new RedirectRuleCreateRequest(expectedFrom, expectedTo, expectedStatus)
        ).block()

        and: "finding created redirect rule in list"
        RedirectRulesResponse listResp = adminRedirectRuleClient.listRedirectRules().block()
        RedirectRule createdRule = listResp?.records?.find { it.redirectFrom == expectedFrom }
        ruleId = createdRule?.id

        then: "rule is created and matches input"
        noExceptionThrown()
        ruleId != null
        assert createdRule != null
        verifyAll(createdRule) {
            redirectFrom == expectedFrom
            redirectTo == expectedTo
            redirectStatus == expectedStatus
        }

        cleanup: "defensive cleanup"
        if (ruleId != null) {
            try {
                adminRedirectRuleClient.deleteRedirectRule(ruleId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "showing non-existent redirect rule throws not found exception"() {
        when: "showing redirect rule with non-existent id"
        adminRedirectRuleClient.showRedirectRule("non_existent_rule_id").block()

        then: "not found exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [400, 404]
    }

    def "cannot search redirect rules as unauthorized role"(RedirectRuleClient client, String role) {
        when: "unauthorized client searches redirect rules"
        client.searchRedirectRules().block()

        then: "unauthorized or forbidden exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [401, 403, 404]

        where:
        [client, role] << [
                [agentRedirectRuleClient, "agent"],
                [userRedirectRuleClient, "user"]
        ]
    }

    def "calling redirect rule client with bad url fails"() {
        when: "calling with bad url"
        badUrlRedirectRuleClient.showRedirectRule("invalid_id").block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
