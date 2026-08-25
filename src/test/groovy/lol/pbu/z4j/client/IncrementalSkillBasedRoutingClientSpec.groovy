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
class IncrementalSkillBasedRoutingClientSpec extends Z4jSpec {

    @Shared
    IncrementalSkillBasedRoutingClient adminRoutingExportClient, agentRoutingExportClient, userRoutingExportClient,
                                       badTokenRoutingExportClient, badUrlRoutingExportClient

    def setupSpec() {
        adminRoutingExportClient = adminCtx.getBean(IncrementalSkillBasedRoutingClient.class)
        agentRoutingExportClient = agentCtx.getBean(IncrementalSkillBasedRoutingClient.class)
        userRoutingExportClient = userCtx.getBean(IncrementalSkillBasedRoutingClient.class)
        badTokenRoutingExportClient = badTokenCtx.getBean(IncrementalSkillBasedRoutingClient.class)
        badUrlRoutingExportClient = badUrlCtx.getBean(IncrementalSkillBasedRoutingClient.class)
    }

    def "can export incremental routing attributes as an admin"() {
        given: "an authenticated admin client"

        when: "requesting incremental routing attributes export"
        adminRoutingExportClient.incrementalSkilBasedRoutingAttributesExport().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can export incremental routing attribute values as an admin"() {
        given: "an authenticated admin client"

        when: "requesting incremental routing attribute values export"
        adminRoutingExportClient.incrementalSkilBasedRoutingAttributeValuesExport().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can export incremental routing instance values as an admin"() {
        given: "an authenticated admin client"

        when: "requesting incremental routing instance values export"
        adminRoutingExportClient.incrementalSkilBasedRoutingInstanceValuesExport().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot export incremental routing attributes"(
            IncrementalSkillBasedRoutingClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting incremental routing attributes export"
        client.incrementalSkilBasedRoutingAttributesExport().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentRoutingExportClient, "agent"],
                [userRoutingExportClient, "end user"]
        ]
    }

    @Unroll
    def "calling incremental routing export client with #description throws HttpClientException"(
            String description, IncrementalSkillBasedRoutingClient client) {
        when: "requesting incremental routing export with invalid client configuration"
        client.incrementalSkilBasedRoutingAttributesExport().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenRoutingExportClient
        "unreachable url" | badUrlRoutingExportClient
    }
}
