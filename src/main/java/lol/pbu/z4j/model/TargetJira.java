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
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * TargetJira
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TargetJira.JSON_PROPERTY_PASSWORD,
    TargetJira.JSON_PROPERTY_TARGET_URL,
    TargetJira.JSON_PROPERTY_USERNAME,
})
@Serdeable
public class TargetJira {

    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_TARGET_URL = "target_url";
    public static final String JSON_PROPERTY_USERNAME = "username";

    /**
     * <p>only writable</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_PASSWORD)
    private String password;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TARGET_URL)
    private String targetUrl;

    @NotNull
    @JsonProperty(JSON_PROPERTY_USERNAME)
    private String username;

    public TargetJira(String password, String targetUrl, String username) {
        this.password = password;
        this.targetUrl = targetUrl;
        this.username = username;
    }

}