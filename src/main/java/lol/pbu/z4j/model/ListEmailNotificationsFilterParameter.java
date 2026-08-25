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
 * ListEmailNotificationsFilterParameter
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ListEmailNotificationsFilterParameter.JSON_PROPERTY_COMMENT_ID,
    ListEmailNotificationsFilterParameter.JSON_PROPERTY_NOTIFICATION_ID,
    ListEmailNotificationsFilterParameter.JSON_PROPERTY_TICKET_ID,
})
@Serdeable
public class ListEmailNotificationsFilterParameter {

    public static final String JSON_PROPERTY_COMMENT_ID = "comment_id";
    public static final String JSON_PROPERTY_NOTIFICATION_ID = "notification_id";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer commentId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTIFICATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer notificationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer ticketId;

}