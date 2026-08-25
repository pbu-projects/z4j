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
class OmnichannelRoutingQueuesClientSpec extends Z4jSpec {

    @Shared
    OmnichannelRoutingQueuesClient adminQueuesClient, agentQueuesClient, userQueuesClient,
                                   badTokenQueuesClient, badUrlQueuesClient

    def setupSpec() {
        adminQueuesClient = adminCtx.getBean(OmnichannelRoutingQueuesClient.class)
        agentQueuesClient = agentCtx.getBean(OmnichannelRoutingQueuesClient.class)
        userQueuesClient = userCtx.getBean(OmnichannelRoutingQueuesClient.class)
        badTokenQueuesClient = badTokenCtx.getBean(OmnichannelRoutingQueuesClient.class)
        badUrlQueuesClient = badUrlCtx.getBean(OmnichannelRoutingQueuesClient.class)
    }

    def "can list omnichannel routing queues as an admin"() {
        given: "an authenticated admin client"

        when: "requesting omnichannel routing queues list"
        adminQueuesClient.listQueues().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    def "can list queue definitions as an admin"() {
        given: "an authenticated admin client"

        when: "requesting queue definitions"
        adminQueuesClient.listQueueDefinitions().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "#userType cannot list omnichannel routing queues"(OmnichannelRoutingQueuesClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting omnichannel routing queues"
        client.listQueues().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentQueuesClient, "agent"],
                [userQueuesClient, "end user"]
        ]
    }

    @Unroll
    def "calling omnichannel routing queues client with #description throws HttpClientException"(
            String description, OmnichannelRoutingQueuesClient client) {
        when: "requesting omnichannel routing queues with invalid client configuration"
        client.listQueues().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenQueuesClient
        "unreachable url" | badUrlQueuesClient
    }
}
