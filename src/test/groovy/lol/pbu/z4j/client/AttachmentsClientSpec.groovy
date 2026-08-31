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
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Tag("admin")
class AttachmentsClientSpec extends Z4jSpec {

    @Shared
    AttachmentsClient adminAttachmentsClient, agentAttachmentsClient, userAttachmentsClient,
                      badTokenAttachmentsClient, badUrlAttachmentsClient

    def setupSpec() {
        adminAttachmentsClient = adminCtx.getBean(AttachmentsClient.class)
        agentAttachmentsClient = agentCtx.getBean(AttachmentsClient.class)
        userAttachmentsClient = userCtx.getBean(AttachmentsClient.class)
        badTokenAttachmentsClient = badTokenCtx.getBean(AttachmentsClient.class)
        badUrlAttachmentsClient = badUrlCtx.getBean(AttachmentsClient.class)
    }

    @Unroll
    def "can upload attachment as #userType"(
            AttachmentsClient client, String userType) {
        given: "an authenticated client for #userType"

        when: "initiating file upload"
        client.uploadFiles("test.txt").block()

        then: "response deserializes successfully without exception"
        noExceptionThrown()

        where:
        [client, userType] << [
                [adminAttachmentsClient, "admin"],
                [agentAttachmentsClient, "agent"],
                [userAttachmentsClient, "end user"]
        ]
    }

    @Unroll
    def "calling attachments client with #description throws HttpClientException"(
            String description, AttachmentsClient client) {
        when: "initiating file upload with invalid client configuration"
        client.uploadFiles("test.txt").block()

        then: "an http client exception is thrown"
        thrown(HttpClientException)

        where:
        description       | client
        "invalid token"   | badTokenAttachmentsClient
        "unreachable url" | badUrlAttachmentsClient
    }
}
