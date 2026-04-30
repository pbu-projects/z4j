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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * ArticleRequestArticle
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
        ArticleRequestArticle.JSON_PROPERTY_LOCALE,
        ArticleRequestArticle.JSON_PROPERTY_PERMISSION_GROUP_ID,
        ArticleRequestArticle.JSON_PROPERTY_TITLE,
        ArticleRequestArticle.JSON_PROPERTY_USER_SEGMENT_ID,
        ArticleRequestArticle.JSON_PROPERTY_BODY,
})
@Serdeable
public class ArticleRequestArticle {

    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_PERMISSION_GROUP_ID = "permission_group_id";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_USER_SEGMENT_ID = "user_segment_id";
    public static final String JSON_PROPERTY_BODY = "body";

    @NotNull
    @JsonProperty(JSON_PROPERTY_LOCALE)
    private String locale;

    @NotNull
    @JsonProperty(JSON_PROPERTY_PERMISSION_GROUP_ID)
    private Long permissionGroupId = 0L;

    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    @NotNull
    @JsonProperty(JSON_PROPERTY_USER_SEGMENT_ID)
    private Long userSegmentId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    public ArticleRequestArticle(String locale, Long permissionGroupId, String title, Long userSegmentId) {
        this.locale = locale;
        this.permissionGroupId = permissionGroupId;
        this.title = title;
        this.userSegmentId = userSegmentId;
    }

}
