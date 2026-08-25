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
import lol.pbu.z4j.model.MacroApplyTicketResponseResultTicketComment;
import lol.pbu.z4j.model.MacroApplyTicketResponseResultTicketFields;
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
 * MacroApplyTicketResponseResultTicket
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    MacroApplyTicketResponseResultTicket.JSON_PROPERTY_ASSIGNEE_ID,
    MacroApplyTicketResponseResultTicket.JSON_PROPERTY_COMMENT,
    MacroApplyTicketResponseResultTicket.JSON_PROPERTY_FIELDS,
    MacroApplyTicketResponseResultTicket.JSON_PROPERTY_GROUP_ID,
    MacroApplyTicketResponseResultTicket.JSON_PROPERTY_ID,
    MacroApplyTicketResponseResultTicket.JSON_PROPERTY_URL,
})
@Serdeable
public class MacroApplyTicketResponseResultTicket {

    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_COMMENT = "comment";
    public static final String JSON_PROPERTY_FIELDS = "fields";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_URL = "url";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer assigneeId;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private MacroApplyTicketResponseResultTicketComment comment;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private MacroApplyTicketResponseResultTicketFields fields;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer groupId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}