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
import lol.pbu.z4j.model.*
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

    /* ---------- list() tests --------------- */

    @SuppressWarnings("GroovyAssignabilityCheck")
    @Unroll("an #clientName user can run the list method with sortby: #sortBy, sortOrder: #sortOrder and include: #include")
    void "can run the list method"(String clientName, SearchClient client, SortBy sortBy, SortOrder sortOrder, String include) {
        when:
        client.list("type:group", sortBy, sortOrder, include, null, null).block()

        then:
        noExceptionThrown()

        where:
        [[client, clientName], sortBy, sortOrder, include] << [[[adminSearchClient, "admin"], [agentSearchClient, "agent"]],
                                                               [SortBy.values(), null].flatten(),
                                                               [SortOrder.values(), null].flatten(),
                                                               [null, faker.cat().name()]].combinations()
    }

    void "can query the list method when results include more than the page size"() {
        given:
        String ticketQuery = "type:ticket"
        def ticketCount = adminSearchClient.count(ticketQuery).block().getCount()
        if (ticketCount < 5) {
            (ticketCount..5).each { createTicketForTest() }
        }

        when:
        def page = 1
        List<SearchResponse> responses = []
        SearchResponse response = adminSearchClient.list(ticketQuery, null, null, null, page, 2).block()
        responses << response
        while (response.nextPage != null && page < 3) {
            response = adminSearchClient.list(ticketQuery, null, null, null, page, 2).block()
            page++
            responses << response
        }

        then:
        noExceptionThrown()
    }

    @Unroll("a simple user querying the list method fails with #sortBy, #sortOrder and #include")
    void "cannot run searchClient.list()"(SearchClient client, SortBy sortBy, SortOrder sortOrder, String include) {
        when:
        client.list(faker.bluey().quote(), sortBy, sortOrder, include, null, null).block()

        then:
        thrown(HttpClientResponseException)

        where:
        [client, sortBy, sortOrder, include] << [[userSearchClient],
                                                 [SortBy.values(), null].flatten(),
                                                 [SortOrder.values(), null].flatten(),
                                                 [null, faker.cat().name()]].combinations()
    }

    /* ---------- count() tests --------------- */

    void "an #clientName user can run the count method"(String clientName, SearchClient client) {
        when:
        client.count(faker.bluey().quote()).block()

        then:
        noExceptionThrown()

        where:
        [client, clientName] << [[adminSearchClient, "admin"], [agentSearchClient, "agent"]]
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

    /* ---------- export() tests --------------- */

    @SuppressWarnings("GroovyAssignabilityCheck")
    void "an #clientName can call export method with pageSize: #pageSize, filterType: #filterType and include: #include"(
            String clientName, SearchClient client, int pageSize, SearchExportType filterType, String include) {
        given:
        client.count("type: $filterType").block()

        when:
        ExportResponse<? extends Exportable> response = client.export("type: $filterType", pageSize, null, filterType, include).block()

        and:
        if (response.getLinks().getNext() != null) {
            client.export("type: $filterType", pageSize, response.getMeta().getAfterCursor(), filterType, include).block()
        }

        then:
        noExceptionThrown()

        where:
        [[client, clientName], pageSize, filterType, include] << [[[adminSearchClient, "admin"], [agentSearchClient, "agent"]],
                                                                  [100],
                                                                  SearchExportType.values(),
                                                                  ["organizations"]].combinations()
    }
}
