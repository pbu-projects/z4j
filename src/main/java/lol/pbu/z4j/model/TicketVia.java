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
import java.util.HashMap;
import java.util.Map;

/**
 * Describes how a ticket was created.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketVia.JSON_PROPERTY_CHANNEL,
        TicketVia.JSON_PROPERTY_SOURCE
})
public record TicketVia(
        @Nullable @JsonProperty(JSON_PROPERTY_CHANNEL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String channel,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE) @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS) Map<String, Object> source
) implements TicketViaBuilder.With {

    public static final String JSON_PROPERTY_CHANNEL = "channel";
    public static final String JSON_PROPERTY_SOURCE = "source";

    public TicketVia() {
        this(null, null);
    }

    public TicketVia(Map<String, Object> source) {
        this(null, source);
    }

    public TicketVia putSourceItem(String key, Object sourceItem) {
        Map<String, Object> newSource = source == null ? new HashMap<>() : new HashMap<>(source);
        newSource.put(key, sourceItem);
        return withSource(newSource);
    }
}
