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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * DeletedUserObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    DeletedUserObject.JSON_PROPERTY_ACTIVE,
    DeletedUserObject.JSON_PROPERTY_CREATED_AT,
    DeletedUserObject.JSON_PROPERTY_EMAIL,
    DeletedUserObject.JSON_PROPERTY_ID,
    DeletedUserObject.JSON_PROPERTY_LOCALE,
    DeletedUserObject.JSON_PROPERTY_LOCALE_ID,
    DeletedUserObject.JSON_PROPERTY_NAME,
    DeletedUserObject.JSON_PROPERTY_ORGANIZATION_ID,
    DeletedUserObject.JSON_PROPERTY_PHONE,
    DeletedUserObject.JSON_PROPERTY_PHOTO,
    DeletedUserObject.JSON_PROPERTY_ROLE,
    DeletedUserObject.JSON_PROPERTY_SHARED_PHONE_NUMBER,
    DeletedUserObject.JSON_PROPERTY_TIME_ZONE,
    DeletedUserObject.JSON_PROPERTY_UPDATED_AT,
    DeletedUserObject.JSON_PROPERTY_URL,
})
@Serdeable
public class DeletedUserObject {

    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_LOCALE_ID = "locale_id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PHONE = "phone";
    public static final String JSON_PROPERTY_PHOTO = "photo";
    public static final String JSON_PROPERTY_ROLE = "role";
    public static final String JSON_PROPERTY_SHARED_PHONE_NUMBER = "shared_phone_number";
    public static final String JSON_PROPERTY_TIME_ZONE = "time_zone";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    @NotNull
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    private Boolean active;

    @NotNull
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    private String createdAt;

    @NotNull
    @JsonProperty(JSON_PROPERTY_EMAIL)
    private String email;

    @NotNull
    @JsonProperty(JSON_PROPERTY_ID)
    private Long id;

    @NotNull
    @JsonProperty(JSON_PROPERTY_LOCALE)
    private String locale;

    @NotNull
    @JsonProperty(JSON_PROPERTY_LOCALE_ID)
    private Long localeId;

    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    @NotNull
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    private Long organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PHONE)
    private String phone;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PHOTO)
    private Object photo;

    @NotNull
    @JsonProperty(JSON_PROPERTY_ROLE)
    private String role;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_PHONE_NUMBER)
    private String sharedPhoneNumber;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TIME_ZONE)
    private String timeZone;

    @NotNull
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    private String updatedAt;

    @NotNull
    @JsonProperty(JSON_PROPERTY_URL)
    private String url;

    public DeletedUserObject(Boolean active, String createdAt, String email, Long id, String locale, Long localeId, String name, Long organizationId, String phone, Object photo, String role, String sharedPhoneNumber, String timeZone, String updatedAt, String url) {
        this.active = active;
        this.createdAt = createdAt;
        this.email = email;
        this.id = id;
        this.locale = locale;
        this.localeId = localeId;
        this.name = name;
        this.organizationId = organizationId;
        this.phone = phone;
        this.photo = photo;
        this.role = role;
        this.sharedPhoneNumber = sharedPhoneNumber;
        this.timeZone = timeZone;
        this.updatedAt = updatedAt;
        this.url = url;
    }

}