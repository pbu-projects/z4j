package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import java.util.List;
import lol.pbu.z4j.Generated;

@Data
@Serdeable
@Generated
public class TranslationsResponse {
    @JsonProperty("translations")
    private List<Translation> translations;
}
