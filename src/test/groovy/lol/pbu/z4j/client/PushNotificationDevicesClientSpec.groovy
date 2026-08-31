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
@Tag("admin")
class PushNotificationDevicesClientSpec extends Z4jSpec {

    @Shared
    PushNotificationDevicesClient adminPushDevicesClient, agentPushDevicesClient, userPushDevicesClient,
                                  badTokenPushDevicesClient, badUrlPushDevicesClient

    def setupSpec() {
        adminPushDevicesClient = adminCtx.getBean(PushNotificationDevicesClient.class)
        agentPushDevicesClient = agentCtx.getBean(PushNotificationDevicesClient.class)
        userPushDevicesClient = userCtx.getBean(PushNotificationDevicesClient.class)
        badTokenPushDevicesClient = badTokenCtx.getBean(PushNotificationDevicesClient.class)
        badUrlPushDevicesClient = badUrlCtx.getBean(PushNotificationDevicesClient.class)
    }

    @Unroll
    def "#userType cannot unregister push notification devices"(
            PushNotificationDevicesClient client, String userType) {
        given: "an unauthorized client for #userType"

        when: "requesting push notification device unregistration"
        client.pushNotificationDevices(null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN

        where:
        [client, userType] << [
                [agentPushDevicesClient, "agent"],
                [userPushDevicesClient, "end user"]
        ]
    }

    @Unroll
    def "calling push notification devices client with #description throws HttpClientException"(
            String description, PushNotificationDevicesClient client) {
        when: "requesting push notification devices with invalid client configuration"
        client.pushNotificationDevices(null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenPushDevicesClient
        "unreachable url" | badUrlPushDevicesClient
    }
}
