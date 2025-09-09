package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class VotesResponseSpec extends Z4jSpec {

    @Unroll
    def "should add votes item"() {
        given:
        def votesResponse = new VotesResponse()
        votesResponse.votes == null
        def vote = new Vote(faker.number().randomNumber())

        when:
        votesResponse.addVotesItem(vote)

        then:
        votesResponse.votes.size() == 1
        votesResponse.votes.getAt(0) == vote
    }

    @Unroll
    def "add votes item to existing list"() {
        given:
        def existingVote = new Vote(faker.number().randomNumber())
        def votesResponse = new VotesResponse(votes: [existingVote])
        def newVote = new Vote(faker.number().randomNumber())

        when:
        votesResponse.addVotesItem(newVote)

        then:
        votesResponse.votes.size() == 2
        votesResponse.votes.containsAll([existingVote, newVote])
    }
}
