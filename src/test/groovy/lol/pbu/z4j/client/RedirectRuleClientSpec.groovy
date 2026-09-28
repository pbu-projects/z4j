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
        response != null
        response.records != null || response.redirectRules != null
    }

    def "can create, show, and delete redirect rule"() {
        given: "a unique redirect from path"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String redirectFrom = "/hc/en-us/articles/${entropy}"
        String redirectTo = "/hc/en-us"
        Integer status = 301
        String ruleId = null

        when: "creating redirect rule"
        adminRedirectRuleClient.createRedirectRule(
                new RedirectRuleCreateRequest(redirectFrom, redirectTo, status)
        ).block()

        and: "finding created redirect rule in list"
        RedirectRulesResponse listResp = adminRedirectRuleClient.searchRedirectRules().block()
        RedirectRule createdRule = listResp?.records?.find { it.redirectFrom == redirectFrom }
        ruleId = createdRule?.id

        then: "rule is created successfully"
        noExceptionThrown()
        ruleId != null
        createdRule.redirectFrom == redirectFrom
        createdRule.redirectTo == redirectTo
        createdRule.redirectStatus == status

        when: "showing redirect rule"
        RedirectRuleResponse shown = adminRedirectRuleClient.showRedirectRule(ruleId).block()

        then: "rule matches created rule"
        noExceptionThrown()
        shown?.redirectRule != null
        shown.redirectRule.id == ruleId
        shown.redirectRule.redirectFrom == redirectFrom

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

    def "calling redirect rule client with bad url fails"() {
        when: "calling with bad url"
        badUrlRedirectRuleClient.showRedirectRule("invalid_id").block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
