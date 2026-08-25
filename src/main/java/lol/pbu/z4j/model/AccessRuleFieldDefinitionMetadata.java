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
 * AccessRuleFieldDefinitionMetadata
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccessRuleFieldDefinitionMetadata.JSON_PROPERTY_COLLECTION_KEY,
    AccessRuleFieldDefinitionMetadata.JSON_PROPERTY_FIELD_ID,
    AccessRuleFieldDefinitionMetadata.JSON_PROPERTY_ITEM_KEY,
    AccessRuleFieldDefinitionMetadata.JSON_PROPERTY_SOURCE,
    AccessRuleFieldDefinitionMetadata.JSON_PROPERTY_URL,
})
@Serdeable
public class AccessRuleFieldDefinitionMetadata {

    public static final String JSON_PROPERTY_COLLECTION_KEY = "collection_key";
    public static final String JSON_PROPERTY_FIELD_ID = "field_id";
    public static final String JSON_PROPERTY_ITEM_KEY = "item_key";
    public static final String JSON_PROPERTY_SOURCE = "source";
    public static final String JSON_PROPERTY_URL = "url";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLECTION_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String collectionKey;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FIELD_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer fieldId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ITEM_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String itemKey;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String source;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}