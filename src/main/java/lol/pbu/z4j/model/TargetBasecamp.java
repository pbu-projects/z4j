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
 * TargetBasecamp
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetBasecamp.JSON_PROPERTY_PROJECT_ID,
    TargetBasecamp.JSON_PROPERTY_RESOURCE,
    TargetBasecamp.JSON_PROPERTY_TARGET_URL,
    TargetBasecamp.JSON_PROPERTY_TOKEN,
    TargetBasecamp.JSON_PROPERTY_MESSAGE_ID,
    TargetBasecamp.JSON_PROPERTY_PASSWORD,
    TargetBasecamp.JSON_PROPERTY_TODO_LIST_ID,
    TargetBasecamp.JSON_PROPERTY_USERNAME,
})
@Serdeable
public class TargetBasecamp {

    public static final String JSON_PROPERTY_PROJECT_ID = "project_id";
    public static final String JSON_PROPERTY_RESOURCE = "resource";
    public static final String JSON_PROPERTY_TARGET_URL = "target_url";
    public static final String JSON_PROPERTY_TOKEN = "token";
    public static final String JSON_PROPERTY_MESSAGE_ID = "message_id";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_TODO_LIST_ID = "todo_list_id";
    public static final String JSON_PROPERTY_USERNAME = "username";

    /**
     * <p>The ID of the project in Basecamp where updates should be pushed</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_PROJECT_ID)
    private String projectId;

    /**
     * <p>\"todo\" or \"message\"</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_RESOURCE)
    private String resource;

    /**
     * <p>The URL of your Basecamp account, including protocol and path</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TARGET_URL)
    private String targetUrl;

    /**
     * <p>Get the API token from My info &gt; Show your tokens &gt; Token for feed readers or the Basecamp API in your Basecamp account</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TOKEN)
    private String token;

    /**
     * <p>Can be filled if it is a \"message\" resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MESSAGE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String messageId;

    /**
     * <p>The 37Signals password for the Basecamp account (only writable)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String password;

    /**
     * <p>Can be filled if it is a \"todo\" resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TODO_LIST_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String todoListId;

    /**
     * <p>The 37Signals username of the account you use to log in to Basecamp</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USERNAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String username;

    public TargetBasecamp(String projectId, String resource, String targetUrl, String token) {
        this.projectId = projectId;
        this.resource = resource;
        this.targetUrl = targetUrl;
        this.token = token;
    }

}