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
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Map;
import lol.pbu.z4j.Generated;

/**
 * TicketEvent
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Serdeable
@Generated
public class TicketEvent implements Exportable {
    @Nullable
    @JsonProperty("id")
    private Long id;

    @Nullable
    @JsonProperty("ticket_id")
    private Long ticketId;

    @Nullable
    @JsonProperty("event_type")
    private String eventType;

    @Nullable
    @JsonProperty("via")
    private Object via;

    @Nullable
    @JsonProperty("created_at")
    private String createdAt;

    @Nullable
    @JsonProperty("author_id")
    private Long authorId;

    @Nullable
    @JsonProperty("value")
    private Object value;

    @Nullable
    @JsonProperty("metadata")
    private Map<String, Object> metadata;
}
