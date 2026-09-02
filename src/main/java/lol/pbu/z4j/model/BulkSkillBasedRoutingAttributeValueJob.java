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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * BulkSkillBasedRoutingAttributeValueJob
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    BulkSkillBasedRoutingAttributeValueJob.JSON_PROPERTY_ACTION,
    BulkSkillBasedRoutingAttributeValueJob.JSON_PROPERTY_ATTRIBUTES,
    BulkSkillBasedRoutingAttributeValueJob.JSON_PROPERTY_ITEMS,
})
@Serdeable
public class BulkSkillBasedRoutingAttributeValueJob {

    public static final String JSON_PROPERTY_ACTION = "action";
    public static final String JSON_PROPERTY_ATTRIBUTES = "attributes";
    public static final String JSON_PROPERTY_ITEMS = "items";

    /**
     * <p>The action to perform on the attribute values. One of the following: \"upsert\", \"update\", \"delete\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String action;

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_ATTRIBUTES)
    private BulkSkillBasedRoutingAttributeValueJobAttributes attributes;

    /**
     * <p>The list of agent ids</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ITEMS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> items = new ArrayList<>();

    public BulkSkillBasedRoutingAttributeValueJob(BulkSkillBasedRoutingAttributeValueJobAttributes attributes) {
        this.attributes = attributes;
    }

}