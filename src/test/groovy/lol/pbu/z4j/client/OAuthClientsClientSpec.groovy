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
class OAuthClientsClientSpec extends Z4jSpec {

    @Shared
    OAuthClientsClient adminOAuthClientsClient, agentOAuthClientsClient, userOAuthClientsClient,
                       badTokenOAuthClientsClient, badUrlOAuthClientsClient

    @Shared
    Integer existingClientId

    def setupSpec() {
        adminOAuthClientsClient = adminCtx.getBean(OAuthClientsClient.class)
        agentOAuthClientsClient = agentCtx.getBean(OAuthClientsClient.class)
        userOAuthClientsClient = userCtx.getBean(OAuthClientsClient.class)
        badTokenOAuthClientsClient = badTokenCtx.getBean(OAuthClientsClient.class)
        badUrlOAuthClientsClient = badUrlCtx.getBean(OAuthClientsClient.class)

        def clients = adminOAuthClientsClient.listOAuthClients().block()
        if (clients?.clients && !clients.clients.isEmpty()) {
            existingClientId = clients.clients.first().id
        }
    }

    def "can list OAuth clients as an admin"() {
        given: "an authenticated admin client"

        when: "requesting OAuth clients list"
        adminOAuthClientsClient.listOAuthClients().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show OAuth client by ID as an admin"() {
        given: "an authenticated admin client and existing client ID"

        when: "requesting OAuth client by ID"
        if (existingClientId != null) {
            adminOAuthClientsClient.showOAuthClient(existingClientId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list current user OAuth clients as an admin"() {
        given: "an authenticated admin client"

        when: "requesting current user OAuth clients list"
        adminOAuthClientsClient.listCurrentUserOAuthClients().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list OAuth clients"(OAuthClientsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting OAuth clients"
        client.listOAuthClients().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentOAuthClientsClient, "agent"],
                [userOAuthClientsClient, "end user"]
        ]
    }

    @Unroll
    def "calling OAuth clients client with #description throws HttpClientException"(
            String description, OAuthClientsClient client) {
        when: "requesting OAuth clients with invalid client configuration"
        client.listOAuthClients().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenOAuthClientsClient
        "unreachable url" | badUrlOAuthClientsClient
    }
}
