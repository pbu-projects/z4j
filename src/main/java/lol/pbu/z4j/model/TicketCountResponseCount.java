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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.time.ZonedDateTime;

/**
 * Count value and refresh timestamp for ticket count responses.
 *
 * @param refreshedAt timestamp when the ticket count was last updated
 * @param value total number of tickets
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketCountResponseCount.JSON_PROPERTY_REFRESHED_AT,
        TicketCountResponseCount.JSON_PROPERTY_VALUE,
})
public record TicketCountResponseCount(
        @Nullable @JsonProperty(JSON_PROPERTY_REFRESHED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime refreshedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_VALUE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long value
) implements TicketCountResponseCountBuilder.With {

    public static final String JSON_PROPERTY_REFRESHED_AT = "refreshed_at";
    public static final String JSON_PROPERTY_VALUE = "value";
}
