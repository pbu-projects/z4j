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
public class ViewCountResponse {
    @Nullable @JsonProperty("view_count") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ViewCount viewCount;
}
