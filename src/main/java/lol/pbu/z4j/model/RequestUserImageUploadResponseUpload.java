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

import java.util.HashMap;
import java.util.Map;

/**
 * RequestUserImageUploadResponseUpload
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        RequestUserImageUploadResponseUpload.JSON_PROPERTY_HEADERS,
        RequestUserImageUploadResponseUpload.JSON_PROPERTY_TOKEN,
        RequestUserImageUploadResponseUpload.JSON_PROPERTY_URL,
})
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class RequestUserImageUploadResponseUpload {

    public static final String JSON_PROPERTY_HEADERS = "headers";
    public static final String JSON_PROPERTY_TOKEN = "token";
    public static final String JSON_PROPERTY_URL = "url";

    @Nullable
    @JsonProperty(JSON_PROPERTY_HEADERS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> headers;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TOKEN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String token;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * Set the value for the key for the headers map property in a chainable fashion.
     *
     * @return The same instance of RequestUserImageUploadResponseUpload for chaining.
     */
    public RequestUserImageUploadResponseUpload putHeadersItem(String key, Object headersItem) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, headersItem);
        return this;
    }

}