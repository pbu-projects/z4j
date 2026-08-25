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
 * TicketCreateVoicemailTicketVoiceCommentInput
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_ANSWERED_BY_ID,
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_CALL_DURATION,
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_FROM,
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_LOCATION,
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_RECORDING_URL,
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_STARTED_AT,
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_TO,
    TicketCreateVoicemailTicketVoiceCommentInput.JSON_PROPERTY_TRANSCRIPTION_TEXT,
})
@Serdeable
public class TicketCreateVoicemailTicketVoiceCommentInput {

    public static final String JSON_PROPERTY_ANSWERED_BY_ID = "answered_by_id";
    public static final String JSON_PROPERTY_CALL_DURATION = "call_duration";
    public static final String JSON_PROPERTY_FROM = "from";
    public static final String JSON_PROPERTY_LOCATION = "location";
    public static final String JSON_PROPERTY_RECORDING_URL = "recording_url";
    public static final String JSON_PROPERTY_STARTED_AT = "started_at";
    public static final String JSON_PROPERTY_TO = "to";
    public static final String JSON_PROPERTY_TRANSCRIPTION_TEXT = "transcription_text";

    /**
     * <p>The agent who answered the call</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ANSWERED_BY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer answeredById;

    /**
     * <p>Duration in seconds of the call</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CALL_DURATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer callDuration;

    /**
     * <p>Incoming phone number</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FROM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String from;

    /**
     * <p>Location of the caller (optional)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String location;

    /**
     * <p>Incoming phone number</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RECORDING_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recordingUrl;

    /**
     * <p><a href=\"https://en.wikipedia.org/wiki/ISO_8601\">ISO 8601</a> timestamp of the call starting time</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STARTED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime startedAt;

    /**
     * <p>Dialed phone number</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TO)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String to;

    /**
     * <p>Transcription of the call (optional)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TRANSCRIPTION_TEXT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String transcriptionText;

}