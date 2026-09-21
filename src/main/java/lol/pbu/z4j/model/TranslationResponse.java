package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lol.pbu.z4j.Generated;

@Data
@Serdeable
@Generated
public class TranslationResponse {
    @JsonProperty("translation")
    private Translation translation;
}
