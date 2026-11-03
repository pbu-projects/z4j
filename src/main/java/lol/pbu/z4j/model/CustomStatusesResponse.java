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
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.List;

/**
 * Response payload containing a list of Zendesk custom ticket statuses.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.6
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        CustomStatusesResponse.JSON_PROPERTY_CUSTOM_STATUSES,
        OffsetPaginationResponse.JSON_PROPERTY_COUNT,
        OffsetPaginationResponse.JSON_PROPERTY_NEXT_PAGE,
        OffsetPaginationResponse.JSON_PROPERTY_PREVIOUS_PAGE
})
public record CustomStatusesResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_STATUSES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<TicketFieldCustomStatusObject> customStatuses,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count,
        @Nullable @JsonProperty(JSON_PROPERTY_NEXT_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String nextPage,
        @Nullable @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String previousPage
) implements OffsetPaginationResponse, CustomStatusesResponseBuilder.With {

    public static final String JSON_PROPERTY_CUSTOM_STATUSES = "custom_statuses";

    public CustomStatusesResponse(@Nullable List<TicketFieldCustomStatusObject> customStatuses) {
        this(customStatuses, null, null, null);
    }
}
