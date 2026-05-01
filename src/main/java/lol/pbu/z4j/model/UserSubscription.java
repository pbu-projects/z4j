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
 * UserSubscription
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        UserSubscription.JSON_PROPERTY_FOLLOWED_ID,
        UserSubscription.JSON_PROPERTY_FOLLOWER_ID,
        UserSubscription.JSON_PROPERTY_ID,
})
@Serdeable
public class UserSubscription {

    public static final String JSON_PROPERTY_FOLLOWED_ID = "followed_id";
    public static final String JSON_PROPERTY_FOLLOWER_ID = "follower_id";
    public static final String JSON_PROPERTY_ID = "id";

    /**
     * The id of the user being followed
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWED_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long followedId;

    /**
     * The id of the user doing the following
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long followerId;

    /**
     * Automatically assigned when the subscription is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

}
