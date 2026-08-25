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
import lol.pbu.z4j.model.CreateOrganizationRequest
import lol.pbu.z4j.model.CurrentUserResponse
import lol.pbu.z4j.model.OrganizationObject
import lol.pbu.z4j.model.OrganizationResponse
import lol.pbu.z4j.model.OrganizationsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class OrganizationsClientSpec extends Z4jSpec {

    @Shared
    OrganizationsClient adminOrganizationsClient, agentOrganizationsClient, userOrganizationsClient,
                        badTokenOrganizationsClient, badUrlOrganizationsClient

    @Shared
    Integer adminUserId

    @Shared
    Integer existingOrgId

    def setupSpec() {
        adminOrganizationsClient = adminCtx.getBean(OrganizationsClient.class)
        agentOrganizationsClient = agentCtx.getBean(OrganizationsClient.class)
        userOrganizationsClient = userCtx.getBean(OrganizationsClient.class)
        badTokenOrganizationsClient = badTokenCtx.getBean(OrganizationsClient.class)
        badUrlOrganizationsClient = badUrlCtx.getBean(OrganizationsClient.class)

        CurrentUserResponse me = adminCtx.getBean(UsersClient.class).showCurrentUser().block()
        adminUserId = me?.user?.id

        OrganizationsResponse orgs = adminOrganizationsClient.listOrganizations().block()
        if (orgs?.organizations && !orgs.organizations.isEmpty()) {
            existingOrgId = orgs.organizations.first().id
        }
    }

    @Unroll
    def "can count organizations as an #userType"(OrganizationsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting organizations count"
        client.countOrganizations().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrganizationsClient, "admin"],
                [agentOrganizationsClient, "agent"]
        ]
    }

    @Unroll
    def "can list organizations as an #userType"(OrganizationsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting organizations list"
        client.listOrganizations().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrganizationsClient, "admin"],
                [agentOrganizationsClient, "agent"]
        ]
    }

    @Unroll
    def "can autocomplete organizations as an #userType with name=#name"(
            OrganizationsClient client, String userType, String name) {
        given: "an authenticated client for #userType"

        when: "searching for organizations by prefix"
        client.autocompleteOrganizations(name, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], name] << [
                [[adminOrganizationsClient, "admin"], [agentOrganizationsClient, "agent"]],
                ["te"]
        ].combinations()
    }

    @Unroll
    def "can count user organizations as an #userType"(OrganizationsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting count of user organizations"
        if (adminUserId != null) {
            client.countUserOrganizations(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrganizationsClient, "admin"],
                [agentOrganizationsClient, "agent"]
        ]
    }

    @Unroll
    def "can list user organizations as an #userType"(OrganizationsClient client, String userType) {
        given: "an authenticated client for #userType and admin user ID"

        when: "requesting list of user organizations"
        if (adminUserId != null) {
            client.listUserOrganizations(adminUserId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrganizationsClient, "admin"],
                [agentOrganizationsClient, "agent"]
        ]
    }

    @Unroll
    def "can search organizations as an #userType with name=#name"(
            OrganizationsClient client, String userType, String name) {
        given: "an authenticated client for #userType"

        when: "searching organizations by name"
        client.searchOrganizations(null, name).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [[client, userType], name] << [
                [[adminOrganizationsClient, "admin"], [agentOrganizationsClient, "agent"]],
                ["Test"]
        ].combinations()
    }

    @Unroll
    def "can show many organizations as an #userType"(OrganizationsClient client, String userType) {
        given: "an authenticated client for #userType and existing organization ID"

        when: "requesting multiple organizations by ID list"
        if (existingOrgId != null) {
            client.showManyOrganizations(existingOrgId.toString(), null).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminOrganizationsClient, "admin"],
                [agentOrganizationsClient, "agent"]
        ]
    }

    def "can perform organization lifecycle as an admin"() {
        given: "an organization payload with unique name"
        String orgName = faker.company().name() + " " + UUID.randomUUID().toString().substring(0, 8)
        CreateOrganizationRequest createRequest = new CreateOrganizationRequest(
                new OrganizationObject().setName(orgName).setDetails("Test Org created by z4j")
        )
        Integer createdOrgId = null

        when: "creating a new organization as admin"
        OrganizationResponse created = adminOrganizationsClient.createOrganization(createRequest).block()
        createdOrgId = created?.organization?.id

        then: "organization is created successfully"
        noExceptionThrown()
        createdOrgId != null

        when: "retrieving the created organization by ID"
        adminOrganizationsClient.showOrganization(createdOrgId).block()

        then: "organization details deserialize successfully"
        noExceptionThrown()

        when: "retrieving related information for the organization"
        adminOrganizationsClient.organizationRelated(createdOrgId).block()

        then: "related info deserializes successfully"
        noExceptionThrown()

        when: "updating the organization details"
        CreateOrganizationRequest updateRequest = new CreateOrganizationRequest(
                new OrganizationObject().setName(orgName).setDetails("Updated details")
        )
        adminOrganizationsClient.updateOrganization(createdOrgId, updateRequest).block()

        then: "organization updates successfully"
        noExceptionThrown()

        cleanup: "delete the created test organization from the instance"
        if (createdOrgId != null) {
            try {
                adminOrganizationsClient.deleteOrganization(createdOrgId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "end user cannot list or count organizations"() {
        given: "an end user client"

        when: "requesting organizations count as an end user"
        userOrganizationsClient.countOrganizations().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling organizations client with #description throws HttpClientException"(
            String description, OrganizationsClient client) {
        when: "requesting organizations with invalid client configuration"
        client.countOrganizations().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenOrganizationsClient
        "unreachable url" | badUrlOrganizationsClient
    }
}
