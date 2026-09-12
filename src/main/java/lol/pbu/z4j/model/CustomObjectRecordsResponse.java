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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lol.pbu.z4j.Generated;

/**
 * CustomObjectRecordsResponse
 *
 * @since 0.2.2
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomObjectRecordsResponse.JSON_PROPERTY_CUSTOM_OBJECT_RECORDS,
    CustomObjectRecordsResponse.JSON_PROPERTY_META,
    CustomObjectRecordsResponse.JSON_PROPERTY_LINKS,
})
@Serdeable
@Generated
public class CustomObjectRecordsResponse {

    public static final String JSON_PROPERTY_CUSTOM_OBJECT_RECORDS = "custom_object_records";
    public static final String JSON_PROPERTY_META = "meta";
    public static final String JSON_PROPERTY_LINKS = "links";

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_RECORDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomObjectRecord> customObjectRecords;

    @Nullable
    @JsonProperty(JSON_PROPERTY_META)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> meta;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LINKS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> links;

    public CustomObjectRecordsResponse addCustomObjectRecordsItem(CustomObjectRecord customObjectRecordsItem) {
        if (customObjectRecords == null) {
            customObjectRecords = new ArrayList<>();
        }
        customObjectRecords.add(customObjectRecordsItem);
        return this;
    }
}
