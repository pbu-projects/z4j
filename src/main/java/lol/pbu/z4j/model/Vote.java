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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Vote
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
        Vote.JSON_PROPERTY_VALUE,
        Vote.JSON_PROPERTY_CREATED_AT,
        Vote.JSON_PROPERTY_ID,
        Vote.JSON_PROPERTY_ITEM_ID,
        Vote.JSON_PROPERTY_ITEM_TYPE,
        Vote.JSON_PROPERTY_UPDATED_AT,
        Vote.JSON_PROPERTY_URL,
        Vote.JSON_PROPERTY_USER_ID,
})
@Serdeable
public class Vote {

    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ITEM_ID = "item_id";
    public static final String JSON_PROPERTY_ITEM_TYPE = "item_type";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_ID = "user_id";

    /**
     * The value of the vote
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_VALUE)
    private Long value;

    /**
     * The time at which the vote was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * Automatically assigned when the vote is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The id of the item for which this vote was cast
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ITEM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long itemId;

    /**
     * The type of the item. Can be \"Article\", \"Comment\", \"Post\" or \"PostComment\"
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ITEM_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String itemType;

    /**
     * The time at which the vote was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The API url of this vote
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * The id of the user who cast this vote
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userId;

    public Vote(Long value) {
        this.value = value;
    }

}
