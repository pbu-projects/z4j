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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

/**
 * GuideMediaResponse
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        GuideMediaResponse.JSON_PROPERTY_GUIDE_MEDIA,
        GuideMediaResponse.JSON_PROPERTY_MEDIA
})
@Serdeable
@Generated
public class GuideMediaResponse {

    public static final String JSON_PROPERTY_GUIDE_MEDIA = "guide_media";
    public static final String JSON_PROPERTY_MEDIA = "media";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_GUIDE_MEDIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private GuideMedia guideMedia;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_MEDIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private GuideMedia media;

    public GuideMediaResponse(GuideMedia guideMedia) {
        this.guideMedia = guideMedia;
        this.media = guideMedia;
    }

    public GuideMedia getGuideMedia() {
        return guideMedia != null ? guideMedia : media;
    }

    public GuideMediaResponse setMedia(GuideMedia media) {
        this.media = media;
        if (this.guideMedia == null) {
            this.guideMedia = media;
        }
        return this;
    }
}
