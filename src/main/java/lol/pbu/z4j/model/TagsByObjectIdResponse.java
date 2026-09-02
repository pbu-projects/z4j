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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * TagsByObjectIdResponse
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder(TagsByObjectIdResponse.JSON_PROPERTY_TAGS)
@Serdeable
public class TagsByObjectIdResponse {

    public static final String JSON_PROPERTY_TAGS = "tags";

    /**
     * <p>An array of strings</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TAGS)
    private List<@NotNull String> tags = new ArrayList<>();

    public TagsByObjectIdResponse(List<@NotNull String> tags) {
        this.tags = tags;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of TagsByObjectIdResponse for chaining.
     */
    public TagsByObjectIdResponse addTagsItem(String tagsItem) {
        tags.add(tagsItem);
        return this;
    }

}