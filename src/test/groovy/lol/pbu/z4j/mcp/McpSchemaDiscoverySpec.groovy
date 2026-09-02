package lol.pbu.z4j.mcp

import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.client.TicketFormsClient
import lol.pbu.z4j.client.TicketFieldsClient
import lol.pbu.z4j.client.CustomObjectsClient
import lol.pbu.z4j.client.CustomObjectFieldsClient
import lol.pbu.z4j.model.CustomObjectCreateInput
import lol.pbu.z4j.model.CustomObjectsCreateRequest
import org.yaml.snakeyaml.Yaml
import spock.lang.Shared

class McpSchemaDiscoverySpec extends Z4jSpec {

    @Shared TicketFormsClient ticketFormsClient
    @Shared TicketFieldsClient ticketFieldsClient
    @Shared CustomObjectsClient customObjectsClient
    @Shared CustomObjectFieldsClient customObjectFieldsClient
    @Shared String testObjectKey

    def setupSpec() {
        ticketFormsClient = adminCtx.getBean(TicketFormsClient)
        ticketFieldsClient = adminCtx.getBean(TicketFieldsClient)
        customObjectsClient = adminCtx.getBean(CustomObjectsClient)
        customObjectFieldsClient = adminCtx.getBean(CustomObjectFieldsClient)
        
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/custom_object_fixtures.yaml").text) as Map
        def objData = fixtures.customObjects[0] as Map
        testObjectKey = objData.key as String
        
        def input = new CustomObjectCreateInput()
            .setKey(testObjectKey)
            .setTitle(objData.title as String)
            .setTitlePluralized(objData.titlePluralized as String)

        customObjectsClient.createCustomObject(new CustomObjectsCreateRequest().setCustomObject(input)).block()
        sleep(2000)
    }

    def cleanupSpec() {
        if (testObjectKey != null) {
            try {
                customObjectsClient.deleteCustomObject(testObjectKey).block()
            } catch (Exception ignored) {}
        }
    }

    def "mcp server can discover ticket forms and fields"() {
        when: "an mcp server requests all ticket forms"
        def formsResponse = ticketFormsClient.listTicketForms(null, null, null, null).block()

        then: "the request succeeds"
        noExceptionThrown()
        formsResponse != null

        when: "the mcp server requests ticket fields"
        def fieldsResponse = ticketFieldsClient.listTicketFields(null, null).block()

        then: "the request succeeds"
        noExceptionThrown()
        fieldsResponse != null
    }

    def "mcp server can discover custom objects and their fields"() {
        when: "an mcp server requests all custom objects"
        def objectsResponse = customObjectsClient.listCustomObjects().block()

        then: "the request succeeds"
        noExceptionThrown()
        objectsResponse != null

        when: "the mcp server maps out fields for the custom object"
        def fieldsResponse = customObjectFieldsClient.listCustomObjectFields(testObjectKey, null).block()
        
        then: "the workflow completes without exceptions"
        noExceptionThrown()
        fieldsResponse != null
    }
}
