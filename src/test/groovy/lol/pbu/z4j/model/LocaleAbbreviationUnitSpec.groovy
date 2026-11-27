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
import java.nio.charset.StandardCharsets
import lol.pbu.z4j.client.ArticleClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class LocaleAbbreviationUnitSpec extends Specification {

    @Shared
    HttpServer server

    @Shared
    @AutoCleanup
    ApplicationContext ctx

    @Shared
    ObjectMapper objectMapper

    @Shared
    ArticleClient articleClient

    void setupSpec() {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/v2/help_center/es-419/articles") { exchange ->
            byte[] body = '{"articles":[{"id":1,"permission_group_id":10,"locale":"es-419","title":"Hola"}]}'.getBytes(StandardCharsets.UTF_8)
            exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            exchange.sendResponseHeaders(200, body.length)
            exchange.responseBody.write(body)
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

    def "NORWEGIAN maps to 'no' instead of 'false'"() {
        expect:
        verifyAll {
            LocaleAbbreviation.NORWEGIAN.getValue() == "no"
            LocaleAbbreviation.NORWEGIAN.toString() == "no"
            LocaleAbbreviation.fromValue("no") == LocaleAbbreviation.NORWEGIAN
            LocaleAbbreviation.fromValue("NO") == LocaleAbbreviation.NORWEGIAN
            objectMapper.writeValueAsString(LocaleAbbreviation.NORWEGIAN) == '"no"'
            objectMapper.readValue('"no"', LocaleAbbreviation) == LocaleAbbreviation.NORWEGIAN
        }

        when:
        LocaleAbbreviation.fromValue("false")

        then:
        thrown(IllegalArgumentException)
    }

    @Unroll
    def "accepts valid unlisted Zendesk locale '#rawLocale' and round-trips JSON"() {
        when:
        LocaleAbbreviation parsed = LocaleAbbreviation.fromValue(rawLocale)
        String json = objectMapper.writeValueAsString(parsed)
        LocaleAbbreviation deserialized = objectMapper.readValue(json, LocaleAbbreviation)

        then:
        verifyAll {
            parsed != null
            parsed.getValue() == expectedNormalized
            parsed.toString() == expectedNormalized
            json == "\"${expectedNormalized}\""
            deserialized == parsed
            LocaleAbbreviation.of(rawLocale) == parsed
        }

        where:
        rawLocale       | expectedNormalized
        "es-419"        | "es-419"
        "ES-419"        | "es-419"
        "de-at"         | "de-at"
        "de-x-informal" | "de-x-informal"
        "nl-x-informal" | "nl-x-informal"
        "en-au"         | "en-au"
        "zh-hk"         | "zh-hk"
        "zh-hans"       | "zh-hans"
    }

    def "implements Serializable and Comparable"() {
        given:
        LocaleAbbreviation german = LocaleAbbreviation.GERMAN
        LocaleAbbreviation informalGerman = LocaleAbbreviation.of("de-x-informal")
        LocaleAbbreviation french = LocaleAbbreviation.FRENCH

        when:
        def out = new ByteArrayOutputStream()
        new ObjectOutputStream(out).withCloseable { it.writeObject(informalGerman) }
        def restored = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray())).withCloseable {
            it.readObject() as LocaleAbbreviation
        }

        then:
        verifyAll {
            informalGerman instanceof Serializable
            informalGerman instanceof Comparable
            restored == informalGerman
            german.compareTo(french) < 0
            french.compareTo(german) > 0
            informalGerman.compareTo(LocaleAbbreviation.of("de-x-informal")) == 0
        }
    }

    def "normalizes using Locale.ROOT regardless of JVM default locale"() {
        given:
        java.util.Locale originalDefault = java.util.Locale.getDefault()

        when:
        java.util.Locale.setDefault(java.util.Locale.forLanguageTag("tr"))
        LocaleAbbreviation finnish = LocaleAbbreviation.fromValue("FI")
        LocaleAbbreviation indonesian = LocaleAbbreviation.fromValue("ID")
        LocaleAbbreviation unlisted = LocaleAbbreviation.fromValue("EN-AU")

        then:
        verifyAll {
            finnish == LocaleAbbreviation.FINNISH
            indonesian == LocaleAbbreviation.INDONESIAN
            unlisted.getValue() == "en-au"
        }

        cleanup:
        java.util.Locale.setDefault(originalDefault)
    }

    def "binds unlisted locale path variable in declarative ArticleClient"() {
        when:
        ArticlesResponse response = articleClient.listArticles(LocaleAbbreviation.fromValue("es-419")).block()

        then:
        verifyAll {
            response != null
            response.articles().size() == 1
            response.articles().first().localeAbbreviation() == LocaleAbbreviation.fromValue("es-419")
        }
    }

    @Unroll
    def "rejects invalid locale '#invalidLocale' and returns null for blank '#blankLocale'"() {
        when:
        LocaleAbbreviation.fromValue(invalidLocale)

        then:
        thrown(IllegalArgumentException)

        expect:
        LocaleAbbreviation.fromValue(blankLocale) == null

        where:
        invalidLocale    | blankLocale
        "invalid_locale" | null
        "../etc/passwd"  | ""
        "a"              | "   "
    }
}
