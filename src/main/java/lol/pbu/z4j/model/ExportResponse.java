package lol.pbu.z4j.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
@Serdeable
public class ExportResponse<T> {
    private List<T> results;
    private String facets;
    private Meta meta;
    private Links links;
}
