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

import io.micronaut.http.client.exceptions.HttpClientResponseException
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.SearchExportType
import lol.pbu.z4j.model.SortBy
import lol.pbu.z4j.model.SortOrder
import spock.lang.Shared
import spock.lang.Unroll

class SearchClientSpec extends Z4jSpec {
    @Shared
    SearchClient adminSearchClient, agentSearchClient, userSearchClient

    def setupSpec() {
        adminSearchClient = adminCtx.getBean(SearchClient.class)
        agentSearchClient = agentCtx.getBean(SearchClient.class)
        userSearchClient = userCtx.getBean(SearchClient.class)
    }

    @Unroll("an #clientName user can run the list method with sortby: #sortBy, sortOrder: #sortOrder and include: #include")
    void "can run the list method"(String clientName, SearchClient client, SortBy sortBy, SortOrder sortOrder, String include) {
        when:
        client.list(faker.bluey().quote(), sortBy, sortOrder, include).block()

        then:
        noExceptionThrown()

        where:
        [[client, clientName], sortBy, sortOrder, include] << [[[adminSearchClient, "admin"], [agentSearchClient, "agent"]],
                                                               [SortBy.values(), null].flatten(),
                                                               [SortOrder.values(), null].flatten(),
                                                               [null, faker.cat().name()]].combinations()
    }

    void "an #clientName user can run the count method"(String clientName, SearchClient client) {
        when:
        client.count(faker.bluey().quote()).block()

        then:
        noExceptionThrown()

        where:
        [client, clientName] << [[adminSearchClient, "admin"], [agentSearchClient, "agent"]]
    }

    @Unroll("a simple user querying the list method fails with #sortBy, #sortOrder and #include")
    void "cannot run searchClient.list()"(SearchClient client, SortBy sortBy, SortOrder sortOrder, String include) {
        when:
        client.list(faker.bluey().quote(), sortBy, sortOrder, include).block()

        then:
        thrown(HttpClientResponseException)

        where:
        [client, sortBy, sortOrder, include] << [[userSearchClient],
                                                 [SortBy.values(), null].flatten(),
                                                 [SortOrder.values(), null].flatten(),
                                                 [null, faker.cat().name()]].combinations()
    }

    void "a normal user cannot run the count method"(SearchClient client) {
        when:
        client.count(faker.bluey().quote()).block()

        then:
        thrown(HttpClientResponseException)

        where:
        client           | _
        userSearchClient | _
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    void "an #clientName can call export method with pageSize: #pageSize, pageAfter: #pageAfter, filterType: #filterType and include: #include"(
            String clientName, SearchClient client, int pageSize, String pageAfter, SearchExportType filterType, String include) {
        when:
        client.export(faker.bluey().quote(), pageSize, pageAfter, filterType, include).block()

        then:
        noExceptionThrown()

        where:
        [[client, clientName], pageSize, pageAfter, filterType, include] << [[[adminSearchClient, "admin"], [agentSearchClient, "agent"]],
                                                                             [100],
                                                                             [faker.internet().uuid()],
                                                                             SearchExportType.values(),
                                                                             ["organizations"]].combinations()
    }
}

