package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientResponseException
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.SearchSortBy
import lol.pbu.z4j.model.SearchSortOrder
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
    void "can run the list method"(String clientName, SearchClient client, SearchSortBy sortBy, SearchSortOrder sortOrder, String include) {
        when:
        client.list(faker.bluey().quote(), sortBy, sortOrder, include).block()

        then:
        noExceptionThrown()

        when:
        client.count(faker.bluey().quote()).block()

        then:
        noExceptionThrown()

        where:
        [[client, clientName], sortBy, sortOrder, include] << [[[adminSearchClient, "admin"], [agentSearchClient, "agent"]],
                                                               [SearchSortBy.values(), null].flatten(),
                                                               [SearchSortOrder.values(), null].flatten(),
                                                               [null, faker.cat().name()]].combinations()
    }

    void "an #clientName user can run the count method"(String clientName, SearchClient client) {
        when:
        client.count(faker.bluey().quote()).block()

        then:
        noExceptionThrown()

        where:
        [[client, clientName]] << [[adminSearchClient, "admin"], [agentSearchClient, "agent"]]
    }

    @Unroll("a simple user querying the list method fails with #sortBy, #sortOrder and #include")
    void "cannot run searchClient.list()"(SearchClient client, SearchSortBy sortBy, SearchSortOrder sortOrder, String include) {
        when:
        client.list(faker.bluey().quote(), sortBy, sortOrder, include).block()

        then:
        thrown(HttpClientResponseException)

        where:
        [client, sortBy, sortOrder, include] << [[userSearchClient],
                                                 [SearchSortBy.values(), null].flatten(),
                                                 [SearchSortOrder.values(), null].flatten(),
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
}
