package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lol.pbu.z4j.Generated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Serdeable
@Generated
public class TranslationUpdateRequest {
    @JsonProperty("translation")
    private Translation translation;
}
