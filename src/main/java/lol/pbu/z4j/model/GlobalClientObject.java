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
 * GlobalClientObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    GlobalClientObject.JSON_PROPERTY_COMPANY,
    GlobalClientObject.JSON_PROPERTY_DESCRIPTION,
    GlobalClientObject.JSON_PROPERTY_ID,
    GlobalClientObject.JSON_PROPERTY_IDENTIFIER,
    GlobalClientObject.JSON_PROPERTY_KIND,
    GlobalClientObject.JSON_PROPERTY_LOGO_URL,
    GlobalClientObject.JSON_PROPERTY_NAME,
})
@Serdeable
public class GlobalClientObject {

    public static final String JSON_PROPERTY_COMPANY = "company";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IDENTIFIER = "identifier";
    public static final String JSON_PROPERTY_KIND = "kind";
    public static final String JSON_PROPERTY_LOGO_URL = "logo_url";
    public static final String JSON_PROPERTY_NAME = "name";

    /**
     * <p>The company that users are asked to approve access to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMPANY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String company;

    /**
     * <p>A short description of the client</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned when the client is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>The unique identifier for the client</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IDENTIFIER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String identifier;

    /**
     * <p>The kind of client, public or confidential</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_KIND)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String kind;

    /**
     * <p>The API logo url of this record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOGO_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String logoUrl;

    /**
     * <p>The name of the client</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

}