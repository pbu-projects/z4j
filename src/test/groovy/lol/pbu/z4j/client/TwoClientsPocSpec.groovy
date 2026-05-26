//package lol.pbu.z4j.client
//
//import com.sun.net.httpserver.HttpServer
//import io.micronaut.http.client.annotation.Client
//import io.micronaut.test.extensions.spock.annotation.MicronautTest
//import jakarta.inject.Inject
//import spock.lang.Specification
//
//@MicronautTest
//class TwoClientsPocSpec extends Specification {
//
//    @Inject
//    ArticleClient articleClient
//
//    @Inject @Client("http://localhost:8080")
//    ArticleClient sandboxArticleClient
//
//    HttpServer mockServer
//
//    void setup() {
//        // Start a lightweight, zero-dependency mock HTTP server on port 8080
//        mockServer = HttpServer.create(new InetSocketAddress(8080), 0)
//        mockServer.createContext("/api/v2/help_center/en-us/articles", { exchange ->
//            def response = '{"articles": [], "count": 0}'
//            exchange.responseHeaders.add("Content-Type", "application/json")
//            exchange.sendResponseHeaders(200, response.length())
//            exchange.responseBody.write(response.getBytes())
//            exchange.responseBody.close()
//        })
//        mockServer.start()
//    }
//
//    void cleanup() {
//        mockServer?.stop(0)
//    }
//
//    void "sandbox client makes call to mock server"() {
//        when: "the sandbox client is used"
//        def response = sandboxArticleClient.listArticles("en-us", null, null, null, null).block()
//
//        then: "it should successfully connect to the mock server running on localhost:8080"
//        response != null
//        response.articles != null
//        response.articles.empty
//    }
//}
