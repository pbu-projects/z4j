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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User object in the Zendesk Support Api.
 * Unset fields are omitted, empty lists serialize as {@code []}, and {@code clear*()} methods
 * send an explicit {@code null}. Calling {@code with*()} or {@code add*Item()} on a cleared
 * field resets its cleared state so a later {@code with*(null)} omits the field.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        User.JSON_PROPERTY_ID,
        User.JSON_PROPERTY_AGENT_BRAND_IDS,
        User.JSON_PROPERTY_EMAIL,
        User.JSON_PROPERTY_NAME,
        User.JSON_PROPERTY_ROLE,
        User.JSON_PROPERTY_ACTIVE,
        User.JSON_PROPERTY_ALIAS,
        User.JSON_PROPERTY_CHAT_ONLY,
        User.JSON_PROPERTY_CUSTOM_ROLE_ID,
        User.JSON_PROPERTY_DEFAULT_GROUP_ID,
        User.JSON_PROPERTY_DETAILS,
        User.JSON_PROPERTY_EXTERNAL_ID,
        User.JSON_PROPERTY_IANA_TIME_ZONE,
        User.JSON_PROPERTY_LAST_LOGIN_AT,
        User.JSON_PROPERTY_LOCALE,
        User.JSON_PROPERTY_LOCALE_ID,
        User.JSON_PROPERTY_MODERATOR,
        User.JSON_PROPERTY_NOTES,
        User.JSON_PROPERTY_ONLY_PRIVATE_COMMENTS,
        User.JSON_PROPERTY_ORGANIZATION_ID,
        User.JSON_PROPERTY_PHONE,
        User.JSON_PROPERTY_PHOTO,
        User.JSON_PROPERTY_REMOTE_PHOTO_URL,
        User.JSON_PROPERTY_REPORT_CSV,
        User.JSON_PROPERTY_RESTRICTED_AGENT,
        User.JSON_PROPERTY_ROLE_TYPE,
        User.JSON_PROPERTY_SHARED,
        User.JSON_PROPERTY_SHARED_AGENT,
        User.JSON_PROPERTY_SHARED_PHONE_NUMBER,
        User.JSON_PROPERTY_SIGNATURE,
        User.JSON_PROPERTY_SUSPENDED,
        User.JSON_PROPERTY_TAGS,
        User.JSON_PROPERTY_TICKET_RESTRICTION,
        User.JSON_PROPERTY_TIME_ZONE,
        User.JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED,
        User.JSON_PROPERTY_URL,
        User.JSON_PROPERTY_USER_FIELDS,
        User.JSON_PROPERTY_VERIFIED,
        User.JSON_PROPERTY_AUTHENTICITY_TOKEN,
        User.JSON_PROPERTY_CREATED_AT,
        User.JSON_PROPERTY_UPDATED_AT
})
public record User(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_AGENT_BRAND_IDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<Long> agentBrandIds,
        @Nullable @JsonProperty(JSON_PROPERTY_EMAIL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String email,
        @Nullable @JsonProperty(JSON_PROPERTY_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_ROLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String role,
        @Nullable @JsonProperty(JSON_PROPERTY_ACTIVE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean active,
        @Nullable @JsonProperty(JSON_PROPERTY_ALIAS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String alias,
        @Nullable @JsonProperty(JSON_PROPERTY_CHAT_ONLY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean chatOnly,
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_ROLE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long customRoleId,
        @Nullable @JsonProperty(JSON_PROPERTY_DEFAULT_GROUP_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long defaultGroupId,
        @Nullable @JsonProperty(JSON_PROPERTY_DETAILS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String details,
        @Nullable @JsonProperty(JSON_PROPERTY_EXTERNAL_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String externalId,
        @Nullable @JsonProperty(JSON_PROPERTY_IANA_TIME_ZONE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String ianaTimeZone,
        @Nullable @JsonProperty(JSON_PROPERTY_LAST_LOGIN_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime lastLoginAt,
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String locale,
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long localeId,
        @Nullable @JsonProperty(JSON_PROPERTY_MODERATOR) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean moderator,
        @Nullable @JsonProperty(JSON_PROPERTY_NOTES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String notes,
        @Nullable @JsonProperty(JSON_PROPERTY_ONLY_PRIVATE_COMMENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean onlyPrivateComments,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long organizationId,
        @Nullable @JsonProperty(JSON_PROPERTY_PHONE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String phone,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_PHOTO) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Attachment photo,
        @Nullable @JsonProperty(JSON_PROPERTY_REMOTE_PHOTO_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String remotePhotoUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_REPORT_CSV) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean reportCsv,
        @Nullable @JsonProperty(JSON_PROPERTY_RESTRICTED_AGENT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean restrictedAgent,
        @Nullable @JsonProperty(JSON_PROPERTY_ROLE_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer roleType,
        @Nullable @JsonProperty(JSON_PROPERTY_SHARED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean shared,
        @Nullable @JsonProperty(JSON_PROPERTY_SHARED_AGENT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean sharedAgent,
        @Nullable @JsonProperty(JSON_PROPERTY_SHARED_PHONE_NUMBER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean sharedPhoneNumber,
        @Nullable @JsonProperty(JSON_PROPERTY_SIGNATURE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String signature,
        @Nullable @JsonProperty(JSON_PROPERTY_SUSPENDED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean suspended,
        @Nullable @JsonProperty(JSON_PROPERTY_TAGS) @JsonInclude(JsonInclude.Include.NON_NULL) List<String> tags,
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_RESTRICTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String ticketRestriction,
        @Nullable @JsonProperty(JSON_PROPERTY_TIME_ZONE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String timeZone,
        @Nullable @JsonProperty(JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean twoFactorAuthEnabled,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_FIELDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Map<String, Object> userFields,
        @Nullable @JsonProperty(JSON_PROPERTY_VERIFIED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean verified,
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHENTICITY_TOKEN) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String authenticityToken,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt,
        @Nullable @JsonIgnore Set<String> clearedFields
) implements Exportable, UserBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_AGENT_BRAND_IDS = "agent_brand_ids";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_ROLE = "role";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_ALIAS = "alias";
    public static final String JSON_PROPERTY_CHAT_ONLY = "chat_only";
    public static final String JSON_PROPERTY_CUSTOM_ROLE_ID = "custom_role_id";
    public static final String JSON_PROPERTY_DEFAULT_GROUP_ID = "default_group_id";
    public static final String JSON_PROPERTY_DETAILS = "details";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_IANA_TIME_ZONE = "iana_time_zone";
    public static final String JSON_PROPERTY_LAST_LOGIN_AT = "last_login_at";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_LOCALE_ID = "locale_id";
    public static final String JSON_PROPERTY_MODERATOR = "moderator";
    public static final String JSON_PROPERTY_NOTES = "notes";
    public static final String JSON_PROPERTY_ONLY_PRIVATE_COMMENTS = "only_private_comments";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PHONE = "phone";
    public static final String JSON_PROPERTY_PHOTO = "photo";
    public static final String JSON_PROPERTY_REMOTE_PHOTO_URL = "remote_photo_url";
    public static final String JSON_PROPERTY_REPORT_CSV = "report_csv";
    public static final String JSON_PROPERTY_RESTRICTED_AGENT = "restricted_agent";
    public static final String JSON_PROPERTY_ROLE_TYPE = "role_type";
    public static final String JSON_PROPERTY_SHARED = "shared";
    public static final String JSON_PROPERTY_SHARED_AGENT = "shared_agent";
    public static final String JSON_PROPERTY_SHARED_PHONE_NUMBER = "shared_phone_number";
    public static final String JSON_PROPERTY_SIGNATURE = "signature";
    public static final String JSON_PROPERTY_SUSPENDED = "suspended";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_TICKET_RESTRICTION = "ticket_restriction";
    public static final String JSON_PROPERTY_TIME_ZONE = "time_zone";
    public static final String JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED = "two_factor_auth_enabled";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_FIELDS = "user_fields";
    public static final String JSON_PROPERTY_VERIFIED = "verified";
    public static final String JSON_PROPERTY_AUTHENTICITY_TOKEN = "authenticity_token";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public User {
        clearedFields = clearedFields == null ? Set.of() : Set.copyOf(clearedFields);
    }

    public User() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Set.of());
    }

    public User(
            Long id,
            List<Long> agentBrandIds,
            String email,
            String name,
            String role,
            Boolean active,
            String alias,
            Boolean chatOnly,
            Long customRoleId,
            Long defaultGroupId,
            String details,
            String externalId,
            String ianaTimeZone,
            ZonedDateTime lastLoginAt,
            String locale,
            Long localeId,
            Boolean moderator,
            String notes,
            Boolean onlyPrivateComments,
            Long organizationId,
            String phone,
            Attachment photo,
            String remotePhotoUrl,
            Boolean reportCsv,
            Boolean restrictedAgent,
            Integer roleType,
            Boolean shared,
            Boolean sharedAgent,
            Boolean sharedPhoneNumber,
            String signature,
            Boolean suspended,
            List<String> tags,
            String ticketRestriction,
            String timeZone,
            Boolean twoFactorAuthEnabled,
            String url,
            Map<String, Object> userFields,
            Boolean verified,
            String authenticityToken,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt
    ) {
        this(id, agentBrandIds, email, name, role, active, alias, chatOnly, customRoleId, defaultGroupId, details,
                externalId, ianaTimeZone, lastLoginAt, locale, localeId, moderator, notes, onlyPrivateComments,
                organizationId, phone, photo, remotePhotoUrl, reportCsv, restrictedAgent, roleType, shared,
                sharedAgent, sharedPhoneNumber, signature, suspended, tags, ticketRestriction, timeZone,
                twoFactorAuthEnabled, url, userFields, verified, authenticityToken, createdAt, updatedAt, Set.of());
    }

    public User addTagsItem(String tagsItem) {
        List<String> newTags = tags == null ? new ArrayList<>() : new ArrayList<>(tags);
        newTags.add(tagsItem);
        return withTags(newTags);
    }

    public User addAgentBrandIdsItem(Long agentBrandIdsItem) {
        List<Long> newAgentBrandIds = agentBrandIds == null ? new ArrayList<>() : new ArrayList<>(agentBrandIds);
        newAgentBrandIds.add(agentBrandIdsItem);
        return withAgentBrandIds(newAgentBrandIds);
    }

    @Override
    public User withAgentBrandIds(@Nullable List<Long> agentBrandIds) {
        return UserBuilder.With.super.withAgentBrandIds(agentBrandIds)
                .unmarkCleared(JSON_PROPERTY_AGENT_BRAND_IDS);
    }

    @Override
    public User withAlias(@Nullable String alias) {
        return UserBuilder.With.super.withAlias(alias)
                .unmarkCleared(JSON_PROPERTY_ALIAS);
    }

    @Override
    public User withCustomRoleId(@Nullable Long customRoleId) {
        return UserBuilder.With.super.withCustomRoleId(customRoleId)
                .unmarkCleared(JSON_PROPERTY_CUSTOM_ROLE_ID);
    }

    @Override
    public User withDefaultGroupId(@Nullable Long defaultGroupId) {
        return UserBuilder.With.super.withDefaultGroupId(defaultGroupId)
                .unmarkCleared(JSON_PROPERTY_DEFAULT_GROUP_ID);
    }

    @Override
    public User withDetails(@Nullable String details) {
        return UserBuilder.With.super.withDetails(details)
                .unmarkCleared(JSON_PROPERTY_DETAILS);
    }

    @Override
    public User withExternalId(@Nullable String externalId) {
        return UserBuilder.With.super.withExternalId(externalId)
                .unmarkCleared(JSON_PROPERTY_EXTERNAL_ID);
    }

    @Override
    public User withLocale(@Nullable String locale) {
        return UserBuilder.With.super.withLocale(locale)
                .unmarkCleared(JSON_PROPERTY_LOCALE);
    }

    @Override
    public User withLocaleId(@Nullable Long localeId) {
        return UserBuilder.With.super.withLocaleId(localeId)
                .unmarkCleared(JSON_PROPERTY_LOCALE_ID);
    }

    @Override
    public User withNotes(@Nullable String notes) {
        return UserBuilder.With.super.withNotes(notes)
                .unmarkCleared(JSON_PROPERTY_NOTES);
    }

    @Override
    public User withOrganizationId(@Nullable Long organizationId) {
        return UserBuilder.With.super.withOrganizationId(organizationId)
                .unmarkCleared(JSON_PROPERTY_ORGANIZATION_ID);
    }

    @Override
    public User withPhone(@Nullable String phone) {
        return UserBuilder.With.super.withPhone(phone)
                .unmarkCleared(JSON_PROPERTY_PHONE);
    }

    @Override
    public User withPhoto(@Nullable Attachment photo) {
        return UserBuilder.With.super.withPhoto(photo)
                .unmarkCleared(JSON_PROPERTY_PHOTO);
    }

    @Override
    public User withRemotePhotoUrl(@Nullable String remotePhotoUrl) {
        return UserBuilder.With.super.withRemotePhotoUrl(remotePhotoUrl)
                .unmarkCleared(JSON_PROPERTY_REMOTE_PHOTO_URL);
    }

    @Override
    public User withSignature(@Nullable String signature) {
        return UserBuilder.With.super.withSignature(signature)
                .unmarkCleared(JSON_PROPERTY_SIGNATURE);
    }

    @Override
    public User withTags(@Nullable List<String> tags) {
        return UserBuilder.With.super.withTags(tags)
                .unmarkCleared(JSON_PROPERTY_TAGS);
    }

    @Override
    public User withTicketRestriction(@Nullable String ticketRestriction) {
        return UserBuilder.With.super.withTicketRestriction(ticketRestriction)
                .unmarkCleared(JSON_PROPERTY_TICKET_RESTRICTION);
    }

    @Override
    public User withTimeZone(@Nullable String timeZone) {
        return UserBuilder.With.super.withTimeZone(timeZone)
                .unmarkCleared(JSON_PROPERTY_TIME_ZONE);
    }

    @Override
    public User withUserFields(@Nullable Map<String, Object> userFields) {
        return UserBuilder.With.super.withUserFields(userFields)
                .unmarkCleared(JSON_PROPERTY_USER_FIELDS);
    }

    public User clearAgentBrandIds() {
        return withAgentBrandIds(null).markCleared(JSON_PROPERTY_AGENT_BRAND_IDS);
    }

    public User clearAlias() {
        return withAlias(null).markCleared(JSON_PROPERTY_ALIAS);
    }

    public User clearCustomRoleId() {
        return withCustomRoleId(null).markCleared(JSON_PROPERTY_CUSTOM_ROLE_ID);
    }

    public User clearDefaultGroupId() {
        return withDefaultGroupId(null).markCleared(JSON_PROPERTY_DEFAULT_GROUP_ID);
    }

    public User clearDetails() {
        return withDetails(null).markCleared(JSON_PROPERTY_DETAILS);
    }

    public User clearExternalId() {
        return withExternalId(null).markCleared(JSON_PROPERTY_EXTERNAL_ID);
    }

    public User clearLocale() {
        return withLocale(null).markCleared(JSON_PROPERTY_LOCALE);
    }

    public User clearLocaleId() {
        return withLocaleId(null).markCleared(JSON_PROPERTY_LOCALE_ID);
    }

    public User clearNotes() {
        return withNotes(null).markCleared(JSON_PROPERTY_NOTES);
    }

    public User clearOrganizationId() {
        return withOrganizationId(null).markCleared(JSON_PROPERTY_ORGANIZATION_ID);
    }

    public User clearPhone() {
        return withPhone(null).markCleared(JSON_PROPERTY_PHONE);
    }

    public User clearPhoto() {
        return withPhoto(null).markCleared(JSON_PROPERTY_PHOTO);
    }

    public User clearRemotePhotoUrl() {
        return withRemotePhotoUrl(null).markCleared(JSON_PROPERTY_REMOTE_PHOTO_URL);
    }

    public User clearSignature() {
        return withSignature(null).markCleared(JSON_PROPERTY_SIGNATURE);
    }

    public User clearTags() {
        return withTags(null).markCleared(JSON_PROPERTY_TAGS);
    }

    public User clearTicketRestriction() {
        return withTicketRestriction(null).markCleared(JSON_PROPERTY_TICKET_RESTRICTION);
    }

    public User clearTimeZone() {
        return withTimeZone(null).markCleared(JSON_PROPERTY_TIME_ZONE);
    }

    public User clearUserFields() {
        return withUserFields(null).markCleared(JSON_PROPERTY_USER_FIELDS);
    }

    private User markCleared(String jsonProperty) {
        return withClearedFields(PartialUpdateSerde.addClearedField(clearedFields, jsonProperty));
    }

    private User unmarkCleared(String jsonProperty) {
        return clearedFields.contains(jsonProperty)
                ? withClearedFields(PartialUpdateSerde.removeClearedField(clearedFields, jsonProperty))
                : this;
    }
}
