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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("admin")
class AccessClientSpec extends Z4jSpec {

    @Shared
    AccessClient adminAccessClient, agentAccessClient, userAccessClient,
                 badTokenAccessClient, badUrlAccessClient

    def setupSpec() {
        adminAccessClient = adminCtx.getBean(AccessClient.class)
        agentAccessClient = agentCtx.getBean(AccessClient.class)
        userAccessClient = userCtx.getBean(AccessClient.class)
        badTokenAccessClient = badTokenCtx.getBean(AccessClient.class)
        badUrlAccessClient = badUrlCtx.getBean(AccessClient.class)
    }

    @Unroll
    def "calling access client with #description throws HttpClientException"(
            String description, AccessClient client) {
        when: "attempting login get with invalid client configuration"
        client.loginGet().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAccessClient
        "unreachable url" | badUrlAccessClient
    }

    @Unroll
    def "can call loginGet as #userType"(String userType, AccessClient client) {
        when: "calling loginGet"
        client.loginGet().block()

        then: "we receive a 403 from Cloudflare protection on the web endpoint"
        def e = thrown(io.micronaut.http.client.exceptions.HttpClientResponseException)
        e.status == io.micronaut.http.HttpStatus.FORBIDDEN

        where:
        [userType, client] << [["admin", adminAccessClient], ["agent", agentAccessClient], ["user", userAccessClient]]
    }

    @Unroll
    def "can call loginPost as #userType"(String userType, AccessClient client) {
        when: "calling loginPost"
        def param = new lol.pbu.z4j.model.LoginPostUserParameter()
        param.setEmail("test@example.com")
        param.setPassword("test")
        client.loginPost(param).block()

        then: "we receive an HTTP error (400/403) depending on Cloudflare / form validation"
        def e = thrown(io.micronaut.http.client.exceptions.HttpClientResponseException)
        e.status == io.micronaut.http.HttpStatus.FORBIDDEN || e.status == io.micronaut.http.HttpStatus.BAD_REQUEST

        where:
        [userType, client] << [["admin", adminAccessClient], ["agent", agentAccessClient], ["user", userAccessClient]]
    }
}
