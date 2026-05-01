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

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.LocalesResponse
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
class LocaleClientSpec extends Z4jSpec {

    @Shared
    LocaleClient adminLocaleClient, agentsLocaleClient, usersLocaleClient, noAuthLocaleClient,
                  badEmailLocaleClient, badUrlLocaleClient

    def setupSpec() {
        adminLocaleClient = adminCtx.getBean(LocaleClient.class)
        agentsLocaleClient = agentCtx.getBean(LocaleClient.class)
        usersLocaleClient = userCtx.getBean(LocaleClient.class)
        noAuthLocaleClient = badTokenCtx.getBean(LocaleClient.class)
        badEmailLocaleClient = badEmailCtx.getBean(LocaleClient.class)
        badUrlLocaleClient = badUrlCtx.getBean(LocaleClient.class)
    }


    @Unroll
    def "can list public locales for #clientName"(String clientName, LocaleClient LocaleClient) {
        when:
        Mono<LocalesResponse> response = LocaleClient.listLocales()

        then:
        response.block() != null

        where:
        clientName              | LocaleClient
        "managers client"       | adminLocaleClient
        "agents client"         | agentsLocaleClient
        "users client"          | usersLocaleClient
        "no-auth client"        | noAuthLocaleClient
        "bad email client"      | badEmailLocaleClient
    }

    def "calling locales client with a bad URL throws an exception"() {
        when:"create the mono<LocalesResponse> with a bad URL"
        def response = badUrlLocaleClient.listLocales()

        and:"attempt to get next signal"
        response.block()

        then:"bad url causes an httpclientException"
        thrown(HttpClientException)

    }
}

