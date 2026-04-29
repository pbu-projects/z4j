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
 * UserSubscriptionsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(UserSubscriptionsResponse.JSON_PROPERTY_USER_SUBSCRIPTIONS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class UserSubscriptionsResponse {

    public static final String JSON_PROPERTY_USER_SUBSCRIPTIONS = "user_subscriptions";

    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_SUBSCRIPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid UserSubscription> userSubscriptions;

    /**
     * Add an item to the userSubscriptions property in a chainable fashion.
     *
     * @return The same instance of UserSubscriptionsResponse for chaining.
     */
    public UserSubscriptionsResponse addUserSubscriptionsItem(UserSubscription userSubscriptionsItem) {
        if (userSubscriptions == null) {
            userSubscriptions = new ArrayList<>();
        }
        userSubscriptions.add(userSubscriptionsItem);
        return this;
    }

}