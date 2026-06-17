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

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

/**
 * TicketCustomField
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@Introspected
public sealed interface TicketCustomField
    permits TicketCustomField.Text,
            TicketCustomField.Numeric,
            TicketCustomField.Decimal,
            TicketCustomField.Checkbox,
            TicketCustomField.TagList {

    Long id();
    Object value();

    @Serdeable record Text(Long id, String value) implements TicketCustomField {}
    @Serdeable record Numeric(Long id, Long value) implements TicketCustomField {}
    @Serdeable record Decimal(Long id, Float value) implements TicketCustomField {}
    @Serdeable record Checkbox(Long id, Boolean value) implements TicketCustomField {}
    @Serdeable record TagList(Long id, List<String> value) implements TicketCustomField {}
}
