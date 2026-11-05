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
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Serdeable
@RecordBuilder
@JsonPropertyOrder(CustomObjectsResponse.JSON_PROPERTY_CUSTOM_OBJECTS)
public record CustomObjectsResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid CustomObject> customObjects
) implements CustomObjectsResponseBuilder.With {

    public static final String JSON_PROPERTY_CUSTOM_OBJECTS = "custom_objects";

    public CustomObjectsResponse addCustomObjectsItem(CustomObject customObjectsItem) {
        List<CustomObject> list = new ArrayList<>(customObjects != null ? customObjects : List.of());
        list.add(customObjectsItem);
        return new CustomObjectsResponse(list);
    }
}
