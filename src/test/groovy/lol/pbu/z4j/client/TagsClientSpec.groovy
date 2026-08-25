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
import lol.pbu.z4j.model.CurrentUserResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class TagsClientSpec extends Z4jSpec {

    @Shared
    TagsClient adminTagsClient, agentTagsClient, userTagsClient,
               badTokenTagsClient, badUrlTagsClient

    @Shared
    Integer adminUserId

    def setupSpec() {
        adminTagsClient = adminCtx.getBean(TagsClient.class)
        agentTagsClient = agentCtx.getBean(TagsClient.class)
        userTagsClient = userCtx.getBean(TagsClient.class)
        badTokenTagsClient = badTokenCtx.getBean(TagsClient.class)
        badUrlTagsClient = badUrlCtx.getBean(TagsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id
    }

    @Unroll
    def "can list tags as an #userType"(
            TagsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting tags list"
        client.listTags().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTagsClient, "admin"],
                [agentTagsClient, "agent"]
        ]
    }

    @Unroll
    def "can count tags as an #userType"(
            TagsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting tags count"
        client.countTags().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTagsClient, "admin"],
                [agentTagsClient, "agent"]
        ]
    }

    @Unroll
    def "can autocomplete tags as an #userType"(
            TagsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting tag autocomplete"
        client.autocompleteTags("test").block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTagsClient, "admin"],
                [agentTagsClient, "agent"]
        ]
    }

    @Unroll
    def "can list user tags as an #userType"(
            TagsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting user tags"
        if (adminUserId != null) {
            client.listUserTags(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTagsClient, "admin"],
                [agentTagsClient, "agent"]
        ]
    }

    def "end user cannot list tags"() {
        given: "an end user client"

        when: "requesting tags as an end user"
        userTagsClient.listTags().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling tags client with #description throws HttpClientException"(
            String description, TagsClient client) {
        when: "requesting tags with invalid client configuration"
        client.listTags().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTagsClient
        "unreachable url" | badUrlTagsClient
    }
}
