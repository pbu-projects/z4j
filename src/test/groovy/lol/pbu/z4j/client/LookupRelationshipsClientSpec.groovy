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
@spock.lang.Ignore("Sandbox limitations")
class LookupRelationshipsClientSpec extends Z4jSpec {

    @Shared
    LookupRelationshipsClient adminLookupClient, agentLookupClient, userLookupClient,
                              badTokenLookupClient, badUrlLookupClient

    def setupSpec() {
        adminLookupClient = adminCtx.getBean(LookupRelationshipsClient.class)
        agentLookupClient = agentCtx.getBean(LookupRelationshipsClient.class)
        userLookupClient = userCtx.getBean(LookupRelationshipsClient.class)
        badTokenLookupClient = badTokenCtx.getBean(LookupRelationshipsClient.class)
        badUrlLookupClient = badUrlCtx.getBean(LookupRelationshipsClient.class)
    }

    @Unroll
    def "can get relationship filter definitions as an #userType with targetType=#targetType"(
            LookupRelationshipsClient client, String userType, String targetType) {
        given: "an authenticated client for #userType"

        when: "requesting filter definitions for target type"
        client.getRelationshipFilterDefinitions(targetType, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], targetType] << [
                [[adminLookupClient, "admin"], [agentLookupClient, "agent"]],
                ["zen:user", "zen:ticket", "zen:organization"]
        ].combinations()
    }

    def "end user cannot get relationship filter definitions"() {
        given: "an end user client"

        when: "requesting filter definitions as an end user"
        userLookupClient.getRelationshipFilterDefinitions("zen:ticket", null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling lookup relationships client with #description throws HttpClientException"(
            String description, LookupRelationshipsClient client) {
        when: "requesting filter definitions with invalid client configuration"
        client.getRelationshipFilterDefinitions("zen:ticket", null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenLookupClient
        "unreachable url" | badUrlLookupClient
    }
}
