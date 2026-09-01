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
class AccountSettingsClientSpec extends Z4jSpec {

    @Shared
    AccountSettingsClient adminAccountSettingsClient, agentAccountSettingsClient, userAccountSettingsClient,
                          badTokenAccountSettingsClient, badUrlAccountSettingsClient

    def setupSpec() {
        adminAccountSettingsClient = adminCtx.getBean(AccountSettingsClient.class)
        agentAccountSettingsClient = agentCtx.getBean(AccountSettingsClient.class)
        userAccountSettingsClient = userCtx.getBean(AccountSettingsClient.class)
        badTokenAccountSettingsClient = badTokenCtx.getBean(AccountSettingsClient.class)
        badUrlAccountSettingsClient = badUrlCtx.getBean(AccountSettingsClient.class)
    }

    @Unroll
    def "can show account settings as an #userType"(AccountSettingsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting account settings"
        client.showAccountSettings().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminAccountSettingsClient, "admin"],
                [agentAccountSettingsClient, "agent"]
        ]
    }

    def "end user cannot show account settings"() {
        given: "an end user client"

        when: "requesting account settings as an end user"
        userAccountSettingsClient.showAccountSettings().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "can update account settings as an #userType"(AccountSettingsClient client, String userType) {
        when: "updating account settings"
        client.updateAccountSettings().block()

        then: "a 400 Bad Request exception is thrown because of missing request body"
        HttpClientResponseException e = thrown()
        e.status == io.micronaut.http.HttpStatus.BAD_REQUEST

        where:
        [client, userType] << [
                [adminAccountSettingsClient, "admin"]
        ]
    }

    @Unroll
    def "agent and end user cannot update account settings"(AccountSettingsClient client, String userType) {
        when: "updating account settings"
        client.updateAccountSettings().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentAccountSettingsClient, "agent"],
                [userAccountSettingsClient, "user"]
        ]
    }

    @Unroll
    def "calling account settings with #description throws HttpClientException"(
            String description, AccountSettingsClient client) {
        when: "requesting account settings with invalid client configuration"
        client.showAccountSettings().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAccountSettingsClient
        "unreachable url" | badUrlAccountSettingsClient
    }
}
