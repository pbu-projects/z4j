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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * Email CC recipient update entry for a ticket.
 *
 * @param action whether to add or remove the email CC recipient
 * @param userEmail email address of the user to CC
 * @param userId unique ID of the user to CC
 * @param userName display name of the user to CC
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        EmailCC.JSON_PROPERTY_ACTION,
        EmailCC.JSON_PROPERTY_USER_EMAIL,
        EmailCC.JSON_PROPERTY_USER_ID,
        EmailCC.JSON_PROPERTY_USER_NAME,
})
public record EmailCC(
        @Nullable @JsonProperty(JSON_PROPERTY_ACTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) EmailCCAllOfAction action,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_EMAIL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String userEmail,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long userId,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String userName
) implements EmailCCBuilder.With {

    public static final String JSON_PROPERTY_ACTION = "action";
    public static final String JSON_PROPERTY_USER_EMAIL = "user_email";
    public static final String JSON_PROPERTY_USER_ID = "user_id";
    public static final String JSON_PROPERTY_USER_NAME = "user_name";
}
