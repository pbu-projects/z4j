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
class RemoteAuthenticationsClientSpec extends Z4jSpec {

    @Shared
    RemoteAuthenticationsClient adminAuthClient, agentAuthClient, userAuthClient,
                                badTokenAuthClient, badUrlAuthClient

    def setupSpec() {
        adminAuthClient = adminCtx.getBean(RemoteAuthenticationsClient.class)
        agentAuthClient = agentCtx.getBean(RemoteAuthenticationsClient.class)
        userAuthClient = userCtx.getBean(RemoteAuthenticationsClient.class)
        badTokenAuthClient = badTokenCtx.getBean(RemoteAuthenticationsClient.class)
        badUrlAuthClient = badUrlCtx.getBean(RemoteAuthenticationsClient.class)
    }

    def "can list remote authentications as an admin"() {
        given: "an authenticated admin client"

        when: "requesting remote authentications list"
        adminAuthClient.listRemoteAuthentications().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list remote authentications"(
            RemoteAuthenticationsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting remote authentications"
        client.listRemoteAuthentications().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentAuthClient, "agent"],
                [userAuthClient, "end user"]
        ]
    }

    @Unroll
    def "calling remote authentications client with #description throws HttpClientException"(
            String description, RemoteAuthenticationsClient client) {
        when: "requesting remote authentications with invalid client configuration"
        client.listRemoteAuthentications().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAuthClient
        "unreachable url" | badUrlAuthClient
    }
}
