package lol.pbu.z4j.model;

import io.micronaut.core.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>{@summary A builder for Zendesk Search Queries}</h1>
 * <p>Dynamically construct Zendesk search strings to avoid manual string concatenation.</p>
 * <p>See <a href="https://support.zendesk.com/hc/en-us/articles/203663226-Zendesk-Support-search-reference">Zendesk Support search reference</a> for details on the query syntax.</p>
 */
public class SearchQueryBuilder {
    private final List<String> parts = new ArrayList<>();

    public static SearchQueryBuilder builder() {
        return new SearchQueryBuilder();
    }

    /**
     * Set the type of entity to search for.
     */
    public SearchQueryBuilder type(@NonNull String type) {
        parts.add("type:" + type);
        return this;
    }

    /**
     * Include a specific keyword.
     */
    public SearchQueryBuilder keyword(@NonNull String keyword) {
        parts.add(keyword.contains(" ") ? "\"" + keyword + "\"" : keyword);
        return this;
    }
    
    /**
     * Exclude a specific keyword.
     */
    public SearchQueryBuilder excludeKeyword(@NonNull String keyword) {
        parts.add("-" + (keyword.contains(" ") ? "\"" + keyword + "\"" : keyword));
        return this;
    }

    /**
     * Filter by status less than the given status.
     */
    public SearchQueryBuilder statusLessThan(@NonNull String status) {
        parts.add("status<" + status);
        return this;
    }
    
    /**
     * Filter by an exact status.
     */
    public SearchQueryBuilder status(@NonNull String status) {
        parts.add("status:" + status);
        return this;
    }

    /**
     * Add a tag filter.
     */
    public SearchQueryBuilder tags(@NonNull String tag) {
        parts.add("tags:" + tag);
        return this;
    }

    /**
     * Filter by a custom field.
     */
    public SearchQueryBuilder customField(long fieldId, @NonNull String value) {
        parts.add("custom_field_" + fieldId + ":" + value);
        return this;
    }

    /**
     * Filter by assignee ID.
     */
    public SearchQueryBuilder assignee(long assigneeId) {
        parts.add("assignee:" + assigneeId);
        return this;
    }

    /**
     * Filter by requester ID.
     */
    public SearchQueryBuilder requester(long requesterId) {
        parts.add("requester:" + requesterId);
        return this;
    }
    
    /**
     * Filter by creation date greater than or equal to the given date.
     */
    public SearchQueryBuilder createdAfter(@NonNull LocalDate date) {
        parts.add("created>=" + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return this;
    }

    /**
     * Build the query string.
     */
    public String build() {
        return String.join(" ", parts);
    }
}
