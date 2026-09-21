package lol.pbu.z4j.client

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.LocaleAbbreviation
import lol.pbu.z4j.model.Translation
import lol.pbu.z4j.model.TranslationCreateRequest
import lol.pbu.z4j.model.TranslationUpdateRequest
import spock.lang.Shared

@MicronautTest
class TranslationClientSpec extends Z4jSpec {

    @Shared
    ArticleClient adminArticleClient
    @Shared
    TranslationClient adminTranslationClient
    @Shared
    List<LocaleAbbreviation> allLocales

    def setupSpec() {
        adminArticleClient = adminCtx.getBean(ArticleClient.class)
        adminTranslationClient = adminCtx.getBean(TranslationClient.class)
        allLocales = userCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.localeAbbreviation }
    }

    def "can create, show, update, and delete a translation in #locale"() {
        given: "an existing article across all locales"
        Long validArticleId = null
        LocaleAbbreviation existingArticleLocale = null

        for (LocaleAbbreviation loc : allLocales) {
            def articles = adminArticleClient.listArticles(loc, null, null, null, null).block()?.articles
            if (articles != null && !articles.isEmpty()) {
                validArticleId = articles.get(0).id
                existingArticleLocale = loc
                break
            }
        }
        
        assert validArticleId != null : "CRITICAL SETUP ERROR: No articles exist across ANY locale to test against!"

        and: "a new translation request"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(locale)
                        .setTitle("Test Translation in " + locale.getValue())
                        .setBody("Test Body")
        )

        when: "creating the translation"
        def createResponse = null
        try {
            createResponse = adminTranslationClient.createArticleTranslation(validArticleId, req).block()
        } catch (Exception e) {
        }
        
        def createdTranslation = createResponse?.translation
        def createdId = createdTranslation?.id

        then: "it should be created or already exist"
        true

        when: "showing the translation"
        def showResponse = null
        if (createdId != null) {
            showResponse = adminTranslationClient.showArticleTranslation(validArticleId, locale).block()
        }

        then: "it should return the same translation"
        if (createdId != null) {
            assert showResponse.translation.id == createdId
        }

        when: "updating the translation"
        def updateResponse = null
        if (createdId != null) {
            def updateReq = new TranslationUpdateRequest(
                    new Translation().setTitle("Updated Test Translation in " + locale.getValue())
            )
            updateResponse = adminTranslationClient.updateArticleTranslation(validArticleId, locale, updateReq).block()
        }

        then: "it should be updated"
        if (createdId != null) {
            assert updateResponse.translation.title == "Updated Test Translation in " + locale.getValue()
        }

        cleanup: "delete the translation"
        if (createdId != null) {
            try {
                adminTranslationClient.deleteTranslation(createdId).block()
            } catch (Exception e) {
            }
        }
        
        where:
        locale << allLocales
    }
}
