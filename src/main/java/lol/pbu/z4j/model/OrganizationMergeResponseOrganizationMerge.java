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
import lol.pbu.z4j.model.OrganizationMergeListResponseOrganizationMergesInnerStatus;
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
 * OrganizationMergeResponseOrganizationMerge
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    OrganizationMergeResponseOrganizationMerge.JSON_PROPERTY_ID,
    OrganizationMergeResponseOrganizationMerge.JSON_PROPERTY_LOSER_ID,
    OrganizationMergeResponseOrganizationMerge.JSON_PROPERTY_STATUS,
    OrganizationMergeResponseOrganizationMerge.JSON_PROPERTY_URL,
    OrganizationMergeResponseOrganizationMerge.JSON_PROPERTY_WINNER_ID,
})
@Serdeable
public class OrganizationMergeResponseOrganizationMerge {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LOSER_ID = "loser_id";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_WINNER_ID = "winner_id";

    @NotNull
    @JsonProperty(JSON_PROPERTY_ID)
    private String id;

    @NotNull
    @JsonProperty(JSON_PROPERTY_LOSER_ID)
    private Long loserId;

    @NotNull
    @JsonProperty(JSON_PROPERTY_STATUS)
    private OrganizationMergeListResponseOrganizationMergesInnerStatus status;

    @NotNull
    @JsonProperty(JSON_PROPERTY_URL)
    private String url;

    @NotNull
    @JsonProperty(JSON_PROPERTY_WINNER_ID)
    private Long winnerId;

    public OrganizationMergeResponseOrganizationMerge(String id, Long loserId, OrganizationMergeListResponseOrganizationMergesInnerStatus status, String url, Long winnerId) {
        this.id = id;
        this.loserId = loserId;
        this.status = status;
        this.url = url;
        this.winnerId = winnerId;
    }

}