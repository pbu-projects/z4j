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
import lol.pbu.z4j.model.ViaObjectSourceFrom;
import lol.pbu.z4j.model.ViaObjectSourceTo;
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
 * <p>For some channels a source object gives more information about how or why the ticket or event was created</p>
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ViaObjectSource.JSON_PROPERTY_FROM,
    ViaObjectSource.JSON_PROPERTY_REL,
    ViaObjectSource.JSON_PROPERTY_TO,
})
@Serdeable
public class ViaObjectSource extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_FROM = "from";
    public static final String JSON_PROPERTY_REL = "rel";
    public static final String JSON_PROPERTY_TO = "to";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_FROM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ViaObjectSourceFrom from;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rel;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TO)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ViaObjectSourceTo to;

}