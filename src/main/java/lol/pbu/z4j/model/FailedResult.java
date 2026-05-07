package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Serdeable
public class FailedResult extends JobStatus {
    private String action;
    private String details;
    private String error;
    @JsonProperty("id")
    private Integer failedResultID;
    private Boolean success;
}
