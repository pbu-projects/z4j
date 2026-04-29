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

import java.util.List;

/**
 * SearchResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        SearchResponse.JSON_PROPERTY_COUNT,
        SearchResponse.JSON_PROPERTY_FACETS,
        SearchResponse.JSON_PROPERTY_NEXT_PAGE,
        SearchResponse.JSON_PROPERTY_PREVIOUS_PAGE,
        SearchResponse.JSON_PROPERTY_RESULTS,
})
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class SearchResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_FACETS = "facets";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";
    public static final String JSON_PROPERTY_RESULTS = "results";

    /**
     * The number of resources returned by the query corresponding to this page of results in the paginated response
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer count;

    /**
     * The facets corresponding to the search query
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FACETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String facets;

    /**
     * URL to the next page of results
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    /**
     * URL to the previous page of results
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousPage;

    /**
     * May consist of tickets, users, groups, or organizations, as specified by the `result_type` property in each result object
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESULTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SearchResult> results;

}