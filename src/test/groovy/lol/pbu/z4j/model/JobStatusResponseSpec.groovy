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
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class JobStatusResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create JobStatusResponse with canonical constructor and builder"() {
        given:
        JobStatus expected = new JobStatus().withId("job_101").withStatus("queued").withProgress(0)

        when:
        JobStatusResponse direct = new JobStatusResponse(expected)
        JobStatusResponse built = JobStatusResponseBuilder.builder().jobStatus(expected).build()

        then:
        verifyAll(direct) {
            jobStatus == expected
            jobStatus.id == "job_101"
            jobStatus.status == "queued"
            jobStatus.progress == 0
        }
        verifyAll(built) {
            jobStatus == expected
            jobStatus.id == "job_101"
            jobStatus.status == "queued"
            jobStatus.progress == 0
        }
    }

    def "should update jobStatus using record wither"() {
        given:
        JobStatus original = new JobStatus().withId("job_1").withStatus("queued")
        JobStatus updatedJobStatus = new JobStatus().withId("job_2").withStatus("working")
        JobStatusResponse response = new JobStatusResponse(original)

        when:
        JobStatusResponse updated = response.withJobStatus(updatedJobStatus)

        then:
        verifyAll(response) {
            jobStatus == original
            jobStatus.status == "queued"
        }
        verifyAll(updated) {
            jobStatus == updatedJobStatus
            jobStatus.status == "working"
        }
    }

    def "should round-trip serialize and deserialize JobStatusResponse"() {
        given:
        JobStatus expected = new JobStatus().withId("job_555").withStatus("completed").withProgress(100)
        JobStatusResponse response = new JobStatusResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        JobStatusResponse deserialized = objectMapper.readValue(json, JobStatusResponse.class)

        then:
        verifyAll(map) {
            containsKey("job_status")
            get("job_status") instanceof Map
            ((Map) get("job_status")).id == "job_555"
            ((Map) get("job_status")).status == "completed"
        }
        verifyAll(deserialized) {
            it == response
            jobStatus != null
            jobStatus.id == "job_555"
            jobStatus.status == "completed"
            jobStatus.progress == 100
        }
    }

    def "should handle null jobStatus component by defaulting to completed status"() {
        when:
        JobStatusResponse response = new JobStatusResponse(null)
        JobStatus replacement = new JobStatus().withId("job_99").withStatus("failed")
        JobStatusResponse updated = response.withJobStatus(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        JobStatusResponse deserialized = objectMapper.readValue(json, JobStatusResponse.class)

        then:
        verifyAll(response) {
            jobStatus() != null
            jobStatus().status == "completed"
        }
        verifyAll(updated) {
            jobStatus() == replacement
            jobStatus().id == "job_99"
        }
        verifyAll(map) {
            containsKey("job_status")
            ((Map) get("job_status")).status == "completed"
        }
        verifyAll(deserialized) {
            jobStatus() != null
            jobStatus().status == "completed"
        }
    }

    def "should populate completed jobStatus when message is provided without jobStatus"() {
        given:
        String messageText = "Tickets are being restored."

        when:
        JobStatusResponse direct = new JobStatusResponse(null, messageText)
        JobStatusResponse built = JobStatusResponseBuilder.builder().message(messageText).build()
        String json = objectMapper.writeValueAsString([message: messageText])
        JobStatusResponse deserialized = objectMapper.readValue(json, JobStatusResponse.class)

        then:
        verifyAll(direct) {
            message == messageText
            jobStatus != null
            jobStatus.status == "completed"
            jobStatus.message == messageText
        }
        verifyAll(built) {
            message == messageText
            jobStatus != null
            jobStatus.status == "completed"
            jobStatus.message == messageText
        }
        verifyAll(deserialized) {
            message == messageText
            jobStatus != null
            jobStatus.status == "completed"
            jobStatus.message == messageText
        }
    }
}
