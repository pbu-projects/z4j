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
class ConversationLogClientSpec extends Z4jSpec {

    @Shared
    ConversationLogClient adminLogClient, agentLogClient, userLogClient,
                          badTokenLogClient, badUrlLogClient

    def setupSpec() {
        adminLogClient = adminCtx.getBean(ConversationLogClient.class)
        agentLogClient = agentCtx.getBean(ConversationLogClient.class)
        userLogClient = userCtx.getBean(ConversationLogClient.class)
        badTokenLogClient = badTokenCtx.getBean(ConversationLogClient.class)
        badUrlLogClient = badUrlCtx.getBean(ConversationLogClient.class)
    }

    def "end user cannot list conversation log for ticket"() {
        given: "an end user client"

        when: "requesting conversation log as an end user"
        userLogClient.listConversationLogForTicket(1).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling conversation log client with #description throws HttpClientException"(
            String description, ConversationLogClient client) {
        when: "requesting conversation log with invalid client configuration"
        client.listConversationLogForTicket(1).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenLogClient
        "unreachable url" | badUrlLogClient
    }
}
