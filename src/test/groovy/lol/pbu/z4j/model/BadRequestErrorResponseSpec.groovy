package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class BadRequestErrorResponseSpec extends Z4jSpec {

    @Unroll
    def "should put errors item"() {
        given:
        def badRequestErrorResponse = new BadRequestErrorResponse()
        def key = faker.lorem().word()
        def value = faker.lorem().sentence()

        when:
        badRequestErrorResponse.putErrorsItem(key, value)

        then:
        badRequestErrorResponse.errors.size() == 1
        badRequestErrorResponse.errors.get(key) == value
    }

    @Unroll
    def "put errors item to existing map"() {
        given:
        def existingKey = faker.lorem().word()
        def existingValue = faker.lorem().sentence()
        def badRequestErrorResponse = new BadRequestErrorResponse()
        badRequestErrorResponse.putErrorsItem(existingKey, existingValue)

        def newKey = faker.lorem().word()
        def newValue = faker.lorem().sentence()

        when:
        badRequestErrorResponse.putErrorsItem(newKey, newValue)

        then:
        badRequestErrorResponse.errors.size() == 2
        badRequestErrorResponse.errors.get(existingKey) == existingValue
        badRequestErrorResponse.errors.get(newKey) == newValue
    }
}
