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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("admin")
class LocalesClientSpec extends Z4jSpec {

    @Shared
    LocalesClient adminLocalesClient, agentLocalesClient, userLocalesClient,
                  badTokenLocalesClient, badUrlLocalesClient

    def setupSpec() {
        adminLocalesClient = adminCtx.getBean(LocalesClient.class)
        agentLocalesClient = agentCtx.getBean(LocalesClient.class)
        userLocalesClient = userCtx.getBean(LocalesClient.class)
        badTokenLocalesClient = badTokenCtx.getBean(LocalesClient.class)
        badUrlLocalesClient = badUrlCtx.getBean(LocalesClient.class)
    }

    @Unroll
    def "can list locales as #userType"(LocalesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting account locales list"
        client.listLocales().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminLocalesClient, "admin"],
                [agentLocalesClient, "agent"],
                [userLocalesClient, "end user"]
        ]
    }

    @Unroll
    def "can list available public locales as #userType"(LocalesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting available public locales list"
        client.listAvailablePublicLocales().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminLocalesClient, "admin"],
                [agentLocalesClient, "agent"],
                [userLocalesClient, "end user"]
        ]
    }

    @Unroll
    def "can list locales for agent as #userType"(LocalesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting locales for agent"
        client.listLocalesForAgent().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminLocalesClient, "admin"],
                [agentLocalesClient, "agent"],
                [userLocalesClient, "end user"]
        ]
    }

    @Unroll
    def "can show current locale as #userType"(LocalesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting current locale"
        client.showCurrentLocale().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminLocalesClient, "admin"],
                [agentLocalesClient, "agent"],
                [userLocalesClient, "end user"]
        ]
    }

    @Unroll
    def "can detect best locale as #userType"(LocalesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting best detected locale"
        client.detectBestLocale().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminLocalesClient, "admin"],
                [agentLocalesClient, "agent"],
                [userLocalesClient, "end user"]
        ]
    }

    @Unroll
    def "can show locale by ID as #userType"(LocalesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting locale by ID"
        client.showLocaleById("en-US").block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminLocalesClient, "admin"],
                [agentLocalesClient, "agent"],
                [userLocalesClient, "end user"]
        ]
    }

    @Unroll
    def "calling locales client with #description throws HttpClientException"(
            String description, LocalesClient client) {
        when: "requesting locales with invalid client configuration"
        client.listLocales().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenLocalesClient
        "unreachable url" | badUrlLocalesClient
    }
}
