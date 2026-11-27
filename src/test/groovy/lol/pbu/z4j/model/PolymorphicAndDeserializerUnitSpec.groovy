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
import io.micronaut.serde.Deserializer
import io.micronaut.serde.Keys
import io.micronaut.serde.KeysAwareDecoder
import io.micronaut.serde.ObjectMapper
import io.micronaut.serde.SerdeRegistry
import io.micronaut.serde.exceptions.InvalidFormatException
import io.micronaut.serde.exceptions.NullValueSerdeException
import io.micronaut.serde.exceptions.SerdeException
import java.nio.charset.StandardCharsets
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

    def "ExportableDeserializer infers TicketEvent, Ticket, User and rejects unrecognized shapes and handles null"() {
        when:
        Exportable nullExportable = contextMapper.readValue("null", Exportable)
        Exportable ticketEvent = contextMapper.readValue('{"id": 10, "ticket_id": 1, "event_type": "Audit"}', Exportable)
        Exportable ticket = contextMapper.readValue('{"id": 11, "requester_id": 5, "subject": "Inferred"}', Exportable)
        Exportable user = contextMapper.readValue('{"id": 12, "name": "Bob", "email": "bob@example.com"}', Exportable)

        then:
        verifyAll {
            nullExportable == null
            ticketEvent instanceof TicketEvent
            (ticketEvent as TicketEvent).ticketId() == 1L
            ticket instanceof Ticket
            (ticket as Ticket).subject() == "Inferred"
            user instanceof User
            (user as User).email() == "bob@example.com"
        }

        when:
        contextMapper.readValue('{"unrecognized_only": 123}', Exportable)

        then:
        thrown(SerdeException)
    }

    def "SearchResultsInnerDeserializer infers Article, Post, PostComment by fields and rejects unknown shapes"() {
        when:
        SearchResultsInner nullResult = contextMapper.readValue("null", SearchResultsInner)
        SearchResultsInner articleBySection = contextMapper.readValue('{"id": 1, "section_id": 5}', SearchResultsInner)
        SearchResultsInner postByTopic = contextMapper.readValue('{"id": 2, "topic_id": 9}', SearchResultsInner)
        SearchResultsInner commentByBody = contextMapper.readValue('{"id": 3, "body": "Only body"}', SearchResultsInner)

        then:
        verifyAll {
            nullResult == null
            articleBySection instanceof Article
            (articleBySection as Article).sectionId() == 5L
            postByTopic instanceof Post
            (postByTopic as Post).topicId() == 9L
            commentByBody instanceof PostComment
            (commentByBody as PostComment).body() == "Only body"
        }

        when:
        contextMapper.readValue('{"unrecognized_only": true}', SearchResultsInner)

        then:
        thrown(SerdeException)
    }

    def "PolymorphicSerdeSupport handles null nodes and decoders"() {
        given:
        Deserializer.DecoderContext decoderContext = context.getBean(SerdeRegistry).newDecoderContext(null)

        expect:
        verifyAll {
            PolymorphicSerdeSupport.decodeObjectNode(JsonNodeTreeDecoder.of(null), "Test") == null
            PolymorphicSerdeSupport.deserialize(JsonNodeTreeDecoder.of(null), decoderContext, "Test", { Ticket.class }) == null
            PolymorphicSerdeSupport.deserializeTarget(null, decoderContext, Ticket.class) == null
            PolymorphicSerdeSupport.deserializeTarget(JsonNode.nullNode(), decoderContext, Ticket.class) == null
        }
    }

    def "AuditEventsInnerDeserializer handles null id and scalar non-string fields"() {
        when:
        AuditEventsInner missingId = contextMapper.readValue('{"type": 123, "field_name": true, "subject": 456}', AuditEventsInner)
        AuditEventsInner nullId = contextMapper.readValue('{"id": null, "type": "Notice"}', AuditEventsInner)

        then:
        verifyAll {
            missingId.id() == null
            missingId.type() == "123"
            missingId.fieldName() == "true"
            missingId.subject() == "456"
            nullId.id() == null
            nullId.type() == "Notice"
        }
    }

    def "GuideMediaUploadUrlResponseDeserializer handles flat upload_url and null fallback"() {
        given:
        GuideMediaUploadUrlResponseDeserializer deserializer = context.getBean(GuideMediaUploadUrlResponseDeserializer)
        Deserializer.DecoderContext decoderContext = context.getBean(SerdeRegistry).newDecoderContext(null)

        when:
        GuideMediaUploadUrlResponse flatOverride = contextMapper.readValue(
                '{"url":"https://old.example.com","upload_url":"https://new.example.com"}',
                GuideMediaUploadUrlResponse
        )
        GuideMediaUploadUrlResponse nullUploadUrlFallback = contextMapper.readValue(
                '{"url":"https://keep.example.com","upload_url":null}',
                GuideMediaUploadUrlResponse
        )
        GuideMediaUploadUrlResponse nullUploadUrlAlone = contextMapper.readValue(
                '{"upload_url":null,"asset_upload_id":"id-1"}',
                GuideMediaUploadUrlResponse
        )

        then:
        verifyAll {
            flatOverride.uploadUrl() == "https://new.example.com"
            nullUploadUrlFallback.uploadUrl() == "https://keep.example.com"
            nullUploadUrlAlone.uploadUrl() == null
            nullUploadUrlAlone.assetUploadId() == "id-1"
            deserializer.getDefaultValue(decoderContext, Argument.of(GuideMediaUploadUrlResponse)) == null
        }
    }

    def "Group constructors create empty and named instances"() {
        when:
        Group empty = new Group()
        Group named = new Group("Escalations")

        then:
        verifyAll {
            empty.name() == null
            empty.id() == null
            named.name() == "Escalations"
            named.id() == null
        }
    }

    def "JsonNodeTreeDecoder decodes all scalar types, arrays, binary, buffers, and JSON escaping"() {
        given:
        byte[] rawBytes = "hello".getBytes(StandardCharsets.UTF_8)
        String b64 = Base64.encoder.encodeToString(rawBytes)
        JsonNode byteArrayNode = JsonNode.createArrayNode(rawBytes.collect { JsonNode.createNumberNode((int) it) })
        String specialChars = "\"quote\"\\slash\b\f\n\r\t\u0001"
        JsonNode scalarMap = JsonNode.createObjectNode([
                b: JsonNode.createNumberNode(7),
                s: JsonNode.createNumberNode(320),
                c: JsonNode.createNumberNode(65),
                i: JsonNode.createNumberNode(42),
                l: JsonNode.createNumberNode(123456789012L),
                f: JsonNode.createNumberNode(1.5f),
                d: JsonNode.createNumberNode(2.25d),
                bi: JsonNode.createNumberNode(BigInteger.valueOf(999999999999L)),
                bd: JsonNode.createNumberNode(new BigDecimal("12.345")),
                num: JsonNode.createNumberNode(88),
                bool: JsonNode.createBooleanNode(true),
                str: JsonNode.createStringNode("abc"),
                singleArr: JsonNode.createArrayNode([JsonNode.createNumberNode(55)])
        ])

        when:
        Decoder obj = JsonNodeTreeDecoder.of(scalarMap).decodeObject(Argument.of(Object))
        Map decoded = [:]
        while (true) {
            String key = obj.decodeKey()
            if (key == null) break
            switch (key) {
                case "b": decoded.b = obj.decodeByte(); break
                case "s": decoded.s = obj.decodeShort(); break
                case "c": decoded.c = obj.decodeChar(); break
                case "i": decoded.i = obj.decodeInt(); break
                case "l": decoded.l = obj.decodeLong(); break
                case "f": decoded.f = obj.decodeFloat(); break
                case "d": decoded.d = obj.decodeDouble(); break
                case "bi": decoded.bi = obj.decodeBigInteger(); break
                case "bd": decoded.bd = obj.decodeBigDecimal(); break
                case "num": decoded.num = obj.decodeNumber(); break
                case "bool": decoded.bool = obj.decodeBoolean(); break
                case "str": decoded.str = obj.decodeString(); break
                case "singleArr": decoded.singleArr = obj.decodeInt(); break
            }
        }
        obj.finishStructure(false)
        obj.close()

        byte[] fromB64 = JsonNodeTreeDecoder.of(JsonNode.createStringNode(b64)).decodeBinary()
        byte[] fromArr = JsonNodeTreeDecoder.of(byteArrayNode).decodeBinary()
        Decoder buffered = JsonNodeTreeDecoder.of(JsonNode.createStringNode("buf")).decodeBuffer()
        String bufferedVal = buffered.decodeString()
        String escapedJson = JsonNodeTreeDecoder.toJson(JsonNode.createStringNode(specialChars))
        Object nullArb = JsonNodeTreeDecoder.toArbitrary(null)
        String nullJson = JsonNodeTreeDecoder.toJson(null)

        then:
        verifyAll(decoded) {
            b == (byte) 7
            s == (short) 320
            c == (char) 65
            i == 42
            l == 123456789012L
            f == 1.5f
            d == 2.25d
            bi == BigInteger.valueOf(999999999999L)
            bd == new BigDecimal("12.345")
            num == 88
            bool == true
            str == "abc"
            singleArr == 55
        }
        verifyAll {
            fromB64 == rawBytes
            fromArr == rawBytes
            bufferedVal == "buf"
            escapedJson == '"\\"quote\\"\\\\slash\\b\\f\\n\\r\\t\\u0001"'
            nullArb == null
            nullJson == "null"
        }
    }

    def "JsonNodeTreeDecoder enforces structure and error contracts on Root, Array, and Object decoders"() {
        when: "decodeArray or decodeObject called on wrong type"
        JsonNodeTreeDecoder.of(JsonNode.createStringNode("x")).decodeArray(Argument.listOf(String))
        then:
        thrown(InvalidFormatException)

        when:
        JsonNodeTreeDecoder.of(JsonNode.createStringNode("x")).decodeObject(Argument.of(Object))
        then:
        thrown(InvalidFormatException)

        when: "scalar decoder called on null or wrong scalar type or invalid single-element array"
        JsonNodeTreeDecoder.of(JsonNode.nullNode()).decodeInt()
        then:
        thrown(NullValueSerdeException)

        when:
        JsonNodeTreeDecoder.of(JsonNode.createStringNode("not-num")).decodeInt()
        then:
        thrown(InvalidFormatException)

        when:
        JsonNodeTreeDecoder.of(JsonNode.createArrayNode([JsonNode.createStringNode("bad")])).decodeInt()
        then:
        thrown(InvalidFormatException)

        when: "RootDecoder invalid operations and drained state"
        Decoder root = JsonNodeTreeDecoder.of(JsonNode.createStringNode("one"))
        boolean rootHasNext = root.hasNextArrayValue()
        root.skipValue()
        then:
        !rootHasNext

        when:
        root.decodeString()
        then:
        thrown(IllegalStateException)

        when:
        root.skipValue()
        then:
        thrown(IllegalStateException)

        when:
        JsonNodeTreeDecoder.of(JsonNode.createStringNode("x")).decodeKey()
        then:
        thrown(IllegalStateException)

        when:
        JsonNodeTreeDecoder.of(JsonNode.createStringNode("x")).finishStructure(false)
        then:
        thrown(IllegalStateException)

        when: "ArrayDecoder contract checks"
        Decoder arr = JsonNodeTreeDecoder.of(JsonNode.createArrayNode([JsonNode.createStringNode("a")])).decodeArray(Argument.listOf(String))
        arr.decodeKey()
        then:
        thrown(IllegalStateException)

        when:
        arr.finishStructure(false)
        then:
        thrown(IllegalStateException)

        when:
        String elem = arr.decodeString()
        arr.finishStructure(false)
        arr.decodeString()
        then:
        elem == "a"
        thrown(IllegalStateException)

        when: "ObjectDecoder and KeysAwareDecoder contract checks"
        Keys keys = Keys.create("known")
        KeysAwareDecoder keyed = JsonNodeTreeDecoder.of(JsonNode.createObjectNode([
                known: JsonNode.createStringNode("v1"),
                extra: JsonNode.createStringNode("v2")
        ])).decodeObject(Argument.of(Object)) as KeysAwareDecoder

        boolean objHasNext = keyed.hasNextArrayValue()
        int idxKnown = keyed.decodeKey(keys)
        String val1 = keyed.decodeString()
        int idxUnknown = keyed.decodeKey(keys)
        int idxUnknownAgain = keyed.decodeKey(keys)
        String extraKey = keyed.decodeKey()
        String val2 = keyed.decodeString()
        int idxEnd = keyed.decodeKey(keys)
        keyed.finishStructure(true)
        int idxAfterFinish = keyed.decodeKey(keys)

        then:
        verifyAll {
            !objHasNext
            idxKnown == 0
            val1 == "v1"
            idxUnknown == KeysAwareDecoder.MATCH_UNKNOWN_NAME
            idxUnknownAgain == KeysAwareDecoder.MATCH_UNKNOWN_NAME
            extraKey == "extra"
            val2 == "v2"
            idxEnd == KeysAwareDecoder.MATCH_END_OBJECT
            idxAfterFinish == KeysAwareDecoder.MATCH_END_OBJECT
        }

        when: "ObjectDecoder unconsumed / out-of-order errors"
        KeysAwareDecoder badObj = JsonNodeTreeDecoder.of(JsonNode.createObjectNode([
                a: JsonNode.createStringNode("1"),
                b: JsonNode.createStringNode("2")
        ])).decodeObject(Argument.of(Object)) as KeysAwareDecoder
        badObj.skipValue()
        then:
        thrown(IllegalStateException)

        when:
        badObj.decodeString()
        then:
        thrown(IllegalStateException)

        when:
        badObj.decodeKey()
        badObj.decodeKey()
        then:
        thrown(IllegalStateException)

        when:
        badObj.decodeKey(keys)
        then:
        thrown(IllegalStateException)

        when:
        badObj.finishStructure(false)
        then:
        thrown(IllegalStateException)
    }
}
