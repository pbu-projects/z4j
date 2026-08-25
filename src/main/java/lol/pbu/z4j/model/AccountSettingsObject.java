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
import lol.pbu.z4j.model.AccountSettingsActiveFeaturesObject;
import lol.pbu.z4j.model.AccountSettingsAgentObject;
import lol.pbu.z4j.model.AccountSettingsApiObject;
import lol.pbu.z4j.model.AccountSettingsAppsObject;
import lol.pbu.z4j.model.AccountSettingsBillingObject;
import lol.pbu.z4j.model.AccountSettingsBrandingObject;
import lol.pbu.z4j.model.AccountSettingsBrandsObject;
import lol.pbu.z4j.model.AccountSettingsCdnObject;
import lol.pbu.z4j.model.AccountSettingsChatObject;
import lol.pbu.z4j.model.AccountSettingsCrossSellObject;
import lol.pbu.z4j.model.AccountSettingsDeviceObject;
import lol.pbu.z4j.model.AccountSettingsEmailObject;
import lol.pbu.z4j.model.AccountSettingsGoogleAppsObject;
import lol.pbu.z4j.model.AccountSettingsGroupObject;
import lol.pbu.z4j.model.AccountSettingsKnowledgeObject;
import lol.pbu.z4j.model.AccountSettingsLimitsObject;
import lol.pbu.z4j.model.AccountSettingsLocalizationObject;
import lol.pbu.z4j.model.AccountSettingsLotusObject;
import lol.pbu.z4j.model.AccountSettingsMessageInactivityObject;
import lol.pbu.z4j.model.AccountSettingsMetricsObject;
import lol.pbu.z4j.model.AccountSettingsOnboardingObject;
import lol.pbu.z4j.model.AccountSettingsRoutingObject;
import lol.pbu.z4j.model.AccountSettingsRuleObject;
import lol.pbu.z4j.model.AccountSettingsSideConversationsObject;
import lol.pbu.z4j.model.AccountSettingsStatisticsObject;
import lol.pbu.z4j.model.AccountSettingsTicketFormObject;
import lol.pbu.z4j.model.AccountSettingsTicketObject;
import lol.pbu.z4j.model.AccountSettingsTwitterObject;
import lol.pbu.z4j.model.AccountSettingsUserObject;
import lol.pbu.z4j.model.AccountSettingsVoiceObject;
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
 * AccountSettingsObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsObject.JSON_PROPERTY_ACTIVE_FEATURES,
    AccountSettingsObject.JSON_PROPERTY_AGENTS,
    AccountSettingsObject.JSON_PROPERTY_API,
    AccountSettingsObject.JSON_PROPERTY_APPS,
    AccountSettingsObject.JSON_PROPERTY_BILLING,
    AccountSettingsObject.JSON_PROPERTY_BRANDING,
    AccountSettingsObject.JSON_PROPERTY_BRANDS,
    AccountSettingsObject.JSON_PROPERTY_CDN,
    AccountSettingsObject.JSON_PROPERTY_CHAT,
    AccountSettingsObject.JSON_PROPERTY_CROSS_SELL,
    AccountSettingsObject.JSON_PROPERTY_DEVICE_METADATA,
    AccountSettingsObject.JSON_PROPERTY_EMAIL,
    AccountSettingsObject.JSON_PROPERTY_GOOGLE_APPS,
    AccountSettingsObject.JSON_PROPERTY_GROUPS,
    AccountSettingsObject.JSON_PROPERTY_KNOWLEDGE,
    AccountSettingsObject.JSON_PROPERTY_LIMITS,
    AccountSettingsObject.JSON_PROPERTY_LOCALIZATION,
    AccountSettingsObject.JSON_PROPERTY_LOTUS,
    AccountSettingsObject.JSON_PROPERTY_MESSAGING_INACTIVITY,
    AccountSettingsObject.JSON_PROPERTY_METRICS,
    AccountSettingsObject.JSON_PROPERTY_ONBOARDING,
    AccountSettingsObject.JSON_PROPERTY_ROUTING,
    AccountSettingsObject.JSON_PROPERTY_RULE,
    AccountSettingsObject.JSON_PROPERTY_SIDE_CONVERSATIONS,
    AccountSettingsObject.JSON_PROPERTY_STATISTICS,
    AccountSettingsObject.JSON_PROPERTY_TICKET_FORM,
    AccountSettingsObject.JSON_PROPERTY_TICKETS,
    AccountSettingsObject.JSON_PROPERTY_TWITTER,
    AccountSettingsObject.JSON_PROPERTY_USER,
    AccountSettingsObject.JSON_PROPERTY_VOICE,
})
@Serdeable
public class AccountSettingsObject {

    public static final String JSON_PROPERTY_ACTIVE_FEATURES = "active_features";
    public static final String JSON_PROPERTY_AGENTS = "agents";
    public static final String JSON_PROPERTY_API = "api";
    public static final String JSON_PROPERTY_APPS = "apps";
    public static final String JSON_PROPERTY_BILLING = "billing";
    public static final String JSON_PROPERTY_BRANDING = "branding";
    public static final String JSON_PROPERTY_BRANDS = "brands";
    public static final String JSON_PROPERTY_CDN = "cdn";
    public static final String JSON_PROPERTY_CHAT = "chat";
    public static final String JSON_PROPERTY_CROSS_SELL = "cross_sell";
    public static final String JSON_PROPERTY_DEVICE_METADATA = "device_metadata";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_GOOGLE_APPS = "google_apps";
    public static final String JSON_PROPERTY_GROUPS = "groups";
    public static final String JSON_PROPERTY_KNOWLEDGE = "knowledge";
    public static final String JSON_PROPERTY_LIMITS = "limits";
    public static final String JSON_PROPERTY_LOCALIZATION = "localization";
    public static final String JSON_PROPERTY_LOTUS = "lotus";
    public static final String JSON_PROPERTY_MESSAGING_INACTIVITY = "messaging_inactivity";
    public static final String JSON_PROPERTY_METRICS = "metrics";
    public static final String JSON_PROPERTY_ONBOARDING = "onboarding";
    public static final String JSON_PROPERTY_ROUTING = "routing";
    public static final String JSON_PROPERTY_RULE = "rule";
    public static final String JSON_PROPERTY_SIDE_CONVERSATIONS = "side_conversations";
    public static final String JSON_PROPERTY_STATISTICS = "statistics";
    public static final String JSON_PROPERTY_TICKET_FORM = "ticket_form";
    public static final String JSON_PROPERTY_TICKETS = "tickets";
    public static final String JSON_PROPERTY_TWITTER = "twitter";
    public static final String JSON_PROPERTY_USER = "user";
    public static final String JSON_PROPERTY_VOICE = "voice";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ACTIVE_FEATURES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsActiveFeaturesObject activeFeatures;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_AGENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsAgentObject agents;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_API)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsApiObject api;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_APPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsAppsObject apps;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_BILLING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsBillingObject billing;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_BRANDING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsBrandingObject branding;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_BRANDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsBrandsObject brands;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CDN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsCdnObject cdn;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CHAT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsChatObject chat;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CROSS_SELL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsCrossSellObject crossSell;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_DEVICE_METADATA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsDeviceObject deviceMetadata;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsEmailObject email;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_GOOGLE_APPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsGoogleAppsObject googleApps;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_GROUPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsGroupObject groups;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_KNOWLEDGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsKnowledgeObject knowledge;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_LIMITS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsLimitsObject limits;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_LOCALIZATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsLocalizationObject localization;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_LOTUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsLotusObject lotus;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_MESSAGING_INACTIVITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsMessageInactivityObject messagingInactivity;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_METRICS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsMetricsObject metrics;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ONBOARDING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsOnboardingObject onboarding;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ROUTING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsRoutingObject routing;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_RULE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsRuleObject rule;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_SIDE_CONVERSATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsSideConversationsObject sideConversations;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_STATISTICS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsStatisticsObject statistics;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TICKET_FORM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsTicketFormObject ticketForm;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsTicketObject tickets;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TWITTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsTwitterObject twitter;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_USER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsUserObject user;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VOICE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsVoiceObject voice;

}