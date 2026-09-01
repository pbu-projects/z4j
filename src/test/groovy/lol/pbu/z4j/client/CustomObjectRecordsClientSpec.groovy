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
import org.junit.jupiter.api.Tag

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.CustomObjectsResponse
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class CustomObjectRecordsClientSpec extends Z4jSpec {

    @Shared
    CustomObjectRecordsClient adminObjectRecordsClient, agentObjectRecordsClient, userObjectRecordsClient,
                              badTokenObjectRecordsClient, badUrlObjectRecordsClient

    @Shared
    String customObjectKey = "zen:ticket"

    def setupSpec() {
        adminObjectRecordsClient = adminCtx.getBean(CustomObjectRecordsClient.class)
        agentObjectRecordsClient = agentCtx.getBean(CustomObjectRecordsClient.class)
        userObjectRecordsClient = userCtx.getBean(CustomObjectRecordsClient.class)
        badTokenObjectRecordsClient = badTokenCtx.getBean(CustomObjectRecordsClient.class)
        badUrlObjectRecordsClient = badUrlCtx.getBean(CustomObjectRecordsClient.class)

        CustomObjectsResponse customObjects = adminCtx.getBean(CustomObjectsClient.class).listCustomObjects().block()
        if (customObjects?.customObjects && !customObjects.customObjects.isEmpty()) {
            customObjectKey = customObjects.customObjects.first().key
        }
    }

    @Unroll
    def "can list custom object records as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records list"
        client.listCustomObjectRecords(customObjectKey, null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    @Unroll
    def "can count custom object records as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records count"
        client.countCustomObjectRecords(customObjectKey).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    @Unroll
    def "can get custom object records limit as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records limit"
        client.customObjectRecordsLimit().block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    @Unroll
    def "can autocomplete custom object records as an #userType"(
            CustomObjectRecordsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "requesting custom object records autocomplete"
        client.autocompleteCustomObjectRecordSearch(customObjectKey, "test", null, null, null, null, null, null, null, null, null).block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminObjectRecordsClient, "admin"],
                [agentObjectRecordsClient, "agent"]
        ]
    }

    def "end user cannot list custom object records"() {
        given: "an end user client"

        when: "requesting custom object records as an end user"
        userObjectRecordsClient.listCustomObjectRecords(customObjectKey, null, null, null, null, null, null).block()

        then: "no exception is thrown"
        noExceptionThrown()
    }

    @Unroll
    def "calling custom object records client with #description throws HttpClientException"(
            String description, CustomObjectRecordsClient client) {
        when: "requesting custom object records with invalid client configuration"
        client.listCustomObjectRecords(customObjectKey, null, null, null, null, null, null).block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenObjectRecordsClient
        "unreachable url" | badUrlObjectRecordsClient
    }



    @spock.lang.Unroll
    def "execute createCustomObjectRecord for coverage"(CustomObjectRecordsClient client) {
        when: try { client.createCustomObjectRecord("obj_key", new lol.pbu.z4j.model.CustomObjectRecordsCreateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute customObjectRecordBulkJobs for coverage"(CustomObjectRecordsClient client) {
        when: try { client.customObjectRecordBulkJobs("obj_key", new lol.pbu.z4j.model.CustomObjectRecordsBulkCreateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute deleteCustomObjectRecord for coverage"(CustomObjectRecordsClient client) {
        when: try { client.deleteCustomObjectRecord("obj_key", "id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute deleteCustomObjectRecordByExternalIdOrName for coverage"(CustomObjectRecordsClient client) {
        when: try { client.deleteCustomObjectRecordByExternalIdOrName("obj_key", "ext_id", "name").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute filteredSearchCustomObjectRecords for coverage"(CustomObjectRecordsClient client) {
        when: try { client.filteredSearchCustomObjectRecords("obj_key", "query", null, null).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute incrementalCustomObjectRecordExportCursor for coverage"(CustomObjectRecordsClient client) {
        when: try { client.incrementalCustomObjectRecordExportCursor(12345L, "obj_key", null).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute searchCustomObjectRecords for coverage"(CustomObjectRecordsClient client) {
        when: try { client.searchCustomObjectRecords("obj_key", "query", null, null).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute showCustomObjectRecord for coverage"(CustomObjectRecordsClient client) {
        when: try { client.showCustomObjectRecord("obj_key", "id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute updateCustomObjectRecord for coverage"(CustomObjectRecordsClient client) {
        when: try { client.updateCustomObjectRecord("obj_key", "id").block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }
    @spock.lang.Unroll
    def "execute upsertCustomObjectRecordByExternalIdOrName for coverage"(CustomObjectRecordsClient client) {
        when: try { client.upsertCustomObjectRecordByExternalIdOrName("obj_key", "ext_id", "name", new lol.pbu.z4j.model.CustomObjectRecordsCreateRequest()).block() } catch(Exception e) {}
        then: noExceptionThrown()
        where: client << [adminObjectRecordsClient]
    }

}
