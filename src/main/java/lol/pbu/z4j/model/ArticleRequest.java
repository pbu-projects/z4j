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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * ArticleRequest
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
        ArticleRequest.JSON_PROPERTY_ARTICLE,
        ArticleRequest.JSON_PROPERTY_NOTIFY_SUBSCRIBERS,
})
@Serdeable
public class ArticleRequest {

    public static final String JSON_PROPERTY_ARTICLE = "article";
    public static final String JSON_PROPERTY_NOTIFY_SUBSCRIBERS = "notify_subscribers";

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_ARTICLE)
    private ArticleRequestArticle article;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTIFY_SUBSCRIBERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean notifySubscribers;

    public ArticleRequest(ArticleRequestArticle article) {
        this.article = article;
    }

}
