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
import lol.pbu.z4j.model.TicketImportInputCommentsInner;
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
 * TicketImportInput
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketImportInput.JSON_PROPERTY_ASSIGNEE_ID,
    TicketImportInput.JSON_PROPERTY_COMMENTS,
    TicketImportInput.JSON_PROPERTY_DESCRIPTION,
    TicketImportInput.JSON_PROPERTY_REQUESTER_ID,
    TicketImportInput.JSON_PROPERTY_SUBJECT,
    TicketImportInput.JSON_PROPERTY_TAGS,
})
@Serdeable
public class TicketImportInput {

    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_COMMENTS = "comments";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_REQUESTER_ID = "requester_id";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_TAGS = "tags";

    /**
     * <p>The agent currently assigned to the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long assigneeId;

    /**
     * <p>The conversation between requesters, collaborators, and agents</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketImportInputCommentsInner> comments;

    /**
     * <p>Read-only first comment on the ticket. When <a href=\"#create-ticket\">creating a ticket</a>, use <code>comment</code> to set the description. See <a href=\"#description-and-first-comment\">Description and first comment</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>The user who requested this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long requesterId;

    /**
     * <p>The value of the subject field for this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    /**
     * <p>The array of tags applied to this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    /**
     * Add an item to the comments property in a chainable fashion.
     *
     * @return The same instance of TicketImportInput for chaining.
     */
    public TicketImportInput addCommentsItem(TicketImportInputCommentsInner commentsItem) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(commentsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of TicketImportInput for chaining.
     */
    public TicketImportInput addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

}