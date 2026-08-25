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
import lol.pbu.z4j.model.CreateWorkspace201Response;
import lol.pbu.z4j.model.CreateWorkspaceRequest;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.ReorderWorkspacesRequest;
import lol.pbu.z4j.model.WorkspaceResponse;
import static io.micronaut.core.convert.converters.MultiValuesConverterFactory.*;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface WorkspacesClient {

    /**
     * {@summary Create Workspace}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param createWorkspaceRequest (optional)
     *
     * @return <p>Created workspace</p> (status code 201)
     */
    @Post("/api/v2/workspaces")
    Mono<@Valid CreateWorkspace201Response> createWorkspace(
        @Body @Nullable @Valid CreateWorkspaceRequest createWorkspaceRequest
    );

    /**
     * {@summary Delete Workspace}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param workspaceId <p>The id of the workspace</p> (required)
     *
     * @return <p>No Content</p> (status code 204)
     */
    @Delete("/api/v2/workspaces/{workspace_id}")
    Mono<Void> deleteWorkspace(
        @PathVariable("workspace_id") @NotNull Integer workspaceId
    );

    /**
     * {@summary Bulk Delete Workspaces}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ids <p>The ids of the workspaces to delete</p> (required)
     *
     * @return <p>Succesful response</p> (status code 200)
     */
    @Delete("/api/v2/workspaces/destroy_many")
    Mono<@NotNull String> destroyManyWorkspaces(
        @QueryValue("ids") @NotNull @Format(FORMAT_MULTI) List<@NotNull Integer> ids
    );

    /**
     * {@summary List Workspaces}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/workspaces")
    Mono<@Valid WorkspaceResponse> listWorkspaces();

    /**
     * {@summary Reorder Workspaces}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param reorderWorkspacesRequest (optional)
     *
     * @return <p>Succesful response</p> (status code 200)
     */
    @Put("/api/v2/workspaces/reorder")
    Mono<@NotNull String> reorderWorkspaces(
        @Body @Nullable @Valid ReorderWorkspacesRequest reorderWorkspacesRequest
    );

    /**
     * {@summary Show Workspace}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param workspaceId <p>The id of the workspace</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/workspaces/{workspace_id}")
    Mono<@Valid CreateWorkspace201Response> showWorkspace(
        @PathVariable("workspace_id") @NotNull Integer workspaceId
    );

    /**
     * {@summary Update Workspace}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param workspaceId <p>The id of the workspace</p> (required)
     * @param createWorkspaceRequest (optional)
     *
     * @return <p>OK</p> (status code 200)
     */
    @Put("/api/v2/workspaces/{workspace_id}")
    Mono<@Valid CreateWorkspace201Response> updateWorkspace(
        @PathVariable("workspace_id") @NotNull Integer workspaceId,
        @Body @Nullable @Valid CreateWorkspaceRequest createWorkspaceRequest
    );
}