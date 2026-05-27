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

import com.fasterxml.jackson.annotation.*;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * TicketCustomFieldsInner
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@JsonPropertyOrder({
        TicketCustomFieldsInner.JSON_PROPERTY_ID,
        TicketCustomFieldsInner.JSON_PROPERTY_VALUE,
})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TicketCustomFieldStringArray.class, name = "string[]"),
        @JsonSubTypes.Type(value = TicketCustomFieldLong.class, name = "long"),
        @JsonSubTypes.Type(value = TicketCustomFieldFloat.class, name = "float"),
        @JsonSubTypes.Type(value = TicketCustomFieldString.class, name = "string"),
        @JsonSubTypes.Type(value = TicketCustomFieldBoolean.class, name = "boolean")
})
@Serdeable
public abstract class TicketCustomFieldsInner {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_VALUE = "value";

    /**
     * The id of the custom field
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    @Getter
    @Setter
    private Long id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public abstract Object getValue();

    @JsonProperty(JSON_PROPERTY_VALUE)
    public abstract void setValue(Object value);
}
