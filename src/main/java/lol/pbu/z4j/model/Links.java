package lol.pbu.z4j.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.net.URL;
import lol.pbu.z4j.Generated;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
@Serdeable
@Generated
public class Links {
    private URL prev;
    private URL next;
}
