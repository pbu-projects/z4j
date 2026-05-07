package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.context.annotation.Any;

public class Meta {
    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("after_cursor")
    private String afterCursor;
    @JsonProperty("before_cursor")
    private Any beforeCursor;
}
