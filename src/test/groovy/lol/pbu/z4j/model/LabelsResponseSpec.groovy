package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class LabelsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add labels item"() {
        given:
        def labelsResponse = new LabelsResponse()
        labelsResponse.labels == null
        def label = new Label(faker.lorem().word())

        when:
        labelsResponse.addLabelsItem(label)

        then:
        labelsResponse.labels.size() == 1
        labelsResponse.labels[0] == label
    }

    @Unroll
    def "add labels item to existing list"() {
        given:
        def existingLabel = new Label(faker.lorem().word())
        def labelsResponse = new LabelsResponse()
        labelsResponse.labels = [existingLabel]
        def newLabel = new Label(faker.lorem().word())

        when:
        labelsResponse.addLabelsItem(newLabel)

        then:
        labelsResponse.labels.size() == 2
        labelsResponse.labels.containsAll([existingLabel, newLabel])
    }
}
