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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * ComplianceDeletionStatusesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(ComplianceDeletionStatusesResponse.JSON_PROPERTY_COMPLIANCE_DELETION_STATUSES)
@Serdeable
public class ComplianceDeletionStatusesResponse {

    public static final String JSON_PROPERTY_COMPLIANCE_DELETION_STATUSES = "compliance_deletion_statuses";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COMPLIANCE_DELETION_STATUSES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ComplianceDeletionStatusObject> complianceDeletionStatuses;

    /**
     * Add an item to the complianceDeletionStatuses property in a chainable fashion.
     *
     * @return The same instance of ComplianceDeletionStatusesResponse for chaining.
     */
    public ComplianceDeletionStatusesResponse addComplianceDeletionStatusesItem(ComplianceDeletionStatusObject complianceDeletionStatusesItem) {
        if (complianceDeletionStatuses == null) {
            complianceDeletionStatuses = new ArrayList<>();
        }
        complianceDeletionStatuses.add(complianceDeletionStatusesItem);
        return this;
    }

}