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

import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import java.util.UUID
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.Category
import lol.pbu.z4j.model.CreateCategoryRequest
import lol.pbu.z4j.model.CreateSectionRequest
import lol.pbu.z4j.model.LocaleAbbreviation
import lol.pbu.z4j.model.Section
import lol.pbu.z4j.model.SectionResponse
import lol.pbu.z4j.model.SectionsResponse
import lol.pbu.z4j.model.Translation
import lol.pbu.z4j.model.TranslationCreateRequest
import spock.lang.Shared

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class SectionClientSpec extends Z4jSpec {

    @Shared
    SectionClient adminSectionClient, agentSectionClient, userSectionClient

    @Shared
    CategoryClient adminCategoryClient

    @Shared
    Category testCategory

    @Shared
    List<LocaleAbbreviation> allLocales

    def setupSpec() {
        adminSectionClient = adminCtx.getBean(SectionClient.class)
        agentSectionClient = agentCtx.getBean(SectionClient.class)
        userSectionClient = userCtx.getBean(SectionClient.class)
        adminCategoryClient = adminCtx.getBean(CategoryClient.class)

        allLocales = adminCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.localeAbbreviation }

        CreateCategoryRequest catReq = new CreateCategoryRequest(
                new Category("SectionClientSpec Category " + UUID.randomUUID())
                        .setDescription("Category created for testing SectionClient")
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        testCategory = adminCategoryClient.createCategory(LocaleAbbreviation.ENGLISH_UNITED_STATES, catReq).block().category
    }

    def cleanupSpec() {
        if (testCategory?.id != null) {
            try {
                adminCategoryClient.deleteCategory(LocaleAbbreviation.ENGLISH_UNITED_STATES, testCategory.id).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "can list sections by locale for #userType user type"(SectionClient sectionClient, String userType) {
        when: "listing sections for default locale"
        SectionsResponse response = sectionClient.listSections(LocaleAbbreviation.ENGLISH_UNITED_STATES).block()

        then:
        noExceptionThrown()
        response != null
        response.sections != null

        where:
        [sectionClient, userType] << [
                [adminSectionClient, "admin"],
                [agentSectionClient, "agent"],
                [userSectionClient, "user"]
        ]
    }

    def "can list sections without locale for #userType user type"(SectionClient sectionClient, String userType) {
        when: "listing sections without locale"
        SectionsResponse response = sectionClient.listSectionsNoLocale().block()

        then:
        noExceptionThrown()
        response != null
        response.sections != null

        where:
        [sectionClient, userType] << [
                [adminSectionClient, "admin"],
                [agentSectionClient, "agent"]
        ]
    }

    def "can list sections in category by locale for #userType user type"(SectionClient sectionClient, String userType) {
        when: "listing sections in test category for default locale"
        SectionsResponse response = sectionClient.listSectionsInCategory(LocaleAbbreviation.ENGLISH_UNITED_STATES, testCategory.id).block()

        then:
        noExceptionThrown()
        response != null

        where:
        [sectionClient, userType] << [
                [adminSectionClient, "admin"],
                [agentSectionClient, "agent"],
                [userSectionClient, "user"]
        ]
    }

    def "can list sections in category without locale for #userType user type"(SectionClient sectionClient, String userType) {
        when: "listing sections in test category without locale"
        SectionsResponse response = sectionClient.listSectionsInCategoryNoLocale(testCategory.id).block()

        then:
        noExceptionThrown()
        response != null

        where:
        [sectionClient, userType] << [
                [adminSectionClient, "admin"],
                [agentSectionClient, "agent"]
        ]
    }

    def "can create, show, update, and delete section with locale as admin"() {
        given: "a section creation request"
        String initialName = "Spec Section " + UUID.randomUUID()
        String initialDesc = "Initial description"
        CreateSectionRequest createReq = new CreateSectionRequest(
                new Section(initialName).setDescription(initialDesc)
        )
        Long sectionId = null

        when: "creating section with locale"
        SectionResponse created = adminSectionClient.createSection(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                testCategory.id,
                createReq
        ).block()
        sectionId = created.section.id

        then: "section is created"
        noExceptionThrown()
        created.section != null
        created.section.name == initialName
        created.section.categoryId == testCategory.id

        when: "fetching section with locale"
        SectionResponse fetched = adminSectionClient.showSection(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                sectionId
        ).block()

        then: "fetched section matches created"
        noExceptionThrown()
        fetched.section.id == sectionId
        fetched.section.name == initialName

        when: "updating section position with locale"
        CreateSectionRequest updateReq = new CreateSectionRequest(
                new Section().setPosition(2L)
        )
        SectionResponse updated = adminSectionClient.updateSection(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                sectionId,
                updateReq
        ).block()

        then: "section position is updated"
        noExceptionThrown()
        updated.section.position == 2L

        when: "deleting section with locale"
        adminSectionClient.deleteSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()

        then: "section is deleted"
        noExceptionThrown()

        cleanup: "defensive cleanup if deletion did not happen"
        if (sectionId != null) {
            try {
                adminSectionClient.deleteSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "can create, show, update, and delete section without locale as admin"() {
        given: "a section creation request"
        String initialName = "Spec Section NoLocale " + UUID.randomUUID()
        CreateSectionRequest createReq = new CreateSectionRequest(
                new Section(initialName).setDescription("Initial description no locale")
        )
        Long sectionId = null

        when: "creating section without locale"
        SectionResponse created = adminSectionClient.createSectionNoLocale(
                testCategory.id,
                createReq
        ).block()
        sectionId = created.section.id

        then: "section is created"
        noExceptionThrown()
        created.section != null
        created.section.name == initialName

        when: "fetching section without locale"
        SectionResponse fetched = adminSectionClient.showSectionNoLocale(sectionId).block()

        then: "fetched section matches created"
        noExceptionThrown()
        fetched.section.id == sectionId

        when: "updating section position without locale"
        CreateSectionRequest updateReq = new CreateSectionRequest(
                new Section().setPosition(2L)
        )
        SectionResponse updated = adminSectionClient.updateSectionNoLocale(
                sectionId,
                updateReq
        ).block()

        then: "section position is updated"
        noExceptionThrown()
        updated.section.position == 2L

        when: "deleting section without locale"
        adminSectionClient.deleteSectionNoLocale(sectionId).block()

        then: "section is deleted"
        noExceptionThrown()

        cleanup: "defensive cleanup if deletion did not happen"
        if (sectionId != null) {
            try {
                adminSectionClient.deleteSectionNoLocale(sectionId).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "can update section source locale as admin"() {
        given: "a section created in the default source locale"
        LocaleAbbreviation targetLocale = allLocales.find { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }
        assert targetLocale != null : "Test requires at least one non-en-us locale active in sandbox"

        CreateSectionRequest req = new CreateSectionRequest(
                new Section("Source Locale Test " + UUID.randomUUID())
                        .setDescription("Test section for source locale update")
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        SectionResponse created = adminSectionClient.createSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, testCategory.id, req).block()
        Long sectionId = created.section.id

        and: "a translation added for the target locale"
        TranslationClient translationClient = adminCtx.getBean(TranslationClient.class)
        translationClient.createTranslation("sections", sectionId, new TranslationCreateRequest(
                new Translation()
                        .setLocale(targetLocale)
                        .setTitle("Translation " + UUID.randomUUID())
        )).block()

        when: "updating section source locale to the target locale"
        adminSectionClient.updateSectionSourceLocale(targetLocale, sectionId).block()

        then: "source locale is updated"
        SectionResponse fetched = adminSectionClient.showSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()
        fetched.section.sourceLocale == targetLocale.toString()

        cleanup:
        try {
            adminSectionClient.deleteSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()
        } catch (Exception ignored) {
        }
    }

    def "end user cannot create section"() {
        given: "a section creation request"
        CreateSectionRequest createReq = new CreateSectionRequest(
                new Section("Forbidden Section " + UUID.randomUUID())
        )

        when: "end user attempts to create section"
        userSectionClient.createSection(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                testCategory.id,
                createReq
        ).block()

        then: "forbidden exception is thrown"
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.status == FORBIDDEN
    }

    def "end user cannot delete section"() {
        given: "a section created by admin"
        CreateSectionRequest createReq = new CreateSectionRequest(
                new Section("User Delete Test " + UUID.randomUUID())
        )
        SectionResponse created = adminSectionClient.createSection(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                testCategory.id,
                createReq
        ).block()
        Long sectionId = created.section.id

        when: "end user attempts to delete section"
        userSectionClient.deleteSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()

        then: "forbidden exception is thrown"
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.status == FORBIDDEN

        cleanup: "admin cleans up the section"
        try {
            adminSectionClient.deleteSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()
        } catch (Exception ignored) {
        }
    }

    def "end user cannot update section source locale"() {
        given: "a section created by admin"
        CreateSectionRequest createReq = new CreateSectionRequest(
                new Section("User Source Locale Test " + UUID.randomUUID())
        )
        SectionResponse created = adminSectionClient.createSection(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                testCategory.id,
                createReq
        ).block()
        Long sectionId = created.section.id

        when: "end user attempts to update section source locale"
        userSectionClient.updateSectionSourceLocale(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()

        then: "forbidden exception is thrown"
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.status == FORBIDDEN

        cleanup: "admin cleans up the section"
        try {
            adminSectionClient.deleteSection(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId).block()
        } catch (Exception ignored) {
        }
    }

}
