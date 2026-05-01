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

/**
 * UserSubscriptionsResponse
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(UserSubscriptionsResponse.JSON_PROPERTY_USER_SUBSCRIPTIONS)
@Serdeable
public class UserSubscriptionsResponse {

    public static final String JSON_PROPERTY_USER_SUBSCRIPTIONS = "user_subscriptions";

    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_SUBSCRIPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid UserSubscription> userSubscriptions;

    /**
     * Add an item to the userSubscriptions property in a chainable fashion.
     *
     * @return The same instance of UserSubscriptionsResponse for chaining.
     */
    public UserSubscriptionsResponse addUserSubscriptionsItem(UserSubscription userSubscriptionsItem) {
        if (userSubscriptions == null) {
            userSubscriptions = new ArrayList<>();
        }
        userSubscriptions.add(userSubscriptionsItem);
        return this;
    }

}
