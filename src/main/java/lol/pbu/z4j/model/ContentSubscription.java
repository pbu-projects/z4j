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
 * ContentSubscription
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        ContentSubscription.JSON_PROPERTY_LOCALE,
        ContentSubscription.JSON_PROPERTY_CONTENT_ID,
        ContentSubscription.JSON_PROPERTY_CONTENT_TYPE,
        ContentSubscription.JSON_PROPERTY_CREATED_AT,
        ContentSubscription.JSON_PROPERTY_ID,
        ContentSubscription.JSON_PROPERTY_INCLUDE_COMMENTS,
        ContentSubscription.JSON_PROPERTY_SOURCE_LOCALE,
        ContentSubscription.JSON_PROPERTY_UPDATED_AT,
        ContentSubscription.JSON_PROPERTY_URL,
        ContentSubscription.JSON_PROPERTY_USER_ID,
})
@Serdeable
public class ContentSubscription {

    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_CONTENT_ID = "content_id";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_INCLUDE_COMMENTS = "include_comments";
    public static final String JSON_PROPERTY_SOURCE_LOCALE = "source_locale";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_ID = "user_id";

    /**
     * The locale of the subscribed item
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locale;

    /**
     * The id of the subscribed item
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long contentId;

    /**
     * The type of the subscribed item
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    /**
     * The time at which the subscription was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * Automatically assigned when the subscription is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * Subscribe also to article comments / post comments. Only for section / topic subscriptions.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INCLUDE_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean includeComments;

    /**
     * Used only for <a href=\"#create-section-subscription\">Create Section Subscription</a> and <a href=\"#create-article-subscription\">Create Article Subscription</a>, where it's mandatory. Selects the locale of the content to be subscribed
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceLocale;

    /**
     * The time at which the subscription was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The API url of the subscription
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * The id of the user who has this subscription
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userId;

}
