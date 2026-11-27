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
package lol.pbu.z4j.model

import com.sun.net.httpserver.HttpServer
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.serde.Encoder
import io.micronaut.serde.ObjectMapper
import io.micronaut.serde.Serializer
import jakarta.validation.Validator
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import lol.pbu.z4j.client.ArticleClient
import org.spockframework.runtime.model.parallel.ExecutionMode
import spock.lang.AutoCleanup
import spock.lang.Execution
import spock.lang.Shared
import spock.lang.Specification

@Execution(ExecutionMode.SAME_THREAD)
class PartialUpdateSerializationUnitSpec extends Specification {

    @Shared
    HttpServer server

    @Shared
    String capturedPath

    @Shared
    String capturedMethod

    @Shared
    String capturedBody

    @Shared
    @AutoCleanup
    ApplicationContext ctx

    @Shared
    ObjectMapper objectMapper

    @Shared
    ArticleClient articleClient

    void setupSpec() {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/") { exchange ->
            capturedMethod = exchange.requestMethod
            capturedPath = exchange.requestURI.path
            capturedBody = new String(exchange.requestBody.readAllBytes(), StandardCharsets.UTF_8)
            byte[] response = '{"article":{"id":42,"permission_group_id":10,"title":"t","label_names":[],"user_segment_id":null}}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, response.length)
            exchange.responseBody.write(response)
            exchange.close()
        }
        server.start()

        ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : "http://127.0.0.1:${server.address.port}",
                "micronaut.http.services.zendesk.oauth.token": "test-token"
        ])
        objectMapper = ctx.getBean(ObjectMapper)
        articleClient = ctx.getBean(ArticleClient)
    }

    void cleanupSpec() {
        server?.stop(0)
    }

    def "ArticleUpdateInput partial update omits unset fields and keeps empty lists and explicit nulls"() {
        given:
        ArticleUpdateInput input = ArticleUpdateInput.builder()
                .title("t")
                .labelNames(List.of())
                .userSegmentId(null)
                .contentTagIds(List.of())
                .userSegmentIds(List.of())
                .promoted(false)
                .draft(false)
                .position(0L)
                .build()
        ArticleUpdateRequest request = ArticleUpdateRequest.of(input)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map parsed = objectMapper.readValue(json, Map)
        Map articleMap = parsed.get("article") as Map

        String defaultJson = ObjectMapper.getDefault().writeValueAsString(request)
        Map defaultParsed = ObjectMapper.getDefault().readValue(defaultJson, Map)
        Map defaultArticleMap = defaultParsed.get("article") as Map

        then:
        [articleMap, defaultArticleMap].each { map ->
            verifyAll(map) {
                get("title") == "t"
                containsKey("label_names")
                get("label_names") == []
                containsKey("content_tag_ids")
                get("content_tag_ids") == []
                containsKey("user_segment_ids")
                get("user_segment_ids") == []
                get("promoted") == false
                get("draft") == false
                get("position") == 0
                !containsKey("body")
                !containsKey("author_id")
                !containsKey("section_id")
                !containsKey("permission_group_id")
            }
        }
        verifyAll(articleMap) {
            containsKey("user_segment_id")
            get("user_segment_id") == null
        }
    }

    def "ArticleUpdateInput withers support clearing and resetting fields"() {
        given:
        ArticleUpdateInput cleared = new ArticleUpdateInput()
                .withTitle("Only Title")
                .clearUserSegmentId()
                .clearLabelNames()
        ArticleUpdateInput reset = cleared
                .withUserSegmentId(99L)
                .withLabelNames(["faq"])

        when:
        Map clearedMap = objectMapper.readValue(objectMapper.writeValueAsString(cleared), Map)
        Map resetMap = objectMapper.readValue(objectMapper.writeValueAsString(reset), Map)

        then:
        verifyAll(clearedMap) {
            get("title") == "Only Title"
            containsKey("user_segment_id")
            get("user_segment_id") == null
            containsKey("label_names")
            get("label_names") == null
            !containsKey("body")
        }
        verifyAll(resetMap) {
            get("title") == "Only Title"
            get("user_segment_id") == 99
            get("label_names") == ["faq"]
            !containsKey("body")
        }
    }

    def "ArticleClient sends ArticleUpdateInput clearing payload over HTTP"() {
        given:
        ArticleUpdateInput input = new ArticleUpdateInput()
                .withTitle("t")
                .withLabelNames(List.of())
                .clearUserSegmentId()

        when:
        ArticleResponse response = articleClient.updateArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, 42L, input).block()
        Map sentPayload = objectMapper.readValue(capturedBody, Map)
        Map sentArticle = sentPayload.get("article") as Map

        then:
        verifyAll {
            capturedMethod == "PUT"
            capturedPath == "/api/v2/help_center/en-us/articles/42"
            response?.article()?.id() == 42L
            sentArticle.get("title") == "t"
            sentArticle.containsKey("label_names")
            sentArticle.get("label_names") == []
            sentArticle.containsKey("user_segment_id")
            sentArticle.get("user_segment_id") == null
            !sentArticle.containsKey("body")
        }
    }

    def "ArticleUpdateRequest preserves backwards compatibility with Article"() {
        given:
        Article legacy = new Article().withTitle("Legacy").withLabelNames(List.of())
        ArticleUpdateRequest req = new ArticleUpdateRequest(legacy)

        when:
        String json = objectMapper.writeValueAsString(req)
        Map parsed = objectMapper.readValue(json, Map)
        ArticleUpdateRequest roundTripped = objectMapper.readValue(json, ArticleUpdateRequest)

        String defaultJson = ObjectMapper.getDefault().writeValueAsString(req)
        Map defaultParsed = ObjectMapper.getDefault().readValue(defaultJson, Map)
        ArticleUpdateRequest defaultRoundTripped = ObjectMapper.getDefault().readValue(defaultJson, ArticleUpdateRequest)
        Validator validator = ctx.getBean(Validator)

        then:
        verifyAll {
            validator.validate(req).isEmpty()
            (parsed.get("article") as Map).get("title") == "Legacy"
            (parsed.get("article") as Map).get("label_names") == []
            !(parsed.get("article") as Map).containsKey("body")
            roundTripped.article().title() == "Legacy"
            roundTripped.article().labelNames() == []
            (defaultParsed.get("article") as Map).get("title") == "Legacy"
            (defaultParsed.get("article") as Map).get("label_names") == []
            defaultRoundTripped.article().title() == "Legacy"
            defaultRoundTripped.article().labelNames() == []
        }
    }

    def "TicketUpdateInput and User clearX then withX and withX(null) omit cleared fields"() {
        given:
        TicketUpdateInput resetTicket = new TicketUpdateInput()
                .clearAssigneeId()
                .withAssigneeId(42L)
                .withAssigneeId(null)
                .clearTags()
                .addTagsItem("urgent")
                .withTags(null)
                .clearCustomStatusId()
                .withCustomStatusId(7)
                .withCustomStatusId((Integer) null)

        User resetUser = new User()
                .clearOrganizationId()
                .withOrganizationId(99L)
                .withOrganizationId(null)
                .clearTags()
                .addTagsItem("vip")
                .withTags(null)
                .clearAgentBrandIds()
                .addAgentBrandIdsItem(10L)
                .withAgentBrandIds(null)

        when:
        Map ticketMap = objectMapper.readValue(objectMapper.writeValueAsString(resetTicket), Map)
        Map userMap = objectMapper.readValue(objectMapper.writeValueAsString(resetUser), Map)

        then:
        verifyAll(ticketMap) {
            isEmpty()
            !containsKey("assignee_id")
            !containsKey("tags")
            !containsKey("custom_status_id")
        }
        verifyAll(userMap) {
            isEmpty()
            !containsKey("organization_id")
            !containsKey("tags")
            !containsKey("agent_brand_ids")
        }
    }

    def "TicketUpdateInput serializes empty lists and explicit nulls while omitting unset fields"() {
        given:
        TicketUpdateInput emptyInput = new TicketUpdateInput()
        TicketUpdateInput clearInput = new TicketUpdateInput()
                .withSubject("Updated subject")
                .withTags(List.of())
                .withCollaboratorIds(List.of())
                .withCustomFields(List.of())
                .withEmailCcs(List.of())
                .withFollowers(List.of())
                .clearAssigneeId()
                .clearGroupId()
                .clearOrganizationId()
                .clearDueAt()
                .clearProblemId()

        when:
        Map emptyMap = objectMapper.readValue(objectMapper.writeValueAsString(emptyInput), Map)
        String clearJson = objectMapper.writeValueAsString(clearInput)
        Map clearMap = objectMapper.readValue(clearJson, Map)
        TicketUpdateInput roundTripped = objectMapper.readValue(clearJson, TicketUpdateInput)

        then:
        emptyMap.isEmpty()
        verifyAll(clearMap) {
            get("subject") == "Updated subject"
            containsKey("tags")
            get("tags") == []
            containsKey("collaborator_ids")
            get("collaborator_ids") == []
            containsKey("custom_fields")
            get("custom_fields") == []
            containsKey("email_ccs")
            get("email_ccs") == []
            containsKey("followers")
            get("followers") == []
            containsKey("assignee_id")
            get("assignee_id") == null
            containsKey("group_id")
            get("group_id") == null
            containsKey("organization_id")
            get("organization_id") == null
            containsKey("due_at")
            get("due_at") == null
            containsKey("problem_id")
            get("problem_id") == null
            !containsKey("priority")
            !containsKey("status")
            !containsKey("external_id")
        }
        verifyAll(roundTripped) {
            subject() == "Updated subject"
            tags() == []
            collaboratorIds() == []
            assigneeId() == null
        }
    }

    def "User serializes empty lists, nullable user_fields, and explicit nulls while omitting unset fields"() {
        given:
        User emptyUser = new User()
        User update = new User()
                .withName("Jane")
                .withTags(List.of())
                .withAgentBrandIds(List.of())
                .withUserFields([dept: null, tier: "gold"])
                .clearOrganizationId()
                .clearDefaultGroupId()
                .clearNotes()
                .clearPhone()

        when:
        Map emptyMap = objectMapper.readValue(objectMapper.writeValueAsString(emptyUser), Map)
        String updateJson = objectMapper.writeValueAsString(update)
        Map updateMap = objectMapper.readValue(updateJson, Map)
        User roundTripped = objectMapper.readValue(updateJson, User)

        then:
        emptyMap.isEmpty()
        verifyAll(updateMap) {
            get("name") == "Jane"
            containsKey("tags")
            get("tags") == []
            containsKey("agent_brand_ids")
            get("agent_brand_ids") == []
            containsKey("organization_id")
            get("organization_id") == null
            containsKey("default_group_id")
            get("default_group_id") == null
            containsKey("notes")
            get("notes") == null
            containsKey("phone")
            get("phone") == null
            (get("user_fields") as Map).containsKey("dept")
            (get("user_fields") as Map).get("dept") == null
            (get("user_fields") as Map).get("tier") == "gold"
            !containsKey("email")
            !containsKey("role")
            !containsKey("signature")
        }
        verifyAll(roundTripped) {
            name() == "Jane"
            tags() == []
            agentBrandIds() == []
            organizationId() == null
        }
    }

    def "ArticleUpdateInput constructors, from(), all withers, adders, clearers, and Builder methods work"() {
        given:
        Article sourceArticle = new Article()
                .withLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .withPermissionGroupId(1L)
                .withTitle("Source")
                .withAuthorId(2L)
                .withBody("<p>Body</p>")
                .withCommentsDisabled(true)
                .withContentTagIds(["c1"])
                .withDraft(false)
                .withLabelNames(["l1"])
                .withPosition(3L)
                .withPromoted(true)
                .withSectionId(4L)
                .withSourceLocale("en-us")
                .withUserSegmentId(5L)
                .withUserSegmentIds(["seg1"])

        when:
        ArticleUpdateInput fromNull = ArticleUpdateInput.from(null)
        ArticleUpdateInput fromArticle = ArticleUpdateInput.from(sourceArticle)
        ArticleUpdateInput viaCtor = new ArticleUpdateInput(
                LocaleAbbreviation.FRENCH, 10L, "Ctor", 20L, "B", false, ["ct"], true, ["ln"], 1L, false, 30L, "fr", 40L, ["us"]
        )
        ArticleUpdateInput withers = new ArticleUpdateInput()
                .withLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .withPermissionGroupId(10L)
                .withTitle("T")
                .withAuthorId(20L)
                .withBody("B")
                .withCommentsDisabled(false)
                .addContentTagIdsItem("ct1")
                .addContentTagIdsItem("ct2")
                .withDraft(true)
                .addLabelNamesItem("lbl1")
                .addLabelNamesItem("lbl2")
                .withPosition(7L)
                .withPromoted(false)
                .withSectionId(8L)
                .withSourceLocale("en-us")
                .withUserSegmentId(9L)
                .addUserSegmentIdsItem("us1")
                .addUserSegmentIdsItem("us2")

        ArticleUpdateInput clearedAll = withers
                .clearLocaleAbbreviation()
                .clearPermissionGroupId()
                .clearTitle()
                .clearAuthorId()
                .clearBody()
                .clearCommentsDisabled()
                .clearContentTagIds()
                .clearDraft()
                .clearLabelNames()
                .clearPosition()
                .clearPromoted()
                .clearSectionId()
                .clearSourceLocale()
                .clearUserSegmentId()
                .clearUserSegmentIds()

        ArticleUpdateInput builtSet = ArticleUpdateInput.builder()
                .localeAbbreviation(LocaleAbbreviation.FRENCH)
                .permissionGroupId(1L)
                .title("Built")
                .authorId(2L)
                .body("Body")
                .commentsDisabled(true)
                .contentTagIds(["c"])
                .draft(false)
                .labelNames(["l"])
                .position(2L)
                .promoted(true)
                .sectionId(3L)
                .sourceLocale("fr")
                .userSegmentId(4L)
                .userSegmentIds(["u"])
                .build()

        ArticleUpdateInput builtCleared = ArticleUpdateInput.builder()
                .clearLocaleAbbreviation()
                .clearPermissionGroupId()
                .clearTitle()
                .clearAuthorId()
                .clearBody()
                .clearCommentsDisabled()
                .clearContentTagIds()
                .clearDraft()
                .clearLabelNames()
                .clearPosition()
                .clearPromoted()
                .clearSectionId()
                .clearSourceLocale()
                .clearUserSegmentId()
                .clearUserSegmentIds()
                .build()

        Map clearedMap = objectMapper.readValue(objectMapper.writeValueAsString(clearedAll), Map)
        Map builtClearedMap = objectMapper.readValue(objectMapper.writeValueAsString(builtCleared), Map)

        then:
        verifyAll {
            fromNull == null
            fromArticle.title() == "Source"
            fromArticle.sectionId() == 4L
            viaCtor.title() == "Ctor"
            withers.contentTagIds() == ["ct1", "ct2"]
            withers.labelNames() == ["lbl1", "lbl2"]
            withers.userSegmentIds() == ["us1", "us2"]
            builtSet.title() == "Built"
            builtSet.localeAbbreviation() == LocaleAbbreviation.FRENCH
            clearedMap.size() == 15
            clearedMap.values().every { it == null }
            builtClearedMap.size() == 15
            builtClearedMap.values().every { it == null }
        }
    }

    def "TicketUpdateInput and User cover remaining adders, withers, clearers, and map scalar types"() {
        given:
        TicketUpdateInput fullTicket = new TicketUpdateInput(
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null
        )
                .addAdditionalCollaboratorsItem(new Collaborator("collab@example.com", "Collab"))
                .addAdditionalTagsItem("t1")
                .addAttributeValueIdsItem(1L)
                .addCollaboratorIdsItem(2L)
                .addCustomFieldsItem(new TicketCustomField.Text(10L, "v"))
                .addEmailCcsItem(new EmailCC(EmailCCAllOfAction.PUT, "cc@example.com", 1L, "CC"))
                .addFollowersItem(new Follower(EmailCCAllOfAction.PUT, "f@example.com", "2"))
                .addSharingAgreementIdsItem(3L)
                .addRemoveTagsItem("old")
                .withAssigneeEmail("a@b.com")
                .withExternalId("ext-1")
                .withPriority(TicketUpdateInputPriority.HIGH)
                .withTicketFormId(5L)
                .withType(TicketUpdateInputType.INCIDENT)
                .clearAssigneeEmail()
                .clearCollaboratorIds()
                .clearCustomFields()
                .clearEmailCcs()
                .clearExternalId()
                .clearFollowers()
                .clearPriority()
                .clearTags()
                .clearTicketFormId()
                .clearType()

        User fullUser = new User(
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null
        )
                .withAlias("Al")
                .withCustomRoleId(1L)
                .withDetails("D")
                .withExternalId("E")
                .withLocale("en-us")
                .withLocaleId(1L)
                .withPhoto(new Attachment().withId(9L))
                .withRemotePhotoUrl("https://img.example.com/a.png")
                .withSignature("Sig")
                .withTicketRestriction("assigned")
                .withTimeZone("UTC")
                .withUserFields([count: 5, weight: 2.5d])
                .clearAlias()
                .clearCustomRoleId()
                .clearDetails()
                .clearExternalId()
                .clearLocale()
                .clearLocaleId()
                .clearPhoto()
                .clearRemotePhotoUrl()
                .clearSignature()
                .clearTicketRestriction()
                .clearTimeZone()
                .clearUserFields()

        User mapUser = new User().withUserFields([count: 5, ratio: 1.5d])

        when:
        Map ticketMap = objectMapper.readValue(objectMapper.writeValueAsString(fullTicket), Map)
        Map fullUserMap = objectMapper.readValue(objectMapper.writeValueAsString(fullUser), Map)
        Map mapUserJson = objectMapper.readValue(objectMapper.writeValueAsString(mapUser), Map)

        then:
        verifyAll(ticketMap) {
            containsKey("assignee_email") && get("assignee_email") == null
            containsKey("collaborator_ids") && get("collaborator_ids") == null
            containsKey("custom_fields") && get("custom_fields") == null
            containsKey("email_ccs") && get("email_ccs") == null
            containsKey("external_id") && get("external_id") == null
            containsKey("followers") && get("followers") == null
            containsKey("priority") && get("priority") == null
            containsKey("tags") && get("tags") == null
            containsKey("ticket_form_id") && get("ticket_form_id") == null
            containsKey("type") && get("type") == null
            (get("additional_tags") as List) == ["t1"]
            (get("remove_tags") as List) == ["old"]
        }
        verifyAll(fullUserMap) {
            containsKey("user_fields") && get("user_fields") == null
            containsKey("alias") && get("alias") == null
            containsKey("custom_role_id") && get("custom_role_id") == null
            containsKey("details") && get("details") == null
            containsKey("external_id") && get("external_id") == null
            containsKey("locale") && get("locale") == null
            containsKey("locale_id") && get("locale_id") == null
            containsKey("photo") && get("photo") == null
            containsKey("remote_photo_url") && get("remote_photo_url") == null
            containsKey("signature") && get("signature") == null
            containsKey("ticket_restriction") && get("ticket_restriction") == null
            containsKey("time_zone") && get("time_zone") == null
        }
        verifyAll(mapUserJson.get("user_fields") as Map) {
            get("count") == 5
            get("ratio") == 1.5d
        }
    }

    def "custom partial update serializers encode null root values cleanly"() {
        given:
        Encoder encoder = Mock(Encoder)
        Serializer.EncoderContext encoderContext = Stub(Serializer.EncoderContext)

        when:
        ctx.getBean(ArticleUpdateInputSerializer).serialize(encoder, encoderContext, Argument.of(ArticleUpdateInput), null)
        ctx.getBean(ArticleUpdateRequestSerializer).serialize(encoder, encoderContext, Argument.of(ArticleUpdateRequest), null)
        ctx.getBean(TicketUpdateInputSerializer).serialize(encoder, encoderContext, Argument.of(TicketUpdateInput), null)
        ctx.getBean(UserSerializer).serialize(encoder, encoderContext, Argument.of(User), null)

        then:
        4 * encoder.encodeNull()
    }
}
