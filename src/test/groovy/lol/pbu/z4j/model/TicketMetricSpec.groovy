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

import java.time.ZoneOffset
import java.time.ZonedDateTime

class TicketMetricSpec extends ModelSpec {



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
        TicketMetricTime agentWaitTime = new TicketMetricTime(10L, 20L)
        TicketMetricTime firstResolutionTime = new TicketMetricTime(30L, 40L)
        TicketMetricTime fullResolutionTime = new TicketMetricTime(50L, 60L)
        TicketMetricTime onHoldTime = new TicketMetricTime(5L, 8L)
        TicketMetricTime replyTimeMin = new TicketMetricTime(15L, 25L)
        TicketMetricTime replyTimeSec = new TicketMetricTime(900L, 1500L)
        TicketMetricTime requesterWaitTime = new TicketMetricTime(12L, 18L)
        ZonedDateTime expectedAssignedAt = ZonedDateTime.of(2026, 11, 12, 1, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedAssigneeUpdatedAt = ZonedDateTime.of(2026, 11, 12, 2, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedCreatedAt = ZonedDateTime.of(2026, 11, 12, 3, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedCustomStatusUpdatedAt = ZonedDateTime.of(2026, 11, 12, 4, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedInitiallyAssignedAt = ZonedDateTime.of(2026, 11, 12, 5, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedLatestCommentAddedAt = ZonedDateTime.of(2026, 11, 12, 6, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedRequesterUpdatedAt = ZonedDateTime.of(2026, 11, 12, 7, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedSolvedAt = ZonedDateTime.of(2026, 11, 12, 8, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedStatusUpdatedAt = ZonedDateTime.of(2026, 11, 12, 9, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime expectedUpdatedAt = ZonedDateTime.of(2026, 11, 12, 10, 0, 0, 0, ZoneOffset.UTC)
        String testUrl = faker.internet().url()
        TicketMetric metric = new TicketMetric(
                agentWaitTime,
                expectedAssignedAt,
                2L,
                expectedAssigneeUpdatedAt,
                expectedCreatedAt,
                expectedCustomStatusUpdatedAt,
                firstResolutionTime,
                fullResolutionTime,
                3L,
                100L,
                expectedInitiallyAssignedAt,
                expectedLatestCommentAddedAt,
                onHoldTime,
                1L,
                5L,
                replyTimeMin,
                replyTimeSec,
                expectedRequesterUpdatedAt,
                requesterWaitTime,
                expectedSolvedAt,
                expectedStatusUpdatedAt,
                200L,
                expectedUpdatedAt,
                testUrl
        )

        when:
        String json = objectMapper.writeValueAsString(metric)
        Map map = objectMapper.readValue(json, Map.class)
        TicketMetric deserialized = objectMapper.readValue(json, TicketMetric.class)

        then:
        verifyAll(map) {
            size() == 24
            get("id") == 100
            get("ticket_id") == 200
            get("assignee_stations") == 2
            get("group_stations") == 3
            get("reopens") == 1
            get("replies") == 5
            get("url") == testUrl
            get("agent_wait_time_in_minutes") instanceof Map
            ((Map) get("agent_wait_time_in_minutes")).get("business") == 10
            ((Map) get("agent_wait_time_in_minutes")).get("calendar") == 20
            get("first_resolution_time_in_minutes") instanceof Map
            ((Map) get("first_resolution_time_in_minutes")).get("business") == 30
            ((Map) get("first_resolution_time_in_minutes")).get("calendar") == 40
            get("full_resolution_time_in_minutes") instanceof Map
            ((Map) get("full_resolution_time_in_minutes")).get("business") == 50
            ((Map) get("full_resolution_time_in_minutes")).get("calendar") == 60
            get("on_hold_time_in_minutes") instanceof Map
            ((Map) get("on_hold_time_in_minutes")).get("business") == 5
            ((Map) get("on_hold_time_in_minutes")).get("calendar") == 8
            get("reply_time_in_minutes") instanceof Map
            ((Map) get("reply_time_in_minutes")).get("business") == 15
            ((Map) get("reply_time_in_minutes")).get("calendar") == 25
            get("reply_time_in_seconds") instanceof Map
            ((Map) get("reply_time_in_seconds")).get("business") == 900
            ((Map) get("reply_time_in_seconds")).get("calendar") == 1500
            get("requester_wait_time_in_minutes") instanceof Map
            ((Map) get("requester_wait_time_in_minutes")).get("business") == 12
            ((Map) get("requester_wait_time_in_minutes")).get("calendar") == 18
            ZonedDateTime.parse((String) get("assigned_at")) == expectedAssignedAt
            ZonedDateTime.parse((String) get("assignee_updated_at")) == expectedAssigneeUpdatedAt
            ZonedDateTime.parse((String) get("created_at")) == expectedCreatedAt
            ZonedDateTime.parse((String) get("custom_status_updated_at")) == expectedCustomStatusUpdatedAt
            ZonedDateTime.parse((String) get("initially_assigned_at")) == expectedInitiallyAssignedAt
            ZonedDateTime.parse((String) get("latest_comment_added_at")) == expectedLatestCommentAddedAt
            ZonedDateTime.parse((String) get("requester_updated_at")) == expectedRequesterUpdatedAt
            ZonedDateTime.parse((String) get("solved_at")) == expectedSolvedAt
            ZonedDateTime.parse((String) get("status_updated_at")) == expectedStatusUpdatedAt
            ZonedDateTime.parse((String) get("updated_at")) == expectedUpdatedAt
        }
        verifyAll(deserialized) {
            agentWaitTimeInMinutes() == agentWaitTime
            assignedAt() == expectedAssignedAt
            assigneeStations() == 2L
            assigneeUpdatedAt() == expectedAssigneeUpdatedAt
            createdAt() == expectedCreatedAt
            customStatusUpdatedAt() == expectedCustomStatusUpdatedAt
            firstResolutionTimeInMinutes() == firstResolutionTime
            fullResolutionTimeInMinutes() == fullResolutionTime
            groupStations() == 3L
            id() == 100L
            initiallyAssignedAt() == expectedInitiallyAssignedAt
            latestCommentAddedAt() == expectedLatestCommentAddedAt
            onHoldTimeInMinutes() == onHoldTime
            reopens() == 1L
            replies() == 5L
            replyTimeInMinutes() == replyTimeMin
            replyTimeInSeconds() == replyTimeSec
            requesterUpdatedAt() == expectedRequesterUpdatedAt
            requesterWaitTimeInMinutes() == requesterWaitTime
            solvedAt() == expectedSolvedAt
            statusUpdatedAt() == expectedStatusUpdatedAt
            ticketId() == 200L
            updatedAt() == expectedUpdatedAt
            url() == testUrl
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
