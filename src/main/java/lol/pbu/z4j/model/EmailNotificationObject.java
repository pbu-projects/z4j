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
import java.util.List;

/**
 * EmailNotificationObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    EmailNotificationObject.JSON_PROPERTY_COMMENT_ID,
    EmailNotificationObject.JSON_PROPERTY_CREATED_AT,
    EmailNotificationObject.JSON_PROPERTY_EMAIL_ID,
    EmailNotificationObject.JSON_PROPERTY_MESSAGE_ID,
    EmailNotificationObject.JSON_PROPERTY_NOTIFICATION_ID,
    EmailNotificationObject.JSON_PROPERTY_RECIPIENTS,
    EmailNotificationObject.JSON_PROPERTY_TICKET_ID,
    EmailNotificationObject.JSON_PROPERTY_UPDATED_AT,
    EmailNotificationObject.JSON_PROPERTY_URL,
})
@Serdeable
public class EmailNotificationObject {

    public static final String JSON_PROPERTY_COMMENT_ID = "comment_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_EMAIL_ID = "email_id";
    public static final String JSON_PROPERTY_MESSAGE_ID = "message_id";
    public static final String JSON_PROPERTY_NOTIFICATION_ID = "notification_id";
    public static final String JSON_PROPERTY_RECIPIENTS = "recipients";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The comment ID associated to this email notification</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long commentId;

    /**
     * <p>When this email notification was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The email ID of this email notification</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String emailId;

    /**
     * <p>The value of the Message-Id header of the email</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MESSAGE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String messageId;

    /**
     * <p>The notification id of this email notification</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTIFICATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long notificationId;

    /**
     * <p>The list of recipients associated to this email notification</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid RecipientObject> recipients;

    /**
     * <p>The ticket ID associated to this email notification</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketId;

    /**
     * <p>When this email notification was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this email notification</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}