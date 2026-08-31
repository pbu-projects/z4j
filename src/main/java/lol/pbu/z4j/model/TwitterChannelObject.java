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
import java.time.ZonedDateTime;
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
 * TwitterChannelObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TwitterChannelObject.JSON_PROPERTY_ID,
    TwitterChannelObject.JSON_PROPERTY_SCREEN_NAME,
    TwitterChannelObject.JSON_PROPERTY_TWITTER_USER_ID,
    TwitterChannelObject.JSON_PROPERTY_ALLOW_REPLY,
    TwitterChannelObject.JSON_PROPERTY_AVATAR_URL,
    TwitterChannelObject.JSON_PROPERTY_BRAND_ID,
    TwitterChannelObject.JSON_PROPERTY_CAN_REPLY,
    TwitterChannelObject.JSON_PROPERTY_CREATED_AT,
    TwitterChannelObject.JSON_PROPERTY_NAME,
    TwitterChannelObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class TwitterChannelObject {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_SCREEN_NAME = "screen_name";
    public static final String JSON_PROPERTY_TWITTER_USER_ID = "twitter_user_id";
    public static final String JSON_PROPERTY_ALLOW_REPLY = "allow_reply";
    public static final String JSON_PROPERTY_AVATAR_URL = "avatar_url";
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_CAN_REPLY = "can_reply";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>The X handle</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SCREEN_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String screenName;

    /**
     * <p>The country's code</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TWITTER_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long twitterUserId;

    /**
     * <p>If replies are allowed for this handle</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_REPLY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowReply;

    /**
     * <p>The profile image url of the handle</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AVATAR_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String avatarUrl;

    /**
     * <p>What brand the handle is associated with</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long brandId;

    /**
     * <p>If replies are allowed for this handle</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CAN_REPLY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean canReply;

    /**
     * <p>The time the handle was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The profile name of the handle</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>The time of the last update of the handle</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

}