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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lol.pbu.z4j.model.AttachmentObject;
import lol.pbu.z4j.model.ConversationLogObjectAuthor;
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
 * ConversationLogObject
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Getter
@Setter
@JsonPropertyOrder({
    ConversationLogObject.JSON_PROPERTY_ATTACHMENTS,
    ConversationLogObject.JSON_PROPERTY_AUTHOR,
    ConversationLogObject.JSON_PROPERTY_CONTENT,
    ConversationLogObject.JSON_PROPERTY_CREATED_AT,
    ConversationLogObject.JSON_PROPERTY_ID,
    ConversationLogObject.JSON_PROPERTY_METADATA,
    ConversationLogObject.JSON_PROPERTY_REFERENCE,
    ConversationLogObject.JSON_PROPERTY_TYPE,
})
@Serdeable
public class ConversationLogObject extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_ATTACHMENTS = "attachments";
    public static final String JSON_PROPERTY_AUTHOR = "author";
    public static final String JSON_PROPERTY_CONTENT = "content";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_METADATA = "metadata";
    public static final String JSON_PROPERTY_REFERENCE = "reference";
    public static final String JSON_PROPERTY_TYPE = "type";

    /**
     * <p>A collection of attachments (image or file) associated with the event</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AttachmentObject> attachments = new ArrayList<>();

    @NotNull
    @JsonProperty(JSON_PROPERTY_AUTHOR)
    private ConversationLogObjectAuthor author;

    /**
     * <p>Object that describes the content of the message. The inner fields depends on the record type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT)
    @JsonInclude(content = JsonInclude.Include.ALWAYS)
    private Map<String, Object> content;

    /**
     * <p>The timestamp of when this record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Unique record identifier</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Various additional data that further describes this record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METADATA)
    @JsonInclude(content = JsonInclude.Include.ALWAYS)
    private Map<String, Object> metadata;

    /**
     * <p>A Zendesk resource name value that uniquely identifies this record. Example: <code>zen:ticket_event:&lt;id&gt;</code></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REFERENCE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String reference;

    /**
     * <p>The type of record, representing one of the conversational ticket events. Examples: <code>Comment</code> or <code>Messaging::ConversationMessage</code></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    public ConversationLogObject(ConversationLogObjectAuthor author) {
        this.author = author;
    }

}