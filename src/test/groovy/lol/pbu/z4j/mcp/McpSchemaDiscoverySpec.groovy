package lol.pbu.z4j.mcp

import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.client.TicketFormsClient
import lol.pbu.z4j.client.TicketFieldsClient
import lol.pbu.z4j.client.CustomObjectsClient
import lol.pbu.z4j.client.CustomObjectFieldsClient
import spock.lang.Shared

class McpSchemaDiscoverySpec extends Z4jSpec {

    @Shared TicketFormsClient ticketFormsClient
    @Shared TicketFieldsClient ticketFieldsClient
    @Shared CustomObjectsClient customObjectsClient
    @Shared CustomObjectFieldsClient customObjectFieldsClient

    def setupSpec() {
        ticketFormsClient = adminCtx.getBean(TicketFormsClient)
        ticketFieldsClient = adminCtx.getBean(TicketFieldsClient)
        customObjectsClient = adminCtx.getBean(CustomObjectsClient)
        customObjectFieldsClient = adminCtx.getBean(CustomObjectFieldsClient)
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

        when: "the mcp server maps out fields for the first custom object if any exist"
        if (objectsResponse.customObjects && !objectsResponse.customObjects.isEmpty()) {
            def key = objectsResponse.customObjects[0].key
            def fieldsResponse = customObjectFieldsClient.listCustomObjectFields(key, null, null).block()
            
            assert fieldsResponse != null
        }

        then: "the workflow completes without exceptions"
        noExceptionThrown()
    }
}
