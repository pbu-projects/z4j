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
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.List;
import java.util.Map;

@Serdeable
@RecordBuilder
public record ContentTagsJob(
        @Nullable @JsonProperty(JSON_PROPERTY_ACTION) @JsonInclude(JsonInclude.Include.NON_NULL) String action,
        @Nullable @JsonProperty(JSON_PROPERTY_ITEMS) @JsonInclude(JsonInclude.Include.NON_NULL) List<String> items,
        @Nullable @JsonProperty(JSON_PROPERTY_TAGS) @JsonInclude(JsonInclude.Include.NON_NULL) List<String> tags,
        @Nullable @JsonProperty(JSON_PROPERTY_ARTICLE_IDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<Long> articleIds,
        @Nullable @JsonProperty(JSON_PROPERTY_ATTRIBUTES) @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, Object> attributes
) implements ContentTagsJobBuilder.With {

    public static final String JSON_PROPERTY_ACTION = "action";
    public static final String JSON_PROPERTY_ITEMS = "items";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_ARTICLE_IDS = "article_ids";
    public static final String JSON_PROPERTY_ATTRIBUTES = "attributes";

    public ContentTagsJob(String action, List<String> items) {
        this(action, items, null, null, null);
    }
}
