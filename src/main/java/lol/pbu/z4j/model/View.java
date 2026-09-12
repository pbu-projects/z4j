package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.OffsetDateTime;
import lol.pbu.z4j.Generated;

@Accessors(chain = true)
@Data
@Serdeable
@Generated
public class View {
    @Nullable @JsonProperty("id") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable @JsonProperty("title") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    @Nullable @JsonProperty("active") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    @Nullable @JsonProperty("updated_at") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private OffsetDateTime updatedAt;

    @Nullable @JsonProperty("created_at") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private OffsetDateTime createdAt;

    @Nullable @JsonProperty("default") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isDefault;

    @Nullable @JsonProperty("position") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    @Nullable @JsonProperty("description") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    @Nullable @JsonProperty("execution") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object execution;

    @Nullable @JsonProperty("conditions") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object conditions;

    @Nullable @JsonProperty("restriction") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object restriction;

    @Nullable @JsonProperty("raw_title") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;
}
