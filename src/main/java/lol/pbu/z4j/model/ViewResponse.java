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
public class ViewResponse {
    @Nullable @JsonProperty("view") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private View view;
}
