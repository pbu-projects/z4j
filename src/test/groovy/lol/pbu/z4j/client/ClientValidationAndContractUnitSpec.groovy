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
package lol.pbu.z4j.client

import com.sun.net.httpserver.HttpServer
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.client.multipart.MultipartBody
import jakarta.validation.ConstraintViolationException
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import lol.pbu.z4j.IncrementalService
import lol.pbu.z4j.SearchService
import lol.pbu.z4j.model.ExportResponse
import lol.pbu.z4j.model.IncrementalOrganizationTimeResponse
import lol.pbu.z4j.model.IncrementalTicketCursorResponse
import lol.pbu.z4j.model.IncrementalTicketEventTimeResponse
import lol.pbu.z4j.model.IncrementalUserCursorResponse
import lol.pbu.z4j.model.LocaleAbbreviation
import lol.pbu.z4j.model.Meta
import lol.pbu.z4j.model.Organization
import lol.pbu.z4j.model.Ticket
import lol.pbu.z4j.model.TicketEvent
import lol.pbu.z4j.model.User
import reactor.core.publisher.Mono
import org.spockframework.runtime.model.parallel.ExecutionMode
import spock.lang.AutoCleanup
import spock.lang.Execution
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Execution(ExecutionMode.SAME_THREAD)
class ClientValidationAndContractUnitSpec extends Specification {

    @Shared
    HttpServer server

    @Shared
    String capturedMethod

    @Shared
    String capturedPath

    @Shared
    String capturedQuery

    @Shared
    String capturedContentType

    @Shared
    String capturedAccept

    @Shared
    String capturedBody

    @Shared
    @AutoCleanup
    ApplicationContext ctx

    @Shared
    CustomObjectsClient customObjectsClient

    @Shared
    CustomObjectRecordsClient customObjectRecordsClient

    @Shared
    JobStatusClient jobStatusClient

    @Shared
    TranslationClient translationClient

    @Shared
    ContentTagClient contentTagClient

    @Shared
    GuideMediaClient guideMediaClient

    @Shared
    RedirectRuleClient redirectRuleClient

    @Shared
    AttachmentClient attachmentClient

    @Shared
    ArticleAttachmentClient articleAttachmentClient

    @Shared
    CustomStatusClient customStatusClient

    void setupSpec() {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/") { exchange ->
            capturedMethod = exchange.requestMethod
            capturedPath = exchange.requestURI.path
            capturedQuery = exchange.requestURI.rawQuery
            capturedContentType = exchange.requestHeaders.getFirst(HttpHeaders.CONTENT_TYPE)
            capturedAccept = exchange.requestHeaders.getFirst(HttpHeaders.ACCEPT)
            capturedBody = new String(exchange.requestBody.readAllBytes(), StandardCharsets.UTF_8)

            String responseJson = "{}"
            int status = 200
            if (capturedPath.startsWith("/api/v2/uploads") && capturedMethod == "POST") {
                status = 201
                responseJson = '{"upload":{"token":"tok-1","attachment":{"id":10,"file_name":"a.txt","size":4}}}'
            } else if (capturedPath.startsWith("/api/v2/uploads") && capturedMethod == "DELETE") {
                status = 204
                responseJson = ""
            } else if (capturedPath.contains("/articles/") && capturedPath.endsWith("/attachments") && capturedMethod == "POST") {
                status = 201
                responseJson = '{"article_attachment":{"id":20,"article_id":55,"file_name":"img.png","inline":true}}'
            } else if (capturedPath.endsWith("/custom_statuses.json")) {
                responseJson = '{"custom_statuses":[],"count":0}'
            } else if (capturedPath.endsWith("/ticket_form_statuses.json")) {
                responseJson = '{"ticket_form_statuses":[],"count":0}'
            }

            byte[] bytes = responseJson.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(status, bytes.length == 0 ? -1 : bytes.length)
            if (bytes.length > 0) {
                exchange.responseBody.write(bytes)
            }
            exchange.close()
        }
        server.start()

        ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : "http://127.0.0.1:${server.address.port}",
                "micronaut.http.services.zendesk.oauth.token": "test-token"
        ])
        customObjectsClient = ctx.getBean(CustomObjectsClient)
        customObjectRecordsClient = ctx.getBean(CustomObjectRecordsClient)
        jobStatusClient = ctx.getBean(JobStatusClient)
        translationClient = ctx.getBean(TranslationClient)
        contentTagClient = ctx.getBean(ContentTagClient)
        guideMediaClient = ctx.getBean(GuideMediaClient)
        redirectRuleClient = ctx.getBean(RedirectRuleClient)
        attachmentClient = ctx.getBean(AttachmentClient)
        articleAttachmentClient = ctx.getBean(ArticleAttachmentClient)
        customStatusClient = ctx.getBean(CustomStatusClient)
    }

    void cleanupSpec() {
        server?.stop(0)
    }

    @Unroll
    def "rejects unsafe String path variables matching '#badValue'"(String badValue) {
        when:
        customObjectsClient.showCustomObject(badValue)
        then:
        thrown(ConstraintViolationException)

        when:
        customObjectRecordsClient.showCustomObjectRecord("valid_key", badValue)
        then:
        thrown(ConstraintViolationException)

        when:
        customObjectRecordsClient.listCustomObjectRecords(badValue)
        then:
        thrown(ConstraintViolationException)

        when:
        jobStatusClient.showJobStatus(badValue)
        then:
        thrown(ConstraintViolationException)

        when:
        translationClient.listTranslations(badValue, 1L)
        then:
        thrown(ConstraintViolationException)

        when:
        contentTagClient.showContentTag(badValue)
        then:
        thrown(ConstraintViolationException)

        when:
        guideMediaClient.showGuideMedia(badValue)
        then:
        thrown(ConstraintViolationException)

        when:
        redirectRuleClient.showRedirectRule(badValue)
        then:
        thrown(ConstraintViolationException)

        when:
        attachmentClient.deleteUpload(badValue)
        then:
        thrown(ConstraintViolationException)

        where:
        badValue << [
                "a/b",
                "..",
                "a..b",
                ".",
                "id?admin=true",
                "id#frag",
                "has space",
                "tab\there",
                "",
                "x" * 257
        ]
    }

    def "SearchService guards null query, null results, and does not inject duplicate type:ticket"() {
        given:
        SearchClient searchClient = Mock(SearchClient)
        SearchService service = new SearchService()
        service.searchClient = searchClient
        String capturedQueryArg = null

        when: "query is null"
        service.getTickets(null, 50)

        then:
        thrown(IllegalArgumentException)

        when: "query is valid and ExportResponse.results is null"
        searchClient.exportTicket(_, 50, null) >> { String q, Integer size, String cursor ->
            capturedQueryArg = q
            Mono.just(new ExportResponse<Ticket>(null, null, new Meta(false, null, null), null))
        }
        List<Ticket> results = service.getTickets("type:ticket status:open", 50).collectList().block()

        then:
        verifyAll {
            results == []
            capturedQueryArg == "status:open"
            !capturedQueryArg.contains("type:ticket")
        }
    }

    def "SearchService sends wildcard for empty query and forwards filter without type:ticket"() {
        given:
        SearchClient searchClient = Mock(SearchClient)
        SearchService service = new SearchService()
        service.searchClient = searchClient
        List<String> capturedQueries = []

        searchClient.exportTicket(_, 100, null) >> { String q, Integer size, String cursor ->
            capturedQueries << q
            Mono.just(new ExportResponse<Ticket>([], null, new Meta(false, null, null), null))
        }

        when:
        service.getTickets("").collectList().block()
        service.getTickets("status:open").collectList().block()

        then:
        verifyAll {
            capturedQueries == ["*", "status:open"]
            !capturedQueries[1].contains("type:ticket")
        }
    }

    def "SearchService rejects explicit type: filter, permits _type:, and paginates with cursor"() {
        given:
        SearchClient searchClient = Mock(SearchClient)
        SearchService service = new SearchService()
        service.searchClient = searchClient

        when: "query contains disallowed type: filter"
        service.getTickets("type:user status:open")

        then:
        thrown(IllegalArgumentException)

        when: "query uses custom _type: field and spans two pages"
        searchClient.exportTicket("custom_type:incident", 100, null) >> Mono.just(
                new ExportResponse<Ticket>([new Ticket().withId(1L)], null, new Meta(true, "cursor-123456", null), null)
        )
        searchClient.exportTicket("custom_type:incident", 100, "cursor-123456") >> Mono.just(
                new ExportResponse<Ticket>([new Ticket().withId(2L)], null, new Meta(false, null, null), null)
        )
        List<Ticket> tickets = service.getTickets("custom_type:incident").collectList().block()

        then:
        tickets*.id() == [1L, 2L]
    }

    def "IncrementalService validates perPage bounds and stops on stalled cursor"() {
        given:
        IncrementalClient incrementalClient = Mock(IncrementalClient)
        IncrementalService service = new IncrementalService()
        service.incrementalClient = incrementalClient

        when: "perPage is zero or negative"
        service.tickets().perPage(0)
        then:
        thrown(IllegalArgumentException)

        when:
        service.organizations().perPage(-5)
        then:
        thrown(IllegalArgumentException)

        when:
        service.tickets().perPage(1001)
        then:
        thrown(IllegalArgumentException)

        when: "cursor repeats on consecutive pages with endOfStream=false"
        incrementalClient.exportTickets(_, _, _) >>> [
                Mono.just(new IncrementalTicketCursorResponse([new Ticket().withId(1L)], "c1", "c1", null, false, 1)),
                Mono.just(new IncrementalTicketCursorResponse([new Ticket().withId(1L)], "c1", "c1", null, false, 1))
        ]
        service.tickets().since(Instant.now().minusSeconds(120)).flux().collectList().block()

        then:
        thrown(IllegalStateException)

        when: "cursor repeats on final page with endOfStream=true"
        IncrementalClient endClient = Mock(IncrementalClient)
        IncrementalService endService = new IncrementalService()
        endService.incrementalClient = endClient
        endClient.exportTickets(_, _, _) >>> [
                Mono.just(new IncrementalTicketCursorResponse([new Ticket().withId(1L)], "c1", "c1", null, false, 1)),
                Mono.just(new IncrementalTicketCursorResponse([new Ticket().withId(2L)], "c1", "c1", null, true, 1))
        ]
        List<Ticket> completed = endService.tickets().since(Instant.now().minusSeconds(120)).flux().collectList().block()

        then:
        completed*.id() == [1L, 2L]
    }

    def "IncrementalService covers cursor users, time builders, deduplication, and error guards"() {
        given:
        IncrementalClient client = Mock(IncrementalClient)
        IncrementalService service = new IncrementalService()
        service.incrementalClient = client
        long pastEpoch = Instant.now().minusSeconds(200).epochSecond

        when: "start time is less than 70s in the past"
        service.tickets().since(Instant.now().minusSeconds(10))
        then:
        thrown(IllegalArgumentException)

        when:
        service.organizations().since(Instant.now().minusSeconds(10))
        then:
        thrown(IllegalArgumentException)

        when: "time builder fetchPage or flux called without since()"
        service.organizations().fetchPage().block()
        then:
        thrown(IllegalArgumentException)

        when:
        service.organizations().flux().collectList().block()
        then:
        thrown(IllegalArgumentException)

        when: "user cursor export fetches single page and streams multiple pages with delay"
        client.exportUsers(pastEpoch, "start-cur", 25) >> Mono.just(
                new IncrementalUserCursorResponse([new User().withId(10L)], "c2", "c2", "c0", false, 1)
        )
        client.exportUsers(null, "c2", 25) >> Mono.just(
                new IncrementalUserCursorResponse([new User().withId(11L)], null, null, "c1", true, 1)
        )
        IncrementalService.IncrementalPage<User> singlePage = service.users()
                .since(pastEpoch)
                .after("start-cur")
                .perPage(25)
                .fetchPage()
                .block()
        List<User> streamedUsers = service.users()
                .since(pastEpoch)
                .after("start-cur")
                .perPage(25)
                .delayBetweenPages(Duration.ofMillis(1))
                .flux()
                .collectList()
                .block()

        then:
        verifyAll {
            singlePage.getResults()*.id() == [10L]
            singlePage.getAfterCursor() == "c2"
            singlePage.getBeforeCursor() == "c0"
            singlePage.getEndOfStream() == false
            singlePage.getCount() == 1
            streamedUsers*.id() == [10L, 11L]
        }

        when: "time-based organization export advances via endTime and nextPage URL and deduplicates"
        client.exportOrganizations(pastEpoch, 50) >> Mono.just(
                new IncrementalOrganizationTimeResponse([new Organization().withId(100L), new Organization().withId(101L)], pastEpoch + 10, null, false, 2)
        )
        client.exportOrganizations(pastEpoch + 10, 50) >> Mono.just(
                new IncrementalOrganizationTimeResponse([new Organization().withId(101L), new Organization()], pastEpoch + 10, "https://example.zendesk.com/api/v2/incremental/organizations.json?start_time=${pastEpoch + 20}", false, 2)
        )
        client.exportOrganizations(pastEpoch + 20, 50) >> Mono.just(
                new IncrementalOrganizationTimeResponse([new Organization().withId(102L)], pastEpoch + 30, null, true, 1)
        )
        IncrementalService.IncrementalTimePage<Organization> orgPage = service.organizations()
                .since(pastEpoch)
                .perPage(50)
                .fetchPage()
                .block()
        List<Organization> orgStream = service.organizations()
                .since(pastEpoch)
                .perPage(50)
                .delayBetweenPages(Duration.ofMillis(1))
                .flux()
                .collectList()
                .block()

        then:
        verifyAll {
            orgPage.getResults()*.id() == [100L, 101L]
            orgPage.getEndTime() == pastEpoch + 10
            orgPage.getEndOfStream() == false
            orgPage.getCount() == 2
            orgStream.size() == 4
            orgStream*.id() == [100L, 101L, null, 102L]
        }

        when: "time-based ticket events stream and stall when endTime and nextPage do not advance"
        client.exportTicketEvents(pastEpoch) >> Mono.just(
                new IncrementalTicketEventTimeResponse([new TicketEvent().withId(500L)], pastEpoch, null, false, 1)
        )
        service.ticketEvents().since(pastEpoch).flux().collectList().block()

        then:
        thrown(IllegalStateException)

        when: "unsupported resource types fail cleanly"
        new IncrementalService.CursorIncrementalBuilder<Organization>(IncrementalService.ResourceType.ORGANIZATION, client).fetchPage().block()
        then:
        thrown(IllegalStateException)

        when:
        new IncrementalService.TimeIncrementalBuilder<Ticket>(IncrementalService.ResourceType.TICKET, client).since(pastEpoch).fetchPage().block()
        then:
        thrown(IllegalStateException)

        expect: "helper methods handle all record types and URL shapes"
        verifyAll {
            IncrementalService.getRecordId(new Organization().withId(1L)) == 1L
            IncrementalService.getRecordId(new TicketEvent().withId(2L)) == 2L
            IncrementalService.getRecordId(new Ticket().withId(3L)) == 3L
            IncrementalService.getRecordId(new User().withId(4L)) == 4L
            IncrementalService.getRecordId("other") == null
            IncrementalService.extractStartTimeFromUrl(null) == null
            IncrementalService.extractStartTimeFromUrl("   ") == null
            IncrementalService.extractStartTimeFromUrl("https://example.com/path?foo=bar") == null
            IncrementalService.extractStartTimeFromUrl("https://example.com/path?start_time=notnum") == null
            IncrementalService.extractStartTimeFromUrl("not a valid uri ://") == null
            IncrementalService.extractStartTimeFromUrl("https://example.com/path?start_time=999") == 999L
        }
    }

    def "AttachmentClient uploads binary data with proper Content-Type and deletes upload by token"() {
        when:
        attachmentClient.uploadAttachment("pic.png", "image/png", [1, 2, 3, 4] as byte[]).block()
        String explicitCt = capturedContentType
        String explicitAccept = capturedAccept
        String uploadPath = capturedPath

        attachmentClient.uploadAttachment("raw.bin", null, null, [1, 2, 3, 4] as byte[]).block()
        String defaultCt = capturedContentType

        attachmentClient.deleteUpload("tok-1").block()
        String deleteMethod = capturedMethod
        String deletePath = capturedPath

        then:
        verifyAll {
            uploadPath == "/api/v2/uploads"
            explicitCt == MediaType.IMAGE_PNG
            explicitAccept.contains(MediaType.APPLICATION_JSON)
            defaultCt == MediaType.APPLICATION_OCTET_STREAM
            deleteMethod == "DELETE"
            deletePath == "/api/v2/uploads/tok-1"
        }
    }

    def "ArticleAttachmentClient posts to /api/v2/help_center/articles/{article_id}/attachments without locale in path"() {
        when:
        articleAttachmentClient.createArticleAttachment(
                LocaleAbbreviation.FRENCH,
                55L,
                "img.png",
                "data".bytes,
                true
        ).block()
        String path1 = capturedPath
        String ct1 = capturedContentType
        String body1 = capturedBody

        MultipartBody multipartBody = MultipartBody.builder()
                .addPart("file", "doc.txt", MediaType.TEXT_PLAIN_TYPE, "hi".bytes)
                .addPart("inline", "false")
                .build()
        articleAttachmentClient.createArticleAttachment(LocaleAbbreviation.ENGLISH_UNITED_STATES, 55L, multipartBody).block()
        String path2 = capturedPath

        then:
        verifyAll {
            path1 == "/api/v2/help_center/articles/55/attachments"
            ct1.startsWith(MediaType.MULTIPART_FORM_DATA)
            body1.contains('name="locale"')
            body1.contains("fr")
            path2 == "/api/v2/help_center/articles/55/attachments"
        }
    }

    def "CustomStatusClient encodes query parameters cleanly without RFC 6570 template mixing"() {
        when:
        customStatusClient.listCustomStatuses("open,pending", true).block()
        String statusPath = capturedPath
        String statusQuery = capturedQuery

        customStatusClient.listTicketFormStatuses(42L).block()
        String formPath = capturedPath
        String formQuery = capturedQuery

        then:
        verifyAll {
            statusPath == "/api/v2/custom_statuses.json"
            statusQuery.contains("status_categories=open%2Cpending") || statusQuery.contains("status_categories=open,pending")
            statusQuery.contains("active=true")
            statusQuery.count("active=") == 1

            formPath == "/api/v2/ticket_form_statuses.json"
            formQuery == "ticket_form_id=42"
        }
    }
}
