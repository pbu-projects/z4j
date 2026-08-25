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
import lol.pbu.z4j.model.SupportAddressesResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class SupportAddressesClientSpec extends Z4jSpec {

    @Shared
    SupportAddressesClient adminSupportAddressesClient, agentSupportAddressesClient, userSupportAddressesClient,
                           badTokenSupportAddressesClient, badUrlSupportAddressesClient

    @Shared
    Integer existingAddressId

    def setupSpec() {
        adminSupportAddressesClient = adminCtx.getBean(SupportAddressesClient.class)
        agentSupportAddressesClient = agentCtx.getBean(SupportAddressesClient.class)
        userSupportAddressesClient = userCtx.getBean(SupportAddressesClient.class)
        badTokenSupportAddressesClient = badTokenCtx.getBean(SupportAddressesClient.class)
        badUrlSupportAddressesClient = badUrlCtx.getBean(SupportAddressesClient.class)

        SupportAddressesResponse addresses = adminSupportAddressesClient.listSupportAddresses().block()
        if (addresses?.recipientAddresses && !addresses.recipientAddresses.isEmpty()) {
            existingAddressId = addresses.recipientAddresses.first().id
        }
    }

    @Unroll
    def "can list support addresses as an #userType"(
            SupportAddressesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting support addresses list"
        client.listSupportAddresses().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSupportAddressesClient, "admin"],
                [agentSupportAddressesClient, "agent"]
        ]
    }

    @Unroll
    def "can show support address by ID as an #userType"(
            SupportAddressesClient client, String userType) {
        given: "an authenticated client for #userType and existing address ID"

        when: "requesting support address by ID"
        if (existingAddressId != null) {
            client.showSupportAddress(existingAddressId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSupportAddressesClient, "admin"],
                [agentSupportAddressesClient, "agent"]
        ]
    }

    def "end user cannot list support addresses"() {
        given: "an end user client"

        when: "requesting support addresses as an end user"
        userSupportAddressesClient.listSupportAddresses().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling support addresses client with #description throws HttpClientException"(
            String description, SupportAddressesClient client) {
        when: "requesting support addresses with invalid client configuration"
        client.listSupportAddresses().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSupportAddressesClient
        "unreachable url" | badUrlSupportAddressesClient
    }
}
