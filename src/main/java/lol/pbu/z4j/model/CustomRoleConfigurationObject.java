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
package lol.pbu.z4j.model;

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * <p>Configuration settings for the role. See <a href=\"#configuration\">Configuration</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomRoleConfigurationObject.JSON_PROPERTY_ASSIGN_TICKETS_TO_ANY_GROUP,
    CustomRoleConfigurationObject.JSON_PROPERTY_CHAT_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_END_USER_LIST_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_END_USER_PROFILE_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_EXPLORE_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_EXPORT_VIEWS,
    CustomRoleConfigurationObject.JSON_PROPERTY_FORUM_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_FORUM_ACCESS_RESTRICTED_CONTENT,
    CustomRoleConfigurationObject.JSON_PROPERTY_GROUP_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_LIGHT_AGENT,
    CustomRoleConfigurationObject.JSON_PROPERTY_MACRO_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_BUSINESS_RULES,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_CONTEXTUAL_WORKSPACES,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_DYNAMIC_CONTENT,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_EXTENSIONS_AND_CHANNELS,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_FACEBOOK,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_ORGANIZATION_FIELDS,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_TICKET_FIELDS,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_TICKET_FORMS,
    CustomRoleConfigurationObject.JSON_PROPERTY_MANAGE_USER_FIELDS,
    CustomRoleConfigurationObject.JSON_PROPERTY_MODERATE_FORUMS,
    CustomRoleConfigurationObject.JSON_PROPERTY_ORGANIZATION_EDITING,
    CustomRoleConfigurationObject.JSON_PROPERTY_ORGANIZATION_NOTES_EDITING,
    CustomRoleConfigurationObject.JSON_PROPERTY_REPORT_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_SIDE_CONVERSATION_CREATE,
    CustomRoleConfigurationObject.JSON_PROPERTY_TICKET_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_TICKET_COMMENT_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_TICKET_DELETION,
    CustomRoleConfigurationObject.JSON_PROPERTY_TICKET_EDITING,
    CustomRoleConfigurationObject.JSON_PROPERTY_TICKET_MERGE,
    CustomRoleConfigurationObject.JSON_PROPERTY_TICKET_TAG_EDITING,
    CustomRoleConfigurationObject.JSON_PROPERTY_TWITTER_SEARCH_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_USER_VIEW_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_VIEW_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_VIEW_ACCESS_LOGS,
    CustomRoleConfigurationObject.JSON_PROPERTY_VIEW_AUDIT_LOGS,
    CustomRoleConfigurationObject.JSON_PROPERTY_VIEW_DELETED_TICKETS,
    CustomRoleConfigurationObject.JSON_PROPERTY_VIEW_FILTER_TICKETS,
    CustomRoleConfigurationObject.JSON_PROPERTY_VOICE_ACCESS,
    CustomRoleConfigurationObject.JSON_PROPERTY_VOICE_DASHBOARD_ACCESS,
})
@Serdeable
public class CustomRoleConfigurationObject {

    public static final String JSON_PROPERTY_ASSIGN_TICKETS_TO_ANY_GROUP = "assign_tickets_to_any_group";
    public static final String JSON_PROPERTY_CHAT_ACCESS = "chat_access";
    public static final String JSON_PROPERTY_END_USER_LIST_ACCESS = "end_user_list_access";
    public static final String JSON_PROPERTY_END_USER_PROFILE_ACCESS = "end_user_profile_access";
    public static final String JSON_PROPERTY_EXPLORE_ACCESS = "explore_access";
    public static final String JSON_PROPERTY_EXPORT_VIEWS = "export_views";
    public static final String JSON_PROPERTY_FORUM_ACCESS = "forum_access";
    public static final String JSON_PROPERTY_FORUM_ACCESS_RESTRICTED_CONTENT = "forum_access_restricted_content";
    public static final String JSON_PROPERTY_GROUP_ACCESS = "group_access";
    public static final String JSON_PROPERTY_LIGHT_AGENT = "light_agent";
    public static final String JSON_PROPERTY_MACRO_ACCESS = "macro_access";
    public static final String JSON_PROPERTY_MANAGE_BUSINESS_RULES = "manage_business_rules";
    public static final String JSON_PROPERTY_MANAGE_CONTEXTUAL_WORKSPACES = "manage_contextual_workspaces";
    public static final String JSON_PROPERTY_MANAGE_DYNAMIC_CONTENT = "manage_dynamic_content";
    public static final String JSON_PROPERTY_MANAGE_EXTENSIONS_AND_CHANNELS = "manage_extensions_and_channels";
    public static final String JSON_PROPERTY_MANAGE_FACEBOOK = "manage_facebook";
    public static final String JSON_PROPERTY_MANAGE_ORGANIZATION_FIELDS = "manage_organization_fields";
    public static final String JSON_PROPERTY_MANAGE_TICKET_FIELDS = "manage_ticket_fields";
    public static final String JSON_PROPERTY_MANAGE_TICKET_FORMS = "manage_ticket_forms";
    public static final String JSON_PROPERTY_MANAGE_USER_FIELDS = "manage_user_fields";
    public static final String JSON_PROPERTY_MODERATE_FORUMS = "moderate_forums";
    public static final String JSON_PROPERTY_ORGANIZATION_EDITING = "organization_editing";
    public static final String JSON_PROPERTY_ORGANIZATION_NOTES_EDITING = "organization_notes_editing";
    public static final String JSON_PROPERTY_REPORT_ACCESS = "report_access";
    public static final String JSON_PROPERTY_SIDE_CONVERSATION_CREATE = "side_conversation_create";
    public static final String JSON_PROPERTY_TICKET_ACCESS = "ticket_access";
    public static final String JSON_PROPERTY_TICKET_COMMENT_ACCESS = "ticket_comment_access";
    public static final String JSON_PROPERTY_TICKET_DELETION = "ticket_deletion";
    public static final String JSON_PROPERTY_TICKET_EDITING = "ticket_editing";
    public static final String JSON_PROPERTY_TICKET_MERGE = "ticket_merge";
    public static final String JSON_PROPERTY_TICKET_TAG_EDITING = "ticket_tag_editing";
    public static final String JSON_PROPERTY_TWITTER_SEARCH_ACCESS = "twitter_search_access";
    public static final String JSON_PROPERTY_USER_VIEW_ACCESS = "user_view_access";
    public static final String JSON_PROPERTY_VIEW_ACCESS = "view_access";
    public static final String JSON_PROPERTY_VIEW_ACCESS_LOGS = "view_access_logs";
    public static final String JSON_PROPERTY_VIEW_AUDIT_LOGS = "view_audit_logs";
    public static final String JSON_PROPERTY_VIEW_DELETED_TICKETS = "view_deleted_tickets";
    public static final String JSON_PROPERTY_VIEW_FILTER_TICKETS = "view_filter_tickets";
    public static final String JSON_PROPERTY_VOICE_ACCESS = "voice_access";
    public static final String JSON_PROPERTY_VOICE_DASHBOARD_ACCESS = "voice_dashboard_access";

    /**
     * <p>Whether or not the agent can assign tickets to any group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGN_TICKETS_TO_ANY_GROUP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean assignTicketsToAnyGroup;

    /**
     * <p>Whether or not the agent has access to Chat</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CHAT_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean chatAccess;

    /**
     * <p>Whether or not the agent can view lists of user profiles. Allowed values: \"full\", \"none\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_LIST_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserListAccess;

    /**
     * <p>What the agent can do with end-user profiles. Allowed values: \"edit\", \"edit-within-org\", \"full\", \"readonly\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_PROFILE_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserProfileAccess;

    /**
     * <p>Allowed values: \"edit\", \"full\", \"none\", \"readonly\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPLORE_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String exploreAccess;

    /**
     * <p>Whether or not the agent can export views</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPORT_VIEWS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean exportViews;

    /**
     * <p>The kind of access the agent has to Guide. Allowed values: \"edit-topics\", \"full\", \"readonly\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FORUM_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String forumAccess;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FORUM_ACCESS_RESTRICTED_CONTENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean forumAccessRestrictedContent;

    /**
     * <p>Whether or not the agent can add or modify groups</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean groupAccess;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LIGHT_AGENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean lightAgent;

    /**
     * <p>What the agent can do with macros. Allowed values: \"full\", \"manage-group\", \"manage-personal\", \"readonly\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String macroAccess;

    /**
     * <p>Whether or not the agent can manage business rules</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_BUSINESS_RULES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageBusinessRules;

    /**
     * <p>Whether or not the agent can view, add, and edit contextual workspaces</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_CONTEXTUAL_WORKSPACES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageContextualWorkspaces;

    /**
     * <p>Whether or not the agent can access dynamic content</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_DYNAMIC_CONTENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageDynamicContent;

    /**
     * <p>Whether or not the agent can manage channels and extensions</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_EXTENSIONS_AND_CHANNELS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageExtensionsAndChannels;

    /**
     * <p>Whether or not the agent can manage Facebook pages</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_FACEBOOK)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageFacebook;

    /**
     * <p>Whether or not the agent can create and manage organization fields</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_ORGANIZATION_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageOrganizationFields;

    /**
     * <p>Whether or not the agent can create and manage ticket fields</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_TICKET_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageTicketFields;

    /**
     * <p>Whether or not the agent can create and manage ticket forms</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_TICKET_FORMS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageTicketForms;

    /**
     * <p>Whether or not the agent can create and manage user fields</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGE_USER_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean manageUserFields;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MODERATE_FORUMS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean moderateForums;

    /**
     * <p>Whether or not the agent can add or modify organizations</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_EDITING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean organizationEditing;

    /**
     * <p>Whether or not the agent can add or modify organization notes</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_NOTES_EDITING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean organizationNotesEditing;

    /**
     * <p>What the agent can do with reports. Allowed values: \"full\", \"none\", \"readonly\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REPORT_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String reportAccess;

    /**
     * <p>Whether or not the agent can contribute to side conversations</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SIDE_CONVERSATION_CREATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sideConversationCreate;

    /**
     * <p>What kind of tickets the agent can access. Allowed values: \"all\", \"assigned-only\", \"within-groups\", \"within-groups-and-public-groups\", \"within-organization\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ticketAccess;

    /**
     * <p>What type of comments the agent can make. Allowed values: \"public\", \"none\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_COMMENT_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ticketCommentAccess;

    /**
     * <p>Whether or not the agent can delete tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_DELETION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ticketDeletion;

    /**
     * <p>Whether or not the agent can edit ticket properties</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_EDITING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ticketEditing;

    /**
     * <p>Whether or not the agent can merge tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_MERGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ticketMerge;

    /**
     * <p>Whether or not the agent can edit ticket tags</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_TAG_EDITING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ticketTagEditing;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TWITTER_SEARCH_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean twitterSearchAccess;

    /**
     * <p>What the agent can do with customer lists. Allowed values: \"full\", \"manage-group\", \"manage-personal\", \"none\", \"readonly\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_VIEW_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String userViewAccess;

    /**
     * <p>What the agent can do with views. Allowed values: \"full\", \"manage-group\", \"manage-personal\", \"playonly\", \"readonly\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIEW_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String viewAccess;

    /**
     * <p>Whether or not the agent can view access logs.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIEW_ACCESS_LOGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean viewAccessLogs;

    /**
     * <p>Whether or not the agent can view audit logs.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIEW_AUDIT_LOGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean viewAuditLogs;

    /**
     * <p>Whether or not the agent can view deleted tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIEW_DELETED_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean viewDeletedTickets;

    /**
     * <p>Whether or not the agent can view and apply filters to tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIEW_FILTER_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean viewFilterTickets;

    /**
     * <p>Whether or not the agent can answer and place calls to end users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VOICE_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean voiceAccess;

    /**
     * <p>Whether or not the agent can view details about calls on the Talk dashboard</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VOICE_DASHBOARD_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean voiceDashboardAccess;

}