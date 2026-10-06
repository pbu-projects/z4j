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
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import lol.pbu.z4j.Generated;

/**
 * <h1>Individual Result Entry in a Background Job Status.</h1>
 * <p>Represents the outcome of an asynchronous batch operation executed for a specific entity in Zendesk.</p>
 *
 * @author Graeme-Rocher
 * @since 0.3.2
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        "id",
        "action",
        "status",
        "success",
        "title",
        "error",
        "details"
})
@Serdeable
@Introspected
@Generated
public class JobStatusResult {

    @Nullable
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @JsonProperty("action")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String action;

    @Nullable
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    @Nullable
    @JsonProperty("success")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean success;

    @Nullable
    @JsonProperty("title")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    @Nullable
    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String error;

    @Nullable
    @JsonProperty("details")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String details;
}
