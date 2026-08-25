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
 * AccountSettingsMessageInactivityObjectDefaultLocalizedMessages
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsMessageInactivityObjectDefaultLocalizedMessages.JSON_PROPERTY_PRE_SOLVED_MESSAGE1,
    AccountSettingsMessageInactivityObjectDefaultLocalizedMessages.JSON_PROPERTY_PRE_SOLVED_MESSAGE2,
    AccountSettingsMessageInactivityObjectDefaultLocalizedMessages.JSON_PROPERTY_SOLVED_MESSAGE,
})
@Serdeable
public class AccountSettingsMessageInactivityObjectDefaultLocalizedMessages {

    public static final String JSON_PROPERTY_PRE_SOLVED_MESSAGE1 = "pre_solved_message_1";
    public static final String JSON_PROPERTY_PRE_SOLVED_MESSAGE2 = "pre_solved_message_2";
    public static final String JSON_PROPERTY_SOLVED_MESSAGE = "solved_message";

    /**
     * <p>The first pre-solved message</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRE_SOLVED_MESSAGE1)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String preSolvedMessage1;

    /**
     * <p>The second pre-solved message</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRE_SOLVED_MESSAGE2)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String preSolvedMessage2;

    /**
     * <p>The solved message</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOLVED_MESSAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String solvedMessage;

}