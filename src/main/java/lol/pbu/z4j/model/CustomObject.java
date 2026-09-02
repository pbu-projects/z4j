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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * CustomObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    CustomObject.JSON_PROPERTY_INCLUDE_IN_LIST_VIEW,
    CustomObject.JSON_PROPERTY_KEY,
    CustomObject.JSON_PROPERTY_TITLE,
    CustomObject.JSON_PROPERTY_TITLE_PLURALIZED,
    CustomObject.JSON_PROPERTY_ALLOWS_ATTACHMENTS,
    CustomObject.JSON_PROPERTY_ALLOWS_PHOTOS,
    CustomObject.JSON_PROPERTY_CREATED_AT,
    CustomObject.JSON_PROPERTY_CREATED_BY_USER_ID,
    CustomObject.JSON_PROPERTY_DESCRIPTION,
    CustomObject.JSON_PROPERTY_RAW_DESCRIPTION,
    CustomObject.JSON_PROPERTY_RAW_TITLE,
    CustomObject.JSON_PROPERTY_RAW_TITLE_PLURALIZED,
    CustomObject.JSON_PROPERTY_UPDATED_AT,
    CustomObject.JSON_PROPERTY_UPDATED_BY_USER_ID,
    CustomObject.JSON_PROPERTY_URL,
})
@Serdeable
public class CustomObject {

    public static final String JSON_PROPERTY_INCLUDE_IN_LIST_VIEW = "include_in_list_view";
    public static final String JSON_PROPERTY_KEY = "key";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TITLE_PLURALIZED = "title_pluralized";
    public static final String JSON_PROPERTY_ALLOWS_ATTACHMENTS = "allows_attachments";
    public static final String JSON_PROPERTY_ALLOWS_PHOTOS = "allows_photos";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY_USER_ID = "created_by_user_id";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_RAW_DESCRIPTION = "raw_description";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_RAW_TITLE_PLURALIZED = "raw_title_pluralized";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_UPDATED_BY_USER_ID = "updated_by_user_id";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>A flag setting the visibility of the object in the agent's list view. If true, all agents and admins have viewing access to the object in the Custom objects record page in the Agent Workspace. If false, only admins have viewing access</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_INCLUDE_IN_LIST_VIEW)
    private Boolean includeInListView;

    /**
     * <p>A user-defined unique identifier. Writable on create only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String key;

    /**
     * <p>User-defined display name for the object</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>User-defined pluralized version of the object's title</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE_PLURALIZED)
    private String titlePluralized;

    /**
     * <p>If true, file attachments can be added to the object's records. If false, new attachments can't be added to the object's records, but existing attachments on records can still be viewed and downloaded</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOWS_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowsAttachments;

    /**
     * <p>If true, photos can be uploaded to the records of the object. If false, new photos cannot be uploaded but existing photos can still be viewed and removed</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOWS_PHOTOS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowsPhotos;

    /**
     * <p>The time the object type was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Id of a user who created the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdByUserId;

    /**
     * <p>User-defined description of the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>The dynamic content placeholder, if present, or the \"raw_description\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawDescription;

    /**
     * <p>The dynamic content placeholder, if present, or the \"title\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;

    /**
     * <p>The dynamic content placeholder, if present, or the \"raw_title_pluralized\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE_PLURALIZED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitlePluralized;

    /**
     * <p>The time of the last update of the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>Id of the last user who updated the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedByUserId;

    /**
     * <p>Direct link to the specific custom object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public CustomObject(Boolean includeInListView, String title, String titlePluralized) {
        this.includeInListView = includeInListView;
        this.title = title;
        this.titlePluralized = titlePluralized;
    }

}