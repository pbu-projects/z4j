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
 * CategoriesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(CategoriesResponse.JSON_PROPERTY_CATEGORIES)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class CategoriesResponse {

    public static final String JSON_PROPERTY_CATEGORIES = "categories";

    @Nullable
    @JsonProperty(JSON_PROPERTY_CATEGORIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Category> categories;

    /**
     * Add an item to the categories property in a chainable fashion.
     *
     * @return The same instance of CategoriesResponse for chaining.
     */
    public CategoriesResponse addCategoriesItem(Category categoriesItem) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(categoriesItem);
        return this;
    }

}