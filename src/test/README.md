# 🧪 Client Test & Authorization Coverage Matrix

> **Note**: Declarative Micronaut client interfaces are tested via live sandbox integration tests (`Z4jSpec`).
> This report tracks endpoint and admin-level test coverage across all client interfaces.

## 📊 Summary Metrics

- **Total Client Interfaces**: 98
- **Clients with Active Tests**: 98 / 98 (100.0%)
- **Total Endpoints Declared**: 629
- **Endpoints Tested**: **265 / 629 (42.1%)**
  - 👑 **Admin Level (`adminCtx`)**: 265 endpoints
  - 🛡️ **Agent Level (`agentCtx`)**: 258 endpoints
  - 👤 **End-User Level (`userCtx`)**: 265 endpoints

---

## 📋 Client Breakdown

### 🟡 `AccessClient` (1/2 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `loginGet` | `GET /access/login` | ✅ | ✅ | ✅ | 🟢 Complete |
| `loginPost` | `POST /access/login` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `AccountFeaturesClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listFeatures` | `GET /api/v2/account/features` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `AccountSettingsClient` (1/2 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `showAccountSettings` | `GET /api/v2/account/settings` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateAccountSettings` | `PUT /api/v2/account/settings` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `ActivityStreamClient` (3/3 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `countActivities` | `GET /api/v2/activities/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listActivities` | `GET /api/v2/activities` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showActivity` | `GET /api/v2/activities/{activity_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `ApprovalRequestsClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `searchApprovals` | `POST /api/v2/approval_workflow_instances/{approval_workflow_instance_id}/approval_requests/search` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showApprovalRequest` | `GET /api/v2/approval_workflow_instances/{approval_workflow_instance_id}/approval_requests/{approval_request_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateDecisionApprovalRequest` | `PATCH /api/v2/approval_workflow_instances/{approval_workflow_instance_id}/approval_requests/{approval_request_id}/decision` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `ArticleClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listArticles` | `GET /api/v2/help_center/{locale}/articles` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `AssigneeFieldAssignableAgentsClient` (1/2 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listAssigneeFieldAssignableGroupAgents` | `GET /api/lotus/assignables/groups/{group_id}/agents.json` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listAssigneeFieldAssignableGroups` | `GET /api/lotus/assignables/groups.json` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `AssigneeFieldAssignableGroupsClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listAssigneeFieldAssignableGroupsAndAgentsSearch` | `GET /api/lotus/assignables/autocomplete.json` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `AttachmentsClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `deleteUpload` | `DELETE /api/v2/uploads/{token}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `redactCommentAttachment` | `PUT /api/v2/tickets/{ticket_id}/comments/{comment_id}/attachments/{attachment_id}/redact` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showAttachment` | `GET /api/v2/attachments/{attachment_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateAttachment` | `PUT /api/v2/attachments/{attachment_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `uploadFiles` | `POST /api/v2/uploads` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `AuditLogsClient` (2/3 endpoints — 67%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `exportAuditLogs` | `POST /api/v2/audit_logs/export` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listAuditLogs` | `GET /api/v2/audit_logs` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showAuditLog` | `GET /api/v2/audit_logs/{audit_log_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `AutomationsClient` (4/9 endpoints — 44%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `bulkDeleteAutomations` | `DELETE /api/v2/automations/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createAutomation` | `POST /api/v2/automations` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteAutomation` | `DELETE /api/v2/automations/{automation_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listActiveAutomations` | `GET /api/v2/automations/active` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAutomations` | `GET /api/v2/automations` | ✅ | ✅ | ✅ | 🟢 Complete |
| `searchAutomations` | `GET /api/v2/automations/search` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showAutomation` | `GET /api/v2/automations/{automation_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateAutomation` | `PUT /api/v2/automations/{automation_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateManyAutomations` | `PUT /api/v2/automations/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `BasicsClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTicketOrVoicemailTicket` | `POST /api/v2/channels/voice/tickets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `openTicketInAgentBrowser` | `POST /api/v2/channels/voice/agents/{agent_id}/tickets/{ticket_id}/display` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `openUsersProfileInAgentBrowser` | `POST /api/v2/channels/voice/agents/{agent_id}/users/{user_id}/display` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `BookmarksClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createBookmark` | `POST /api/v2/bookmarks` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteBookmark` | `DELETE /api/v2/bookmarks/{bookmark_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listBookmarks` | `GET /api/v2/bookmarks` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `BrandAgentsClient` (5/5 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listBrandAgents` | `GET /api/v2/brand_agents` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listBrandAgentsByBrand` | `GET /api/v2/brands/{brand_id}/agents` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserBrandAgents` | `GET /api/v2/users/{user_id}/brand_agents` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showBrandAgentById` | `GET /api/v2/brand_agents/{brand_agent_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showUserBrandAgentById` | `GET /api/v2/users/{user_id}/brand_agents/{brand_agent_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `BrandsClient` (4/7 endpoints — 57%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `checkHostMappingValidity` | `GET /api/v2/brands/check_host_mapping` | ✅ | ✅ | ✅ | 🟢 Complete |
| `checkHostMappingValidityForExistingBrand` | `GET /api/v2/brands/{brand_id}/check_host_mapping` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createBrand` | `POST /api/v2/brands` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteBrand` | `DELETE /api/v2/brands/{brand_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listBrands` | `GET /api/v2/brands` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showBrand` | `GET /api/v2/brands/{brand_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateBrand` | `PUT /api/v2/brands/{brand_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `CategoryClient` (4/11 endpoints — 36%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createCategory` | `POST /api/v2/help_center/{locale}/categories` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createCategoryNoLocale` | `POST /api/v2/help_center/categories` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteCategory` | `DELETE /api/v2/help_center/{locale}/categories/{category_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteCategoryNoLocale` | `DELETE /api/v2/help_center/categories/{category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCategories` | `GET /api/v2/help_center/{locale}/categories` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listCategoriesNoLocale` | `GET /api/v2/help_center/categories` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showCategory` | `GET /api/v2/help_center/{locale}/categories/{category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showCategoryNoLocale` | `GET /api/v2/help_center/categories/{category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateCategory` | `PUT /api/v2/help_center/{locale}/categories/{category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateCategoryNoLocale` | `PUT /api/v2/help_center/categories/{category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateCategorySourceLocale` | `PUT /api/v2/help_center/categories/{category_id}/source_locale` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `ChannelFrameworkClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `pushContentToSupport` | `POST /api/v2/any_channel/push` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `reportChannelbackError` | `POST /api/v2/any_channel/channelback/report_error` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `validateToken` | `POST /api/v2/any_channel/validate_token` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `ConversationLogClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listConversationLogForTicket` | `GET /api/v2/tickets/{ticket_id}/conversation_log` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `CustomObjectFieldsClient` (2/7 endpoints — 29%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createCustomObjectField` | `POST /api/v2/custom_objects/{custom_object_key}/fields` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `customObjectFieldsLimit` | `GET /api/v2/custom_objects/{custom_object_key}/limits/field_limit` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteCustomObjectField` | `DELETE /api/v2/custom_objects/{custom_object_key}/fields/{custom_object_field_key_or_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCustomObjectFields` | `GET /api/v2/custom_objects/{custom_object_key}/fields` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderCustomObjectFields` | `PUT /api/v2/custom_objects/{custom_object_key}/fields/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showCustomObjectField` | `GET /api/v2/custom_objects/{custom_object_key}/fields/{custom_object_field_key_or_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateCustomObjectField` | `PATCH /api/v2/custom_objects/{custom_object_key}/fields/{custom_object_field_key_or_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `CustomObjectPermissionsClient` (3/9 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createAccessRule` | `POST /api/v2/custom_objects/{custom_object_key}/access_rules` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteAccessRule` | `DELETE /api/v2/custom_objects/{custom_object_key}/access_rules/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listAccessRuleDefinitions` | `GET /api/v2/custom_objects/{custom_object_key}/access_rules/definitions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAccessRules` | `GET /api/v2/custom_objects/{custom_object_key}/access_rules` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listPermissionPolicies` | `GET /api/v2/custom_objects/{custom_object_key}/permission_policies` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showAccessRule` | `GET /api/v2/custom_objects/{custom_object_key}/access_rules/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showPermissionPolicy` | `GET /api/v2/custom_objects/{custom_object_key}/permission_policies/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateAccessRule` | `PATCH /api/v2/custom_objects/{custom_object_key}/access_rules/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updatePermissionPolicy` | `PATCH /api/v2/custom_objects/{custom_object_key}/permission_policies/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `CustomObjectRecordAttachmentsClient` (1/4 endpoints — 25%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createCustomObjectRecordAttachment` | `POST /api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteCustomObjectRecordAttachment` | `DELETE /api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCustomObjectRecordAttachments` | `GET /api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments/{id}/download` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateCustomObjectRecordAttachment` | `PUT /api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `CustomObjectRecordsClient` (4/14 endpoints — 29%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `autocompleteCustomObjectRecordSearch` | `GET /api/v2/custom_objects/{custom_object_key}/records/autocomplete` | ✅ | ✅ | ✅ | 🟢 Complete |
| `countCustomObjectRecords` | `GET /api/v2/custom_objects/{custom_object_key}/records/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createCustomObjectRecord` | `POST /api/v2/custom_objects/{custom_object_key}/records` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `customObjectRecordBulkJobs` | `POST /api/v2/custom_objects/{custom_object_key}/jobs` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `customObjectRecordsLimit` | `GET /api/v2/custom_objects/limits/record_limit` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteCustomObjectRecord` | `DELETE /api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteCustomObjectRecordByExternalIdOrName` | `DELETE /api/v2/custom_objects/{custom_object_key}/records` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `filteredSearchCustomObjectRecords` | `POST /api/v2/custom_objects/{custom_object_key}/records/search` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `incrementalCustomObjectRecordExportCursor` | `GET /api/v2/incremental/custom_objects/{custom_object_key}/cursor` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCustomObjectRecords` | `GET /api/v2/custom_objects/{custom_object_key}/records` | ✅ | ✅ | ✅ | 🟢 Complete |
| `searchCustomObjectRecords` | `GET /api/v2/custom_objects/{custom_object_key}/records/search` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showCustomObjectRecord` | `GET /api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateCustomObjectRecord` | `PATCH /api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `upsertCustomObjectRecordByExternalIdOrName` | `PATCH /api/v2/custom_objects/{custom_object_key}/records` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `CustomObjectsClient` (3/6 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createCustomObject` | `POST /api/v2/custom_objects` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `customObjectsLimit` | `GET /api/v2/custom_objects/limits/object_limit` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteCustomObject` | `DELETE /api/v2/custom_objects/{custom_object_key}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCustomObjects` | `GET /api/v2/custom_objects` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showCustomObject` | `GET /api/v2/custom_objects/{custom_object_key}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateCustomObject` | `PATCH /api/v2/custom_objects/{custom_object_key}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `CustomRolesClient` (2/5 endpoints — 40%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createCustomRole` | `POST /api/v2/custom_roles` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteCustomRoleById` | `DELETE /api/v2/custom_roles/{custom_role_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCustomRoles` | `GET /api/v2/custom_roles` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showCustomRoleById` | `GET /api/v2/custom_roles/{custom_role_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateCustomRoleById` | `PUT /api/v2/custom_roles/{custom_role_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `CustomTicketStatusesClient` (2/6 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `bulkUpdateDefaultCustomStatus` | `PUT /api/v2/custom_status/default` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createCustomStatus` | `POST /api/v2/custom_statuses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createTicketFormStatusesForCustomStatus` | `POST /api/v2/custom_statuses/{custom_status_id}/ticket_form_statuses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCustomStatuses` | `GET /api/v2/custom_statuses` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showCustomStatus` | `GET /api/v2/custom_statuses/{custom_status_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateCustomStatus` | `PUT /api/v2/custom_statuses/{custom_status_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `DeletionSchedulesClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createDeletionSchedule` | `POST /api/v2/deletion_schedules` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteDeletionSchedule` | `DELETE /api/v2/deletion_schedules/{deletion_schedule_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `getDeletionSchedule` | `GET /api/v2/deletion_schedules/{deletion_schedule_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listDeletionSchedules` | `GET /api/v2/deletion_schedules` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateDeletionSchedule` | `PUT /api/v2/deletion_schedules/{deletion_schedule_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `DynamicContentClient` (3/6 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createDynamicContent` | `POST /api/v2/dynamic_content/items` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteDynamicContentItem` | `DELETE /api/v2/dynamic_content/items/{dynamic_content_item_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listDynamicContents` | `GET /api/v2/dynamic_content/items` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showDynamicContentItem` | `GET /api/v2/dynamic_content/items/{dynamic_content_item_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showManyDynamicContents` | `GET /api/v2/dynamic_content/items/show_many` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateDynamicContentItem` | `PUT /api/v2/dynamic_content/items/{dynamic_content_item_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `DynamicContentItemVariantsClient` (2/7 endpoints — 29%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createDynamicContentVariant` | `POST /api/v2/dynamic_content/items/{dynamic_content_item_id}/variants` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createManyDynamicContentVariants` | `POST /api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/create_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteDynamicContentVariant` | `DELETE /api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/{dynamic_content_variant_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `dynamicContentListVariants` | `GET /api/v2/dynamic_content/items/{dynamic_content_item_id}/variants` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showDynamicContentVariant` | `GET /api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/{dynamic_content_variant_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateDynamicContentVariant` | `PUT /api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/{dynamic_content_variant_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateManyDynamicContentVariants` | `PUT /api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `EmailNotificationsClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listEmailNotifications` | `GET /api/v2/email_notifications` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showEmailNotification` | `GET /api/v2/email_notifications/{notification_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showManyEmailNotifications` | `GET /api/v2/email_notifications/show_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `EssentialsCardClient` (1/4 endpoints — 25%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `deleteEssentialsCard` | `DELETE /api/v2/object_layouts/{object_type}/essentials_card` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showEssentialsCard` | `GET /api/v2/object_layouts/{object_type}/essentials_card` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showEssentialsCards` | `GET /api/v2/object_layouts/essentials_cards` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateEssentialsCard` | `PUT /api/v2/object_layouts/{object_type}/essentials_card` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `GlobalClientsClient` (3/3 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `globalOAuthClientsTokenSummary` | `GET /api/v2/oauth/global_clients/token_summary` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listGlobalOAuthClients` | `GET /api/v2/oauth/global_clients` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showGlobalClient` | `GET /api/v2/oauth/global_clients/{global_client_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `GrantTypeTokensClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTokenForGrantType` | `POST /oauth/tokens` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `GroupMembershipsClient` (5/14 endpoints — 36%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createGroupMembership` | `POST /api/v2/group_memberships` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createUserGroupMembership` | `POST /api/v2/users/{user_id}/group_memberships` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteGroupMembership` | `DELETE /api/v2/group_memberships/{group_membership_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteUserGroupMembership` | `DELETE /api/v2/users/{user_id}/group_memberships/{group_membership_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `groupMembershipBulkCreate` | `POST /api/v2/group_memberships/create_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `groupMembershipBulkDelete` | `DELETE /api/v2/group_memberships/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `groupMembershipSetDefault` | `PUT /api/v2/users/{user_id}/group_memberships/{group_membership_id}/make_default` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listAssignableGroupMemberships` | `GET /api/v2/group_memberships/assignable` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAssignableGroupMembershipsByGroup` | `GET /api/v2/groups/{group_id}/memberships/assignable` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listGroupMemberships` | `GET /api/v2/group_memberships` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listGroupMembershipsByGroup` | `GET /api/v2/groups/{group_id}/memberships` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserGroupMemberships` | `GET /api/v2/users/{user_id}/group_memberships` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showGroupMembershipById` | `GET /api/v2/group_memberships/{group_membership_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showUserGroupMembershipById` | `GET /api/v2/users/{user_id}/group_memberships/{group_membership_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `GroupSlaPoliciesClient` (3/7 endpoints — 43%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createGroupSLAPolicy` | `POST /api/v2/group_slas/policies` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteGroupSLAPolicy` | `DELETE /api/v2/group_slas/policies/{group_sla_policy_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listGroupSLAPolicies` | `GET /api/v2/group_slas/policies` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderGroupSLAPolicies` | `PUT /api/v2/group_slas/policies/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `retrieveGroupSLAPolicyFilterDefinitionItems` | `GET /api/v2/group_slas/policies/definitions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showGroupSLAPolicy` | `GET /api/v2/group_slas/policies/{group_sla_policy_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateGroupSLAPolicy` | `PUT /api/v2/group_slas/policies/{group_sla_policy_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `GroupsClient` (9/9 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `countGroups` | `GET /api/v2/groups/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `countUserGroups` | `GET /api/v2/users/{user_id}/groups/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createGroup` | `POST /api/v2/groups` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteGroup` | `DELETE /api/v2/groups/{group_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAssignableGroups` | `GET /api/v2/groups/assignable` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listGroups` | `GET /api/v2/groups` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserGroups` | `GET /api/v2/users/{user_id}/groups` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showGroupById` | `GET /api/v2/groups/{group_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateGroup` | `PUT /api/v2/groups/{group_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `IncrementalExportClient` (7/7 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `incrementalOrganizationExport` | `GET /api/v2/incremental/organizations` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalSampleExport` | `GET /api/v2/incremental/{incremental_resource}/sample` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalTicketEvents` | `GET /api/v2/incremental/ticket_events` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalTicketExportCursor` | `GET /api/v2/incremental/tickets/cursor` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalTicketExportTime` | `GET /api/v2/incremental/tickets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalUserExportCursor` | `GET /api/v2/incremental/users/cursor` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalUserExportTime` | `GET /api/v2/incremental/users` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `IncrementalSkillBasedRoutingClient` (3/3 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `incrementalSkilBasedRoutingAttributeValuesExport` | `GET /api/v2/incremental/routing/attribute_values` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalSkilBasedRoutingAttributesExport` | `GET /api/v2/incremental/routing/attributes` | ✅ | ✅ | ✅ | 🟢 Complete |
| `incrementalSkilBasedRoutingInstanceValuesExport` | `GET /api/v2/incremental/routing/instance_values` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `InternalClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `resolveOrganizationNames` | `POST /api/v2/internal/organizations/resolve_by_names` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `ItamAssetFieldsClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createItamAssetTypeField` | `POST /api/v2/it_asset_management/asset_types/{asset_type_id}/fields` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteItamAssetTypeField` | `DELETE /api/v2/it_asset_management/asset_types/{asset_type_id}/fields/{asset_type_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listItamAssetTypeFields` | `GET /api/v2/it_asset_management/asset_types/{asset_type_id}/fields` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showItamAssetTypeField` | `GET /api/v2/it_asset_management/asset_types/{asset_type_id}/fields/{asset_type_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateItamAssetTypeField` | `PATCH /api/v2/it_asset_management/asset_types/{asset_type_id}/fields/{asset_type_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `ItamAssetLocationsClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createItamLocation` | `POST /api/v2/it_asset_management/locations` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteItamLocation` | `DELETE /api/v2/it_asset_management/locations/{location_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listItamLocations` | `GET /api/v2/it_asset_management/locations` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showItamLocation` | `GET /api/v2/it_asset_management/locations/{location_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateItamLocation` | `PATCH /api/v2/it_asset_management/locations/{location_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `ItamAssetStatusesClient` (2/2 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listItamStatuses` | `GET /api/v2/it_asset_management/statuses` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showItamStatus` | `GET /api/v2/it_asset_management/statuses/{status_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `ItamAssetTypesClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createItamAssetType` | `POST /api/v2/it_asset_management/asset_types` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteItamAssetType` | `DELETE /api/v2/it_asset_management/asset_types/{asset_type_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listItamAssetTypes` | `GET /api/v2/it_asset_management/asset_types` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showItamAssetType` | `GET /api/v2/it_asset_management/asset_types/{asset_type_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateItamAssetType` | `PATCH /api/v2/it_asset_management/asset_types/{asset_type_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `ItamAssetsClient` (1/6 endpoints — 17%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createItamAsset` | `POST /api/v2/it_asset_management/assets` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteItamAsset` | `DELETE /api/v2/it_asset_management/assets/{asset_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `itamAssetBulkJobs` | `POST /api/v2/it_asset_management/assets/jobs` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listItamAssets` | `GET /api/v2/it_asset_management/assets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showItamAsset` | `GET /api/v2/it_asset_management/assets/{asset_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateItamAsset` | `PATCH /api/v2/it_asset_management/assets/{asset_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `JobStatusesClient` (3/3 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listJobStatuses` | `GET /api/v2/job_statuses` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showJobStatus` | `GET /api/v2/job_statuses/{job_status_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showManyJobStatuses` | `GET /api/v2/job_statuses/show_many` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `LocaleClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listLocales` | `GET /api/v2/locales` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `LocalesClient` (6/6 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `detectBestLocale` | `GET /api/v2/locales/detect_best_locale` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAvailablePublicLocales` | `GET /api/v2/locales/public` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listLocales` | `GET /api/v2/locales` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listLocalesForAgent` | `GET /api/v2/locales/agent` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showCurrentLocale` | `GET /api/v2/locales/current` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showLocaleById` | `GET /api/v2/locales/{locale_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `LookupRelationshipsClient` (1/2 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `getRelationshipFilterDefinitions` | `GET /api/v2/relationships/definitions/{target_type}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `getSourcesByTarget` | `GET /api/v2/{target_type}/{target_id}/relationship_fields/{field_id}/{source_type}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `MacrosClient` (12/19 endpoints — 63%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createAssociatedMacroAttachment` | `POST /api/v2/macros/{macro_id}/attachments` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createMacro` | `POST /api/v2/macros` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createMacroAttachment` | `POST /api/v2/macros/attachments` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteMacro` | `DELETE /api/v2/macros/{macro_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteManyMacros` | `DELETE /api/v2/macros/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listActiveMacros` | `GET /api/v2/macros/active` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listMacroActionDefinitions` | `GET /api/v2/macros/definitions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listMacroAttachments` | `GET /api/v2/macros/{macro_id}/attachments` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listMacroCategories` | `GET /api/v2/macros/categories` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listMacros` | `GET /api/v2/macros` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listMacrosActions` | `GET /api/v2/macros/actions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `searchMacro` | `GET /api/v2/macros/search` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showChangesToTicket` | `GET /api/v2/macros/{macro_id}/apply` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showDerivedMacro` | `GET /api/v2/macros/new` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showMacro` | `GET /api/v2/macros/{macro_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showMacroAttachment` | `GET /api/v2/macros/attachments/{attachment_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showTicketAfterChanges` | `GET /api/v2/tickets/{ticket_id}/macros/{macro_id}/apply` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateMacro` | `PUT /api/v2/macros/{macro_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateManyMacros` | `PUT /api/v2/macros/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `OAuthClientsClient` (2/7 endpoints — 29%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `clientGenerateSecret` | `PUT /api/v2/oauth/clients/{oauth_client_id}/generate_secret` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createOAuthClient` | `POST /api/v2/oauth/clients` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteClient` | `DELETE /api/v2/oauth/clients/{oauth_client_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCurrentUserOAuthClients` | `GET /api/v2/users/me/oauth/clients` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listOAuthClients` | `GET /api/v2/oauth/clients` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showClient` | `GET /api/v2/oauth/clients/{oauth_client_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateClient` | `PUT /api/v2/oauth/clients/{oauth_client_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `OAuthTokensClient` (1/6 endpoints — 17%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createOAuthToken` | `POST /api/v2/oauth/tokens` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOAuthTokens` | `GET /api/v2/oauth/tokens` | ✅ | ✅ | ✅ | 🟢 Complete |
| `revokeCurrentOAuthToken` | `DELETE /api/v2/oauth/tokens/current` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `revokeOAuthToken` | `DELETE /api/v2/oauth/tokens/{oauth_token_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showCurrentToken` | `GET /api/v2/oauth/tokens/current` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showToken` | `GET /api/v2/oauth/tokens/{oauth_token_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `ObjectTriggersClient` (1/10 endpoints — 10%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createObjectTrigger` | `POST /api/v2/custom_objects/{custom_object_key}/triggers` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteManyObjectTriggers` | `DELETE /api/v2/custom_objects/{custom_object_key}/triggers/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteObjectTrigger` | `DELETE /api/v2/custom_objects/{custom_object_key}/triggers/{trigger_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `getObjectTrigger` | `GET /api/v2/custom_objects/{custom_object_key}/triggers/{trigger_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listActiveObjectTriggers` | `GET /api/v2/custom_objects/{custom_object_key}/triggers/active` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listObjectTriggers` | `GET /api/v2/custom_objects/{custom_object_key}/triggers` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listObjectTriggersDefinitions` | `GET /api/v2/custom_objects/{custom_object_key}/triggers/definitions` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `searchObjectTriggers` | `GET /api/v2/custom_objects/{custom_object_key}/triggers/search` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateManyObjectTriggers` | `PUT /api/v2/custom_objects/{custom_object_key}/triggers/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateObjectTrigger` | `PUT /api/v2/custom_objects/{custom_object_key}/triggers/{trigger_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `OmnichannelRoutingQueuesClient` (3/7 endpoints — 43%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createQueue` | `POST /api/v2/queues` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteQueue` | `DELETE /api/v2/queues/{queue_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listQueueDefinitions` | `GET /api/v2/queues/definitions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listQueues` | `GET /api/v2/queues` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderQueues` | `PATCH /api/v2/queues/order` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showQueueById` | `GET /api/v2/queues/{queue_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateQueue` | `PUT /api/v2/queues/{queue_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `OrganizationFieldsClient` (2/6 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createOrganizationField` | `POST /api/v2/organization_fields` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteOrganizationField` | `DELETE /api/v2/organization_fields/{organization_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOrganizationFields` | `GET /api/v2/organization_fields` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderOrganizationField` | `PUT /api/v2/organization_fields/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showOrganizationField` | `GET /api/v2/organization_fields/{organization_field_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateOrganizationField` | `PUT /api/v2/organization_fields/{organization_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `OrganizationMembershipsClient` (5/14 endpoints — 36%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createManyOrganizationMemberships` | `POST /api/v2/organization_memberships/create_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createOrganizationMembership` | `POST /api/v2/organization_memberships` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createUserOrganizationMembership` | `POST /api/v2/users/{user_id}/organization_memberships` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteManyOrganizationMemberships` | `DELETE /api/v2/organization_memberships/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteOrganizationMembership` | `DELETE /api/v2/organization_memberships/{organization_membership_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteUserOrganizationMembership` | `DELETE /api/v2/users/{user_id}/organization_memberships/{organization_membership_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOrganizationMemberships` | `GET /api/v2/organization_memberships` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listOrganizationMembershipsByOrganization` | `GET /api/v2/organizations/{organization_id}/organization_memberships` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserOrganizationMemberships` | `GET /api/v2/users/{user_id}/organization_memberships` | ✅ | ✅ | ✅ | 🟢 Complete |
| `setOrganizationAsDefault` | `PUT /api/v2/users/{user_id}/organizations/{organization_id}/make_default` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `setOrganizationMembershipAsDefault` | `PUT /api/v2/users/{user_id}/organization_memberships/{organization_membership_id}/make_default` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showOrganizationMembershipById` | `GET /api/v2/organization_memberships/{organization_membership_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showOrganizationMembershipByUserId` | `GET /api/v2/users/{user_id}/organization_memberships/{organization_membership_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `unassignOrganization` | `DELETE /api/v2/users/{user_id}/organizations/{organization_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `OrganizationSubscriptionsClient` (3/6 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createOrganizationSubscription` | `POST /api/v2/organization_subscriptions` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteOrganizationSubscription` | `DELETE /api/v2/organization_subscriptions/{organization_subscription_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOrganizationSubscriptions` | `GET /api/v2/organization_subscriptions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listOrganizationSubscriptionsByOrganization` | `GET /api/v2/organizations/{organization_id}/subscriptions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserOrganizationSubscriptions` | `GET /api/v2/users/{user_id}/organization_subscriptions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showOrganizationSubscription` | `GET /api/v2/organization_subscriptions/{organization_subscription_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `OrganizationsClient` (12/19 endpoints — 63%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `autocompleteOrganizations` | `GET /api/v2/organizations/autocomplete` | ✅ | ✅ | ✅ | 🟢 Complete |
| `countOrganizations` | `GET /api/v2/organizations/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `countUserOrganizations` | `GET /api/v2/users/{user_id}/organizations/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createManyOrganizations` | `POST /api/v2/organizations/create_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createOrUpdateOrganization` | `POST /api/v2/organizations/create_or_update` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createOrganization` | `POST /api/v2/organizations` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createOrganizationMerge` | `POST /api/v2/organizations/{organization_id}/merge` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteManyOrganizations` | `DELETE /api/v2/organizations/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteOrganization` | `DELETE /api/v2/organizations/{organization_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listOrganizationMerges` | `GET /api/v2/organizations/{organization_id}/merges` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOrganizations` | `GET /api/v2/organizations` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserOrganizations` | `GET /api/v2/users/{user_id}/organizations` | ✅ | ✅ | ✅ | 🟢 Complete |
| `organizationRelated` | `GET /api/v2/organizations/{organization_id}/related` | ✅ | ✅ | ✅ | 🟢 Complete |
| `searchOrganizations` | `GET /api/v2/organizations/search` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showManyOrganizations` | `GET /api/v2/organizations/show_many` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showOrganization` | `GET /api/v2/organizations/{organization_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showOrganizationMerge` | `GET /api/v2/organization_merges/{organization_merge_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateManyOrganizations` | `PUT /api/v2/organizations/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateOrganization` | `PUT /api/v2/organizations/{organization_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `OtpClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `removeOtpSetting` | `DELETE /auth/api/one_time_password` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showOtpSetting` | `GET /auth/api/one_time_password` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateOtpSetting` | `PATCH /auth/api/one_time_password` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `PushNotificationDevicesClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `pushNotificationDevices` | `POST /api/v2/push_notification_devices/destroy_many` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `RemoteAuthenticationsClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listRemoteAuthentications` | `GET /api/v2/remote_authentications` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `RequestsClient` (5/12 endpoints — 42%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createRequest` | `POST /api/v2/requests` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listCCDRequests` | `GET /api/v2/requests/ccd` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listComments` | `GET /api/v2/requests/{request_id}/comments` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOpenRequests` | `GET /api/v2/requests/open` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listOrganizationRequests` | `GET /api/v2/organizations/{organization_id}/requests` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listRequests` | `GET /api/v2/requests` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listSolvedRequests` | `GET /api/v2/requests/solved` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserRequests` | `GET /api/v2/users/{user_id}/requests` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `searchRequests` | `GET /api/v2/requests/search` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showComment` | `GET /api/v2/requests/{request_id}/comments/{ticket_comment_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showRequest` | `GET /api/v2/requests/{request_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateRequest` | `PUT /api/v2/requests/{request_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `ResellerClient` (1/2 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTrialAccount` | `POST /api/v2/accounts` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `verifySubdomainAvailability` | `GET /api/v2/accounts/available` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `ResourceCollectionsClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createResourceCollection` | `POST /api/v2/resource_collections` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteResourceCollection` | `DELETE /api/v2/resource_collections/{resource_collection_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listResourceCollections` | `GET /api/v2/resource_collections` | ✅ | ✅ | ✅ | 🟢 Complete |
| `retrieveResourceCollection` | `GET /api/v2/resource_collections/{resource_collection_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateResourceCollection` | `PUT /api/v2/resource_collections/{resource_collection_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `SatisfactionRatingsClient` (3/4 endpoints — 75%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `countSatisfactionRatings` | `GET /api/v2/satisfaction_ratings/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createTicketSatisfactionRating` | `POST /api/v2/tickets/{ticket_id}/satisfaction_rating` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listSatisfactionRatings` | `GET /api/v2/satisfaction_ratings` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showSatisfactionRating` | `GET /api/v2/satisfaction_ratings/{satisfaction_rating_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `SatisfactionReasonsClient` (2/2 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listSatisfactionRatingReasons` | `GET /api/v2/satisfaction_reasons` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showSatisfactionRatings` | `GET /api/v2/satisfaction_reasons/{satisfaction_reason_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `SearchClient` (2/2 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `count` | `GET /api/v2/search/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `list` | `GET /api/v2/search/export?filter[type]=ticket` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `SecuritySettingsClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `showSecuritySettings` | `GET /api/v2/security_settings` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `SessionsClient` (2/8 endpoints — 25%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `bulkDeleteSessionsByUserId` | `DELETE /api/v2/users/{user_id}/sessions` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteAuthenticatedSession` | `DELETE /api/v2/users/me/logout` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteSession` | `DELETE /api/v2/users/{user_id}/sessions/{session_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listSessions` | `GET /api/v2/sessions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserSessions` | `GET /api/v2/users/{user_id}/sessions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `renewCurrentSession` | `GET /api/v2/users/me/session/renew` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showCurrentlyAuthenticatedSession` | `GET /api/v2/users/me/session` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showSession` | `GET /api/v2/users/{user_id}/sessions/{session_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `SharingAgreementsClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createSharingAgreement` | `POST /api/v2/sharing_agreements` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteSharingAgreement` | `DELETE /api/v2/sharing_agreements/{sharing_agreement_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listSharingAgreements` | `GET /api/v2/sharing_agreements` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showSharingAgreement` | `GET /api/v2/sharing_agreements/{sharing_agreement_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateSharingAgreement` | `PUT /api/v2/sharing_agreements/{sharing_agreement_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `SkillBasedRoutingClient` (5/18 endpoints — 28%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `bulkSetAgentAttributeValuesJob` | `POST /api/v2/routing/agents/instance_values/job` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createAttribute` | `POST /api/v2/routing/attributes` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createAttributeValue` | `POST /api/v2/routing/attributes/{attribute_id}/values` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteAttribute` | `DELETE /api/v2/routing/attributes/{attribute_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteAttributeValue` | `DELETE /api/v2/routing/attributes/{attribute_id}/values/{attribute_value_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listAGentAttributeValues` | `GET /api/v2/routing/agents/{user_id}/instance_values` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAccountAttributes` | `GET /api/v2/routing/attributes` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAttributeValues` | `GET /api/v2/routing/attributes/{attribute_id}/values` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listManyAgentsAttributeValues` | `GET /api/v2/routing/agents/instance_values` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listRoutingAttributeDefinitions` | `GET /api/v2/routing/attributes/definitions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTicketAttributeValues` | `GET /api/v2/routing/tickets/{ticket_id}/instance_values` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTicketsFullfilledByUser` | `GET /api/v2/routing/requirements/fulfilled` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `setAgentAttributeValues` | `POST /api/v2/routing/agents/{user_id}/instance_values` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `setTicketAttributeValues` | `POST /api/v2/routing/tickets/{ticket_id}/instance_values` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showAttribute` | `GET /api/v2/routing/attributes/{attribute_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showAttributeValue` | `GET /api/v2/routing/attributes/{attribute_id}/values/{attribute_value_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateAttribute` | `PUT /api/v2/routing/attributes/{attribute_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateAttributeValue` | `PATCH /api/v2/routing/attributes/{attribute_id}/values/{attribute_value_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `SlaPoliciesClient` (3/7 endpoints — 43%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createSLAPolicy` | `POST /api/v2/slas/policies` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteSLAPolicy` | `DELETE /api/v2/slas/policies/{sla_policy_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listSLAPolicies` | `GET /api/v2/slas/policies` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderSLAPolicies` | `PUT /api/v2/slas/policies/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `retrieveSLAPolicyFilterDefinitionItems` | `GET /api/v2/slas/policies/definitions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showSLAPolicy` | `GET /api/v2/slas/policies/{sla_policy_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateSLAPolicy` | `PUT /api/v2/slas/policies/{sla_policy_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `SupportAddressesClient` (2/6 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createSupportAddress` | `POST /api/v2/recipient_addresses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteRecipientAddress` | `DELETE /api/v2/recipient_addresses/{support_address_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listSupportAddresses` | `GET /api/v2/recipient_addresses` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showSupportAddress` | `GET /api/v2/recipient_addresses/{support_address_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateSupportAddress` | `PUT /api/v2/recipient_addresses/{support_address_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `verifySupportAddressForwarding` | `PUT /api/v2/recipient_addresses/{support_address_id}/verify` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `SuspendedTicketsClient` (1/8 endpoints — 13%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `deleteSuspendedTicket` | `DELETE /api/v2/suspended_tickets/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteSuspendedTickets` | `DELETE /api/v2/suspended_tickets/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `exportSuspendedTickets` | `POST /api/v2/suspended_tickets/export` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listSuspendedTickets` | `GET /api/v2/suspended_tickets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `recoverSuspendedTicket` | `PUT /api/v2/suspended_tickets/{id}/recover` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `recoverSuspendedTickets` | `PUT /api/v2/suspended_tickets/recover_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showSuspendedTickets` | `GET /api/v2/suspended_tickets/{id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `suspendedTicketsAttachments` | `POST /api/v2/suspended_tickets/{id}/attachments` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TagsClient` (4/15 endpoints — 27%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `addOrganizationTags` | `PUT /api/v2/organizations/{organization_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `autocompleteTags` | `GET /api/v2/autocomplete/tags` | ✅ | ✅ | ✅ | 🟢 Complete |
| `countTags` | `GET /api/v2/tags/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteTagsTicket` | `DELETE /api/v2/tickets/{ticket_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteUserTags` | `DELETE /api/v2/users/{user_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOrganizationTags` | `GET /api/v2/organizations/{organization_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listResourceTags` | `GET /api/v2/tickets/{ticket_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTags` | `GET /api/v2/tags` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserTags` | `GET /api/v2/users/{user_id}/tags` | ✅ | ✅ | ✅ | 🟢 Complete |
| `putTagsTicket` | `PUT /api/v2/tickets/{ticket_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `putUserTags` | `PUT /api/v2/users/{user_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `removeOrganizationTags` | `DELETE /api/v2/organizations/{organization_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `setOrganizationTags` | `POST /api/v2/organizations/{organization_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `setTagsTicket` | `POST /api/v2/tickets/{ticket_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `setUserTags` | `POST /api/v2/users/{user_id}/tags` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TargetFailuresClient` (1/2 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listTargetFailures` | `GET /api/v2/target_failures` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTargetFailure` | `GET /api/v2/target_failures/{target_failure_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TargetsClient` (2/5 endpoints — 40%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTarget` | `POST /api/v2/targets` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTarget` | `DELETE /api/v2/targets/{target_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTargets` | `GET /api/v2/targets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTarget` | `GET /api/v2/targets/{target_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateTarget` | `PUT /api/v2/targets/{target_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TicketAuditsClient` (4/5 endpoints — 80%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `countAuditsForTicket` | `GET /api/v2/tickets/{ticket_id}/audits/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listAuditsForTicket` | `GET /api/v2/tickets/{ticket_id}/audits` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTicketAudits` | `GET /api/v2/ticket_audits` | ✅ | ✅ | ✅ | 🟢 Complete |
| `makeTicketCommentPrivateFromAudits` | `PUT /api/v2/tickets/{ticket_id}/audits/{ticket_audit_id}/make_private` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showTicketAudit` | `GET /api/v2/tickets/{ticket_id}/audits/{ticket_audit_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `TicketClient` (7/7 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTicket` | `POST /api/v2/tickets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createTicketField` | `POST /api/v2/ticket_fields` | ✅ | ✅ | ✅ | 🟢 Complete |
| `getTicketCount` | `GET /api/v2/tickets/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTicketFields` | `GET /api/v2/ticket_fields` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTickets` | `GET /api/v2/tickets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTicket` | `GET /api/v2/tickets/{ticket_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateTicket` | `PUT /api/v2/tickets/{ticket_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `TicketCommentsClient` (2/7 endpoints — 29%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `countTicketComments` | `GET /api/v2/tickets/{ticket_id}/comments/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTicketComments` | `GET /api/v2/tickets/{ticket_id}/comments` | ✅ | ✅ | ✅ | 🟢 Complete |
| `makeTicketCommentPrivate` | `PUT /api/v2/tickets/{ticket_id}/comments/{ticket_comment_id}/make_private` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `redactChatComment` | `PUT /api/v2/chat_redactions/{ticket_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `redactChatCommentAttachment` | `PUT /api/v2/chat_file_redactions/{ticket_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `redactStringInComment` | `PUT /api/v2/tickets/{ticket_id}/comments/{ticket_comment_id}/redact` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `redactTicketCommentInAgentWorkspace` | `PUT /api/v2/comment_redactions/{ticket_comment_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TicketContentPinsClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTicketContentPin` | `POST /api/v2/ticket_content_pins` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTicketContentPin` | `DELETE /api/v2/ticket_content_pins/{content_pin_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTicketContentPins` | `GET /api/v2/ticket_content_pins` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `TicketFieldsClient` (4/12 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `countTicketFields` | `GET /api/v2/ticket_fields/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createOrUpdateTicketFieldOption` | `POST /api/v2/ticket_fields/{ticket_field_id}/options` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createTicketField` | `POST /api/v2/ticket_fields` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTicketField` | `DELETE /api/v2/ticket_fields/{ticket_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTicketFieldOption` | `DELETE /api/v2/ticket_fields/{ticket_field_id}/options/{ticket_field_option_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTicketFieldOptions` | `GET /api/v2/ticket_fields/{ticket_field_id}/options` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTicketFields` | `GET /api/v2/ticket_fields` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderTicketFields` | `PUT /api/v2/ticket_fields/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showManyTicketFields` | `GET /api/v2/ticket_fields/show_many` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTicketFieldOption` | `GET /api/v2/ticket_fields/{ticket_field_id}/options/{ticket_field_option_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showTicketfield` | `GET /api/v2/ticket_fields/{ticket_field_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateTicketField` | `PUT /api/v2/ticket_fields/{ticket_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TicketFormStatusesClient` (1/4 endpoints — 25%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `deleteTicketFormStatusById` | `DELETE /api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses/{ticket_form_status_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTicketFormStatuses` | `DELETE /api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTicketFormStatuses` | `GET /api/v2/ticket_form_statuses` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showManyTicketFormStatuses` | `GET /api/v2/ticket_form_statuses/show_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TicketFormsClient` (4/12 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `cloneTicketForm` | `POST /api/v2/ticket_forms/{ticket_form_id}/clone` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createTicketForm` | `POST /api/v2/ticket_forms` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createTicketFormStatuses` | `POST /api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTicketForm` | `DELETE /api/v2/ticket_forms/{ticket_form_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTicketForms` | `GET /api/v2/ticket_forms` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderTicketForms` | `PUT /api/v2/ticket_forms/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showManyTicketForms` | `GET /api/v2/ticket_forms/show_many` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTicketForm` | `GET /api/v2/ticket_forms/{ticket_form_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `ticketFormTicketFormStatuses` | `GET /api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateTicketForm` | `PUT /api/v2/ticket_forms/{ticket_form_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateTicketFormStatusById` | `PUT /api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses/{ticket_form_status_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateTicketFormStatuses` | `PUT /api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TicketImportClient` (1/2 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `ticketBulkImport` | `POST /api/v2/imports/tickets/create_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `ticketImport` | `POST /api/v2/imports/tickets` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `TicketMetricEventsClient` (1/1 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listTicketMetricEvents` | `GET /api/v2/incremental/ticket_metric_events` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟢 `TicketMetricsClient` (3/3 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listTicketMetrics` | `GET /api/v2/ticket_metrics` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTicketMetrics` | `GET /api/v2/ticket_metrics/{ticket_metric_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTicketMetricsByTicket` | `GET /api/v2/tickets/{ticket_id}/metrics` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `TicketSkipsClient` (2/4 endpoints — 50%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `listSkips` | `GET /api/v2/skips` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTicketSkips` | `GET /api/v2/users/{user_id}/skips` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTicketSkipsByTicket` | `GET /api/v2/tickets/{ticket_id}/skips` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `recordNewSkip` | `POST /api/v2/skips` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TicketsClient` (5/28 endpoints — 18%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `bulkDeleteTickets` | `POST /api/v2/problems/autocomplete` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `bulkPermanentlyDeleteTickets` | `DELETE /api/v2/deleted_tickets/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `bulkRestoreDeletedTickets` | `PUT /api/v2/deleted_tickets/restore_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `countOrganizationTickets` | `GET /api/v2/organizations/{organization_id}/tickets/count` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `countTickets` | `GET /api/v2/tickets/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `countUserAssignedTickets` | `GET /api/v2/users/{user_id}/tickets/assigned/count` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `countUserCCDTickets` | `GET /api/v2/users/{user_id}/tickets/ccd/count` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createTicket` | `POST /api/v2/tickets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteTicket` | `DELETE /api/v2/tickets/{ticket_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTicketPermanently` | `DELETE /api/v2/deleted_tickets/{ticket_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listDeletedTickets` | `GET /api/v2/deleted_tickets` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOrganizationTickets` | `GET /api/v2/organizations/{organization_id}/tickets` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listRecentTickets` | `GET /api/v2/tickets/recent` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTickets` | `GET /api/v2/tickets/{ticket_id}/collaborators` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserAssignedTickets` | `GET /api/v2/users/{user_id}/tickets/assigned` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listUserCCDTickets` | `GET /api/v2/users/{user_id}/tickets/ccd` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listUserFollowedTickets` | `GET /api/v2/users/{user_id}/tickets/followed` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listUserRequestedTickets` | `GET /api/v2/users/{user_id}/tickets/requested` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `markManyTicketsAsSpam` | `PUT /api/v2/tickets/mark_many_as_spam` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `markTicketAsSpamAndSuspendRequester` | `PUT /api/v2/tickets/{ticket_id}/mark_as_spam` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `mergeTicketsIntoTargetTicket` | `POST /api/v2/tickets/{ticket_id}/merge` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `restoreDeletedTicket` | `PUT /api/v2/deleted_tickets/{ticket_id}/restore` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showTicket` | `GET /api/v2/tickets/{ticket_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `ticketRelatedInformation` | `GET /api/v2/tickets/{ticket_id}/related` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `ticketsCreateMany` | `POST /api/v2/tickets/create_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `ticketsShowMany` | `GET /api/v2/tickets/show_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `ticketsUpdateMany` | `PUT /api/v2/tickets/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateTicket` | `PUT /api/v2/tickets/{ticket_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `TriggerCategoriesClient` (2/6 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `batchOperateTriggerCategories` | `POST /api/v2/trigger_categories/jobs` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createTriggerCategory` | `POST /api/v2/trigger_categories` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTriggerCategory` | `DELETE /api/v2/trigger_categories/{trigger_category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTriggerCategories` | `GET /api/v2/trigger_categories` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showTriggerCategoryById` | `GET /api/v2/trigger_categories/{trigger_category_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateTriggerCategory` | `PATCH /api/v2/trigger_categories/{trigger_category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `TriggersClient` (5/13 endpoints — 38%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTrigger` | `POST /api/v2/triggers` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteManyTriggers` | `DELETE /api/v2/triggers/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteTrigger` | `DELETE /api/v2/triggers/{trigger_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `getTrigger` | `GET /api/v2/triggers/{trigger_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listActiveTriggers` | `GET /api/v2/triggers/active` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTriggerActionConditionDefinitions` | `GET /api/v2/triggers/definitions` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTriggerRevisions` | `GET /api/v2/triggers/{trigger_id}/revisions` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listTriggers` | `GET /api/v2/triggers` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderTriggers` | `PUT /api/v2/triggers/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `searchTriggers` | `GET /api/v2/triggers/search` | ✅ | ✅ | ✅ | 🟢 Complete |
| `triggerRevision` | `GET /api/v2/triggers/{trigger_id}/revisions/{trigger_revision_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateManyTriggers` | `PUT /api/v2/triggers/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateTrigger` | `PUT /api/v2/triggers/{trigger_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `UserFieldsClient` (3/10 endpoints — 30%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createOrUpdateUserFieldOption` | `POST /api/v2/user_fields/{user_field_id}/options` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createUserField` | `POST /api/v2/user_fields` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteUserField` | `DELETE /api/v2/user_fields/{user_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteUserFieldOption` | `DELETE /api/v2/user_fields/{user_field_id}/options/{user_field_option_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listUserFieldOptions` | `GET /api/v2/user_fields/{user_field_id}/options` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listUserFields` | `GET /api/v2/user_fields` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderUserField` | `PUT /api/v2/user_fields/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showUserField` | `GET /api/v2/user_fields/{user_field_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showUserFieldOption` | `GET /api/v2/user_fields/{user_field_id}/options/{user_field_option_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateUserField` | `PUT /api/v2/user_fields/{user_field_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `UserIdentitiesClient` (2/14 endpoints — 14%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createEndUserIdentity` | `POST /api/v2/end_users/{user_id}/identities` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createUserIdentity` | `POST /api/v2/users/{user_id}/identities` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteEndUserIdentity` | `DELETE /api/v2/end_users/{user_id}/identities/{user_identity_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteUserIdentity` | `DELETE /api/v2/users/{user_id}/identities/{user_identity_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listEndUserIdentities` | `GET /api/v2/end_users/{user_id}/identities` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listUserIdentities` | `GET /api/v2/users/{user_id}/identities` | ✅ | ✅ | ✅ | 🟢 Complete |
| `makeEndUserIdentityPrimary` | `PUT /api/v2/end_users/{user_id}/identities/{user_identity_id}/make_primary` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `makeUserIdentityPrimary` | `PUT /api/v2/users/{user_id}/identities/{user_identity_id}/make_primary` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `requestEndUserVerification` | `PUT /api/v2/end_users/{user_id}/identities/{user_identity_id}/request_verification` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `requestUserVerfication` | `PUT /api/v2/users/{user_id}/identities/{user_identity_id}/request_verification` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showEndUserIdentity` | `GET /api/v2/end_users/{user_id}/identities/{user_identity_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showUserIdentity` | `GET /api/v2/users/{user_id}/identities/{user_identity_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateUserIdentity` | `PUT /api/v2/users/{user_id}/identities/{user_identity_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `verifyUserIdentity` | `PUT /api/v2/users/{user_id}/identities/{user_identity_id}/verify` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `UserPasswordsClient` (1/3 endpoints — 33%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `changeOwnPassword` | `PUT /api/v2/users/{user_id}/password` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `getUserPasswordRequirements` | `GET /api/v2/users/{user_id}/password/requirements` | ✅ | ✅ | ✅ | 🟢 Complete |
| `setUserPassword` | `POST /api/v2/users/{user_id}/password` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟢 `UserSegmentClient` (7/7 endpoints — 100%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createUserSegment` | `POST /api/v2/help_center/user_segments` | ✅ | ⚪ | ✅ | 🟡 In Progress |
| `deleteUserSegment` | `DELETE /api/v2/help_center/user_segments/{user_segment_id}` | ✅ | ⚪ | ✅ | 🟡 In Progress |
| `listUserSegmentSections` | `GET /api/v2/help_center/user_segments/{user_segment_id}/sections` | ✅ | ⚪ | ✅ | 🟡 In Progress |
| `listUserSegmentTopics` | `GET /api/v2/help_center/user_segments/{user_segment_id}/topics` | ✅ | ⚪ | ✅ | 🟡 In Progress |
| `listUserSegments` | `GET /api/v2/help_center/user_segments` | ✅ | ⚪ | ✅ | 🟡 In Progress |
| `showUserSegment` | `GET /api/v2/help_center/user_segments/{user_segment_id}` | ✅ | ⚪ | ✅ | 🟡 In Progress |
| `updateUserSegment` | `PUT /api/v2/help_center/user_segments/{user_segment_id}` | ✅ | ⚪ | ✅ | 🟡 In Progress |

### 🟡 `UsersClient` (11/31 endpoints — 35%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `autocompleteUsers` | `GET /api/v2/users/autocomplete` | ✅ | ✅ | ✅ | 🟢 Complete |
| `autocompleteUsersPost` | `POST /api/v2/users/autocomplete` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `countDeletedUsers` | `GET /api/v2/deleted_users/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `countGroupUsers` | `GET /api/v2/groups/{group_id}/users/count` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `countOrganizationUsers` | `GET /api/v2/organizations/{organization_id}/users/count` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `countUsers` | `GET /api/v2/users/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createManyUsers` | `POST /api/v2/users/create_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createOrUpdateManyUsers` | `POST /api/v2/users/create_or_update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createOrUpdateUser` | `POST /api/v2/users/create_or_update` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `createUser` | `POST /api/v2/users` | ✅ | ✅ | ✅ | 🟢 Complete |
| `deleteUser` | `DELETE /api/v2/users/{user_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `destroyManyUsers` | `DELETE /api/v2/users/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `getUserEntitlementsFull` | `GET /api/v2/users/{user_id}/entitlements/full` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listDeletedUsers` | `GET /api/v2/deleted_users` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listGroupUsers` | `GET /api/v2/groups/{group_id}/users` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listOrganizationUsers` | `GET /api/v2/organizations/{organization_id}/users` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listUsers` | `GET /api/v2/users` | ✅ | ✅ | ✅ | 🟢 Complete |
| `logoutManyUsers` | `POST /api/v2/users/logout_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `mergeEndUsers` | `PUT /api/v2/users/{user_id}/merge` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `permanentlyDeleteUser` | `DELETE /api/v2/deleted_users/{deleted_user_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `requestUserCreate` | `POST /api/v2/users/request_create` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `searchUsers` | `GET /api/v2/users/search` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showCurrentUser` | `GET /api/v2/users/me` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showDeletedUser` | `GET /api/v2/deleted_users/{deleted_user_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showManyUsers` | `GET /api/v2/users/show_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showUser` | `GET /api/v2/users/{user_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showUserComplianceDeletionStatuses` | `GET /api/v2/users/{user_id}/compliance_deletion_statuses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showUserRelated` | `GET /api/v2/users/{user_id}/related` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateCurrentUserSettings` | `PUT /api/v2/users/me/settings` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateManyUsers` | `PUT /api/v2/users/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateUser` | `PUT /api/v2/users/{user_id}` | ✅ | ✅ | ✅ | 🟢 Complete |

### 🟡 `ViewCategoriesClient` (1/5 endpoints — 20%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createViewCategory` | `POST /api/v2/view_categories` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteViewCategory` | `DELETE /api/v2/view_categories/{view_category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listViewCategories` | `GET /api/v2/view_categories` | ✅ | ✅ | ✅ | 🟢 Complete |
| `orderViewCategories` | `PUT /api/v2/view_categories/order` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateViewCategory` | `PATCH /api/v2/view_categories/{view_category_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `ViewsClient` (7/19 endpoints — 37%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `bulkDeleteViews` | `DELETE /api/v2/views/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `countViews` | `GET /api/v2/views/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `createView` | `POST /api/v2/views` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteView` | `DELETE /api/v2/views/{view_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `executeView` | `GET /api/v2/views/{view_id}/execute` | ✅ | ✅ | ✅ | 🟢 Complete |
| `exportView` | `GET /api/v2/views/{view_id}/export` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `getViewCount` | `GET /api/v2/views/{view_id}/count` | ✅ | ✅ | ✅ | 🟢 Complete |
| `getViewCounts` | `GET /api/v2/views/count_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listActiveViews` | `GET /api/v2/views/active` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listCompactViews` | `GET /api/v2/views/compact` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listTicketsFromView` | `GET /api/v2/views/{view_id}/tickets` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listViews` | `GET /api/v2/views` | ✅ | ✅ | ✅ | 🟢 Complete |
| `listViewsById` | `GET /api/v2/views/show_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `previewCount` | `POST /api/v2/views/preview/count` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `previewViews` | `POST /api/v2/views/preview` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `searchViews` | `GET /api/v2/views/search` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showView` | `GET /api/v2/views/{view_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateManyViews` | `PUT /api/v2/views/update_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `updateView` | `PUT /api/v2/views/{view_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `WorkspacesClient` (2/7 endpoints — 29%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createWorkspace` | `POST /api/v2/workspaces` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `deleteWorkspace` | `DELETE /api/v2/workspaces/{workspace_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `destroyManyWorkspaces` | `DELETE /api/v2/workspaces/destroy_many` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listWorkspaces` | `GET /api/v2/workspaces` | ✅ | ✅ | ✅ | 🟢 Complete |
| `reorderWorkspaces` | `PUT /api/v2/workspaces/reorder` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `showWorkspace` | `GET /api/v2/workspaces/{workspace_id}` | ✅ | ✅ | ✅ | 🟢 Complete |
| `updateWorkspace` | `PUT /api/v2/workspaces/{workspace_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

### 🟡 `XChannelClient` (1/4 endpoints — 25%)

| Method | Route | Admin | Agent | End User | Status |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `createTicketFromTweet` | `POST /api/v2/channels/twitter/tickets` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `gettingTwicketStatus` | `GET /api/v2/channels/twitter/tickets/{comment_id}/statuses` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |
| `listMonitoredTwitterHandles` | `GET /api/v2/channels/twitter/monitored_twitter_handles` | ✅ | ✅ | ✅ | 🟢 Complete |
| `showMonitoredTwitterHandle` | `GET /api/v2/channels/twitter/monitored_twitter_handles/{monitored_twitter_handle_id}` | ⚪ | ⚪ | ⚪ | ⚪ Not Tested |

