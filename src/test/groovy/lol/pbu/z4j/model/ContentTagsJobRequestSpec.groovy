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

class ContentTagsJobRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ContentTagsJobRequest with convenience constructor, canonical constructor, and builder"() {
        given:
        ContentTagsJob expectedJob = new ContentTagsJob("delete", ["tag-1", "tag-2"])

        when:
        ContentTagsJobRequest twoArg = new ContentTagsJobRequest("delete", ["tag-1", "tag-2"])
        ContentTagsJobRequest direct = new ContentTagsJobRequest(expectedJob)
        ContentTagsJobRequest built = ContentTagsJobRequestBuilder.builder().job(expectedJob).build()

        then:
        verifyAll(twoArg) {
            it == direct
            job() == expectedJob
            job().action() == "delete"
            job().items() == ["tag-1", "tag-2"]
        }
        verifyAll(direct) {
            job() == expectedJob
            job().action() == "delete"
            job().items() == ["tag-1", "tag-2"]
        }
        verifyAll(built) {
            it == direct
            job() == expectedJob
            job().action() == "delete"
            job().items() == ["tag-1", "tag-2"]
        }
    }

    def "should update job using record wither"() {
        given:
        ContentTagsJob originalJob = new ContentTagsJob("delete", ["tag-1"])
        ContentTagsJob newJob = new ContentTagsJob("update", ["tag-2"])
        ContentTagsJobRequest request = new ContentTagsJobRequest(originalJob)

        when:
        ContentTagsJobRequest updated = request.withJob(newJob)

        then:
        verifyAll(request) {
            job() == originalJob
            job().action() == "delete"
        }
        verifyAll(updated) {
            job() == newJob
            job().action() == "update"
        }
    }

    def "should round-trip serialize and deserialize ContentTagsJobRequest"() {
        given:
        ContentTagsJobRequest request = new ContentTagsJobRequest("delete", ["tag-10", "tag-20"])

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagsJobRequest deserialized = objectMapper.readValue(json, ContentTagsJobRequest.class)

        then:
        verifyAll(map) {
            containsKey("job")
            get("job") instanceof Map
            ((Map) get("job")).action == "delete"
            ((Map) get("job")).items == ["tag-10", "tag-20"]
        }
        verifyAll(deserialized) {
            it == request
            job() != null
            job().action() == "delete"
            job().items() == ["tag-10", "tag-20"]
        }
    }

    def "should handle null job component in constructor and wither"() {
        when:
        ContentTagsJobRequest request = new ContentTagsJobRequest(null)
        ContentTagsJob replacement = new ContentTagsJob("delete", ["tag-99"])
        ContentTagsJobRequest updated = request.withJob(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagsJobRequest deserialized = objectMapper.readValue(json, ContentTagsJobRequest.class)

        then:
        verifyAll(request) {
            job() == null
        }
        verifyAll(updated) {
            job() == replacement
            job().action() == "delete"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("job")
        }
        verifyAll(deserialized) {
            it == request
            job() == null
        }
    }
}
