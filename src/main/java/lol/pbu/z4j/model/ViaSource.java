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
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * For some channels a source object gives more information about how or why the ticket or event was created
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        ViaSource.JSON_PROPERTY_FROM,
        ViaSource.JSON_PROPERTY_REL,
        ViaSource.JSON_PROPERTY_TO,
})
@Serdeable
public class ViaSource extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_FROM = "from";
    public static final String JSON_PROPERTY_REL = "rel";
    public static final String JSON_PROPERTY_TO = "to";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_FROM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ViaSourceFrom from;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rel;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TO)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ViaSourceTo to;

}
