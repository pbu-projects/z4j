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
import java.util.Map;
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
 * <p>The value of the chat event object</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketChatCommentRedactionResponseChatEventValue.JSON_PROPERTY_CHAT_ID,
    TicketChatCommentRedactionResponseChatEventValue.JSON_PROPERTY_HISTORY,
    TicketChatCommentRedactionResponseChatEventValue.JSON_PROPERTY_VISITOR_ID,
})
@Serdeable
public class TicketChatCommentRedactionResponseChatEventValue {

    public static final String JSON_PROPERTY_CHAT_ID = "chat_id";
    public static final String JSON_PROPERTY_HISTORY = "history";
    public static final String JSON_PROPERTY_VISITOR_ID = "visitor_id";

    /**
     * <p>Id of the chat session</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CHAT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String chatId;

    /**
     * <p>Chat events within the chat session</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HISTORY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> history;

    /**
     * <p>Id assigned to the visitor</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VISITOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String visitorId;

    /**
     * Add an item to the history property in a chainable fashion.
     *
     * @return The same instance of TicketChatCommentRedactionResponseChatEventValue for chaining.
     */
    public TicketChatCommentRedactionResponseChatEventValue addHistoryItem(Map<String, Object> historyItem) {
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(historyItem);
        return this;
    }

}