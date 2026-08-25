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
import lol.pbu.z4j.model.BrandsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class BrandsClientSpec extends Z4jSpec {

    @Shared
    BrandsClient adminBrandsClient, agentBrandsClient, userBrandsClient,
                 badTokenBrandsClient, badUrlBrandsClient

    @Shared
    Integer existingBrandId

    def setupSpec() {
        adminBrandsClient = adminCtx.getBean(BrandsClient.class)
        agentBrandsClient = agentCtx.getBean(BrandsClient.class)
        userBrandsClient = userCtx.getBean(BrandsClient.class)
        badTokenBrandsClient = badTokenCtx.getBean(BrandsClient.class)
        badUrlBrandsClient = badUrlCtx.getBean(BrandsClient.class)

        BrandsResponse brands = adminBrandsClient.listBrands().block()
        if (brands?.brands && !brands.brands.isEmpty()) {
            existingBrandId = brands.brands.first().id
        }
    }

    @Unroll
    def "can list brands as an #userType"(
            BrandsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting brands list"
        client.listBrands().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminBrandsClient, "admin"],
                [agentBrandsClient, "agent"]
        ]
    }

    @Unroll
    def "can show brand by ID as an #userType"(
            BrandsClient client, String userType) {
        given: "an authenticated client for #userType and existing brand ID"

        when: "requesting brand by ID"
        if (existingBrandId != null) {
            client.showBrand(existingBrandId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminBrandsClient, "admin"],
                [agentBrandsClient, "agent"]
        ]
    }

    def "end user cannot list brands"() {
        given: "an end user client"

        when: "requesting brands as an end user"
        userBrandsClient.listBrands().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling brands client with #description throws HttpClientException"(
            String description, BrandsClient client) {
        when: "requesting brands with invalid client configuration"
        client.listBrands().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenBrandsClient
        "unreachable url" | badUrlBrandsClient
    }
}
