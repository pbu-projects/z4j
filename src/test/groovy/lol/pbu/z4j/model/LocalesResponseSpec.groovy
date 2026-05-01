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

class LocalesResponseSpec extends Z4jSpec {

    @Unroll
    def "should add locales item"() {
        given:
        def localesResponse = new LocalesResponse()
        localesResponse.locales == null
        def locale = new Locale(name: faker.nation().language())

        when:
        localesResponse.addLocalesItem(locale)

        then:
        localesResponse.locales.size() == 1
        localesResponse.locales.getAt(0) == locale
    }

    @Unroll
    def "add locales item to existing list"() {
        given:
        def existingLocale = new Locale(name: faker.nation().language())
        def localesResponse = new LocalesResponse(locales: [existingLocale])
        def newLocale = new Locale(name: faker.nation().language())

        when:
        localesResponse.addLocalesItem(newLocale)

        then:
        localesResponse.locales.size() == 2
        localesResponse.locales.containsAll([existingLocale, newLocale])
    }
}

