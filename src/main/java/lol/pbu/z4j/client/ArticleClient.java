package lol.pbu.z4j.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lol.pbu.z4j.model.ArticlesResponse;
import lol.pbu.z4j.model.ListArticlesSortByParameter;
import lol.pbu.z4j.model.ListArticlesSortOrderParameter;
import reactor.core.publisher.Mono;

@Retryable
@Client("zendesk")
public interface ArticleClient {

    /**
     * {@summary List Articles}
     *
     * @param locale     The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param sortBy     Sorts the articles by one of the accepted values (optional)
     * @param sortOrder  Selects the order of the results. (optional)
     * @param startTime  You can use the incremental article endpoint to list all the articles that were updated since a certain date and time. This time is given as a <a href='https://www.epochconverter.com/'>Unix epoch timeStamp</a>  (optional)
     * @param labelNames Only articles that have all the labels are returned. Multiple labels can be passed in a comma delimited string (optional)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles")
    Mono<@Valid ArticlesResponse> listArticles(
            @PathVariable("locale") @NotNull String locale,
            @QueryValue("sort_by") @Nullable ListArticlesSortByParameter sortBy,
            @QueryValue("sort_order") @Nullable ListArticlesSortOrderParameter sortOrder,
            @QueryValue("start_time") @Nullable Long startTime,
            @QueryValue("label_names") @Nullable @Pattern(regexp = "^\\\\S+$") String labelNames
    );
}