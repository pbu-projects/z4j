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
 * AuditLogObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AuditLogObject.JSON_PROPERTY_ACTION,
    AuditLogObject.JSON_PROPERTY_ACTION_LABEL,
    AuditLogObject.JSON_PROPERTY_ACTOR_ID,
    AuditLogObject.JSON_PROPERTY_ACTOR_NAME,
    AuditLogObject.JSON_PROPERTY_CHANGE_DESCRIPTION,
    AuditLogObject.JSON_PROPERTY_CREATED_AT,
    AuditLogObject.JSON_PROPERTY_ID,
    AuditLogObject.JSON_PROPERTY_IP_ADDRESS,
    AuditLogObject.JSON_PROPERTY_SOURCE_ID,
    AuditLogObject.JSON_PROPERTY_SOURCE_LABEL,
    AuditLogObject.JSON_PROPERTY_SOURCE_TYPE,
    AuditLogObject.JSON_PROPERTY_URL,
})
@Serdeable
public class AuditLogObject {

    public static final String JSON_PROPERTY_ACTION = "action";
    public static final String JSON_PROPERTY_ACTION_LABEL = "action_label";
    public static final String JSON_PROPERTY_ACTOR_ID = "actor_id";
    public static final String JSON_PROPERTY_ACTOR_NAME = "actor_name";
    public static final String JSON_PROPERTY_CHANGE_DESCRIPTION = "change_description";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IP_ADDRESS = "ip_address";
    public static final String JSON_PROPERTY_SOURCE_ID = "source_id";
    public static final String JSON_PROPERTY_SOURCE_LABEL = "source_label";
    public static final String JSON_PROPERTY_SOURCE_TYPE = "source_type";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>Type of change made. Possible values are \"create\", \"destroy\", \"exported\", \"login\", and \"update\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String action;

    /**
     * <p>Localized string of action field</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTION_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String actionLabel;

    /**
     * <p>id of the user or system that initiated the change</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer actorId;

    /**
     * <p>Name of the user or system that initiated the change</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTOR_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String actorName;

    /**
     * <p>The description of the change that occurred</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CHANGE_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String changeDescription;

    /**
     * <p>The time the audit got created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The id automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The IP address of the user doing the audit</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IP_ADDRESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ipAddress;

    /**
     * <p>The id of the item being audited</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer sourceId;

    /**
     * <p>The name of the item being audited</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceLabel;

    /**
     * <p>Item type being audited. Typically describes the system where the change was initiated. Possible values vary based on your account's Zendesk products and activity. Common values include \"apitoken\", \"rule\", \"ticket\", \"user\", and \"zendesk/app_market/app\". The \"rule\" value is used for <a href=\"https://support.zendesk.com/hc/en-us/articles/4408832701850\">automations</a>, <a href=\"https://support.zendesk.com/hc/en-us/articles/4408844187034\">macros</a>, <a href=\"https://support.zendesk.com/hc/en-us/articles/4408822236058\">triggers</a>, <a href=\"https://support.zendesk.com/hc/en-us/articles/4408888828570\">views</a>, and other automated business rules</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceType;

    /**
     * <p>The URL to access the audit log</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}