package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;
import lol.pbu.z4j.Generated;

@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Serdeable
@Generated
public class ViewsResponse extends OffsetPaginationResponse {
    @Nullable @JsonProperty("views") @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<View> views;
}
