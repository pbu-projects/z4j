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
import lol.pbu.z4j.model.CustomObjectsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class ObjectTriggersClientSpec extends Z4jSpec {

    @Shared
    ObjectTriggersClient adminObjectTriggersClient, agentObjectTriggersClient, userObjectTriggersClient,
                         badTokenObjectTriggersClient, badUrlObjectTriggersClient

    @Shared
    String customObjectKey = "zen:ticket"

    def setupSpec() {
        adminObjectTriggersClient = adminCtx.getBean(ObjectTriggersClient.class)
        agentObjectTriggersClient = agentCtx.getBean(ObjectTriggersClient.class)
        userObjectTriggersClient = userCtx.getBean(ObjectTriggersClient.class)
        badTokenObjectTriggersClient = badTokenCtx.getBean(ObjectTriggersClient.class)
        badUrlObjectTriggersClient = badUrlCtx.getBean(ObjectTriggersClient.class)

        CustomObjectsResponse customObjects = adminCtx.getBean(CustomObjectsClient.class).listCustomObjects().block()
        if (customObjects?.customObjects && !customObjects.customObjects.isEmpty()) {
            customObjectKey = customObjects.customObjects.first().key
        }
    }

    @Unroll
    def "can list object triggers as an #userType"(
            ObjectTriggersClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting object triggers list"
        client.listObjectTriggers(customObjectKey, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectTriggersClient, "admin"],
                [agentObjectTriggersClient, "agent"]
        ]
    }

    def "end user cannot list object triggers"() {
        given: "an end user client"

        when: "requesting object triggers as an end user"
        userObjectTriggersClient.listObjectTriggers(customObjectKey, null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling object triggers client with #description throws HttpClientException"(
            String description, ObjectTriggersClient client) {
        when: "requesting object triggers with invalid client configuration"
        client.listObjectTriggers(customObjectKey, null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenObjectTriggersClient
        "unreachable url" | badUrlObjectTriggersClient
    }
}
