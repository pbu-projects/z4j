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
import lol.pbu.z4j.model.ResolveOrganizationNamesRequest
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class InternalClientSpec extends Z4jSpec {

    @Shared
    InternalClient adminInternalClient, agentInternalClient, userInternalClient,
                   badTokenInternalClient, badUrlInternalClient

    @Shared
    ResolveOrganizationNamesRequest request = new ResolveOrganizationNamesRequest().setOrganizationNames(["Test Org"])

    def setupSpec() {
        adminInternalClient = adminCtx.getBean(InternalClient.class)
        agentInternalClient = agentCtx.getBean(InternalClient.class)
        userInternalClient = userCtx.getBean(InternalClient.class)
        badTokenInternalClient = badTokenCtx.getBean(InternalClient.class)
        badUrlInternalClient = badUrlCtx.getBean(InternalClient.class)
    }

    @Unroll
    def "#userType cannot resolve organization names on internal endpoint"(InternalClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "attempting internal resolve"
        client.resolveOrganizationNames(request).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentInternalClient, "agent"],
                [userInternalClient, "end user"]
        ]
    }

    @Unroll
    def "calling internal client with #description throws HttpClientException"(
            String description, InternalClient client) {
        when: "attempting internal resolve with invalid client configuration"
        client.resolveOrganizationNames(request).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenInternalClient
        "unreachable url" | badUrlInternalClient
    }
}
