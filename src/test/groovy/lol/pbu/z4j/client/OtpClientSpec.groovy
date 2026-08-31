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
class OtpClientSpec extends Z4jSpec {

    @Shared
    OtpClient adminOtpClient, agentOtpClient, userOtpClient,
              badTokenOtpClient, badUrlOtpClient

    def setupSpec() {
        adminOtpClient = adminCtx.getBean(OtpClient.class)
        agentOtpClient = agentCtx.getBean(OtpClient.class)
        userOtpClient = userCtx.getBean(OtpClient.class)
        badTokenOtpClient = badTokenCtx.getBean(OtpClient.class)
        badUrlOtpClient = badUrlCtx.getBean(OtpClient.class)
    }

    @Unroll
    def "can show OTP setting as #userType"(
            OtpClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting OTP setting"
        client.showOtpSetting().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOtpClient, "admin"],
                [agentOtpClient, "agent"],
                [userOtpClient, "end user"]
        ]
    }

    @Unroll
    def "calling OTP client with #description throws HttpClientException"(
            String description, OtpClient client) {
        when: "requesting OTP setting with invalid client configuration"
        client.showOtpSetting().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenOtpClient
        "unreachable url" | badUrlOtpClient
    }
}
