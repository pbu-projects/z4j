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
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class SecuritySettingsClientSpec extends Z4jSpec {

    @Shared
    SecuritySettingsClient adminSecurityClient, agentSecurityClient, userSecurityClient,
                           badTokenSecurityClient, badUrlSecurityClient

    def setupSpec() {
        adminSecurityClient = adminCtx.getBean(SecuritySettingsClient.class)
        agentSecurityClient = agentCtx.getBean(SecuritySettingsClient.class)
        userSecurityClient = userCtx.getBean(SecuritySettingsClient.class)
        badTokenSecurityClient = badTokenCtx.getBean(SecuritySettingsClient.class)
        badUrlSecurityClient = badUrlCtx.getBean(SecuritySettingsClient.class)
    }

    def "can show security settings as an admin"() {
        given: "an authenticated admin client"

        when: "requesting security settings"
        adminSecurityClient.showSecuritySettings().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot show security settings"(SecuritySettingsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting security settings"
        client.showSecuritySettings().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentSecurityClient, "agent"],
                [userSecurityClient, "end user"]
        ]
    }

    @Unroll
    def "calling security settings client with #description throws HttpClientException"(
            String description, SecuritySettingsClient client) {
        when: "requesting security settings with invalid client configuration"
        client.showSecuritySettings().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSecurityClient
        "unreachable url" | badUrlSecurityClient
    }
}
