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
import spock.lang.Unroll

class BadRequestErrorResponseSpec extends Z4jSpec {

    @Unroll
    def "should put errors item"() {
        given:
        def badRequestErrorResponse = new BadRequestErrorResponse()
        def key = faker.lorem().word()
        def value = faker.lorem().sentence()

        when:
        badRequestErrorResponse.putErrorsItem(key, value)

        then:
        badRequestErrorResponse.errors.size() == 1
        badRequestErrorResponse.errors.get(key) == value
    }

    @Unroll
    def "put errors item to existing map"() {
        given:
        def existingKey = faker.lorem().word()
        def existingValue = faker.lorem().sentence()
        def badRequestErrorResponse = new BadRequestErrorResponse()
        badRequestErrorResponse.putErrorsItem(existingKey, existingValue)

        def newKey = faker.lorem().word()
        def newValue = faker.lorem().sentence()

        when:
        badRequestErrorResponse.putErrorsItem(newKey, newValue)

        then:
        badRequestErrorResponse.errors.size() == 2
        badRequestErrorResponse.errors.get(existingKey) == existingValue
        badRequestErrorResponse.errors.get(newKey) == newValue
    }
}

