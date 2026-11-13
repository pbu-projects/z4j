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

class TicketMetricTimeSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketMetricTime with constructors and builder"() {
        when:
        TicketMetricTime direct = new TicketMetricTime(120L, 240L)
        TicketMetricTime defaultConstructor = new TicketMetricTime()
        TicketMetricTime built = TicketMetricTimeBuilder.builder()
                .business(120L)
                .calendar(240L)
                .build()

        then:
        verifyAll(direct) {
            business() == 120L
            calendar() == 240L
        }
        verifyAll(defaultConstructor) {
            business() == null
            calendar() == null
        }
        verifyAll(built) {
            it == direct
            business() == 120L
            calendar() == 240L
        }
    }

    def "should update TicketMetricTime with record withers"() {
        given:
        TicketMetricTime original = new TicketMetricTime(10L, 20L)

        when:
        TicketMetricTime updated = original
                .withBusiness(15L)
                .withCalendar(25L)

        then:
        verifyAll(original) {
            business() == 10L
            calendar() == 20L
        }
        verifyAll(updated) {
            business() == 15L
            calendar() == 25L
        }
    }

    def "should serialize and deserialize TicketMetricTime"() {
        given:
        TicketMetricTime metricTime = new TicketMetricTime(300L, 600L)

        when:
        String json = objectMapper.writeValueAsString(metricTime)
        Map map = objectMapper.readValue(json, Map.class)
        TicketMetricTime deserialized = objectMapper.readValue(json, TicketMetricTime.class)

        then:
        verifyAll(map) {
            get("business") == 300
            get("calendar") == 600
        }
        verifyAll(deserialized) {
            it == metricTime
            business() == 300L
            calendar() == 600L
        }
    }

    def "should handle null values in TicketMetricTime serialization"() {
        given:
        TicketMetricTime empty = new TicketMetricTime(null, null)

        when:
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketMetricTime deserialized = objectMapper.readValue(json, TicketMetricTime.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            business() == null
            calendar() == null
        }
    }
}
