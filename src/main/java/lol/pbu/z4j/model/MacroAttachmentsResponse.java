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
import lol.pbu.z4j.model.MacroAttachmentObject;
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
 * MacroAttachmentsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(MacroAttachmentsResponse.JSON_PROPERTY_MACRO_ATTACHMENTS)
@Serdeable
public class MacroAttachmentsResponse {

    public static final String JSON_PROPERTY_MACRO_ATTACHMENTS = "macro_attachments";

    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid MacroAttachmentObject> macroAttachments;

    /**
     * Add an item to the macroAttachments property in a chainable fashion.
     *
     * @return The same instance of MacroAttachmentsResponse for chaining.
     */
    public MacroAttachmentsResponse addMacroAttachmentsItem(MacroAttachmentObject macroAttachmentsItem) {
        if (macroAttachments == null) {
            macroAttachments = new ArrayList<>();
        }
        macroAttachments.add(macroAttachmentsItem);
        return this;
    }

}