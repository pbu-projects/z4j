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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.micronaut.serde.annotation.Serdeable;

/**
 * This interface exists to facilitate Jackson's deserialization of implementing classes.
 * <ul>
 * <li> {@link TicketCustomFieldBoolean} </li>
 * <li> {@link TicketCustomFieldFloat} </li>
 * <li> {@link TicketCustomFieldLong} </li>
 * <li> {@link TicketCustomFieldsInner} </li>
 * <li> {@link TicketCustomFieldString} </li>
 * <li> {@link TicketCustomFieldStringArray} </li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TicketCustomFieldBoolean.class, name = "boolean"),
        @JsonSubTypes.Type(value = TicketCustomFieldFloat.class, name = "float"),
        @JsonSubTypes.Type(value = TicketCustomFieldLong.class, name = "long"),
        @JsonSubTypes.Type(value = TicketCustomFieldString.class, name = "string"),
        @JsonSubTypes.Type(value = TicketCustomFieldStringArray.class, name = "string_array")
})
public abstract class TicketCustomFieldsInner {

}
