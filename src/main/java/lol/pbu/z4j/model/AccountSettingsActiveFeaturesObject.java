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
 * <p>The active features for an account. See <a href=\"#active-features\">Active Features</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_AGENT_FORWARDING,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_ALLOW_CCS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_ALLOW_EMAIL_TEMPLATE_CUSTOMIZATION,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_AUTOMATIC_ANSWERS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_BCC_ARCHIVING,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_BENCHMARK_OPT_OUT,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_BUSINESS_HOURS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_CHAT,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_CHAT_ABOUT_MY_TICKET,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_CSAT_REASON_CODE,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_CUSTOM_DKIM_DOMAIN,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_CUSTOMER_CONTEXT_AS_DEFAULT,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_CUSTOMER_SATISFACTION,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_DYNAMIC_CONTENTS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_EXPLORE,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_EXPLORE_ON_SUPPORT_ENT_PLAN,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_EXPLORE_ON_SUPPORT_PRO_PLAN,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_FACEBOOK,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_FACEBOOK_LOGIN,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_FALLBACK_COMPOSER,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_FORUM_ANALYTICS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_GOOD_DATA_AND_EXPLORE,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_GOOGLE_LOGIN,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_IS_ABUSIVE,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_LIGHT_AGENTS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_MARKDOWN,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_ON_HOLD_STATUS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_ORGANIZATION_ACCESS_ENABLED,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_RICH_CONTENT_IN_EMAILS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_SANDBOX,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_SATISFACTION_PREDICTION,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_SUSPENDED_TICKET_NOTIFICATION,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_TICKET_FORMS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_TICKET_TAGGING,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_TOPIC_SUGGESTION,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_TWITTER,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_TWITTER_LOGIN,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_USER_ORG_FIELDS,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_USER_TAGGING,
    AccountSettingsActiveFeaturesObject.JSON_PROPERTY_VOICE,
})
@Serdeable
public class AccountSettingsActiveFeaturesObject {

    public static final String JSON_PROPERTY_AGENT_FORWARDING = "agent_forwarding";
    public static final String JSON_PROPERTY_ALLOW_CCS = "allow_ccs";
    public static final String JSON_PROPERTY_ALLOW_EMAIL_TEMPLATE_CUSTOMIZATION = "allow_email_template_customization";
    public static final String JSON_PROPERTY_AUTOMATIC_ANSWERS = "automatic_answers";
    public static final String JSON_PROPERTY_BCC_ARCHIVING = "bcc_archiving";
    public static final String JSON_PROPERTY_BENCHMARK_OPT_OUT = "benchmark_opt_out";
    public static final String JSON_PROPERTY_BUSINESS_HOURS = "business_hours";
    public static final String JSON_PROPERTY_CHAT = "chat";
    public static final String JSON_PROPERTY_CHAT_ABOUT_MY_TICKET = "chat_about_my_ticket";
    public static final String JSON_PROPERTY_CSAT_REASON_CODE = "csat_reason_code";
    public static final String JSON_PROPERTY_CUSTOM_DKIM_DOMAIN = "custom_dkim_domain";
    public static final String JSON_PROPERTY_CUSTOMER_CONTEXT_AS_DEFAULT = "customer_context_as_default";
    public static final String JSON_PROPERTY_CUSTOMER_SATISFACTION = "customer_satisfaction";
    public static final String JSON_PROPERTY_DYNAMIC_CONTENTS = "dynamic_contents";
    public static final String JSON_PROPERTY_EXPLORE = "explore";
    public static final String JSON_PROPERTY_EXPLORE_ON_SUPPORT_ENT_PLAN = "explore_on_support_ent_plan";
    public static final String JSON_PROPERTY_EXPLORE_ON_SUPPORT_PRO_PLAN = "explore_on_support_pro_plan";
    public static final String JSON_PROPERTY_FACEBOOK = "facebook";
    public static final String JSON_PROPERTY_FACEBOOK_LOGIN = "facebook_login";
    public static final String JSON_PROPERTY_FALLBACK_COMPOSER = "fallback_composer";
    public static final String JSON_PROPERTY_FORUM_ANALYTICS = "forum_analytics";
    public static final String JSON_PROPERTY_GOOD_DATA_AND_EXPLORE = "good_data_and_explore";
    public static final String JSON_PROPERTY_GOOGLE_LOGIN = "google_login";
    public static final String JSON_PROPERTY_IS_ABUSIVE = "is_abusive";
    public static final String JSON_PROPERTY_LIGHT_AGENTS = "light_agents";
    public static final String JSON_PROPERTY_MARKDOWN = "markdown";
    public static final String JSON_PROPERTY_ON_HOLD_STATUS = "on_hold_status";
    public static final String JSON_PROPERTY_ORGANIZATION_ACCESS_ENABLED = "organization_access_enabled";
    public static final String JSON_PROPERTY_RICH_CONTENT_IN_EMAILS = "rich_content_in_emails";
    public static final String JSON_PROPERTY_SANDBOX = "sandbox";
    public static final String JSON_PROPERTY_SATISFACTION_PREDICTION = "satisfaction_prediction";
    public static final String JSON_PROPERTY_SUSPENDED_TICKET_NOTIFICATION = "suspended_ticket_notification";
    public static final String JSON_PROPERTY_TICKET_FORMS = "ticket_forms";
    public static final String JSON_PROPERTY_TICKET_TAGGING = "ticket_tagging";
    public static final String JSON_PROPERTY_TOPIC_SUGGESTION = "topic_suggestion";
    public static final String JSON_PROPERTY_TWITTER = "twitter";
    public static final String JSON_PROPERTY_TWITTER_LOGIN = "twitter_login";
    public static final String JSON_PROPERTY_USER_ORG_FIELDS = "user_org_fields";
    public static final String JSON_PROPERTY_USER_TAGGING = "user_tagging";
    public static final String JSON_PROPERTY_VOICE = "voice";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_FORWARDING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentForwarding;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_CCS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowCcs;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_EMAIL_TEMPLATE_CUSTOMIZATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowEmailTemplateCustomization;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTOMATIC_ANSWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean automaticAnswers;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BCC_ARCHIVING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean bccArchiving;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BENCHMARK_OPT_OUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean benchmarkOptOut;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BUSINESS_HOURS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean businessHours;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CHAT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean chat;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CHAT_ABOUT_MY_TICKET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean chatAboutMyTicket;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CSAT_REASON_CODE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean csatReasonCode;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_DKIM_DOMAIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean customDkimDomain;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOMER_CONTEXT_AS_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean customerContextAsDefault;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOMER_SATISFACTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean customerSatisfaction;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DYNAMIC_CONTENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean dynamicContents;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPLORE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean explore;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPLORE_ON_SUPPORT_ENT_PLAN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean exploreOnSupportEntPlan;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPLORE_ON_SUPPORT_PRO_PLAN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean exploreOnSupportProPlan;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FACEBOOK)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean facebook;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FACEBOOK_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean facebookLogin;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FALLBACK_COMPOSER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean fallbackComposer;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FORUM_ANALYTICS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean forumAnalytics;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GOOD_DATA_AND_EXPLORE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean goodDataAndExplore;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GOOGLE_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean googleLogin;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_ABUSIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isAbusive;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LIGHT_AGENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean lightAgents;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MARKDOWN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean markdown;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ON_HOLD_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean onHoldStatus;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ACCESS_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean organizationAccessEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RICH_CONTENT_IN_EMAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean richContentInEmails;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SANDBOX)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sandbox;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SATISFACTION_PREDICTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean satisfactionPrediction;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUSPENDED_TICKET_NOTIFICATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean suspendedTicketNotification;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORMS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ticketForms;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_TAGGING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ticketTagging;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TOPIC_SUGGESTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean topicSuggestion;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TWITTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean twitter;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TWITTER_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean twitterLogin;

    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ORG_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean userOrgFields;

    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_TAGGING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean userTagging;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VOICE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean voice;

}