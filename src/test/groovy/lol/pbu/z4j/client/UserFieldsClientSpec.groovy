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
import lol.pbu.z4j.model.ShowOrganizationFieldOrganizationFieldIdParameter
import lol.pbu.z4j.model.UserFieldsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class UserFieldsClientSpec extends Z4jSpec {

    @Shared
    UserFieldsClient adminUserFieldsClient, agentUserFieldsClient, userUserFieldsClient,
                     badTokenUserFieldsClient, badUrlUserFieldsClient

    @Shared
    Integer existingUserFieldId

    def setupSpec() {
        adminUserFieldsClient = adminCtx.getBean(UserFieldsClient.class)
        agentUserFieldsClient = agentCtx.getBean(UserFieldsClient.class)
        userUserFieldsClient = userCtx.getBean(UserFieldsClient.class)
        badTokenUserFieldsClient = badTokenCtx.getBean(UserFieldsClient.class)
        badUrlUserFieldsClient = badUrlCtx.getBean(UserFieldsClient.class)

        UserFieldsResponse fields = adminUserFieldsClient.listUserFields().block()
        if (fields?.userFields && !fields.userFields.isEmpty()) {
            existingUserFieldId = fields.userFields.first().id
        }
    }

    @Unroll
    def "can list user fields as an #userType"(
            UserFieldsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting user fields list"
        client.listUserFields().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUserFieldsClient, "admin"],
                [agentUserFieldsClient, "agent"]
        ]
    }

    @Unroll
    def "can show user field by ID as an #userType"(
            UserFieldsClient client, String userType) {
        given: "an authenticated client for #userType and existing user field ID"

        when: "requesting user field by ID"
        if (existingUserFieldId != null) {
            client.showUserField(existingUserFieldId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminUserFieldsClient, "admin"],
                [agentUserFieldsClient, "agent"]
        ]
    }

    def "end user cannot list user fields"() {
        given: "an end user client"

        when: "requesting user fields as an end user"
        userUserFieldsClient.listUserFields().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling user fields client with #description throws HttpClientException"(
            String description, UserFieldsClient client) {
        when: "requesting user fields with invalid client configuration"
        client.listUserFields().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenUserFieldsClient
        "unreachable url" | badUrlUserFieldsClient
    }
}
