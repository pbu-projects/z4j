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
import lol.pbu.z4j.model.CustomObjectsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class CustomObjectRecordsClientSpec extends Z4jSpec {

    @Shared
    CustomObjectRecordsClient adminObjectRecordsClient, agentObjectRecordsClient, userObjectRecordsClient,
                              badTokenObjectRecordsClient, badUrlObjectRecordsClient

    @Shared
    String customObjectKey = "zen:ticket"

    def setupSpec() {
        adminObjectRecordsClient = adminCtx.getBean(CustomObjectRecordsClient.class)
        agentObjectRecordsClient = agentCtx.getBean(CustomObjectRecordsClient.class)
        userObjectRecordsClient = userCtx.getBean(CustomObjectRecordsClient.class)
        badTokenObjectRecordsClient = badTokenCtx.getBean(CustomObjectRecordsClient.class)
        badUrlObjectRecordsClient = badUrlCtx.getBean(CustomObjectRecordsClient.class)

        CustomObjectsResponse customObjects = adminCtx.getBean(CustomObjectsClient.class).listCustomObjects().block()
        if (customObjects?.customObjects && !customObjects.customObjects.isEmpty()) {
            customObjectKey = customObjects.customObjects.first().key
        }
    }

    @Unroll
    def "can list custom object records as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records list"
        client.listCustomObjectRecords(customObjectKey, null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    @Unroll
    def "can count custom object records as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records count"
        client.countCustomObjectRecords(customObjectKey).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    @Unroll
    def "can get custom object records limit as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records limit"
        client.customObjectRecordsLimit().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    @Unroll
    def "can autocomplete custom object records as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records autocomplete"
        client.autocompleteCustomObjectRecordSearch(customObjectKey, "test", null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    def "end user cannot list custom object records"() {
        given: "an end user client"

        when: "requesting custom object records as an end user"
        userObjectRecordsClient.listCustomObjectRecords(customObjectKey, null, null, null, null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling custom object records client with #description throws HttpClientException"(
            String description, CustomObjectRecordsClient client) {
        when: "requesting custom object records with invalid client configuration"
        client.listCustomObjectRecords(customObjectKey, null, null, null, null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenObjectRecordsClient
        "unreachable url" | badUrlObjectRecordsClient
    }
}
