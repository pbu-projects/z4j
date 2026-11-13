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
import java.time.ZoneOffset
import java.time.ZonedDateTime
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class TicketMetricSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketMetric with constructors and builder"() {
        given:
        String testUrl = faker.internet().url()
        TicketMetricTime waitTime = new TicketMetricTime(10L, 20L)
        TicketMetricTime resTime1 = new TicketMetricTime(30L, 40L)
        TicketMetricTime resTime2 = new TicketMetricTime(50L, 60L)
        TicketMetricTime holdTime = new TicketMetricTime(5L, 8L)
        TicketMetricTime replyTimeMin = new TicketMetricTime(15L, 25L)
        TicketMetricTime replyTimeSec = new TicketMetricTime(900L, 1500L)
        TicketMetricTime reqWaitTime = new TicketMetricTime(12L, 18L)
        ZonedDateTime timestamp = ZonedDateTime.of(2026, 11, 12, 10, 0, 0, 0, ZoneOffset.UTC)

        when:
        TicketMetric direct = new TicketMetric(
                waitTime,
                timestamp,
                2L,
                timestamp,
                timestamp,
                timestamp,
                resTime1,
                resTime2,
                3L,
                100L,
                timestamp,
                timestamp,
                holdTime,
                1L,
                4L,
                replyTimeMin,
                replyTimeSec,
                timestamp,
                reqWaitTime,
                timestamp,
                timestamp,
                200L,
                timestamp,
                testUrl
        )
        TicketMetric defaultConstructor = new TicketMetric()
        TicketMetric built = TicketMetricBuilder.builder()
                .agentWaitTimeInMinutes(waitTime)
                .assignedAt(timestamp)
                .assigneeStations(2L)
                .assigneeUpdatedAt(timestamp)
                .createdAt(timestamp)
                .customStatusUpdatedAt(timestamp)
                .firstResolutionTimeInMinutes(resTime1)
                .fullResolutionTimeInMinutes(resTime2)
                .groupStations(3L)
                .id(100L)
                .initiallyAssignedAt(timestamp)
                .latestCommentAddedAt(timestamp)
                .onHoldTimeInMinutes(holdTime)
                .reopens(1L)
                .replies(4L)
                .replyTimeInMinutes(replyTimeMin)
                .replyTimeInSeconds(replyTimeSec)
                .requesterUpdatedAt(timestamp)
                .requesterWaitTimeInMinutes(reqWaitTime)
                .solvedAt(timestamp)
                .statusUpdatedAt(timestamp)
                .ticketId(200L)
                .updatedAt(timestamp)
                .url(testUrl)
                .build()

        then:
        verifyAll(direct) {
            agentWaitTimeInMinutes() == waitTime
            assignedAt() == timestamp
            assigneeStations() == 2L
            assigneeUpdatedAt() == timestamp
            createdAt() == timestamp
            customStatusUpdatedAt() == timestamp
            firstResolutionTimeInMinutes() == resTime1
            fullResolutionTimeInMinutes() == resTime2
            groupStations() == 3L
            id() == 100L
            initiallyAssignedAt() == timestamp
            latestCommentAddedAt() == timestamp
            onHoldTimeInMinutes() == holdTime
            reopens() == 1L
            replies() == 4L
            replyTimeInMinutes() == replyTimeMin
            replyTimeInSeconds() == replyTimeSec
            requesterUpdatedAt() == timestamp
            requesterWaitTimeInMinutes() == reqWaitTime
            solvedAt() == timestamp
            statusUpdatedAt() == timestamp
            ticketId() == 200L
            updatedAt() == timestamp
            url() == testUrl
        }
        verifyAll(defaultConstructor) {
            agentWaitTimeInMinutes() == null
            assignedAt() == null
            assigneeStations() == null
            assigneeUpdatedAt() == null
            createdAt() == null
            customStatusUpdatedAt() == null
            firstResolutionTimeInMinutes() == null
            fullResolutionTimeInMinutes() == null
            groupStations() == null
            id() == null
            initiallyAssignedAt() == null
            latestCommentAddedAt() == null
            onHoldTimeInMinutes() == null
            reopens() == null
            replies() == null
            replyTimeInMinutes() == null
            replyTimeInSeconds() == null
            requesterUpdatedAt() == null
            requesterWaitTimeInMinutes() == null
            solvedAt() == null
            statusUpdatedAt() == null
            ticketId() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(built) {
            it == direct
            agentWaitTimeInMinutes() == waitTime
            id() == 100L
            ticketId() == 200L
            url() == testUrl
        }
    }

    def "should update TicketMetric using record withers"() {
        given:
        TicketMetric initial = new TicketMetric()
        TicketMetricTime timeVal = new TicketMetricTime(10L, 20L)
        ZonedDateTime timestamp = ZonedDateTime.of(2026, 11, 12, 12, 0, 0, 0, ZoneOffset.UTC)
        String testUrl = faker.internet().url()

        when:
        TicketMetric updated = initial
                .withAgentWaitTimeInMinutes(timeVal)
                .withAssignedAt(timestamp)
                .withAssigneeStations(1L)
                .withAssigneeUpdatedAt(timestamp)
                .withCreatedAt(timestamp)
                .withCustomStatusUpdatedAt(timestamp)
                .withFirstResolutionTimeInMinutes(timeVal)
                .withFullResolutionTimeInMinutes(timeVal)
                .withGroupStations(2L)
                .withId(500L)
                .withInitiallyAssignedAt(timestamp)
                .withLatestCommentAddedAt(timestamp)
                .withOnHoldTimeInMinutes(timeVal)
                .withReopens(3L)
                .withReplies(4L)
                .withReplyTimeInMinutes(timeVal)
                .withReplyTimeInSeconds(timeVal)
                .withRequesterUpdatedAt(timestamp)
                .withRequesterWaitTimeInMinutes(timeVal)
                .withSolvedAt(timestamp)
                .withStatusUpdatedAt(timestamp)
                .withTicketId(600L)
                .withUpdatedAt(timestamp)
                .withUrl(testUrl)

        then:
        verifyAll(initial) {
            id() == null
            ticketId() == null
        }
        verifyAll(updated) {
            agentWaitTimeInMinutes() == timeVal
            assignedAt() == timestamp
            assigneeStations() == 1L
            assigneeUpdatedAt() == timestamp
            createdAt() == timestamp
            customStatusUpdatedAt() == timestamp
            firstResolutionTimeInMinutes() == timeVal
            fullResolutionTimeInMinutes() == timeVal
            groupStations() == 2L
            id() == 500L
            initiallyAssignedAt() == timestamp
            latestCommentAddedAt() == timestamp
            onHoldTimeInMinutes() == timeVal
            reopens() == 3L
            replies() == 4L
            replyTimeInMinutes() == timeVal
            replyTimeInSeconds() == timeVal
            requesterUpdatedAt() == timestamp
            requesterWaitTimeInMinutes() == timeVal
            solvedAt() == timestamp
            statusUpdatedAt() == timestamp
            ticketId() == 600L
            updatedAt() == timestamp
            url() == testUrl
        }
    }

    def "should serialize and deserialize TicketMetric"() {
        given:
        TicketMetricTime waitTime = new TicketMetricTime(10L, 20L)
        ZonedDateTime timestamp = ZonedDateTime.of(2026, 11, 12, 10, 0, 0, 0, ZoneOffset.UTC)
        String testUrl = faker.internet().url()
        TicketMetric metric = new TicketMetric()
                .withId(100L)
                .withTicketId(200L)
                .withAgentWaitTimeInMinutes(waitTime)
                .withCreatedAt(timestamp)
                .withUpdatedAt(timestamp)
                .withReplies(5L)
                .withUrl(testUrl)

        when:
        String json = objectMapper.writeValueAsString(metric)
        Map map = objectMapper.readValue(json, Map.class)
        TicketMetric deserialized = objectMapper.readValue(json, TicketMetric.class)

        then:
        verifyAll(map) {
            get("id") == 100
            get("ticket_id") == 200
            get("replies") == 5
            get("url") == testUrl
            get("agent_wait_time_in_minutes") instanceof Map
            ((Map) get("agent_wait_time_in_minutes")).get("business") == 10
            ((Map) get("agent_wait_time_in_minutes")).get("calendar") == 20
        }
        verifyAll(deserialized) {
            id() == 100L
            ticketId() == 200L
            replies() == 5L
            url() == testUrl
            agentWaitTimeInMinutes() == waitTime
            createdAt() == timestamp
            updatedAt() == timestamp
        }
    }

    def "should handle null values in TicketMetric serialization"() {
        given:
        TicketMetric empty = new TicketMetric()

        when:
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketMetric deserialized = objectMapper.readValue(json, TicketMetric.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            id() == null
            ticketId() == null
        }
    }
}
