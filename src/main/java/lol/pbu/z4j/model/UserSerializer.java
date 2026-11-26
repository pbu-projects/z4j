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

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Serializes {@link User}, keeping empty lists and explicitly cleared nulls while omitting unset fields.
 */
@Singleton
public class UserSerializer implements Serializer<User> {

    private static final Argument<User> USER_ARG = Argument.of(User.class);
    private static final Argument<List<Long>> LONG_LIST_ARG = Argument.listOf(Long.class);
    private static final Argument<List<String>> STRING_LIST_ARG = Argument.listOf(String.class);
    private static final Argument<ZonedDateTime> ZONED_DATE_TIME_ARG = Argument.of(ZonedDateTime.class);
    private static final Argument<Attachment> ATTACHMENT_ARG = Argument.of(Attachment.class);
    private static final Argument<Map<String, Object>> USER_FIELDS_ARG = Argument.mapOf(String.class, Object.class);

    @Override
    public void serialize(
            @NonNull Encoder encoder,
            @NonNull EncoderContext context,
            @NonNull Argument<? extends User> type,
            @Nullable User value
    ) throws IOException {
        if (value == null) {
            encoder.encodeNull();
            return;
        }
        Set<String> cleared = value.clearedFields();
        try (Encoder obj = encoder.encodeObject(USER_ARG)) {
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_ID, Argument.LONG, value.id(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_AGENT_BRAND_IDS, LONG_LIST_ARG, value.agentBrandIds(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_EMAIL, Argument.STRING, value.email(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_NAME, Argument.STRING, value.name(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_ROLE, Argument.STRING, value.role(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_ACTIVE, Argument.BOOLEAN, value.active(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_ALIAS, Argument.STRING, value.alias(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_CHAT_ONLY, Argument.BOOLEAN, value.chatOnly(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_CUSTOM_ROLE_ID, Argument.LONG, value.customRoleId(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_DEFAULT_GROUP_ID, Argument.LONG, value.defaultGroupId(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_DETAILS, Argument.STRING, value.details(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_EXTERNAL_ID, Argument.STRING, value.externalId(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_IANA_TIME_ZONE, Argument.STRING, value.ianaTimeZone(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_LAST_LOGIN_AT, ZONED_DATE_TIME_ARG, value.lastLoginAt(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_LOCALE, Argument.STRING, value.locale(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_LOCALE_ID, Argument.LONG, value.localeId(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_MODERATOR, Argument.BOOLEAN, value.moderator(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_NOTES, Argument.STRING, value.notes(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_ONLY_PRIVATE_COMMENTS, Argument.BOOLEAN, value.onlyPrivateComments(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_ORGANIZATION_ID, Argument.LONG, value.organizationId(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_PHONE, Argument.STRING, value.phone(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_PHOTO, ATTACHMENT_ARG, value.photo(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_REMOTE_PHOTO_URL, Argument.STRING, value.remotePhotoUrl(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_REPORT_CSV, Argument.BOOLEAN, value.reportCsv(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_RESTRICTED_AGENT, Argument.BOOLEAN, value.restrictedAgent(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_ROLE_TYPE, Argument.INT, value.roleType(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_SHARED, Argument.BOOLEAN, value.shared(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_SHARED_AGENT, Argument.BOOLEAN, value.sharedAgent(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_SHARED_PHONE_NUMBER, Argument.BOOLEAN, value.sharedPhoneNumber(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_SIGNATURE, Argument.STRING, value.signature(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_SUSPENDED, Argument.BOOLEAN, value.suspended(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_TAGS, STRING_LIST_ARG, value.tags(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_TICKET_RESTRICTION, Argument.STRING, value.ticketRestriction(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_TIME_ZONE, Argument.STRING, value.timeZone(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED, Argument.BOOLEAN, value.twoFactorAuthEnabled(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_URL, Argument.STRING, value.url(), cleared);
            PartialUpdateSerde.writeNullableMapField(obj, context, User.JSON_PROPERTY_USER_FIELDS, USER_FIELDS_ARG, value.userFields(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_VERIFIED, Argument.BOOLEAN, value.verified(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_AUTHENTICITY_TOKEN, Argument.STRING, value.authenticityToken(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_CREATED_AT, ZONED_DATE_TIME_ARG, value.createdAt(), cleared);
            PartialUpdateSerde.writeField(obj, context, User.JSON_PROPERTY_UPDATED_AT, ZONED_DATE_TIME_ARG, value.updatedAt(), cleared);
        }
    }
}
