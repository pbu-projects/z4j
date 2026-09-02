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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * HostMappingObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    HostMappingObject.JSON_PROPERTY_CNAME,
    HostMappingObject.JSON_PROPERTY_EXPECTED_CNAMES,
    HostMappingObject.JSON_PROPERTY_IS_VALID,
    HostMappingObject.JSON_PROPERTY_REASON,
})
@Serdeable
public class HostMappingObject {

    public static final String JSON_PROPERTY_CNAME = "cname";
    public static final String JSON_PROPERTY_EXPECTED_CNAMES = "expected_cnames";
    public static final String JSON_PROPERTY_IS_VALID = "is_valid";
    public static final String JSON_PROPERTY_REASON = "reason";

    /**
     * <p>The canonical name record for a host mapping</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CNAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String cname;

    /**
     * <p>Array of expected CNAME records for host mapping(s) of a given brand</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPECTED_CNAMES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> expectedCnames;

    /**
     * <p>Whether a host mapping is valid or not for a given brand</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_VALID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isValid;

    /**
     * <p>Reason why a host mapping is valid or not</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REASON)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String reason;

    /**
     * Add an item to the expectedCnames property in a chainable fashion.
     *
     * @return The same instance of HostMappingObject for chaining.
     */
    public HostMappingObject addExpectedCnamesItem(String expectedCnamesItem) {
        if (expectedCnames == null) {
            expectedCnames = new ArrayList<>();
        }
        expectedCnames.add(expectedCnamesItem);
        return this;
    }

}