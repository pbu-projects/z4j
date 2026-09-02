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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ResourceCollectionObjectResourcesInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ResourceCollectionObjectResourcesInner.JSON_PROPERTY_DELETED,
    ResourceCollectionObjectResourcesInner.JSON_PROPERTY_IDENTIFIER,
    ResourceCollectionObjectResourcesInner.JSON_PROPERTY_RESOURCE_ID,
    ResourceCollectionObjectResourcesInner.JSON_PROPERTY_TYPE,
})
@Serdeable
public class ResourceCollectionObjectResourcesInner {

    public static final String JSON_PROPERTY_DELETED = "deleted";
    public static final String JSON_PROPERTY_IDENTIFIER = "identifier";
    public static final String JSON_PROPERTY_RESOURCE_ID = "resource_id";
    public static final String JSON_PROPERTY_TYPE = "type";

    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean deleted;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IDENTIFIER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String identifier;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RESOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long resourceId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

}