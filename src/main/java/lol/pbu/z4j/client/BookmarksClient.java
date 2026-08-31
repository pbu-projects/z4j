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

import io.micronaut.http.annotation.*;
import io.micronaut.core.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.core.convert.format.Format;
import lol.pbu.z4j.model.BookmarkCreateRequest;
import lol.pbu.z4j.model.BookmarkResponse;
import lol.pbu.z4j.model.BookmarksResponse;
import reactor.core.publisher.Mono;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface BookmarksClient {

    /**
     * {@summary Create Bookmark}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param bookmarkCreateRequest (optional)
     *
     * @return <p>Successfully created</p> (status code 200)
     *         or <p>Successfully created</p> (status code 201)
     */
    @Post("/api/v2/bookmarks")
    Mono<@Valid BookmarkResponse> createBookmark(
        @Body @Nullable @Valid BookmarkCreateRequest bookmarkCreateRequest
    );

    /**
     * {@summary Delete Bookmark}
     * <h4>Allowed For</h4> <ul> <li>Agents (own bookmarks only)</li> </ul> <p>If the bookmark already exists with a specified ticket id, the response status will be <code>http Status: 200 OK</code>.</p>
     *
     * @param bookmarkId <p>The ID of the bookmark</p> (required)
     *
     * @return <p>No content</p> (status code 204)
     */
    @Delete("/api/v2/bookmarks/{bookmark_id}")
    Mono<Void> deleteBookmark(
        @PathVariable("bookmark_id") @NotNull Long bookmarkId
    );

    /**
     * {@summary List Bookmarks}
     * <p>Archived tickets are not included in the response. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4408887617050\">About archived tickets</a> in Zendesk help.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/bookmarks")
    Mono<@Valid BookmarksResponse> listBookmarks();
}