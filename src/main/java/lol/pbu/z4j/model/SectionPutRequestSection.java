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

/**
 * SectionPutRequestSection
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        SectionPutRequestSection.JSON_PROPERTY_CATEGORY_ID,
        SectionPutRequestSection.JSON_PROPERTY_DESCRIPTION,
        SectionPutRequestSection.JSON_PROPERTY_NAME,
        SectionPutRequestSection.JSON_PROPERTY_PARENT_SECTION_ID,
        SectionPutRequestSection.JSON_PROPERTY_POSITION,
        SectionPutRequestSection.JSON_PROPERTY_SORTING,
        SectionPutRequestSection.JSON_PROPERTY_THEME_TEMPLATE,
})
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class SectionPutRequestSection {

    public static final String JSON_PROPERTY_CATEGORY_ID = "category_id";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PARENT_SECTION_ID = "parent_section_id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_SORTING = "sorting";
    public static final String JSON_PROPERTY_THEME_TEMPLATE = "theme_template";

    /**
     * The id of the category to which this section belongs
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CATEGORY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long categoryId;

    /**
     * The description of the section
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * The name of the section
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * The id of the section to which this section belongs. Only writable for Guide Enterprise customers
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PARENT_SECTION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long parentSectionId;

    /**
     * The position of this section in the section list. Used when sorting is set to ´manual´.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SORTING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SectionPutRequestSectionSorting sorting;

    /**
     * The theme template name used to display this section in Help Center.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_THEME_TEMPLATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String themeTemplate;

}