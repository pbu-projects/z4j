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

class JobStatusResultSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create JobStatusResult with constructors and builder"() {
        when:
        JobStatusResult direct = new JobStatusResult(1L, "update", "Completed", true, "Title 1", null, "Detail 1")
        JobStatusResult noArg = new JobStatusResult()
        JobStatusResult built = JobStatusResultBuilder.builder()
                .id(1L)
                .action("update")
                .status("Completed")
                .success(true)
                .title("Title 1")
                .error(null)
                .details("Detail 1")
                .build()

        then:
        verifyAll(direct) {
            id() == 1L
            action() == "update"
            status() == "Completed"
            success() == true
            title() == "Title 1"
            error() == null
            details() == "Detail 1"
        }
        verifyAll(noArg) {
            id() == null
            action() == null
            status() == null
            success() == null
            title() == null
            error() == null
            details() == null
        }
        verifyAll(built) {
            it == direct
            id() == 1L
            action() == "update"
            status() == "Completed"
            success() == true
            title() == "Title 1"
            error() == null
            details() == "Detail 1"
        }
    }

    def "should update JobStatusResult with record withers"() {
        given:
        JobStatusResult initial = new JobStatusResult()

        when:
        JobStatusResult updated = initial
                .withId(2L)
                .withAction("create")
                .withStatus("Failed")
                .withSuccess(false)
                .withTitle("Failed Title")
                .withError("Validation error")
                .withDetails("Field missing")

        then:
        verifyAll(updated) {
            id() == 2L
            action() == "create"
            status() == "Failed"
            success() == false
            title() == "Failed Title"
            error() == "Validation error"
            details() == "Field missing"
        }
    }

    def "should round-trip serialize and deserialize JobStatusResult"() {
        given:
        JobStatusResult result = new JobStatusResult(42L, "delete", "Done", true, "Ticket 42", null, "Deleted successfully")

        when:
        String json = objectMapper.writeValueAsString(result)
        Map map = objectMapper.readValue(json, Map.class)
        JobStatusResult deserialized = objectMapper.readValue(json, JobStatusResult.class)

        then:
        verifyAll(map) {
            containsKey("id")
            containsKey("action")
            containsKey("status")
            containsKey("success")
            containsKey("title")
            containsKey("details")
            get("id") == 42
            get("action") == "delete"
            get("status") == "Done"
            get("success") == true
        }
        verifyAll(deserialized) {
            it == result
            id() == 42L
            action() == "delete"
            status() == "Done"
            success() == true
            title() == "Ticket 42"
            error() == null
            details() == "Deleted successfully"
        }
    }

    def "should handle null fields in serialization"() {
        when:
        JobStatusResult empty = new JobStatusResult()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        JobStatusResult deserialized = objectMapper.readValue(json, JobStatusResult.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            id() == null
            action() == null
            status() == null
            success() == null
            title() == null
            error() == null
            details() == null
        }
    }
}
