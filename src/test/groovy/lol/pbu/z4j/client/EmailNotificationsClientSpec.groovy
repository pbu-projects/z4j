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
import lol.pbu.z4j.model.ListEmailNotificationsFilterParameter
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class EmailNotificationsClientSpec extends Z4jSpec {

    @Shared
    EmailNotificationsClient adminEmailClient, agentEmailClient, userEmailClient,
                             badTokenEmailClient, badUrlEmailClient

    @Shared
    ListEmailNotificationsFilterParameter filter = new ListEmailNotificationsFilterParameter().ticketId(1)

    def setupSpec() {
        adminEmailClient = adminCtx.getBean(EmailNotificationsClient.class)
        agentEmailClient = agentCtx.getBean(EmailNotificationsClient.class)
        userEmailClient = userCtx.getBean(EmailNotificationsClient.class)
        badTokenEmailClient = badTokenCtx.getBean(EmailNotificationsClient.class)
        badUrlEmailClient = badUrlCtx.getBean(EmailNotificationsClient.class)
    }

    @Unroll
    def "can list email notifications as an #userType"(
            EmailNotificationsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting email notifications list"
        client.listEmailNotifications(filter, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminEmailClient, "admin"],
                [agentEmailClient, "agent"]
        ]
    }

    def "end user cannot list email notifications"() {
        given: "an end user client"

        when: "requesting email notifications as an end user"
        userEmailClient.listEmailNotifications(filter, null, null).block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling email notifications client with #description throws HttpClientException"(
            String description, EmailNotificationsClient client) {
        when: "requesting email notifications with invalid client configuration"
        client.listEmailNotifications(filter, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenEmailClient
        "unreachable url" | badUrlEmailClient
    }
}
