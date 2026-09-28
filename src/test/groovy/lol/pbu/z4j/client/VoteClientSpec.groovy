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

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.Article
import lol.pbu.z4j.model.ArticleCreateRequest
import lol.pbu.z4j.model.Locale
import lol.pbu.z4j.model.LocaleAbbreviation
import lol.pbu.z4j.model.Vote
import lol.pbu.z4j.model.VoteCreateRequest
import lol.pbu.z4j.model.VoteResponse
import lol.pbu.z4j.model.VotesResponse
import spock.lang.Isolated
import spock.lang.Shared
import spock.lang.Stepwise

@Isolated
@Stepwise
@MicronautTest
class VoteClientSpec extends Z4jSpec {

    @Shared
    VoteClient adminVoteClient, agentVoteClient, userVoteClient, badUrlVoteClient

    @Shared
    ArticleClient adminArticleClient

    @Shared
    Article testArticle

    @Shared
    LocaleAbbreviation defaultLocale = LocaleAbbreviation.ENGLISH_UNITED_STATES

    @Shared
    boolean testArticleCreatedByUs = false

    def setupSpec() {
        adminVoteClient = adminCtx.getBean(VoteClient.class)
        agentVoteClient = agentCtx.getBean(VoteClient.class)
        userVoteClient = userCtx.getBean(VoteClient.class)
        badUrlVoteClient = badUrlCtx.getBean(VoteClient.class)
        adminArticleClient = adminCtx.getBean(ArticleClient.class)

        def articles = adminArticleClient.listArticles(defaultLocale, null, null, null, null).block()?.articles
        if (articles != null && !articles.isEmpty()) {
            testArticle = articles.get(0)
            return
        }

        for (Locale loc : accountLocales) {
            def locAbbr = loc.localeAbbreviation
            if (locAbbr != null) {
                def locArticles = adminArticleClient.listArticles(locAbbr, null, null, null, null).block()?.articles
                if (locArticles != null && !locArticles.isEmpty()) {
                    testArticle = locArticles.get(0)
                    defaultLocale = locAbbr
                    return
                }
            }
        }

        Long validSectionId = null
        Long validPermissionGroupId = null

        def auth = "Basic " + (System.getenv("Z4J_ADMIN_EMAIL") + "/token:" + System.getenv("Z4J_TOKEN")).bytes.encodeBase64().toString()
        def sectionsConn = URI.create(System.getenv("Z4J_URL") + "/api/v2/help_center/en-us/sections.json").toURL().openConnection()
        sectionsConn.setRequestProperty("Authorization", auth)
        sectionsConn.setRequestProperty("Accept", "application/json")
        def sectionsJson = new ObjectMapper().readValue(sectionsConn.inputStream, Map.class)

        def pgConn = URI.create(System.getenv("Z4J_URL") + "/api/v2/guide/permission_groups.json").toURL().openConnection()
        pgConn.setRequestProperty("Authorization", auth)
        pgConn.setRequestProperty("Accept", "application/json")
        def pgJson = new ObjectMapper().readValue(pgConn.inputStream, Map.class)

        if (sectionsJson.sections && pgJson.permission_groups) {
            validSectionId = sectionsJson.sections[0].id as Long
            validPermissionGroupId = pgJson.permission_groups[0].id as Long
        }

        assert validSectionId != null : "Could not find a valid sectionId for article creation!"
        assert validPermissionGroupId != null : "Could not find a valid permissionGroupId for article creation!"

        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        def createReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Vote Spec Test Article ${entropy}")
                        .setBody("<p>Test article for testing votes.</p>")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        testArticle = adminArticleClient.createArticle(defaultLocale, validSectionId, createReq).block()?.article
        testArticleCreatedByUs = true
        assert testArticle != null && testArticle.id != null : "Failed to seed test article!"
    }

    def cleanupSpec() {
        if (testArticleCreatedByUs && testArticle?.id != null) {
            try {
                adminArticleClient.deleteArticle(defaultLocale, testArticle.id).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can create, show, list, and delete vote on article"() {
        given: "a vote request with value 1"
        def req = new VoteCreateRequest(new Vote(1))
        Long voteId = null
        Long userId = null

        when: "creating vote on article"
        VoteResponse response = adminVoteClient.createVoteOnArticle(testArticle.id, req).block()
        voteId = response?.vote?.id
        userId = response?.vote?.userId

        then: "vote is created with value 1"
        noExceptionThrown()
        response != null
        response.vote != null
        response.vote.id != null
        response.vote.value == 1

        when: "showing vote by id"
        VoteResponse shown = adminVoteClient.showVote(voteId).block()

        then: "shown vote matches created vote"
        noExceptionThrown()
        shown?.vote != null
        shown.vote.id == voteId
        shown.vote.value == 1

        when: "listing votes on article"
        VotesResponse articleVotes = null
        for (int i = 0; i < 5; i++) {
            articleVotes = adminVoteClient.listVotesOnArticle(testArticle.id).block()
            if (articleVotes?.votes?.any { it.id == voteId }) {
                break
            }
            sleep(1000)
        }

        then: "article votes list contains the vote"
        noExceptionThrown()
        articleVotes?.votes != null
        articleVotes.votes.any { it.id == voteId }

        when: "listing votes by user"
        VotesResponse userVotes = null
        for (int i = 0; i < 5; i++) {
            userVotes = adminVoteClient.listVotesByUser(userId).block()
            if (userVotes?.votes?.any { it.id == voteId }) {
                break
            }
            sleep(1000)
        }

        then: "user votes list contains the vote"
        noExceptionThrown()
        userVotes?.votes != null
        userVotes.votes.any { it.id == voteId }

        when: "deleting the vote"
        adminVoteClient.deleteVote(voteId).block()

        then: "no exception is thrown"
        noExceptionThrown()

        cleanup: "delete created vote defensively"
        if (voteId != null) {
            try {
                adminVoteClient.deleteVote(voteId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can upvote and downvote article directly"() {
        given: "the test article"
        Long voteId = null

        when: "upvoting the article directly"
        VoteResponse upResp = adminVoteClient.upvoteArticle(testArticle.id).block()
        voteId = upResp?.vote?.id

        then: "upvote response has value 1"
        noExceptionThrown()
        upResp?.vote != null
        upResp.vote.id != null
        upResp.vote.value == 1

        when: "downvoting the article directly"
        VoteResponse downResp = adminVoteClient.downvoteArticle(testArticle.id).block()

        then: "downvote response has value -1"
        noExceptionThrown()
        downResp?.vote != null
        downResp.vote.value == -1

        cleanup: "delete created vote"
        if (voteId != null) {
            try {
                adminVoteClient.deleteVote(voteId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "calling vote client with bad url fails"() {
        when: "calling with bad url"
        badUrlVoteClient.showVote(12345L).block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
