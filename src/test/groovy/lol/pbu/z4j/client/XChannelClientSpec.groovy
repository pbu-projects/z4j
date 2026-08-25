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
class XChannelClientSpec extends Z4jSpec {

    @Shared
    XChannelClient adminXChannelClient, agentXChannelClient, userXChannelClient,
                   badTokenXChannelClient, badUrlXChannelClient

    def setupSpec() {
        adminXChannelClient = adminCtx.getBean(XChannelClient.class)
        agentXChannelClient = agentCtx.getBean(XChannelClient.class)
        userXChannelClient = userCtx.getBean(XChannelClient.class)
        badTokenXChannelClient = badTokenCtx.getBean(XChannelClient.class)
        badUrlXChannelClient = badUrlCtx.getBean(XChannelClient.class)
    }

    @Unroll
    def "can list monitored twitter handles as an #userType"(
            XChannelClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting monitored twitter handles list"
        client.listMonitoredTwitterHandles().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminXChannelClient, "admin"],
                [agentXChannelClient, "agent"]
        ]
    }

    def "end user cannot list monitored twitter handles"() {
        given: "an end user client"

        when: "requesting monitored twitter handles as an end user"
        userXChannelClient.listMonitoredTwitterHandles().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling x channel client with #description throws HttpClientException"(
            String description, XChannelClient client) {
        when: "requesting monitored twitter handles with invalid client configuration"
        client.listMonitoredTwitterHandles().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenXChannelClient
        "unreachable url" | badUrlXChannelClient
    }
}
