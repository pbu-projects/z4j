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
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
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
 * TargetObject
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Getter
@Setter
@JsonPropertyOrder({
    TargetObject.JSON_PROPERTY_TITLE,
    TargetObject.JSON_PROPERTY_TYPE,
    TargetObject.JSON_PROPERTY_PASSWORD,
    TargetObject.JSON_PROPERTY_PROJECT_ID,
    TargetObject.JSON_PROPERTY_RESOURCE,
    TargetObject.JSON_PROPERTY_TARGET_URL,
    TargetObject.JSON_PROPERTY_TOKEN,
    TargetObject.JSON_PROPERTY_USERNAME,
    TargetObject.JSON_PROPERTY_ROOM,
    TargetObject.JSON_PROPERTY_SUBDOMAIN,
    TargetObject.JSON_PROPERTY_API_ID,
    TargetObject.JSON_PROPERTY_ATTRIBUTE,
    TargetObject.JSON_PROPERTY_METHOD,
    TargetObject.JSON_PROPERTY_TO,
    TargetObject.JSON_PROPERTY_EMAIL,
    TargetObject.JSON_PROPERTY_SUBJECT,
    TargetObject.JSON_PROPERTY_API_TOKEN,
    TargetObject.JSON_PROPERTY_ACCOUNT_NAME,
    TargetObject.JSON_PROPERTY_STORY_TITLE,
    TargetObject.JSON_PROPERTY_STORY_TYPE,
    TargetObject.JSON_PROPERTY_CONTENT_TYPE,
    TargetObject.JSON_PROPERTY_ACTIVE,
    TargetObject.JSON_PROPERTY_CREATED_AT,
    TargetObject.JSON_PROPERTY_ID,
    TargetObject.JSON_PROPERTY_MESSAGE_ID,
    TargetObject.JSON_PROPERTY_TODO_LIST_ID,
    TargetObject.JSON_PROPERTY_PRESERVE_FORMAT,
    TargetObject.JSON_PROPERTY_SSL,
    TargetObject.JSON_PROPERTY_FROM,
    TargetObject.JSON_PROPERTY_US_SMALL_BUSINESS_ACCOUNT,
    TargetObject.JSON_PROPERTY_OWNER_BY,
    TargetObject.JSON_PROPERTY_REQUESTED_BY,
    TargetObject.JSON_PROPERTY_STORY_LABELS,
    TargetObject.JSON_PROPERTY_SECRET,
    TargetObject.JSON_PROPERTY_GROUP_ID,
})
@Serdeable
public class TargetObject extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_PROJECT_ID = "project_id";
    public static final String JSON_PROPERTY_RESOURCE = "resource";
    public static final String JSON_PROPERTY_TARGET_URL = "target_url";
    public static final String JSON_PROPERTY_TOKEN = "token";
    public static final String JSON_PROPERTY_USERNAME = "username";
    public static final String JSON_PROPERTY_ROOM = "room";
    public static final String JSON_PROPERTY_SUBDOMAIN = "subdomain";
    public static final String JSON_PROPERTY_API_ID = "api_id";
    public static final String JSON_PROPERTY_ATTRIBUTE = "attribute";
    public static final String JSON_PROPERTY_METHOD = "method";
    public static final String JSON_PROPERTY_TO = "to";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_API_TOKEN = "api_token";
    public static final String JSON_PROPERTY_ACCOUNT_NAME = "account_name";
    public static final String JSON_PROPERTY_STORY_TITLE = "story_title";
    public static final String JSON_PROPERTY_STORY_TYPE = "story_type";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MESSAGE_ID = "message_id";
    public static final String JSON_PROPERTY_TODO_LIST_ID = "todo_list_id";
    public static final String JSON_PROPERTY_PRESERVE_FORMAT = "preserve_format";
    public static final String JSON_PROPERTY_SSL = "ssl";
    public static final String JSON_PROPERTY_FROM = "from";
    public static final String JSON_PROPERTY_US_SMALL_BUSINESS_ACCOUNT = "us_small_business_account";
    public static final String JSON_PROPERTY_OWNER_BY = "owner_by";
    public static final String JSON_PROPERTY_REQUESTED_BY = "requested_by";
    public static final String JSON_PROPERTY_STORY_LABELS = "story_labels";
    public static final String JSON_PROPERTY_SECRET = "secret";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";

    /**
     * <p>A name for the target</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>A pre-defined target, such as \"basecamp_target\". See the additional attributes for the type that follow</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TYPE)
    private String type;

    /**
     * <p>only writable</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    private String password;

    @NotNull
    @JsonProperty(JSON_PROPERTY_PROJECT_ID)
    private String projectId;

    /**
     * <p>\"todo\" or \"message\"</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_RESOURCE)
    private String resource;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TARGET_URL)
    private String targetUrl;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TOKEN)
    private String token;

    @NotNull
    @JsonProperty(JSON_PROPERTY_USERNAME)
    private String username;

    @NotNull
    @JsonProperty(JSON_PROPERTY_ROOM)
    private String room;

    @NotNull
    @JsonProperty(JSON_PROPERTY_SUBDOMAIN)
    private String subdomain;

    @NotNull
    @JsonProperty(JSON_PROPERTY_API_ID)
    private String apiId;

    @NotNull
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE)
    private String attribute;

    /**
     * <p>\"get\", \"patch\", \"put\", \"post\", or \"delete\"</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_METHOD)
    private String method;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TO)
    private String to;

    @NotNull
    @JsonProperty(JSON_PROPERTY_EMAIL)
    private String email;

    @NotNull
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    private String subject;

    @NotNull
    @JsonProperty(JSON_PROPERTY_API_TOKEN)
    private String apiToken;

    @NotNull
    @JsonProperty(JSON_PROPERTY_ACCOUNT_NAME)
    private String accountName;

    @NotNull
    @JsonProperty(JSON_PROPERTY_STORY_TITLE)
    private String storyTitle;

    @NotNull
    @JsonProperty(JSON_PROPERTY_STORY_TYPE)
    private String storyType;

    /**
     * <p>\"application/json\", \"application/xml\", or \"application/x-www-form-urlencoded\"</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    private String contentType;

    /**
     * <p>Whether or not the target is activated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The time the target was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Can be filled if it is a \"message\" resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MESSAGE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String messageId;

    /**
     * <p>Can be filled if it is a \"todo\" resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TODO_LIST_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String todoListId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRESERVE_FORMAT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean preserveFormat;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SSL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ssl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FROM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String from;

    @Nullable
    @JsonProperty(JSON_PROPERTY_US_SMALL_BUSINESS_ACCOUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String usSmallBusinessAccount;

    @Nullable
    @JsonProperty(JSON_PROPERTY_OWNER_BY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ownerBy;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTED_BY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String requestedBy;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STORY_LABELS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String storyLabels;

    /**
     * <p>only writable</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SECRET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String secret;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String groupId;

    public TargetObject(String title, String type, String password, String projectId, String resource, String targetUrl, String token, String username, String room, String subdomain, String apiId, String attribute, String method, String to, String email, String subject, String apiToken, String accountName, String storyTitle, String storyType, String contentType) {
        this.title = title;
        this.type = type;
        this.password = password;
        this.projectId = projectId;
        this.resource = resource;
        this.targetUrl = targetUrl;
        this.token = token;
        this.username = username;
        this.room = room;
        this.subdomain = subdomain;
        this.apiId = apiId;
        this.attribute = attribute;
        this.method = method;
        this.to = to;
        this.email = email;
        this.subject = subject;
        this.apiToken = apiToken;
        this.accountName = accountName;
        this.storyTitle = storyTitle;
        this.storyType = storyType;
        this.contentType = contentType;
    }

}