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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Search
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder(Search.JSON_PROPERTY_RESULTS)
@Serdeable
public class Search {

    public static final String JSON_PROPERTY_RESULTS = "results";

    /**
     * An array with the base articles or community posts
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_RESULTS)
    private List<@Valid SearchResultsInner> results = new ArrayList<>();

    public Search(List<@Valid SearchResultsInner> results) {
        this.results = results;
    }

    /**
     * Add an item to the results property in a chainable fashion.
     *
     * @return The same instance of Search for chaining.
     */
    public Search addResultsItem(SearchResultsInner resultsItem) {
        results.add(resultsItem);
        return this;
    }

}
