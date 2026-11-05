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

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CustomObject
import lol.pbu.z4j.model.CustomObjectRecord
import lol.pbu.z4j.model.CustomObjectRecordsCreateRequest
import lol.pbu.z4j.model.CustomObjectsCreateRequest
import org.yaml.snakeyaml.Yaml
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class CustomObjectRecordsClientSpec extends Z4jSpec {

    @Shared CustomObjectRecordsClient adminObjectRecordsClient, agentObjectRecordsClient, badTokenObjectRecordsClient, badUrlObjectRecordsClient
    @Shared CustomObjectsClient adminCustomObjectsClient
    @Shared String customObjectKey

    def setupSpec() {
        adminObjectRecordsClient = adminCtx.getBean(CustomObjectRecordsClient.class)
        agentObjectRecordsClient = agentCtx.getBean(CustomObjectRecordsClient.class)
        badTokenObjectRecordsClient = badTokenCtx.getBean(CustomObjectRecordsClient.class)
        badUrlObjectRecordsClient = badUrlCtx.getBean(CustomObjectRecordsClient.class)
        adminCustomObjectsClient = adminCtx.getBean(CustomObjectsClient.class)

        // Create a custom object to test against
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/custom_object_fixtures.yaml").text) as Map
        def objData = fixtures.customObjects[1] as Map
        customObjectKey = (objData.key as String) + "_" + UUID.randomUUID().toString().substring(0, 8)
        def customObject = new CustomObject(customObjectKey, objData.title as String, objData.titlePluralized as String)

        adminCustomObjectsClient.createCustomObject(new CustomObjectsCreateRequest(customObject)).block()
        sleep(2000)
    }

    def cleanupSpec() {
        if (customObjectKey != null) {
            try {
                adminCustomObjectsClient.deleteCustomObject(customObjectKey).block()
            } catch (Exception ignored) {}
        }
    }

    def "can perform custom object record CRUD lifecycle as an admin"() {
        given: "a payload for a new custom object record"
        def fixtures = new Yaml().load(new File("src/test/resources/fixtures/custom_object_fixtures.yaml").text) as Map
        def objData = fixtures.customObjects[1] as Map
        String recordName = (objData.recordName as String) + " " + UUID.randomUUID().toString().substring(0, 8)
        def createPayload = new CustomObjectRecordsCreateRequest(
            new CustomObjectRecord().setName(recordName)
        )
        String createdRecordId = null

        when: "creating a new custom object record"
        def createResponse = adminObjectRecordsClient.createCustomObjectRecord(customObjectKey, createPayload).block()
        createdRecordId = createResponse?.customObjectRecord()?.id

        then: "the record is created successfully"
        noExceptionThrown()
        createdRecordId != null

        when: "retrieving the created record by id"
        def showResponse = adminObjectRecordsClient.showCustomObjectRecord(customObjectKey, createdRecordId).block()

        then: "record details are retrieved successfully"
        noExceptionThrown()
        showResponse.customObjectRecord().id == createdRecordId

        when: "updating the record"
        def updatePayload = new CustomObjectRecordsCreateRequest(
            new CustomObjectRecord().setName(recordName + " Updated")
        )
        adminObjectRecordsClient.updateCustomObjectRecord(customObjectKey, createdRecordId, updatePayload).block()

        then: "record updates successfully"
        noExceptionThrown()

        when: "listing custom object records"
        def listResponse = adminObjectRecordsClient.listCustomObjectRecords(customObjectKey).block()

        then: "records are returned"
        noExceptionThrown()
        listResponse != null
        listResponse.customObjectRecords() != null

        when: "searching for the record"
        def searchResponse = adminObjectRecordsClient.searchCustomObjectRecords(customObjectKey, recordName).block()

        then: "search returns successfully"
        noExceptionThrown()
        searchResponse != null

        cleanup: "delete the created record"
        if (createdRecordId != null) {
            try {
                adminObjectRecordsClient.deleteCustomObjectRecord(customObjectKey, createdRecordId).block()
            } catch (Exception ignored) {}
        }
    }

    @Unroll
    def "calling custom object records client with #description throws HttpClientException"(
            String description, CustomObjectRecordsClient client) {
        when: "requesting custom object records with invalid client configuration"
        client.listCustomObjectRecords(customObjectKey).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenObjectRecordsClient
        "unreachable url" | badUrlObjectRecordsClient
    }
}
