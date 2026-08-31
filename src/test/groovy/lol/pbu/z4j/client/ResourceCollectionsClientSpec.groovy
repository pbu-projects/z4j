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
class ResourceCollectionsClientSpec extends Z4jSpec {

    @Shared
    ResourceCollectionsClient adminCollectionsClient, agentCollectionsClient, userCollectionsClient,
                              badTokenCollectionsClient, badUrlCollectionsClient

    def setupSpec() {
        adminCollectionsClient = adminCtx.getBean(ResourceCollectionsClient.class)
        agentCollectionsClient = agentCtx.getBean(ResourceCollectionsClient.class)
        userCollectionsClient = userCtx.getBean(ResourceCollectionsClient.class)
        badTokenCollectionsClient = badTokenCtx.getBean(ResourceCollectionsClient.class)
        badUrlCollectionsClient = badUrlCtx.getBean(ResourceCollectionsClient.class)
    }

    def "can list resource collections as an admin"() {
        given: "an authenticated admin client"

        when: "requesting resource collections list"
        adminCollectionsClient.listResourceCollections().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list resource collections"(ResourceCollectionsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting resource collections"
        client.listResourceCollections().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentCollectionsClient, "agent"],
                [userCollectionsClient, "end user"]
        ]
    }

    @Unroll
    def "calling resource collections client with #description throws HttpClientException"(
            String description, ResourceCollectionsClient client) {
        when: "requesting resource collections with invalid client configuration"
        client.listResourceCollections().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenCollectionsClient
        "unreachable url" | badUrlCollectionsClient
    }
}
