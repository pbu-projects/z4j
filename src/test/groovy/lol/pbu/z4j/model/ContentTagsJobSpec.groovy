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

class ContentTagsJobSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ContentTagsJob with convenience constructor, canonical constructor, and builder"() {
        when:
        ContentTagsJob twoArg = new ContentTagsJob("delete", ["tag-1", "tag-2"])
        ContentTagsJob direct = new ContentTagsJob(
                "update",
                ["tag-1", "tag-2"],
                ["billing", "support"],
                [101L, 102L],
                [priority: "high"]
        )
        ContentTagsJob built = ContentTagsJobBuilder.builder()
                .action("update")
                .items(["tag-1", "tag-2"])
                .tags(["billing", "support"])
                .articleIds([101L, 102L])
                .attributes([priority: "high"])
                .build()

        then:
        verifyAll(twoArg) {
            action() == "delete"
            items() == ["tag-1", "tag-2"]
            tags() == null
            articleIds() == null
            attributes() == null
        }
        verifyAll(direct) {
            action() == "update"
            items() == ["tag-1", "tag-2"]
            tags() == ["billing", "support"]
            articleIds() == [101L, 102L]
            attributes() == [priority: "high"]
        }
        verifyAll(built) {
            it == direct
            action() == "update"
            items() == ["tag-1", "tag-2"]
            tags() == ["billing", "support"]
            articleIds() == [101L, 102L]
            attributes() == [priority: "high"]
        }
    }

    def "should update components using record withers"() {
        given:
        ContentTagsJob original = new ContentTagsJob("delete", ["tag-1"])

        when:
        ContentTagsJob updated = original
                .withAction("merge")
                .withItems(["tag-2", "tag-3"])
                .withTags(["guide"])
                .withArticleIds([200L])
                .withAttributes([source: "api"])

        then:
        verifyAll(original) {
            action() == "delete"
            items() == ["tag-1"]
            tags() == null
            articleIds() == null
            attributes() == null
        }
        verifyAll(updated) {
            action() == "merge"
            items() == ["tag-2", "tag-3"]
            tags() == ["guide"]
            articleIds() == [200L]
            attributes() == [source: "api"]
        }
    }

    def "should round-trip serialize and deserialize ContentTagsJob"() {
        given:
        ContentTagsJob job = new ContentTagsJob(
                "delete",
                ["01ARZ3NDEK001", "01ARZ3NDEK002"],
                ["faq"],
                [501L, 502L],
                [reason: "cleanup"]
        )

        when:
        String json = objectMapper.writeValueAsString(job)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagsJob deserialized = objectMapper.readValue(json, ContentTagsJob.class)

        then:
        verifyAll(map) {
            get("action") == "delete"
            get("items") == ["01ARZ3NDEK001", "01ARZ3NDEK002"]
            get("tags") == ["faq"]
            get("article_ids") == [501, 502]
            get("attributes") == [reason: "cleanup"]
        }
        verifyAll(deserialized) {
            it == job
            action() == "delete"
            items() == ["01ARZ3NDEK001", "01ARZ3NDEK002"]
            tags() == ["faq"]
            articleIds() == [501L, 502L]
            attributes() == [reason: "cleanup"]
        }
    }

    def "should handle null components in constructor, wither, and serde"() {
        when:
        ContentTagsJob job = new ContentTagsJob(null, null, null, null, null)
        ContentTagsJob updated = job.withAction("delete")
        String json = objectMapper.writeValueAsString(job)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagsJob deserialized = objectMapper.readValue(json, ContentTagsJob.class)

        then:
        verifyAll(job) {
            action() == null
            items() == null
            tags() == null
            articleIds() == null
            attributes() == null
        }
        verifyAll(updated) {
            action() == "delete"
            items() == null
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("action")
            !containsKey("items")
            !containsKey("tags")
            !containsKey("article_ids")
            !containsKey("attributes")
        }
        verifyAll(deserialized) {
            it == job
            action() == null
            items() == null
            tags() == null
            articleIds() == null
            attributes() == null
        }
    }
}
