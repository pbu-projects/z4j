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

/**
 * BatchErrorItem
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    BatchErrorItem.JSON_PROPERTY_CODE,
    BatchErrorItem.JSON_PROPERTY_TITLE,
    BatchErrorItem.JSON_PROPERTY_DETAIL,
    BatchErrorItem.JSON_PROPERTY_ID,
    BatchErrorItem.JSON_PROPERTY_LINKS,
    BatchErrorItem.JSON_PROPERTY_SOURCE,
    BatchErrorItem.JSON_PROPERTY_STATUS,
    BatchErrorItem.JSON_PROPERTY_TRIGGER_ID,
})
@Serdeable
public class BatchErrorItem {

    public static final String JSON_PROPERTY_CODE = "code";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_DETAIL = "detail";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LINKS = "links";
    public static final String JSON_PROPERTY_SOURCE = "source";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_TRIGGER_ID = "trigger_id";

    @NotNull
    @JsonProperty(JSON_PROPERTY_CODE)
    private String code;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DETAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String detail;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LINKS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object links;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object source;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TRIGGER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String triggerId;

    public BatchErrorItem(String code, String title) {
        this.code = code;
        this.title = title;
    }

}