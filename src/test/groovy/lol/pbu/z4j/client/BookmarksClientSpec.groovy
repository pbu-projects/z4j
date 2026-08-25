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
class BookmarksClientSpec extends Z4jSpec {

    @Shared
    BookmarksClient adminBookmarksClient, agentBookmarksClient, userBookmarksClient,
                    badTokenBookmarksClient, badUrlBookmarksClient

    def setupSpec() {
        adminBookmarksClient = adminCtx.getBean(BookmarksClient.class)
        agentBookmarksClient = agentCtx.getBean(BookmarksClient.class)
        userBookmarksClient = userCtx.getBean(BookmarksClient.class)
        badTokenBookmarksClient = badTokenCtx.getBean(BookmarksClient.class)
        badUrlBookmarksClient = badUrlCtx.getBean(BookmarksClient.class)
    }

    @Unroll
    def "can list bookmarks as an #userType"(
            BookmarksClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting bookmarks list"
        client.listBookmarks().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminBookmarksClient, "admin"],
                [agentBookmarksClient, "agent"]
        ]
    }

    def "end user cannot list bookmarks"() {
        given: "an end user client"

        when: "requesting bookmarks as an end user"
        userBookmarksClient.listBookmarks().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling bookmarks client with #description throws HttpClientException"(
            String description, BookmarksClient client) {
        when: "requesting bookmarks with invalid client configuration"
        client.listBookmarks().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenBookmarksClient
        "unreachable url" | badUrlBookmarksClient
    }
}
