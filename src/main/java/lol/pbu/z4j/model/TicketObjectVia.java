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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * <p>For more information, see the <a href=\"/documentation/ticketing/reference-guides/via-object-reference\">Via object reference</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketObjectVia.JSON_PROPERTY_CHANNEL,
    TicketObjectVia.JSON_PROPERTY_SOURCE,
})
@Serdeable
public class TicketObjectVia {

    public static final String JSON_PROPERTY_CHANNEL = "channel";
    public static final String JSON_PROPERTY_SOURCE = "source";

    /**
     * <p>This tells you how the ticket or event was created. Examples: \"web\", \"mobile\", \"rule\", \"system\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CHANNEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String channel;

    /**
     * <p>For some channels a source object gives more information about how or why the ticket or event was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> source;

    /**
     * Set the value for the key for the source map property in a chainable fashion.
     *
     * @return The same instance of TicketObjectVia for chaining.
     */
    public TicketObjectVia putSourceItem(String key, Object sourceItem) {
        if (source == null) {
            source = new HashMap<>();
        }
        source.put(key, sourceItem);
        return this;
    }

}