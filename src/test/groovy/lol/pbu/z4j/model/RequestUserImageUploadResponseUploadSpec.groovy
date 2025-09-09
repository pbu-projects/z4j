package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class RequestUserImageUploadResponseUploadSpec extends Z4jSpec {

    @Unroll
    def "should put headers item"() {
        given:
        def requestUserImageUploadResponseUpload = new RequestUserImageUploadResponseUpload()
        requestUserImageUploadResponseUpload.headers == null
        def key = faker.lorem().word()
        def value = faker.lorem().sentence()

        when:
        requestUserImageUploadResponseUpload.putHeadersItem(key, value)

        then:
        requestUserImageUploadResponseUpload.headers.size() == 1
        requestUserImageUploadResponseUpload.headers.get(key) == value
    }

    @Unroll
    def "put headers item to existing map"() {
        given:
        def existingKey = faker.lorem().word()
        def existingValue = faker.lorem().sentence()
        def requestUserImageUploadResponseUpload = new RequestUserImageUploadResponseUpload(headers: [(existingKey): existingValue])
        def newKey = faker.lorem().word()
        def newValue = faker.lorem().sentence()

        when:
        requestUserImageUploadResponseUpload.putHeadersItem(newKey, newValue)

        then:
        requestUserImageUploadResponseUpload.headers.size() == 2
        requestUserImageUploadResponseUpload.headers.get(existingKey) == existingValue
        requestUserImageUploadResponseUpload.headers.get(newKey) == newValue
    }
}
