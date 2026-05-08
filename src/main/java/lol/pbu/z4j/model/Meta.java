package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@Serdeable
public class Meta {
    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("after_cursor")
    private String afterCursor;
    @JsonProperty("before_cursor")
    private String beforeCursor;
}
