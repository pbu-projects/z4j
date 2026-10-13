package lol.pbu.z4j.client

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

@MicronautTest
class ViewClientSpec extends Z4jSpec {

    @Shared
    ViewClient adminViewClient

    @Shared
    Long firstActiveViewId

    def setupSpec() {
        adminViewClient = adminCtx.getBean(ViewClient.class)
        def views = adminViewClient.listActiveViews().block().getViews()
        if (views != null && !views.isEmpty()) {
            firstActiveViewId = views.get(0).getId()
        }
    }

    def "can list views"() {
        when:
        def response = adminViewClient.listViews().block()

        then:
        noExceptionThrown()
        response != null
        response.getViews() != null
    }

    def "can list active views"() {
        when:
        def response = adminViewClient.listActiveViews().block()

        then:
        noExceptionThrown()
        response != null
        response.getViews() != null
    }

    def "can show a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.showView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        response != null
        response.getView().getId() == firstActiveViewId
    }

    def "can execute a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.executeView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        response != null
        response.getRows() != null
    }

    def "can count a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.countView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        response != null
        response.getViewCount() != null
        response.getViewCount().getViewId() == firstActiveViewId
    }

    def "can list tickets for a view"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.listTicketsForView(firstActiveViewId).block()

        then:
        noExceptionThrown()
        response != null
        response.getTickets() != null
    }

    def "can list tickets for a view with cursor pagination"() {
        setup:
        if (firstActiveViewId == null) return

        when:
        def response = adminViewClient.listTicketsForView(firstActiveViewId, null, 10).block()

        then:
        noExceptionThrown()
        response != null
        if (response.getTickets() != null && !response.getTickets().isEmpty()) {
            response.getMeta() != null
        }
    }
}
