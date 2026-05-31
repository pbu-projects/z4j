package lol.pbu.z4j.model

import io.micronaut.core.type.Argument
import io.micronaut.serde.ObjectMapper
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

@MicronautTest
class TicketCustomFieldSerdeSpec extends Specification {

    @Inject
    ObjectMapper objectMapper

    @Unroll
    def "should deserialize polymorphic custom field type: #expectedClass.simpleName"() {
        given: "A raw JSON snippet representing a customized field payload"
        def json = """{"id": 42, "value": ${jsonValue}}"""

        when: "We explicitly request parsing using a concrete type Argument"
        def targetArgument = Argument.of(TicketCustomField, valueClass)
        TicketCustomField<?> result = objectMapper.readValue(json, targetArgument)

        then: "The structural fields are correctly extracted"
        result.id == 42
        expectedClass.isInstance(result.value)

        and: "The data value matches our expected value perfectly"
        result.value == expectedValue

        where:
        jsonValue             | valueClass | expectedClass | expectedValue
        '"flibberty giblets"' | String     | String        | "flibberty giblets"
        'true'                | Boolean    | Boolean       | true
        '10023'               | Long       | Long          | 10023L
        '45.29'               | Float      | Float         | 45.29f
    }

    def "should handle nested collection signatures seamlessly"() {
        given: "A list-based custom field payload representing tags"
        def json = '{"id": 99, "value": ["alpha", "beta"]}'

        when: "We pass a nested generic Argument signature to the reader"
        // This simulates: TicketCustomField<List<String>>
        def nestedArgument = Argument.of(TicketCustomField, Argument.listOf(String))
        TicketCustomField<List<String>> result = objectMapper.readValue(json, nestedArgument)

        then: "Micronaut passes the composite type parameters all the way to our Serde"
        result.id == 99
        result.value instanceof List
        ((List<String>) result.getValue()).size() == 2
        ((List<String>) result.getValue()).containsAll(["alpha", "beta"])
    }

    def "should serialize custom fields symmetrically back to JSON structures"() {
        given: "A populated instance of a custom field"
        def field = new TicketCustomField<String>(id: 123, value: "PBU Unicorn")

        when: "Writing the object back into a string pipeline"
        String outJson = objectMapper.writeValueAsString(field)

        then: "The output JSON matches the targeted schema requirements exactly"
        outJson == '{"id":123,"value":"PBU Unicorn"}'
    }
}