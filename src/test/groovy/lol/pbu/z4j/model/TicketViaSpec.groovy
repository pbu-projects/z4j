package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketViaSpec extends Z4jSpec {

    @Unroll
    def "should put source item"() {
        given:
        def ticketVia = new TicketVia()
        ticketVia.source == null
        def key = faker.lorem().word()
        def value = faker.lorem().sentence()

        when:
        ticketVia.putSourceItem(key, value)

        then:
        ticketVia.source.size() == 1
        ticketVia.source.get(key) == value
    }

    @Unroll
    def "put source item to existing map"() {
        given:
        def existingKey = faker.lorem().word()
        def existingValue = faker.lorem().sentence()
        def ticketVia = new TicketVia(source: [(existingKey): existingValue])
        def newKey = faker.lorem().word()
        def newValue = faker.lorem().sentence()

        when:
        ticketVia.putSourceItem(newKey, newValue)

        then:
        ticketVia.source.size() == 2
        ticketVia.source.get(existingKey) == existingValue
        ticketVia.source.get(newKey) == newValue
    }
}
