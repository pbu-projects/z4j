import re

with open('build.gradle.kts', 'r') as f:
    content = f.read()

# First, remove the adminTest1, adminTest2, adminTest3 blocks we just added.
# Also remove the original adminTest block if it's there (should be gone already).
pattern_to_remove = r'val adminTest[123]? by tasks\.registering\(Test::class\) \{.*?\}\n'
content = re.sub(pattern_to_remove, '', content, flags=re.DOTALL)

# Let's cleanly append our new meaningful suites at the end before tasks.check {
account_tests = ["Access", "AccountFeatures", "AccountSettings", "Attachments", "AuditLogs", "Basics", "Bookmarks", "BrandAgents", "Brands", "ChannelFramework", "GlobalClients", "GrantTypeTokens", "Locale", "Locales", "OAuthClients", "OAuthTokens", "Otp", "PushNotificationDevices", "RemoteAuthentications", "Reseller", "SecuritySettings", "Sessions", "SharingAgreements", "SupportAddresses", "Workspaces", "XChannel"]
workflow_tests = ["ActivityStream", "ApprovalRequests", "AssigneeFieldAssignableAgents", "Automations", "ConversationLog", "DeletionSchedules", "DynamicContent", "DynamicContentItemVariants", "EmailNotifications", "JobStatuses", "ObjectTriggers", "OmnichannelRoutingQueues", "Requests", "ResourceCollections", "SatisfactionRatings", "SatisfactionReasons", "Search", "SkillBasedRouting", "SlaPolicies", "Tags", "TargetFailures", "Targets", "Triggers"]
custom_itam_tests = ["CustomObjectFields", "CustomObjectPermissions", "CustomObjectRecordAttachments", "CustomObjectRecords", "CustomObjects", "CustomRoles", "EssentialsCard", "IncrementalExport", "IncrementalSkillBasedRouting", "Internal", "ItamAssetFields", "ItamAssetLocations", "ItamAssetStatuses", "ItamAssetTypes", "ItamAssets", "LookupRelationships"]

def make_includes(test_list):
    includes = [f'"**/{t}ClientSpec.class"' for t in test_list]
    return ", ".join(includes)

suites = f"""
val accountSettingsTest by tasks.registering(Test::class) {{
    description = "Runs account, brand, security, and global admin integration tests"
    group = "verification"
    useJUnitPlatform()
    include({make_includes(account_tests)})
    maxHeapSize = "2g"
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    finalizedBy(tasks.jacocoTestReport)
}}

val routingWorkflowTest by tasks.registering(Test::class) {{
    description = "Runs routing, SLA, automations, and trigger integration tests"
    group = "verification"
    useJUnitPlatform()
    include({make_includes(workflow_tests)})
    maxHeapSize = "2g"
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    finalizedBy(tasks.jacocoTestReport)
}}

val customObjectsItamTest by tasks.registering(Test::class) {{
    description = "Runs custom objects, ITAM, and specialized data integration tests"
    group = "verification"
    useJUnitPlatform()
    include({make_includes(custom_itam_tests)})
    maxHeapSize = "2g"
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    finalizedBy(tasks.jacocoTestReport)
}}

"""

# Insert right before tasks.check {
if 'tasks.check {' in content:
    content = content.replace('tasks.check {', suites + 'tasks.check {')
else:
    content += suites

with open('build.gradle.kts', 'w') as f:
    f.write(content)

