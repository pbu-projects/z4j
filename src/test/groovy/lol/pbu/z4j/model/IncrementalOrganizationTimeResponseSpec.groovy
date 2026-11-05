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

class IncrementalOrganizationTimeResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create IncrementalOrganizationTimeResponse with canonical constructor, convenience constructor, and builder"() {
        given:
        Organization org = new Organization(789L)
        List<Organization> orgs = [org]

        when:
        IncrementalOrganizationTimeResponse direct = new IncrementalOrganizationTimeResponse(
                orgs,
                1727330400L,
                "https://example.zendesk.com/api/v2/incremental/organizations.json?start_time=1727330400",
                false,
                1
        )
        IncrementalOrganizationTimeResponse convenience = new IncrementalOrganizationTimeResponse(orgs)
        IncrementalOrganizationTimeResponse built = IncrementalOrganizationTimeResponseBuilder.builder()
                .organizations(orgs)
                .endTime(1727330400L)
                .nextPage("https://example.zendesk.com/api/v2/incremental/organizations.json?start_time=1727330400")
                .endOfStream(false)
                .count(1)
                .build()

        then:
        verifyAll(direct) {
            organizations() == orgs
            results() == orgs
            endTime() == 1727330400L
            nextPage() == "https://example.zendesk.com/api/v2/incremental/organizations.json?start_time=1727330400"
            endOfStream() == false
            count() == 1
        }
        verifyAll(convenience) {
            organizations() == orgs
            results() == orgs
            endTime() == null
            nextPage() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(built) {
            it == direct
            organizations() == orgs
            results() == orgs
            endTime() == 1727330400L
            nextPage() == "https://example.zendesk.com/api/v2/incremental/organizations.json?start_time=1727330400"
            endOfStream() == false
            count() == 1
        }
    }

    def "should update IncrementalOrganizationTimeResponse using record withers"() {
        given:
        Organization first = new Organization(1L)
        Organization second = new Organization(2L)
        IncrementalOrganizationTimeResponse response = new IncrementalOrganizationTimeResponse(
                [first], 1000L, "next1", false, 1
        )

        when:
        IncrementalOrganizationTimeResponse updated = response
                .withOrganizations([second])
                .withEndTime(2000L)
                .withNextPage("next2")
                .withEndOfStream(true)
                .withCount(2)

        then:
        verifyAll(response) {
            organizations() == [first]
            results() == [first]
            endTime() == 1000L
            nextPage() == "next1"
            endOfStream() == false
            count() == 1
        }
        verifyAll(updated) {
            organizations() == [second]
            results() == [second]
            endTime() == 2000L
            nextPage() == "next2"
            endOfStream() == true
            count() == 2
        }
    }

    def "should round-trip serialize and deserialize IncrementalOrganizationTimeResponse"() {
        given:
        Organization org = new Organization(789L)
        IncrementalOrganizationTimeResponse response = new IncrementalOrganizationTimeResponse(
                [org], 1727330400L, "https://example.com/next", true, 1
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalOrganizationTimeResponse deserialized = objectMapper.readValue(json, IncrementalOrganizationTimeResponse.class)

        then:
        verifyAll(map) {
            containsKey("organizations")
            containsKey("end_time")
            containsKey("next_page")
            containsKey("end_of_stream")
            containsKey("count")
            !containsKey("results")
        }
        verifyAll(deserialized) {
            it == response
            organizations().size() == 1
            organizations().first().id == 789L
            results() == organizations()
            endTime() == 1727330400L
            nextPage() == "https://example.com/next"
            endOfStream() == true
            count() == 1
        }
    }

    def "should handle null components in IncrementalOrganizationTimeResponse"() {
        when:
        IncrementalOrganizationTimeResponse empty = new IncrementalOrganizationTimeResponse(null, null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalOrganizationTimeResponse deserialized = objectMapper.readValue(json, IncrementalOrganizationTimeResponse.class)

        then:
        verifyAll(empty) {
            organizations() == null
            results() == null
            endTime() == null
            nextPage() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            organizations() == null
            results() == null
            endTime() == null
            nextPage() == null
            endOfStream() == null
            count() == null
        }
    }
}
