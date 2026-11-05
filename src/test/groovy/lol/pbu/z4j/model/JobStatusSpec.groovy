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

import io.micronaut.serde.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

class JobStatusSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create JobStatus with constructors and builder"() {
        when:
        JobStatus direct = new JobStatus(
                "job_123",
                "https://example.zendesk.com/api/v2/job_statuses/job_123.json",
                10,
                5,
                "working",
                "Processing...",
                "bulk_update",
                [[id: 1L, action: "update", success: true]]
        )
        JobStatus noArg = new JobStatus()
        JobStatus statusOnly = new JobStatus("queued")
        JobStatus statusAndMsg = new JobStatus("failed", "Error occurred")
        JobStatus built = JobStatusBuilder.builder()
                .id("job_123")
                .url("https://example.zendesk.com/api/v2/job_statuses/job_123.json")
                .total(10)
                .progress(5)
                .status("working")
                .message("Processing...")
                .jobType("bulk_update")
                .results([[id: 1L, action: "update", success: true]])
                .build()

        then:
        verifyAll(direct) {
            id() == "job_123"
            url() == "https://example.zendesk.com/api/v2/job_statuses/job_123.json"
            total() == 10
            progress() == 5
            status() == "working"
            message() == "Processing..."
            jobType() == "bulk_update"
            results().size() == 1
        }
        verifyAll(noArg) {
            id() == null
            status() == null
        }
        verifyAll(statusOnly) {
            status() == "queued"
            message() == null
        }
        verifyAll(statusAndMsg) {
            status() == "failed"
            message() == "Error occurred"
        }
        verifyAll(built) {
            it == direct
            id() == "job_123"
        }
    }

    def "should default status to completed when message is present without status in compact constructor"() {
        when:
        JobStatus defaulted = new JobStatus(null, null, null, null, null, "All done!", null, null)

        then:
        verifyAll(defaulted) {
            status() == "completed"
            message() == "All done!"
        }
    }

    def "should update JobStatus using withers and addResultsItem"() {
        given:
        JobStatus initial = new JobStatus()

        when:
        JobStatus updated = initial
                .withId("job_456")
                .withUrl("https://example.com/job_456.json")
                .withTotal(20)
                .withProgress(15)
                .withStatus("working")
                .withMessage("In progress")
                .withJobType("ticket_export")
                .withResults([[id: 10L]])
        JobStatus added = updated.addResultsItem([id: 20L])
        JobStatus fromNull = new JobStatus().addResultsItem([id: 30L])

        then:
        verifyAll(updated) {
            id() == "job_456"
            url() == "https://example.com/job_456.json"
            total() == 20
            progress() == 15
            status() == "working"
            message() == "In progress"
            jobType() == "ticket_export"
            results().size() == 1
        }
        verifyAll(added) {
            results().size() == 2
        }
        verifyAll(fromNull) {
            results().size() == 1
        }
    }

    def "should convert raw map results to typed JobStatusResult and back"() {
        given:
        JobStatus job = new JobStatus().withResults([
                [id: 101L, action: "create", status: "Created", success: true, title: "T1", details: "ok"],
                [id: "102", action: "update", status: "Failed", success: false, error: "Validation failed"],
                [id: "invalid_id", action: null, status: null, success: null, title: null, error: null, details: null],
                [id: null, success: "true"]
        ])

        when:
        List<JobStatusResult> typedResults = job.getJobStatusResults()

        then:
        verifyAll(typedResults) {
            it != null
            size() == 4
            get(0).id() == 101L
            get(0).action() == "create"
            get(0).status() == "Created"
            get(0).success() == true
            get(0).title() == "T1"
            get(0).details() == "ok"
            get(1).id() == 102L
            get(1).action() == "update"
            get(1).status() == "Failed"
            get(1).success() == false
            get(1).error() == "Validation failed"
            get(2).id() == null
            get(3).id() == null
            get(3).success() == true
        }

        when:
        JobStatus newJob = new JobStatus().withJobStatusResults(typedResults)

        then:
        verifyAll(newJob) {
            results() != null
            results().size() == 4
            results().get(0).get("id") == 101L
            results().get(1).get("id") == 102L
            getJobStatusResults().size() == 4
        }

        when:
        JobStatus nullResults = new JobStatus().withJobStatusResults(null)

        then:
        verifyAll(nullResults) {
            results() == null
            getJobStatusResults() == null
        }
    }

    def "should round-trip serialize and deserialize JobStatus"() {
        given:
        JobStatus job = new JobStatus()
                .withId("job_789")
                .withStatus("completed")
                .withTotal(5)
                .withProgress(5)

        when:
        String json = objectMapper.writeValueAsString(job)
        Map map = objectMapper.readValue(json, Map.class)
        JobStatus deserialized = objectMapper.readValue(json, JobStatus.class)

        then:
        verifyAll(map) {
            get("id") == "job_789"
            get("status") == "completed"
            get("total") == 5
            get("progress") == 5
        }
        verifyAll(deserialized) {
            it == job
            id() == "job_789"
            status() == "completed"
        }
    }

    def "should verify JobStatus constants"() {
        expect:
        verifyAll {
            JobStatus.JSON_PROPERTY_ID == "id"
            JobStatus.JSON_PROPERTY_URL == "url"
            JobStatus.JSON_PROPERTY_TOTAL == "total"
            JobStatus.JSON_PROPERTY_PROGRESS == "progress"
            JobStatus.JSON_PROPERTY_STATUS == "status"
            JobStatus.JSON_PROPERTY_MESSAGE == "message"
            JobStatus.JSON_PROPERTY_JOB_TYPE == "job_type"
            JobStatus.JSON_PROPERTY_RESULTS == "results"
        }
    }
}
