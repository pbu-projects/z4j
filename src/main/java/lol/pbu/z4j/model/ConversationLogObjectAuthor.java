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
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * <p>Object that describes the user who created the event</p>
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ConversationLogObjectAuthor.JSON_PROPERTY_TYPE,
    ConversationLogObjectAuthor.JSON_PROPERTY_ZEN_SUNCO_USER_ID,
    ConversationLogObjectAuthor.JSON_PROPERTY_ZEN_SUPPORT_USER_ID,
})
@Serdeable
public class ConversationLogObjectAuthor extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_ZEN_SUNCO_USER_ID = "zen:sunco:user_id";
    public static final String JSON_PROPERTY_ZEN_SUPPORT_USER_ID = "zen:support:user_id";

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ConversationLogObjectAuthorType type;

    /**
     * <p>A Zendesk resource name prefix describing a messaging user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ZEN_SUNCO_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String zenSuncoUserId;

    /**
     * <p>A Zendesk resource name prefix describing a Support user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ZEN_SUPPORT_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long zenSupportUserId;

}