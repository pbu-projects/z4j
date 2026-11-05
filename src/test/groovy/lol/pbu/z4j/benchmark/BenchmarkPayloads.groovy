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
package lol.pbu.z4j.benchmark

import java.nio.charset.StandardCharsets

class BenchmarkPayloads {

    private static final Map<Integer, byte[]> PREGENERATED_RESPONSES = [
            25 : buildResponseBytes(25),
            50 : buildResponseBytes(50),
            100: buildResponseBytes(100)
    ]

    static byte[] getPayload(int count) {
        return PREGENERATED_RESPONSES.get(count) ?: buildResponseBytes(count)
    }

    private static byte[] buildResponseBytes(int count) {
        StringBuilder sb = new StringBuilder(count * 800)
        sb.append('{"tickets":[')
        (1..count).each { i ->
            if (i > 1) sb.append(',')
            long id = 1000 + i
            sb.append("""{"id":${id},"url":"https://example.zendesk.com/api/v2/tickets/${id}.json","subject":"Benchmark Ticket ${i}","description":"Benchmark comment ${i} for performance testing.","priority":"normal","status":"open","requester_id":123456,"submitter_id":123456,"organization_id":78910,"group_id":111213,"collaborator_ids":[],"has_incidents":false,"tags":["benchmark","performance","test"],"created_at":"2026-11-05T00:00:00Z","updated_at":"2026-11-05T00:00:00Z","via":{"channel":"api","source":{"from":{},"to":{},"rel":null}},"custom_fields":[""")
            (1..30).each { f ->
                if (f > 1) sb.append(',')
                sb.append("""{"id":${40971535000000L + f},"value":${f % 2 == 0 ? "\"field-val-${f}\"" : "null"}}""")
            }
            sb.append("""],"satisfaction_rating":{"score":"unoffered"}}""")
        }
        sb.append('],"count":').append(count).append('}')
        return sb.toString().getBytes(StandardCharsets.UTF_8)
    }
}
