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
import lol.pbu.z4j.model.AccountSettingsEmailObjectEmailSenderAuthenticationProfile;
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
 * <p>Email settings</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsEmailObject.JSON_PROPERTY_ACCEPT_WILDCARD_EMAILS,
    AccountSettingsEmailObject.JSON_PROPERTY_CUSTOM_DKIM_DOMAIN,
    AccountSettingsEmailObject.JSON_PROPERTY_EMAIL_SENDER_AUTHENTICATION,
    AccountSettingsEmailObject.JSON_PROPERTY_EMAIL_SENDER_AUTHENTICATION_PROFILE,
    AccountSettingsEmailObject.JSON_PROPERTY_EMAIL_STATUS,
    AccountSettingsEmailObject.JSON_PROPERTY_EMAIL_TEMPLATE_PHOTOS,
    AccountSettingsEmailObject.JSON_PROPERTY_EMAIL_TEMPLATE_SELECTION,
    AccountSettingsEmailObject.JSON_PROPERTY_GMAIL_ACTIONS,
    AccountSettingsEmailObject.JSON_PROPERTY_HTML_MAIL_TEMPLATE,
    AccountSettingsEmailObject.JSON_PROPERTY_MAIL_DELIMITER,
    AccountSettingsEmailObject.JSON_PROPERTY_MODERN_EMAIL_TEMPLATE,
    AccountSettingsEmailObject.JSON_PROPERTY_NO_MAIL_DELIMITER,
    AccountSettingsEmailObject.JSON_PROPERTY_PERSONALIZED_REPLIES,
    AccountSettingsEmailObject.JSON_PROPERTY_RICH_CONTENT_IN_EMAILS,
    AccountSettingsEmailObject.JSON_PROPERTY_SEND_GMAIL_MESSAGES_VIA_GMAIL,
    AccountSettingsEmailObject.JSON_PROPERTY_TEXT_MAIL_TEMPLATE,
})
@Serdeable
public class AccountSettingsEmailObject {

    public static final String JSON_PROPERTY_ACCEPT_WILDCARD_EMAILS = "accept_wildcard_emails";
    public static final String JSON_PROPERTY_CUSTOM_DKIM_DOMAIN = "custom_dkim_domain";
    public static final String JSON_PROPERTY_EMAIL_SENDER_AUTHENTICATION = "email_sender_authentication";
    public static final String JSON_PROPERTY_EMAIL_SENDER_AUTHENTICATION_PROFILE = "email_sender_authentication_profile";
    public static final String JSON_PROPERTY_EMAIL_STATUS = "email_status";
    public static final String JSON_PROPERTY_EMAIL_TEMPLATE_PHOTOS = "email_template_photos";
    public static final String JSON_PROPERTY_EMAIL_TEMPLATE_SELECTION = "email_template_selection";
    public static final String JSON_PROPERTY_GMAIL_ACTIONS = "gmail_actions";
    public static final String JSON_PROPERTY_HTML_MAIL_TEMPLATE = "html_mail_template";
    public static final String JSON_PROPERTY_MAIL_DELIMITER = "mail_delimiter";
    public static final String JSON_PROPERTY_MODERN_EMAIL_TEMPLATE = "modern_email_template";
    public static final String JSON_PROPERTY_NO_MAIL_DELIMITER = "no_mail_delimiter";
    public static final String JSON_PROPERTY_PERSONALIZED_REPLIES = "personalized_replies";
    public static final String JSON_PROPERTY_RICH_CONTENT_IN_EMAILS = "rich_content_in_emails";
    public static final String JSON_PROPERTY_SEND_GMAIL_MESSAGES_VIA_GMAIL = "send_gmail_messages_via_gmail";
    public static final String JSON_PROPERTY_TEXT_MAIL_TEMPLATE = "text_mail_template";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACCEPT_WILDCARD_EMAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean acceptWildcardEmails;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_DKIM_DOMAIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean customDkimDomain;

    /**
     * <p>Whether incoming email is subjected to sender authentication checks (SPF, DKIM)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_SENDER_AUTHENTICATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emailSenderAuthentication;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_SENDER_AUTHENTICATION_PROFILE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsEmailObjectEmailSenderAuthenticationProfile emailSenderAuthenticationProfile;

    /**
     * <p>Whether email status and delivery information is shown in the Agent Workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emailStatus;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_TEMPLATE_PHOTOS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emailTemplatePhotos;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_TEMPLATE_SELECTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emailTemplateSelection;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GMAIL_ACTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean gmailActions;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_MAIL_TEMPLATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlMailTemplate;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAIL_DELIMITER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String mailDelimiter;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MODERN_EMAIL_TEMPLATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean modernEmailTemplate;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NO_MAIL_DELIMITER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean noMailDelimiter;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PERSONALIZED_REPLIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean personalizedReplies;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RICH_CONTENT_IN_EMAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean richContentInEmails;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SEND_GMAIL_MESSAGES_VIA_GMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sendGmailMessagesViaGmail;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TEXT_MAIL_TEMPLATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String textMailTemplate;

}