package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * TicketCountResponseCount
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        TicketCountResponseCount.JSON_PROPERTY_REFRESHED_AT,
        TicketCountResponseCount.JSON_PROPERTY_VALUE,
})
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class TicketCountResponseCount {

    public static final String JSON_PROPERTY_REFRESHED_AT = "refreshed_at";
    public static final String JSON_PROPERTY_VALUE = "value";

    @Nullable
    @JsonProperty(JSON_PROPERTY_REFRESHED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime refreshedAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long value;

}