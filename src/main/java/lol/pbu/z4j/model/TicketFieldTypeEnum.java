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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;
import lol.pbu.z4j.client.TicketClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h4>All available custom field types.</h4>
 * Create a new Ticket Field with {@link TicketClient#createTicketField} ()}
 * See <a href="https://support.zendesk.com/hc/en-us/articles/4408883152794-Adding-custom-ticket-fields-to-your-tickets-and-forms">Zendesk KB</a> for more info
 *
 * <ul>
 *     <li>{@link #TEXT} - Add a few words</li>
 *     <li>{@link #TEXT_AREA} - Add a few lines of text</li>
 *     <li>{@link #CHECKBOX} - Add a yes or no option</li>
 *     <li>{@link #DATE} - Select a date from a calendar</li>
 *     <li>{@link #INTEGER} - Enter a number with no decimal</li>
 *     <li>{@link #DECIMAL} - Enter a number with a decimal</li>
 *     <li>{@link #REGEXP} - Format regular expressions (Example: dates, URLs)</li>
 *     <li>{@link #PARTIAL_CREDIT_CARD} - Add a payment card number</li>
 *     <li>{@link #MULTI_SELECT} - Choose one or more options in a pre-set list</li>
 *     <li>{@link #TAGGER} - Drop-down: Choose one option in a menu of choices</li>
 *     <li>{@link #LOOKUP} - Link objects (Example: ticket to user, ticket to ticket)</li>
 * </ul>
 *
 *  <p><strong>Note</strong>: Tags can't be re-used across custom ticket fields. For example, if you configure
 *  a tag for a checkbox field, you can't use that tag value for a dropdown (tagger) field option. The use of tags
 *  isn't validated and can prevent editing in the future.</p>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@RequiredArgsConstructor
@Getter(onMethod_ = {@JsonValue})
@Serdeable
public enum TicketFieldTypeEnum {

    /**
     * <h1>Add a few words.</h1> <p>Default custom field type when <code>type</code> is not specified.</p>
     */
    @JsonProperty("text") TEXT("text"),

    /**
     * <h1>Add a few lines of text.</h1> <p>For multi-line text.</p>
     */
    @JsonProperty("textarea") TEXT_AREA("textarea"),

    /**
     * <h1>Add a yes or no option.</h1> <p>To capture a boolean value. Allowed values are true or false.</p>
     * <p>Optionally, you can specify a tag to be added to the ticket when the value is true.</p>
     */
    @JsonProperty("checkbox") CHECKBOX("checkbox"),

    /**
     * <h1>Select a date from a calendar.</h1> <h4>Example: 2021-04-16.</h4>
     */
    @JsonProperty("date") DATE("date"),

    /**
     * <h1>Enter a number with no decimal.</h1> <p>String composed of numbers.</p>
     */
    @JsonProperty("integer") INTEGER("integer"),

    /**
     * <h1>Enter a number with a decimal.</h1> <p>For numbers containing decimals.</p>
     */
    @JsonProperty("decimal") DECIMAL("decimal"),

    /**
     * <h1>Format regular expressions.</h1> <p>Matches the Regex pattern found in the custom field settings.</p> <h4>Example: dates, URLs</h4>
     */
    @JsonProperty("regexp") REGEXP("regexp"),

    /**
     * <h1>Add a payment card number.</h1> <p>A credit card number. Only the last 4 digits are retained.</p>
     */
    @JsonProperty("partial_credit_card") PARTIAL_CREDIT_CARD("partial_credit_card"),

    /**
     * <h1>Select one or more options in a menu of options.</h1>
     * <p>It contains one or more tag values belonging to the field's options. {@see #TAGGER}</p>
     */
    @JsonProperty("multi-select") MULTI_SELECT("multi-select"),

    /**
     * <h1>Single-select dropdown menu options.</h1>
     * <p>It contains one or more tag values belonging to the field's options. {@see #MULTI_SELECT}</p>
     * <h4>Example: ( {"id": 21938362, "value": ["hd_3000", "hd_5555"]})</h4>
     */
    @JsonProperty("tagger") TAGGER("tagger"),

    /**
     * <h1>Link objects.</h1>
     * <p>A field to create a relationship (see <a href="https://developer.zendesk.com//api-reference/ticketing/lookup_relationships/lookup_relationships/">lookup relationships</a>) to
     * another object such as a user, ticket, or organization.</p>
     * <h4>Example: ticket to user, ticket to ticket</h4>
     */
    @JsonProperty("lookup") LOOKUP("lookup");

    public static final Map<String, TicketFieldTypeEnum> VALUE_MAPPING = Map.copyOf(Arrays.stream(values())
            .collect(Collectors.toMap(v -> v.value, Function.identity())));

    private final String value;

    /**
     * Create this enum from a value.
     *
     * @param value The value
     * @return The enum
     */
    @JsonCreator
    public static TicketFieldTypeEnum fromValue(String value) {
        if (!VALUE_MAPPING.containsKey(value)) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
        return VALUE_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
