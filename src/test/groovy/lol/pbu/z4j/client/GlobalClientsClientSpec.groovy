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
class GlobalClientsClientSpec extends Z4jSpec {

    @Shared
    GlobalClientsClient adminGlobalClientsClient, agentGlobalClientsClient, userGlobalClientsClient,
                        badTokenGlobalClientsClient, badUrlGlobalClientsClient

    @Shared
    Long existingGlobalClientId

    def setupSpec() {
        adminGlobalClientsClient = adminCtx.getBean(GlobalClientsClient.class)
        agentGlobalClientsClient = agentCtx.getBean(GlobalClientsClient.class)
        userGlobalClientsClient = userCtx.getBean(GlobalClientsClient.class)
        badTokenGlobalClientsClient = badTokenCtx.getBean(GlobalClientsClient.class)
        badUrlGlobalClientsClient = badUrlCtx.getBean(GlobalClientsClient.class)

        def clients = adminGlobalClientsClient.listGlobalOAuthClients().block()
        if (clients?.globalClients && !clients.globalClients.isEmpty()) {
            existingGlobalClientId = clients.globalClients.first().id
        }
    }

    def "can list global OAuth clients as an admin"() {
        given: "an authenticated admin client"

        when: "requesting global OAuth clients list"
        adminGlobalClientsClient.listGlobalOAuthClients().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show global OAuth client by ID as an admin"() {
        given: "an authenticated admin client and existing global client ID"

        when: "requesting global OAuth client by ID"
        if (existingGlobalClientId != null) {
            adminGlobalClientsClient.showGlobalClient(existingGlobalClientId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show token summary for global OAuth clients as an admin"() {
        given: "an authenticated admin client"

        when: "requesting global OAuth clients token summary"
        adminGlobalClientsClient.globalOAuthClientsTokenSummary(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list global OAuth clients"(GlobalClientsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting global OAuth clients"
        client.listGlobalOAuthClients().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentGlobalClientsClient, "agent"],
                [userGlobalClientsClient, "end user"]
        ]
    }

    @Unroll
    def "calling global OAuth clients client with #description throws HttpClientException"(
            String description, GlobalClientsClient client) {
        when: "requesting global OAuth clients with invalid client configuration"
        client.listGlobalOAuthClients().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenGlobalClientsClient
        "unreachable url" | badUrlGlobalClientsClient
    }
}
