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
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Serdeable
@RecordBuilder
public record MergeTicketsRequest(
        @NotNull @JsonProperty(JSON_PROPERTY_IDS) List<Long> ids,
        @Nullable @JsonProperty(JSON_PROPERTY_TARGET_COMMENT) String targetComment,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_COMMENT) String sourceComment
) implements MergeTicketsRequestBuilder.With {

    public static final String JSON_PROPERTY_IDS = "ids";
    public static final String JSON_PROPERTY_TARGET_COMMENT = "target_comment";
    public static final String JSON_PROPERTY_SOURCE_COMMENT = "source_comment";

    public MergeTicketsRequest(List<Long> ids) {
        this(ids, null, null);
    }
}
