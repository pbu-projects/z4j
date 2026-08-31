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
class SharingAgreementsClientSpec extends Z4jSpec {

    @Shared
    SharingAgreementsClient adminSharingClient, agentSharingClient, userSharingClient,
                            badTokenSharingClient, badUrlSharingClient

    def setupSpec() {
        adminSharingClient = adminCtx.getBean(SharingAgreementsClient.class)
        agentSharingClient = agentCtx.getBean(SharingAgreementsClient.class)
        userSharingClient = userCtx.getBean(SharingAgreementsClient.class)
        badTokenSharingClient = badTokenCtx.getBean(SharingAgreementsClient.class)
        badUrlSharingClient = badUrlCtx.getBean(SharingAgreementsClient.class)
    }

    @Unroll
    def "can list sharing agreements as an #userType"(
            SharingAgreementsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting sharing agreements list"
        client.listSharingAgreements().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSharingClient, "admin"],
                [agentSharingClient, "agent"]
        ]
    }

    def "end user cannot list sharing agreements"() {
        given: "an end user client"

        when: "requesting sharing agreements as an end user"
        userSharingClient.listSharingAgreements().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling sharing agreements client with #description throws HttpClientException"(
            String description, SharingAgreementsClient client) {
        when: "requesting sharing agreements with invalid client configuration"
        client.listSharingAgreements().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSharingClient
        "unreachable url" | badUrlSharingClient
    }
}
