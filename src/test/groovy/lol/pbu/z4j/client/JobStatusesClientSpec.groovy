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
import lol.pbu.z4j.model.JobStatusesResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class JobStatusesClientSpec extends Z4jSpec {

    @Shared
    JobStatusesClient adminJobStatusesClient, agentJobStatusesClient, userJobStatusesClient,
                      badTokenJobStatusesClient, badUrlJobStatusesClient

    @Shared
    String existingJobStatusId

    def setupSpec() {
        adminJobStatusesClient = adminCtx.getBean(JobStatusesClient.class)
        agentJobStatusesClient = agentCtx.getBean(JobStatusesClient.class)
        userJobStatusesClient = userCtx.getBean(JobStatusesClient.class)
        badTokenJobStatusesClient = badTokenCtx.getBean(JobStatusesClient.class)
        badUrlJobStatusesClient = badUrlCtx.getBean(JobStatusesClient.class)

        JobStatusesResponse jobs = adminJobStatusesClient.listJobStatuses().block()
        if (jobs?.jobStatuses && !jobs.jobStatuses.isEmpty()) {
            existingJobStatusId = jobs.jobStatuses.first().id
        }
    }

    @Unroll
    def "can list job statuses as an #userType"(
            JobStatusesClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting job statuses list"
        client.listJobStatuses().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminJobStatusesClient, "admin"],
                [agentJobStatusesClient, "agent"]
        ]
    }

    @Unroll
    def "can show job status by ID as an #userType"(
            JobStatusesClient client, String userType) {
        given: "an authenticated client for #userType and existing job status ID"

        when: "requesting job status by ID"
        if (existingJobStatusId != null) {
            client.showJobStatus(existingJobStatusId).block()
        }

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminJobStatusesClient, "admin"],
                [agentJobStatusesClient, "agent"]
        ]
    }

    def "end user cannot list job statuses"() {
        given: "an end user client"

        when: "requesting job statuses as an end user"
        userJobStatusesClient.listJobStatuses().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling job statuses client with #description throws HttpClientException"(
            String description, JobStatusesClient client) {
        when: "requesting job statuses with invalid client configuration"
        client.listJobStatuses().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenJobStatusesClient
        "unreachable url" | badUrlJobStatusesClient
    }
}
