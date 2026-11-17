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

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

/**
 * Typed value for a {@link ViewCondition}.
 *
 * <p>Zendesk view conditions can hold a single string, a list of strings, a true/false flag,
 * or a whole number. Use the {@code of(...)} helpers to build a value, or match on the
 * variants with a {@code switch} expression.
 *
 * @author Jonathan-Zollinger
 * @since 0.4.3
 */
@Serdeable
public sealed interface ViewConditionValue
        permits ViewConditionValue.Text,
                ViewConditionValue.TextList,
                ViewConditionValue.Flag,
                ViewConditionValue.Numeric {

    /**
     * A single text value, such as {@code "open"} or {@code "24000932"}.
     */
    @Serdeable
    record Text(String value) implements ViewConditionValue {}

    /**
     * A list of text values, such as ticket tags.
     */
    @Serdeable
    record TextList(List<String> values) implements ViewConditionValue {}

    /**
     * A true or false flag value.
     */
    @Serdeable
    record Flag(Boolean value) implements ViewConditionValue {}

    /**
     * A whole number value, such as a priority number or ticket ID.
     */
    @Serdeable
    record Numeric(Long value) implements ViewConditionValue {}

    /**
     * Wraps a text value.
     */
    static ViewConditionValue of(String value) {
        return new Text(value);
    }

    /**
     * Wraps a list of text values.
     */
    static ViewConditionValue of(List<String> values) {
        return new TextList(values);
    }

    /**
     * Wraps a true or false flag value.
     */
    static ViewConditionValue of(boolean value) {
        return new Flag(value);
    }

    /**
     * Wraps a whole number value.
     */
    static ViewConditionValue of(long value) {
        return new Numeric(value);
    }
}
