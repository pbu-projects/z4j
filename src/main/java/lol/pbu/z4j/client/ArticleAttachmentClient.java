/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lol.pbu.z4j.client;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.ArticleAttachmentResponse;
import lol.pbu.z4j.model.ArticleAttachmentsResponse;
import lol.pbu.z4j.model.LocaleAbbreviation;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Help Center Article Attachments in Zendesk.</h1>
 * <ul>
 *     <li>List Article Attachments {@link #listArticleAttachments}</li>
 *     <li>List Article Attachments No Locale {@link #listArticleAttachmentsNoLocale}</li>
 *     <li>List Article Block Attachments {@link #listArticleBlockAttachments}</li>
 *     <li>List Article Block Attachments No Locale {@link #listArticleBlockAttachmentsNoLocale}</li>
 *     <li>List Article Inline Attachments {@link #listArticleInlineAttachments}</li>
 *     <li>List Article Inline Attachments No Locale {@link #listArticleInlineAttachmentsNoLocale}</li>
 *     <li>Show Article Attachment {@link #showArticleAttachment}</li>
 *     <li>Create Unassociated Attachment {@link #createUnassociatedAttachment}</li>
 *     <li>Create Article Attachment {@link #createArticleAttachment}</li>
 *     <li>Create Article Attachment No Locale {@link #createArticleAttachmentNoLocale}</li>
 *     <li>Delete Article Attachment {@link #deleteArticleAttachment}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface ArticleAttachmentClient {

    /**
     * <h1>{@summary List Article Attachments}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return Article attachments (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles/{article_id}/attachments")
    Mono<@Valid ArticleAttachmentsResponse> listArticleAttachments(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary List Article Attachments (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Article attachments (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}/attachments")
    Mono<@Valid ArticleAttachmentsResponse> listArticleAttachmentsNoLocale(
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary List Article Block Attachments}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return Article block attachments (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles/{article_id}/attachments/block")
    Mono<@Valid ArticleAttachmentsResponse> listArticleBlockAttachments(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary List Article Block Attachments (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Article block attachments (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}/attachments/block")
    Mono<@Valid ArticleAttachmentsResponse> listArticleBlockAttachmentsNoLocale(
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary List Article Inline Attachments}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return Article inline attachments (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles/{article_id}/attachments/inline")
    Mono<@Valid ArticleAttachmentsResponse> listArticleInlineAttachments(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary List Article Inline Attachments (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Article inline attachments (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}/attachments/inline")
    Mono<@Valid ArticleAttachmentsResponse> listArticleInlineAttachmentsNoLocale(
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary Show Article Attachment}</h1>
     *
     * @param attachmentId The unique ID of the attachment (required)
     * @return Article attachment (status code 200)
     */
    @Get("/api/v2/help_center/articles/attachments/{attachment_id}")
    Mono<@Valid ArticleAttachmentResponse> showArticleAttachment(
            @PathVariable("attachment_id") @NotNull Long attachmentId
    );

    /**
     * <h1>{@summary Create Unassociated Attachment}</h1>
     *
     * @param body The multipart body containing the file and inline flag (required)
     * @return Article attachment (status code 201)
     */
    @Post(value = "/api/v2/help_center/articles/attachments", consumes = MediaType.APPLICATION_JSON, produces = MediaType.MULTIPART_FORM_DATA)
    Mono<@Valid ArticleAttachmentResponse> createUnassociatedAttachment(
            @Body MultipartBody body
    );

    /**
     * <h1>{@summary Create Article Attachment}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param body               The multipart body containing the file and inline flag (required)
     * @return Article attachment (status code 201)
     */
    @Post(value = "/api/v2/help_center/{locale}/articles/{article_id}/attachments", consumes = MediaType.APPLICATION_JSON, produces = MediaType.MULTIPART_FORM_DATA)
    Mono<@Valid ArticleAttachmentResponse> createArticleAttachment(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @Body MultipartBody body
    );

    /**
     * <h1>{@summary Create Article Attachment (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      The multipart body containing the file and inline flag (required)
     * @return Article attachment (status code 201)
     */
    @Post(value = "/api/v2/help_center/articles/{article_id}/attachments", consumes = MediaType.APPLICATION_JSON, produces = MediaType.MULTIPART_FORM_DATA)
    Mono<@Valid ArticleAttachmentResponse> createArticleAttachmentNoLocale(
            @PathVariable("article_id") @NotNull Long articleId,
            @Body MultipartBody body
    );

    /**
     * <h1>{@summary Delete Article Attachment}</h1>
     *
     * @param attachmentId The unique ID of the attachment (required)
     * @return Void (status code 204)
     */
    @Delete("/api/v2/help_center/articles/attachments/{attachment_id}")
    Mono<Void> deleteArticleAttachment(
            @PathVariable("attachment_id") @NotNull Long attachmentId
    );

    /**
     * <h1>{@summary Create Unassociated Attachment convenience method}</h1>
     *
     * @param filename The name of the file (required)
     * @param data     The byte array content of the file (required)
     * @param inline   Whether the attachment is inline (required)
     * @return Article attachment (status code 201)
     */
    default Mono<@Valid ArticleAttachmentResponse> createUnassociatedAttachment(
            String filename,
            byte[] data,
            boolean inline
    ) {
        return createUnassociatedAttachment(
                MultipartBody.builder()
                        .addPart("file", filename, MediaType.APPLICATION_OCTET_STREAM_TYPE, data)
                        .addPart("inline", String.valueOf(inline))
                        .build()
        );
    }

    /**
     * <h1>{@summary Create Article Attachment convenience method}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param filename           The name of the file (required)
     * @param data               The byte array content of the file (required)
     * @param inline             Whether the attachment is inline (required)
     * @return Article attachment (status code 201)
     */
    default Mono<@Valid ArticleAttachmentResponse> createArticleAttachment(
            LocaleAbbreviation localeAbbreviation,
            Long articleId,
            String filename,
            byte[] data,
            boolean inline
    ) {
        return createArticleAttachment(
                localeAbbreviation,
                articleId,
                MultipartBody.builder()
                        .addPart("file", filename, MediaType.APPLICATION_OCTET_STREAM_TYPE, data)
                        .addPart("inline", String.valueOf(inline))
                        .build()
        );
    }

    /**
     * <h1>{@summary Create Article Attachment (Default Locale) convenience method}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param filename  The name of the file (required)
     * @param data      The byte array content of the file (required)
     * @param inline    Whether the attachment is inline (required)
     * @return Article attachment (status code 201)
     */
    default Mono<@Valid ArticleAttachmentResponse> createArticleAttachmentNoLocale(
            Long articleId,
            String filename,
            byte[] data,
            boolean inline
    ) {
        return createArticleAttachmentNoLocale(
                articleId,
                MultipartBody.builder()
                        .addPart("file", filename, MediaType.APPLICATION_OCTET_STREAM_TYPE, data)
                        .addPart("inline", String.valueOf(inline))
                        .build()
        );
    }
}
