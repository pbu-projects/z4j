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
import lol.pbu.z4j.model.BrandAgentsResponse
import lol.pbu.z4j.model.BrandsResponse
import lol.pbu.z4j.model.CurrentUserResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class BrandAgentsClientSpec extends Z4jSpec {

    @Shared
    BrandAgentsClient adminBrandAgentsClient, agentBrandAgentsClient, userBrandAgentsClient,
                      badTokenBrandAgentsClient, badUrlBrandAgentsClient

    @Shared
    Long adminUserId

    @Shared
    Long existingBrandId

    @Shared
    String existingBrandAgentId

    def setupSpec() {
        adminBrandAgentsClient = adminCtx.getBean(BrandAgentsClient.class)
        agentBrandAgentsClient = agentCtx.getBean(BrandAgentsClient.class)
        userBrandAgentsClient = userCtx.getBean(BrandAgentsClient.class)
        badTokenBrandAgentsClient = badTokenCtx.getBean(BrandAgentsClient.class)
        badUrlBrandAgentsClient = badUrlCtx.getBean(BrandAgentsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id

        BrandsResponse brands = adminCtx.getBean(BrandsClient.class).listBrands().block()
        if (brands?.brands && !brands.brands.isEmpty()) {
            existingBrandId = brands.brands.first().id
        }

        BrandAgentsResponse brandAgents = adminBrandAgentsClient.listBrandAgents(null, null).block()
        if (brandAgents?.brandAgents && !brandAgents.brandAgents.isEmpty()) {
            existingBrandAgentId = brandAgents.brandAgents.first().id
        }
    }

    def "can list brand agents as an admin"() {
        given: "an authenticated admin client"

        when: "requesting brand agents list"
        adminBrandAgentsClient.listBrandAgents(null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show brand agent by ID as an admin"() {
        given: "an authenticated admin client and existing brand agent ID"

        when: "requesting brand agent by ID"
        if (existingBrandAgentId != null) {
            adminBrandAgentsClient.showBrandAgentById(existingBrandAgentId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can show user brand agent by ID as an admin"() {
        given: "an authenticated admin client, admin user ID, and existing brand agent ID"

        when: "requesting user brand agent by ID"
        if (adminUserId != null && existingBrandAgentId != null) {
            adminBrandAgentsClient.showUserBrandAgentById(adminUserId, existingBrandAgentId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list agents by brand as an admin"() {
        given: "an authenticated admin client and existing brand ID"

        when: "requesting brand agents by brand"
        if (existingBrandId != null) {
            adminBrandAgentsClient.listBrandAgentsByBrand(existingBrandId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list user brand agents as an admin"() {
        given: "an authenticated admin client and admin user ID"

        when: "requesting brand agents for user"
        if (adminUserId != null) {
            adminBrandAgentsClient.listUserBrandAgents(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list brand agents"(BrandAgentsClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting brand agents"
        client.listBrandAgents(null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentBrandAgentsClient, "agent"],
                [userBrandAgentsClient, "end user"]
        ]
    }

    @Unroll
    def "calling brand agents client with #description throws HttpClientException"(
            String description, BrandAgentsClient client) {
        when: "requesting brand agents with invalid client configuration"
        client.listBrandAgents(null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenBrandAgentsClient
        "unreachable url" | badUrlBrandAgentsClient
    }
}
