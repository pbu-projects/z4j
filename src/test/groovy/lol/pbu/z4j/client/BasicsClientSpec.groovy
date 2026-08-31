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
class BasicsClientSpec extends Z4jSpec {

    @Shared
    BasicsClient adminBasicsClient, agentBasicsClient, userBasicsClient,
                 badTokenBasicsClient, badUrlBasicsClient

    def setupSpec() {
        adminBasicsClient = adminCtx.getBean(BasicsClient.class)
        agentBasicsClient = agentCtx.getBean(BasicsClient.class)
        userBasicsClient = userCtx.getBean(BasicsClient.class)
        badTokenBasicsClient = badTokenCtx.getBean(BasicsClient.class)
        badUrlBasicsClient = badUrlCtx.getBean(BasicsClient.class)
    }

    def "end user cannot create ticket or voicemail ticket"() {
        given: "an end user client"

        when: "attempting to create voicemail ticket as an end user"
        userBasicsClient.createTicketOrVoicemailTicket(null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling basics client with #description throws HttpClientException"(
            String description, BasicsClient client) {
        when: "attempting create ticket or voicemail ticket with invalid client configuration"
        client.createTicketOrVoicemailTicket(null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenBasicsClient
        "unreachable url" | badUrlBasicsClient
    }
}
