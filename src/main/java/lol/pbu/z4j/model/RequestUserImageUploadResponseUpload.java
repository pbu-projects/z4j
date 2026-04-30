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

import java.util.HashMap;
import java.util.Map;

/**
 * RequestUserImageUploadResponseUpload
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        RequestUserImageUploadResponseUpload.JSON_PROPERTY_HEADERS,
        RequestUserImageUploadResponseUpload.JSON_PROPERTY_TOKEN,
        RequestUserImageUploadResponseUpload.JSON_PROPERTY_URL,
})
@Serdeable
public class RequestUserImageUploadResponseUpload {

    public static final String JSON_PROPERTY_HEADERS = "headers";
    public static final String JSON_PROPERTY_TOKEN = "token";
    public static final String JSON_PROPERTY_URL = "url";

    @Nullable
    @JsonProperty(JSON_PROPERTY_HEADERS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> headers;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TOKEN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String token;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * Set the value for the key for the headers map property in a chainable fashion.
     *
     * @return The same instance of RequestUserImageUploadResponseUpload for chaining.
     */
    public RequestUserImageUploadResponseUpload putHeadersItem(String key, Object headersItem) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, headersItem);
        return this;
    }

}
