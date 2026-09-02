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

/**
 * OrganizationMergeListResponseOrganizationMergesInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    OrganizationMergeListResponseOrganizationMergesInner.JSON_PROPERTY_ID,
    OrganizationMergeListResponseOrganizationMergesInner.JSON_PROPERTY_LOSER_ID,
    OrganizationMergeListResponseOrganizationMergesInner.JSON_PROPERTY_STATUS,
    OrganizationMergeListResponseOrganizationMergesInner.JSON_PROPERTY_URL,
    OrganizationMergeListResponseOrganizationMergesInner.JSON_PROPERTY_WINNER_ID,
})
@Serdeable
public class OrganizationMergeListResponseOrganizationMergesInner {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LOSER_ID = "loser_id";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_WINNER_ID = "winner_id";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOSER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long loserId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private OrganizationMergeListResponseOrganizationMergesInnerStatus status;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty(JSON_PROPERTY_WINNER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long winnerId;

}