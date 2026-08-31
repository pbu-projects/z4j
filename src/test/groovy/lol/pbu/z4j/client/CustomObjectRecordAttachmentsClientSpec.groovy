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
import org.junit.jupiter.api.Tag

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@Tag("admin")
class CustomObjectRecordAttachmentsClientSpec extends Z4jSpec {

    @Shared
    CustomObjectRecordAttachmentsClient adminAttachmentsClient, agentAttachmentsClient, userAttachmentsClient,
                                        badTokenAttachmentsClient, badUrlAttachmentsClient

    def setupSpec() {
        adminAttachmentsClient = adminCtx.getBean(CustomObjectRecordAttachmentsClient.class)
        agentAttachmentsClient = agentCtx.getBean(CustomObjectRecordAttachmentsClient.class)
        userAttachmentsClient = userCtx.getBean(CustomObjectRecordAttachmentsClient.class)
        badTokenAttachmentsClient = badTokenCtx.getBean(CustomObjectRecordAttachmentsClient.class)
        badUrlAttachmentsClient = badUrlCtx.getBean(CustomObjectRecordAttachmentsClient.class)
    }

    def "end user cannot list custom object record attachments"() {
        given: "an end user client"

        when: "requesting custom object record attachments as an end user"
        userAttachmentsClient.listCustomObjectRecordAttachments("zen:ticket", "1").block()

        then: "a 403 Forbidden exception is thrown as documented"
        HttpClientResponseException e = thrown()
        e.status == FORBIDDEN
    }

    @Unroll
    def "calling custom object record attachments client with #description throws HttpClientException"(
            String description, CustomObjectRecordAttachmentsClient client) {
        when: "requesting attachments with invalid client configuration"
        client.listCustomObjectRecordAttachments("zen:ticket", "1").block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAttachmentsClient
        "unreachable url" | badUrlAttachmentsClient
    }
}
