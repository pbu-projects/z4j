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
package lol.pbu.z4j.model

import io.micronaut.serde.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

class UserRelatedSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create UserRelated with constructors and builder"() {
        when:
        UserRelated direct = new UserRelated(5, 2, 1, 10)
        UserRelated noArg = new UserRelated()
        UserRelated built = UserRelatedBuilder.builder()
                .assignedTickets(5)
                .ccdTickets(2)
                .organizationSubscriptions(1)
                .requestedTickets(10)
                .build()

        then:
        verifyAll(direct) {
            assignedTickets() == 5
            ccdTickets() == 2
            organizationSubscriptions() == 1
            requestedTickets() == 10
        }
        verifyAll(noArg) {
            assignedTickets() == null
            ccdTickets() == null
            organizationSubscriptions() == null
            requestedTickets() == null
        }
        verifyAll(built) {
            it == direct
            assignedTickets() == 5
            requestedTickets() == 10
        }
    }

    def "should update UserRelated using withers"() {
        given:
        UserRelated initial = new UserRelated()

        when:
        UserRelated updated = initial
                .withAssignedTickets(8)
                .withCcdTickets(4)
                .withOrganizationSubscriptions(2)
                .withRequestedTickets(15)

        then:
        verifyAll(updated) {
            assignedTickets() == 8
            ccdTickets() == 4
            organizationSubscriptions() == 2
            requestedTickets() == 15
        }
    }

    def "should round-trip serialize and deserialize UserRelated"() {
        given:
        UserRelated related = new UserRelated()
                .withAssignedTickets(3)
                .withCcdTickets(1)
                .withOrganizationSubscriptions(0)
                .withRequestedTickets(7)

        when:
        String json = objectMapper.writeValueAsString(related)
        Map map = objectMapper.readValue(json, Map.class)
        UserRelated deserialized = objectMapper.readValue(json, UserRelated.class)

        then:
        verifyAll(map) {
            get("assigned_tickets") == 3
            get("ccd_tickets") == 1
            get("organization_subscriptions") == 0
            get("requested_tickets") == 7
        }
        verifyAll(deserialized) {
            it == related
            assignedTickets() == 3
            ccdTickets() == 1
            organizationSubscriptions() == 0
            requestedTickets() == 7
        }
    }

    def "should verify UserRelated constants"() {
        expect:
        verifyAll {
            UserRelated.JSON_PROPERTY_ASSIGNED_TICKETS == "assigned_tickets"
            UserRelated.JSON_PROPERTY_CCD_TICKETS == "ccd_tickets"
            UserRelated.JSON_PROPERTY_ORGANIZATION_SUBSCRIPTIONS == "organization_subscriptions"
            UserRelated.JSON_PROPERTY_REQUESTED_TICKETS == "requested_tickets"
        }
    }
}
