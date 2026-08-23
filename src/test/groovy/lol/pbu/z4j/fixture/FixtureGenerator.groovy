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
package lol.pbu.z4j.fixture

import net.datafaker.Faker
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FixtureGenerator {

    static void main(String[] args) {
        Path outputDir = Paths.get("src/test/resources/fixtures")
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir)
        }

        Faker faker = new Faker(new Random(42L)) // Deterministic seed for reproducible fixture generation

        DumperOptions options = new DumperOptions()
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
        options.setPrettyFlow(true)
        Yaml yaml = new Yaml(options)

        // 1. Category Fixtures
        Map<String, Object> categoryFixtures = [
            categories: [
                [userType: "admin", categoryName: faker.animal().name(), description: faker.backToTheFuture().quote()],
                [userType: "agent", categoryName: faker.animal().name(), description: faker.backToTheFuture().quote()],
                [userType: "user", categoryName: faker.animal().name(), description: faker.backToTheFuture().quote()]
            ],
            deleteCategories: [
                [userType: "admin", categoryName: "${faker.bluey().quote()} ${UUID.randomUUID().toString().substring(0, 8)}".toString(), description: faker.lordOfTheRings().location()],
                [userType: "agent", categoryName: "${faker.bluey().quote()} ${UUID.randomUUID().toString().substring(0, 8)}".toString(), description: faker.lordOfTheRings().location()],
                [userType: "user", categoryName: "${faker.bluey().quote()} ${UUID.randomUUID().toString().substring(0, 8)}".toString(), description: faker.lordOfTheRings().location()]
            ],
            sortOptions: [
                [sortBy: "TITLE", sortOrder: "ASC"],
                [sortBy: "POSITION", sortOrder: "DESC"],
                [sortBy: "CREATED_AT", sortOrder: "ASC"]
            ]
        ]
        Files.writeString(outputDir.resolve("category_fixtures.yaml"), yaml.dump(categoryFixtures))

        // 2. User Segment Fixtures
        Map<String, Object> userSegmentFixtures = [
            userSegments: [
                [userType: "signed_in_users", segmentName: "SignedIn-${faker.cat().name()}".toString(), updatedName: "Updated-${faker.studioGhibli().movie()}".toString()],
                [userType: "staff", segmentName: "Staff-${faker.movie().name()}".toString(), updatedName: "Updated-${faker.studioGhibli().movie()}".toString()]
            ]
        ]
        Files.writeString(outputDir.resolve("user_segment_fixtures.yaml"), yaml.dump(userSegmentFixtures))

        // 3. Ticket Fixtures
        List<Map<String, String>> ticketDataList = []
        5.times {
            ticketDataList << [
                subject: faker.chuckNorris().fact(),
                comment: faker.hitchhikersGuideToTheGalaxy().marvinQuote(),
                updateComment: faker.hitchhikersGuideToTheGalaxy().marvinQuote()
            ]
        }
        Map<String, Object> ticketFixtures = [ticketData: ticketDataList]
        Files.writeString(outputDir.resolve("ticket_fixtures.yaml"), yaml.dump(ticketFixtures))

        // 4. Article Fixtures
        Map<String, Object> articleFixtures = [
            articleQueries: [
                [sortBy: "TITLE", sortOrder: "ASC"],
                [sortBy: "POSITION", sortOrder: "DESC"],
                [sortBy: "CREATED_AT", sortOrder: "ASC"],
                [sortBy: "UPDATED_AT", sortOrder: "DESC"]
            ]
        ]
        Files.writeString(outputDir.resolve("article_fixtures.yaml"), yaml.dump(articleFixtures))

        println "Successfully generated test data fixtures in ${outputDir.toAbsolutePath()}"
    }
}
