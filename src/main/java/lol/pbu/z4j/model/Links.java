package lol.pbu.z4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.net.URL;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class Links {
    private URL prev;
    private URL next;
}
