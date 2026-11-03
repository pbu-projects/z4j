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

class MergeTicketsRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create MergeTicketsRequest with canonical constructor, convenience constructor, and builder"() {
        given:
        List<Long> expectedIds = [101L, 102L]

        when:
        MergeTicketsRequest direct = new MergeTicketsRequest(expectedIds, "Merged into target", "Closing source")
        MergeTicketsRequest convenience = new MergeTicketsRequest(expectedIds)
        MergeTicketsRequest built = MergeTicketsRequestBuilder.builder()
                .ids(expectedIds)
                .targetComment("Merged into target")
                .sourceComment("Closing source")
                .build()

        then:
        verifyAll(direct) {
            ids() == expectedIds
            targetComment() == "Merged into target"
            sourceComment() == "Closing source"
        }
        verifyAll(convenience) {
            ids() == expectedIds
            targetComment() == null
            sourceComment() == null
        }
        verifyAll(built) {
            it == direct
            ids() == expectedIds
            targetComment() == "Merged into target"
            sourceComment() == "Closing source"
        }
    }

    def "should update components using record withers"() {
        given:
        MergeTicketsRequest request = new MergeTicketsRequest([1L])

        when:
        MergeTicketsRequest updated = request
                .withIds([2L, 3L])
                .withTargetComment("Updated target comment")
                .withSourceComment("Updated source comment")

        then:
        verifyAll(request) {
            ids() == [1L]
            targetComment() == null
            sourceComment() == null
        }
        verifyAll(updated) {
            ids() == [2L, 3L]
            targetComment() == "Updated target comment"
            sourceComment() == "Updated source comment"
        }
    }

    def "should round-trip serialize and deserialize MergeTicketsRequest"() {
        given:
        MergeTicketsRequest request = new MergeTicketsRequest([555L, 777L], "Target note", "Source note")

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        MergeTicketsRequest deserialized = objectMapper.readValue(json, MergeTicketsRequest.class)

        then:
        verifyAll(map) {
            containsKey("ids")
            get("ids") == [555, 777]
            get("target_comment") == "Target note"
            get("source_comment") == "Source note"
        }
        verifyAll(deserialized) {
            it == request
            ids() == [555L, 777L]
            targetComment() == "Target note"
            sourceComment() == "Source note"
        }
    }

    def "should omit null optional comments during serialization"() {
        given:
        MergeTicketsRequest request = new MergeTicketsRequest([99L])

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        MergeTicketsRequest deserialized = objectMapper.readValue(json, MergeTicketsRequest.class)

        then:
        verifyAll(map) {
            containsKey("ids")
            get("ids") == [99]
            !containsKey("target_comment")
            !containsKey("source_comment")
        }
        verifyAll(deserialized) {
            it == request
            ids() == [99L]
            targetComment() == null
            sourceComment() == null
        }
    }
}
