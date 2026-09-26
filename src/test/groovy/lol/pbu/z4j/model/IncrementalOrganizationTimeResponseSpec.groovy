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

class IncrementalOrganizationTimeResponseSpec extends Z4jSpec {

    def "should instantiate and set properties on IncrementalOrganizationTimeResponse"() {
        given:
        def response = new IncrementalOrganizationTimeResponse()
        def org = new Organization()
        org.setId(789L)

        when:
        response.setOrganizations([org])
              .setEndTime(1727330400L)
              .setNextPage("https://example.zendesk.com/api/v2/incremental/organizations.json?start_time=1727330400")
              .setEndOfStream(false)
              .setCount(1)

        then:
        response.organizations.size() == 1
        response.organizations[0].id == 789L
        response.endTime == 1727330400L
        response.nextPage != null
        response.endOfStream == false
        response.count == 1
    }
}
