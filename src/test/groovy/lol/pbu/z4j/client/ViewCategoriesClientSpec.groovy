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
@Tag("ticketing")
class ViewCategoriesClientSpec extends Z4jSpec {

    @Shared
    ViewCategoriesClient adminViewCategoriesClient, agentViewCategoriesClient, userViewCategoriesClient,
                         badTokenViewCategoriesClient, badUrlViewCategoriesClient

    def setupSpec() {
        adminViewCategoriesClient = adminCtx.getBean(ViewCategoriesClient.class)
        agentViewCategoriesClient = agentCtx.getBean(ViewCategoriesClient.class)
        userViewCategoriesClient = userCtx.getBean(ViewCategoriesClient.class)
        badTokenViewCategoriesClient = badTokenCtx.getBean(ViewCategoriesClient.class)
        badUrlViewCategoriesClient = badUrlCtx.getBean(ViewCategoriesClient.class)
    }

    @Unroll
    def "listing view categories returns 403 Forbidden for #userType"(
            ViewCategoriesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting view categories list"
        client.listViewCategories(null).block()

        then: "a 403 Forbidden exception is thrown (sandbox limitation or legacy feature)"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [adminViewCategoriesClient, "admin"],
                [agentViewCategoriesClient, "agent"],
                [userViewCategoriesClient, "end user"]
        ]
    }

    @Unroll
    def "calling view categories client with #description throws HttpClientException"(
            String description, ViewCategoriesClient client) {
        when: "requesting view categories with invalid client configuration"
        client.listViewCategories(null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenViewCategoriesClient
        "unreachable url" | badUrlViewCategoriesClient
    }
}
