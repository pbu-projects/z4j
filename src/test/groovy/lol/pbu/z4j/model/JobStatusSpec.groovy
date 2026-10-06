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
package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec

class JobStatusSpec extends Z4jSpec {

    def "should instantiate and set properties on JobStatus"() {
        given:
        def job = new JobStatus()

        when:
        job.setId("job_123")
           .setUrl("https://example.zendesk.com/api/v2/job_statuses/job_123.json")
           .setTotal(5)
           .setProgress(3)
           .setStatus("working")
           .setMessage("Processing 3 of 5")
           .setJobType("ticket_bulk_update")
           .setResults([[id: 1L, action: "update", success: true]])

        then:
        job.id == "job_123"
        job.url == "https://example.zendesk.com/api/v2/job_statuses/job_123.json"
        job.total == 5
        job.progress == 3
        job.status == "working"
        job.message == "Processing 3 of 5"
        job.jobType == "ticket_bulk_update"
        job.results.size() == 1
        job.results[0].success == true
    }

    def "should instantiate JobStatusResponse"() {
        given:
        def job = new JobStatus().setId("job_abc").setStatus("completed")

        when:
        def response = new JobStatusResponse(job)

        then:
        response.jobStatus != null
        response.jobStatus.id == "job_abc"
        response.jobStatus.status == "completed"
    }

    def "should instantiate and manipulate JobStatusResult"() {
        given:
        def result = new JobStatusResult()

        when:
        result.setId(42L)
              .setAction("update")
              .setStatus("Updated")
              .setSuccess(true)
              .setTitle("Ticket title")
              .setError(null)
              .setDetails("All good")

        then:
        result.id == 42L
        result.action == "update"
        result.status == "Updated"
        result.success == true
        result.title == "Ticket title"
        result.error == null
        result.details == "All good"
    }

    def "should convert raw map results to typed JobStatusResult and vice versa"() {
        given: "a JobStatus with raw map results"
        def job = new JobStatus()
        job.setResults([
                [id: 101L, action: "create", status: "Created", success: true, title: "T1", details: "ok"],
                [id: "102", action: "update", status: "Failed", success: false, error: "Validation failed"]
        ])

        when: "getting typed job status results"
        List<JobStatusResult> typedResults = job.getJobStatusResults()

        then: "results are converted to strongly-typed models"
        typedResults != null
        typedResults.size() == 2
        typedResults[0].id == 101L
        typedResults[0].action == "create"
        typedResults[0].status == "Created"
        typedResults[0].success == true
        typedResults[0].title == "T1"
        typedResults[0].details == "ok"
        typedResults[1].id == 102L
        typedResults[1].action == "update"
        typedResults[1].status == "Failed"
        typedResults[1].success == false
        typedResults[1].error == "Validation failed"

        when: "setting typed job status results back"
        def newJob = new JobStatus()
        newJob.setJobStatusResults(typedResults)

        then: "underlying map results are populated properly"
        newJob.results != null
        newJob.results.size() == 2
        newJob.results[0].id == 101L
        newJob.results[1].id == 102L
        newJob.getJobStatusResults().size() == 2

        when: "handling null results"
        job.setResults(null)
        newJob.setJobStatusResults(null)

        then:
        job.getJobStatusResults() == null
        newJob.results == null
    }
}
