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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CurrentUserResponse
import lol.pbu.z4j.model.OrganizationsResponse
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class OrganizationSubscriptionsClientSpec extends Z4jSpec {

    @Shared
    OrganizationSubscriptionsClient adminSubscriptionsClient, agentSubscriptionsClient, userSubscriptionsClient,
                                    badTokenSubscriptionsClient, badUrlSubscriptionsClient

    @Shared
    Long adminUserId

    @Shared
    Long existingOrgId

    def setupSpec() {
        adminSubscriptionsClient = adminCtx.getBean(OrganizationSubscriptionsClient.class)
        agentSubscriptionsClient = agentCtx.getBean(OrganizationSubscriptionsClient.class)
        userSubscriptionsClient = userCtx.getBean(OrganizationSubscriptionsClient.class)
        badTokenSubscriptionsClient = badTokenCtx.getBean(OrganizationSubscriptionsClient.class)
        badUrlSubscriptionsClient = badUrlCtx.getBean(OrganizationSubscriptionsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id

        OrganizationsResponse orgs = adminCtx.getBean(OrganizationsClient.class).listOrganizations().block()
        if (orgs?.organizations && !orgs.organizations.isEmpty()) {
            existingOrgId = orgs.organizations.first().id
        }
    }

    @Unroll
    def "can list organization subscriptions for all roles as #userType"(
            OrganizationSubscriptionsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting organization subscriptions list"
        client.listOrganizationSubscriptions().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSubscriptionsClient, "admin"],
                [agentSubscriptionsClient, "agent"],
                [userSubscriptionsClient, "end user"]
        ]
    }

    @Unroll
    def "can list organization subscriptions by organization as an #userType"(
            OrganizationSubscriptionsClient client, String userType) {
        given: "an authenticated client for #userType and existing organization ID"

        when: "requesting subscriptions for organization"
        if (existingOrgId != null) {
            client.listOrganizationSubscriptionsByOrganization(existingOrgId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSubscriptionsClient, "admin"],
                [agentSubscriptionsClient, "agent"]
        ]
    }

    @Unroll
    def "can list user organization subscriptions as an #userType"(
            OrganizationSubscriptionsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting subscriptions for user"
        if (adminUserId != null) {
            client.listUserOrganizationSubscriptions(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminSubscriptionsClient, "admin"],
                [agentSubscriptionsClient, "agent"]
        ]
    }

    @Unroll
    def "calling organization subscriptions client with #description throws HttpClientException"(
            String description, OrganizationSubscriptionsClient client) {
        when: "requesting organization subscriptions with invalid client configuration"
        client.listOrganizationSubscriptions().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenSubscriptionsClient
        "unreachable url" | badUrlSubscriptionsClient
    }
}
