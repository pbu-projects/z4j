package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * ContentSubscriptionsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(ContentSubscriptionsResponse.JSON_PROPERTY_SUBSCRIPTIONS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class ContentSubscriptionsResponse {

    public static final String JSON_PROPERTY_SUBSCRIPTIONS = "subscriptions";

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBSCRIPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ContentSubscription> subscriptions;

    /**
     * Add an item to the subscriptions property in a chainable fashion.
     *
     * @return The same instance of ContentSubscriptionsResponse for chaining.
     */
    public ContentSubscriptionsResponse addSubscriptionsItem(ContentSubscription subscriptionsItem) {
        if (subscriptions == null) {
            subscriptions = new ArrayList<>();
        }
        subscriptions.add(subscriptionsItem);
        return this;
    }

}