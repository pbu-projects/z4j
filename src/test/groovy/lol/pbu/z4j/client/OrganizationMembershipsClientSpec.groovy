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
import lol.pbu.z4j.model.OrganizationMembershipsResponse
import lol.pbu.z4j.model.OrganizationsResponse
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class OrganizationMembershipsClientSpec extends Z4jSpec {

    @Shared
    OrganizationMembershipsClient adminOrgMembershipsClient, agentOrgMembershipsClient, userOrgMembershipsClient,
                                  badTokenOrgMembershipsClient, badUrlOrgMembershipsClient

    @Shared
    Long adminUserId

    @Shared
    Long existingOrgId

    @Shared
    Long existingMembershipId

    def setupSpec() {
        adminOrgMembershipsClient = adminCtx.getBean(OrganizationMembershipsClient.class)
        agentOrgMembershipsClient = agentCtx.getBean(OrganizationMembershipsClient.class)
        userOrgMembershipsClient = userCtx.getBean(OrganizationMembershipsClient.class)
        badTokenOrgMembershipsClient = badTokenCtx.getBean(OrganizationMembershipsClient.class)
        badUrlOrgMembershipsClient = badUrlCtx.getBean(OrganizationMembershipsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id

        OrganizationsResponse orgs = adminCtx.getBean(OrganizationsClient.class).listOrganizations().block()
        if (orgs?.organizations && !orgs.organizations.isEmpty()) {
            existingOrgId = orgs.organizations.first().id
        }

        OrganizationMembershipsResponse memberships = adminOrgMembershipsClient.listOrganizationMemberships().block()
        if (memberships?.organizationMemberships && !memberships.organizationMemberships.isEmpty()) {
            existingMembershipId = memberships.organizationMemberships.first().id
        }
    }

    @Unroll
    def "can list organization memberships for all roles as #userType"(
            OrganizationMembershipsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting organization memberships list"
        client.listOrganizationMemberships().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrgMembershipsClient, "admin"],
                [agentOrgMembershipsClient, "agent"],
                [userOrgMembershipsClient, "end user"]
        ]
    }

    @Unroll
    def "can list organization memberships by organization as an #userType"(
            OrganizationMembershipsClient client, String userType) {
        given: "an authenticated client for #userType and existing organization ID"

        when: "requesting memberships for organization"
        if (existingOrgId != null) {
            client.listOrganizationMembershipsByOrganization(existingOrgId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrgMembershipsClient, "admin"],
                [agentOrgMembershipsClient, "agent"]
        ]
    }

    @Unroll
    def "can list organization memberships by user as an #userType"(
            OrganizationMembershipsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting memberships for user"
        if (adminUserId != null) {
            client.listUserOrganizationMemberships(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrgMembershipsClient, "admin"],
                [agentOrgMembershipsClient, "agent"]
        ]
    }

    @Unroll
    def "can show organization membership by ID as an #userType"(
            OrganizationMembershipsClient client, String userType) {
        given: "an authenticated client for #userType and membership ID"

        when: "requesting organization membership by ID"
        if (existingMembershipId != null) {
            client.showOrganizationMembershipById(existingMembershipId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrgMembershipsClient, "admin"],
                [agentOrgMembershipsClient, "agent"]
        ]
    }

    @Unroll
    def "can show organization membership by user as an #userType"(
            OrganizationMembershipsClient client, String userType) {
        given: "an authenticated client for #userType, admin user ID, and membership ID"

        when: "requesting user organization membership by ID"
        if (adminUserId != null && existingMembershipId != null) {
            client.showOrganizationMembershipByUserId(adminUserId, existingMembershipId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrgMembershipsClient, "admin"],
                [agentOrgMembershipsClient, "agent"]
        ]
    }

    @Unroll
    def "calling organization memberships client with #description throws HttpClientException"(
            String description, OrganizationMembershipsClient client) {
        when: "requesting organization memberships with invalid client configuration"
        client.listOrganizationMemberships().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenOrgMembershipsClient
        "unreachable url" | badUrlOrgMembershipsClient
    }
}
