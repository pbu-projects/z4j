package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

@Accessors(chain = true)
@Data
@Serdeable
@Generated
public class Translation {
    @Nullable @JsonProperty("id") private Long id;
    @Nullable @JsonProperty("url") private String url;
    @Nullable @JsonProperty("html_url") private String htmlUrl;
    @Nullable @JsonProperty("source_id") private Long sourceId;
    @Nullable @JsonProperty("source_type") private String sourceType;
    @Nullable @JsonProperty("locale") private String locale;
    @Nullable @JsonProperty("title") private String title;
    @Nullable @JsonProperty("body") private String body;
    @Nullable @JsonProperty("outdated") private Boolean outdated;
    @Nullable @JsonProperty("draft") private Boolean draft;
    @Nullable @JsonProperty("created_at") private String createdAt;
    @Nullable @JsonProperty("updated_at") private String updatedAt;
    @Nullable @JsonProperty("updated_by_id") private Long updatedById;
    @Nullable @JsonProperty("created_by_id") private Long createdById;
}
