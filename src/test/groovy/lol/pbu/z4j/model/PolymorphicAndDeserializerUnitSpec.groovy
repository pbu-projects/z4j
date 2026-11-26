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

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.json.tree.JsonNode
import io.micronaut.serde.Decoder
import io.micronaut.serde.ObjectMapper
import io.micronaut.serde.exceptions.SerdeException
import java.time.ZoneOffset
import java.time.ZonedDateTime
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class PolymorphicAndDeserializerUnitSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext context

    @Shared
    ObjectMapper contextMapper

    @Shared
    @AutoCleanup
    ObjectMapper.CloseableObjectMapper packageScopedMapper = ObjectMapper.create(Map.of(), "lol.pbu.z4j.model")

    void setupSpec() {
        context = ApplicationContext.run()
        contextMapper = context.getBean(ObjectMapper)
    }

    def "Exportable deserializes polymorphic results by result_type and by field shape"() {
        given:
        String exportJson = '''{
          "results": [
            {
              "id": 1,
              "requester_id": 10,
              "subject": "Ticket A",
              "custom_fields": [
                {"id": 101, "value": "vip"},
                {"id": 102, "value": 3.14},
                {"id": 103, "value": ["alpha", "beta"]}
              ],
              "result_type": "ticket"
            },
            {"id": 2, "name": "Alice", "email": "alice@example.com", "role": "agent", "result_type": "user"},
            {"id": 3, "result_type": "organization"},
            {"id": 4, "name": "Support", "description": "Tier 1", "default": true, "deleted": false, "is_public": true, "result_type": "group"},
            {
              "id": 5,
              "ticket_id": 1,
              "event_type": "Audit",
              "audit_events": [
                {
                  "id": 501,
                  "type": "AutomaticAnswerSend",
                  "body": [{"article": {"title": "Help Guide"}}],
                  "recipients": ["101", 102]
                }
              ],
              "result_type": "ticket_event"
            }
          ]
        }'''
        String inferredJson = '{"id": 9, "name": "Ops", "default": false, "deleted": false}'

        when:
        ExportResponse<Exportable> fromContext = contextMapper.readValue(exportJson, Argument.of(ExportResponse, Exportable))
        ExportResponse<Exportable> fromScoped = packageScopedMapper.readValue(exportJson, Argument.of(ExportResponse, Exportable))
        Exportable inferred = contextMapper.readValue(inferredJson, Exportable)

        then:
        [fromContext, fromScoped].each { resp ->
            verifyAll(resp) {
                results().size() == 5
                results()[0] instanceof Ticket
                (results()[0] as Ticket).subject() == "Ticket A"
                (results()[0] as Ticket).customFields().size() == 3
                (results()[0] as Ticket).customFields()[0] instanceof TicketCustomField.Text
                (results()[0] as Ticket).customFields()[0].id() == 101L
                (results()[0] as Ticket).customFields()[0].value() == "vip"
                (results()[0] as Ticket).customFields()[1] instanceof TicketCustomField.Decimal
                (results()[0] as Ticket).customFields()[1].value() == 3.14d
                (results()[0] as Ticket).customFields()[2] instanceof TicketCustomField.TagList
                (results()[0] as Ticket).customFields()[2].value() == ["alpha", "beta"]
                results()[1] instanceof User
                (results()[1] as User).email() == "alice@example.com"
                results()[2] instanceof Organization
                (results()[2] as Organization).id() == 3L
                results()[3] instanceof Group
                (results()[3] as Group).name() == "Support"
                (results()[3] as Group).isDefault() == true
                (results()[3] as Group).isPublic() == true
                results()[4] instanceof TicketEvent
                (results()[4] as TicketEvent).ticketId() == 1L
                (results()[4] as TicketEvent).auditEvents().size() == 1
                (results()[4] as TicketEvent).auditEvents()[0].id() == 501L
                (results()[4] as TicketEvent).auditEvents()[0].type() == "AutomaticAnswerSend"
                (results()[4] as TicketEvent).auditEvents()[0].body().contains("Help Guide")
                (results()[4] as TicketEvent).auditEvents()[0].recipients() == [101L, 102L]
            }
        }
        verifyAll(inferred) {
            it instanceof Group
            (it as Group).id() == 9L
            (it as Group).name() == "Ops"
            (it as Group).isDefault() == false
        }
    }

    def "Exportable classifies Organization with name or org-specific fields as Organization instead of Group"() {
        given:
        String orgWithNameJson = '{"id":123,"name":"Acme Corp"}'
        String orgWithFieldsJson = '{"id":456,"name":"Beta Org","domain_names":["beta.example"],"shared_tickets":true}'

        when:
        Exportable orgWithName = contextMapper.readValue(orgWithNameJson, Exportable)
        Exportable orgWithFields = packageScopedMapper.readValue(orgWithFieldsJson, Exportable)

        then:
        verifyAll {
            orgWithName instanceof Organization
            (orgWithName as Organization).id() == 123L
            orgWithFields instanceof Organization
            (orgWithFields as Organization).id() == 456L
        }
    }

    def "SearchResultsInner infers Article when title and body are present without result_type"() {
        given:
        String articleJson = '{"id":1,"title":"My Article","body":"<p>Hello</p>"}'

        when:
        SearchResultsInner fromContext = contextMapper.readValue(articleJson, SearchResultsInner)
        SearchResultsInner fromScoped = packageScopedMapper.readValue(articleJson, SearchResultsInner)

        then:
        [fromContext, fromScoped].each { result ->
            verifyAll(result) {
                it instanceof Article
                (it as Article).id() == 1L
                (it as Article).title() == "My Article"
                (it as Article).body() == "<p>Hello</p>"
            }
        }
    }

    def "SearchResultsInner deserializes Article, Post, and PostComment in Search"() {
        given:
        String json = '''{
          "results": [
            {"id": 10, "permission_group_id": 1, "section_id": 5, "title": "Help Article", "result_type": "article"},
            {"id": 20, "title": "Community Idea", "topic_id": 7, "result_type": "post"},
            {"id": 30, "body": "Nice idea!", "post_id": 20, "official": true, "result_type": "comment"}
          ]
        }'''
        Search search = new Search([
                new Post("Search Hit").withId(555L),
                new PostComment("Reply").withId(556L).withPostId(555L)
        ])

        when:
        Search deserializedFromContext = contextMapper.readValue(json, Search)
        Search deserializedFromScoped = packageScopedMapper.readValue(json, Search)
        String serialized = contextMapper.writeValueAsString(search)
        Search roundTripped = contextMapper.readValue(serialized, Search)

        then:
        [deserializedFromContext, deserializedFromScoped].each { res ->
            verifyAll(res) {
                results().size() == 3
                results()[0] instanceof Article
                (results()[0] as Article).title() == "Help Article"
                results()[1] instanceof Post
                (results()[1] as Post).topicId() == 7L
                results()[2] instanceof PostComment
                (results()[2] as PostComment).official() == true
            }
        }
        verifyAll(roundTripped) {
            results().size() == 2
            results()[0] instanceof Post
            (results()[0] as Post).title() == "Search Hit"
            results()[1] instanceof PostComment
            (results()[1] as PostComment).body() == "Reply"
        }
    }

    def "Exportable and SearchResultsInner reject unknown result_type and non-objects"() {
        when:
        contextMapper.readValue('{"result_type":"macro"}', Exportable)

        then:
        thrown(SerdeException)

        when:
        contextMapper.readValue('{"result_type":"unknown"}', SearchResultsInner)

        then:
        thrown(SerdeException)

        when:
        contextMapper.readValue('"not-an-object"', Exportable)

        then:
        thrown(SerdeException)
    }

    def "Exportable rejects non-string result_type"() {
        when:
        contextMapper.readValue('{"result_type":{}}', Exportable)

        then:
        thrown(SerdeException)
    }

    def "Group populates Zendesk Group schema fields and round-trips"() {
        given:
        ZonedDateTime created = ZonedDateTime.of(2026, 1, 10, 12, 0, 0, 0, ZoneOffset.UTC)
        ZonedDateTime updated = ZonedDateTime.of(2026, 1, 11, 12, 0, 0, 0, ZoneOffset.UTC)
        Group group = GroupBuilder.builder()
                .id(77L)
                .url("https://example.zendesk.com/api/v2/groups/77.json")
                .name("Billing")
                .description("Handles invoices")
                .isDefault(true)
                .deleted(false)
                .isPublic(true)
                .createdAt(created)
                .updatedAt(updated)
                .build()

        when:
        String json = contextMapper.writeValueAsString(group)
        Map map = contextMapper.readValue(json, Map)
        Group roundTripped = contextMapper.readValue(json, Group)

        then:
        verifyAll(map) {
            get("id") == 77
            get("url") == "https://example.zendesk.com/api/v2/groups/77.json"
            get("name") == "Billing"
            get("description") == "Handles invoices"
            get("default") == true
            get("deleted") == false
            get("is_public") == true
        }
        verifyAll(roundTripped) {
            it == group
            id() == 77L
            name() == "Billing"
            isDefault() == true
            deleted() == false
            isPublic() == true
        }
    }

    def "GuideMediaUploadUrlResponseDeserializer handles nulls and JSON object headers"() {
        expect:
        [contextMapper, packageScopedMapper].each { mapper ->
            GuideMediaUploadUrlResponse nullResp = mapper.readValue("null", GuideMediaUploadUrlResponse)
            GuideMediaUploadUrlResponse nullFields = mapper.readValue(
                    '{"url":null,"asset_upload_id":null,"headers":null}',
                    GuideMediaUploadUrlResponse
            )
            GuideMediaUploadUrlResponse objectHeaders = mapper.readValue(
                    '{"url":"https://upload.example.com","asset_upload_id":"01ABC","headers":{"Content-Type":"image/png","x-amz-acl":"private"}}',
                    GuideMediaUploadUrlResponse
            )
            GuideMediaUploadUrlResponse nestedObjectHeaders = mapper.readValue(
                    '{"upload_url":{"url":"https://nested.example.com","asset_upload_id":"01XYZ","headers":{"Content-Type":"image/jpeg"}}}',
                    GuideMediaUploadUrlResponse
            )

            verifyAll {
                nullResp == null
                nullFields.uploadUrl() == null
                nullFields.assetUploadId() == null
                nullFields.headers() == null

                objectHeaders.uploadUrl() == "https://upload.example.com"
                objectHeaders.assetUploadId() == "01ABC"
                objectHeaders.headers().contains('"Content-Type":"image/png"')
                objectHeaders.headers().contains('"x-amz-acl":"private"')

                nestedObjectHeaders.uploadUrl() == "https://nested.example.com"
                nestedObjectHeaders.assetUploadId() == "01XYZ"
                nestedObjectHeaders.headers() == '{"Content-Type":"image/jpeg"}'
            }
        }
    }

    def "SearchResultsInner treats explicit null body as absent and resolves Post"() {
        given:
        String json = '{"id":1,"title":"Post","body":null}'

        when:
        SearchResultsInner fromContext = contextMapper.readValue(json, SearchResultsInner)
        SearchResultsInner fromScoped = packageScopedMapper.readValue(json, SearchResultsInner)

        then:
        [fromContext, fromScoped].each { result ->
            verifyAll(result) {
                it instanceof Post
                (it as Post).id() == 1L
                (it as Post).title() == "Post"
            }
        }
    }

    def "JsonNodeTreeDecoder throws SerdeException for empty and multi-element array scalars"() {
        given:
        Decoder emptyArrayDecoder = JsonNodeTreeDecoder.of(JsonNode.createArrayNode([]))
        Decoder multiArrayDecoder = JsonNodeTreeDecoder.of(JsonNode.createArrayNode([
                JsonNode.createStringNode("first"),
                JsonNode.createStringNode("second")
        ]))

        when:
        emptyArrayDecoder.decodeString()

        then:
        SerdeException emptyEx = thrown(SerdeException)
        emptyEx.suppressed.length == 0

        when:
        multiArrayDecoder.decodeString()

        then:
        SerdeException multiEx = thrown(SerdeException)
        multiEx.suppressed.length == 0
    }

    def "JsonNodeTreeDecoder handles null root and stops emitting keys after finishStructure(true)"() {
        given:
        Decoder nullDecoder = JsonNodeTreeDecoder.of(null)
        JsonNode obj = JsonNode.createObjectNode([
                first: JsonNode.createStringNode("1"),
                second: JsonNode.createStringNode("2")
        ])

        when:
        boolean isNull = nullDecoder.decodeNull()
        Decoder objectDecoder = JsonNodeTreeDecoder.of(obj).decodeObject(Argument.of(Object))
        String firstKey = objectDecoder.decodeKey()
        String firstValue = objectDecoder.decodeString()
        objectDecoder.finishStructure(true)
        String afterFinish = objectDecoder.decodeKey()
        objectDecoder.close()

        then:
        verifyAll {
            isNull
            firstKey == "first"
            firstValue == "1"
            afterFinish == null
        }
    }

    def "AuditEventsInnerDeserializer throws SerdeException when type, field_name, or subject is an object or array"() {
        when:
        contextMapper.readValue(json, AuditEventsInner)

        then:
        thrown(SerdeException)

        where:
        json << [
                '{"id":1,"type":{"nested":true}}',
                '{"id":1,"field_name":{"nested":true}}',
                '{"id":1,"subject":{"nested":true}}',
                '{"id":1,"type":["a"]}',
                '{"id":1,"field_name":["a"]}',
                '{"id":1,"subject":["a"]}'
        ]
    }

    def "SealedTicketCustomFieldDeserializer handles null id, mixed lists, and Double decimals"() {
        when:
        TicketCustomField nullField = contextMapper.readValue("null", TicketCustomField)
        TicketCustomField nullId = contextMapper.readValue('{"id":null,"value":"hello"}', TicketCustomField)
        TicketCustomField decimalField = contextMapper.readValue('{"id":42,"value":123456.78901234}', TicketCustomField)
        TicketCustomField mixedList = contextMapper.readValue('{"id":43,"value":["ok", 123]}', TicketCustomField)
        TicketCustomField tagList = contextMapper.readValue('{"id":44,"value":["vip","tier1"]}', TicketCustomField)

        then:
        verifyAll {
            nullField == null
            nullId instanceof TicketCustomField.Text
            nullId.id() == null
            nullId.value() == "hello"

            decimalField instanceof TicketCustomField.Decimal
            decimalField.id() == 42L
            decimalField.value() instanceof Double
            decimalField.value() == 123456.78901234d

            mixedList instanceof TicketCustomField.Raw
            mixedList.id() == 43L
            mixedList.value() == ["ok", 123]

            tagList instanceof TicketCustomField.TagList
            tagList.id() == 44L
            tagList.value() == ["vip", "tier1"]
        }
    }
}
