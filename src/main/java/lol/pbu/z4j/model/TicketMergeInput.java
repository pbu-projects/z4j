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
 * TicketMergeInput
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TicketMergeInput.JSON_PROPERTY_IDS,
    TicketMergeInput.JSON_PROPERTY_SOURCE_COMMENT,
    TicketMergeInput.JSON_PROPERTY_SOURCE_COMMENT_IS_PUBLIC,
    TicketMergeInput.JSON_PROPERTY_TARGET_COMMENT,
    TicketMergeInput.JSON_PROPERTY_TARGET_COMMENT_IS_PUBLIC,
})
@Serdeable
public class TicketMergeInput {

    public static final String JSON_PROPERTY_IDS = "ids";
    public static final String JSON_PROPERTY_SOURCE_COMMENT = "source_comment";
    public static final String JSON_PROPERTY_SOURCE_COMMENT_IS_PUBLIC = "source_comment_is_public";
    public static final String JSON_PROPERTY_TARGET_COMMENT = "target_comment";
    public static final String JSON_PROPERTY_TARGET_COMMENT_IS_PUBLIC = "target_comment_is_public";

    /**
     * <p>Ids of tickets to merge into the target ticket</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_IDS)
    private List<@NotNull Long> ids = new ArrayList<>();

    /**
     * <p>Private comment to add to the source ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceComment;

    /**
     * <p>Whether comment in source tickets are public or private</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_COMMENT_IS_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sourceCommentIsPublic;

    /**
     * <p>Private comment to add to the target ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String targetComment;

    /**
     * <p>Whether comment in target ticket is public or private</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET_COMMENT_IS_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean targetCommentIsPublic;

    public TicketMergeInput(List<@NotNull Long> ids) {
        this.ids = ids;
    }

    /**
     * Add an item to the ids property in a chainable fashion.
     *
     * @return The same instance of TicketMergeInput for chaining.
     */
    public TicketMergeInput addIdsItem(Long idsItem) {
        ids.add(idsItem);
        return this;
    }

}