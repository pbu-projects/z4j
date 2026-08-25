# Zendesk Support API Migration & Code Generation Notes

> **Context**: All client interfaces and model definitions from `Support.yaml` have been migrated into the main source tree. This document summarizes the state of the codebase, the changes applied, skipped duplicates, resolved defects, and next steps for continuing development.

---

## 1. Migration Summary

- **Clients**:
  - **92** new client interfaces added under [`src/main/java/lol/pbu/z4j/client/`](file:///home/jimmy/git/pbu/z4j/src/main/java/lol/pbu/z4j/client/).
  - **1** duplicate client skipped: `SearchClient.java` (retained existing user implementation).
  - **Total Clients**: **99** client interfaces in project.
- **Models**:
  - **732** new model classes added under [`src/main/java/lol/pbu/z4j/model/`](file:///home/jimmy/git/pbu/z4j/src/main/java/lol/pbu/z4j/model/).
  - **21** duplicate models skipped (retained user implementations):
    - `AttachmentThumbnails.java`
    - `CreateResourceResult.java`
    - `FailedResult.java`
    - `JobStatusResponse.java`
    - `LocaleResponse.java`
    - `LocalesResponse.java`
    - `SearchResponse.java`
    - `SystemFieldOptionObject.java`
    - `TicketCreateInput.java`
    - `TicketCreateRequest.java`
    - `TicketFieldCustomStatusObject.java`
    - `TicketFieldsResponse.java`
    - `TicketResponse.java`
    - `TicketUpdateInput.java`
    - `TicketUpdateInputStatus.java`
    - `TicketUpdateInputType.java`
    - `TicketUpdateRequest.java`
    - `TicketUpdateResponse.java`
    - `TicketsCreateRequest.java`
    - `TicketsResponse.java`
    - `UpdateResourceResult.java`
  - **Total Models**: **870** model classes in project.

---

## 2. Transformations & Standardization Applied

Every newly migrated Java file was processed to match existing project conventions:
1. **License Header**: Replaced OpenAPI generator preamble with the standard Apache 2.0 license:
   ```java
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
   ```
2. **Annotation Cleanup**: Removed `@Generated(...)` annotations and `import jakarta.annotation.Generated;` / `import javax.annotation.processing.Generated;`.
3. **Build Script**: In [`build.gradle.kts`](file:///home/jimmy/git/pbu/z4j/build.gradle.kts), commented out `id("io.micronaut.openapi") version "5.0.0"` and the `openapi { ... }` block so OpenAPI generation does not run during standard Gradle builds.

---

## 3. Defects Found & Resolved

### [`CustomObjectRecordsFilteredSearchRequestComplexFilter.java`](file:///home/jimmy/git/pbu/z4j/src/main/java/lol/pbu/z4j/model/CustomObjectRecordsFilteredSearchRequestComplexFilter.java)
- **Problem**: The generated class declared fields named `$and` and `$or` with `@Data`, `@NoArgsConstructor`, and `@AllArgsConstructor`. Micronaut Serde's `@Introspected.Property` processor threw compilation errors (`Element annotated with @Introspected.Property cannot be used as an introspected property: the field is not accessible for visibility [DEFAULT]`) and duplicate constructor conflicts.
- **Resolution**: 
  - Renamed fields to `and` and `or` while keeping `@JsonProperty(JSON_PROPERTY_$_AND)` (`"$and"`) and `@JsonProperty(JSON_PROPERTY_$_OR)` (`"$or"`).
  - Standardized Lombok annotations to `@Accessors(chain = true)`, `@EqualsAndHashCode`, `@ToString`, `@Getter`, `@Setter`.
  - Updated builder helper methods to `addAndItem` and `addOrItem`.

---

## 4. Known Zendesk OAS Artifacts & Watchpoints

1. **Reserved Keyword Models**:
   - `ModelApplication.java` (from OAS `Application` model)
   - `ModelList.java` (from OAS `List` model)
2. **OAS Schema Deficiencies in Zendesk Specs**:
   - In Zendesk's `Support.yaml`, certain endpoints reference `#/components/schemas/TicketFieldResponse` or complex `oneOf`/`allOf` polymorphic structures which required inline enum promotion.
   - When writing client tests for endpoints utilizing generic maps (`Map<String, Object>`), verify if strong typing can be introduced via custom models or serde deserializers.

---

## 5. Build & Verification Status

- `./gradlew compileJava` - **BUILD SUCCESSFUL**
- `./gradlew compileTestGroovy` - **BUILD SUCCESSFUL**
- `./gradlew assemble` - **BUILD SUCCESSFUL** (produces `build/libs/z4j-0.2.1.jar`, `z4j-0.2.1-sources.jar`, and `z4j-0.2.1-javadoc.jar`)

---

## 6. How to Resume Work

1. **Writing Tests**:
   - Create new Spock specification classes extending `lol.pbu.z4j.Z4jSpec` in `src/test/groovy/lol/pbu/z4j/client/` or `src/test/groovy/lol/pbu/z4j/model/`.
   - Adhere to the [AGENTS.md](file:///home/jimmy/git/pbu/z4j/AGENTS.md) guidelines: no mocking for sandbox-testable endpoints, use pre-generated fixtures in `src/test/resources/fixtures/`, and clean up any created sandbox resources in `cleanup:` blocks.
2. **Re-running OAS Generator in Future**:
   - If regenerating from `HelpCenter.yaml` or `Support.yaml`, uncomment `id("io.micronaut.openapi")` and `openapi { ... }` in [`build.gradle.kts`](file:///home/jimmy/git/pbu/z4j/build.gradle.kts) temporarily, run `./gradlew generateClientOpenApiApis generateClientOpenApiModels`, and re-apply transformations.
