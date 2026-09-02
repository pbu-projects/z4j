package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CustomObjectsCreateRequest
import lol.pbu.z4j.model.CustomObjectCreateInput
import org.yaml.snakeyaml.Yaml
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class CustomObjectsClientSpec extends Z4jSpec {

    @Shared CustomObjectsClient adminCustomObjectsClient, agentCustomObjectsClient, userCustomObjectsClient, badTokenCustomObjectsClient, badUrlCustomObjectsClient
    @Shared String existingObjectKey

    def setupSpec() {
        adminCustomObjectsClient = adminCtx.getBean(CustomObjectsClient.class)
        agentCustomObjectsClient = agentCtx.getBean(CustomObjectsClient.class)
        userCustomObjectsClient = userCtx.getBean(CustomObjectsClient.class)
        badTokenCustomObjectsClient = badTokenCtx.getBean(CustomObjectsClient.class)
        badUrlCustomObjectsClient = badUrlCtx.getBean(CustomObjectsClient.class)

        // Create test custom object
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/custom_object_fixtures.yaml").text) as Map
        def objData = fixtures.customObjects[0] as Map
        existingObjectKey = objData.key as String
        def input = new CustomObjectCreateInput()
            .setKey(existingObjectKey)
            .setTitle(objData.title as String)
            .setTitlePluralized(objData.titlePluralized as String)

        adminCustomObjectsClient.createCustomObject(new CustomObjectsCreateRequest().setCustomObject(input)).block()
        sleep(2000)
    }

    def cleanupSpec() {
        if (existingObjectKey != null) {
            try {
                adminCustomObjectsClient.deleteCustomObject(existingObjectKey).block()
            } catch (Exception ignored) {}
        }
    }

    @Unroll
    def "can list custom objects as an #userType"(
            CustomObjectsClient client, String userType) {
        when: "requesting custom objects list"
        client.listCustomObjects().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminCustomObjectsClient, "admin"],
                [agentCustomObjectsClient, "agent"]
        ]
    }

    def "can check custom objects limit as an admin"() {
        when: "requesting custom objects limit"
        adminCustomObjectsClient.customObjectsLimit().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()
    }

    @Unroll
    def "can show custom object by key as an #userType"(
            CustomObjectsClient client, String userType) {
        when: "requesting custom object by key"
        client.showCustomObject(existingObjectKey).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminCustomObjectsClient, "admin"],
                [agentCustomObjectsClient, "agent"]
        ]
    }

    def "end user cannot list custom objects"() {
        when: "requesting custom objects as an end user"
        userCustomObjectsClient.listCustomObjects().block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling custom objects client with #description throws HttpClientException"(
            String description, CustomObjectsClient client) {
        when: "requesting custom objects with invalid client configuration"
        client.listCustomObjects().block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenCustomObjectsClient
        "unreachable url" | badUrlCustomObjectsClient
    }
}
