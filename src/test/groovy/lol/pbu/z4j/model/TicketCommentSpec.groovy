package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketCommentSpec extends Z4jSpec {

    @Unroll
    def "should add uploads item"() {
        given:
        def ticketComment = new TicketComment()
        ticketComment.uploads == null
        def upload = faker.lorem().word()

        when:
        ticketComment.addUploadsItem(upload)

        then:
        ticketComment.uploads.size() == 1
        ticketComment.uploads.getAt(0) == upload
    }

    @Unroll
    def "add uploads item to existing list"() {
        given:
        def existingUpload = faker.lorem().word()
        def ticketComment = new TicketComment(uploads: [existingUpload])
        def newUpload = faker.lorem().word()

        when:
        ticketComment.addUploadsItem(newUpload)

        then:
        ticketComment.uploads.size() == 2
        ticketComment.uploads.containsAll([existingUpload, newUpload])
    }
}
