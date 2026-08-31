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
 * SessionObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    SessionObject.JSON_PROPERTY_ID,
    SessionObject.JSON_PROPERTY_AUTHENTICATED_AT,
    SessionObject.JSON_PROPERTY_LAST_SEEN_AT,
    SessionObject.JSON_PROPERTY_URL,
    SessionObject.JSON_PROPERTY_USER_ID,
})
@Serdeable
public class SessionObject {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_AUTHENTICATED_AT = "authenticated_at";
    public static final String JSON_PROPERTY_LAST_SEEN_AT = "last_seen_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_ID = "user_id";

    /**
     * <p>Automatically assigned when the session is created</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_ID)
    private Long id;

    /**
     * <p>When the session was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHENTICATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String authenticatedAt;

    /**
     * <p>The last approximate time this session was seen. This does not update on every request.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LAST_SEEN_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String lastSeenAt;

    /**
     * <p>The API URL of this session</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>The id of the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userId;

    public SessionObject(Long id) {
        this.id = id;
    }

}