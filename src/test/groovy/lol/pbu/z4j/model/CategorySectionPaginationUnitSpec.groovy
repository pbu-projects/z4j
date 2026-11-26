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
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.serde.ObjectMapper
import java.net.InetSocketAddress
import java.net.URI
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import javax.tools.DiagnosticCollector
import javax.tools.JavaCompiler
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject
import javax.tools.ToolProvider
import lol.pbu.z4j.client.CategoryClient
import lol.pbu.z4j.client.SectionClient
import org.spockframework.runtime.model.parallel.ExecutionMode
import spock.lang.AutoCleanup
import spock.lang.Execution
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir

@Execution(ExecutionMode.SAME_THREAD)
class CategorySectionPaginationUnitSpec extends Specification {

    @Shared
    HttpServer server

    @Shared
    String capturedPath

    @Shared
    String capturedQuery

    @Shared
    @AutoCleanup
    ApplicationContext ctx

    @Shared
    ObjectMapper objectMapper

    @Shared
    CategoryClient categoryClient

    @Shared
    SectionClient sectionClient

    void setupSpec() {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/help_center") { exchange ->
            capturedPath = exchange.requestURI.path
            capturedQuery = exchange.requestURI.rawQuery
            String payload
            if (capturedPath.contains("categories") && !capturedPath.endsWith("/sections")) {
                payload = '{"categories":[{"id":1,"name":"Billing"}],"count":59,"page":2,"page_count":3,"next_page":"https://example.zendesk.com/api/v2/help_center/en-us/categories.json?page=3&per_page=25","previous_page":"https://example.zendesk.com/api/v2/help_center/en-us/categories.json?page=1&per_page=25"}'
            } else {
                payload = '{"sections":[{"id":10,"name":"Getting Started"}],"count":59,"page":2,"page_count":2,"next_page":null,"previous_page":"https://example.zendesk.com/api/v2/help_center/en-us/sections.json?page=1&per_page=30"}'
            }
            byte[] bytes = payload.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, bytes.length)
            exchange.responseBody.write(bytes)
            exchange.close()
        }
        server.start()

        ctx = ApplicationContext.run([
                "micronaut.http.services.zendesk.url"        : "http://127.0.0.1:${server.address.port}",
                "micronaut.http.services.zendesk.oauth.token": "test-token"
        ])
        objectMapper = ctx.getBean(ObjectMapper)
        categoryClient = ctx.getBean(CategoryClient)
        sectionClient = ctx.getBean(SectionClient)
    }

    void cleanupSpec() {
        server?.stop(0)
    }

    def "CategoriesResponse and SectionsResponse support offset pagination fields and backwards-compatible constructors"() {
        given:
        Category cat = new Category("Docs").withId(1L)
        Section sec = new Section("Intro").withId(2L)

        when:
        CategoriesResponse catSimple = new CategoriesResponse([cat])
        CategoriesResponse catPaged = new CategoriesResponse([cat], 59, 1, 2, "next", "prev")
        CategoriesResponse catAdded = catPaged.addCategoriesItem(new Category("FAQ").withId(3L))

        SectionsResponse secSimple = new SectionsResponse([sec])
        SectionsResponse secPaged = new SectionsResponse([sec], 59, 1, 2, "next", "prev")
        SectionsResponse secAdded = secPaged.addSectionsItem(new Section("Setup").withId(4L))

        then:
        verifyAll {
            catSimple instanceof OffsetPaginationResponse
            catSimple.categories() == [cat]
            catSimple.count() == null
            catSimple.page() == null
            catSimple.pageCount() == null
            catAdded.categories().size() == 2
            catAdded.count() == 59
            catAdded.page() == 1
            catAdded.pageCount() == 2
            catAdded.nextPage() == "next"
            catAdded.previousPage() == "prev"

            secSimple instanceof OffsetPaginationResponse
            secSimple.sections() == [sec]
            secSimple.count() == null
            secAdded.sections().size() == 2
            secAdded.count() == 59
            secAdded.page() == 1
            secAdded.pageCount() == 2
            secAdded.nextPage() == "next"
            secAdded.previousPage() == "prev"
        }
    }

    def "CategoryClient sends page and per_page query parameters and decodes pagination response"() {
        when:
        CategoriesResponse byLocale = categoryClient.listCategoriesByPage(LocaleAbbreviation.ENGLISH_UNITED_STATES, 2, 25).block()
        String query1 = capturedQuery
        String path1 = capturedPath

        CategoriesResponse noLocale = categoryClient.listCategoriesNoLocaleByPage(2, 50).block()
        String query2 = capturedQuery
        String path2 = capturedPath

        CategoriesResponse legacy = categoryClient.listCategories(LocaleAbbreviation.ENGLISH_UNITED_STATES).block()
        String query3 = capturedQuery

        then:
        verifyAll {
            path1 == "/api/v2/help_center/en-us/categories"
            query1.contains("page=2")
            query1.contains("per_page=25")
            byLocale.categories().size() == 1
            byLocale.count() == 59
            byLocale.page() == 2
            byLocale.pageCount() == 3
            byLocale.nextPage() != null
            byLocale.previousPage() != null

            path2 == "/api/v2/help_center/categories"
            query2.contains("page=2")
            query2.contains("per_page=50")
            noLocale.count() == 59

            legacy.categories().size() == 1
            query3 == null || query3.isEmpty()
        }
    }

    def "CategoryClient listCategories(locale, null, null) and listCategoriesNoLocale(null, null) compile cleanly in Java"(@TempDir Path tempDir) {
        given:
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler()
        String source = '''
            import lol.pbu.z4j.client.CategoryClient;
            import lol.pbu.z4j.model.LocaleAbbreviation;

            public class CategoryOverloadCompileCheck {
                void check(CategoryClient client, LocaleAbbreviation locale) {
                    client.listCategories(locale, null, null);
                    client.listCategoriesNoLocale(null, null);
                }
            }
        '''
        SimpleJavaFileObject fileObject = new SimpleJavaFileObject(
                URI.create("string:///CategoryOverloadCompileCheck.java"),
                JavaFileObject.Kind.SOURCE
        ) {
            @Override
            CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return source
            }
        }
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>()
        List<String> options = ["-d", tempDir.toString(), "-proc:none", "-classpath", System.getProperty("java.class.path")]

        when:
        Boolean compiled = compiler.getTask(null, null, diagnostics, options, null, [fileObject]).call()

        then:
        verifyAll {
            compiled
            diagnostics.diagnostics.isEmpty()
        }
    }

    def "SectionClient sends page and per_page query parameters across all list methods"() {
        when:
        SectionsResponse byLocale = sectionClient.listSections(LocaleAbbreviation.ENGLISH_UNITED_STATES, 2, 100).block()
        String path1 = capturedPath
        String query1 = capturedQuery

        SectionsResponse noLocale = sectionClient.listSectionsNoLocale(1, 50).block()
        String path2 = capturedPath
        String query2 = capturedQuery

        SectionsResponse inCategory = sectionClient.listSectionsInCategory(LocaleAbbreviation.ENGLISH_UNITED_STATES, 99L, 3, 40).block()
        String path3 = capturedPath
        String query3 = capturedQuery

        SectionsResponse inCategoryNoLocale = sectionClient.listSectionsInCategoryNoLocale(99L, 4, 60).block()
        String path4 = capturedPath
        String query4 = capturedQuery

        then:
        verifyAll {
            path1 == "/api/v2/help_center/en-us/sections"
            query1.contains("page=2")
            query1.contains("per_page=100")
            byLocale.count() == 59
            byLocale.page() == 2
            byLocale.pageCount() == 2
            byLocale.nextPage() == null
            byLocale.previousPage() != null

            path2 == "/api/v2/help_center/sections"
            query2.contains("page=1")
            query2.contains("per_page=50")
            noLocale.sections().size() == 1

            path3 == "/api/v2/help_center/en-us/categories/99/sections"
            query3.contains("page=3")
            query3.contains("per_page=40")
            inCategory.count() == 59

            path4 == "/api/v2/help_center/categories/99/sections"
            query4.contains("page=4")
            query4.contains("per_page=60")
            inCategoryNoLocale.count() == 59
        }
    }
}
