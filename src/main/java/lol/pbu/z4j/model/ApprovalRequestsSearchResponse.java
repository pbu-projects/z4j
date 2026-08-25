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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.ApprovalRequestsSearchResponseApprovalRequestsInner;
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
 * ApprovalRequestsSearchResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ApprovalRequestsSearchResponse.JSON_PROPERTY_AFTER_CURSOR,
    ApprovalRequestsSearchResponse.JSON_PROPERTY_AFTER_URL,
    ApprovalRequestsSearchResponse.JSON_PROPERTY_APPROVAL_REQUESTS,
    ApprovalRequestsSearchResponse.JSON_PROPERTY_BEFORE_CURSOR,
    ApprovalRequestsSearchResponse.JSON_PROPERTY_BEFORE_URL,
})
@Serdeable
public class ApprovalRequestsSearchResponse {

    public static final String JSON_PROPERTY_AFTER_CURSOR = "after_cursor";
    public static final String JSON_PROPERTY_AFTER_URL = "after_url";
    public static final String JSON_PROPERTY_APPROVAL_REQUESTS = "approval_requests";
    public static final String JSON_PROPERTY_BEFORE_CURSOR = "before_cursor";
    public static final String JSON_PROPERTY_BEFORE_URL = "before_url";

    /**
     * <p>Cursor for the next page of results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterCursor;

    /**
     * <p>URL for the next page of results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_APPROVAL_REQUESTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ApprovalRequestsSearchResponseApprovalRequestsInner> approvalRequests;

    /**
     * <p>Cursor for the previous page of results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeCursor;

    /**
     * <p>URL for the previous page of results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeUrl;

    /**
     * Add an item to the approvalRequests property in a chainable fashion.
     *
     * @return The same instance of ApprovalRequestsSearchResponse for chaining.
     */
    public ApprovalRequestsSearchResponse addApprovalRequestsItem(ApprovalRequestsSearchResponseApprovalRequestsInner approvalRequestsItem) {
        if (approvalRequests == null) {
            approvalRequests = new ArrayList<>();
        }
        approvalRequests.add(approvalRequestsItem);
        return this;
    }

}