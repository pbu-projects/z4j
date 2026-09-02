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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * UsersRequest
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder(UsersRequest.JSON_PROPERTY_USERS)
@Serdeable
public class UsersRequest implements UpdateManyUsersRequest {

    public static final String JSON_PROPERTY_USERS = "users";

    @NotNull
    @JsonProperty(JSON_PROPERTY_USERS)
    private List<@Valid UserInput> users = new ArrayList<>();

    public UsersRequest(List<@Valid UserInput> users) {
        this.users = users;
    }

    /**
     * Add an item to the users property in a chainable fashion.
     *
     * @return The same instance of UsersRequest for chaining.
     */
    public UsersRequest addUsersItem(UserInput usersItem) {
        users.add(usersItem);
        return this;
    }

}