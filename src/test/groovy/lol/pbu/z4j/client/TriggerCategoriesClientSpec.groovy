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
import lol.pbu.z4j.model.ListTriggerCategories200Response
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class TriggerCategoriesClientSpec extends Z4jSpec {

    @Shared
    TriggerCategoriesClient adminTriggerCategoriesClient, agentTriggerCategoriesClient, userTriggerCategoriesClient,
                            badTokenTriggerCategoriesClient, badUrlTriggerCategoriesClient

    @Shared
    String existingCategoryId

    def setupSpec() {
        adminTriggerCategoriesClient = adminCtx.getBean(TriggerCategoriesClient.class)
        agentTriggerCategoriesClient = agentCtx.getBean(TriggerCategoriesClient.class)
        userTriggerCategoriesClient = userCtx.getBean(TriggerCategoriesClient.class)
        badTokenTriggerCategoriesClient = badTokenCtx.getBean(TriggerCategoriesClient.class)
        badUrlTriggerCategoriesClient = badUrlCtx.getBean(TriggerCategoriesClient.class)

        ListTriggerCategories200Response categories = adminTriggerCategoriesClient.listTriggerCategories(null, null, null).block()
        if (categories?.triggerCategories && !categories.triggerCategories.isEmpty()) {
            existingCategoryId = categories.triggerCategories.first().id
        }
    }

    @Unroll
    def "can list trigger categories as an #userType"(TriggerCategoriesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting trigger categories list"
        client.listTriggerCategories(null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTriggerCategoriesClient, "admin"],
                [agentTriggerCategoriesClient, "agent"]
        ]
    }

    @Unroll
    def "can show trigger category by ID as an #userType"(TriggerCategoriesClient client, String userType) {
        given: "an authenticated client for #userType and existing category ID"

        when: "requesting trigger category by ID"
        if (existingCategoryId != null) {
            client.showTriggerCategoryById(existingCategoryId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminTriggerCategoriesClient, "admin"],
                [agentTriggerCategoriesClient, "agent"]
        ]
    }

    def "end user cannot list trigger categories"() {
        given: "an end user client"

        when: "requesting trigger categories as an end user"
        userTriggerCategoriesClient.listTriggerCategories(null, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling trigger categories client with #description throws HttpClientException"(
            String description, TriggerCategoriesClient client) {
        when: "requesting trigger categories with invalid client configuration"
        client.listTriggerCategories(null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenTriggerCategoriesClient
        "unreachable url" | badUrlTriggerCategoriesClient
    }
}
