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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;

/**
 * Ticket requester representation for creating a ticket with an inline user.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.4
 */
@Serdeable
@Introspected
@JsonInclude(Include.NON_NULL)
public record TicketRequester(
        @Nullable @JsonProperty(JSON_PROPERTY_NAME) String name,
        @Nullable @Email @JsonProperty(JSON_PROPERTY_EMAIL) String email,
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE_ID) Long localeId
) {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_LOCALE_ID = "locale_id";

    public TicketRequester(String email) {
        this(null, email, null);
    }

    public TicketRequester(String name, String email) {
        this(name, email, null);
    }
}
