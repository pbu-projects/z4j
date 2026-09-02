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

import java.time.ZonedDateTime;

/**
 * QueueObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    QueueObject.JSON_PROPERTY_CREATED_AT,
    QueueObject.JSON_PROPERTY_DEFINITION,
    QueueObject.JSON_PROPERTY_DESCRIPTION,
    QueueObject.JSON_PROPERTY_ID,
    QueueObject.JSON_PROPERTY_NAME,
    QueueObject.JSON_PROPERTY_ORDER,
    QueueObject.JSON_PROPERTY_PRIMARY_GROUPS,
    QueueObject.JSON_PROPERTY_PRIORITY,
    QueueObject.JSON_PROPERTY_SECONDARY_GROUPS,
    QueueObject.JSON_PROPERTY_UPDATED_AT,
    QueueObject.JSON_PROPERTY_URL,
})
@Serdeable
public class QueueObject {

    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFINITION = "definition";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_ORDER = "order";
    public static final String JSON_PROPERTY_PRIMARY_GROUPS = "primary_groups";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_SECONDARY_GROUPS = "secondary_groups";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The time the queue was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_DEFINITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private QueueObjectDefinition definition;

    /**
     * <p>The description of the queue</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned when creating queue</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>The name of the queue</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>The queue-applied order</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORDER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long order;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_PRIMARY_GROUPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private QueueObjectPrimaryGroups primaryGroups;

    /**
     * <p>The queue-applied priority</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long priority;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_SECONDARY_GROUPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private QueueObjectSecondaryGroups secondaryGroups;

    /**
     * <p>The time of the queue's last update</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API URL of the queue</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}