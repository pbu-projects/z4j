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

/**
 * Meta response found in cursor paginated responses.
 *
 * @param hasMore true if more results exist after this page
 * @param afterCursor cursor string for fetching the next page
 * @param beforeCursor cursor string for fetching the previous page
 * @author Jonathan-Zollinger
 * @since 0.1.4
 */
@Serdeable
@RecordBuilder
public record Meta(
        @Nullable @JsonProperty(JSON_PROPERTY_HAS_MORE) Boolean hasMore,
        @Nullable @JsonProperty(JSON_PROPERTY_AFTER_CURSOR) String afterCursor,
        @Nullable @JsonProperty(JSON_PROPERTY_BEFORE_CURSOR) String beforeCursor
) implements MetaBuilder.With {

    public static final String JSON_PROPERTY_HAS_MORE = "has_more";
    public static final String JSON_PROPERTY_AFTER_CURSOR = "after_cursor";
    public static final String JSON_PROPERTY_BEFORE_CURSOR = "before_cursor";
}
