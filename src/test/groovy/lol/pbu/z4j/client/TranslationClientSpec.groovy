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

    def "can create, show, update, and delete a translation"() {
        given: "an existing article across all locales"
        Long validArticleId = null

        for (LocaleAbbreviation loc : allLocales) {
            def articles = adminArticleClient.listArticles(loc, null, null, null, null).block()?.articles
            if (articles != null && !articles.isEmpty()) {
                validArticleId = articles.get(0).id
                break
            }
        }
        
        assert validArticleId != null : "CRITICAL SETUP ERROR: No articles exist across ANY locale to test against!"

        and: "find an available locale to translate to (or just try 'es-mx' or something)"
        // Let's use 'fr' or 'es' or just a hardcoded locale that isn't the source locale
        def targetLocale = "fr-ca"

        and: "a new translation request"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(targetLocale)
                        .setTitle("Test Translation")
                        .setBody("Test Body")
        )

        when: "creating the translation"
        def createResponse = adminTranslationClient.createArticleTranslation(validArticleId, req).block()
        def createdTranslation = createResponse.translation
        def createdId = createdTranslation?.id

        then: "it should be created"
        createdId != null
        createdTranslation.title == "Test Translation"

        when: "showing the translation"
        def showResponse = adminTranslationClient.showArticleTranslation(validArticleId, targetLocale).block()

        then: "it should return the same translation"
        showResponse.translation.id == createdId
        showResponse.translation.title == "Test Translation"

        when: "updating the translation"
        def updateReq = new TranslationUpdateRequest(
                new Translation().setTitle("Updated Test Translation")
        )
        def updateResponse = adminTranslationClient.updateArticleTranslation(validArticleId, targetLocale, updateReq).block()

        then: "it should be updated"
        updateResponse.translation.title == "Updated Test Translation"

        cleanup: "delete the translation"
        if (createdId != null) {
            try {
                adminTranslationClient.deleteTranslation(createdId).block()
            } catch (Exception e) {
            }
        }
    }
}
