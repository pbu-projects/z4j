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
 * SatisfactionRatingObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    SatisfactionRatingObject.JSON_PROPERTY_ASSIGNEE_ID,
    SatisfactionRatingObject.JSON_PROPERTY_GROUP_ID,
    SatisfactionRatingObject.JSON_PROPERTY_REQUESTER_ID,
    SatisfactionRatingObject.JSON_PROPERTY_SCORE,
    SatisfactionRatingObject.JSON_PROPERTY_TICKET_ID,
    SatisfactionRatingObject.JSON_PROPERTY_COMMENT,
    SatisfactionRatingObject.JSON_PROPERTY_CREATED_AT,
    SatisfactionRatingObject.JSON_PROPERTY_ID,
    SatisfactionRatingObject.JSON_PROPERTY_REASON,
    SatisfactionRatingObject.JSON_PROPERTY_REASON_CODE,
    SatisfactionRatingObject.JSON_PROPERTY_REASON_ID,
    SatisfactionRatingObject.JSON_PROPERTY_UPDATED_AT,
    SatisfactionRatingObject.JSON_PROPERTY_URL,
})
@Serdeable
public class SatisfactionRatingObject {

    public static final String JSON_PROPERTY_ASSIGNEE_ID = "assignee_id";
    public static final String JSON_PROPERTY_GROUP_ID = "group_id";
    public static final String JSON_PROPERTY_REQUESTER_ID = "requester_id";
    public static final String JSON_PROPERTY_SCORE = "score";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_COMMENT = "comment";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_REASON = "reason";
    public static final String JSON_PROPERTY_REASON_CODE = "reason_code";
    public static final String JSON_PROPERTY_REASON_ID = "reason_id";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The id of agent assigned to at the time of rating</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer assigneeId;

    /**
     * <p>The id of group assigned to at the time of rating</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer groupId;

    /**
     * <p>The id of ticket requester submitting the rating</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer requesterId;

    /**
     * <p>The rating \"offered\", \"unoffered\", \"good\" or \"bad\". For POST requests, only \"good\" or \"bad\" are valid</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_SCORE)
    private String score;

    /**
     * <p>The id of ticket being rated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer ticketId;

    /**
     * <p>The comment received with this rating, if available</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String comment;

    /**
     * <p>The time the satisfaction rating got created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The reason for a bad rating given by the requester in a follow-up question. Satisfaction reasons must be <a href=\"https://support.zendesk.com/hc/en-us/articles/223152967\">enabled</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REASON)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String reason;

    /**
     * <p>The default reasons the user can select from a list menu for giving a negative rating. See <a href=\"/api-reference/ticketing/ticket-management/satisfaction_reasons/#reason-codes\">Reason codes</a> in the Satisfaction Reasons API. Can only be set on ratings with a <code>score</code> of \"bad\". Responses don't include this property</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REASON_CODE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer reasonCode;

    /**
     * <p>id for the reason the user gave a negative rating. Can only be set on ratings with a <code>score</code> of \"bad\". To get a descriptive value for the id, use the <a href=\"/api-reference/ticketing/ticket-management/satisfaction_reasons/#show-reason-for-satisfaction-rating\">Show Reason for Satisfaction Rating</a> endpoint</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REASON_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer reasonId;

    /**
     * <p>The time the satisfaction rating got updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this rating</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public SatisfactionRatingObject(String score) {
        this.score = score;
    }

}