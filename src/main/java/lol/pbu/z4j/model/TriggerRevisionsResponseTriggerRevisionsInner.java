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
import lol.pbu.z4j.model.TriggerRevisionsResponseTriggerRevisionsInnerDiff;
import lol.pbu.z4j.model.TriggerSnapshotObject;
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
 * TriggerRevisionsResponseTriggerRevisionsInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TriggerRevisionsResponseTriggerRevisionsInner.JSON_PROPERTY_AUTHOR_ID,
    TriggerRevisionsResponseTriggerRevisionsInner.JSON_PROPERTY_CREATED_AT,
    TriggerRevisionsResponseTriggerRevisionsInner.JSON_PROPERTY_DIFF,
    TriggerRevisionsResponseTriggerRevisionsInner.JSON_PROPERTY_ID,
    TriggerRevisionsResponseTriggerRevisionsInner.JSON_PROPERTY_SNAPSHOT,
    TriggerRevisionsResponseTriggerRevisionsInner.JSON_PROPERTY_URL,
})
@Serdeable
public class TriggerRevisionsResponseTriggerRevisionsInner {

    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DIFF = "diff";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_SNAPSHOT = "snapshot";
    public static final String JSON_PROPERTY_URL = "url";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long authorId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_DIFF)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TriggerRevisionsResponseTriggerRevisionsInnerDiff diff;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_SNAPSHOT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TriggerSnapshotObject snapshot;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}