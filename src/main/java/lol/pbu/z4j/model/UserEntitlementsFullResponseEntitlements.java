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

/**
 * <p>Entitlements for Zendesk products (Live Chat, Explore, Voice, Knowledge)</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    UserEntitlementsFullResponseEntitlements.JSON_PROPERTY_CHAT,
    UserEntitlementsFullResponseEntitlements.JSON_PROPERTY_EXPLORE,
    UserEntitlementsFullResponseEntitlements.JSON_PROPERTY_GUIDE,
    UserEntitlementsFullResponseEntitlements.JSON_PROPERTY_TALK,
})
@Serdeable
public class UserEntitlementsFullResponseEntitlements {

    public static final String JSON_PROPERTY_CHAT = "chat";
    public static final String JSON_PROPERTY_EXPLORE = "explore";
    public static final String JSON_PROPERTY_GUIDE = "guide";
    public static final String JSON_PROPERTY_TALK = "talk";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CHAT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserEntitlementObject chat;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_EXPLORE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserEntitlementObject explore;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_GUIDE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserEntitlementObject guide;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TALK)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserEntitlementObject talk;

}