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
 * MacroApplyTicketResponseResultTicketComment
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    MacroApplyTicketResponseResultTicketComment.JSON_PROPERTY_BODY,
    MacroApplyTicketResponseResultTicketComment.JSON_PROPERTY_PUBLIC,
    MacroApplyTicketResponseResultTicketComment.JSON_PROPERTY_SCOPED_BODY,
})
@Serdeable
public class MacroApplyTicketResponseResultTicketComment {

    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_PUBLIC = "public";
    public static final String JSON_PROPERTY_SCOPED_BODY = "scoped_body";

    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _public;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SCOPED_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull List<@NotNull String>> scopedBody;

    /**
     * Add an item to the scopedBody property in a chainable fashion.
     *
     * @return The same instance of MacroApplyTicketResponseResultTicketComment for chaining.
     */
    public MacroApplyTicketResponseResultTicketComment addScopedBodyItem(List<@NotNull String> scopedBodyItem) {
        if (scopedBody == null) {
            scopedBody = new ArrayList<>();
        }
        scopedBody.add(scopedBodyItem);
        return this;
    }

}