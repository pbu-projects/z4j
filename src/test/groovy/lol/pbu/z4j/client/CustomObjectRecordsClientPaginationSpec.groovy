package lol.pbu.z4j.client

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CustomObject
import lol.pbu.z4j.model.CustomObjectRecord
import lol.pbu.z4j.model.CustomObjectRecordsCreateRequest
import lol.pbu.z4j.model.CustomObjectsCreateRequest
import org.yaml.snakeyaml.Yaml
import spock.lang.Shared

@MicronautTest
class CustomObjectRecordsClientPaginationSpec extends Z4jSpec {

    @Shared CustomObjectRecordsClient adminObjectRecordsClient
    @Shared CustomObjectsClient adminCustomObjectsClient
    @Shared String customObjectKey
    @Shared List<String> createdRecordIds = []

    def setupSpec() {
        adminObjectRecordsClient = adminCtx.getBean(CustomObjectRecordsClient.class)
        adminCustomObjectsClient = adminCtx.getBean(CustomObjectsClient.class)

        // Create a custom object to test against
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/custom_object_fixtures.yaml").text) as Map
        def objData = fixtures.customObjects[1] as Map
        customObjectKey = (objData.key as String) + "_" + UUID.randomUUID().toString().substring(0, 8)
        def customObject = new CustomObject(customObjectKey, objData.title as String, objData.titlePluralized as String)

        adminCustomObjectsClient.createCustomObject(new CustomObjectsCreateRequest(customObject)).block()
        sleep(2000)

        // Create multiple records to ensure pagination works
        for (int i = 0; i < 3; i++) {
            def createPayload = new CustomObjectRecordsCreateRequest(
                new CustomObjectRecord().setName((objData.recordName as String) + " " + UUID.randomUUID().toString().substring(0, 8))
            )
            def createResponse = adminObjectRecordsClient.createCustomObjectRecord(customObjectKey, createPayload).block()
            createdRecordIds.add(createResponse?.customObjectRecord()?.id)
            sleep(1000)
        }
    }

    def cleanupSpec() {
        for (String id : createdRecordIds) {
            try {
                adminObjectRecordsClient.deleteCustomObjectRecord(customObjectKey, id).block()
            } catch (Exception ignored) {}
        }
        if (customObjectKey != null) {
            try {
                adminCustomObjectsClient.deleteCustomObject(customObjectKey).block()
            } catch (Exception ignored) {}
        }
    }

    def "can list all custom object records using cursor pagination recursively"() {
        when: "retrieving all records"
        def allRecords = adminObjectRecordsClient.listAllCustomObjectRecords(customObjectKey, 1L).collectList().block()

        then: "all created records are returned"
        noExceptionThrown()
        allRecords != null
        allRecords.size() >= 3
        
        // Ensure all created record IDs are present in the results
        def retrievedIds = allRecords.collect { it.id }
        createdRecordIds.every { retrievedIds.contains(it) }
    }


    def "can list all custom object records using default page size"() {
        when: "retrieving all records without specifying page size"
        def allRecords = adminObjectRecordsClient.listAllCustomObjectRecords(customObjectKey).collectList().block()

        then: "all created records are returned"
        noExceptionThrown()
        allRecords != null
        allRecords.size() >= 3
    }
}
