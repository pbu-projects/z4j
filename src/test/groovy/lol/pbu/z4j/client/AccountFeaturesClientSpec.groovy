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
import org.junit.jupiter.api.Tag

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class AccountFeaturesClientSpec extends Z4jSpec {

    @Shared
    AccountFeaturesClient adminAccountFeaturesClient, agentAccountFeaturesClient, userAccountFeaturesClient,
                          badTokenAccountFeaturesClient, badUrlAccountFeaturesClient

    def setupSpec() {
        adminAccountFeaturesClient = adminCtx.getBean(AccountFeaturesClient.class)
        agentAccountFeaturesClient = agentCtx.getBean(AccountFeaturesClient.class)
        userAccountFeaturesClient = userCtx.getBean(AccountFeaturesClient.class)
        badTokenAccountFeaturesClient = badTokenCtx.getBean(AccountFeaturesClient.class)
        badUrlAccountFeaturesClient = badUrlCtx.getBean(AccountFeaturesClient.class)
    }

    @Unroll
    def "can list account features as an #userType with subscription=#subscription and ids=#ids"(
            AccountFeaturesClient client, String userType, Boolean subscription, String ids) {
        given: "an authenticated client for #userType"

        when: "requesting account features"
        client.listFeatures(subscription, ids).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], subscription, ids] << [
                [[adminAccountFeaturesClient, "admin"], [agentAccountFeaturesClient, "agent"]],
                [null, true, false],
                [null]
        ].combinations()
    }

    def "end user cannot list account features"() {
        given: "an end user client"

        when: "requesting account features as an end user"
        userAccountFeaturesClient.listFeatures(null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling account features with #description throws HttpClientException"(
            String description, AccountFeaturesClient client) {
        when: "requesting account features with invalid client configuration"
        client.listFeatures(null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAccountFeaturesClient
        "unreachable url" | badUrlAccountFeaturesClient
    }
}
