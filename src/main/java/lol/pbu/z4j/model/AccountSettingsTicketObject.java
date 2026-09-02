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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>Ticket settings. See <a href=\"#tickets\">Tickets</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsTicketObject.JSON_PROPERTY_ACCEPTED_NEW_COLLABORATION_TOS,
    AccountSettingsTicketObject.JSON_PROPERTY_AGENT_COLLISION,
    AccountSettingsTicketObject.JSON_PROPERTY_AGENT_INVITATION_ENABLED,
    AccountSettingsTicketObject.JSON_PROPERTY_AGENT_TICKET_DELETION,
    AccountSettingsTicketObject.JSON_PROPERTY_ALLOW_GROUP_RESET,
    AccountSettingsTicketObject.JSON_PROPERTY_ASSIGN_DEFAULT_ORGANIZATION,
    AccountSettingsTicketObject.JSON_PROPERTY_ASSIGN_TICKETS_UPON_SOLVE,
    AccountSettingsTicketObject.JSON_PROPERTY_AUTO_TRANSLATION_ENABLED,
    AccountSettingsTicketObject.JSON_PROPERTY_AUTO_UPDATED_CCS_FOLLOWERS_RULES,
    AccountSettingsTicketObject.JSON_PROPERTY_CHAT_SLA_ENABLEMENT,
    AccountSettingsTicketObject.JSON_PROPERTY_COLLABORATION,
    AccountSettingsTicketObject.JSON_PROPERTY_COMMENTS_PUBLIC_BY_DEFAULT,
    AccountSettingsTicketObject.JSON_PROPERTY_DEFAULT_SOLVED_TICKET_REASSIGNMENT_STRATEGY,
    AccountSettingsTicketObject.JSON_PROPERTY_DEFAULT_TO_DRAFT_MODE,
    AccountSettingsTicketObject.JSON_PROPERTY_EMAIL_ATTACHMENTS,
    AccountSettingsTicketObject.JSON_PROPERTY_EMOJI_AUTOCOMPLETION,
    AccountSettingsTicketObject.JSON_PROPERTY_FOLLOWER_AND_EMAIL_CC_COLLABORATIONS,
    AccountSettingsTicketObject.JSON_PROPERTY_HAS_COLOR_TEXT,
    AccountSettingsTicketObject.JSON_PROPERTY_IS_FIRST_COMMENT_PRIVATE_ENABLED,
    AccountSettingsTicketObject.JSON_PROPERTY_LIGHT_AGENT_EMAIL_CCS_ALLOWED,
    AccountSettingsTicketObject.JSON_PROPERTY_LIST_EMPTY_VIEWS,
    AccountSettingsTicketObject.JSON_PROPERTY_LIST_NEWEST_COMMENTS_FIRST,
    AccountSettingsTicketObject.JSON_PROPERTY_MARKDOWN_TICKET_COMMENTS,
    AccountSettingsTicketObject.JSON_PROPERTY_MAXIMUM_PERSONAL_VIEWS_TO_LIST,
    AccountSettingsTicketObject.JSON_PROPERTY_MODERN_TICKET_REASSIGNMENT,
    AccountSettingsTicketObject.JSON_PROPERTY_PRIVATE_ATTACHMENTS,
    AccountSettingsTicketObject.JSON_PROPERTY_RICH_TEXT_COMMENTS,
    AccountSettingsTicketObject.JSON_PROPERTY_SHOW_MODERN_TICKET_REASSIGNMENT,
    AccountSettingsTicketObject.JSON_PROPERTY_STATUS_HOLD,
    AccountSettingsTicketObject.JSON_PROPERTY_TAGGING,
    AccountSettingsTicketObject.JSON_PROPERTY_USING_SKILL_BASED_ROUTING,
})
@Serdeable
public class AccountSettingsTicketObject {

    public static final String JSON_PROPERTY_ACCEPTED_NEW_COLLABORATION_TOS = "accepted_new_collaboration_tos";
    public static final String JSON_PROPERTY_AGENT_COLLISION = "agent_collision";
    public static final String JSON_PROPERTY_AGENT_INVITATION_ENABLED = "agent_invitation_enabled";
    public static final String JSON_PROPERTY_AGENT_TICKET_DELETION = "agent_ticket_deletion";
    public static final String JSON_PROPERTY_ALLOW_GROUP_RESET = "allow_group_reset";
    public static final String JSON_PROPERTY_ASSIGN_DEFAULT_ORGANIZATION = "assign_default_organization";
    public static final String JSON_PROPERTY_ASSIGN_TICKETS_UPON_SOLVE = "assign_tickets_upon_solve";
    public static final String JSON_PROPERTY_AUTO_TRANSLATION_ENABLED = "auto_translation_enabled";
    public static final String JSON_PROPERTY_AUTO_UPDATED_CCS_FOLLOWERS_RULES = "auto_updated_ccs_followers_rules";
    public static final String JSON_PROPERTY_CHAT_SLA_ENABLEMENT = "chat_sla_enablement";
    public static final String JSON_PROPERTY_COLLABORATION = "collaboration";
    public static final String JSON_PROPERTY_COMMENTS_PUBLIC_BY_DEFAULT = "comments_public_by_default";
    public static final String JSON_PROPERTY_DEFAULT_SOLVED_TICKET_REASSIGNMENT_STRATEGY = "default_solved_ticket_reassignment_strategy";
    public static final String JSON_PROPERTY_DEFAULT_TO_DRAFT_MODE = "default_to_draft_mode";
    public static final String JSON_PROPERTY_EMAIL_ATTACHMENTS = "email_attachments";
    public static final String JSON_PROPERTY_EMOJI_AUTOCOMPLETION = "emoji_autocompletion";
    public static final String JSON_PROPERTY_FOLLOWER_AND_EMAIL_CC_COLLABORATIONS = "follower_and_email_cc_collaborations";
    public static final String JSON_PROPERTY_HAS_COLOR_TEXT = "has_color_text";
    public static final String JSON_PROPERTY_IS_FIRST_COMMENT_PRIVATE_ENABLED = "is_first_comment_private_enabled";
    public static final String JSON_PROPERTY_LIGHT_AGENT_EMAIL_CCS_ALLOWED = "light_agent_email_ccs_allowed";
    public static final String JSON_PROPERTY_LIST_EMPTY_VIEWS = "list_empty_views";
    public static final String JSON_PROPERTY_LIST_NEWEST_COMMENTS_FIRST = "list_newest_comments_first";
    public static final String JSON_PROPERTY_MARKDOWN_TICKET_COMMENTS = "markdown_ticket_comments";
    public static final String JSON_PROPERTY_MAXIMUM_PERSONAL_VIEWS_TO_LIST = "maximum_personal_views_to_list";
    public static final String JSON_PROPERTY_MODERN_TICKET_REASSIGNMENT = "modern_ticket_reassignment";
    public static final String JSON_PROPERTY_PRIVATE_ATTACHMENTS = "private_attachments";
    public static final String JSON_PROPERTY_RICH_TEXT_COMMENTS = "rich_text_comments";
    public static final String JSON_PROPERTY_SHOW_MODERN_TICKET_REASSIGNMENT = "show_modern_ticket_reassignment";
    public static final String JSON_PROPERTY_STATUS_HOLD = "status_hold";
    public static final String JSON_PROPERTY_TAGGING = "tagging";
    public static final String JSON_PROPERTY_USING_SKILL_BASED_ROUTING = "using_skill_based_routing";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACCEPTED_NEW_COLLABORATION_TOS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean acceptedNewCollaborationTos;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_COLLISION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentCollision;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_INVITATION_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentInvitationEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_TICKET_DELETION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentTicketDeletion;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ALLOW_GROUP_RESET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean allowGroupReset;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGN_DEFAULT_ORGANIZATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean assignDefaultOrganization;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGN_TICKETS_UPON_SOLVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean assignTicketsUponSolve;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTO_TRANSLATION_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean autoTranslationEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTO_UPDATED_CCS_FOLLOWERS_RULES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean autoUpdatedCcsFollowersRules;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CHAT_SLA_ENABLEMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean chatSlaEnablement;

    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLABORATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean collaboration;

    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENTS_PUBLIC_BY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean commentsPublicByDefault;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_SOLVED_TICKET_REASSIGNMENT_STRATEGY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String defaultSolvedTicketReassignmentStrategy;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_TO_DRAFT_MODE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean defaultToDraftMode;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emailAttachments;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMOJI_AUTOCOMPLETION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emojiAutocompletion;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_AND_EMAIL_CC_COLLABORATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean followerAndEmailCcCollaborations;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HAS_COLOR_TEXT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean hasColorText;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_FIRST_COMMENT_PRIVATE_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isFirstCommentPrivateEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LIGHT_AGENT_EMAIL_CCS_ALLOWED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean lightAgentEmailCcsAllowed;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LIST_EMPTY_VIEWS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean listEmptyViews;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LIST_NEWEST_COMMENTS_FIRST)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean listNewestCommentsFirst;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MARKDOWN_TICKET_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean markdownTicketComments;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAXIMUM_PERSONAL_VIEWS_TO_LIST)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long maximumPersonalViewsToList;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MODERN_TICKET_REASSIGNMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean modernTicketReassignment;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIVATE_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean privateAttachments;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RICH_TEXT_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean richTextComments;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHOW_MODERN_TICKET_REASSIGNMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean showModernTicketReassignment;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS_HOLD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean statusHold;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGGING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean tagging;

    @Nullable
    @JsonProperty(JSON_PROPERTY_USING_SKILL_BASED_ROUTING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean usingSkillBasedRouting;

}